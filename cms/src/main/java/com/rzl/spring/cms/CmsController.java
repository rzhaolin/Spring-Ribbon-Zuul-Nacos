package com.rzl.spring.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CmsController {
    public static final String CMS_GET_CONTENT = "cms/getContent";
    public static final String CMS_SAVE_CONTENT = "cms/saveContent";

    @Autowired
    private CmsServcie servcie;

    @GetMapping(path = CMS_GET_CONTENT)
    @LoadBalanced
    public String getContent() {
        return servcie.getContent();
    }

    @GetMapping(path = CMS_SAVE_CONTENT)
    @LoadBalanced
    public String saveContent() {
        return servcie.saveContent();
    }
}
