<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 19 nov. 2008
  Time: 14:34:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<link href="${pageContext.request.contextPath}/css/asaRate.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" language="Javascript">
			var ODD_CLASSNAME = "Odd";
			var EVEN_CLASSNAME = "Even";
			var HIGHLIGHT_CLASSNAME = "HighlightRow";
            function getCurrentMenuIndex () {
                return opener.getCurrentMenuIndex ();
            }
        </script>
	</head>
	<body>
        <div id="errorDiv">
            <s:actionmessage/>
            <s:actionerror/>
        </div>
        <fieldset style="width: 370px">
            <legend><s:text name="COM_HIST_LBL_HISTORIQUE"/> :</legend>
            <table width="100%">
                <tr>
                    <td><s:text name="COM_HIST_LBL_DATECREATION"/> :</td>
                    <td class="Highlight1"><s:property value="histo.grille.dateCreation"/></td>
                </tr>
                <tr>
                    <td><s:text name="COM_HIST_LBL_AUTEURCREATION"/> :</td>
                    <td class="Highlight1"><s:property value="histo.userCreation.label"/></td>
                </tr>
                <tr>
                    <td><s:text name="COM_HIST_LBL_DATEAJOUTCXX"/> :</td>
                    <td class="Highlight1"><s:property value="histo.grille.dateAddCXX"/></td>
                </tr>
                <tr>
                    <td><s:text name="COM_HIST_LBL_AUTEURAJOUTCXX"/> :</td>
                    <td class="Highlight1"><s:property value="histo.userAddCXX.label"/></td>
                </tr>
                <tr>
                    <td><s:text name="COM_HIST_LBL_DATELASTMODIF"/> :</td>
                    <td class="Highlight1"><s:property value="histo.grille.dateLastModif"/></td>
                </tr>
                <tr>
                    <td><s:text name="COM_HIST_LBL_AUTEURLASTMODIF"/> :</td>
                    <td class="Highlight1"><s:property value="histo.userLastModif.label"/></td>
                </tr>
            </table>
        </fieldset>
        <table>
            <tr>
                <td align="center">
                    <br>
                    <input type="button" class="Button" id="addButton" value="<s:text name="ALL_ALL_BACK"/>" onclick="window.close();"/>
                </td>
            </tr>
        </table>
    </body>
</html>
