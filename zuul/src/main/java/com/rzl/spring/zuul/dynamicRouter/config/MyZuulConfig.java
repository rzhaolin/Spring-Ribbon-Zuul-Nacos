package com.rzl.spring.zuul.dynamicRouter.config;

import com.rzl.spring.zuul.dynamicRouter.locator.ZuulRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyZuulConfig {

	@Autowired
	private ZuulProperties zuulProperties;

	@Bean
	public ZuulRouteLocator routeLocator() {
		ZuulRouteLocator routeLocator = new ZuulRouteLocator(
				"", this.zuulProperties);
		return routeLocator;
	}
}
