<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 20 mai 2008
  Time: 15:25:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<s:include value="/head.jsp" />
<div id="mainDiv">
	
		<div id="errorDiv">
			<s:actionmessage /> 
			<s:actionerror />
			<p>&nbsp;
			<p>
		</div>
	
	<s:form action="%{actionString}" name="rateForm">
		<s:hidden name="idPeriodeValidite" />
		<s:hidden name="codeEcran">
		
		<s:if test="%{withErrors}">
			<s:submit key="IMPOR_GRILLE_BTN_BACK" cssClass="BUTTON"/>
		</s:if> 
		
	</s:hidden>
	</s:form>
	</div>
	<s:if test="%{!withErrors}">
		<script type="text/javascript">
			document.forms["rateForm"].submit();
		</script>
	</s:if>
</body>
</html>