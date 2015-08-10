<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 20 mai 2008
  Time: 10:59:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" language="Javascript">
    function getCurrentMenuIndex() {
        return 1;
    }
    function changeValidite() {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "listChildRate.action";
        form.submit();
    }
    function addRate() {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailChildRate!showCreateNew.action";
        form.key.value = "";
        form.submit();
    }
    function exportRate() {
        var form = document.getElementById("changeForm");
        form.target = "_export";
        form.action = "listChildRate!export.action";
        form.submit();
    }
    function displayContracts(idGrille, codeRateLevel) {
    <s:url id="displayContractsURL" action="displayListContracts.action" includeParams="false"/>
        window.open("<s:property value="#displayContractsURL"/>?idGrille=" + idGrille + "&codeRateLevel=" + codeRateLevel, "lc", "width=600,height=300,menubar=no, status=no");
    }
    function displayRate(key) {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailChildRate!showEdit.action";
        form.key.value = key;
        form.screenReadOnly.value = true;
        form.submit();
    }
    function editRate(key) {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailChildRate!showEdit.action";
        form.key.value = key;
        form.screenReadOnly.value = false;
        form.submit();
    }
    function duplicateRate(key) {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailChildRate!showDuplicate.action";
        form.key.value = key;
        form.screenReadOnly.value = false;
        form.submit();
    }
    function deleteRate(key) {
        if(confirm('<s:text name="COM_RAT_MSG_ALERTDELETE"/>')) {
            var form = document.getElementById("changeForm");
            form.target = "_self";
            form.action = "listChildRate!delete.action";
            form.key.value = key;
            form.submit();
        }
    }
</script>
<s:if test="rates.size > 0">
<s:iterator value="enfantServices" status="esStatus">
    <s:set name="serviceName" value="enfantService + #und +#esStatus.count"></s:set>
    <s:set name="#serviceName" value="libelle"></s:set>
    <s:set name="serviceID" value="enfantServiceID + #und +#esStatus.count"></s:set>
    <s:set name="#serviceIDe" value="code"></s:set>
</s:iterator>
<table class="List" style="width: 100%">
    <thead>
        <tr>
            <th rowspan="2"><s:text name="ENF_LST_LBL_PRODUIT"/></th>
            <th rowspan="2"><s:text name="ENF_LST_LBL_TARIF"/></th>
            <th colspan="2"><s:text name="ENF_LST_LBL_PERIODES"/></th>
            <th rowspan="2"><s:text name="ENF_LST_LBL_NB_MAX_CHILD"/></th>
            <th rowspan="2"><s:text name="ENF_LST_LBL_NB_MAX_ADULT"/></th>
            <th rowspan="2"><s:text name="ENF_LST_LBL_CHAMBRE_SEPARE"/></th>
            <th rowspan="2"><s:text name="ENF_LST_LBL_DEVISE"/></th>
            <th colspan="2"><s:text name="ENF_LST_LBL_AGE"/></th>
            <th colspan="6"><s:text name="ENF_LST_LBL_SERVICES"/></th>
            <th colspan="3" rowspan="2"></th>
        </tr>
        <tr>
            <th><s:text name="ENF_LST_LBL_DATE_FROM"/></th>
            <th><s:text name="ENF_LST_LBL_DATE_TO"/></th>
            <th><s:text name="ENF_LST_LBL_AGE_FROM"/></th>
            <th><s:text name="ENF_LST_LBL_AGE_TO"/></th>
            <th><s:text name="ENF_LST_LBL_ACCOMMODATION"/></th>
            <th><s:text name="ENF_LST_LBL_ACC_VALUE"/></th>
            <th><s:text name="ENF_LST_LBL_BREAKFAST"/></th>
            <th><s:text name="ENF_LST_LBL_BKF_VALUE"/></th>
            <th><s:text name="ENF_LST_LBL_MEAL"/></th>
            <th><s:text name="ENF_LST_LBL_MEAL_VALUE"/></th>
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
            <td rowspan='<s:property value="#n"/>' align="center"><s:checkbox name="chambreSepare" cssClass="formInput" theme="simple" label="Chambre separe"disabled="true"/></td>
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
                <s:if test="#servicesStatus.first">
                    <s:set name="n" value="services.size"/>
                    <td rowspan='<s:property value="#n"/>'>
                        <s:if test="not rowReadOnly">
                            <s:url  value="images/picto_edit.jpg" id="img"/>
                            <a href="javascript:editRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                        </s:if>
                        <s:else>
                            <s:url  value="images/picto_detail.jpg" id="img"/>
                            <a href="javascript:displayRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                        </s:else>
                    </td>
                    <s:set name="n" value="services.size"/>
                    <td rowspan='<s:property value="#n"/>'>
                        <s:if test="not rowReadOnly">
                            <s:url  value="images/picto_duplicate.jpg" id="img"/>
                            <a href="javascript:duplicateRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                        </s:if>
                    </td>
                    <s:set name="n" value="services.size"/>
                    <td rowspan='<s:property value="#n"/>'>
                        <s:if test="not rowReadOnly">
                            <s:url  value="images/picto_delete.jpg" id="img"/>
                            <a href="javascript:deleteRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                        </s:if>
                    </td>
                </s:if>
            </tr>
            </s:iterator>
        </s:iterator>
    </tbody>
</table>
</s:if>