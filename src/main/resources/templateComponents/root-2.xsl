<?xml version="1.1" encoding="utf-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">

        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">            
            <fo:layout-master-set>
                <fo:simple-page-master master-name="my-sequence">
                    <fo:region-body/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="my-sequence">
                <fo:flow flow-name="xsl-region-body">
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
        
</xsl:stylesheet>