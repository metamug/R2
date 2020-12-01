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
        
        <m:xrequest var="role" url="<%= url %>" method="POST" output="true" >
            <m:header name="Content-Type" value="application/x-www-form-urlencoded"/>
            <m:header name="Authorization" value='<%= token %>' />
            <m:xparam name="action" value="query"/>
            <m:xparam name="query" value="SELECT usr.*,role.* FROM usr 
                      RIGHT JOIN usr_role ON usr_role.user_id=usr.user_id
                            RIGHT JOIN role ON role.role_id=usr_role.role_id" />
        </m:xrequest>

        <m:xrequest var="user" url="<%= url %>" method="POST"  output="true" >
            <m:header name="Content-Type" value="application/x-www-form-urlencoded"/>
            <m:header name="Authorization" value='<%= token %>' />
            <m:xparam name="action" value="query"/>
            <m:xparam name="query" value="SELECT usr.*,role.* FROM usr 
                      LEFT JOIN usr_role ON usr_role.user_id=usr.user_id
                            LEFT JOIN role ON role.role_id=usr_role.role_id" />
        </m:xrequest>
<%--
        <sql:query var="query1" dataSource="${datasource}"> SELECT FILE_NAME,FILE_VERSION FROM CONSOLE_FILE
            WHERE APP_NAME=? AND DELETED_ON IS NULL
            <sql:param value="${mtgReq.params['appid']}"/>
        </sql:query>     
        <c:set target="${output}" property="allresource" value="${query1}"/>--%>

        <sql:query var="query2" dataSource="${datasource}"> SELECT FILE_NAME,FILE_VERSION,FILE_AUTH FROM CONSOLE_FILE
            WHERE APP_NAME=? AND SECURE=1 AND DELETED_ON IS NULL
            <sql:param value="${mtgReq.params['appid']}"/>
        </sql:query>
        <c:set target="${output}" property="resource" value="${query2}"/>

    </m:request>

    <m:request method="POST">
        <m:xrequest var="addRole" url="<%= url %>" method="POST" output="true" >
            <m:header name="Content-Type" value="application/x-www-form-urlencoded"/>
            <m:header name="Authorization" value='<%= token %>' />
            <m:xparam name="action" value="query"/>
            <m:xparam name="query" value="INSERT INTO role (role_name) VALUES ('${mtgReq.params['role']}'); 
                      SELECT IDENTITY() as role_id
            " />
        </m:xrequest>
    </m:request>
        
    <m:request method="DELETE" item="true">
        <m:xrequest var="deleteRole" url="<%= url %>" method="POST" output="true" >
            <m:header name="Content-Type" value="application/x-www-form-urlencoded"/>
            <m:header name="Authorization" value='<%= token %>' />
            <m:xparam name="action" value="query"/>
            <m:xparam name="query" value="DELETE FROM role WHERE role_id=${mtgReq.id}" />
        </m:xrequest>
    </m:request>

</m:resource>
