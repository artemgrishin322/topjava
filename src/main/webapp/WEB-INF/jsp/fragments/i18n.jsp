<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<script type="text/javascript">
    <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved", "common.errorStatus","common.confirm"}%>'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>
</html>
