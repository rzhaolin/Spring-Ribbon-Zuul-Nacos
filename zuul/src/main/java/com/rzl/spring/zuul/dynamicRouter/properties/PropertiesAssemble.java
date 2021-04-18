package com.rzl.spring.zuul.dynamicRouter.properties;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.rzl.spring.zuul.dynamicRouter.entity.ZuulRouteEntity;
import com.rzl.spring.zuul.dynamicRouter.locator.ZuulRouteLocator;
import org.apache.catalina.Executor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.stereotype.Component;

@Component
public class PropertiesAssemble implements InitializingBean {
	@Autowired
	private ZuulRouteLocator simpleRouteLocator;
	@Autowired
	private MyNacosProperties nacosProperties;

	@Override
	public void afterPropertiesSet() throws Exception {
		getProperties();
	}

	public Map<String, ZuulRoute> getProperties() {
		Map<String, ZuulRoute> routes = new LinkedHashMap<>();
		List<ZuulRouteEntity> results = listenerNacos(nacosProperties.getDataId(),nacosProperties.getGroup());
		for (ZuulRouteEntity result : results) {
			if (StringUtils.isBlank(result.getPath())) {
				continue;
			}
			ZuulRoute zuulRoute = new ZuulRoute();
			try {
				BeanUtils.copyProperties(result, zuulRoute);
			} catch (Exception e) {
			}
			routes.put(zuulRoute.getPath(), zuulRoute);
		}
		return routes;
	}

	private List<ZuulRouteEntity> listenerNacos (String dataId, String group) {
		try {
			Properties properties = new Properties();
			properties.put(PropertyKeyConst.SERVER_ADDR, nacosProperties.getServerAddr());
			ConfigService configService = NacosFactory.createConfigService(properties);
			String content = configService.getConfig(dataId, group, nacosProperties.getTimeoutMs());
			//注册Nacos配置更新监听器
			configService.addListener(dataId, group, new Listener()  {
				@Override
				public void receiveConfigInfo(String configInfo) {
//					System.out.println("nacos更新了");
					simpleRouteLocator.refresh();
				}
				@Override
				public Executor getExecutor() {
					return null;
				}
			});

			List<ZuulRouteEntity> list = JSONObject.parseArray(content, ZuulRouteEntity.class);
			if (list == null || list.isEmpty()) {
				return null;
			}

			Stream<ZuulRouteEntity> stream =  list.stream();
			if (stream == null) {
				return null;
			}
			stream = stream.filter(zuulRouteEntity->zuulRouteEntity.getEnabled());
			if (stream == null) {
				return null;
			}
			return stream.collect(Collectors.toList());

		} catch (NacosException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}