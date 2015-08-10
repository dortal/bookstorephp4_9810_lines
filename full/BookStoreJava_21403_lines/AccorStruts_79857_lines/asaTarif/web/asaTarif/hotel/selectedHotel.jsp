<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 27 mai 2008
  Time: 08:47:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="url not in {null, ''}">
	<script type="text/javascript">
		window.location.href='<s:property value="url"/>';
	</script>
</s:if>
<s:else>
	<html>
		<head>
		</head>
		<body>
			<s:include value="/head.jsp"/>
			<div id="mainDiv" class="DefaultStyle">
				<br/>
				<br/>
				<fieldset style="width:400px;">
					<legend><s:text name="COM_SELECTEDHOTEL_LBL_TITRE"/></legend>
					<s:if test="#session.hotel != null">
						<table>
							<tr>
								<td style="font-weight: bold; text-align: right"><s:text name="COM_SELECTEDHOTEL_LBL_CODE"/> :</td>
								<td style="text-align: left"><s:property value="#session.hotel.code"/></td>
							</tr>
							<tr>
								<td style="font-weight: bold; text-align: right"><s:text name="COM_SELECTEDHOTEL_LBL_NOM"/> :</td>
								<td style="text-align: left"><s:property value="#session.hotel.name"/></td>
							</tr>
							<tr>
								<td style="font-weight: bold; text-align: right"><s:text name="COM_SELECTEDHOTEL_LBL_VILLE"/> :</td>
								<td style="text-align: left"><s:property value="#session.hotel.city"/></td>
							</tr>
							<tr>
								<td style="font-weight: bold; text-align: right"><s:text name="COM_SELECTEDHOTEL_LBL_PAYS"/> :</td>
								<td style="text-align: left"><s:property value="#session.hotel.countryName"/></td>
							</tr>
						</table>
					</s:if>
				</fieldset>
				<br/>
				<br/>
			</div>
		</body>
	</html>
</s:else>