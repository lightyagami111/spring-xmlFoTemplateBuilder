package com.ivaylorusev.xmlFoTemplateBuilder.yamlservices;

import com.ivaylorusev.xmlFoTemplateBuilder.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YamlControlProperties {

    private Brand brand;
    private MailTypeVariant mailTypeVariant;
    private Salutation salutation;
    private CustomerType customerType;
    private PaymentType paymentType;
    private InvoiceVoucherData invoiceVoucherData;

}
