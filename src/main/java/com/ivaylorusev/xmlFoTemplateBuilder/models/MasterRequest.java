package com.ivaylorusev.xmlFoTemplateBuilder.models;

import lombok.Data;

@Data
public class MasterRequest {

    private MailTypeVariant mailType;
    private Brand brand;
    private CustomerInformation customerInformation;
    private PaymentData paymentData;

}
