<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">

    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Arial, sans-serif, SimSun">


        <fo:layout-master-set>
            <fo:simple-page-master master-name="first-page" page-height="29.7cm" page-width="21cm" margin-top="2.3cm"
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
                        <fo:external-graphic src="templateRequestData.logo" height="28.2mm" width="31mm"
                                             content-height="scale-to-fit" scaling="non-uniform"/>
                    </fo:block>
                </fo:block-container>


            </fo:static-content>
            <fo:static-content flow-name="xsl-region-before">


                <fo:block-container margin-top="-7.5mm">
                    <fo:block text-align="left" margin-left="-4.5mm">
                        <fo:external-graphic src="templateRequestData.logo" height="28.2mm" width="31mm"
                                             content-height="scale-to-fit" scaling="non-uniform"/>
                    </fo:block>
                </fo:block-container>


                <fo:block text-align="end" margin-top="12mm" font-size="10pt">
                    templateContentKeys.pagenumbercitation &#160;<fo:page-number/>/
                    <fo:page-number-citation ref-id="TheVeryLastPage"/>
                </fo:block>


            </fo:static-content>
            <fo:static-content flow-name="xsl-region-after">


                <fo:block font-size="7pt" color="#B4B8BA" margin-bottom="0.5cm">
                    templateContentKeys.commercialregister
                    <fo:block/>
                    templateContentKeys.vatvw
                </fo:block>


            </fo:static-content>

            <fo:flow flow-name="xsl-region-body">

                <fo:block id="TheVeryLastPage"></fo:block>
            </fo:flow>

        </fo:page-sequence>

    </fo:root>
</xsl:stylesheet>