<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 6 oct. 2008
  Time: 12:13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<fieldset style="width: 100%">
    <legend><s:text name="COM_RAT_LBL_HOTELGRILLE"/></legend>
    <table width="100%">
        <tr>
            <td colspan="2" class="ScreenTitle" align="center">
                <s:text name="COM_RAT_LBL_TITLEBOD"/>
            </td>
        </tr>
        <tr>
            <td><s:text name="COM_RAT_LBL_HOTEL"/> :</td>
            <td class="Highlight1">
                <s:property value="grille.hotel.name"/>
                -
                (
                <s:property value="grille.hotel.code"/>
                )
            </td>
        </tr>
        <tr>
            <td><s:text name="COM_RAT_LBL_STATUT"/> :</td>
            <td class="Highlight1">
                <s:property value="grille.statutGrille.libelle"/>
            </td>
        </tr>
        <tr>
            <td><s:text name="COM_DET_LBL_PERIODEVALIDITE"/> :</td>
            <td class="Highlight1">
                <s:property value="grille.periodeValidite.libelle"/>
            </td>
        </tr>
    </table>
</fieldset>
