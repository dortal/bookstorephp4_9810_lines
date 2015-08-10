<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 10 sept. 2008
  Time: 12:26:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<script type="text/javascript" language="Javascript">
			var ODD_CLASSNAME = "Odd";
			var EVEN_CLASSNAME = "Even";
			var HIGHLIGHT_CLASSNAME = "HighlightRow";

            function getCurrentMenuIndex () {
                return 2;
            }
            function addTaxe() {
                var form = document.getElementById("changeForm");
                form.target = "_self";
                form.action = "detailTaxe!showCreateNew.action";
                form.idTaxe.value = "";
                form.submit();
            }
            function exportTaxe() {
                var form = document.getElementById("changeForm");
                form.target = "_export";
                form.action = "listTaxe!export.action";
                form.submit();
            }
            function displayTaxe(idTaxe) {
                var form = document.getElementById("changeForm");
                form.target = "_self";
                form.action = "detailTaxe!showEdit.action";
                form.idTaxe.value = idTaxe;
                form.screenReadOnly.value = true;
                form.submit();
            }
            function editTaxe(idTaxe) {
                var form = document.getElementById("changeForm");
                form.target = "_self";
                form.action = "detailTaxe!showEdit.action";
                form.idTaxe.value = idTaxe;
                form.screenReadOnly.value = false;
                form.submit();
            }
            function duplicateTaxe(idTaxe) {
                var form = document.getElementById("changeForm");
                form.target = "_self";
                form.action = "detailTaxe!showDuplicate.action";
                form.idTaxe.value = idTaxe;
                form.screenReadOnly.value = false;
                form.submit();
            }
            function invalidateTaxe(idTaxe) {
                var form = document.getElementById("changeForm");
                form.target = "_self";
                form.action = "detailTaxe!showInvalidate.action";
                form.idTaxe.value = idTaxe;
                form.screenReadOnly.value = false;
                form.submit();
            }
            function deleteTaxe(idTaxe) {
                if(confirm('<s:text name="COM_RAT_MSG_ALERTDELETE"/>')) {
                    var form = document.getElementById("changeForm");
                    form.target = "_self";
                    form.action = "listTaxe!delete.action";
                    form.idTaxe.value = idTaxe;
                    form.submit();
                }
            }
        </script>
    </head>
	<body>
		<s:include value="/head.jsp"/>
        <div id="mainDiv">
            <br>
            <s:include value="/taxe/infos.jsp"/>
            <br/>
            <s:form id="changeForm" action="listBusinessRate.action" method="GET">
                <s:hidden name="idTaxe"/>
                <s:hidden name="screenReadOnly"/>
            </s:form>
            <s:if test="taxes.size > 0">
                <table class="List" style="width: 100%">
                    <thead>
                        <tr>
                            <th rowspan="2"><s:text name="TAX_LST_LBL_TAXES"/> </th>
                            <th colspan="2"><s:text name="TAX_LST_LBL_DATES"/></th>
                            <th colspan="3"><s:text name="TAX_LST_LBL_AMOUNT"/></th>
                            <th rowspan="2"><s:text name="TAX_LST_LBL_INLUDED"/> </th>
                            <th rowspan="2"><s:text name="TAX_LST_LBL_UNIT"/></th>
                            <th rowspan="2"><s:text name="TAX_LST_LBL_RATELEVEL"/></th>
                            <th rowspan="2"><s:text name="TAX_LST_LBL_INVALIDATION"/></th>
                            <th colspan="4" rowspan="4"></th>
                        </tr>
                        <tr>
                            <th><s:text name="TAX_LST_LBL_BEGINDATE"/></th>
                            <th><s:text name="TAX_LST_LBL_ENDDATE"/></th>
                            <th><s:text name="TAX_LST_LBL_TYPE"/></th>
                            <th><s:text name="TAX_LST_LBL_VALUE"/></th>
                            <th><s:text name="TAX_LST_LBL_CURRENCY"/></th>
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
                            <td>
                                <s:if test="!screenReadOnly &&  !transferred">
                                    <s:url  value="images/picto_edit.jpg" id="img"/>
                                    <a href="javascript:editTaxe('<s:property value="idTaxe"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                                </s:if>
                                <s:else>
                                    <s:url  value="images/picto_detail.jpg" id="img"/>
                                    <a href="javascript:displayTaxe('<s:property value="idTaxe"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                                </s:else>
                            </td>
                            <td>
                                <s:if test="!screenReadOnly">
                                    <s:url  value="images/picto_duplicate.jpg" id="img"/>
                                    <a href="javascript:duplicateTaxe('<s:property value="idTaxe"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                                </s:if>
                            </td>
                            <td>
                                <s:if test="!screenReadOnly && transferred">
                                    <s:url  value="images/picto_invalide.jpg" id="img"/>
                                    <a href="javascript:invalidateTaxe('<s:property value="idTaxe"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                                </s:if>
                            </td>
                            <td>
                                <s:if test="!screenReadOnly && !transferred">
                                    <s:url  value="images/picto_delete.jpg" id="img"/>
                                    <a href="javascript:deleteTaxe('<s:property value="idTaxe"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                                </s:if>
                            </td>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
            </s:if>
            <br/>
            <div style="text-align: center">
                <s:if test="not screenReadOnly">
                    <input type="button" class="Button" id="addButton" value="<s:text name="ALL_ALL_ADD"/>" onclick="addTaxe();"/>
                </s:if>
                <s:if test="taxes.size > 0">
                    <input type="button" class="Button" id="exportButton" value="<s:text name="ALL_ALL_EXPORT"/>" onclick="exportTaxe();"/>
                </s:if>
            </div>
            <br/>
        </div>
	</body>
</html>            