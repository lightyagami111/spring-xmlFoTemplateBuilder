package com.ivaylorusev.xmlFoTemplateBuilder.services;

import com.ivaylorusev.xmlFoTemplateBuilder.models.*;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class YamlControlProperties {

    private Brand brand;
    private MailTypeVariant mailTypeVariant;
    private Salutation salutation;
    private CustomerType customerType;
    private PaymentType paymentType;
    private InvoiceVoucherData invoiceVoucherData;

    public YamlControlProperties(MasterRequest masterRequest) {
        brand = masterRequest.getBrand();
        mailTypeVariant = masterRequest.getMailTypeVariant();
        salutation = masterRequest.getCustomerInformation().getSalutation();
        customerType = masterRequest.getCustomerInformation().getCustomerType();
        paymentType = masterRequest.getPaymentData().getPaymentType();

        if (StringUtils.hasText(masterRequest.getVoucherReferenceNumber())) {
            invoiceVoucherData = InvoiceVoucherData.HAVE_VOUCHER_DATA;
        }
        else {
            invoiceVoucherData = InvoiceVoucherData.NO_VOUCHER_DATA;
        }
    }

}
