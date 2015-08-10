<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/utils.js"></script>
		<script type="text/javascript">
			function getCurrentMenuIndex () {
				return 3;
			}
			function getGrilles () {
				document.getElementById("form").action = "finSaisie";
				document.getElementById("form").submit();
			}
			function getControles () {
				alert("<s:text name="TARIF_CONTROLES_WAIT"/>");
				document.getElementById("form").action = "finSaisie!controle";
				document.getElementById("form").submit();
			}
			function finSaisie () {
				document.getElementById("form").action = "finSaisie!finSaisie";
				document.getElementById("form").submit();
			}
		</script>
	</head>
	<body>
		<div id="bodyDiv">
			<s:include value="/head.jsp"/>
			<div id="mainDiv">
				<div id="errorDiv">
					<s:actionmessage/>
					<s:actionerror/>
					<s:fielderror/>
				</div>
				<s:form id="form" action="" method="post" theme="simple" style="width: 600px">
                    <fieldset style="width: 600px">
                        <legend><s:text name="TARIF_CTL_LBL_TITLE"/> :</legend>
                        <table width="100%">
                            <tr>
                                <td colspan="2" class="ScreenTitle" align="center">
                                    <s:text name="COM_RAT_LBL_TITLEENDSEIZURE"/>
                                </td>
                            </tr>
                            <tr>
                                <td><s:text name="TARIF_CTL_LBL_HOTEL"/> :</td>
                                <td class="Highlight1">
                                    <s:property value="session.hotel.name"/>
                                    -
                                    (
                                    <s:property value="session.hotel.code"/>
                                    )
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <table style="text-align: center; width: 100%">
						<tr>
							<td>
								<s:text name="TARIF_CTL_INPUT"/> :
								<s:select id="groupesTarif" name="idGroupeTarif" label="Groupe tarif" list="groupesTarif" listKey="id" listValue="libelle" value="%{idGroupeTarif}" onchange="javascript:getGrilles()"/>
							</td>
							<td>
								<s:text name="TARIF_CTL_PERIODVALIDITY"/> :
								<s:select id="periodesValidite" name="idPeriodeValidite" label="Période de validité" list="periodesValidite" listKey="code" listValue="libelle" value="%{idPeriodeValidite}" onchange="javascript:getGrilles()"/>
							</td>
						</tr>
					</table>
					<fieldset>
						<legend><s:text name="TARIF_CTL_GRIDS"/></legend>
						<br/>
						<s:if test="grilles.size > 0">
							<table class="List" style="width: 100%">
								<thead>
									<tr>
										<th style="width: 50%"><s:text name="TARIF_CTL_TARIFF_FAMILY"/></th>
										<th style="width: 50%"><s:text name="TARIF_CTL_STATUS"/></th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="grilles" id="grille" status="statutGrille">
										<tr class="Even">
											<td><s:property value="#grille.familleTarif.libelle"/></td>
											<td><s:property value="#grille.statutGrille.libelle"/></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</s:if>
					</fieldset>
					<s:if test="controlesBloquantsEnEchec.size > 0 || controlesNonBloquantsEnEchec.size > 0">
						<br/>
						<fieldset>
							<legend><s:text name="TARIF_CTL_CONTROLS"/></legend>
							<s:if test="controlesBloquantsEnEchec.size > 0">
								<br/>
								<table class="List" style="width: 100%">
									<thead>
										<tr>
											<th colspan="2"><s:text name="TARIF_CTL_BLOCKING_CONTROLS"/></th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="controlesBloquantsEnEchec" id="controle" status="statutControle">
											<tr class="Even">
												<td style="width: 30px">#<s:property value="#controle.idControle"/></td>
												<td style="text-align: left"><s:property value="#controle.libelleMessage"/></td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</s:if>
							<s:if test="controlesNonBloquantsEnEchec.size > 0">
								<br/>
								<table class="List" style="width: 100%">
									<thead>
										<tr>
											<th colspan="2"><s:text name="TARIF_CTL_NON_BLOCKING_CONTROLS"/></th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="controlesNonBloquantsEnEchec" id="controle" status="statutControle">
											<tr class="Even">
												<td style="width: 30px">#<s:property value="#controle.idControle"/></td>
												<td style="text-align: left"><s:property value="#controle.libelleMessage"/></td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</s:if>
						</fieldset>
					</s:if>
					<br/>
					<s:if test="%{displayBoutonControle}">
						<br/>
						<button class="Button" onclick="getControles()"><s:text name="TARIF_CTL_LAUNCH_CONTROLS"/></button>
					</s:if>
					<s:elseif test="%{displayBoutonFinSaisie}">
						<br/>
						<button class="Button" onclick="finSaisie()"><s:text name="TARIF_CTL_END_INPUT"/></button>
					</s:elseif>
				</s:form>
				<br/>
			</div>
		</div>
	</body>
</html>