<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 26 mai 2008
  Time: 16:32:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/utils.js"></script>
		<script type="text/javascript">
			function getCurrentMenuIndex () {
				return 5;
			}
			function search() {
				var form = document.getElementById("detailForm");
				form.action = "searchHotel!search.action";
				form.submit();
			}
			function clearDetailForm() {
				var form = document.getElementById("detailForm");
				cleanForm(form);
			}
			var isAllSelected = false;
			function selectAll() {
				var tabInputs = document.getElementsByName('idHotel');
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
				var tabInputs = document.getElementsByName('idHotel');
				for (var i = 0; i < tabInputs.length; i ++)
					if(tabInputs[i].checked)
						s += (s=='')?tabInputs[i].value:','+tabInputs[i].value;
				return s;
			}
			function select() {
				var s = getSelectedHotels();
				if (s!='') {
					var form = document.getElementById("listForm");
					form.submit();
				} else {
					alert('Aucun hotel selectionn?!!!')
				}
			}
		</script>
	</head>
	<body>
		<s:include value="/head.jsp"/>
		<div id="mainDiv">
			<fieldset id="hotelFieldset">
				<legend id="hotelLegend"><s:text name="COM_SEARCHHOTEL_LBL_TITRE"/></legend>
				<div id="errorDiv">
					<s:actionmessage/>
					<s:actionerror/>
					<s:fielderror/>
				</div>
				<s:form id="detailForm" validate="true" theme="simple">
					<s:hidden name="url" value="%{url}"/>
					<s:hidden name="multiSelect" value="%{multiSelect}"/>
					<table style="width: 100%">
						<tr>
							<td style="text-align: right"><s:text name="COM_SEARCHHOTEL_LBL_CODEHOTEL"/></td>
							<td style="text-align: left"><s:textfield name="code" label="Code hotel" maxLength="4" size="4" value="%{code}"/></td>
							<td style="text-align: right"><s:text name="COM_SEARCHHOTEL_LBL_CODEMARQUE"/></td>
							<td style="text-align: left">
								<s:select list="listsProvider.marques" listKey="code" listValue="libelle" name="codeMarque" label="Code marque" value="%{codeMarque}" emptyOption="true"/>
							</td>
							<td style="text-align: right"><s:text name="COM_SEARCHHOTEL_LBL_CODEPLACE"/></td>
							<td style="text-align: left"><s:textfield name="codePlace" label="Code place" maxLength="64" size="20" value="%{codePlace}"/></td>
						</tr>
						<tr>
							<td style="text-align: right"><s:text name="COM_SEARCHHOTEL_LBL_NOMHOTEL"/></td>
							<td style="text-align: left"><s:textfield name="nom" label="Nom hotel" maxLength="64" size="32" value="%{nom}"/></td>
							<td style="text-align: right"><s:text name="COM_SEARCHHOTEL_LBL_CODECHAINE"/></td>
							<td style="text-align: left">
								<s:select list="listsProvider.chaines" listKey="code" listValue="libelle" name="codeChaine" label="Code chaine" value="%{codeChaine}" emptyOption="true"/>
							</td>
							<td style="text-align: right"><s:text name="COM_SEARCHHOTEL_LBL_CODEDGR"/></td>
							<td style="text-align: left"><s:textfield name="CodeDirOper" label="Code DGR" maxLength="6" size="6" value="%{CodeDirOper}"/></td>
						</tr>
						<tr>
							<td style="text-align: right"><s:text name="COM_SEARCHHOTEL_LBL_VILLE"/></td>
							<td style="text-align: left"><s:textfield name="ville" label="Ville" maxLength="64" size="20" value="%{ville}"/></td>
							<td style="text-align: right"><s:text name="COM_SEARCHHOTEL_LBL_CODEASACATEGORY"/></td>
							<td style="text-align: left">
								<s:select list="listsProvider.categories" listKey="code" listValue="libelle" name="codeGroupe" label="ASA Category" headerValue="%{codeGroupe}" emptyOption="true"/>
							</td>
							<td style="text-align: right"><s:text name="COM_SEARCHHOTEL_LBL_CODEDOP"/></td>
							<td style="text-align: left"><s:textfield name="codeLocOp" label="Code DOP" maxLength="6" size="6" value="%{codeLocOp}"/></td>
						</tr>
						<tr>
							<td style="text-align: right"><s:text name="COM_SEARCHHOTEL_LBL_CODEPAYS"/></td>
							<td style="text-align: left" colspan="5">
								<s:select list="listsProvider.pays" listKey="code" listValue="libelle" name="codePays" label="Code pays" value="%{codePays}" emptyOption="true"/>
							</td>
						</tr>
					</table>
				</s:form>
			</fieldset>
			<input type="button" class="Button" value="<s:text name="ALL_ALL_SEARCH"/>" onclick="search()"/>
			<input type="button" class="Button" value="<s:text name="ALL_ALL_CLEAR"/>" onclick="clearDetailForm()"/>
			<s:if test="multiSelect==1 && hotels.size > 0">
				<input type="button" class="Button" value="<s:text name="ALL_ALL_UNSELECT_ALL"/>" onclick="selectAll()" id="selectButton"/>
				<input type="button" class="Button" value="<s:text name="ALL_ALL_VALIDATE"/>" onclick="select()"/>
			</s:if>
			<fieldset>
				<s:if test="hotels.size > 0">
					<legend id="hotelListLegend"><s:text name="COM_SEARCHHOTEL_LBL_LISTE"/> : <s:property value="%{hotels.size}"/> <s:text name="COM_SEARCHHOTEL_LBL_LISTHOTELS"/></legend>
				</s:if>
				<s:else>
					<legend id="hotelListLegend"><s:text name="COM_SEARCHHOTEL_LBL_LISTE"/> : 0 <s:text name="COM_SEARCHHOTEL_LBL_LISTHOTELS"/></legend>
				</s:else>
				<s:form id="listForm" action="%{url}" theme="simple">
					<s:if test="hotels.size > 0">
						<table class="List" style="width: 100%">
							<thead>
								<tr>
                                    <s:if test="multiSelect==1">
                                        <th><s:text name="COM_SEARCHHOTEL_LBL_LISTSELECT"/></th>
                                    </s:if>
                                    <th><s:text name="COM_SEARCHHOTEL_LBL_LISTCODE"/></th>
									<th><s:text name="COM_SEARCHHOTEL_LBL_LISTNOM"/></th>
									<th><s:text name="COM_SEARCHHOTEL_LBL_LISTDOP"/></th>
									<th><s:text name="COM_SEARCHHOTEL_LBL_LISTDGR"/></th>
									<th><s:text name="COM_SEARCHHOTEL_LBL_LISTMARQUE"/></th>
									<th><s:text name="COM_SEARCHHOTEL_LBL_LISTVILLE"/></th>
									<th><s:text name="COM_SEARCHHOTEL_LBL_LISTPAYS"/></th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="hotels" status="listStatus">
									<tr class="<s:if test="#listStatus.odd == true ">Odd</s:if><s:else>Even</s:else>">
                                        <s:if test="multiSelect==1">
                                            <td>
												<s:checkbox name="idHotel" fieldValue="%{code}" theme="simple"/>
										    </td>
                                        </s:if>
                                        <s:if test="multiSelect==1">
                                            <td><s:property value="code" /></td>
                                            <td align="left"><s:property value="name" /></td>
										</s:if>
										<s:else>
											<s:url action="searchHotel?idHotel=%{code}" method="select" id="selectHotel" escapeAmp="false">
												<s:param name="url" value="%{#request.url}"></s:param>
											</s:url>
											<td><a href="<s:property value="#selectHotel"/>"><s:property value="code" /></a></td>
                                            <td align="left"><a href="<s:property value="#selectHotel"/>"><s:property value="name" /></a></td>
                                        </s:else>
										<td><s:property value="DOPCode" /></td>
										<td><s:property value="DGRCode" /></td>
										<td><s:property value="mark.code" /></td>
										<td><s:property value="city" /></td>
										<td><s:property value="countryName" /></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</s:if>
				</s:form>
			</fieldset>
		</div>
	</body>
</html>