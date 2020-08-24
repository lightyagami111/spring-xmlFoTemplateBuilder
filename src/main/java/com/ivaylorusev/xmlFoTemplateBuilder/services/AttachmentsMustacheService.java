package com.ivaylorusev.xmlFoTemplateBuilder.services;

import com.ivaylorusev.xmlFoTemplateBuilder.ResourceService;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MasterRequest;
import com.samskivert.mustache.Mustache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

@Service
public class AttachmentsMustacheService {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private AttachmentsYamlService attachmentsYamlService;


    private Mustache.TemplateLoader templateLoader = new Mustache.TemplateLoader() {
        public Reader getTemplate(String name) throws FileNotFoundException {
            return new InputStreamReader(resourceService.getComponentIS("/templateLayouts", name));
        }
    };

    private Mustache.Compiler templateLayoutCompiler = Mustache.compiler().withDelims("%{ }").escapeHTML(false);
    private Mustache.Compiler templateFlowCompiler = Mustache.compiler().withDelims("${ }").escapeHTML(false).withLoader(templateLoader);


    public String buildAttachmentTemplate(MasterRequest masterRequest) throws Exception {
        YamlConfiguration yamlConfiguration = attachmentsYamlService.getYamlConfiguration(new YamlControlProperties(masterRequest));

        //building root layout
        Object rootLayout = yamlConfiguration.getTemplateLayout().get("root");
        String templateLayout = "";
        if (rootLayout instanceof String) { //it's the name of template
            templateLayout = resourceService.getComponent("/templateLayouts", (String) yamlConfiguration.getTemplateLayout().get("root"));
        } else if (rootLayout instanceof HashMap) { //it's hashmap structure , must compile root.mustache
            String rootComponent = resourceService.getComponent("/templateLayouts", "root");
            templateLayout = compile(templateLayoutCompiler, rootComponent, yamlConfiguration.getTemplateLayout());
        }

        //building layout flows
        String templateFlow = compile(templateFlowCompiler, templateLayout, yamlConfiguration.getTemplateFlow());

        //resolve content keys
        return templateFlow;

    }

    public String compile(Mustache.Compiler compiler, String template, HashMap<String, Object> data) {
        return compiler.compile(template).execute(data);
    }

}
