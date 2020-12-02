<jsp:directive.include file="../fragments/mason-init.jspf"/>
<%
    String token = request.getHeader("Authorization");
    String serverName = request.getServerName();
    String scheme = request.getScheme();
    int port = request.getServerPort();
%>
<m:resource>

    <m:request method="POST">
        <m:execute className="com.metamug.console.processables.AddToResourceProcessor"
                   var="getResult" output="true" param="${mtgReq}">
            <m:arg name="Authorization" value="<%= token %>" />
            <m:arg name="ServerName" value="<%= serverName %>" />
            <m:arg name="Scheme" value="<%= scheme %>" />
            <m:arg name="Port" value="<%= port %>" />
        </m:execute>

    </m:request>

</m:resource>