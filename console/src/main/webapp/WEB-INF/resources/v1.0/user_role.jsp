<jsp:directive.include file="../fragments/mason-init.jspf"/>
<%
    String appid = request.getParameter("appid");
    String token = com.metamug.console.util.Util.getMasonApiRequestSignature(appid);
    String port = ":"+Integer.toString(request.getServerPort());
    if (request.getServerPort() == 80 || request.getServerPort() == 443) {
        port = "";
    }  
    String url = request.getScheme()+"://"+request.getServerName()+port+"/"+appid+"/query";
%>
<m:resource>

    <m:request method="POST" >
        <m:xrequest var="user" url="<%= url %>" method="POST" output="true">
            <m:header name="Content-Type" value="application/x-www-form-urlencoded"/>
            <m:header name="Authorization" value='<%= token %>' />
            <m:xparam name="action" value="query"/>      
            <m:xparam name="query" value="INSERT INTO usr_role (role_id,user_id) 
                          VALUES ${mtgReq.params['roleId']},${mtgReq.params['userId']}" />
        </m:xrequest>
    </m:request>
                    
    <m:request method="DELETE">
        <m:xrequest var="user" output="true"  url="<%= url %>" method="POST" >
            <m:header name="Content-Type" value="application/x-www-form-urlencoded"/>
            <m:header name="Authorization" value='<%= token %>' />
            <m:xparam name="action" value="query"/>                   
            <m:xparam name="query" 
                    value="DELETE FROM usr_role WHERE role_id=${mtgReq.params['roleId']} AND user_id=${mtgReq.params['userId']}" />
        </m:xrequest>        
    </m:request>

</m:resource>
