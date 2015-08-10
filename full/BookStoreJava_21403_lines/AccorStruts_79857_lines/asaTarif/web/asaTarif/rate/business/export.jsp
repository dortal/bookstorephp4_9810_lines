<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="rates.size > 0">
    <table width="100%" class="ImprimList">
      <thead>
        <tr>
            <th rowspan="2"><s:property value="getText('BUS_EXP_LBL_PRODUIT')"/></th>
            <th rowspan="2"><s:property value="getText('BUS_EXP_LBL_TARIF')"/></th>
            <th colspan="2"><s:property value="getText('BUS_EXP_LBL_PERIODES')"/></th>
            <th rowspan="2"><s:property value="getText('BUS_EXP_LBL_TYPEPERIODE')"/></th>
            <th colspan="2"><s:property value="getText('BUS_EXP_LBL_PRIX')"/></th>
            <th rowspan="2"><s:property value="getText('BUS_EXP_LBL_PERIODEAPPLICATION')"/></th>
            <th rowspan="2"><s:property value="getText('BUS_EXP_LBL_COMMISSION')"/></th>
            <th colspan="3"><s:property value="getText('BUS_EXP_LBL_PETITDEJEUNER')"/></th>
            <th rowspan="2"><s:property value="getText('BUS_EXP_LBL_LIBELLESALON')"/></th>
            <th rowspan="2"><s:property value="getText('BUS_EXP_LBL_DUREESEJOUR')"/></th>
            <s:if test="%{companiesRates}">
                <th rowspan="2"><s:property value="getText('BUS_EXP_LBL_BLACKOUTDATES')"/></th>
                <th rowspan="2"><s:property value="getText('BUS_EXP_LBL_OPENNEWCONTRACT')"/></th>
                <th rowspan="2"><s:property value="getText('BUS_EXP_LBL_NBRENUITIEES')"/></th>
            </s:if>
            <th rowspan="2"><s:property value="getText('BUS_EXP_LBL_WEEKEND')"/></th>
        </tr>
        <tr>
            <th><s:property value="getText('BUS_EXP_LBL_DU')"/></th>
            <th><s:property value="getText('BUS_EXP_LBL_AU')"/></th>
            <th><s:property value="getText('BUS_EXP_LBL_1PAX')"/></th>
            <th><s:property value="getText('BUS_EXP_LBL_2PAX')"/></th>
            <th><s:property value="getText('BUS_EXP_LBL_PRIXPETITDEJEUNER')"/></th>
            <th><s:property value="getText('BUS_EXP_LBL_TYPEPETITDEJEUNER')"/></th>
            <th><s:property value="getText('BUS_EXP_LBL_INCLUS')"/></th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="rates" status="listStatus">
          <s:set name="ds" scope="page" value=""></s:set>
            <tr id="tr<s:property value="key"/>">
                <td><s:property value="libProduit"/></td>
                <td><s:property value="codeRateLevel"/></td>
                <td align="center"><s:property value="dateDebut"/></td>
                <td align="center"><s:property value="dateFin"/></td>
                <td><s:property value="periodeGenerique.libelle"/></td>
                <td align="right">
                  <s:property value="prix1"/>
                </td>
                <td align="right">
                        <s:property value="prix2"/>
                </td>
                <td><s:property value="divisionSemaine.libelle"/></td>
                <td align="right">
                        <s:property value="commission"/>
                </td>
                <td align="right"><s:property value="prixPdj"/></td>
                <td><s:property value="petitDejeuner.libelle"/></td>
                <td align="center" valign="center">
                    <s:if test="mealPlan.pdjInclu">
                        <s:text name="ALL_ALL_OUI"/>
                    </s:if>
                    <s:else>
                        <s:text name="ALL_ALL_NON"/>
                    </s:else>
                </td>
                <td><s:property value="libelleSalon"/></td>
                <td><s:property value="dureeSejour.libelle"/></td>
                <s:if test="%{companiesRates}">
                    <td align="center">
                        <s:if test="blackOutDates">
                            <s:text name="ALL_ALL_OUI"/>
                        </s:if>
                        <s:else>
                            <s:text name="ALL_ALL_NON"/>
                        </s:else>
                    </td>
                    <td align="center">
                        <s:if test="openNewContrat">
                            <s:text name="ALL_ALL_OUI"/>
                        </s:if>
                        <s:else>
                            <s:text name="ALL_ALL_NON"/>
                        </s:else>
                    </td>
                    <td align="center"><s:property value="nbreNuitsMin"/> -- <s:property value="nbreNuitsMax"/></td>
                </s:if>
                <td>
                  <s:if test="weekAndWeekEnd">
                      <s:property value="weekendDays"/>
                  </s:if>
                </td>
            </tr>
        </s:iterator>
      </tbody>
   </table>
</s:if>