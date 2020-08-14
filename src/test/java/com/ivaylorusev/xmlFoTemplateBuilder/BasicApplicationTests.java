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
import java.util.*;
import java.util.function.BiConsumer;

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

        String rootComponent = resourceService.getComponent("/templateLayout","root");
        HashMap<String, Object> templateLayoutsByBrandAndMailType = getTemplateComponent("templateLayout", b, mtv);


        Mustache.Compiler templateLayoutCompiler = Mustache.compiler().
                withDelims("%{ }").
                escapeHTML(false).
                withLoader(new Mustache.TemplateLoader() {
                    public Reader getTemplate(String name) throws FileNotFoundException {
                        return new InputStreamReader(resourceService.getComponentIS("/templateLayout", name));
                    }
                });
        String templateLayout = templateLayoutCompiler.compile(rootComponent).execute(templateLayoutsByBrandAndMailType);





        HashMap<String, Object> templateFlowsByBrandAndMailType = getTemplateComponent("templateFlow", b, mtv);
        Mustache.Compiler templateFlowCompiler = Mustache.compiler().
                withDelims("${ }").
                escapeHTML(false).
                withLoader(new Mustache.TemplateLoader() {
                    public Reader getTemplate(String name) throws FileNotFoundException {
                        return new InputStreamReader(resourceService.getComponentIS("/templateFlow", name));
                    }
                });

        for(Map.Entry<String, Object> flow : templateFlowsByBrandAndMailType.entrySet()) {
            String flowName = flow.getKey();
            List<HashMap<String, Object>> components = (List<HashMap<String, Object>>) flow.getValue();
            StringBuilder sb = new StringBuilder();
            for(HashMap<String, Object> c : components){
                for(Map.Entry<String, Object> ce : c.entrySet()) {
                    String componentName = ce.getKey();
                    HashMap componentData = (HashMap) ce.getValue();
                    String component = resourceService.getComponent("/templateFlow",componentName);
                    sb.append("\n").append(templateFlowCompiler.compile(component).execute(componentData));
                }
            }
            templateFlowsByBrandAndMailType.put(flowName, sb.toString());
        }

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
