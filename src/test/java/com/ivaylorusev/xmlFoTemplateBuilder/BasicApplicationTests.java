package com.ivaylorusev.xmlFoTemplateBuilder;

import com.ivaylorusev.xmlFoTemplateBuilder.models.*;
import com.ivaylorusev.xmlFoTemplateBuilder.services.AttachmentsMustacheService;
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

    Brand b = Brand.VW;
    MailTypeVariant mtv = MailTypeVariant.ACTIVATION;
    Salutation s = Salutation.MR;
    CustomerType c = CustomerType.PRIVATE;
    MasterRequest masterRequest = new MasterRequest(b, mtv, new CustomerInformation(s, c), new PaymentData(null));


    @Test
    public void parse() throws Exception {
        Dump.dumpToXlsFile("format", attachmentsMustacheService.buildAttachmentTemplate(masterRequest));
    }




}
