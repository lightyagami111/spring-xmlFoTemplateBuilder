
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
                        <fo:block margin-top="1.1mm" margin-left="0.2mm">
                            <fo:external-graphic src="classpath:{//logo}" height="8.6mm" width="3.95cm" content-height="scale-to-fit" scaling="non-uniform"/>
                        </fo:block>

                        <fo:block  margin-bottom="5mm" margin-top="2.22mm" >
                            <fo:leader leader-pattern="rule" leader-length="100%" rule-style="solid" rule-thickness="0.3mm" color="#ECECEC"/>
                        </fo:block>

                    </fo:block>
                </fo:block-container>
            </fo:static-content>
            <fo:static-content flow-name="xsl-region-before">

                <fo:block-container >
                    <fo:block>
                        <fo:block margin-top="1.1mm" margin-left="0.2mm">
                            <fo:external-graphic src="classpath:{//logo}" height="8.6mm" width="3.95cm" content-height="scale-to-fit" scaling="non-uniform"/>
                        </fo:block>

                        <fo:block  margin-bottom="5mm" margin-top="2.22mm" >
                            <fo:leader leader-pattern="rule" leader-length="100%" rule-style="solid" rule-thickness="0.3mm" color="#ECECEC"/>
                        </fo:block>

                    </fo:block>
                </fo:block-container>
                <fo:block text-align="end" margin-top="12mm"  font-size="11pt">
                    {{invoice.text.page}} &#160;<fo:page-number/>/<fo:page-number-citation ref-id="TheVeryLastPage"/>
                </fo:block>
            </fo:static-content>
            <fo:static-content flow-name="xsl-region-after">

                <fo:block >
                    <fo:leader leader-pattern="rule" leader-length="100%" rule-style="solid" rule-thickness="0.3mm" color="#ECECEC"/>
                </fo:block>
                <fo:block  font-size="10pt" margin-top="5mm" >
                    <fo:table  table-layout="fixed" width="100%" >
                        <fo:table-column  column-width="70%" />
                        <fo:table-column  column-width="30%" />


                        <fo:table-body  width="100%" >
                            <fo:table-row >
                                <fo:table-cell >
                                    <fo:block >
                                        {{invoice.footer.address}
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell >
                                    <fo:block >

                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>

                    </fo:table>
                </fo:block>
            </fo:static-content>

            <fo:flow flow-name="xsl-region-body">
            </fo:flow>

        </fo:page-sequence>

    </fo:root>
</xsl:stylesheet>
