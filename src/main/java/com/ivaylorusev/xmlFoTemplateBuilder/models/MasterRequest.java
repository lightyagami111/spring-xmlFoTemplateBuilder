package com.ivaylorusev.xmlFoTemplateBuilder.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MasterRequest {

    private Brand brand;
    private MailTypeVariant mailTypeVariant;
    private CustomerInformation customerInformation;
    private PaymentData paymentData;
    private String voucherReferenceNumber;

}
