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
		return 1;
	}
    function changeValidite() {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "listSpecialRate.action";
        form.submit();
    }
    function addRate() {
        var form = document.getElementById("changeForm");
       form.target = "_self";
       form.action = "detailSpecialRate!showCreateNew.action";
       form.key.value = "";
       form.submit();
    }
    function exportRate() {
        var form = document.getElementById("changeForm");
        form.target = "_export";
        form.action = "listSpecialRate!export.action";
        form.submit();
    }
    function displayContracts(idGrille, codeRateLevel) {
        <s:url id="displayContractsURL" action="displayListContracts.action" includeParams="false"/>
        window.open("<s:property value="#displayContractsURL"/>?idGrille="+idGrille+"&codeRateLevel="+codeRateLevel,"lc","width=600,height=300,menubar=no, status=no");
    }
    function displayRate(key) {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailSpecialRate!showEdit.action";
        form.key.value = key;
        form.screenReadOnly.value = true;
        form.submit();
    }
    function editRate(key) {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailSpecialRate!showEdit.action";
        form.key.value = key;
        form.screenReadOnly.value = false;
        form.submit();
    }
    function duplicateRate(key) {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailSpecialRate!showDuplicate.action";
        form.key.value = key;
        form.screenReadOnly.value = false;
        form.submit();
    }
    function deleteRate(key) {
        if(confirm('<s:text name="COM_RAT_MSG_ALERTDELETE"/>')) {
            var form = document.getElementById("changeForm");
            form.target = "_self";
            form.action = "listSpecialRate!delete.action";
            form.key.value = key;
            form.submit();
        }
    }
</script>
<s:if test="rates.size > 0">
    <table class="List" style="width: 100%">
      <thead>
        <tr>
            <th><s:text name="SPE_LST_LBL_OFFRESPECIALE"/></th>
            <th><s:text name="SPE_LST_LBL_DE"/></th>
            <th><s:text name="SPE_LST_LBL_A"/></th>
            <th><s:text name="SPE_LST_LBL_OBLIGATOIRE"/></th>
            <th><s:text name="SPE_LST_LBL_PRIX"/></th>
            <th><s:text name="SPE_LST_LBL_TYPEPRIX"/></th>
            <th><s:text name="SPE_LST_LBL_UNITEPRIX"/></th>
            <th><s:text name="SPE_LST_LBL_ALLMARKETS"/></th>
            <th><s:text name="SPE_LST_LBL_PAYS"/></th>
            <th><s:text name="SPE_LST_LBL_CONTINENTS"/></th>
            <th colspan="3"></th>
        </tr>
      </thead>
      <tbody>
        <s:iterator value="rates" status="listStatus" id="rate">
					<s:if test="#listStatus.odd == true">
						<tr id="tr<s:property value="key"/>" class="Even">
					</s:if>
					<s:else>
						<tr id="tr<s:property value="key"/>" class="Odd">
					</s:else>
                <td><s:property value="offreSpeciale.libelle"/></td>
                <td align="center"><s:property value="#rate.dateDebut"/></td>
                <td align="center"><s:property value="#rate.dateFin"/></td>
                <td align="center">
                	<s:checkbox name="obligatoire" disabled="true" label="" theme="simple"></s:checkbox>
                </td>
                <td align="right"><s:property value="prixLabel"/></td>
                <td><s:property value="typePrix.libelle"/></td>
                <td><s:property value="unitePrix.libelle"/></td>
                <td align="center">
	                <s:checkbox name="tousMarches" disabled="true" label="" theme="simple"></s:checkbox>
                </td>
                <td><s:property value="paysNames"/></td>
                <td><s:property value="continentNames"/></td>
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