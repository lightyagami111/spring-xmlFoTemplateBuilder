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
                    <fo:block text-align="end" font-size="10pt" margin-top="12mm">
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
                        <fo:table>

                            <fo:table-body>
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
                    <fo:table>

                        <fo:table-body>
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
                        <fo:table font-size="10pt" space-before="15mm">
                            <fo:table-column column-width="55%"/>
                            <fo:table-column column-width="45%"/>

                            <fo:table-body>
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
                                                                {{invoice.label.invoicedate}}
                                                            </fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell>
                                                            <fo:block text-align="end">
                                                                <xsl:value-of
                                                                        select="//request/invoiceData/invoiceDate"/>
                                                            </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                    <fo:table-row>
                                                        <fo:table-cell>
                                                            <fo:block>
                                                                {{invoice.label.invoiceno}}
                                                            </fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell>
                                                            <fo:block text-align="end">
                                                                <xsl:value-of
                                                                        select="//request/invoiceData/invoiceNumber"/>
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
                                                                {{invoice.text.orderdate}}
                                                            </fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell>
                                                            <fo:block text-align="end">
                                                                <xsl:value-of select="//request/orderDate"/>
                                                            </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                    <fo:table-row>
                                                        <fo:table-cell>
                                                            <fo:block>
                                                                {{invoice.text.orderno}}
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
                                                                {{invoice.text.vatno}}
                                                            </fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell>
                                                            <fo:block text-align="end">
                                                                <xsl:value-of
                                                                        select="//request/customerInformation/taxId"/>
                                                            </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                    <fo:table-row>
                                                        <fo:table-cell number-columns-spanned="2">
                                                            <fo:block>
                                                                {{invoice.label.dateinvoiceservice}}
                                                            </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                    <fo:table-row>
                                                        <fo:table-cell>
                                                            <fo:block>
                                                                {{invoice.label.paymentmethod}}
                                                            </fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell>
                                                            <fo:block text-align="end">
                                                                <fo:inline>
                                                                    {{invoice.label.paycreditcard}} &#160;
                                                                    {{invoice.label.paycreditcard.americanexpress}}
                                                                    &#160;
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
                    <fo:block text-align="end" padding="20pt 20pt 0pt 0pt">
                        <fo:table table-layout="fixed" border-collapse="separate" space-after="7mm">

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
                                            {{invoice.label.total}}
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="end">
                                            <xsl:value-of select="invoiceNetAmount"/> &#160;
                                            <xsl:value-of select="paymentCurrencyCode"/> &#160;
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
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="end" color="{$color}">
                                            <xsl:value-of select="invoiceVatAmount"/> &#160;
                                            <xsl:value-of select="paymentCurrencyCode"/> &#160;
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell>
                                        <fo:block text-align="end">
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell text-align="end" number-columns-spanned="2">
                                        <fo:block>
                                            <fo:leader leader-pattern="rule" leader-length="100%" rule-style="solid"
                                                       rule-thickness="1pt" text-align="end" color="{$color}"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell>
                                        <fo:block text-align="end">
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="end" padding="10pt 0pt 0pt 0pt" font-weight="bold">
                                            {{label.subtotal}} &#160;
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="end" padding="10pt 0pt 0pt 0pt" font-weight="bold">
                                            <xsl:value-of select="invoiceVoucherGrossAmount"/> &#160;
                                            <xsl:value-of select="paymentCurrencyCode"/> &#160;
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell>
                                        <fo:block text-align="end">
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="end" padding="10pt 0pt 0pt 0pt" font-weight="bold">
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="end" padding="10pt 0pt 0pt 0pt" font-weight="bold">
                                            <xsl:value-of select="invoiceGrossAmount"/> &#160;
                                            <xsl:value-of select="paymentCurrencyCode"/> &#160;
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>

                        </fo:table>
                    </fo:block>
                    <fo:block id="TheVeryLastPage"></fo:block>
                </fo:flow>

            </fo:page-sequence>

        </fo:root>
    </xsl:template>

    <xsl:template match="//request/orderPositionsMapByVin">
        <fo:block font-size="10pt" space-before="15mm">
            <fo:table>
                <fo:table-header>
                    <fo:table-cell>
                        <fo:block font-size="16pt" font-weight="bold">
                            <xsl:value-of select="modelName"/> &#160;
                        </fo:block>

                        <fo:block margin-bottom="1cm">
                            <fo:inline>
                                <xsl:value-of select="vin"/> &#160;
                            </fo:inline>
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
                                    <fo:table-row>
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
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell number-columns-spanned="4">
                                            <fo:block xsl:use-attribute-sets="borderbottom" margin-left="-2mm"
                                                      margin-right="-2mm">

                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
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