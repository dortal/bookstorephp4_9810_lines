
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<s:if test="rates.size > 0">
    <table width="100%" class="ImprimList">
      <thead>
        <tr>
            <th rowspan="2"><s:property value="getText('LSR_EXP_LBL_PRODUIT')"/></th>
            <th rowspan="2"><s:property value="getText('LSR_EXP_LBL_TARIF')"/></th>
            <th colspan="2"><s:property value="getText('LSR_EXP_LBL_PERIODES')"/></th>
            <th rowspan="2"><s:property value="getText('LSR_EXP_LBL_TYPEPERIODE')"/></th>
            <th rowspan="2"><s:property value="getText('LSR_EXP_LBL_LIBELLESALON')"/></th>
            <th colspan="3"><s:property value="getText('LSR_EXP_LBL_PRIX')"/></th>
            <th rowspan="2"><s:property value="getText('LSR_EXP_LBL_TYPE_REPAS')"/></th>
            <th rowspan="2"><s:property value="getText('LSR_EXP_LBL_COMMISSION')"/></th>
            <th colspan="2"><s:property value="getText('LSR_EXP_LBL_PETITDEJEUNER')"/></th>
            <th colspan="2"><s:property value="getText('LSR_EXP_LBL_SUPPLEMENTS')"/></th>
            <th rowspan="2"><s:property value="getText('LSR_EXP_LBL_PERIODE_APPLICATION')"/></th>
            <th rowspan="2"><s:property value="getText('LSR_EXP_LBL_WEEKEND')"/></th>
        </tr>
        <tr>
            <th><s:property value="getText('LSR_EXP_LBL_DU')"/></th>
            <th><s:property value="getText('LSR_EXP_LBL_AU')"/></th>
            <th><s:property value="getText('LSR_EXP_LBL_PRIX_1DBL')"/></th>
            <th><s:property value="getText('LSR_EXP_LBL_PRIX_SUPPSGL')"/></th>
            <th><s:property value="getText('LSR_EXP_LBL_PRIX_SUPPTRP')"/></th>
            <th><s:property value="getText('LSR_EXP_LBL_PRIXPETITDEJEUNER')"/></th>
            <th><s:property value="getText('LSR_EXP_LBL_INCLUS')"/></th>
            <th><s:property value="getText('LSR_EXP_LBL_SUPP_DEMPENS')"/></th>
            <th><s:property value="getText('LSR_EXP_LBL_SUPP_PENS')"/></th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="rates" status="listStatus">
            <tr id="tr<s:property value="key"/>">
                 <td><s:property value="libProduit"/></td>
                <td><s:property value="codeRateLevel"/></td>
                <td align="center"><s:property value="dateDebut"/></td>
                <td align="center"><s:property value="dateFin"/></td>
                <td><s:property value="periodeGenerique.getLibelle()"/></td>
                <td><s:property value="libelleSalon"/></td>
                <td align="right"><s:property value="prixLabel"/></td>
                <td align="right"><s:property value="prixSupSGLLabel"/></td>
                <td align="right"><s:property value="prixSupTRILabel"/></td>
                <td align="center"><s:property value="codeMealPlan"/></td>
                <td align="right"><s:property value="commission"/></td>
                <td align="right"><s:property value="prixPdj"/></td>
                <td align="center">
                    <s:if test="pdjIncluded">
                        <s:text name="ALL_ALL_OUI"/>
                    </s:if>
                    <s:else>
                        <s:text name="ALL_ALL_NON"/>
                    </s:else>
				</td>
                <td><s:property value="supplDemPens"/></td>
                <td><s:property value="supplPensCompl"/></td>
                <td><s:property value="divisionSemaine.libelle"/></td>
                <td>
                    <s:if test="weekAndWeekEnd">
                        <s:property value="weekendDays"/></td>
                    </s:if>
            </tr>
        </s:iterator>
      </tbody>
   </table>
</s:if>