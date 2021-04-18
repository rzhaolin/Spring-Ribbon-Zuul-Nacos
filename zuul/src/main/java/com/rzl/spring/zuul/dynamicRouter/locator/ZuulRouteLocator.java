package com.rzl.spring.zuul.dynamicRouter.locator;

import java.util.LinkedHashMap;
import java.util.Map;

import com.rzl.spring.zuul.dynamicRouter.properties.PropertiesAssemble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.util.StringUtils;

public class ZuulRouteLocator extends SimpleRouteLocator  {

	@Autowired
	private ZuulProperties properties;

	@Autowired
	private PropertiesAssemble propertiesAssemble;

	public ZuulRouteLocator(String servletPath, ZuulProperties properties) {
		super(servletPath, properties);
		this.properties = properties;
	}

	public void refresh() {
		doRefresh();
	}

	@Override
	protected Map<String, ZuulRoute> locateRoutes() {
		LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<String, ZuulRoute>();
		routesMap.putAll(super.locateRoutes());
		// 从Nacos中加载路由信息
		routesMap.putAll(propertiesAssemble.getProperties());
		LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
		for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
			String path = entry.getKey();
			// Prepend with slash if not already present.
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (StringUtils.hasText(this.properties.getPrefix())) {
				path = this.properties.getPrefix() + path;
				if (!path.startsWith("/")) {
					path = "/" + path;
				}
			}
			values.put(path, entry.getValue());
		}
		return values;
	}
}
