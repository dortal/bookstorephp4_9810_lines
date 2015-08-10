<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 20 mai 2008
  Time: 10:59:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="rates.size > 0">
    <table width="100%" class="ImprimList">
      <thead>
        <tr>
            <th><s:property value="getText('SPE_EXP_LBL_OFFRESPECIALE')"/></th>
            <th><s:property value="getText('SPE_EXP_LBL_DE')"/></th>
            <th><s:property value="getText('SPE_EXP_LBL_A')"/></th>
            <th><s:property value="getText('SPE_EXP_LBL_OBLIGATOIRE')"/></th>
            <th><s:property value="getText('SPE_EXP_LBL_PRIX')"/></th>
            <th><s:property value="getText('SPE_EXP_LBL_TYPEPRIX')"/></th>
            <th><s:property value="getText('SPE_EXP_LBL_UNITEPRIX')"/></th>
            <th><s:property value="getText('SPE_EXP_LBL_ALLMARKETS')"/></th>
            <th><s:property value="getText('SPE_EXP_LBL_PAYS')"/></th>
            <th><s:property value="getText('SPE_EXP_LBL_CONTINENTS')"/></th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="rates" status="listStatus">
            <tr id="tr<s:property value="key"/>">
                <td><s:property value="offreSpeciale.libelle"/></td>
                <td align="center"><s:property value="dateDebut"/></td>
                <td align="center"><s:property value="dateFin"/></td>
                <td align="center">
                    <s:if test="obligatoire">
                        <s:text name="ALL_ALL_OUI"/>
                    </s:if>
                    <s:else>
                        <s:text name="ALL_ALL_NON"/>
                    </s:else>
                </td>
                <td align="right"><s:property value="prixLabel"/></td>
                <td><s:property value="typePrix.libelle"/></td>
                <td><s:property value="unitePrix.libelle"/></td>
                <td align="center">
                    <s:if test="tousMarches">
                        <s:text name="ALL_ALL_OUI"/>
                    </s:if>
                    <s:else>
                        <s:text name="ALL_ALL_NON"/>
                    </s:else>
                </td>
                <td><s:property value="paysNames"/></td>
                <td><s:property value="continentNames"/></td>
            </tr>
        </s:iterator>
      </tbody>
   </table>
</s:if>