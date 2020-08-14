package com.ivaylorusev.xmlFoTemplateBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ivaylorusev.xmlFoTemplateBuilder.models.Brand;
import com.ivaylorusev.xmlFoTemplateBuilder.models.ExtendedHashMap;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MailTypeVariant;
import com.samskivert.mustache.Mustache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicApplicationTests {

    @Autowired
    private ResourceService resourceService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void parse() throws Exception {
        Brand b = Brand.SKODA;
        MailTypeVariant mtv = MailTypeVariant.ACTIVATION;

        HashMap templateLayoutsByBrandAndMailType = getTemplateComponent("templateLayout", b, mtv);
        String component = resourceService.getComponent("root");

        Mustache.Compiler templateLayoutCompiler = Mustache.compiler().
                withDelims("%{ }").
                escapeHTML(false).
                withLoader(new Mustache.TemplateLoader() {
                    public Reader getTemplate(String name) throws FileNotFoundException {
                        return new InputStreamReader(resourceService.getComponentIS("/templateLayout", name));
                    }
                });       

        String templateLayout = templateLayoutCompiler.compile(component).execute(templateLayoutsByBrandAndMailType);

        HashMap templateFlowsByBrandAndMailType = getTemplateComponent("templateFlow", b, mtv);
        Mustache.Compiler templateFlowCompiler = Mustache.compiler().
                withDelims("${ }").
                escapeHTML(false).
                withLoader(new Mustache.TemplateLoader() {
                    public Reader getTemplate(String name) throws FileNotFoundException {
                        return new InputStreamReader(resourceService.getComponentIS("/templateFlow", name));
                    }
                });

        System.out.println(templateFlowCompiler.compile(templateLayout).execute(templateFlowsByBrandAndMailType));
    }

    private HashMap getTemplateConfigHashMap() throws Exception {
        InputStream templateConfig = resourceService.getTemplateConfig();
        TypeReference<HashMap> typeRef = new TypeReference<>() {
        };
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        HashMap templateConfigHashMap = objectMapper.readValue(templateConfig, typeRef);

        return templateConfigHashMap;
    }

    private HashMap getTemplateComponent(String component, Brand b, MailTypeVariant mtv) throws Exception {
        HashMap templateConfigHashMap = getTemplateConfigHashMap();
        HashMap templateComponents = (HashMap) templateConfigHashMap.get(component);

        List<Object> matchedKeys = ExtendedHashMap.findMatchedKeys(templateComponents, b.name());
        for (Object key : matchedKeys) {
            HashMap templateComponentsByBrand = (HashMap) templateComponents.get(key);
            HashMap templateComponentsByBrandAndMailType = (HashMap) ExtendedHashMap.get(templateComponentsByBrand, mtv.getType());
            if (templateComponentsByBrandAndMailType != null) {
                return templateComponentsByBrandAndMailType;
            }
        }
        return null;
    }


}
