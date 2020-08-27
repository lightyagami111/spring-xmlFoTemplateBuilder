<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Arial, sans-serif, SimSun">

            <fo:layout-master-set>
                <fo:simple-page-master master-name="first-page" page-height="29.7cm" page-width="21cm"
                                       margin-top="2.3cm"
                                       margin-bottom="2cm" margin-left="2cm" margin-right="2cm">

                    <fo:region-body region-name="xsl-region-body" margin-top="4cm" margin-bottom="3cm"/>

                    <fo:region-before region-name="xsl-region-before-first" extent="1cm"/>

                    <fo:region-after region-name="xsl-region-after" extent="3cm" margin-bottom="2cm"/>


                </fo:simple-page-master>
                <fo:simple-page-master master-name="all-pages" page-height="29.7cm" page-width="21cm" margin-top="2.3cm"
                                       margin-bottom="2cm" margin-left="2cm" margin-right="2cm">

                    <fo:region-body region-name="xsl-region-body" margin-top="5.5cm" margin-bottom="3cm"/>

                    <fo:region-before region-name="xsl-region-before" extent="1cm"/>

                    <fo:region-after region-name="xsl-region-after" extent="3cm" margin-bottom="2cm"/>


                </fo:simple-page-master>

                <fo:page-sequence-master master-name="my-sequence">
                    <fo:single-page-master-reference master-reference="first-page"/>
                    <fo:repeatable-page-master-reference master-reference="all-pages"/>
                </fo:page-sequence-master>

            </fo:layout-master-set>


            <fo:page-sequence master-reference="my-sequence">
                <fo:static-content flow-name="xsl-region-before-first">


                    <fo:block-container margin-top="-7.5mm">


                        <fo:block text-align="left" margin-left="-4.5mm">


                            <fo:external-graphic src="classpath:{//logo}" height="28.2mm" width="31mm"
                                                 content-height="scale-to-fit" scaling="non-uniform"/>


                        </fo:block>


                    </fo:block-container>


                </fo:static-content>

                <fo:static-content flow-name="xsl-region-before">


                    <fo:block-container margin-top="-7.5mm">


                        <fo:block text-align="left" margin-left="-4.5mm">


                            <fo:external-graphic src="classpath:{//logo}" height="28.2mm" width="31mm"
                                                 content-height="scale-to-fit" scaling="non-uniform"/>


                        </fo:block>


                    </fo:block-container>


                    <fo:block text-align="end" margin-top="12mm" font-size="10pt">


                        {{invoice.text.page}} &#160;


                        <fo:page-number/>/
                        <fo:page-number-citation ref-id="TheVeryLastPage"/>


                    </fo:block>


                </fo:static-content>

                <fo:static-content flow-name="xsl-region-after">


                    <fo:block font-size="7pt" color="#B4B8BA" margin-bottom="0.5cm">


                        {{invoice.text.commercialregister}} &#160;


                        <fo:block/>

                        {{invoice.text.vatvw}} &#160;


                    </fo:block>


                    <fo:block font-size="7pt">


                        <fo:table table-layout="fixed" width="100%" border-collapse="separate">


                            <fo:table-body width="100%">


                                <fo:table-row>


                                    <fo:table-cell>


                                        <fo:block color="#B4B8BA">


                                            {{invoice.text.postalvw}} &#160;


                                        </fo:block>


                                    </fo:table-cell>
                                    <fo:table-cell>


                                        <fo:block color="#B4B8BA">


                                        </fo:block>


                                    </fo:table-cell>
                                    <fo:table-cell>


                                        <fo:block color="#B4B8BA">


                                            {{invoice.text.board}} &#160;


                                        </fo:block>


                                    </fo:table-cell>


                                </fo:table-row>


                            </fo:table-body>


                        </fo:table>


                    </fo:block>


                </fo:static-content>

                <fo:flow flow-name="xsl-region-body">


                    <fo:table table-layout="fixed" width="100%">


                        <fo:table-body width="100%">


                            <fo:table-row>


                                <fo:table-cell>


                                    <fo:block font-size="16pt" color="#899093" font-weight="bold">


                                        {{invoice.subheadline.shop}} &#160;


                                    </fo:block>


                                </fo:table-cell>

                                <fo:table-cell display-align="after">


                                    <fo:block text-align="end" font-size="10pt">


                                        {{invoice.text.page}} &#160;


                                        <fo:page-number/>/
                                        <fo:page-number-citation ref-id="TheVeryLastPage"/>


                                    </fo:block>


                                </fo:table-cell>


                            </fo:table-row>


                        </fo:table-body>


                    </fo:table>


                    <fo:block>


                        <fo:table table-layout="fixed" width="100%" border-collapse="separate" font-size="10pt"
                                  space-before="15mm">


                            <fo:table-column column-width="55%"/>
                            <fo:table-column column-width="45%"/>


                            <fo:table-body width="100%">


                                <fo:table-row>


                                    <fo:table-cell>


                                        <fo:block-container width="85%">


                                            <fo:block>


                                                <xsl:value-of select="//request/customerInformation/company"/> &#160;


                                                <fo:block/>


                                                <xsl:value-of select="//request/customerInformation/addr1"/> &#160;
                                                <xsl:value-of select="//request/customerInformation/houseNumber"/>
                                                &#160;


                                                <fo:block/>


                                                <xsl:value-of select="//request/customerInformation/postcode"/> &#160;
                                                <xsl:value-of select="//request/customerInformation/city"/> &#160;


                                                <fo:block/>


                                                <xsl:value-of select="//request/customerInformation/countryCode"/>
                                                &#160;


                                                <fo:block/>


                                                <fo:block/>


                                                <xsl:value-of select="//request/customerInformation/email"/> &#160;


                                            </fo:block>


                                        </fo:block-container>


                                    </fo:table-cell>
                                    <fo:table-cell>


                                        <fo:block>


                                            <fo:table>


                                                <fo:table-body>


                                                    <fo:table-row>


                                                        <fo:table-cell>


                                                            <fo:block>


                                                                {{invoice.label.invoicedate}} &#160;


                                                            </fo:block>


                                                        </fo:table-cell>
                                                        <fo:table-cell>


                                                            <fo:block text-align="end">


                                                                <xsl:value-of
                                                                        select="//request/invoiceData/invoiceDate"/>
                                                                &#160;


                                                            </fo:block>


                                                        </fo:table-cell>


                                                    </fo:table-row>
                                                    <fo:table-row>


                                                        <fo:table-cell>


                                                            <fo:block>


                                                                {{invoice.label.invoiceno}} &#160;


                                                            </fo:block>


                                                        </fo:table-cell>
                                                        <fo:table-cell>


                                                            <fo:block text-align="end">


                                                                <xsl:value-of
                                                                        select="//request/invoiceData/invoiceNumber"/>
                                                                &#160;


                                                            </fo:block>


                                                        </fo:table-cell>


                                                    </fo:table-row>
                                                    <fo:table-row>


                                                    </fo:table-row>
                                                    <fo:table-row>


                                                    </fo:table-row>
                                                    <fo:table-row>


                                                        <fo:table-cell>


                                                            <fo:block>


                                                                {{invoice.text.orderdate}} &#160;


                                                            </fo:block>


                                                        </fo:table-cell>
                                                        <fo:table-cell>


                                                            <fo:block text-align="end">


                                                                <xsl:value-of select="//request/orderDate"/> &#160;


                                                            </fo:block>


                                                        </fo:table-cell>


                                                    </fo:table-row>
                                                    <fo:table-row>


                                                        <fo:table-cell>


                                                            <fo:block>


                                                                {{invoice.text.orderno}} &#160;


                                                            </fo:block>


                                                        </fo:table-cell>
                                                        <fo:table-cell>


                                                            <fo:block text-align="end">


                                                                <xsl:call-template name="intersperse-with-zero-spaces">
                                                                    <xsl:with-param name="str"
                                                                                    select="//request/orderIdentifier"/>
                                                                </xsl:call-template>


                                                            </fo:block>


                                                        </fo:table-cell>


                                                    </fo:table-row>
                                                    <fo:table-row>


                                                    </fo:table-row>
                                                    <fo:table-row>


                                                        <fo:table-cell>


                                                            <fo:block>


                                                                {{invoice.text.vatno}} &#160;


                                                            </fo:block>


                                                        </fo:table-cell>
                                                        <fo:table-cell>


                                                            <fo:block text-align="end">


                                                                <xsl:value-of
                                                                        select="//request/customerInformation/taxId"/>
                                                                &#160;


                                                            </fo:block>


                                                        </fo:table-cell>


                                                    </fo:table-row>
                                                    <fo:table-row>


                                                        <fo:table-cell number-columns-spanned="2">


                                                            <fo:block>


                                                                {{invoice.label.dateinvoiceservice}} &#160;


                                                            </fo:block>


                                                        </fo:table-cell>


                                                    </fo:table-row>
                                                    <fo:table-row>


                                                        <fo:table-cell>


                                                            <fo:block>


                                                                {{invoice.label.paymentmethod}} &#160;


                                                            </fo:block>


                                                        </fo:table-cell>
                                                        <fo:table-cell>


                                                            <fo:block text-align="end">


                                                                <fo:inline>


                                                                    {{invoice.label.paycreditcard}} &#160;
                                                                    {{invoice.label.paycreditcard.mastercard}} &#160;


                                                                </fo:inline>


                                                            </fo:block>


                                                        </fo:table-cell>


                                                    </fo:table-row>


                                                </fo:table-body>


                                            </fo:table>


                                        </fo:block>


                                    </fo:table-cell>


                                </fo:table-row>


                            </fo:table-body>


                        </fo:table>


                    </fo:block>


                    <fo:block>


                        <fo:block>


                            <xsl:apply-templates select="//orderPositionsMapByVin"/>


                        </fo:block>


                        <fo:block page-break-before="always">


                            <fo:block>


                                <fo:block text-align="end" padding="20pt 20pt 0pt 0pt">


                                    <fo:table table-layout="fixed" border-collapse="separate">


                                        <fo:table-column column-width="45%"/>
                                        <fo:table-column column-width="30%"/>
                                        <fo:table-column column-width="25%"/>


                                        <fo:table-body>


                                            <fo:table-row>


                                                <fo:table-cell>


                                                    <fo:block text-align="end">


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end">


                                                        {{invoice.label.total}} &#160;


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end">


                                                        <xsl:value-of select="//request/invoiceData/invoiceNetAmount"/>
                                                        &#160;
                                                        <xsl:value-of
                                                                select="//request/paymentData/paymentCurrencyCode"/>
                                                        &#160;


                                                    </fo:block>


                                                </fo:table-cell>


                                            </fo:table-row>
                                            <fo:table-row>


                                                <fo:table-cell>


                                                    <fo:block text-align="end">


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end" color="{$color}">


                                                        {{invoice.label.vat}} &#160;


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end" color="{$color}">


                                                        <xsl:value-of select="//request/invoiceData/invoiceVatAmount"/>
                                                        &#160;
                                                        <xsl:value-of
                                                                select="//request/paymentData/paymentCurrencyCode"/>
                                                        &#160;


                                                    </fo:block>


                                                </fo:table-cell>


                                            </fo:table-row>
                                            <fo:table-row>


                                                <fo:table-cell>


                                                </fo:table-cell>


                                                <fo:table-cell text-align="end" number-columns-spanned="2">


                                                    <fo:leader leader-pattern="rule" leader-length="100%"
                                                               rule-style="solid" rule-thickness="1pt" text-align="end"
                                                               color="#D4D7D8"/>


                                                </fo:table-cell>


                                            </fo:table-row>


                                        </fo:table-body>


                                    </fo:table>


                                </fo:block>


                            </fo:block>
                            <fo:block>


                                <fo:block>


                                    <fo:table table-layout="fixed" border-collapse="separate">


                                        <fo:table-body>


                                            <fo:table-row>


                                                <fo:table-cell>


                                                    <fo:block text-align="end">


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end" padding="10pt 0pt 0pt 0pt"
                                                              font-weight="bold">


                                                        {{label.subtotal}} &#160;


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end" padding="10pt 0pt 0pt 0pt"
                                                              font-weight="bold">


                                                        <xsl:value-of
                                                                select="//request/invoiceData/invoiceVoucherData/invoiceVoucherGrossAmount"/>
                                                        &#160;
                                                        <xsl:value-of
                                                                select="//request/paymentData/paymentCurrencyCode"/>
                                                        &#160;


                                                    </fo:block>


                                                </fo:table-cell>


                                            </fo:table-row>
                                            <fo:table-row>


                                                <fo:table-cell>


                                                    <fo:block text-align="end">


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end" padding="10pt 0pt 0pt 0pt">


                                                        <fo:block>


                                                            <xsl:value-of select="//voucherCode"/> &#160;


                                                        </fo:block>


                                                        <fo:block font-weight="bold">


                                                            {{ds-general-label-voucher}} &#160;


                                                        </fo:block>


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end" padding="10pt 0pt 0pt 0pt"
                                                              font-weight="bold">


                                                        <xsl:value-of
                                                                select="//request/paymentData/paymentCurrencyCode"/>
                                                        &#160;
                                                        -<xsl:value-of select="//request/orderPosition/voucherAmount"/>
                                                        &#160;


                                                    </fo:block>


                                                </fo:table-cell>


                                            </fo:table-row>
                                            <fo:table-row>


                                                <fo:table-cell>


                                                </fo:table-cell>


                                                <fo:table-cell text-align="end" number-columns-spanned="2">


                                                    <fo:leader leader-pattern="rule" leader-length="100%"
                                                               rule-style="solid" rule-thickness="1pt" text-align="end"
                                                               color="#B4B8BA"/>


                                                </fo:table-cell>


                                            </fo:table-row>


                                        </fo:table-body>


                                    </fo:table>


                                </fo:block>
                                <fo:block>


                                    <fo:table table-layout="fixed" border-collapse="separate">


                                        <fo:table-body>


                                            <fo:table-row>


                                                <fo:table-cell>


                                                    <fo:block text-align="end">


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end" padding="10pt 0pt 0pt 0pt">


                                                        {{invoice.label.total}} &#160;


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end" padding="10pt 0pt 0pt 0pt">


                                                        <xsl:value-of
                                                                select="//request/invoiceData/invoiceVoucherData/invoiceVoucherNetAmount"/>
                                                        &#160;
                                                        <xsl:value-of
                                                                select="//request/paymentData/paymentCurrencyCode"/>
                                                        &#160;


                                                    </fo:block>


                                                </fo:table-cell>


                                            </fo:table-row>
                                            <fo:table-row>


                                                <fo:table-cell>


                                                    <fo:block text-align="end">


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end" color="#B4B8BA">


                                                        {{invoice.label.vat}} &#160;


                                                    </fo:block>


                                                </fo:table-cell>
                                                <fo:table-cell>


                                                    <fo:block text-align="end" color="#B4B8BA">


                                                        <xsl:value-of
                                                                select="//request/invoiceData/invoiceVoucherData/invoiceVoucherVatAmount"/>
                                                        &#160;
                                                        <xsl:value-of
                                                                select="//request/paymentData/paymentCurrencyCode"/>
                                                        &#160;


                                                    </fo:block>


                                                </fo:table-cell>


                                            </fo:table-row>
                                            <fo:table-row>


                                                <fo:table-cell>


                                                </fo:table-cell>


                                                <fo:table-cell text-align="end" number-columns-spanned="2">


                                                    <fo:leader leader-pattern="rule" leader-length="100%"
                                                               rule-style="solid" rule-thickness="1pt" text-align="end"
                                                               color="#B4B8BA"/>


                                                </fo:table-cell>


                                            </fo:table-row>


                                        </fo:table-body>


                                    </fo:table>


                                </fo:block>


                            </fo:block>
                            <fo:block>


                                <fo:table table-layout="fixed" border-collapse="separate">


                                    <fo:table-column column-width="45%"/>
                                    <fo:table-column column-width="30%"/>
                                    <fo:table-column column-width="25%"/>


                                    <fo:table-body>


                                        <fo:table-row>


                                            <fo:table-cell>


                                                <fo:block text-align="end">


                                                </fo:block>


                                            </fo:table-cell>
                                            <fo:table-cell>


                                                <fo:block text-align="end" padding="10pt 0pt 0pt 0pt"
                                                          font-weight="bold">


                                                    {{invoice.label.invoicetotal}} &#160;


                                                </fo:block>


                                            </fo:table-cell>
                                            <fo:table-cell>


                                                <fo:block text-align="end" padding="10pt 0pt 0pt 0pt"
                                                          font-weight="bold">


                                                    <xsl:value-of select="//request/invoiceData/invoiceGrossAmount"/>
                                                    &#160;
                                                    <xsl:value-of select="//request/paymentData/paymentCurrencyCode"/>
                                                    &#160;


                                                </fo:block>


                                            </fo:table-cell>


                                        </fo:table-row>


                                    </fo:table-body>


                                </fo:table>


                            </fo:block>


                        </fo:block>


                    </fo:block>


                    <fo:block id="TheVeryLastPage"></fo:block>
                </fo:flow>

            </fo:page-sequence>

        </fo:root>
    </xsl:template>


    <xsl:template match="//orderPositionsMapByVin">


        <fo:block font-size="10pt" space-before="15mm">


            <fo:table>


                <fo:table-header>


                    <fo:table-cell>


                        <fo:block font-size="16pt" font-weight="bold">


                            <xsl:value-of select="modelName"/> &#160;


                        </fo:block>


                    </fo:table-cell>
                    <fo:table-cell>


                        <fo:block margin-bottom="1cm">


                            <xsl:value-of select="vin"/> &#160;


                        </fo:block>


                    </fo:table-cell>


                </fo:table-header>


                <fo:table-body>


                    <fo:table-row>


                        <fo:table-cell>


                            <fo:table table-layout="fixed" width="100%" border-collapse="separate" margin-top="-6.06mm"
                                      border-separation="4mm" xsl:use-attribute-sets="borderbottom">


                                <fo:table-column column-width="40%"/>
                                <fo:table-column column-width="20%"/>
                                <fo:table-column column-width="20%"/>
                                <fo:table-column column-width="20%"/>


                                <fo:table-header width="100%" font-size="12pt">


                                    <fo:table-cell>


                                        <fo:block font-weight="bold">


                                            {{label.product}} &#160;


                                        </fo:block>


                                    </fo:table-cell>
                                    <fo:table-cell>


                                        <fo:block font-weight="bold">


                                            {{invoice.labe.articelno}} &#160;


                                        </fo:block>


                                    </fo:table-cell>
                                    <fo:table-cell>


                                        <fo:block font-weight="bold">


                                            {{invoice.label.period}} &#160;


                                        </fo:block>


                                    </fo:table-cell>
                                    <fo:table-cell>


                                        <fo:block font-weight="bold" text-align="right">


                                            {{invoice.label.price}} &#160; {{text.netPrice}} &#160;


                                        </fo:block>


                                    </fo:table-cell>


                                </fo:table-header>


                                <fo:table-body width="100%">


                                    <xsl:apply-templates select="orderPositions"/>


                                </fo:table-body>


                            </fo:table>


                        </fo:table-cell>


                    </fo:table-row>


                </fo:table-body>


            </fo:table>


        </fo:block>


    </xsl:template>
    <xsl:template match="orderPositions">


        <fo:table-row>


            <fo:table-cell>


                <fo:block>


                    <xsl:value-of select="articleLicense/salesItemName"/> &#160;


                </fo:block>


            </fo:table-cell>
            <fo:table-cell>


                <fo:block>


                    <xsl:value-of select="articleLicense/salesItemNumber"/> &#160;


                </fo:block>


            </fo:table-cell>
            <fo:table-cell>


                <fo:block>


                    <xsl:value-of select="articleLicense/articleRunDuration"/> &#160;


                </fo:block>


            </fo:table-cell>
            <fo:table-cell>


                <fo:block text-align="right">


                    <xsl:value-of select="articleLicense/salesItemTotalNetAmount"/> &#160;


                    <xsl:value-of select="//request/paymentData/paymentCurrencyCode"/> &#160;


                </fo:block>


            </fo:table-cell>


        </fo:table-row>


    </xsl:template>


</xsl:stylesheet>