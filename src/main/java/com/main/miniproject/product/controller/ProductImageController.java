package com.main.miniproject.product.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.HandlerMapping;

@Controller
public class ProductImageController {
    @Value("${itemImgLocation}")
    String itemImgPath;

    @GetMapping("/src/main/resources/static/images/**")
    public ResponseEntity<Resource> serverFile(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        path = path.replace("/src/main/resources/static/images", "");

        Resource file = new FileSystemResource(itemImgPath + path);
        if (file.exists()) {
            return ResponseEntity.ok().body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}