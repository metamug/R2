<jsp:directive.include file="../fragments/mason-init.jspf"/>
<%
    String domain = (String)request.getAttribute("domain");
%>
<m:resource>
    
    <m:request method="GET">
        <m:execute className="com.metamug.console.processables.datasource.GetBackendDatasource"
                   var="backendDatasource" output="true" param="${mtgReq}">
        </m:execute>
    </m:request>
    
    <m:request method="POST">
        <m:execute className="com.metamug.console.processables.datasource.AddDatasourceToBackend"
                   var="datasourceToBackend" output="true" param="${mtgReq}">
            <m:arg name="domain" value="<%= domain %>" />
        </m:execute>
    </m:request>
        
    <m:request method="DELETE">
        <m:execute className="com.metamug.console.processables.datasource.RemoveBackendDatasource"
                   var="backendDatasourceRemove" output="true" param="${mtgReq}">
            <m:arg name="domain" value="<%= domain %>" />
        </m:execute>
    </m:request>

</m:resource>