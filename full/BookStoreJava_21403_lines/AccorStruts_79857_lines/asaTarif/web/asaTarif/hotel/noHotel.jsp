<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 26 mai 2008
  Time: 17:19:43
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
			<br/><br/>
			<fieldset style="width:400px;">
				<legend><s:text name="COM_NOHOTEL_LBL_TITRE"/> :</legend>
				<br/>
				<s:text name="COM_NOHOTEL_LBL_INTRO"/>
				<br/><br/>
				<s:url action="searchHotel.action" id="goSearchHotel" escapeAmp="false">
					<s:param name="url" value="%{#request.url}"></s:param>
				</s:url>
				<a href="<s:property value="#goSearchHotel"/>"><b>Ok</b></a><br/><br/>
			</fieldset>
			<br/><br/>
		</div>
	</body>
</html>