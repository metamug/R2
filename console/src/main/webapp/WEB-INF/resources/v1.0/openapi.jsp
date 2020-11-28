<jsp:directive.include file="../fragments/mason-init.jspf"/>

<m:resource>

    <m:request method='GET' item="true">

        <sql:query var="app" dataSource="${datasource}">
          SELECT APP_NAME,APP_DESCRIPTION
          FROM console_app
          WHERE app_name=?
            <sql:param value="${mtgReq.id}"/>
        </sql:query>
        <sql:query var="resources" dataSource="${datasource}">
            SELECT FILE_NAME,FILE_VERSION
            FROM console_file
            WHERE app_name=? AND deleted_on IS NULL
            <sql:param value="${mtgReq.id}"/>
        </sql:query>
        <m:execute className="com.metamug.parser.OpenApiJson" var="spec" output="true" param="${mtgReq}">
            <m:arg name="appname" value="${app.rows[0].APP_NAME}" />
            <m:arg name="appdesc" value="${app.rows[0].APP_DESCRIPTION}" />
            <m:arg name="resources" value="${resources.rows}" />
        </m:execute>
    </m:request>

</m:resource>
