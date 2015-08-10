<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 22 mai 2008
  Time: 10:06:13
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
					<legend><s:text name="COM_LOGIN_LBL_TITRECHANGE"/></legend>
					<s:form validate="true"  theme="simple">
                        <s:hidden name="login" value="%{login}"/>
						<s:hidden name="codeLangue" value="%{codeLangue}"/>
                        <table>
                            <tr>
                                <td style="text-align: right"><s:text name="COM_LOGIN_LBL_OLDPASSWORD"/></td>
                                <td style="text-align: left"><s:password name="password" key="COM_LOGIN_LBL_OLDPASSWORD" maxLength="12" size="15"/></td>
                            </tr>
                            <tr>
                                <td style="text-align: right"><s:text name="COM_LOGIN_LBL_NEWPASSWORD"/></td>
                                <td style="text-align: left"><s:password name="newPassword" key="COM_LOGIN_LBL_NEWPASSWORD" maxLength="12" size="15"/></td>
                            </tr>
                            <tr>
                                <td style="text-align: right"><s:text name="COM_LOGIN_LBL_CONFIRMPASSWORD"/></td>
                                <td style="text-align: left"><s:password name="confirmPassword" key="COM_LOGIN_LBL_CONFIRMPASSWORD" maxLength="12" size="15"/></td>
                            </tr>
                            <tr>
                                <td style="text-align: center;" colspan="2">
                                    <s:submit key="COM_LOGIN_BTN_CHANGE" action="changePassword"  cssClass="Button"/>
                                </td>
                            </tr>
                        </table>
					</s:form>
					<s:url action="login.action" id="goLogin" escapeAmp="false"/>
					<a href="<s:property value="#goLogin"/>"><b><s:text name="COM_LOGIN_LBL_RETOURLOGIN"/></b></a>
					<br/><br/>
				</fieldset>
			</div>
			<br/><br/>
		</div>
	</body>
</html>