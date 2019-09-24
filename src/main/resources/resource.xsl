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
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"/>
            </head>
            <body>
                
                <div class="container" style="padding-top:25px">
                    <div class="panel-body">
                        
                        <span id="resName" style="color:darkgrey;font-weight:bold;margin-left:5px;margin-right:5px">
                            ABC
                        </span>
                        <span id="resVersion" class="badge">
                            <xsl:value-of select="@v"/>
                        </span>
                        <xsl:choose>
                            <xsl:when test="@auth != ''">
                                <span class="fa fa-lock" style="color: #777777;padding-left: 5px;font-size: large;"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <span class="fa fa-unlock" style="color: #777777;padding-left: 5px;font-size: large;"/>
                            </xsl:otherwise>
                        </xsl:choose>
                        <span id="resGroup" style="padding:5px;color:#777777;">
                            <!--This span displays parameters encountered in Request and is set using JS-->
                        </span>
                        <span>
                            <a id="resUri" target="_blank" style="font-weight:bold;cursor:pointer;" 
                                  class="text text-success pull-right">
                            </a>
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
                        <xsl:variable name="HeaderName" select="mtg:Header/@name"/>
                        <xsl:variable name="JointVarReqs" select="string-join( ($UpdateRequires,$QueryRequires,$ExecuteRequires), ',')"/>
                        <xsl:variable name="JointVarPN" select="string-join( ($ParamName), ',')"/>
                        <xsl:variable name="TokenizedVarReqs" select="tokenize($JointVarReqs,'[,]')"/>
                        <xsl:variable name="TokenizedParamName" select="tokenize($JointVarPN,'[,]')"/>
                        <xsl:variable name="AllValues" select="(($TokenizedVarReqs),',')"/>
                        <xsl:variable name="DistAllValues" select="distinct-values($AllValues)"/>
                        <xsl:variable name="DistinctAllValues" select="string-join($DistAllValues,',')"/>
                        <xsl:variable name="TokDistinctAllValues" select="tokenize($DistinctAllValues,',')"/>
                        
                        <div class="col-md-12" style="padding-top:10px">
                            <xsl:choose>
                                <!-- condition - item request and method not DELETE -->
                                <xsl:when test="contains(@item,'true') and not(contains(@method,'DELETE'))" >
                                    <xsl:variable name="panelClass">
                                        <xsl:choose>
                                            <xsl:when test="contains(@method,'GET')">panel panel-info</xsl:when>
                                            <xsl:when test="contains(@method,'POST')">panel panel-success</xsl:when>
                                            <xsl:when test="contains(@method,'PUT')">panel panel-warning</xsl:when>
                                        </xsl:choose>
                                    </xsl:variable>
                                    <div>
                                        <xsl:attribute name="class">
                                            <xsl:value-of select="$panelClass"/>
                                        </xsl:attribute>
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
                                            <!-- Desc -->
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
                                            <!-- Headers -->
                                            <div style="padding-bottom:12px">
                                                <div style="color:darkgrey;font-weight:bold;margin-bottom:4px">
                                                    Response Headers
                                                </div>
                                                <div>
                                                    <xsl:for-each select="mtg:Header">    
                                                        <span class="label label-primary" style="margin-right:5px">
                                                            <xsl:value-of select="@name"/>
                                                        </span>
                                                    </xsl:for-each> 
                                                </div>
                                            </div>   
                                            
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
                                                            <xsl:otherwise>
                                                                <span class="label label-warning" style="margin-right: 5px; background-color: #f1d408;">No Variable Passed</span>
                                                            </xsl:otherwise>
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
                                                            <xsl:otherwise>
                                                                <span class="label label-warning" style="margin-right: 5px; background-color: #f1d408;">No Variable Passed</span>
                                                            </xsl:otherwise>
                                                        </xsl:choose>
                                                    </xsl:for-each>
                                                    <xsl:for-each select="mtg:Execute">
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
                                                            <xsl:otherwise>
                                                                <span class="label label-warning" style="margin-right: 5px; background-color: #f1d408;">No Variable Passed</span>
                                                            </xsl:otherwise>
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
                                                <xsl:when test="mtg:Param">  
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
                                                                    <xsl:variable name="tempName" select="@name"/>
                                                                    <xsl:variable name="exempt" select="index-of(($TokenizedVarReqs),$tempName)"/>
                                                                    <xsl:if test="empty($exempt)">
                                                                        <tbody>
                                                                            <tr>
                                                                                <td>
                                                                                    <xsl:choose>
                                                                                        <xsl:when test="@name">
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
                                                                                        <xsl:when test="@required or matches($JointVarReqs,@name)">
                                                                                            <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                        </xsl:when>
                                                                                        <xsl:otherwise>
                                                                                            <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                        </xsl:otherwise>
                                                                                    </xsl:choose>
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </xsl:if>
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
                                                                        <xsl:choose>
                                                                            <xsl:when test="mtg:Param"> 
                                                                                <xsl:for-each select="mtg:Param">
                                                                                    <xsl:variable name="tempName" select="@name"/>
                                                                                    <xsl:variable name="tempType" select="@type"/>
                                                                                    <xsl:variable name="tempValue" select="@value"/>
                                                                                    <xsl:variable name="tempMin" select="@min"/>
                                                                                    <xsl:variable name="tempMax" select="@max"/>
                                                                                    <xsl:variable name="tempMinLen" select="@minlength"/>
                                                                                    <xsl:variable name="tempMaxLen" select="@maxlength"/>
                                                                                    <xsl:for-each select="$TokDistinctAllValues">
                                                                                        <xsl:variable name="i" select="position()" />
                                                                                        <xsl:if test="not(compare($TokDistinctAllValues[$i],$tempName))">
                                                                                            <xsl:if test="$i &lt; (last()-1)">
                                                                                                <tbody>
                                                                                                    <tr>
                                                                                                        <td>
                                                                                                            <span>
                                                                                                                <xsl:value-of select="$TokDistinctAllValues[$i]"/>
                                                                                                                <sup>
                                                                                                                    <span style="color:red;">*</span>
                                                                                                                </sup>
                                                                                                            </span>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <span>
                                                                                                                <xsl:value-of select="$tempType"/>
                                                                                                            </span>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='number'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMin"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>

                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='number'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMax"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='text'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMinLen"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>

                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='text'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMaxLen"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <xsl:choose>
                                                                                                                <xsl:when test="$tempValue">
                                                                                                                    <xsl:if test=" $tempValue !=''">
                                                                                                                        <span>
                                                                                                                            <xsl:value-of select="$tempValue"/>
                                                                                                                        </span>
                                                                                                                    </xsl:if>
                                                                                                                </xsl:when>
                                                                                                            </xsl:choose>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <span class="glyphicon glyphicon-remove" style="color:red;"></span>
                                                                                                        </td>
                                                                                                    </tr>                     
                                                                                                </tbody>
                                                                                            </xsl:if>
                                                                                        </xsl:if>
                                                                                    </xsl:for-each>
                                                                                </xsl:for-each>
                                                                            </xsl:when>
                                                                            <xsl:otherwise>
                                                                                <xsl:for-each select="$TokDistinctAllValues">
                                                                                    <xsl:variable name="i" select="position()" />
                                                                                    <xsl:if test="$i &lt; (last()-1)">
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
                                                                            </xsl:otherwise>
                                                                        </xsl:choose>
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
                                <!-- condition - item/collection request and DELETE -->
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
                                            <!-- Headers -->
                                            <div style="padding-bottom:12px">
                                                <div style="color:darkgrey;font-weight:bold;margin-bottom:4px">
                                                    Response Headers
                                                </div>
                                                <div>
                                                    <xsl:for-each select="mtg:Header">    
                                                        <span class="label label-primary" style="margin-right:5px">
                                                            <xsl:value-of select="@name"/>
                                                        </span>
                                                    </xsl:for-each> 
                                                </div>
                                            </div>        
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
                                                <xsl:when test="mtg:Param ">  
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
                                                                    <xsl:variable name="tempName" select="@name"/>
                                                                    <xsl:variable name="exempt" select="index-of(($TokenizedVarReqs),$tempName)"/>
                                                                    <xsl:if test="empty($exempt)">
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
                                                                                        <xsl:when test="@required or matches($JointVarReqs,@name)">
                                                                                            <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                        </xsl:when>
                                                                                        <xsl:otherwise>
                                                                                            <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                        </xsl:otherwise>
                                                                                    </xsl:choose>
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </xsl:if>
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
                                                                        
                                                                        <xsl:choose>
                                                                            <xsl:when test="mtg:Param"> 
                                                                                <xsl:for-each select="mtg:Param">
                                                                                    <xsl:variable name="tempName" select="@name"/>
                                                                                    <xsl:variable name="tempType" select="@type"/>
                                                                                    <xsl:variable name="tempValue" select="@value"/>
                                                                                    <xsl:variable name="tempMin" select="@min"/>
                                                                                    <xsl:variable name="tempMax" select="@max"/>
                                                                                    <xsl:variable name="tempMinLen" select="@minlength"/>
                                                                                    <xsl:variable name="tempMaxLen" select="@maxlength"/>
                                                                                    <xsl:for-each select="$TokDistinctAllValues">
                                                                                        <xsl:variable name="i" select="position()" />
                                                                                        <xsl:if test="not(compare($TokDistinctAllValues[$i],$tempName))">
                                                                                            <xsl:if test="$i &lt; (last()-1)">
                                                                                                <tbody>
                                                                                                    <tr>
                                                                                                        <td>
                                                                                                            <span>
                                                                                                                <xsl:value-of select="$TokDistinctAllValues[$i]"/>
                                                                                                                <sup>
                                                                                                                    <span style="color:red;">*</span>
                                                                                                                </sup>
                                                                                                            </span>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <span>
                                                                                                                <xsl:value-of select="$tempType"/>
                                                                                                            </span>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='number'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMin"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>

                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='number'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMax"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='text'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMinLen"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>

                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='text'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMaxLen"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <xsl:choose>
                                                                                                                <xsl:when test="$tempValue">
                                                                                                                    <xsl:if test=" $tempValue !=''">
                                                                                                                        <span>
                                                                                                                            <xsl:value-of select="$tempValue"/>
                                                                                                                        </span>
                                                                                                                    </xsl:if>
                                                                                                                </xsl:when>
                                                                                                            </xsl:choose>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <span class="glyphicon glyphicon-remove" style="color:red;"></span>
                                                                                                        </td>
                                                                                                    </tr>                     
                                                                                                </tbody>
                                                                                            </xsl:if>
                                                                                        </xsl:if>
                                                                                    </xsl:for-each>
                                                                                </xsl:for-each>
                                                                            </xsl:when>
                                                                            <xsl:otherwise>
                                                                                <xsl:for-each select="$TokDistinctAllValues">
                                                                                    <xsl:variable name="i" select="position()" />
                                                                                    <xsl:if test="$i &lt; (last()-1)">
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
                                                                            </xsl:otherwise>
                                                                        </xsl:choose>
                                                        
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
                                <!-- condition - collection request and all methods -->
                                <xsl:when test="not(@item) or contains(@item,'false')">
                                    <xsl:variable name="panelClass">
                                        <xsl:choose>
                                            <xsl:when test="contains(@method,'GET')">panel panel-info</xsl:when>
                                            <xsl:when test="contains(@method,'POST')">panel panel-success</xsl:when>
                                            <xsl:when test="contains(@method,'PUT')">panel panel-warning</xsl:when>
                                            <xsl:when test="contains(@method,'DELETE')">panel panel-danger</xsl:when>
                                        </xsl:choose>
                                    </xsl:variable>
                                    <xsl:variable name="labelClass">
                                        <xsl:choose>
                                            <xsl:when test="contains(@method,'GET')">label label-info</xsl:when>
                                            <xsl:when test="contains(@method,'POST')">label label-success</xsl:when>
                                            <xsl:when test="contains(@method,'PUT')">label label-warning</xsl:when>
                                            <xsl:when test="contains(@method,'DELETE')">label label-danger</xsl:when>
                                        </xsl:choose>
                                    </xsl:variable>
                                    <div>
                                        <xsl:attribute name="class">
                                            <xsl:value-of select="$panelClass"/>
                                        </xsl:attribute>
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
                                            <!-- Headers -->
                                            <div style="padding-bottom:12px">
                                                <div style="color:darkgrey;font-weight:bold;margin-bottom:4px">
                                                    Response Headers
                                                </div>
                                                <div>
                                                    <xsl:for-each select="mtg:Header">    
                                                        <span class="label label-primary" style="margin-right:5px">
                                                            <xsl:value-of select="@name"/>
                                                        </span>
                                                    </xsl:for-each> 
                                                </div>
                                            </div>        
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
                                                <xsl:when test="mtg:Param ">
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
                                                                    <xsl:variable name="tempName" select="@name"/>
                                                                    <xsl:variable name="exempt" select="index-of(($TokenizedVarReqs),$tempName)"/>
                                                                    <xsl:if test="empty($exempt)">
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
                                                                                        <xsl:when test="@required or matches($JointVarReqs,@name)">
                                                                                            <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                        </xsl:when>
                                                                                        <xsl:otherwise>
                                                                                            <span class="glyphicon glyphicon-ok" style="color:green;"></span>
                                                                                        </xsl:otherwise>
                                                                                        <!--<xsl:value-of select="@required"/>-->
                                                                                    </xsl:choose>
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </xsl:if>
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
                                                                        
                                                                        <xsl:choose>
                                                                            <xsl:when test="mtg:Param"> 
                                                                                <xsl:for-each select="mtg:Param">
                                                                                    <xsl:variable name="tempName" select="@name"/>
                                                                                    <xsl:variable name="tempType" select="@type"/>
                                                                                    <xsl:variable name="tempValue" select="@value"/>
                                                                                    <xsl:variable name="tempMin" select="@min"/>
                                                                                    <xsl:variable name="tempMax" select="@max"/>
                                                                                    <xsl:variable name="tempMinLen" select="@minlength"/>
                                                                                    <xsl:variable name="tempMaxLen" select="@maxlength"/>
                                                                                    <xsl:for-each select="$TokDistinctAllValues">
                                                                                        <xsl:variable name="i" select="position()" />
                                                                                        <xsl:if test="not(compare($TokDistinctAllValues[$i],$tempName))">
                                                                                            <xsl:if test="$i &lt; (last()-1)">
                                                                                                <tbody>
                                                                                                    <tr>
                                                                                                        <td>
                                                                                                            <span>
                                                                                                                <xsl:value-of select="$TokDistinctAllValues[$i]"/>
                                                                                                                <sup>
                                                                                                                    <span style="color:red;">*</span>
                                                                                                                </sup>
                                                                                                            </span>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <span>
                                                                                                                <xsl:value-of select="$tempType"/>
                                                                                                            </span>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='number'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMin"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>

                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='number'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMax"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='text'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMinLen"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>

                                                                                                        <td>
                                                                                                            <xsl:if test=" $tempType='text'">
                                                                                                                <span>
                                                                                                                    <xsl:value-of select="$tempMaxLen"/>
                                                                                                                </span>
                                                                                                            </xsl:if>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <xsl:choose>
                                                                                                                <xsl:when test="$tempValue">
                                                                                                                    <xsl:if test=" $tempValue !=''">
                                                                                                                        <span>
                                                                                                                            <xsl:value-of select="$tempValue"/>
                                                                                                                        </span>
                                                                                                                    </xsl:if>
                                                                                                                </xsl:when>
                                                                                                            </xsl:choose>
                                                                                                        </td>
                                                                                                        <td>
                                                                                                            <span class="glyphicon glyphicon-remove" style="color:red;"></span>
                                                                                                        </td>
                                                                                                    </tr>                     
                                                                                                </tbody>
                                                                                            </xsl:if>
                                                                                        </xsl:if>
                                                                                    </xsl:for-each>
                                                                                </xsl:for-each>
                                                                            </xsl:when>
                                                                            <xsl:otherwise>
                                                                                <xsl:for-each select="$TokDistinctAllValues">
                                                                                    <xsl:variable name="i" select="position()" />
                                                                                    <xsl:if test="$i &lt; (last()-1)">
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
                                                                            </xsl:otherwise>
                                                                        </xsl:choose>
                                                        
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
                    
                    var urlArr = url.split("/");
                    var uri = urlArr[0]+"//"+urlArr[2]+"/"+urlArr[3]+
                            ("/v"+version+"/"+resName+" "+group).toLowerCase();
                    var reqUri=("/v"+version+"/"+resName).toLowerCase();
                    uriDiv.innerHTML = uri;
                    uriDiv.href = uri;
                    
                    var parentSpan = document.getElementById('resParent');
                    if(parentSpan != null){
                    parentSpan.innerHTML = parentSpan.innerHTML.toUpperCase();
                    }
                    
                    var paramListClasses = document.getElementsByClassName("sqlVariables");
                    for (var i = 0; i &lt; paramListClasses.length; i++) {
                    var unique = [];
                    var paramList = paramListClasses[i].childNodes;
                    for(var j = 0; j &lt; paramList.length; j++){
                    var param = paramList[j];
                    unique.push(param.innerHTML);
                    unique = eliminateDuplicates(unique);
                    }
                    var l = paramList.length;
                    for(var j=l-1; j &gt;= 0; j--){
                    paramList[j].parentNode.removeChild(paramList[j]);
                    }
                    for(var j=0; j &lt; unique.length; j++){
                    var span = document.createElement('span');
                    span.classList.add('label','label-primary');
                    span.innerHTML = unique[j];
                    span.style['margin-right'] = '5px';
                    if(span.innerHTML=="No Variable Passed")
                    {
                    span.style['background-color'] = 'orange';
                    }
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
                    
                    var ReqTypeItem = document.getElementsByClassName('ReqTypeItem');             
                    for(var i=0;i &lt; ReqTypeItem.length;i++) {
                        ReqTypeItem[i].innerHTML = reqUri+"/{id}";
                    }
                    var ReqTypeCollection = document.getElementsByClassName('ReqTypeCollection');                    
                    for(var i=0;i &lt; ReqTypeCollection.length;i++) {
                        ReqTypeCollection[i].innerHTML = reqUri;
                    }
                </script>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
