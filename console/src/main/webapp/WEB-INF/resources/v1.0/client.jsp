<jsp:directive.include file="../fragments/mason-init.jspf"/>

<m:resource>

    <m:request method='GET'>
        <sql:query var="client" dataSource="${datasource}">
            SELECT client_id,client_name,created_on FROM app_client
                LEFT JOIN console_app ON console_app.app_id=app_client.app_id
                    WHERE console_app.app_name=?
            <sql:param value="${mtgReq.params['backendName']}"/>
        </sql:query>
    </m:request>
    
    <m:request method='GET' item="true">
        <sql:query var="client" dataSource="${datasource}">
            SELECT client_name,access_key,secret_key FROM app_client
                WHERE client_id=?
            <sql:param value="${mtgReq.id}"/>
        </sql:query>
    </m:request>
    
    <m:request method='POST'>
        <m:execute className="com.metamug.console.processables.ClientKeyGenerator" var="keys" param="${mtgReq}"/>
        <sql:update var="client" dataSource="${datasource}">
            INSERT INTO app_client (client_name,access_key,secret_key,app_id) VALUES (?,?,?,?)
            <sql:param value="${mtgReq.params['clientName']}"/>
            <sql:param value="${keys.access}"/>
            <sql:param value="${keys.secret}"/>
            <sql:param value="${mtgReq.params['backendName']}"/>
        </sql:update>
    </m:request>
    
    <m:request method='DELETE' item="true">
        <sql:update var="deleteClient" dataSource="${datasource}">
            DELETE FROM app_client WHERE client_id=?
            <sql:param value="${mtgReq.id}"/>
        </sql:update>
    </m:request>

</m:resource>
