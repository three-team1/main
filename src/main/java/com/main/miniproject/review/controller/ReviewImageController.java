package com.main.miniproject.review.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReviewImageController {
    @Value("${reviewResizedLocation}")
    String reviewResizedPath;

    @GetMapping("/review/resized/**")
    public ResponseEntity<Resource> serverFile(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        path = path.replace("/review/resized", "");

        Resource file = new FileSystemResource(reviewResizedPath + path);
        if (file.exists()) {
            return ResponseEntity.ok().body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
