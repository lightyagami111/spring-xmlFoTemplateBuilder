package com.ivaylorusev.xmlFoTemplateBuilder.services;

import com.ivaylorusev.xmlFoTemplateBuilder.models.*;
import lombok.Data;

@Data
public class YamlControlProperties {

    private Brand brand;
    private MailTypeVariant mailTypeVariant;
    private Salutation salutation;
    private CustomerType customerType;
    private PaymentType paymentType;

    public YamlControlProperties(MasterRequest masterRequest) {
        brand = masterRequest.getBrand();
        mailTypeVariant = masterRequest.getMailTypeVariant();
        salutation = masterRequest.getCustomerInformation().getSalutation();
        customerType = masterRequest.getCustomerInformation().getCustomerType();
        paymentType = masterRequest.getPaymentData().getPaymentType();
    }

}
