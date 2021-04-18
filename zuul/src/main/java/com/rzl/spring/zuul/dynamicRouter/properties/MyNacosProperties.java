package com.rzl.spring.zuul.dynamicRouter.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class MyNacosProperties {
    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;

    @Value("${project.nacos.dataId}")
    private String dataId;

    @Value("${project.nacos.group}")
    private String group;

    @Value("${project.nacos.timeoutMs}")
    private long timeoutMs;
}

