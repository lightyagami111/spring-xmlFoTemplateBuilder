<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="SKODANext, sans-serif, SimSun">
        <fo:layout-master-set>
            <fo:simple-page-master master-name="first-page" page-height="29.7cm" page-width="21cm" margin-top="0.6cm" margin-bottom="2cm" margin-left="1.2cm" margin-right="1cm">
                <fo:region-body  region-name="xsl-region-body"   margin-top="3.109cm" margin-bottom="3cm" />
                <fo:region-before region-name="xsl-region-before-first" extent="1cm"/>
                <fo:region-after region-name="xsl-region-after" extent="3.5cm" margin-bottom="2cm"/>


            </fo:simple-page-master>
            <fo:simple-page-master master-name="all-pages" page-height="29.7cm" page-width="21cm" margin-top="0.6cm" margin-bottom="2cm" margin-left="1.2cm" margin-right="1cm">
                <fo:region-body  region-name="xsl-region-body"   margin-top="3.5cm" margin-bottom="3cm" />
                <fo:region-before region-name="xsl-region-before" extent="1cm"/>
                <fo:region-after region-name="xsl-region-after" extent="3.5cm" margin-bottom="2cm"/>


            </fo:simple-page-master>

            <fo:page-sequence-master master-name="my-sequence">
                <fo:single-page-master-reference master-reference="first-page"/>
                <fo:repeatable-page-master-reference master-reference="all-pages"/>
            </fo:page-sequence-master>

        </fo:layout-master-set>

        <fo:page-sequence master-reference="my-sequence">
            <fo:static-content flow-name="xsl-region-before-first">

                <fo:block-container >
                    <fo:block>
                        <fo:block text-align="left" margin-left="-4.5mm">
                            <fo:external-graphic src="classpath:{//logo}" height="28.2mm" width="31mm" content-height="scale-to-fit" scaling="non-uniform"/>
                        </fo:block>

                        <fo:block margin-bottom="5mm" margin-top="2.22mm">
                            <fo:leader leader-pattern="rule" leader-length="100%" rule-style="solid" rule-thickness="0.3mm" color="#ECECEC"/>
                        </fo:block>

                    </fo:block>
                </fo:block-container>
            </fo:static-content>
            <fo:static-content flow-name="xsl-region-before">

                <fo:block-container >
                    <fo:block>
                        <fo:block text-align="left" margin-left="-4.5mm">
                            <fo:external-graphic src="classpath:{//logo}" height="28.2mm" width="31mm" content-height="scale-to-fit" scaling="non-uniform"/>
                        </fo:block>

                        <fo:block margin-bottom="5mm" margin-top="2.22mm">
                            <fo:leader leader-pattern="rule" leader-length="100%" rule-style="solid" rule-thickness="0.3mm" color="#ECECEC"/>
                        </fo:block>

                    </fo:block>
                </fo:block-container>
                <fo:block text-align="end" margin-top="12mm"  font-size="11pt">
                    {{invoice.text.page}} &#160;<fo:page-number/>/<fo:page-number-citation ref-id="TheVeryLastPage"/>
                </fo:block>
            </fo:static-content>
            <fo:static-content flow-name="xsl-region-after">
            </fo:static-content>

            <fo:flow flow-name="xsl-region-body">
            </fo:flow>

        </fo:page-sequence>
    </fo:root>
</xsl:stylesheet>