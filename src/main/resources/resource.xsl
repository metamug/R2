<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
<xsl:template match="Resource">
    <html><head><title><xsl:value-of select="@table"/></title></head>
    <body>
    	<p><b>Resource Name: </b><xsl:value-of select="@table"/></p>
    	<p><b>Resource Description: </b><xsl:apply-templates select="Resource/desc"/></p>
		<p><b>API Version: </b><xsl:value-of select="@version"/></p>
		<p><b>Requests: </b></p>
		<table style="width=100%">
			<tr>
				<td><b>Type</b></td><td><b>Description</b></td><td><b>Method</b></td><td><b>Output</b></td>
			</tr>
			<xsl:for-each select="Resource/Request">
				<tr>
					<td><b>Request</b></td>
					<td><b><xsl:value-of select="desc"/></b></td>
					<td><b><xsl:value-of select="@method"/></b></td>
					<td><b><xsl:value-of select="@out"/></b></td>
				</tr>
			</xsl:for-each>
			<xsl:for-each select="Resource/Create">
				<tr>
					<td><b>Create</b></td>
					<td><b><xsl:value-of select="desc"/></b></td>
					<td><b><xsl:value-of select="@method"/></b></td>
					<td><b><xsl:value-of select="@out"/></b></td>
				</tr>
			</xsl:for-each>
			<xsl:for-each select="Resource/Read">
				<tr>
					<td><b>Read</b></td>
					<td><b><xsl:value-of select="desc"/></b></td>
					<td><b><xsl:value-of select="@method"/></b></td>
					<td><b><xsl:value-of select="@out"/></b></td>
				</tr>
			</xsl:for-each>
			<xsl:for-each select="Resource/Update">
				<tr>
					<td><b>Update</b></td>
					<td><b><xsl:value-of select="desc"/></b></td>
					<td><b><xsl:value-of select="@method"/></b></td>
					<td><b><xsl:value-of select="@out"/></b></td>
				</tr>
			</xsl:for-each>
			<xsl:for-each select="Resource/Delete">
				<tr>
					<td><b>Delete</b></td>
					<td><b><xsl:value-of select="desc"/></b></td>
					<td><b><xsl:value-of select="@method"/></b></td>
					<td><b><xsl:value-of select="@out"/></b></td>
				</tr>
			</xsl:for-each>
		</table>	
    </body>
    </html>
</xsl:template>

</xsl:stylesheet>