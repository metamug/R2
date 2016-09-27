<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:fn="http://www.w3.org/2005/xpath-functions">

    <xsl:template match="Resource">
        <html>
            <head>
                <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
                      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
            </head>
            <body>
                <div class="container" style="padding-top:25px">
                    <table class="table table-striped">
                        <tbody>
                            <tr>
                                <th scope="row" style="width:200px">
                                    Resource Name
                                </th>
                                <td id="resName">
                                    <xsl:value-of select="@table"/>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"  style="width:200px">
                                    Resource Description
                                </th>
                                <td>
                                    <xsl:choose>
                                        <xsl:when test='string-length(Desc) &gt; 0'>
                                            <xsl:value-of select="Desc"/>
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

                    <xsl:for-each select="Request">
                        <br></br>
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <b>REQUEST</b>
                            </div>
                            <div class="panel-body">
                                <div>
                                    <xsl:choose>
                                        <xsl:when test='string-length(Desc) &gt; 0'>
                                            <b><xsl:value-of select="Desc"/></b>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            No description
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </div>
                                <br></br>
                                <table class="table borderless">
                                    <tbody>
                                        <tr>
                                            <td class="col-md-6">Method</td>
                                            <td class="col-md-6"><b><xsl:value-of select="@method"/></b></td>
                                        </tr>
                                        <tr>
                                            <td class="col-md-6">Item</td>
                                            <td class="col-md-6">
                                                <xsl:choose>
                                                    <xsl:when test='string-length(@item) &gt; 0'>
                                                        <b><xsl:value-of select="@item"/></b>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <b>false</b>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="col-md-6">Status</td>
                                            <td class="col-md-6">
                                                <xsl:choose>
                                                    <xsl:when test='string-length(@status) &gt; 0'>
                                                        <b><xsl:value-of select="@status"/></b>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <b>not specified</b>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="col-md-6">Param/s</td>
                                            <td id="paramList" class="col-md-6">
                                                <xsl:choose>
                                                    <xsl:when test="Param">
                                                        <xsl:for-each select="Param">
                                                            <xsl:value-of select="@name"/>,
                                                        </xsl:for-each>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <b>not specified</b>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </xsl:for-each>

                    <!--
                    <table class="table table-striped">
                        <thead class="thead-inverse">
                            <tr>
                                <th>Type</th>
                                <th>Description</th>
                                <th>Parameter</th>
                                <th>Method</th>
                                <th>Condition</th>
                                <th>Output</th>
                            </tr>
                        </thead>
                        <tbody>

            <xsl:for-each select="Request">
                <xsl:for-each select="Sql">
                    <tr>
                    TD for query-type
                    <td>
                        <xsl:choose>
                            <xsl:when test="contains(@type,'query')">
                                Query
                            </xsl:when>
                            <xsl:otherwise>
                                Update
                            </xsl:otherwise>
                        </xsl:choose>
                    </td>
                    TD for description
                    <td>
                        <xsl:value-of select="../Desc"/>
                    </td>
                    TD for parameter list
                    <td>
                        <xsl:analyze-string select="." regex="@(\w+)">
                            <xsl:matching-substring>
                                <xsl:value-of select="concat(regex-group(1),', ')"/>
                            </xsl:matching-substring>
                            <xsl:non-matching-substring>
                            </xsl:non-matching-substring>
                        </xsl:analyze-string>
                    </td>
                   TD for request method
                                        <td>
                                            <xsl:value-of select="../@method"/>
                                        </td>
                                        TD for condition
                                        <td>
                                            <xsl:value-of select="replace(@when,'@','')"/>
                                        </td>
                                        TD for output format
                                        <td>
                                            <xsl:choose>
                                                <xsl:when test='string-length(@Out) &gt; 0'>
                                                    <xsl:value-of select="@Out"/>
                                                </xsl:when>
                                                <xsl:otherwise>json
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </td>
                                    </tr>
                                </xsl:for-each>
                            </xsl:for-each>
                        </tbody>
                    </table>-->
                </div>
                <script>
                    var url = window.location.href;
                    var resName = url.substr(url.lastIndexOf('/')+1);
                    var resNameDiv = document.getElementById('resName');
                    resName = resName.slice(0,-5);
                    resNameDiv.innerHTML = resName;
                    document.title = resName;
                    var paramListDiv = document.getElementById('paramList');
                    var paramList = paramListDiv.innerHTML.trim();
                    paramListDiv.innerHTML = (paramList.slice(0,-1)).bold();
                </script>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
