<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
<xsl:template match="Resource">
    <html><head><title><xsl:value-of select="@table"/></title></head>
    <body>
    	<p><b>Resource Name: </b><xsl:value-of select="@table"/></p>
    	<p><b>Resource Description: </b><xsl:choose>
                                                <xsl:when test='string-length(desc)>0'>
                                                        <xsl:value-of select="desc"/> 
                                                </xsl:when>
                                                <xsl:otherwise>Not Given
                                                </xsl:otherwise></xsl:choose></p>
                                                
	<p><b>API Version: </b><xsl:value-of select="@version"/></p>
	<p><b>Requests: </b></p>
		
                <table border="1">
			<tr>
				<td><b>Type</b></td><td><b>Description</b></td><td><b>Method</b></td><td><b>Output</b></td>
			</tr>
			<xsl:for-each select="Request">
				<tr>
					<td>Request</td>
					<td><xsl:choose>
                                                <xsl:when test='string-length(desc)>0'>
                                                        <xsl:value-of select="desc"/> 
                                                </xsl:when>
                                                <xsl:otherwise>Not Given
                                                </xsl:otherwise></xsl:choose></td>
					<td><xsl:value-of select="@method"/></td>
					<td><xsl:choose>
                                                <xsl:when test='string-length(@out)>0'>
                                                        <xsl:value-of select="@out"/> 
                                                </xsl:when>
                                                <xsl:otherwise>json
                                                </xsl:otherwise></xsl:choose></td>
				</tr>
			</xsl:for-each>
			<xsl:for-each select="Create">
				<tr>
					<td>Create</td>
					<td><xsl:choose>
                                                <xsl:when test='string-length(desc)>0'>
                                                        <xsl:value-of select="desc"/> 
                                                </xsl:when>
                                                <xsl:otherwise>Not Given
                                                </xsl:otherwise></xsl:choose></td>
					<td>POST</td>
					<td><xsl:choose>
                                                <xsl:when test='string-length(@out)>0'>
                                                        <xsl:value-of select="@out"/> 
                                                </xsl:when>
                                                <xsl:otherwise>json
                                                </xsl:otherwise></xsl:choose></td>
				</tr>
			</xsl:for-each>
			<xsl:for-each select="Read">
				<tr>
					<td>Read</td>
					<td><xsl:choose>
                                                <xsl:when test='string-length(desc)>0'>
                                                        <xsl:value-of select="desc"/> 
                                                </xsl:when>
                                                <xsl:otherwise>Not Given
                                                </xsl:otherwise></xsl:choose></td>
					<td>GET</td>
					<td><xsl:choose>
                                                <xsl:when test='string-length(@out)>0'>
                                                        <xsl:value-of select="@out"/> 
                                                </xsl:when>
                                                <xsl:otherwise>json
                                                </xsl:otherwise></xsl:choose></td>
				</tr>
			</xsl:for-each>
			<xsl:for-each select="Update">
				<tr>
					<td>Update</td>
					<td><xsl:choose>
                                                <xsl:when test='string-length(desc)>0'>
                                                        <xsl:value-of select="desc"/> 
                                                </xsl:when>
                                                <xsl:otherwise>Not Given
                                                </xsl:otherwise></xsl:choose></td>
					<td>PUT</td>
					<td><xsl:choose>
                                                <xsl:when test='string-length(@out)>0'>
                                                        <xsl:value-of select="@out"/> 
                                                </xsl:when>
                                                <xsl:otherwise>json
                                                </xsl:otherwise></xsl:choose></td>
				</tr>
			</xsl:for-each>
			<xsl:for-each select="Delete">
				<tr>
					<td>Delete</td>
					<td><xsl:choose>
                                                <xsl:when test='string-length(desc)>0'>
                                                        <xsl:value-of select="desc"/> 
                                                </xsl:when>
                                                <xsl:otherwise>Not Given
                                                </xsl:otherwise></xsl:choose></td>
					<td>DELETE</td>
					<td><xsl:choose>
                                                <xsl:when test='string-length(@out)>0'>
                                                        <xsl:value-of select="@out"/> 
                                                </xsl:when>
                                                <xsl:otherwise>json
                                                </xsl:otherwise></xsl:choose></td>
				</tr>
			</xsl:for-each>
		</table>	
    </body>
    </html>
</xsl:template>

</xsl:stylesheet>