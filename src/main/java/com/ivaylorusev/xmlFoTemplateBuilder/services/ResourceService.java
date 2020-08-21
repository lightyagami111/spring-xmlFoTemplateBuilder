/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivaylorusev.xmlFoTemplateBuilder.services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.apache.commons.io.IOUtils;

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

    private String getInputStreamAsString(InputStream inputStream) {
        String result = "";
        try {
            result = IOUtils.toString(inputStream, Charset.defaultCharset());
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        return result;
    }
    
    public InputStream getTemplateConfig() {
        return this.getInputStream(Paths.get("application.yml"));
    }
    
    public String getComponent(String dir, String componentName) {
        return getInputStreamAsString(getInputStream(Paths.get(dir,componentName+".mustache")));
    }

    public InputStream getComponentIS(String dir, String componentName) {
        return getInputStream(Paths.get(dir,componentName+".mustache"));
    }
    
}
