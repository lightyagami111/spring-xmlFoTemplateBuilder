<?xml version="1.0" encoding="UTF-8"?>

<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>

                <fo:simple-page-master master-name="first-page">
                    <fo:region-body/>
                    <fo:region-before region-name="xsl-region-before-first"/>
                    <fo:region-after region-name="xsl-region-after"/>
                </fo:simple-page-master>

                <fo:simple-page-master master-name="all-pages">
                    <fo:region-body/>
                    <fo:region-before region-name="xsl-region-before"/>
                    <fo:region-after region-name="xsl-region-after"/>
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

                </fo:flow>

            </fo:page-sequence>
</fo:root>
