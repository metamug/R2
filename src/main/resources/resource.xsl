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
                        <xsl:choose>
                            <xsl:when test="contains(@auth,'true')">
                                <span class="glyphicon glyphicon-lock">
                                </span>
                            </xsl:when>
                        </xsl:choose>
                        <span id="resName" style="color:darkgrey;font-weight:bold;margin-left:5px;margin-right:5px">
                            ABC
                        </span>
                        <span id="resVersion" class="badge">
                            <xsl:value-of select="@v"/>
                        </span>
                        <span id="resGroup" style="padding:5px;color:#777777;">
                            <xsl:choose>
                                <xsl:when test='not(matches(@group,"[^ ]+"))'>
                                    <span class="glyphicons glyphicons-unlock"></span>
                                </xsl:when>
                                <xsl:when test="not(empty(@group))">
                                    <span class="glyphicon glyphicon-lock">
                                    </span>
                                </xsl:when>
                                <xsl:otherwise>
                                    
                                </xsl:otherwise>
                            </xsl:choose>
                        </span>
                        <span id="resUri" style="font-weight:bold;" class="text text-success pull-right">
                        </span>
                        <div style="margin-left: 5px;font-size: small;">
                            <xsl:choose>
                                <xsl:when test='string-length(mtg:Desc) &gt; 0'>
                                    <xsl:value-of select="mtg:Desc"/>
                                </xsl:when>
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
                        <xsl:variable name="UpdateRequires" select="mtg:Update/@requires"/>
                        <xsl:variable name="QueryRequires" select="mtg:Query/@requires"/>
                        <xsl:variable name="ExecuteRequires" select="mtg:Execute/@requires"/>
                        <xsl:variable name="ParamName" select="mtg:Param/@name"/>
                        <xsl:variable name="JointVarUR" select="string-join( ($UpdateRequires), ',')"/>
                        <xsl:variable name="JointVarQR" select="string-join( ($QueryRequires), ',')"/>
                        <xsl:variable name="JointVarER" select="string-join( ($ExecuteRequires), ',')"/>
                        <xsl:variable name="JointVarPN" select="string-join( ($ParamName), ',')"/>
                        <xsl:variable name="TokenizedUpdateRequires" select="tokenize($JointVarUR,'[,]')"/>
                        <xsl:variable name="TokenizedQueryRequires" select="tokenize($JointVarQR,'[,]')"/>
                        <xsl:variable name="TokenizedExecuteRequires" select="tokenize($JointVarER,'[,]')"/>
                        <xsl:variable name="TokenizedParamName" select="tokenize($JointVarPN,'[,]')"/>
                        <xsl:variable name="AllValues" select="(($TokenizedUpdateRequires,$TokenizedQueryRequires,$TokenizedExecuteRequires),'')"/>
                        <xsl:variable name="DistAllValues" select="distinct-values($AllValues)"/>
                        <xsl:variable name="DistinctAllValues" select="string-join($DistAllValues,',')"/>
                        <xsl:variable name="TokDistinctAllValues" select="tokenize($DistinctAllValues,',')"/>
                        
                        <div class="col-md-12" style="padding-top:10px">
                            <xsl:choose>
                                <xsl:when test="contains(@item,'true') and not(contains(@method,'DELETE'))" >
                                    <div class="panel panel-success">
                                        <div class="panel-heading">
                                            <span class="ReqTypeItem" style="font-weight:bold">ITEM REQUEST</span>
                                            
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
                                                        <div class="label label-success" style="font-size:14px;background-color: #5cb85c;">
                                                            GET
                                                        </div>
                                                    </xsl:when>
                                                    <xsl:when test="contains(@method,'POST')">
                                                        <div class="label label-info" style="font-size:14px;background-color: #5cb85c;">
                                                            POST
                                                        </div>
                                                    </xsl:when>
                                                    <xsl:when test="contains(@method,'PUT')">
                                                        <div class="label label-warning" style="font-size:14px;background-color: #5cb85c;">
                                                            PUT
                                                        </div>
                                                    </xsl:when>
                                                </xsl:choose>
                                            </span>
                                        </div>
                                        <div class="panel-body">
                                            
                                            <xsl:choose>
                                                <xsl:when test='string-length(mtg:Desc) &gt; 0'>
                                                    <div style="padding-bottom:12px">
                                                        <div style="color:darkgrey;font-weight:bold;">
                                                            Description
                                                        </div>
                                                        <xsl:value-of select="mtg:Desc"/>
                                                    </div>    
                                                </xsl:when>
                                            </xsl:choose>       
                                            <div style="padding-bottom:12px">
                                                <div style="color:darkgrey;font-weight:bold;">
                                                    Parameters
                                                </div>
                                                <div class="sqlVariables" style="padding-top:6px">
                                                                                                       
                                                    <xsl:for-each select="mtg:Query">
                                                        <xsl:choose>
                                                            <xsl:when test="@when">
                                                                <xsl:analyze-string select="@when" regex="\$([\w.]+)">
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
                                                                <xsl:analyze-string select="@when" regex="\$([\w.]+)">
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
                                                    <xsl:analyze-string select="." regex="\$([\w.]+)">
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
                                            
                                            <xsl:choose>
                                                <xsl:when test="mtg:Param or mtg:Update/@requires or mtg:Query/@requires or mtg:Execute/@requires">  
                                                    <div>
                                                        <div style="color:darkgrey;font-weight:bold;">
                                                            Parameter(s) criteria
                                                        </div>
                                                        <div class="paramList" style="font-size:16px;">

                                                            <table class="table table-hover">
                                                                <thead>
                                                                    <tr>
                                                                        <th>
                                                                            Parameter
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
                                                                            Nullable
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                <xsl:for-each select="mtg:Param">
                                                                    <tbody>
                                                                        <tr>
                                                                            <td>
                                                                                <xsl:choose>
                                                                                    <xsl:when test=" @name">
                                                                                        <xsl:if test=" @name ">
                                                                                            <span>
                                                                                                <xsl:value-of select="@name"/>
                                                                                            </span>
                                                                                        </xsl:if>
                                                                                    </xsl:when>
                                                                                    <xsl:otherwise>
                                                                                        <span>
                                                                                            <xsl:value-of select="@name"/>
                                                                                        </span>
                                                                                    </xsl:otherwise>
                                                                                </xsl:choose>
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
                                                                            </td>

                                                                            <td>
                                                                                <xsl:if test=" @type='number'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@max"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:if test=" @type='text'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@minlength"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                            </td>

                                                                            <td>
                                                                                <xsl:if test=" @type='text'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@maxlength"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:choose>
                                                                                    <xsl:when test="@value">
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
                                                                                            <span class="glyphicon glyphicon-remove"></span>
                                                                                        </xsl:if>
                                                                                        <xsl:if test=" @required='false'">
                                                                                            <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                        </xsl:if>
                                                                                    </xsl:when>
                                                                                    <xsl:otherwise>
                                                                                        <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                    </xsl:otherwise>
                                                                                    <!--<xsl:value-of select="@required"/>-->
                                                                                </xsl:choose>
                                                                            </td>
                                                                        </tr>
                                                                    </tbody>
                                                                </xsl:for-each>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <xsl:choose>
                                                        <xsl:when test="mtg:Update/@requires or mtg:Query/@requires or mtg:Execute/@requires">
                                                            <div>
                                                                <div style="color:darkgrey;font-weight:bold;">
                                                                    Parameter(s) criteria (Compulsory)
                                                                </div>
                                                                <div class="paramList" style="font-size:16px;">

                                                                    <table class="table table-hover">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>
                                                                                    Parameter
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
                                                                                    Nullable
                                                                                </th>
                                                                            </tr>
                                                                        </thead>
                                                                        <xsl:for-each select="$TokDistinctAllValues">
                                                                            <xsl:variable name="i" select="position()" />
                                                                            <xsl:if test="$i &lt; last()">
                                                                            <tbody>
                                                                                <tr>
                                                                                    <td colspan="7">
                                                                                        <span>
                                                                                            <xsl:value-of select="$TokDistinctAllValues[$i]"/>
                                                                                            <sup>
                                                                                                <span style="color:red;">*</span>
                                                                                            </sup>
                                                                                        </span>
                                                                                    </td>
                                                                                    <td>
                                                                                        <span class="glyphicon glyphicon-remove" style="color:red;"></span>
                                                                                    </td>
                                                                                </tr>                     
                                                                            </tbody>
                                                                            </xsl:if>
                                                                        </xsl:for-each>
                                                        
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </xsl:when>
                                                    </xsl:choose>           
                                                </xsl:when>
                                            </xsl:choose>
                                        </div>
                                    </div>
                                </xsl:when>
                                <xsl:when test="contains(@method,'DELETE')">
                                    <div class="panel panel-success" style="border-color: #e0bab9;">
                                        <div class="panel-heading" style="color: #d9534f;background-color: #fbd8d7;border-color: #e0bab9;">
                                            <span class="ReqTypeItem" style="font-weight:bold;color: #d9534f;background-color: #fdd5d4;border-color: #e0bab9;">ITEM REQUEST</span>
                                            
                                            <span class="pull-right" style="font-size:14px">
                                                <div class="badge" style="margin-right:10px;color: #fbd8d7;background-color: #c14a46;">
                                                    <xsl:choose>
                                                        <xsl:when test='string-length(@status) &gt; 0'>
                                                            <xsl:value-of select="@status"/>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <div>410</div>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                </div>
                                                <xsl:choose>
                                                    <xsl:when test="contains(@method,'DELETE')">
                                                        <div class="label label-danger" style="font-size:14px;background-color: #d9534f;">
                                                            DELETE
                                                        </div>
                                                    </xsl:when>
                                                </xsl:choose>
                                            </span>
                                        </div>
                                        <div class="panel-body">
                                            
                                            <xsl:choose>
                                                <xsl:when test='string-length(mtg:Desc) &gt; 0'>
                                                    <div style="padding-bottom:12px">
                                                        <div style="color:darkgrey;font-weight:bold;">
                                                            Description
                                                        </div>
                                                        <xsl:value-of select="mtg:Desc"/>
                                                    </div>    
                                                </xsl:when>
                                            </xsl:choose>       
                                            <div style="padding-bottom:12px">
                                                <div style="color:darkgrey;font-weight:bold;">
                                                    Parameters
                                                </div>
                                                <div class="sqlVariables" style="padding-top:6px">
                                                                                                       
                                                    <xsl:for-each select="mtg:Query">
                                                        <xsl:choose>
                                                            <xsl:when test="@when">
                                                                <xsl:analyze-string select="@when" regex="\$([\w.]+)">
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
                                                                <xsl:analyze-string select="@when" regex="\$([\w.]+)">
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
                                                    <xsl:analyze-string select="." regex="\$([\w.]+)">
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
                                            
                                            <xsl:choose>
                                                <xsl:when test="mtg:Param or mtg:Update/@requires or mtg:Query/@requires or mtg:Execute/@requires">  
                                                    <div>
                                                        <div style="color:darkgrey;font-weight:bold;">
                                                            Parameter(s) criterias
                                                        </div>
                                                        <div class="paramList" style="font-size:16px;">

                                                            <table class="table table-hover">
                                                                <thead>
                                                                    <tr>
                                                                        <th>
                                                                            Parameter
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
                                                                            Nullable
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                <xsl:for-each select="mtg:Param">
                                                                    <tbody>
                                                                        <tr>
                                                                            <td>
                                                                                <xsl:choose>
                                                                                    <xsl:when test=" @name">
                                                                                        <xsl:if test=" @name ">
                                                                                            <span>
                                                                                                <xsl:value-of select="@name"/>
                                                                                            </span>
                                                                                        </xsl:if>
                                                                                    </xsl:when>
                                                                                    <xsl:otherwise>
                                                                                        <span>
                                                                                            <xsl:value-of select="@name"/>
                                                                                        </span>
                                                                                    </xsl:otherwise>
                                                                                </xsl:choose>
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
                                                                            </td>

                                                                            <td>
                                                                                <xsl:if test=" @type='number'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@max"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:if test=" @type='text'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@minlength"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                            </td>

                                                                            <td>
                                                                                <xsl:if test=" @type='text'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@maxlength"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:choose>
                                                                                    <xsl:when test="@value">
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
                                                                                            <span class="glyphicon glyphicon-remove"></span>
                                                                                        </xsl:if>
                                                                                        <xsl:if test=" @required='false'">
                                                                                            <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                        </xsl:if>
                                                                                    </xsl:when>
                                                                                    <xsl:otherwise>
                                                                                        <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                    </xsl:otherwise>
                                                                                    <!--<xsl:value-of select="@required"/>-->
                                                                                </xsl:choose>
                                                                            </td>
                                                                        </tr>
                                                                    </tbody>
                                                                </xsl:for-each>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <xsl:choose>
                                                        <xsl:when test="mtg:Update/@requires or mtg:Query/@requires or mtg:Execute/@requires">
                                                            <div>
                                                                <div style="color:darkgrey;font-weight:bold;">
                                                                    Parameter(s) criteria (Compulsory)
                                                                </div>
                                                                <div class="paramList" style="font-size:16px;">

                                                                    <table class="table table-hover">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>
                                                                                    Parameter
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
                                                                                    Nullable
                                                                                </th>
                                                                            </tr>
                                                                        </thead>
                                                                        <xsl:for-each select="$TokDistinctAllValues">
                                                                            <xsl:variable name="i" select="position()" />
                                                                            <xsl:if test="$i &lt; last()">
                                                                            <tbody>
                                                                                <tr>
                                                                                    <td colspan="7">
                                                                                        <span>
                                                                                            <xsl:value-of select="$TokDistinctAllValues[$i]"/>
                                                                                            <sup>
                                                                                                <span style="color:red;">*</span>
                                                                                            </sup>
                                                                                        </span>
                                                                                    </td>
                                                                                    <td>
                                                                                        <span class="glyphicon glyphicon-remove" style="color:red;"></span>
                                                                                    </td>
                                                                                </tr>                     
                                                                            </tbody>
                                                                            </xsl:if>
                                                                        </xsl:for-each>
                                                        
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </xsl:when>
                                                    </xsl:choose> 
                                                </xsl:when>
                                            </xsl:choose>
                                        </div>
                                    </div>
                                </xsl:when>
                                <xsl:when test="not(@item) or contains(@item,'false')">
                                    <div class="panel panel-info">
                                        <div class="panel-heading">
                                            <span class="ReqTypeCollection" style="font-weight:bold">COLLECTION REQUEST</span>
                                            
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
                                                        <div class="label label-success" style="font-size:14px;background-color: #5bc0de;">
                                                            GET
                                                        </div>
                                                    </xsl:when>
                                                    <xsl:when test="contains(@method,'POST')">
                                                        <div class="label label-info" style="font-size:14px;background-color: #5bc0de;">
                                                            POST
                                                        </div>
                                                    </xsl:when>
                                                    <xsl:when test="contains(@method,'PUT')">
                                                        <div class="label label-warning" style="font-size:14px;background-color: #5bc0de;">
                                                            PUT
                                                        </div>
                                                    </xsl:when>
                                                    <xsl:when test="contains(@method,'DELETE')">
                                                        <div class="label label-danger" style="font-size:14px;background-color: #5bc0de;">
                                                            DELETE
                                                        </div>
                                                    </xsl:when>
                                                </xsl:choose>
                                            </span>
                                        </div>
                                        <div class="panel-body">
                                            
                                            <xsl:choose>
                                                <xsl:when test='string-length(mtg:Desc) &gt; 0'>
                                                    <div style="padding-bottom:12px">
                                                        <div style="color:darkgrey;font-weight:bold;">
                                                            Description
                                                        </div>
                                                        <xsl:value-of select="mtg:Desc"/>
                                                    </div>    
                                                </xsl:when>
                                            </xsl:choose>      
                                            <div style="padding-bottom:12px">
                                                <div style="color:darkgrey;font-weight:bold;">
                                                    Parameters
                                                </div>
                                                <div class="sqlVariables" style="padding-top:6px">
                                                                                                       
                                                    <xsl:for-each select="mtg:Query">
                                                        <xsl:choose>
                                                            <xsl:when test="@when">
                                                                <xsl:analyze-string select="@when" regex="\$([\w.]+)">
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
                                                                <xsl:analyze-string select="@when" regex="\$([\w.]+)">
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
                                                    <xsl:analyze-string select="." regex="\$([\w.]+)">
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
                                            
                                            
                                            <xsl:choose>
                                                <xsl:when test="mtg:Param or mtg:Update/@requires or mtg:Query/@requires or mtg:Execute/@requires">
                                                    <div>
                                                        <div style="color:darkgrey;font-weight:bold;">
                                                            Parameter(s) Criterias
                                                        </div>
                                                        <div class="paramList" style="font-size:16px;">                                                    
                                                            <table class="table table-hover">
                                                                <thead>
                                                                    <tr>
                                                                        <th>
                                                                            Parameter
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
                                                                            Nullable
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                
                                                                <xsl:for-each select="mtg:Param" >
                                                                    <tbody>
                                                                        <tr>
                                                                            <td>
                                                                                <xsl:choose>
                                                                                    <xsl:when test="@required or @name">
                                                                                        <xsl:if test=" @name ">
                                                                                            <span>
                                                                                                <xsl:value-of select="@name"/>
                                                                                            </span>
                                                                                        </xsl:if>
                                                                                    </xsl:when>
                                                                                    <xsl:otherwise>
                                                                                        <span>
                                                                                            <xsl:value-of select="@name"/>
                                                                                        </span>
                                                                                    </xsl:otherwise>
                                                                                </xsl:choose> 
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
                                                                            </td>

                                                                            <td>
                                                                                <xsl:if test=" @type='number'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@max"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:if test=" @type='text'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@minlength"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                            </td>

                                                                            <td>
                                                                                <xsl:if test=" @type='text'">
                                                                                    <span>
                                                                                        <xsl:value-of select="@maxlength"/>
                                                                                    </span>
                                                                                </xsl:if>
                                                                            </td>
                                                                            <td>
                                                                                <xsl:choose>
                                                                                    <xsl:when test="@value">
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
                                                                                    <xsl:when test="@required or contains($UpdateRequires,@name)">
                                                                                        <xsl:if test=" @required='false'">
                                                                                            <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                        </xsl:if>
                                                                                        <xsl:if test=" @required='true' or contains($UpdateRequires,@name)">
                                                                                            <span class="glyphicon glyphicon-remove" style="color:red;"></span>
                                                                                        </xsl:if>
                                                                                    </xsl:when>
                                                                                    <xsl:otherwise>
                                                                                        <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                    </xsl:otherwise>
                                                                                    <!--<xsl:value-of select="@required"/>-->
                                                                                </xsl:choose>
                                                                            </td>
                                                                        </tr>
                                                                    </tbody>
                                                                </xsl:for-each>
                                                            </table>                                                                
                                                        </div>
                                                    </div>
                                                    <xsl:choose>
                                                        <xsl:when test="mtg:Update/@requires or mtg:Query/@requires or mtg:Execute/@requires">
                                                            <div>
                                                                <div style="color:darkgrey;font-weight:bold;">
                                                                    Parameter(s) criteria (Compulsory)
                                                                </div>
                                                                <div class="paramList" style="font-size:16px;">

                                                                    <table class="table table-hover">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>
                                                                                    Parameter
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
                                                                                    Nullable
                                                                                </th>
                                                                            </tr>
                                                                        </thead>
                                                                        <xsl:for-each select="$TokDistinctAllValues">
                                                                            <xsl:variable name="i" select="position()" />
                                                                            <xsl:if test="$i &lt; last()">
                                                                            <tbody>
                                                                                <tr>
                                                                                    <td colspan="7">
                                                                                        <span>
                                                                                            <xsl:value-of select="$TokDistinctAllValues[$i]"/>
                                                                                            <sup>
                                                                                                <span style="color:red;">*</span>
                                                                                            </sup>
                                                                                        </span>
                                                                                    </td>
                                                                                    <td>
                                                                                        <span class="glyphicon glyphicon-remove" style="color:red;"></span>
                                                                                    </td>
                                                                                </tr>                     
                                                                            </tbody>
                                                                            </xsl:if>
                                                                        </xsl:for-each>
                                                        
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </xsl:when>
                                                    </xsl:choose> 
                                                </xsl:when>
                                            </xsl:choose>    

                                        </div>
                                    </div>
                                </xsl:when>
                            </xsl:choose>

                        </div>
                    </xsl:for-each>

                </div>
                <script>
                    var url = window.location.href;
                    var resName = url.substr(url.lastIndexOf('/')+1).toUpperCase();
                    var resNameDiv = document.getElementById('resName');
                    resName = resName.slice(0,-5);
                    resNameDiv.innerHTML = (resName).toLowerCase();
                    document.title = resName.toLowerCase();
                    
                    var uriDiv = document.getElementById('resUri');
                    var version = document.getElementById('resVersion').innerHTML;
                    var group= document.getElementById('resGroup').innerHTML;
                    var uri = ("/v"+version+"/"+resName+" "+group).toLowerCase();
                    var reqUri=("/v"+version+"/"+resName).toLowerCase();
                    uriDiv.innerHTML = uri;
                    
                    var ReqTypeItem = document.getElementsByClassName('ReqTypeItem');
                    
                    for(var i=0;i &lt; ReqTypeItem.length;i++)
                    {
                    ReqTypeItem[i].innerHTML = reqUri+"/{id}";
                    }
                    
                    var ReqTypeCollection = document.getElementsByClassName('ReqTypeCollection');
                    
                    for(var i=0;i &lt; ReqTypeItem.length;i++)
                    {
                    ReqTypeCollection[i].innerHTML = reqUri;
                    }
                    
                    var parentSpan = document.getElementById('resParent');
                    if(parentSpan != null){
                    parentSpan.innerHTML = parentSpan.innerHTML.toUpperCase();
                    }
                    
                    var paramListClasses = document.getElementsByClassName("sqlVariables");
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
                    }
                </script>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
