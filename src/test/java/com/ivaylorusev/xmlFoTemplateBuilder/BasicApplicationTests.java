package com.ivaylorusev.xmlFoTemplateBuilder;

import com.ivaylorusev.xmlFoTemplateBuilder.models.*;
import com.ivaylorusev.xmlFoTemplateBuilder.yamlservices.AttachmentsMustacheService;
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

    @Test
    public void parse() throws Exception {
        MasterRequest masterRequestDump = new MasterRequest(Brand.VW, MailTypeVariant.VOUCHER_DELIVERY,
                                                            new CustomerInformation(Salutation.MR, CustomerType.COMPANY),
                                                            new PaymentData(PaymentType.CC_MASTER),
                                                "444444");

        Dump.dumpToXlsFile("format", attachmentsMustacheService.buildAttachmentTemplate(masterRequestDump));

//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.VW, MailTypeVariant.CREDIT_NOTE,
//                new CustomerInformation(Salutation.MR, CustomerType.COMPANY),
//                new PaymentData(PaymentType.CC_MASTER),
//                null));
//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.VW, MailTypeVariant.CREDIT_NOTE,
//                new CustomerInformation(Salutation.MR, CustomerType.PRIVATE),
//                new PaymentData(PaymentType.CC_MASTER),
//                null));
//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.VW, MailTypeVariant.CREDIT_NOTE,
//                new CustomerInformation(Salutation.MR, CustomerType.COMPANY),
//                new PaymentData(PaymentType.PREPAID),
//                null));
//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.VW, MailTypeVariant.CREDIT_NOTE,
//                new CustomerInformation(Salutation.MR, CustomerType.COMPANY),
//                new PaymentData(PaymentType.CC_MASTER),
//                "54654654645"));
//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.VW, MailTypeVariant.ACTIVATION,
//                new CustomerInformation(Salutation.MR, CustomerType.PRIVATE),
//                new PaymentData(PaymentType.CC_VISA),
//                null));
//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.VW, MailTypeVariant.VOUCHER_INVOICE_DE,
//                new CustomerInformation(Salutation.MR, CustomerType.COMPANY),
//                new PaymentData(PaymentType.CC_MASTER),
//                null));
//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.VW, MailTypeVariant.VOUCHER_INVOICE_EU,
//                new CustomerInformation(Salutation.MR, CustomerType.COMPANY),
//                new PaymentData(PaymentType.CC_MASTER),
//                null));
//
//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.SKODA, MailTypeVariant.CREDIT_NOTE,
//                new CustomerInformation(Salutation.MR, CustomerType.COMPANY),
//                new PaymentData(PaymentType.CC_MASTER),
//                null));
//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.SKODA, MailTypeVariant.CREDIT_NOTE,
//                new CustomerInformation(Salutation.MR, CustomerType.PRIVATE),
//                new PaymentData(PaymentType.CC_MASTER),
//                null));
//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.SKODA, MailTypeVariant.CREDIT_NOTE,
//                new CustomerInformation(Salutation.MR, CustomerType.COMPANY),
//                new PaymentData(PaymentType.PREPAID),
//                null));
//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.SKODA, MailTypeVariant.CREDIT_NOTE,
//                new CustomerInformation(Salutation.MR, CustomerType.COMPANY),
//                new PaymentData(PaymentType.CC_MASTER),
//                "54654654645"));
//
//        attachmentsMustacheService.buildAttachmentTemplate(new MasterRequest(Brand.SKODA, MailTypeVariant.ACTIVATION,
//                new CustomerInformation(Salutation.MR, CustomerType.PRIVATE),
//                new PaymentData(PaymentType.CC_VISA),
//                null));

    }




}
