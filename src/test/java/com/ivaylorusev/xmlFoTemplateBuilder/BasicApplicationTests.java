package com.ivaylorusev.xmlFoTemplateBuilder;

import com.ivaylorusev.xmlFoTemplateBuilder.models.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicApplicationTests {

    @Autowired
    private AttachmentsMustacheService attachmentsMustacheService;
    @Autowired
    private AttachmentsYamlService attachmentsYamlService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void hashmaps() throws Exception {
        Brand b = Brand.VW;
        MailTypeVariant mtv = MailTypeVariant.ACTIVATION;
        Salutation s = Salutation.MR;
        CustomerType c = CustomerType.PRIVATE;
        MasterRequest masterRequest = new MasterRequest();
        masterRequest.setBrand(b);
        masterRequest.setMailTypeVariant(mtv);
        masterRequest.setCustomerInformation(new CustomerInformation(s, c));

        attachmentsYamlService.getYamlConfiguration(masterRequest);

    }

    @Ignore
    @Test
    public void parse() throws Exception {
        Brand b = Brand.VW;
        MailTypeVariant mtv = MailTypeVariant.CREDIT_NOTE;
        Salutation s = Salutation.MR;
        CustomerType c = CustomerType.COMPANY;
        MasterRequest masterRequest = new MasterRequest();
        masterRequest.setBrand(b);
        masterRequest.setMailTypeVariant(mtv);
        masterRequest.setCustomerInformation(new CustomerInformation(s, c));

        attachmentsMustacheService.buildAttachmentTemplate(masterRequest);

    }


}
