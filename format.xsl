<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Arial, sans-serif, SimSun">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="my-sequence" page-height="29.7cm" page-width="21cm">
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="my-sequence">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block-container background-color="#1ca3db" padding-top="12.75mm" height="92.25mm">
                        <fo:block-container border-color="white" border-style="solid" border-width="0.4mm"
                                            margin-left="12.75mm" width="171.2mm">
                            <fo:block-container margin-left="0mm">
                                <fo:block margin-left="9.9mm" margin-top="50.679mm" font-weight="bold" font-size="26pt"
                                          line-height="28.262pt" text-align="start" color="#003870">
                                    {{label.ds-voucher-pdf-headline-bold}} &#160;
                                </fo:block>
                                <fo:block margin-left="9.9mm" margin-bottom="11.58mm" margin-top="1.67mm"
                                          font-size="26pt" line-height="28.262pt" text-align="start" color="#003870">
                                    {{label.ds-voucher-pdf-headline}} &#160;
                                </fo:block>
                            </fo:block-container>
                        </fo:block-container>
                    </fo:block-container>
                    <fo:block-container background-color="#1ca3db" position="absolute" top="28.74mm" left="177.094mm"
                                        width="15.169mm" height="18.62mm">
                        <fo:block-container margin-top="1.37mm" width="15.169mm" height="15.169mm">
                            <fo:block>
                                <fo:external-graphic src="classpath:{//logo}" content-height="scale-to-fit"
                                                     content-width="scale-to-fit" scaling="non-uniform" width="15.169mm"
                                                     height="15.169mm"/>
                            </fo:block>
                        </fo:block-container>
                    </fo:block-container>
                    <fo:block-container margin-left="23mm" margin-top="7.8mm">
                        <fo:block-container margin-left="0mm" width="170mm">
                            <fo:block>
                                <fo:block font-size="10pt" line-height="3.835mm" text-align="start">
                                    {{ds-voucher-pdf-text}} &#160;
                                </fo:block>
                            </fo:block>
                            <fo:block>
                                <fo:block margin-left="30.933mm" margin-top="14.707mm" font-weight="bold"
                                          font-size="16pt" line-height="6.136mm" text-align="start">
                                    {{ds-voucher-pdf-subheadline-amount}} &#160;
                                </fo:block>
                            </fo:block>
                            <fo:block>
                                <fo:table border-collapse="separate" table-layout="fixed" width="100%"
                                          margin-top="6.885mm">
                                    <fo:table-column column-width="80mm"/>
                                    <fo:table-column column-width="80mm"/>
                                    <fo:table-body>
                                        <fo:table-row>
                                            <fo:table-cell>
                                                <fo:block-container>
                                                    <fo:table border-collapse="separate" table-layout="fixed"
                                                              width="100%">
                                                        <fo:table-column column-width="30mm"/>
                                                        <fo:table-column column-width="48.85mm"/>
                                                        <fo:table-body>
                                                            <fo:table-row>
                                                                <fo:table-cell>
                                                                    <fo:block display-align="center" text-align="start"
                                                                              font-size="10pt" font-weight="bold"
                                                                              margin-top="0.76mm">
                                                                        {{ds-voucher-pdf-label-code}} &#160;
                                                                    </fo:block>
                                                                </fo:table-cell>
                                                                <fo:table-cell>
                                                                    <fo:block border-color="black" border-width="0.27mm"
                                                                              border-style="solid"
                                                                              display-align="center" text-align="start"
                                                                              font-size="12pt" font-weight="bold"
                                                                              padding-top="2.26mm"
                                                                              padding-bottom="1.971mm"
                                                                              padding-left="2.862mm"
                                                                              margin-left="-1.234mm">
                                                                        <xsl:value-of select="//voucherCode"/> &#160;
                                                                    </fo:block>
                                                                </fo:table-cell>
                                                            </fo:table-row>
                                                        </fo:table-body>
                                                    </fo:table>
                                                </fo:block-container>
                                                <fo:block-container>
                                                    <fo:table border-collapse="separate" table-layout="fixed"
                                                              width="100%" margin-top="6.045mm">
                                                        <fo:table-column column-width="30mm"/>
                                                        <fo:table-column column-width="48.85mm"/>
                                                        <fo:table-body>
                                                            <fo:table-row>
                                                                <fo:table-cell>
                                                                    <fo:block padding-top="1.26mm"
                                                                              display-align="center" text-align="start"
                                                                              font-size="10pt">
                                                                        <fo:block>
                                                                            {{ds-voucher-pdf-label-voucher-date}} &#160;
                                                                        </fo:block>
                                                                        <fo:block>
                                                                            {{ds-voucher-pdf-label-expiry-date}} &#160;
                                                                        </fo:block>
                                                                    </fo:block>
                                                                </fo:table-cell>
                                                                <fo:table-cell>
                                                                    <fo:block border-color="black" border-width="0.27mm"
                                                                              border-style="solid"
                                                                              padding-top="1.26mm"
                                                                              padding-bottom="1.971mm"
                                                                              padding-left="2.862mm"
                                                                              margin-left="-1.234mm"
                                                                              display-align="center" text-align="start"
                                                                              font-size="10pt">
                                                                        <fo:block>
                                                                            <xsl:value-of
                                                                                    select="//request/orderPosition/itemActivationDate"/>
                                                                            &#160;
                                                                        </fo:block>
                                                                        <fo:block>
                                                                            <xsl:value-of
                                                                                    select="//request/orderPosition/itemExpirationDate"/>
                                                                            &#160;
                                                                        </fo:block>
                                                                    </fo:block>
                                                                </fo:table-cell>
                                                            </fo:table-row>
                                                        </fo:table-body>
                                                    </fo:table>
                                                </fo:block-container>
                                            </fo:table-cell>
                                            <fo:table-cell>
                                                <fo:block-container margin-left="4.167mm" margin-top="-3.283mm">
                                                    <fo:block>
                                                        <xsl:variable name="qrCode" select="//qrCode"/>
                                                        <fo:external-graphic
                                                                src="data:image/png;base64,{$qrCode}"
                                                                height="54.957mm" width="54.958mm"
                                                                content-height="scale-to-fit"
                                                                scaling="non-uniform"/>
                                                    </fo:block>
                                                </fo:block-container>

                                            </fo:table-cell>
                                        </fo:table-row>
                                    </fo:table-body>
                                </fo:table>
                            </fo:block>
                        </fo:block-container>
                        <fo:block-container margin-left="0mm" margin-top="14mm" width="157mm">
                            <fo:block font-weight="bold" font-size="5pt" line-height="2.822mm" text-align="start">
                                {{ds-voucher-pdf-disclaimer-headline}} &#160;
                            </fo:block>
                            <fo:block font-size="5pt" line-height="2.822mm" text-align="start">
                                1. Diese Bedingungen regeln die Nutzung von Gutscheincodes der Volkswagen AG (VW bzw.
                                Aussteller), die zum Bezug von digitalen Produkten und Dienstleistungen des Ausstellers
                                (z.B. Car-Net (Security &#38; Service, Guide &#38; Inform) und We Connect Plus
                                Lizenzverlängerungen, We Upgrade, Mobiler Schlüssel) im We Connect Webshop sowie im
                                In-Car Shop (in Abhängigkeit von Fahrzeug und Baujahr) des Ausstellers für ein dafür
                                befähigtes Fahrzeug eingelöst werden können.
                            </fo:block>
                            <fo:block font-size="5pt" line-height="2.822mm" text-align="start">
                                2. Die Einzelheiten zu den für ein bestimmtes Fahrzeug verfügbaren digitalen Produkten
                                und Dienstleistungen ergeben sich aus dem Angebot im We Connect Webshop sowie im In-Car
                                Shop (in Abhängigkeit von Fahrzeug und Baujahr) und der Fahrzeugidentifikati-onsnummer.
                            </fo:block>
                            <fo:block font-size="5pt" line-height="2.822mm" text-align="start">
                                3. Die Gutscheincodes sind nur bei teilnehmenden Partnern der Volkswagen AG erhältlich
                                und sind weder personen-, produkt- noch fahrzeuggebunden. Gutscheincodes können weder
                                für den Erwerb weiterer Gutscheincodes noch bei anderen Konzerngesellschaf-ten der
                                Volkswagen AG oder Dritten eingelöst werden.
                            </fo:block>
                            <fo:block font-size="5pt" line-height="2.822mm" text-align="start">
                                4. Die Gutscheincodes können im We Connect Webshop sowie im In-Car Shop (in Abhängigkeit
                                von Fahrzeug und Baujahr) beim Erwerb eines digitalen Produktes oder einer digitalen
                                Dienstleistung im Bezahlprozess eingesetzt werden. Ergänzend können weitere, im We
                                Connect Webshop sowie im In-Car Shop (in Abhängigkeit von Fahrzeug und Baujahr)
                                vorgesehene Zahlungsmethoden verwendet werden, soweit der Wert des Gutscheins in Form
                                des Gutscheincodes nicht ausreichend ist. Der Nominalwert des Gut-scheins in Form des
                                Gutscheincodes kann in mehreren Einzeltransaktionen eingelöst werden.
                            </fo:block>
                            <fo:block font-size="5pt" line-height="2.822mm" text-align="start">
                                5. Widerruft ein Verbraucher den Erwerb eines digitalen Produktes oder einer
                                Dienstleis-tung fristgerecht, geht der eingesetzte Betrag automatisch auf den Wert des
                                auf den Wert des Gutscheins in Form des Gutscheincodes zurück und ist bei dem nächsten
                                Er-werb von digitalen Produkten und/oder Dienstleistungen im We Connect Webshop sowie im
                                In-Car Shop (in Abhängigkeit von Fahrzeug und Baujahr) nutzbar.
                            </fo:block>
                            <fo:block font-size="5pt" line-height="2.822mm" text-align="start">
                                6. Gutscheine in Form des Gutscheincodes sind bis zum Ende des fünften Jahres nach dem
                                Erwerb einlösbar und verfallen nach diesem Zeitpunkt ersatzlos. Der Wert des Gutscheins
                                in Form des Gutscheincodes kann nicht wieder aufgeladen werden. Eine Rückgabe des
                                Gutscheins in Form des Gutscheincodes oder eine Barauszahlung des Gutscheins in Form des
                                Gutscheincodes ist ausgeschlossen.
                            </fo:block>
                            <fo:block font-size="5pt" line-height="2.822mm" text-align="start">
                                7. Der Aussteller übernimmt keine Haftung für Verlust, Diebstahl, Zerstörung oder
                                anderweitig unbefugte Verwendung des Gutscheincodes.
                            </fo:block>
                            <fo:block font-size="5pt" line-height="2.822mm" text-align="start">
                                8. Diese Nutzungsbedingungen unterliegen dem Recht der Bundesrepublik Deutschland.
                                Gerichtsstand ist, soweit gesetzlich zulässig, Wolfsburg.
                            </fo:block>
                            <fo:block font-size="5pt" line-height="2.822mm" text-align="start">
                                9. Der Aussteller ist zur Teilnahme an einem Streitbeilegungsverfahren vor einer
                                Verbraucherschlichtungsstelle weder bereit noch verpflichtet.
                            </fo:block>
                            <fo:block font-size="5pt" line-height="2.822mm" text-align="start">
                                10. Bei Fragen und Problemen im Zusammenhang mit einem Gutschein in Form des
                                Gut-scheincodes wenden Sie sich bitte an den teilnehmenden Partner der Volkswagen AG,
                                bei dem der Gutschein in Form des Gutscheincodes erworben wurde.
                            </fo:block>
                            <fo:block font-size="5pt" line-height="2.822mm" text-align="start">
                                Stand: 03.04.2020
                            </fo:block>
                        </fo:block-container>
                    </fo:block-container>
                </fo:flow>
            </fo:page-sequence>

        </fo:root>
    </xsl:template>

</xsl:stylesheet>