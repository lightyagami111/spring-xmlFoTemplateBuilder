/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivaylorusev.xmlFoTemplateBuilder;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 *
 * @author Ivaylo Rusev
 */
@Slf4j
@Service
public class ResourceService {
    private static final String CLASSPATH = "classpath:";
    private final ResourceLoader resourceLoader;
    
    public ResourceService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    private Resource getResource(Path path) {
        return resourceLoader.getResource(CLASSPATH + path.toString());
    }
    
    private InputStream getInputStream(Path path) {
        InputStream result = new ByteArrayInputStream(new byte[]{});
        try {
            Resource resource = getResource(path);
            result = resource.getInputStream();
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        return result;
    }
    
    public InputStream getTemplateConfig() {
        return this.getInputStream(Paths.get("application.yml"));
    }
    
    public InputStream getTemplateComponent(String componentName) {
        return this.getInputStream(Paths.get("/templateComponents",componentName+".xsl"));
    }
    
}
