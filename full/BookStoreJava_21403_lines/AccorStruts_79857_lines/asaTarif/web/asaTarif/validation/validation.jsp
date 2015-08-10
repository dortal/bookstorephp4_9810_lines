<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 16 sept. 2008
  Time: 15:59:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/utils.js"></script>
		<script type="text/javascript">
			function getCurrentMenuIndex () {
				return 4;
			}
            function onChangeGroupeTarif() {
                var form = document.getElementById("detailForm");
                form.action = "validationRates.action";
                form.target = "_self";
                form.submit();
            }
            function onSearch() {
				var form = document.getElementById("detailForm");
                form.action = "validationRates!search.action";
                form.target = "_self";
                form.submit();
			}
			function onClear() {
				var form = document.getElementById("detailForm");
				cleanForm(form);
			}
            function  onSave () {
                var form = document.getElementById("detailForm");
                form.action = "validationRates!save.action";
                form.target = "_self";
                form.submit();
            }
            var isAllSelected = false;
            function selectAll() {
                var tabInputs = document.getElementsByName('codeHotels');
                if (tabInputs!=null) {
                    for (var i = 0; i < tabInputs.length; i ++)
                        tabInputs[i].checked = !isAllSelected;
                    var boutonAllSelected = document.getElementById("selectButton");
                    if (isAllSelected)
                        boutonAllSelected.value = '<s:text name="ALL_ALL_SELECTALL"/>';
                    else
                        boutonAllSelected.value = '<s:text name="ALL_ALL_DESELECTALL"/>';
                    isAllSelected = !isAllSelected;
                }
            }
            function getSelectedHotels() {
                var s = '';
                var tabInputs = document.getElementsByName('codeHotels');
                for (var i = 0; i < tabInputs.length; i ++)
                    if(tabInputs[i].checked)
                        s += (s=='')?tabInputs[i].value:','+tabInputs[i].value;
                return s;
            }
            function  showAllRates() {
                var codeHotels = getSelectedHotels();
                if (codeHotels!='') {
                    var form = document.getElementById("detailForm");
                    form.action = "validationRates!showAllRates.action";
                    form.target = "_export";
                    form.submit();
                } else {
                    alert("<s:text name="VAL_VAL_MSG_SELECTHOTEL"/>");
                }
            }
        </script>
	</head>
	<body>
		<s:include value="/head.jsp"/>
		<div id="mainDiv">
            <s:form id="detailForm" theme="simple">
            <table>
                <tr><td class="ScreenTitle"><s:text name="COM_RAT_LBL_TARIFFVALIDATION"/></td></tr>
            </table>
            <fieldset id="hotelFieldset">
				<legend id="hotelLegend"><s:text name="VAL_VAL_LBL_TITRE"/></legend>
                    <div id="errorDiv">
                        <s:actionmessage/>
                        <s:actionerror/>
                        <s:fielderror/>
                    </div>
                    <table style="width: 100%">
                        <tr>
                            <td style="text-align: right"><s:text name="VAL_VAL_LBL_GROUPETARIFS"/>:</td>
                            <td style="text-align: left">
                                <s:select list="listsProvider.groupesTarif" listKey="code" listValue="libelle"
                                          name="idGroupeTarif" label="Groupe tarif" value="%{idGroupeTarif}" emptyOption="false"
                                          onchange="onChangeGroupeTarif()"/>
                            </td>
                            <td colspan="2"></td>
                            <td style="text-align: right"><s:text name="VAL_VAL_LBL_VALIDITY"/>:</td>
                            <td style="text-align: left">
                                <s:select list="listsProvider.periodesValidite" listKey="code" listValue="libelle"
                                          name="idPeriodeValidite" label="Periode validite" value="%{idPeriodeValidite}" emptyOption="false"/>
                            </td>
                        </tr>
                        <tr><td colspan="6"><hr></td></tr>
                        <tr>
							<td style="text-align: right"><s:text name="VAL_VAL_LBL_CODEHOTEL"/></td>
							<td style="text-align: left"><s:textfield name="code" label="Code hotel" maxLength="4" size="4" value="%{code}"/></td>
                            <td style="text-align: right"><s:text name="VAL_VAL_LBL_CODEDOP"/></td>
                            <td style="text-align: left"><s:textfield name="codeLocOp" label="Code DOP" maxLength="6" size="6" value="%{codeLocOp}"/></td>
                            <td style="text-align: right"><s:text name="VAL_VAL_LBL_CODEDGR"/></td>
                            <td style="text-align: left"><s:textfield name="CodeDirOper" label="Code DGR" maxLength="6" size="6" value="%{CodeDirOper}"/></td>
						</tr>
						<tr>
							<td style="text-align: right"><s:text name="VAL_VAL_LBL_NOMHOTEL"/></td>
							<td style="text-align: left"><s:textfield name="nom" label="Nom hotel" maxLength="64" size="32" value="%{nom}"/></td>
                            <td style="text-align: right"><s:text name="VAL_VAL_LBL_VILLE"/></td>
                            <td style="text-align: left"><s:textfield name="ville" label="Ville" maxLength="64" size="20" value="%{ville}"/></td>
                            <td style="text-align: right"><s:text name="VAL_VAL_LBL_CODEPAYS"/></td>
                            <td style="text-align: left" colspan="5">
                                <s:select list="listsProvider.pays" listKey="code" listValue="libelle" name="codePays" label="Code pays" value="%{codePays}" emptyOption="true"/>
                            </td>
						</tr>
						<tr>
                            <td style="text-align: right"><s:text name="VAL_VAL_LBL_CODECHAINE"/></td>
							<td style="text-align: left">
								<s:select list="listsProvider.chaines" listKey="code" listValue="libelle" name="codeChaine" label="Code chaine" value="%{codeChaine}" emptyOption="true"/>
							</td>
                            <td style="text-align: center;" colspan="4">
                                <s:if test="hotels.size > 0">
                                    <s:text name="VAL_VAL_LBL_SHOWALLRATES"/>
                                    <s:url  value="images/pictosearch.gif" id="img"/>
                                    <a href="javascript:showAllRates()"><img src="<s:property value= "#img"/>" border="0"></a>
                                </s:if>
                            </td>
						</tr>
						<tr>
						</tr>
					</table>
			</fieldset>
            <input type="button" class="Button" value="<s:text name="ALL_ALL_SEARCH"/>" onclick="onSearch()"/>
			<input type="button" class="Button" value="<s:text name="ALL_ALL_CLEAR"/>" onclick="onClear()"/>
            <s:if test="hotels.size > 0">
                <input type="button" class="Button" value="<s:text name="ALL_ALL_UNSELECT_ALL"/>" onclick="selectAll()" id="selectButton"/>
            </s:if>
            <fieldset>
				<s:if test="hotels.size > 0">
					<legend id="hotelListLegend"><s:text name="VAL_VAL_LBL_LISTE"/> : <s:property value="%{hotels.size}"/> <s:text name="VAL_VAL_LBL_LISTHOTELS"/></legend>
				</s:if>
				<s:else>
					<legend id="hotelListLegend"><s:text name="VAL_VAL_LBL_LISTE"/> : 0 <s:text name="VAL_VAL_LBL_LISTHOTELS"/></legend>
				</s:else>
					<s:if test="hotels.size > 0">
						<table class="List" style="width: 100%">
							<thead>
								<tr>
                  <th></th>
									<th><s:text name="VAL_VAL_LBL_LISTCODE"/></th>
									<th><s:text name="VAL_VAL_LBL_LISTNOM"/></th>
									<th><s:text name="VAL_VAL_LBL_LISTVILLE"/></th>
									<th><s:text name="VAL_VAL_LBL_LISTPAYS"/></th>
                                    <s:iterator value="familleTarifs" status="listFamilles">
                                        <th><s:property value="libelle" /></th>
                                    </s:iterator>
                                </tr>
							</thead>
							<tbody>
								<s:iterator value="hotels" status="h">
                                    <tr class="<s:if test="#h.odd == true ">Odd</s:if><s:else>Even</s:else>">
                                        <td><s:checkbox name="codeHotels" fieldValue="%{code}" theme="simple"/></td>
                                        <s:url action="searchHotel?idHotel=%{code}" method="select" id="selectHotel" escapeAmp="false">
                                            <s:param name="url" value="%{#request.url}"></s:param>
                                        </s:url>
                                        <td><a href="<s:property value="#selectHotel"/>"><s:property value="code" /></a></td>
                                        <td align="left"><a href="<s:property value="#selectHotel"/>"><s:property value="name" /></a></td>
										<td><s:property value="city" /></td>
										<td><s:property value="countryName" /></td>
                                        <s:iterator value="grilles" status="g">
                                            <td>
                                                <s:property value="statutGrille.libelle" />&nbsp;
                                                <s:if test="!screenReadOnly">
                                                    <s:if test="validation1stLevel">
                                                        <s:set id="showSave"  value="true"/>
                                                        <s:select list="listValidate1stLevel" listKey="code" listValue="libelle"
                                                                  name="validations" label="validations" value=""
                                                                  emptyOption="true"/>
                                                    </s:if>
                                                    <s:elseif test="validation2ndLevel">
                                                        <s:set id="showSave"  value="true"/>
                                                        <s:select list="listValidate2ndLevel" listKey="code" listValue="libelle"
                                                                  name="validations" label="validations" value=""
                                                                  emptyOption="true"/>
                                                    </s:elseif>
                                                </s:if>
                                            </td>
                                        </s:iterator>
                                    </tr>
								</s:iterator>
							</tbody>
						</table>
                        <s:if test="#showSave">
                            <table class="List" style="width: 100%">
                                <tr><td align="center"><input type="button" value="<s:text name="ALL_ALL_SAVE"/>" class="Button" style="width:100px" onclick="onSave()"></td></tr>
                            </table>
                        </s:if>
                    </s:if>
            </fieldset>
            </s:form>
        </div>
	</body>
</html>