<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 11 sept. 2008
  Time: 15:34:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<fieldset style="width: 600px">
    <legend><s:text name="COM_RAT_LBL_HOTELGRILLE"/> :</legend>
    <table width="100%">
        <tr>
            <td colspan="2" class="ScreenTitle" align="center">
                <s:text name="%{rateScrean.titleKey}"/>
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
        <s:if test="%{rateScrean.uniteKey neq ''}">
        <tr>
            <td><s:text name="COM_RAT_LBL_UNITE"/> :</td>
            <td class="Highlight1">
                <s:text name="%{rateScrean.uniteKey}"/>
            </td>
        </tr>
        </s:if>
    </table>
</fieldset>
