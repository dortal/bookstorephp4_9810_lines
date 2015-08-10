<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 11 sept. 2008
  Time: 15:34:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<fieldset style="width: 600px">
    <legend><s:text name="TAX_INF_LBL_TITLE"/> :</legend>
    <table width="100%">
        <tr>
            <td colspan="2" class="ScreenTitle" align="center">
                <s:text name="COM_RAT_LBL_TITLETAXESERVICE"/>
            </td>
        </tr>
        <tr>
            <td><s:text name="TAX_INF_LBL_HOTEL"/> :</td>
            <td class="Highlight1">
                <s:property value="session.hotel.name"/>
                -
                (
                <s:property value="session.hotel.code"/>
                )
            </td>
        </tr>
    </table>
</fieldset>
