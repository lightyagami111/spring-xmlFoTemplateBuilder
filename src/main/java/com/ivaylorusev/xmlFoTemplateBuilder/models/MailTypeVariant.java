/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivaylorusev.xmlFoTemplateBuilder.models;

/**
 *
 * @author Ivaylo Rusev
 */
public enum MailTypeVariant {
    
    ACTIVATION_CC("activation-cc"),
    ACTIVATION("activation"),
    CS_MAIL_ACTIVATION("cs-mail-activation"),
    CREDIT_NOTE("credit-note"),
    IDENT("ident"),
    DEACTIVATION("deactivation"),
    DEACTIVATION_NO_PAYMENT("deactivation-no-payment"),
    DEACTIVATION_CANCELLATION("deactivation-cancellation"),
    DEACTIVATION_CONTRADICT_GTC("deactivation-contradict-gtc"),
    DEACTIVATION_PAYMENT_WITHDRAWN("deactivation-payment-withdrawn"),
    PAYMENT_REMINDER_VORKASSE("payment-reminder-vorkasse"),
    DELETE_ORDER_NO_PAYMENT("delete-order-no-payment"),
    UNDER_PAYMENT("under-payment"),
    DELETE_ORDER_NO_PAYMENT_PARTIALLY("delete-order-no-payment-partially"),
    OVER_PAYMENT("over-payment"),
    ORDER_CONFIRMATION_CREDITCARD("order-confirmation-creditcard"),
    ORDER_CONFIRMATION_PAYPAL("order-confirmation-paypal"),
    ORDER_CONFIRMATION_VORKASSE("order-confirmation-vorkasse"),
    ORDER_CONFIRMATION_ZERO_SUM("order-confirmation-zero-sum"),
    CUSTOMER_COMPENSATION("customer-compensation"),
    VOUCHER_WILL_EXPIRE_CC("voucher-will-expire-cc"),
    VOUCHER_ORDER_CONFIRMATION("voucher-order-confirmation"),
    VOUCHER_DELIVERY("voucher-delivery"),
    VOUCHER_INVOICE_DE("voucher-invoice-de"),
    VOUCHER_INVOICE_EU("voucher-invoice-eu"),
    //only Seat specific
    CONFIRMATION("confirmation"),
    ORDER_ACTIVATION("order_activation"),
    ACCOUNT("account"),
    REMINDER_1M("reminder_1M"),
    REMINDER_2W("reminder_2W"),
    REMINDER_1W("reminder_1W"),
    INVOICE("invoice"),
    REVOCATION("revocation"),
    DEACTIVATION_REVOCATION("deactivation_revocation");
    
    private final String type;

    MailTypeVariant(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return type;
    }
    
}
