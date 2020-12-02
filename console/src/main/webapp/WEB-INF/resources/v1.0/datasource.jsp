<jsp:directive.include file="../fragments/mason-init.jspf"/>

<m:resource>

    <m:request method='GET'>
        <sql:query var="datasources" dataSource="${datasource}">
          SELECT * from datasource
        </sql:query>
        <c:set target="${output}" property="datasource" value="${datasources}"/>
    </m:request>
    
    <m:request method='GET' item="true">
        <sql:query var="datasources" dataSource="${datasource}">
          SELECT * from datasource WHERE ds_id=? 
            <sql:param value="${mtgReq.id}"/>
        </sql:query>
        <c:set target="${output}" property="datasource" value="${datasources}"/>
    </m:request>

    <m:request method='POST'>
        <m:execute className="com.metamug.console.processables.datasource.TestDsConnection"
                   var="testConnection" output="true" param="${mtgReq}">
        </m:execute>
        <c:if test="${testConnection eq true}">
            <sql:update var="addDatasource" dataSource="${datasource}">
                INSERT INTO datasource (ds_name,ds_url,db_type,ds_username,ds_password) VALUES (?,?,?,?,?)
                <sql:param value="${mtgReq.params['name']}"/>
                <sql:param value="${mtgReq.params['url']}"/>
                <sql:param value="${mtgReq.params['type']}"/>
                <sql:param value="${mtgReq.params['username']}"/>
                <sql:param value="${mtgReq.params['password']}"/>
            </sql:update>
        </c:if>
        <%--<sql:update var="addDatasource" dataSource="${datasource}">
            INSERT INTO datasource (ds_name,ds_url,db_type,ds_username,ds_password) VALUES (?,?,?,?,?)
            <sql:param value="${mtgReq.params['name']}"/>
            <sql:param value="${mtgReq.params['url']}"/>
            <sql:param value="${mtgReq.params['type']}"/>
            <sql:param value="${mtgReq.params['username']}"/>
            <sql:param value="${mtgReq.params['password']}"/>
        </sql:update>
        <c:set target="${output}" property="addDatasource" value="${addDatasource}"/>--%>
    </m:request>
    
    <m:request method='DELETE' item="true">
        <sql:update var="deleteDatasource" dataSource="${datasource}">
            DELETE FROM datasource WHERE ds_id=?
            <sql:param value="${mtgReq.id}"/>
        </sql:update>
        <c:set target="${output}" property="deleteDatasource" value="${deleteDatasource}"/>
        <%--<m:execute className="com.metamug.console.processables.datasource.RemoveAllBackendDatasource"
                   var="datasourceBackendRemove" output="true" param="${mtgReq}">
        </m:execute>--%>
    </m:request>
    
</m:resource>
