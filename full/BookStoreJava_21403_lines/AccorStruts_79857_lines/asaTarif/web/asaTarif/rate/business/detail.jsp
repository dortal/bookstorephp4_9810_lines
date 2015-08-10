<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 20 mai 2008
  Time: 10:59:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
var defaultPdjInclu         = new Array();
var defaultCommissionValue  = new Array();
var defaultCommissionUnite  = new Array();
var defaultIsNewContratFlag = new Array();
var defaultIsBlackOutDates  = new Array();
var rateLevelsValidite      = new Array();
var defaultIsLunWe = new Array();
var defaultIsMarWe = new Array();
var defaultIsMerWe = new Array();
var defaultIsJeuWe = new Array();
var defaultIsVenWe = new Array();
var defaultIsSamWe = new Array();
var defaultIsDimWe = new Array();
<s:iterator value="listsProvider.rateLevels" status="listStatus">
    <s:set name="codeRL" value="code"/>
	defaultPdjInclu[<s:property value="#listStatus.index"/>]        =<s:property value="defaultGrilleValuesProvider.isDefaultPdjInclu(#codeRL)"/>
	defaultCommissionValue[<s:property value="#listStatus.index"/>] ='<s:property value="defaultGrilleValuesProvider.getValueCommission(#codeRL)"/>'
	defaultCommissionUnite[<s:property value="#listStatus.index"/>] ='<s:property value="defaultGrilleValuesProvider.getUniteCommission(#codeRL)"/>'
	defaultIsNewContratFlag[<s:property value="#listStatus.index"/>]=<s:property value="defaultGrilleValuesProvider.isNewContrat(#codeRL)"/>
	defaultIsBlackOutDates[<s:property value="#listStatus.index"/>] =<s:property value="defaultGrilleValuesProvider.isBlackOutDates(#codeRL)"/>
    rateLevelsValidite[<s:property value="#listStatus.index"/>]     =<s:property value="defaultGrilleValuesProvider.getRateLevelValidity(#codeRL)"/>
    defaultIsLunWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultLunWe(#codeRL)"/>
    defaultIsMarWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultMarWe(#codeRL)"/>
    defaultIsMerWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultMerWe(#codeRL)"/>
    defaultIsJeuWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultJeuWe(#codeRL)"/>
    defaultIsVenWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultVenWe(#codeRL)"/>
    defaultIsSamWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultSamWe(#codeRL)"/>
    defaultIsDimWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultDimWe(#codeRL)"/>
</s:iterator>
function getCurrentMenuIndex () {
	return 0;
}
function changeProduct() {
    var codesProduit = rateForm['codesProduit'];
    var listProd = "";
    for (var i = 0; i < codesProduit.length; i++)
        if (codesProduit[i].selected)
            listProd += (listProd == "") ? codesProduit[i].value : ", " + codesProduit[i].value;
    document.getElementById("tdCodeProduit").innerHTML = listProd;
}
function changeRateLevel() {
    document.getElementById("tdRateLevel").innerHTML = rateForm['codeRateLevel'].value;
    if (rateForm['codeRateLevel'].selectedIndex >= 0)
        document.getElementById("tdIsOneYearOnly").innerHTML = rateLevelsValidite[rateForm['codeRateLevel'].selectedIndex];
}
function changeDateDebut() {
    var date = rateForm['dateDebut'].value;
    if (date != '' && validDate(date))
        document.getElementById("tdDateDebut").innerHTML = getDayFromDate(stringToDate(date));
}
function changeDateFin() {
    var date = rateForm['dateFin'].value;
    if (date != '' && validDate(date))
        document.getElementById("tdDateFin").innerHTML = getDayFromDate(stringToDate(date));
}
function setParamValuesForRateLevel() {
    var idx = rateForm['codeRateLevel'].selectedIndex
    if (idx >= 0) {
        document.getElementById("pdjInclus").checked = defaultPdjInclu[idx];
        document.getElementById("valueCommission").value = defaultCommissionValue[idx];
        selectValueInCombo(document.getElementById("uniteCommission"), defaultCommissionUnite[idx]);
        if (document.getElementById("openNewContrat"))
            document.getElementById("openNewContrat").checked = defaultIsNewContratFlag[idx];
        if (document.getElementById("blackOutDates"))
            document.getElementById("blackOutDates").checked = defaultIsBlackOutDates[idx];
        checkRadioOption(rateForm['lunWe'], defaultIsLunWe[idx]);
        checkRadioOption(rateForm['marWe'], defaultIsMarWe[idx]);
        checkRadioOption(rateForm['merWe'], defaultIsMerWe[idx]);
        checkRadioOption(rateForm['jeuWe'], defaultIsJeuWe[idx]);
        checkRadioOption(rateForm['venWe'], defaultIsVenWe[idx]);
        checkRadioOption(rateForm['samWe'], defaultIsSamWe[idx]);
        checkRadioOption(rateForm['dimWe'], defaultIsDimWe[idx]);
        corelateFileds();
    }
}
function corelateFileds() {
    setPrix2PaxStatus();
    corelatePdjInclus();
    onChangeTypePeriode();
    <s:if test="%{companiesRates}">
    onChangeOnNouveauxContrats();
    </s:if>
}
function corelatePdjInclus() {
    var inclus = rateForm['pdjInclus'].checked
    if (inclus) {
        rateForm['prixPdj'].value = '';
        rateForm['prixPdj'].disabled = true;
    } else {
        rateForm['prixPdj'].disabled = false;
    }
}
function setPrix2PaxStatus() {
    var ck = rateForm['prix1PaxEq2Pax'].checked
    rateForm['prix2Pax'].disabled = ck;
    if (ck) {
        rateForm['prix2Pax'].value = rateForm['prix1Pax'].value;
    }
}
function setPrix2PaxValue() {
    var ck = rateForm['prix1PaxEq2Pax'].checked
    if (ck) {
        rateForm['prix2Pax'].value = rateForm['prix1Pax'].value;
    }
}
function onChangeTypePeriode() {
    var idTP = document.getElementById("codePeriode").value;
    var ls = document.getElementById("libelleSalon");
    var divSem=document.getElementById("idDivSemaine");
    if (idTP != 'BF' &&  idTP != 'BH') {
        ls.value = "";
        ls.disabled = true;
        divSem.disabled = false;
    } else {
        ls.disabled = false;
        divSem.value=1
        divSem.disabled = true;
    }
}
function onChangeOnNouveauxContrats() {
    ckd = document.getElementById("openNewContrat").checked
    nnMax = document.getElementById("nbreNuitsMax");
    nnMin = document.getElementById("nbreNuitsMin");
    if (!ckd) {
        nnMax.value = "";
        nnMin.value = ""
    }
    nnMax.disabled = !ckd;
    nnMin.disabled = !ckd
}
function onSave() {
   	setFormReadOnly(rateForm,false);
   	rateForm.submit();
}
function init(withErrors) {
	changeProduct();
    changeRateLevel();
    changeDateDebut();
    changeDateFin();
    corelateFileds();
}
function displayContracts(idGrille, codeRateLevel) {
     <s:url id="displayContractsURL" action="displayListContracts.action" includeParams="false"/>
     window.open("<s:property value="#displayContractsURL"/>?idGrille="+idGrille+"&codeRateLevel="+codeRateLevel,"lc","width=600,height=300,menubar=no, status=no");
}  
</script>
<s:form action="listBusinessRate.action" name="listForm" method="GET">
		<s:hidden name="codeEcran" value="%{codeEcran}"/>
		<s:hidden name="idPeriodeValidite" value="%{idPeriodeValidite}"/>
</s:form>
<table>
	<tr>
		<td>
<s:form  name="rateForm" id="detailForm" validate="true" action="detailBusinessRate!save.action">
	<s:hidden name="codeEcran" value="%{codeEcran}"/>
	<s:hidden name="idPeriodeValidite" value="%{idPeriodeValidite}"/>
	<s:hidden name="key" value="%{key}"/>
	<table>
		<tr>
		    <td>
		        <table style="width: 100%">
		            <tr style="vertical-align: top">
		                <td  colspan="2">
		                    <fieldset>
		                        <legend><s:text name="BUS_DET_LBL_CHAMBRESTARIF"/> :</legend>
		                        <table>
		                            <tr>
		                                <td>
		                                    <s:select list="listsProvider.rooms" listKey="code" listValue="code+' >> '+nom+' >> '+nbPers"
		                                              name="codesProduit"
		                                              multiple="true" size="3"
		                                              onchange="changeProduct()"
		                                              theme="simple"/>
		                                </td>
		                                <td>
		                                    <s:select list="listsProvider.rateLevels" listKey="code" listValue="code+' >> '+libelle"
		                                              name="codeRateLevel" id="codeRateLevel"
		                                              onchange="changeRateLevel();setParamValuesForRateLevel();"
		                                              theme="simple"/>
		                                </td>
		                            </tr>
		                            <tr>
		                                <td id="tdCodeProduit" class="HighLight1" align="center"></td>
		                                <td id="tdRateLevel" class="HighLight1" align="center"></td>
		                            </tr>
		                        </table>
		                    </fieldset>
		                </td>
		            </tr>
		        </table>
		    </td>
		</tr>
		<tr>
			<td>
				<table style="width: 100%">
					<tr style="vertical-align: top">
						<td>
						    <fieldset>
						        <legend><s:text name="BUS_DET_LBL_PERIODES"/> :</legend>
						        <table>
						            <tr>
						                <td>
						                    <table>
						                        <tr>
						                            <td nowrap>
						                                <s:text name="BUS_DET_LBL_PERIODE"/>
						                                <s:text name="BUS_DET_LBL_DU"/>
						                            </td>
						                            <td id="tdDateDebut" nowrap align="center"></td>
                                                    <td nowrap>
						                                <s:textfield id="dateDebut"
                                                                     name="dateDebut"
						                                             maxLength="10" size="10"
						                                             onchange="changeDateDebut();"
						                                             theme="simple"/>
                                                        <img id="bDateDebut" src="images/picto_calendrier.gif" border="0"
                                                                onclick="calShow(calID('dateDebut'),event);">
                                                    </td>
						                            <td nowrap="nowrap" align="right"><s:text name="BUS_DET_LBL_AU"/>:</td>
						                            <td id="tdDateFin" nowrap align="center"></td>
						                            <td nowrap>
						                                <s:textfield id="dateFin"
                                                                     name="dateFin"
						                                             maxLength="10" size="10"
						                                             onchange="changeDateFin()"
						                                             theme="simple"/>
                                                        <img id="bDateFin" src="images/picto_calendrier.gif" border="0"
                                                                onclick="calShow(calID('dateFin'),event);">
                                                    </td>
						                            <td width="20"></td>
						                            <td nowrap="nowrap" align="right"><s:text name="BUS_DET_LBL_VALIDITETARIF"/>:</td>
						                            <td id="tdIsOneYearOnly" class="Highlight2"></td>
						                        </tr>
						                    </table>
						                </td>
						            </tr>
						            <tr>
						                <td style="text-align: left">
						                    <s:text name="BUS_DET_LBL_TYPEPERIODE"/>
						                    <s:select list="listsProvider.periodesGeneriques" listKey="code" listValue="libelle"
						                              name="codePeriode" id="codePeriode" value="%{codePeriode}"
						                              theme="simple" onchange="onChangeTypePeriode()"/>
						                </td>
						            </tr>
						            <tr>
						                <td style="text-align: left">
						                    <s:text name="BUS_DET_LBL_LIBELLESALON"/>
						                    <s:textfield name="libelleSalon" id="libelleSalon" value="%{libelleSalon}"
						                                 maxLength="255" size="32" theme="simple"/>
						                </td>
						            </tr>
						        </table>
						    </fieldset>
						</td>
						<td>
						    <fieldset>
						        <legend><s:text name="BUS_DET_LBL_DIVISIONSEMAINE"/> :</legend>
						        <table>
						            <tr>
						                <td>
						                    <table>
						                        <tr>
						                            <td nowrap="nowrap" align="center"><s:text name="BUS_DET_LBL_PERIODEAPPLICATION"/></td>
						                        </tr>
						                        <tr>
						                            <td nowrap align="center">
						                                    <s:select list="listsProvider.divisionSemaines" listKey="code" listValue="libelle"
						                                              name="idDivSemaine" id="idDivSemaine" value="%{idDivSemaine}"
						                                              theme="simple"/>
						                        </tr>
						                    </table>
						                </td>
						                <td>
						                    <table style="padding: 0 px">
						                        <tr>
						                            <td></td>
						                            <s:iterator value="weekDays" id="we">
						                                <td nowrap align="center">
						                                    <s:property/>
						                                </td>
						                            </s:iterator>
						                        </tr>
						                        <tr>
						                            <td nowrap="nowrap" align="right"><s:text name="BUS_DET_LBL_SEMAINE"/></td>
						                            <td nowrap align="center">
						                                <s:radio name="lunWe" list="#{false:''}" theme="simple"/>
						                            </td>
						                            <td nowrap align="center">
						                                <s:radio name="marWe" list="#{false:''}" theme="simple"/>
						                            </td>
						                            <td nowrap align="center">
						                                <s:radio name="merWe" list="#{false:''}" theme="simple"/>
						                            </td>
						                            <td nowrap align="center">
						                                <s:radio name="jeuWe" list="#{false:''}" theme="simple"/>
						                            </td>
						                            <td nowrap align="center">
						                                <s:radio name="venWe" list="#{false:''}" theme="simple"/>
						                            </td>
						                            <td nowrap align="center">
						                                <s:radio name="samWe" list="#{false:''}" theme="simple"/>
						                            </td>
						                            <td nowrap align="center">
						                                <s:radio name="dimWe" list="#{false:''}" theme="simple"/>
						                            </td>
						                        </tr>
						                        <tr>
						                            <td nowrap="nowrap" align="right"><s:text name="BUS_DET_LBL_WEEKEND"/></td>
						                            <td nowrap align="center">
						                                <s:radio name="lunWe" list="#{true:''}" theme="simple"/>
						                            </td>
						                            <td nowrap="nowrap" align="center">
						                                <s:radio name="marWe" list="#{true:''}" theme="simple"/>
						                            </td>
						                            <td nowrap="nowrap" align="center">
						                                <s:radio name="merWe" list="#{true:''}" theme="simple"/>
						                            </td>
						                            <td nowrap="nowrap" align="center">
						                                <s:radio name="jeuWe" list="#{true:''}" theme="simple"/>
						                            </td>
						                            <td nowrap="nowrap" align="center">
						                                <s:radio name="venWe" list="#{true:''}" theme="simple"/>
						                            </td>
						                            <td nowrap="nowrap" align="center">
						                                <s:radio name="samWe" list="#{true:''}" theme="simple"/>
						                            </td>
						                            <td nowrap="nowrap" align="center">
						                                <s:radio name="dimWe" list="#{true:''}" theme="simple"/>
						                            </td>
						                        </tr>
						                    </table>
						                </td>
						            </tr>
						        </table>
						    </fieldset>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<s:if test="%{companiesRates}">
		<tr>
		    <td>
		    	<table style="width: 100%">
		    		<tr>
		    			<td>
				        <fieldset>
				            <legend><s:text name="BUS_DET_LBL_BUSINESS"/> :</legend>
				            <table>
				                <tr>
				                    <td>
				                        <s:text name="BUS_DET_LBL_BLACKOUTDATES"/>
				                        <s:checkbox name="blackOutDates" id="blackOutDates" value="%{blackOutDates}"
				                                    theme="simple"/>
				                    </td>
				                    <td width="20"></td>
				                    <td>
				                        <s:text name="BUS_DET_LBL_OUVERTNEWCONTRAT"/>
				                        <s:checkbox name="openNewContrat" id="openNewContrat" value="%{openNewContrat}"
				                                    onclick="onChangeOnNouveauxContrats()"
				                                    theme="simple"/>
				                    </td>
				                    <td width="20"></td>
				                    <td>
				                        <s:text name="BUS_DET_LBL_NBRENUITS_DE"/>
				                        <s:textfield name="nbreNuitsMin" id="nbreNuitsMin" value="%{nbreNuitsMin}"
				                                     maxLength="15" size="5"
				                                     theme="simple"/>
				                    </td>
				                    <td width="20"></td>
				                    <td>
				                        <s:text name="BUS_DET_LBL_NBRENUITS_A"/>
				                        <s:textfield name="nbreNuitsMax" id="nbreNuitsMax" value="%{nbreNuitsMax}"
				                                     maxLength="15" size="5"
				                                     theme="simple"/>
				                    </td>
				                </tr>
				            </table>
				        </fieldset>
				      </td>
				    </tr>
				  </table>
		    </td>
		</tr>
		</s:if>
		<tr>
		    <td>
		        <table style="width: 100%">
		            <tr style="vertical-align: top">
		                <td>
		                    <fieldset>
		                        <legend><s:text name="BUS_DET_LBL_PRIX"/> :</legend>
		                        <table>
		                            <tr>
		                                <td>
		                                    <s:text name="BUS_DET_LBL_1PAX"/>
		                                    <s:textfield name="prix1Pax" value="%{prix1Pax}"
		                                                 maxLength="15" size="5"
		                                                 onchange="setPrix2PaxValue()"
		                                                 theme="simple"/>
		                                </td>
		                                <td>
		                                    <s:text name="BUS_DET_LBL_1PAXEQ2PAX"/>
		                                    <s:checkbox name="prix1PaxEq2Pax" value="%{prix1PaxEq2Pax}" id="prix1PaxEq2Pax"
		                                                onclick="setPrix2PaxStatus()"
		                                                theme="simple"/>
		                                </td>
		                                <td>
		                                    <s:text name="BUS_DET_LBL_2PAX"/>
		                                    <s:textfield name="prix2Pax" id="prix2Pax" value="%{prix2Pax}"
		                                                 maxLength="15" size="5"
		                                                 theme="simple"/>
		                                </td>
		                                <td>
		                                    <s:text name="BUS_DET_LBL_DEVISE"/>
		                                    <s:select list="listsProvider.devises" listKey="codeDevise" listValue="codeDevise"
		                                              name="codeDevise" value="%{codeDevise}"
		                                              theme="simple"/>
		                                </td>
		                            </tr>
		                        </table>
		                    </fieldset>
		                </td>
		                <td>
		                    <fieldset>
		                        <legend><s:text name="BUS_DET_LBL_PETITDEJEUNER"/> :</legend>
		                        <table>
		                            <tr>
		                                <td>
		                                    <s:textfield name="prixPdj" id="prixPdj" label="Pdj" value="%{prixPdj}"
		                                                 maxLength="15" size="5"
		                                                theme="simple"/>
		                                </td>
		                                <td>
		                                    <s:text name="BUS_DET_LBL_INCLUS"/>
		                                    <s:checkbox name="pdjInclus" value="%{pdjInclus}" id="pdjInclus"
		                                                onclick="corelatePdjInclus()"
		                                                theme="simple"/>
		                                </td>
		                                <td>
		                                    <s:text name="BUS_DET_LBL_TYPE"/>
		                                    <s:select list="listsProvider.petitDejs" listKey="code" listValue="libelle"
		                                              name="codePetitDej" value="%{codePetitDej}"
		                                              theme="simple"/>
		                                </td>
		                                <td>
		                                </td>
		                            </tr>
		                        </table>
		                    </fieldset>
		                </td>
		                <td>
		                    <fieldset>
		                        <legend><s:text name="BUS_DET_LBL_COMMISSION"/> :</legend>
		                        <table>
		                            <tr>
		                                <td align="left">
		                                    <s:textfield name="valueCommission" id="valueCommission"
		                                                 value="%{valueCommission}" maxLength="15" size="5" theme="simple"/>
		                                    <s:select list="listsProvider.devises" listKey="codeDevise" listValue="codeDevise" headerKey="%"
		                                              headerValue="%"
		                                              name="uniteCommission" id="uniteCommission" value="%{uniteCommission}"
		                                              theme="simple"/>
		                                </td>
		                            </tr>
		                        </table>
		                    </fieldset>
		                </td>
		            </tr>
		        </table>
		    </td>
		</tr>
	</table>
</s:form>
</td>
</tr>
</table>