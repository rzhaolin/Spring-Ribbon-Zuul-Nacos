package com.rzl.spring.cms;

import org.springframework.stereotype.Service;

@Service
public class CmsServcie {
    public String getContent() {
        return "Contentn from cms";
    }

    public String saveContent() {
        return "Save content to cms";
    }
}
