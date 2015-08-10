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
        form.action = "listBusinessRate.action";
        form.submit();
    }
    function addRate() {
     	var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailBusinessRate!showCreateNew.action";
        form.key.value = "";
        form.submit();
    }
    function exportRate() {
        var form = document.getElementById("changeForm");
        form.target = "_export";
        form.action = "listBusinessRate!export.action";
        form.submit();
    }
    function displayContracts(idGrille, codeRateLevel) {
        <s:url id="displayContractsURL" action="displayListContracts.action" includeParams="false"/>
        window.open("<s:property value="#displayContractsURL"/>?idGrille="+idGrille+"&codeRateLevel="+codeRateLevel,"lc","width=600,height=300,menubar=no, status=no");
    }
    function displayRate(key) {
    	var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailBusinessRate!showEdit.action";
        form.key.value = key;
        form.screenReadOnly.value = true;
        form.submit();
    }
    function editRate(key) {
    	var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailBusinessRate!showEdit.action";
        form.key.value = key;
        form.screenReadOnly.value = false;
        form.submit();
    }
    function duplicateRate(key) {
        var form = document.getElementById("changeForm");
        form.target = "_self";
        form.action = "detailBusinessRate!showDuplicate.action";
        form.key.value = key;
        form.screenReadOnly.value = false;
        form.submit();

    }
    function deleteRate(key) {
        if(confirm('<s:text name="COM_RAT_MSG_ALERTDELETE"/>')) {
            var form = document.getElementById("changeForm");
            form.target = "_self";
            form.action = "listBusinessRate!delete.action";
            form.key.value = key;
            form.submit();
        }
    }
    function displayHistorique(idGrille) {
        <s:url id="histo" action="histoGrille.action" includeParams="false"/>
        window.open("<s:property value="#histo"/>?idGrille="+idGrille,"HISTO","width=400,height=200,menubar=no, status=no");
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
		<s:submit key="BUS_LST_LBL_BTNIMPORT" value="Import" cssClass="Button"></s:submit>
	</s:form>
	<p>
</s:if>
<s:if test="grille.idGrille!=null">
    <table>
        <tr>
            <td align="center">
                <s:text name="COM_HIST_LBL_HISTORIQUE"/>:
                <s:url  value="images/pictosearch.gif" id="img"/>
                <a href="javascript:displayHistorique(<s:property value="grille.idGrille"/>)"><img src="<s:property value= "#img"/>" border="0"></a>
            </td>
        </tr>
    </table>
</s:if>
<s:if test="rates.size > 0">
	<table class="List" style="width: 100%">
		<thead>
			<tr>
				<th rowspan="2"></th>
				<th rowspan="2"><s:text name="BUS_LST_LBL_PRODUIT"/></th>
				<th rowspan="2"><s:text name="BUS_LST_LBL_TARIF"/></th>
				<th colspan="2"><s:text name="BUS_LST_LBL_PERIODES"/></th>
				<th rowspan="2"><s:text name="BUS_LST_LBL_TYPEPERIODE"/></th>
				<th colspan="2"><s:text name="BUS_LST_LBL_PRIX"/></th>
				<th rowspan="2"><s:text name="BUS_LST_LBL_PERIODEAPPLICATION"/></th>
				<th rowspan="2"><s:text name="BUS_LST_LBL_COMMISSION"/></th>
				<th colspan="2"><s:text name="BUS_LST_LBL_PETITDEJEUNER"/></th>
				<th rowspan="2"><s:text name="BUS_LST_LBL_LIBELLESALON"/></th>
                <th colspan="3" rowspan="2"></th>
            </tr>
			<tr>
				<th><s:text name="BUS_LST_LBL_DU"/></th>
				<th><s:text name="BUS_LST_LBL_AU"/></th>
				<th><s:text name="BUS_LST_LBL_1PAX"/></th>
				<th><s:text name="BUS_LST_LBL_2PAX"/></th>
				<th><s:text name="BUS_LST_LBL_PRIXPETITDEJEUNER"/></th>
				<th><s:text name="BUS_LST_LBL_INCLUS"/></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="rates" status="listStatus" id="r">
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
                <td align="center"><s:property value="#r.dateDebut"/></td>
                <td align="center"><s:property value="#r.dateFin"/></td>
                <td><s:property value="periodeGenerique.libelle"/></td>
                <td align="right"><s:property value="prix1"/> </td>
                <td align="right">
                        <s:property value="prix2"/>
                </td>
                <td><s:property value="divisionSemaine.libelle"/></td>
                <td align="right">
                        <s:property value="commission"/> 
                </td>
                <td align="right"><s:property value="prixPdj"/></td>
                <td align="center"><s:checkbox name="pdjInclus" label="" cssClass="formInput" theme="simple" disabled="true"/></td>
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