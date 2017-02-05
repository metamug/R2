<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:fn="http://www.w3.org/2005/xpath-functions"
                xmlns:mtg="http://xml.metamug.net/resource/1.0">

    <xsl:template match="mtg:Resource">
        <html>
            <head>
                <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
                      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
            </head>
            <body>
                <div class="container" style="padding-top:25px">
                    <div class="panel-body">
                        <span id="resName" style="color:darkgrey;font-weight:bold;margin-right:5px">
                            ABC
                        </span>
                        <span id="resVersion" class="badge" style="margin-right:5px;">
                            <xsl:value-of select="@v"/>
                        </span>
                        <xsl:choose>
                            <xsl:when test="contains(@auth,'true')">
                                <span class="glyphicon glyphicon-lock">
                                </span>
                            </xsl:when>
                        </xsl:choose>
                        <span id="resUri" style="font-weight:bold;" class="text text-success pull-right">
                        </span>
                        <div>
                            <xsl:choose>
                                <xsl:when test='string-length(mtg:Desc) &gt; 0'>
                                    <xsl:value-of select="mtg:Desc"/>
                                </xsl:when>
                                <xsl:otherwise>Not Given
                                </xsl:otherwise>
                            </xsl:choose>
                            <xsl:choose>
                                <xsl:when test='@parent'>
                                    <span class="text text-info pull-right" style="font-weight:bold;">Parent:
                                        <span id="resParent" style="color:darkgrey;">
                                            <xsl:value-of select="@parent" />
                                        </span>
                                    </span>
                                </xsl:when>
                            </xsl:choose>
                        </div>
                    </div>

                    <xsl:for-each select="mtg:Request">
                        <div class="col-md-12" style="padding-top:10px">
                            <xsl:choose>
                                <xsl:when test="contains(@item,'true')" >
                                    <div class="panel panel-success">

                                        <div class="panel-heading">
                                            <span style="font-weight:bold">ITEM REQUEST</span>
                                            <span class="pull-right" style="font-size:14px">
                                                <div class="badge" style="margin-right:10px;">
                                                    <xsl:choose>
                                                        <xsl:when test='string-length(@status) &gt; 0'>
                                                            <xsl:value-of select="@status"/>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <div>200</div>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                </div>
                                                <xsl:choose>
                                                    <xsl:when test="contains(@method,'GET')">
                                                        <div class="label label-success" style="font-size:14px">
                                                            GET
                                                        </div>
                                                    </xsl:when>
                                                    <xsl:when test="contains(@method,'POST')">
                                                        <div class="label label-info" style="font-size:14px">
                                                            POST
                                                        </div>
                                                    </xsl:when>
                                                    <xsl:when test="contains(@method,'PUT')">
                                                        <div class="label label-warning" style="font-size:14px">
                                                            PUT
                                                        </div>
                                                    </xsl:when>
                                                    <xsl:when test="contains(@method,'DELETE')">
                                                        <div class="label label-danger" style="font-size:14px">
                                                            DELETE
                                                        </div>
                                                    </xsl:when>
                                                </xsl:choose>
                                            </span>
                                        </div>
                                        <div class="panel-body">
                                            <div>
                                                <div style="color:darkgrey;font-weight:bold;">
                                                    Description
                                                </div>
                                                <xsl:choose>
                                                    <xsl:when test='string-length(mtg:Desc) &gt; 0'>
                                                        <xsl:value-of select="mtg:Desc"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <div>-</div>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </div>
                                            <div style="padding-top:12px">
                                                <div style="color:darkgrey;font-weight:bold;">
                                                    Param(s)
                                                </div>
                                                <div class="paramList" style="padding-top:8px;font-size:16px;">
                                                    <xsl:choose>
                                                        <xsl:when test="mtg:Param">
                                                            <xsl:for-each select="mtg:Param">
                                                                <span class="label label-primary" style="margin-right:5px">
                                                                    <xsl:value-of select="@name"/>
                                                                </span>
                                                                <span class="label label-primary" style="margin-right:5px">
                                                                    <xsl:value-of select="@min"/>
                                                                </span>
                                                            </xsl:for-each>
                                                        </xsl:when>
                                                    </xsl:choose>

                                                    <xsl:for-each select="mtg:Sql">
                                                        <xsl:choose>
                                                            <xsl:when test="@when">
                                                                <xsl:analyze-string select="@when" regex="$((\w+)(.\w)*?\s)">
                                                                    <xsl:matching-substring>
                                                                        <span class="label label-primary" style="margin-right:5px">
                                                                            <xsl:value-of select="regex-group(1)"/>
                                                                        </span>
                                                                    </xsl:matching-substring>
                                                                    <xsl:non-matching-substring>
                                                                    </xsl:non-matching-substring>
                                                                </xsl:analyze-string>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </xsl:for-each>
                                                    <xsl:for-each select="mtg:Query">
                                                        <xsl:choose>
                                                            <xsl:when test="@when">
                                                                <xsl:analyze-string select="@when" regex="$((\w+)(.\w)*?\s)">
                                                                    <xsl:matching-substring>
                                                                        <span class="label label-primary" style="margin-right:5px">
                                                                            <xsl:value-of select="regex-group(1)"/>
                                                                        </span>
                                                                    </xsl:matching-substring>
                                                                    <xsl:non-matching-substring>
                                                                    </xsl:non-matching-substring>
                                                                </xsl:analyze-string>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </xsl:for-each>
                                                    <xsl:for-each select="mtg:Update">
                                                        <xsl:choose>
                                                            <xsl:when test="@when">
                                                                <xsl:analyze-string select="@when" regex="$((\w+)(.\w)*?\s)">
                                                                    <xsl:matching-substring>
                                                                        <span class="label label-primary" style="margin-right:5px">
                                                                            <xsl:value-of select="regex-group(1)"/>
                                                                        </span>
                                                                    </xsl:matching-substring>
                                                                    <xsl:non-matching-substring>
                                                                    </xsl:non-matching-substring>
                                                                </xsl:analyze-string>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </xsl:for-each>

                                                    <xsl:analyze-string select="." regex="$((\w+)(.\w)*?\s)">
                                                        <xsl:matching-substring>
                                                            <span>
                                                                <xsl:value-of select="regex-group(1)"/>
                                                            </span>
                                                        </xsl:matching-substring>
                                                        <xsl:non-matching-substring>
                                                        </xsl:non-matching-substring>
                                                    </xsl:analyze-string>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </xsl:when>
                                <xsl:otherwise>
                                    <div class="panel panel-info">
                                        <div class="panel-heading">
                                            <span style="font-weight:bold">COLLECTION REQUEST</span>
                                            <span class="pull-right" style="font-size:14px">
                                                <div class="badge" style="margin-right:10px;">
                                                    <xsl:choose>
                                                        <xsl:when test='string-length(@status) &gt; 0'>
                                                            <xsl:value-of select="@status"/>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <div>200</div>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                </div>
                                                <xsl:choose>
                                                    <xsl:when test="contains(@method,'GET')">
                                                        <div class="label label-success" style="font-size:14px">
                                                            GET
                                                        </div>
                                                    </xsl:when>
                                                    <xsl:when test="contains(@method,'POST')">
                                                        <div class="label label-info" style="font-size:14px">
                                                            POST
                                                        </div>
                                                    </xsl:when>
                                                    <xsl:when test="contains(@method,'PUT')">
                                                        <div class="label label-warning" style="font-size:14px">
                                                            PUT
                                                        </div>
                                                    </xsl:when>
                                                    <xsl:when test="contains(@method,'DELETE')">
                                                        <div class="label label-danger" style="font-size:14px">
                                                            DELETE
                                                        </div>
                                                    </xsl:when>
                                                </xsl:choose>
                                            </span>
                                        </div>
                                        <div class="panel-body">
                                            <div>
                                                <div style="color:darkgrey;font-weight:bold;">
                                                    Description
                                                </div>
                                                <xsl:choose>
                                                    <xsl:when test='string-length(mtg:Desc) &gt; 0'>
                                                        <xsl:value-of select="mtg:Desc"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <div>-</div>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </div>
                                            <div style="padding-top:12px">
                                                <div style="color:darkgrey;font-weight:bold;">
                                                    Parameter(s)
                                                </div>
                                                <div class="paramList" style="padding-top:8px;font-size:16px;">
                                                    <xsl:choose>
                                                        <xsl:when test="mtg:Param">
                                                            <table class="table table-hover">
                                                                <thead>
                                                                    <tr>
                                                                        <th>
                                                                        </th>
                                                                        <th>
                                                                            Type
                                                                        </th>
                                                                        <th>
                                                                            Min
                                                                        </th>
                                                                        <th>
                                                                            Max
                                                                        </th>
                                                                        <th>
                                                                            Min Length
                                                                        </th>
                                                                        <th>
                                                                            Max Length
                                                                        </th>
                                                                        <th>
                                                                            Value
                                                                        </th>
                                                                        <th>
                                                                            Required
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                <xsl:for-each select="mtg:Param">
                                                                    <tbody>
                                                                        <tr>
                                                                            <td>
                                                                                <span>
                                                                                    <xsl:value-of select="@name"/>
                                                                                </span>
                                                                            </td>
                                                                            <td>
                                                                                <span>
                                                                                    <xsl:value-of select="@type"/>
                                                                                </span>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:if test=" @type='number'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@min"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                                <xsl:if test=" @type='text'">
                                                                                    <span class="glyphicon glyphicon-minus"></span>
                                                                                </xsl:if>
                                                                            </td>

                                                                            <td>
                                                                                <xsl:if test=" @type='number'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@max"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                                <xsl:if test=" @type='text'">
                                                                                    <span class="glyphicon glyphicon-minus"></span>
                                                                                </xsl:if>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:if test=" @type='text'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@minlength"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                                <xsl:if test=" @type='number'">
                                                                                    <span class="glyphicon glyphicon-minus"></span>
                                                                                </xsl:if>
                                                                            </td>

                                                                            <td>
                                                                                <xsl:if test=" @type='text'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@maxlength"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                                <xsl:if test=" @type='number'">
                                                                                    <span class="glyphicon glyphicon-minus"></span>
                                                                                </xsl:if>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:choose>
                                                                                    <xsl:when test="@value">
                                                                                        <xsl:if test=" @value=''">
                                                                                            <span class="glyphicon glyphicon-minus"></span>
                                                                                        </xsl:if>
                                                                                        <xsl:if test=" @value !=''">
                                                                                            <span>
                                                                                                <xsl:value-of select="@value"/>
                                                                                            </span>
                                                                                        </xsl:if>
                                                                                    </xsl:when>
                                                                                </xsl:choose>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:choose>
                                                                                    <xsl:when test="@required">
                                                                                        <xsl:if test=" @required='true'">
                                                                                            <span class="glyphicon glyphicon-ok"></span>
                                                                                        </xsl:if>
                                                                                        <xsl:if test=" @required='false'">
                                                                                            <span class="glyphicon glyphicon-remove"></span>
                                                                                        </xsl:if>
                                                                                    </xsl:when>
                                                                                    <!--<xsl:value-of select="@required"/>-->
                                                                                </xsl:choose>
                                                                            </td>
                                                                        </tr>
                                                                    </tbody>
                                                                </xsl:for-each>
                                                            </table>
                                                        </xsl:when>
                                                    </xsl:choose>

                                                    <xsl:for-each select="mtg:Sql">
                                                        <xsl:choose>
                                                            <xsl:when test="@when">
                                                                <xsl:analyze-string select="@when" regex="$((\w+)(.\w)*?\s)">
                                                                    <xsl:matching-substring>
                                                                        <span class="label label-primary" style="margin-right:5px">
                                                                            <xsl:value-of select="regex-group(1)"/>
                                                                        </span>
                                                                    </xsl:matching-substring>
                                                                    <xsl:non-matching-substring>
                                                                    </xsl:non-matching-substring>
                                                                </xsl:analyze-string>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </xsl:for-each>
                                                    <xsl:for-each select="mtg:Query">
                                                        <xsl:choose>
                                                            <xsl:when test="@when">
                                                                <xsl:analyze-string select="@when" regex="$((\w+)(.\w)*?\s)">
                                                                    <xsl:matching-substring>
                                                                        <span class="label label-primary" style="margin-right:5px">
                                                                            <xsl:value-of select="regex-group(1)"/>
                                                                        </span>
                                                                    </xsl:matching-substring>
                                                                    <xsl:non-matching-substring>
                                                                    </xsl:non-matching-substring>
                                                                </xsl:analyze-string>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </xsl:for-each>
                                                    <xsl:for-each select="mtg:Update">
                                                        <xsl:choose>
                                                            <xsl:when test="@when">
                                                                <xsl:analyze-string select="@when" regex="$((\w+)(.\w)*?\s)">
                                                                    <xsl:matching-substring>
                                                                        <span class="label label-primary" style="margin-right:5px">
                                                                            <xsl:value-of select="regex-group(1)"/>
                                                                        </span>
                                                                    </xsl:matching-substring>
                                                                    <xsl:non-matching-substring>
                                                                    </xsl:non-matching-substring>
                                                                </xsl:analyze-string>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </xsl:for-each>

                                                    <xsl:analyze-string select="." regex="$((\w+)(.\w)*?\s)">
                                                        <xsl:matching-substring>
                                                            <span class="label label-primary" style="margin-right:5px">
                                                                <xsl:value-of select="regex-group(1)"/>
                                                            </span>
                                                        </xsl:matching-substring>
                                                        <xsl:non-matching-substring>
                                                        </xsl:non-matching-substring>
                                                    </xsl:analyze-string>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </xsl:otherwise>
                            </xsl:choose>

                        </div>
                    </xsl:for-each>

                </div>
                <script>
                    var url = window.location.href;
                    var resName = url.substr(url.lastIndexOf('/')+1).toUpperCase();
                    var resNameDiv = document.getElementById('resName');
                    resName = resName.slice(0,-5);
                    resNameDiv.innerHTML = resName;
                    document.title = resName.toLowerCase();

                    var uriDiv = document.getElementById('resUri');
                    var version = document.getElementById('resVersion').innerHTML;
                    var uri = "/v"+version+"/"+resName;
                    uriDiv.innerHTML = uri;

                    var parentSpan = document.getElementById('resParent');
                    if(parentSpan != null){
                    parentSpan.innerHTML = parentSpan.innerHTML.toUpperCase();
                    }
                    /*
                    var paramListClasses = document.getElementsByClassName("paramList");
                    for (var i = 0; i &lt; paramListClasses.length; i++) {
                    //console.log(paramListClasses[i]);
                    var unique = [];
                    var paramList = paramListClasses[i].childNodes;
                    for(var j = 0; j &lt; paramList.length; j++){
                    var param = paramList[j];
                    unique.push(param.innerHTML);
                    unique = eliminateDuplicates(unique);
                    }
                    //console.log("unique: "+unique);
                    var l = paramList.length;
                    for(var j=l-1; j &gt;= 0; j--){
                    paramList[j].parentNode.removeChild(paramList[j]);
                    }
                    for(var j=0; j &lt; unique.length; j++){
                    var span = document.createElement('span');
                    span.classList.add('label','label-primary');
                    span.innerHTML = unique[j];
                    span.style['margin-right'] = '5px';
                    paramListClasses[i].appendChild(span);
                    }
                    }
                    function eliminateDuplicates(arr) {
                    var i, len=arr.length, out=[], obj={};
                    for (i=0;i &lt; len;i++) {
                    obj[arr[i]]=0;
                    }
                    for (i in obj) {
                    out.push(i);
                    }
                    return out;
                    }*/
                </script>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
