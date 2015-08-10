<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 15 mai 2008
  Time: 17:08:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title><s:text name="COM_HEAD_LBL_TITRE"/></title>
	</head>
	<body>
		<s:include value="/head.jsp"/>
		<div id="mainDiv" class="DefaultStyle">
			<br/><br/>
			<div id="loginDiv">
				<div id="errorDiv">
					<s:actionmessage/>
					<s:actionerror/>
					<s:fielderror/>
				</div>
				<fieldset>
					<legend><s:text name="COM_LOGIN_LBL_TITRE"/></legend>
					<s:form id="login" validate="true" theme="simple">
                        <table>
                            <tr>
                                <td style="text-align: right"><s:text name="COM_LOGIN_LBL_LOGIN"/></td>
                                <td style="text-align: left"><s:textfield name="login" key="COM_LOGIN_LBL_LOGIN" maxlength="15" size="15" value="%{login}"/></td>
                            </tr>
                            <tr>
                                <td style="text-align: right"><s:text name="COM_LOGIN_LBL_PASSWORD"/></td>
                                <td style="text-align: left"><s:password name="password" key="COM_LOGIN_LBL_PASSWORD" maxlength="12" size="15"/></td>
                            </tr>
                            <tr>
                                <td style="text-align: right"><s:text name="COM_LOGIN_LBL_LANGUE"/></td>
                                <td style="text-align: left"><s:select list="#session.langues" listKey="code" listValue="libelle" name="codeLangue" key="COM_LOGIN_LBL_LANGUE" value="%{codeLangue}" emptyOption="true"/></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <s:submit name="connexion"  key="COM_LOGIN_BTN_CONNEXION" action="login" method="connexion" cssClass="Button"/>
                                    <s:submit name="changePassword" key="COM_LOGIN_BTN_CHANGE" action="login" method="goChangePassword" cssClass="Button"/>
                                </td>
                            </tr>
                        </table>
					</s:form>
				</fieldset>
			</div>
			<br/><br/>
		</div>
	</body>
</html>