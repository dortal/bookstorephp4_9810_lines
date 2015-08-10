<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 21 mai 2008
  Time: 07:51:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
	</head>
	<body>
		<s:include value="/head.jsp"/>
		<div id="mainDiv" class="DefaultStyle">
			<p class="ErrorTitle"><s:text name="COM_PRM_LBL_ERREUR"/></p>
			<div id="errorDiv" style="width: 600px">
				<p><s:text name="COM_PRM_LBL_MESSAGE"/></p>
				<s:actionmessage/>
				<s:actionerror/>
			</div>
			<br/><br/>
		</div>
	</body>
</html>