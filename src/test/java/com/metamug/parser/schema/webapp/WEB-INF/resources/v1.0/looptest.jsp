<jsp:directive.include file="../fragments/mason-init.jspf"/>
<m:resource>

  <m:request method="POST" item="true">
<m:param name="name" type="TEXT" value="${mtgReq.params['name']}" max="100.0" minLen="1" />
<m:param name="rating" type="NUMBER" value="${mtgReq.params['rating']}" max="5.0" min="1.0" />