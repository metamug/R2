<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="Resource">
        <html>
            <head>
                <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css" integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd" crossorigin="anonymous"/>
                <title>
                    <xsl:value-of select="@table"/>
                </title>
            </head>
            <body>
                <div class="container" style="padding-top:25px">
                    <table class="table table-striped">
                        <tbody>
                            <tr>
                                <th scope="row" style="width:200px">
                                    Resource Name
                                </th>
                                <td>
                                    <xsl:value-of select="@table"/>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"  style="width:200px">
                                    Resource Description
                                </th>
                                <td>
                                    <xsl:choose>
                                        <xsl:when test='string-length(desc)>0'>
                                            <xsl:value-of select="desc"/>
                                        </xsl:when>
                                        <xsl:otherwise>Not Given
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row" style="width:200px">
                                    API Version:
                                </th>
                                <td>
                                    <xsl:value-of select="@version"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <table class="table table-striped">
                        <thead class="thead-inverse">
                            <tr>
                                <th>Type</th>
                                <th>Description</th>
                                <th>Method</th>
                                <th>Output</th>
                            </tr>
                        </thead>
                        <tbody>

                            <xsl:for-each select="Request">
                                <tr>
                                    <td>Request</td>
                                    <td>
                                        <xsl:choose>
                                            <xsl:when test='string-length(desc)>0'>
                                                <xsl:value-of select="desc"/>
                                            </xsl:when>
                                            <xsl:otherwise>Not Given
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </td>
                                    <td>
                                        <xsl:value-of select="@method"/>
                                    </td>
                                    <td>
                                        <xsl:choose>
                                            <xsl:when test='string-length(@out)>0'>
                                                <xsl:value-of select="@out"/>
                                            </xsl:when>
                                            <xsl:otherwise>json
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </td>
                                </tr>
                            </xsl:for-each>
                        </tbody>
                    </table>
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
