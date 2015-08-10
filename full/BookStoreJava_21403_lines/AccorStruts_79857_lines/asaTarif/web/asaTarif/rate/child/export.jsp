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
    <s:iterator value="enfantServices" status="esStatus">
    	<s:set name="serviceName" value="enfantService + #und +#esStatus.count"></s:set>
    	<s:set name="#serviceName" value="libelle"></s:set>
    	<s:set name="serviceID" value="enfantServiceID + #und +#esStatus.count"></s:set>
    	<s:set name="#serviceIDe" value="code"></s:set>
    </s:iterator>
    <table width="100%" class="ImprimList">
    	<thead>
        <tr>
            <th rowspan="2"><s:property value="getText('ENF_EXP_LBL_PRODUIT')"/></th>
            <th rowspan="2"><s:property value="getText('ENF_EXP_LBL_TARIF')"/></th>
            <th colspan="2"><s:property value="getText('ENF_EXP_LBL_PERIODES')"/></th>
            <th rowspan="2"><s:property value="getText('ENF_EXP_LBL_NB_MAX_CHILD')"/></th>
            <th rowspan="2"><s:property value="getText('ENF_EXP_LBL_NB_MAX_ADULT')"/></th>
            <th rowspan="2"><s:property value="getText('ENF_EXP_LBL_CHAMBRE_SEPARE')"/></th>
            <th rowspan="2"><s:property value="getText('ENF_EXP_LBL_DEVISE')"/></th>
            <th colspan="2"><s:property value="getText('ENF_EXP_LBL_AGE')"/></th>
            <th colspan="6"><s:property value="getText('ENF_EXP_LBL_SERVICES')"/></th>
        </tr>
        <tr>
            <th><s:property value="getText('ENF_EXP_LBL_DATE_FROM')"/></th>
            <th><s:property value="getText('ENF_EXP_LBL_DATE_TO')"/></th>
            <th><s:property value="getText('ENF_EXP_LBL_AGE_FROM')"/></th>
            <th><s:property value="getText('ENF_EXP_LBL_AGE_TO"/></th>
            <th><s:property value="getText('ENF_EXP_LBL_ACCOMMODATION')"/></th>
            <th><s:property value="getText('ENF_EXP_LBL_ACC_VALUE')"/></th>
            <th><s:property value="getText('ENF_EXP_LBL_BREAKFAST')"/></th>
            <th><s:property value="getText('ENF_EXP_LBL_BKF_VALUE')"/></th>
            <th><s:property value="getText('ENF_EXP_LBL_MEAL')"/></th>
            <th><s:property value="getText('ENF_EXP_LBL_MEAL_VALUE')"/></th>
        </tr>
      </thead>
        <tbody>
            <s:iterator value="rates" status="listStatus" id="rate">
            <tr id="tr<s:property value="key"/>" class="Even">
                <s:set name="n" value="services.size"/>
                <td rowspan='<s:property value="#n"/>'><s:property value="libProduit"/></td>
                <s:set name="n" value="services.size"/>
                <td rowspan='<s:property value="#n"/>'><s:property value="codeRateLevel"/></td>
                <s:set name="n" value="services.size"/>
                <td rowspan='<s:property value="#n"/>' align="center"><s:property value="#rate.dateDebut"/></td>
                <s:set name="n" value="services.size"/>
                <td rowspan='<s:property value="#n"/>' align="center"><s:property value="#rate.dateFin"/></td>
                <s:set name="n" value="services.size"/>
                <td rowspan='<s:property value="#n"/>'><s:property value="maxAdult"/></td>
                <s:set name="n" value="services.size"/>
                <td rowspan='<s:property value="#n"/>'><s:property value="maxChild"/></td>
                <s:set name="n" value="services.size"/>
                <td rowspan='<s:property value="#n"/>' align="center">
                    <s:if test="chambreSepare">
                        <s:text name="ALL_ALL_OUI"/>
                    </s:if>
                    <s:else>
                        <s:text name="ALL_ALL_NON"/>
                    </s:else>
                </td>
                <s:set name="n" value="services.size"/>
                <td rowspan='<s:property value="#n"/>'><s:property value="codeDevise"/></td>
                <s:iterator value="services" status="servicesStatus">
                    <s:if test="!#servicesStatus.first">
                        <tr class="Even">
                    </s:if>
                    <s:set name="serviceData" value="#rate.getService(#servicesStatus.index)"/>
                    <td align="right"><s:property value="#serviceData.minAge"/></td>
                    <td align="right"><s:property value="#serviceData.maxAge"/></td>
                    <td><s:property value="#serviceData.accomodationLabelTypePrix"/></td>
                    <td align="right"><s:property value="#serviceData.accomodationMontant"/></td>
                    <td><s:property value="#serviceData.breakfastLabelTypePrix"/></td>
                    <td align="right"><s:property value="#serviceData.breakfastMontant"/></td>
                    <td><s:property value="#serviceData.mealLabelTypePrix"/></td>
                    <td align="right"><s:property value="#serviceData.mealMontant"/></td>
                </tr>
                </s:iterator>
            </s:iterator>
        </tbody>

   </table>
</s:if>