package com.ivaylorusev.xmlFoTemplateBuilder;

import com.ivaylorusev.xmlFoTemplateBuilder.models.MasterRequest;
import com.samskivert.mustache.Mustache;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@Service
public class AttachmentsMustacheService {

    private final ResourceService resourceService;
    private final AttachmentsYamlService attachmentsYamlService;


    private Mustache.TemplateLoader templateLoader = new Mustache.TemplateLoader() {
        public Reader getTemplate(String name) throws FileNotFoundException {
            return new InputStreamReader(resourceService.getComponentIS("/templateLayouts", name));
        }
    };

    private Mustache.Compiler templateLayoutCompiler = Mustache.compiler().withDelims("%{ }").escapeHTML(false);
    private Mustache.Compiler templateFlowCompiler = Mustache.compiler().withDelims("${ }").escapeHTML(false).withLoader(templateLoader);
    private Mustache.Compiler templateDataCompiler = Mustache.compiler().withDelims("&{ }").escapeHTML(false);


    public AttachmentsMustacheService(ResourceService resourceService, AttachmentsYamlService attachmentsYamlService) {
        this.resourceService = resourceService;
        this.attachmentsYamlService = attachmentsYamlService;
    }


    public void buildAttachmentTemplate(MasterRequest masterRequest) throws Exception {
        YamlConfiguration yamlConfiguration = attachmentsYamlService.getYamlConfiguration(masterRequest);

        //building root layout
        String rootComponent = resourceService.getComponent("/templateLayouts","root");
        String templateLayout = compile(templateLayoutCompiler, rootComponent, yamlConfiguration.getTemplateLayout());

        //building layout flows
        String templateFlow = compile(templateFlowCompiler, templateLayout, yamlConfiguration.getTemplateFlow());

        Files.writeString(Paths.get("format.xsl"), templateFlow);

        //----------------------------------
        //resolve content keys & request data fields
        //----------------------------------
    /*
        HashMap<String, Object> templateRequestData = getTemplateRequestData(b, mtv);
        HashMap<String, Object> templateContentKeys = getTemplateContentKeys(b, mtv);
        HashMap<String, Object> templateData = new HashMap<>();
        templateData.putAll(templateRequestData);
        templateData.putAll(templateContentKeys);
        Mustache.Compiler templateDataCompiler = Mustache.compiler().withDelims("&{ }").escapeHTML(false);
        Files.writeString(Paths.get("format.xsl"), templateDataCompiler.compile(templateFlow).execute(templateData));
    */

    }

    public String compile(Mustache.Compiler compiler, String template, HashMap<String, Object> data) {
        return compiler.compile(template).execute(data);
    }

}
