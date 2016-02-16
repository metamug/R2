<%@include  file="../fragments/taglibs.jspf"%>
<c:choose>
  <c:when test="${not empty mtgReq.id and mtgReq.method eq 'GET'}">
    <sql:query var="result" dataSource="jdbc/mtgMySQL">
            select * from employee 
            where employee_name = ? 
            and employee_id = ?
        <sql:param value="${mtgReq.params.param1}" />
<sql:param value="${mtgReq.id}"/>
</sql:query>
    <p:postProcess className="com.mtg.Urls" value="${result}"></p:postProcess>
    <sql:update var="result" dataSource="jdbc/mtgMySQL">
            update employee set employee_name = ? where employee_id=?
        <sql:param value="${mtgReq.params.param1}" />
<sql:param value="${mtgReq.id}"/>
</sql:update>
  </c:when>

  <c:when test="${empty mtgReq.id and mtgReq.method eq 'POST'}">
    <sql:update var="result" dataSource="jdbc/mtgMySQL">
            insert into employee 
            (employee_name, employee_id) 
            values(?, ?)
        <sql:param value="${mtgReq.params.name}" />
<sql:param value="${mtgReq.id}"/>
</sql:update>
    <code:execute className="Apple" methodName="getApples"><code:param value="Value" /><code:param value="${mtgReq.params.p}" />
</code:execute>
  </c:when>

  <c:when test="${empty mtgReq.id and mtgReq.method eq 'GET'}">
    <code:execute className="Apple" methodName="getApples"><code:param value="${mtgReq.params.p}" />
<code:param value="${mtgReq.params.q}" />
</code:execute>
  </c:when>

  <c:when test="${not empty mtgReq.id and mtgReq.method eq 'GET'}">
    <sql:query var="result" dataSource="jdbc/mtgMySQL">
            select * from employee where employee_id=12
        </sql:query>
    <mtg:out value="${result}"></mtg:out>
  </c:when>

  <c:otherwise>
    <json:object name="data">
      <json:property name="Code" value="405"/>
      <json:property name="Message" value="Method not allowed"/>
    </json:object>
  </c:otherwise>
</c:choose>