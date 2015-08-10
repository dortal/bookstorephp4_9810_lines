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
	function getCurrentMenuIndex () {
		return 0;
	}
    function changeValidite() {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "listAdagioBusinessRate.action";
        form.submit();
    }
    function addRate() {
    	var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailAdagioBusinessRate!showCreateNew.action";
        form.key.value = "";
        form.submit();
    }
    function exportRate() {
        var form = document.getElementById("changeForm");
        form.target = "_export";
        form.action = "listAdagioBusinessRate!export.action";
        form.submit();
    }
    function displayContracts(idGrille, codeRateLevel) {
        <s:url id="displayContractsURL" action="displayListContracts.action" includeParams="false"/>
        window.open("<s:property value="#displayContractsURL"/>?idGrille="+idGrille+"&codeRateLevel="+codeRateLevel,"lc","width=600,height=300,menubar=no, status=no");
    }
    function displayRate(key) {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailAdagioBusinessRate!showEdit.action";
        form.key.value = key;
        form.screenReadOnly.value = true;
        form.submit();
    }
    function editRate(key) {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailAdagioBusinessRate!showEdit.action";
        form.key.value = key;
        form.screenReadOnly.value = false;
        form.submit();
    }
    function duplicateRate(key) {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailAdagioBusinessRate!showDuplicate.action";
        form.key.value = key;
        form.screenReadOnly.value = false;
        form.submit();
    }
    function deleteRate(key) {
        if(confirm('<s:text name="COM_RAT_MSG_ALERTDELETE"/>')) {
            var form = document.getElementById("changeForm");
            form.target = "_self";
            form.action = "listAdagioBusinessRate!delete.action";
            form.key.value = key;
            form.submit();
        }
    }
</script>
<s:if test="importAvailable">
	<p>&nbsp;
	<hr>
	<p>
	<s:text name="BUS_LST_LBL_LBLIMPORT"/>
	<s:form action="importOldGrilleData.action">
		<s:hidden name="codeEcran"/>
		<s:hidden name="idPeriodeValidite"/>
		<s:hidden name="rateKey" value="Business"></s:hidden>
		<s:submit key="ADG_LST_LBL_BTNIMPORT" value="Import" cssClass="Button"></s:submit>
	</s:form>
	<p>
</s:if>
<s:if test="rates.size > 0">
	<table class="List" style="width: 100%">
		<thead>
			<tr>
				<th rowspan="2"></th>
				<th rowspan="2"><s:text name="ADG_LST_LBL_PRODUCT"/></th>
				<th rowspan="2"><s:text name="ADG_LST_LBL_RATE"/></th>
				<th colspan="2"><s:text name="ADG_LST_LBL_PRIODES"/></th>
				<th rowspan="2"><s:text name="ADG_LST_LBL_PERIODE_TYPE"/></th>
				<th rowspan="2"><s:text name="ADG_LST_LBL_DUREE_SEJOUR"/></th>
				<th colspan="4"><s:text name="ADG_LST_LBL_PRIX"/></th>
				<th rowspan="2"><s:text name="ADG_LST_LBL_PERIODE_APPLICATION"/></th>
				<th rowspan="2"><s:text name="ADG_LST_LBL_COMMISSION"/></th>
				<th rowspan="2"><s:text name="ADG_LST_LBL_SALON_EVT"/></th>
                <th colspan="3" rowspan="2"></th>
            </tr>
			<tr>
				<th><s:text name="ADG_LST_LBL_FROM"/></th>
				<th><s:text name="ADG_LST_LBL_TO"/></th>
				<th><s:text name="ADG_LST_LBL_1PAX"/></th>
				<th><s:text name="ADG_LST_LBL_2PAX"/></th>
				<th><s:text name="ADG_LST_LBL_3PAX"/></th>
				<th><s:text name="ADG_LST_LBL_4PAX"/></th>
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
                 <td align="center">
                	<s:if test="%{withContracts}">
                		<a href="javascript:displayContracts(<s:property value="idGrille"/>,'<s:property value="codeRateLevel"/>')">
                			<s:url  value="images/pictosearch.gif" id="img"/>
                			<img src="<s:property value= "#img"/>" border="0">
                		</a>
                	</s:if>
                </td>
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
                
                <td><s:property value="divisionSemaine.libelle"/></td>
                <td align="right" nowrap="nowrap"><s:property value="commission"/></td>
                <td><s:property value="libelleSalon"/></td>
                <td>
                    <s:if test="not rowReadOnly">
                        <s:url  value="images/picto_edit.jpg" id="img"/>
                        <a href="javascript:editRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                    </s:if>
                    <s:else>
                        <s:url  value="images/picto_detail.jpg" id="img"/>
                        <a href="javascript:displayRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                    </s:else>
                </td>
                <td>
                    <s:if test="not rowReadOnly">
                        <s:url  value="images/picto_duplicate.jpg" id="img"/>
                        <a href="javascript:duplicateRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                    </s:if>
                </td>
                <td>
                    <s:if test="not rowReadOnly">
                        <s:url  value="images/picto_delete.jpg" id="img"/>
                        <a href="javascript:deleteRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                    </s:if>
                </td>
                </tr>
            </s:iterator>
		</tbody>
	</table>
</s:if>