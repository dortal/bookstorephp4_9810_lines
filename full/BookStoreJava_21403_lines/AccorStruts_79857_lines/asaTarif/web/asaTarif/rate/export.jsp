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
        <style type="text/css">
            <s:include value="../css/asaRate.css"/>
        </style>
    </head>
	<body class="Imprimable">
        <table>
            <tr><td colspan="10"><b><s:property value="getText('COM_EXP_LBL_HOTEL')"/> :</b> <s:property value="session.hotel.name"/> - (<s:property value="session.hotel.code"/>)</td></tr>
            <tr><td colspan="10"><b><s:property value="getText('COM_EXP_LBL_PERIODEVALIDITE')"/> :</b> <s:property value="grille.periodeValidite.libelle"/></td></tr>
            <tr><td colspan="10"><b><s:property value="getText('COM_EXP_LBL_FAMILLETARIF')"/> :</b> <s:property value="grille.familleTarif.libelle"/></td></tr>
            <tr><td colspan="10"><b><s:property value="getText('COM_EXP_LBL_STATUT')"/> :</b> <s:property value="grille.statutGrille.libelle"/></td></tr>
        </table>
		<br/>
		<s:set name="urlList" value="%{'/rate/'+pagesPath+'/export.jsp'}"/>
		<s:include value="%{#urlList}"/>
	</body>
</html>