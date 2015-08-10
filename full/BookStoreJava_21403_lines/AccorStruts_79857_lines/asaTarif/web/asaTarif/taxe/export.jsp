<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 11 sept. 2008
  Time: 14:50:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <tr><td colspan="10"><b><s:property value="getText('TAX_EXP_LBL_HOTEL')"/> :</b> <s:property value="session.hotel.name"/> - (<s:property value="session.hotel.code"/>)</td></tr>
        </table>
		<br/>
        <s:if test="taxes.size > 0">
            <table width="100%" class="ImprimList">
                <thead>
                    <tr>
                        <th rowspan="2"><s:property value="getText('TAX_EXP_LBL_TAXES')"/> </th>
                        <th colspan="2"><s:property value="getText('TAX_EXP_LBL_DATES')"/></th>
                        <th colspan="3"><s:property value="getText('TAX_EXP_LBL_AMOUNT')"/></th>
                        <th rowspan="2"><s:property value="getText('TAX_EXP_LBL_INLUDED')"/></th>
                        <th rowspan="2"><s:property value="getText('TAX_EXP_LBL_UNIT')"/></th>
                        <th rowspan="2"><s:property value="getText('TAX_EXP_LBL_RATELEVEL')"/></th>
                        <th rowspan="2"><s:property value="getText('TAX_EXP_LBL_INVALIDATION')"/></th>
                    </tr>
                    <tr>
                        <th><s:property value="getText('TAX_EXP_LBL_BEGINDATE')"/></th>
                        <th><s:property value="getText('TAX_EXP_LBL_ENDDATE')"/></th>
                        <th><s:property value="getText('TAX_EXP_LBL_TYPE')"/></th>
                        <th><s:property value="getText('TAX_EXP_LBL_VALUE')"/></th>
                        <th><s:property value="getText('TAX_EXP_LBL_CURRENCY')"/></th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="taxes" status="listStatus" id="r">
                        <s:if test="#listStatus.odd == true">
                            <tr id="tr<s:property value="idTaxe"/>" class="Even">
                        </s:if>
                        <s:else>
                            <tr id="tr<s:property value="idTaxe"/>" class="Odd">
                        </s:else>
                        <td align="left"><s:property value="nom"/></td>
                        <td><s:property value="#r.dateDebut"/></td>
                        <td><s:property value="#r.dateFin"/></td>
                        <td><s:property value="typePrix.libelle"/></td>
                        <td><s:property value="montant"/></td>
                        <td><s:property value="codeDevise"/></td>
                        <td>
                            <s:if test="inclus">
                                <s:text name="ALL_ALL_OUI"/>
                            </s:if>
                            <s:else>
                                <s:text name="ALL_ALL_NON"/>
                            </s:else>
                        </td>
                        <td><s:property value="uniteTaxe.libelle"/></td>
                        <td>
                            <s:property value="rateLevelsOnText"/>
                        </td>
                        <td>
                            <s:if test="supprime">
                                <s:text name="ALL_ALL_OUI"/>
                            </s:if>
                            <s:else>
                                <s:text name="ALL_ALL_NON"/>
                            </s:else>
                        </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
        </s:if>
	</body>
</html>