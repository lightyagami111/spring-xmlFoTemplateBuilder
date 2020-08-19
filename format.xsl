<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">

    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Arial, sans-serif, SimSun">


            <fo:layout-master-set>
                    <fo:simple-page-master master-name="first-page" page-height="29.7cm" page-width="21cm" margin-top="2.3cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
                        <fo:region-body  region-name="xsl-region-body"   margin-top="4cm" margin-bottom="3cm" />
                         <fo:region-before region-name="xsl-region-before-first" extent="1cm"/> 
                         <fo:region-after region-name="xsl-region-after" extent="3cm" margin-bottom="2cm"/> 
                        
                        
                    </fo:simple-page-master>
                    <fo:simple-page-master master-name="all-pages" page-height="29.7cm" page-width="21cm" margin-top="2.3cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
                        <fo:region-body  region-name="xsl-region-body"   margin-top="5.5cm" margin-bottom="3cm" />
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
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-before">
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after">
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body">

                    <fo:block id="TheVeryLastPage"></fo:block>
                </fo:flow>

            </fo:page-sequence>

    </fo:root>
</xsl:stylesheet>