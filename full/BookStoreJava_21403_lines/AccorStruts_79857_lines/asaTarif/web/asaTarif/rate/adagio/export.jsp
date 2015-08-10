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
				<th rowspan="2"><s:property value="getText('ADG_EXP_LBL_PRODUCT')"/></th>
				<th rowspan="2"><s:property value="getText('ADG_EXP_LBL_RATE')"/></th>
				<th colspan="2"><s:property value="getText('ADG_EXP_LBL_PRIODES')"/></th>
				<th rowspan="2"><s:property value="getText('ADG_EXP_LBL_PERIODE_TYPE')"/></th>
				<th rowspan="2"><s:property value="getText('ADG_EXP_LBL_DUREE_SEJOUR')"/></th>
				<th colspan="6"><s:property value="getText('ADG_EXP_LBL_PRIX')"/></th>
				<th rowspan="2"><s:property value="getText('ADG_EXP_LBL_PERIODE_APPLICATION')"/></th>
				<th rowspan="2"><s:property value="getText('ADG_EXP_LBL_COMMISSION')"/></th>
				<th rowspan="2"><s:property value="getText('ADG_EXP_LBL_SALON_EVT')"/></th>
			</tr>
			<tr>
				<th><s:property value="getText('ADG_EXP_LBL_FROM')"/></th>
				<th><s:property value="getText('ADG_EXP_LBL_TO')"/></th>
				<th><s:property value="getText('ADG_EXP_LBL_1PAX')"/></th>
				<th><s:property value="getText('ADG_EXP_LBL_2PAX')"/></th>
				<th><s:property value="getText('ADG_EXP_LBL_3PAX')"/></th>
				<th><s:property value="getText('ADG_EXP_LBL_4PAX')"/></th>
				<th><s:property value="getText('ADG_EXP_LBL_5PAX')"/></th>
				<th><s:property value="getText('ADG_EXP_LBL_6PAX')"/></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="rates" status="listStatus">
				<s:if test="#listStatus.odd == true">
					<tr id="tr<s:property value="key"/>" class="Even">
				</s:if>
				<s:else>
					<tr id="tr<s:property value="key"/>" class="Odd">
				</s:else>
                
                <td><s:property value="libProduit"/></td>
                <td><s:property value="codeRateLevel"/></td>
                <td align="center"><s:property value="dateDebut"/></td>
                <td align="center"><s:property value="dateFin"/></td>
                <td><s:property value="periodeGenerique.libelle"/></td>
                <td><s:property value="dureeSejour.libelle"/></td>
                <td align="right" nowrap="nowrap"><s:property value="prix1"/> </td>
                <td align="right" nowrap="nowrap"><s:property value="prix2"/> </td>
                <td align="right" nowrap="nowrap"><s:property value="prix3"/> </td>
                <td align="right" nowrap="nowrap"><s:property value="prix4"/> </td>
                <td align="right" nowrap="nowrap"><s:property value="prix5"/> </td>
                <td align="right" nowrap="nowrap"><s:property value="prix6"/> </td>
                
                <td><s:property value="divisionSemaine.libelle"/></td>
                <td align="right" nowrap="nowrap">
                        <s:property value="commission"/> 
                </td>
                    <td><s:property value="libelleSalon"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if>