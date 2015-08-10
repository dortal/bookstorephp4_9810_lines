<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 20 mai 2008
  Time: 15:25:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
	</head>
	<body>
		<s:include value="/head.jsp"/>
        <s:set name="jspSuf" value="%{'.jsp'}"/>
        <s:if test="url.endsWith(#jspSuf)">
            <s:include value="%{url}"/>
        </s:if>
        <s:else>
            <iframe src="<s:property value="url"/>" width="100%" height="100%" scrolling="auto" frameborder="0"></iframe>
        </s:else>
	</body>
</html>