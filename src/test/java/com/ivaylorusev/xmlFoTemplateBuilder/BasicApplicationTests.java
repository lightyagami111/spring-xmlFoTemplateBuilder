package com.ivaylorusev.xmlFoTemplateBuilder;

import com.ivaylorusev.xmlFoTemplateBuilder.models.*;
import com.ivaylorusev.xmlFoTemplateBuilder.yamlservices.AttachmentsMustacheService;
import com.ivaylorusev.xmlFoTemplateBuilder.yamlservices.AttachmentsYamlService;
import com.ivaylorusev.xmlFoTemplateBuilder.yamlservices.YamlConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicApplicationTests {

    @Autowired
    private AttachmentsMustacheService attachmentsMustacheService;
    @Autowired
    private AttachmentsYamlService attachmentsYamlService;

    @Autowired
    private ReadCSV readCSV;

    @Test
    public void parse() throws Exception {
        MasterRequest masterRequestDump = new MasterRequest(Brand.VW, MailTypeVariant.ACTIVATION,
                                                            new CustomerInformation(Salutation.MR, CustomerType.COMPANY),
                                                            new PaymentData(PaymentType.CC_MASTER),
                                                "444444");

        YamlConfiguration yamlConfiguration = attachmentsYamlService.getYamlConfiguration(masterRequestDump);
        Dump.dumpToYamlFile("yamlConfiguration", yamlConfiguration);

        Dump.dumpToXlsFile("format", attachmentsMustacheService.buildAttachmentTemplate(masterRequestDump));

    }

    @Ignore
    @Test
    public void parseAll() throws Exception {
        List<MasterRequest> masterRequests = readCSV.readYamlPropCombinations(-3);

        for (MasterRequest mr : masterRequests) {
            try {
                attachmentsMustacheService.buildAttachmentTemplate(mr);
            } catch (Exception e) {
                System.out.println("wrong master request see 'wrong...' files ");
                Dump.dumpToYamlFile("wrong masterrequest", mr);
                e.printStackTrace();
            }
        }
    }


}
