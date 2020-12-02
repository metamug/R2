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

    <m:request method="GET">        
        <m:xrequest var="user" url="<%= url %>" method="POST"  output="true" >
            <m:header name="Content-Type" value="application/x-www-form-urlencoded"/>
            <m:header name="Authorization" value='<%= token %>' />
            <m:xparam name="action" value="query"/>
            <m:xparam name="query" value="SELECT * FROM usr" />
            <%--<m:xparam name="query" value="SELECT usr.*,role.* FROM role 
                      RIGHT JOIN usr_role ON usr_role.role_id=role.role_id
                            RIGHT JOIN usr ON usr.user_id=usr_role.user_id" />--%>
        </m:xrequest>
    </m:request>

    <m:request method="POST">
        <m:xrequest var="user" url="<%= url %>" method="POST" output="true" >
            <m:header name="Content-Type" value="application/x-www-form-urlencoded"/>
            <m:header name="Authorization" value='<%= token %>' />
            <m:xparam name="action" value="query"/>
            <m:xparam name="query" value="INSERT INTO usr (user_name,pass_word) 
                      VALUES ('${mtgReq.params['username']}','${mtgReq.params['password']}');
                      SELECT IDENTITY() as user_id;
            " />
        </m:xrequest>
    </m:request>
        
    <m:request method="DELETE" item="true">
        <m:xrequest var="deleteUser" url="<%= url %>" method="POST" output="true" >
            <m:header name="Content-Type" value="application/x-www-form-urlencoded"/>
            <m:header name="Authorization" value='<%= token %>' />
            <m:xparam name="action" value="query"/>
            <m:xparam name="query" value="DELETE FROM usr WHERE user_id=${mtgReq.id}" />
        </m:xrequest>
    </m:request>

</m:resource>
