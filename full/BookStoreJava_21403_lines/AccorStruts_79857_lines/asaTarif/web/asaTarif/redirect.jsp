<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 29 mai 2008
  Time: 17:06:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title></title>
		<s:if test="url not in {null, ''}">
			<script type="text/javascript">
				window.location.href = '<s:property value="url"/>';
			</script>
		</s:if>
	</head>
	<body>
	</body>
</html>