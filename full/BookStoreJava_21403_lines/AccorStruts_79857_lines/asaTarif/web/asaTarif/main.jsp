<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 28 mai 2008
  Time: 08:50:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
	</head>
	<body>
        <table>
            <tr><td><br/><br/><br/></td></tr>
        </table>
        <s:if test="#session.contexte != null">
            <fieldset style="width:400px;">
                <legend><s:text name="COM_MAIN_LBL_LEGEND"/></legend>
                <table>
                    <tr>
                        <td style="font-weight: bold; text-align: right"><s:text name="COM_MAIN_LBL_LOGIN"/> :</td>
                        <td style="text-align: left"><s:property value="#session.contexte.utilisateurValue.login"/></td>
                    </tr>
                    <tr>
                        <td style="font-weight: bold; text-align: right"><s:text name="COM_MAIN_LBL_NOM"/> :</td>
                        <td style="text-align: left"><s:property value="#session.contexte.utilisateurValue.lastName"/></td>
                    </tr>
                    <tr>
                        <td style="font-weight: bold; text-align: right"><s:text name="COM_MAIN_LBL_PRENOM"/> :</td>
                        <td style="text-align: left"><s:property value="#session.contexte.utilisateurValue.firstName"/></td>
                    </tr>
                    <s:if test="#session.contexte.utilisateurValue.hotelId.length()>0">
                        <tr>
                            <td style="font-weight: bold; text-align: right"><s:text name="COM_MAIN_LBL_HOTEL"/> :</td>
                            <td style="text-align: left">
                                <s:property value="#session.contexte.utilisateurValue.hotelName"/> -
                                (<s:property value="#session.contexte.utilisateurValue.hotelId"/>)
                            </td>
                        </tr>
                    </s:if>
                    <tr><td colspan="2"><br/></td></tr>
                </table>
            </fieldset>
        </s:if>
	</body>
</html>