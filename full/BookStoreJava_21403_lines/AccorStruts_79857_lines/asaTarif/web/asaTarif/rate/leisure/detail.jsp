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
var defaultMealPlan         = new Array();
var defaultCommissionValue  = new Array();
var defaultCommissionUnite  = new Array();
var defaultIsLunWe = new Array();
var defaultIsMarWe = new Array();
var defaultIsMerWe = new Array();
var defaultIsJeuWe = new Array();
var defaultIsVenWe = new Array();
var defaultIsSamWe = new Array();
var defaultIsDimWe = new Array();
<s:iterator value="listsProvider.rateLevels" status="listStatus">
	<s:set name="codeRL" value="code"/>
    defaultMealPlan[<s:property value="#listStatus.index"/>]        ='<s:property value="defaultGrilleValuesProvider.getDefaultCodeMealplan(#codeRL)"/>'
    defaultCommissionValue[<s:property value="#listStatus.index"/>] ='<s:property value="defaultGrilleValuesProvider.getValueCommission(#codeRL)"/>'
    defaultCommissionUnite[<s:property value="#listStatus.index"/>] ='<s:property value="defaultGrilleValuesProvider.getUniteCommission(#codeRL)"/>'
    defaultIsLunWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultLunWe(#codeRL)"/>
    defaultIsMarWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultMarWe(#codeRL)"/>
    defaultIsMerWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultMerWe(#codeRL)"/>
    defaultIsJeuWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultJeuWe(#codeRL)"/>
    defaultIsVenWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultVenWe(#codeRL)"/>
    defaultIsSamWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultSamWe(#codeRL)"/>
    defaultIsDimWe[<s:property value="#listStatus.index"/>]         =<s:property value="defaultGrilleValuesProvider.getDefaultDimWe(#codeRL)"/>
</s:iterator>
var listPdjInclu=new Array()
<s:iterator value="listsProvider.listMealplans" status="listMP">
    listPdjInclu[<s:property value="#listMP.index"/>] = <s:property value="pdjInclu"/>;
</s:iterator>
        
function getCurrentMenuIndex () {
	return 1;
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
        selectValueInCombo(rateForm['codeMealPlan'], defaultMealPlan[idx]);
        document.getElementById("valueCommission").value = defaultCommissionValue[idx];
        selectValueInCombo(document.getElementById("uniteCommission"), defaultCommissionUnite[idx]);
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

function onChangeTypePeriode() {
	var idTP = document.getElementById("codePeriode").value;
	var ls=document.getElementById("libelleSalon");
    var divSem=document.getElementById("idDivSemaine");
    if(idTP != 'TF' && idTP != 'TH') {
		ls.value="";
		ls.disabled=true;
        divSem.disabled = false;
    } else {
		ls.disabled=false;
        divSem.value=1
        divSem.disabled = true;
    }
}
function onChangeMealPlan() {
	var idx=rateForm['codeMealPlan'].selectedIndex;
	var codeMP=rateForm['codeMealPlan'].value
	if(idx>=0) 	{
		isPdjInclu=listPdjInclu[idx]
		document.getElementById("pdjInclus").checked=isPdjInclu;
		if(isPdjInclu) {
   		    rateForm['prixPdj'].value='';
   			rateForm['prixPdj'].disabled=true;
   		} else {
            rateForm['prixPdj'].disabled=false;
   		}
	}
	if(codeMP=='HB') {
   		rateForm['supplDemPens'].value='';
        rateForm['supplDemPens'].disabled=true;
        rateForm['supplPensCompl'].disabled=false;
   	} else if	((codeMP=='FB')) {
   		rateForm['supplPensCompl'].value='';
        rateForm['supplPensCompl'].disabled=true;
        rateForm['supplDemPens'].disabled=false;
   	} else {
        rateForm['supplPensCompl'].disabled=false;
        rateForm['supplDemPens'].disabled=false;
   	}
}

function corelateFileds() {
    onChangeMealPlan();
    corelatePdjInclus();
    onChangeTypePeriode();
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
<s:form action="listLeisureRate.action" name="listForm"  method="GET">
		<s:hidden name="codeEcran" value="%{codeEcran}"/>
		<s:hidden name="idPeriodeValidite" value="%{idPeriodeValidite}"/>
</s:form>
<table>
    <tr>
		<td>
<s:form name="rateForm" id="detailForm" validate="true" action="detailLeisureRate!save.action">
    <s:hidden name="codeEcran" value="%{codeEcran}"/>
    <s:hidden name="idPeriodeValidite" value="%{idPeriodeValidite}"/>
    <s:hidden name="key" value="%{key}"/>
    <table>
      <tr>
        <td>
            <table style="width: 100%">
                <tr style="vertical-align: top">
                    <td>
                        <fieldset>
                            <legend><s:text name="LSR_DET_LBL_ROOM_RATE"/>:</legend>
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
                                    <td id="tdCodeProduit" class="Highlight1" align="center"></td>
                                    <td id="tdRateLevel" class="Highlight1" align="center"></td>
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
                            <legend><s:text name="LSR_DET_LBL_PERIODES"/>:</legend>
                            <table>
                                <tr>
                                    <td>
                                        <table>
                                            <tr>
                                                <td nowrap><s:text name="LSR_DET_LBL_FROM"/>:</td>
                                                <td id="tdDateDebut" nowrap align="center"></td>
                                                <td nowrap>
                                                    <s:textfield id="dateDebut"
                                                                 name="dateDebut"
                                                                 maxLength="10" size="10"
                                                                 onchange="changeDateDebut()"
                                                                 theme="simple"/>
                                                    <img id="bDateDebut" src="images/picto_calendrier.gif" border="0"
                                                            onclick="calShow(calID('dateDebut'),event);">
                                                </td>
                                                <td nowrap align="right"><s:text name="LSR_DET_LBL_TO"/>:</td>
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
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <table>
                                          <tr>
                                            <td nowrap><s:text name="LSR_DET_LBL_PERIODE_TYPE"/>:</td>
                                            <td nowrap>
                                                <s:select list="listsProvider.periodesGeneriques" listKey="code" listValue="libelle"
                                                          name="codePeriode" id="codePeriode" value="%{codePeriode}"
                                                          theme="simple" onchange="onChangeTypePeriode()"/>
                                            </td>
                                          </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <table>
                                          <tr>
                                            <td nowrap><s:text name="LSR_DET_LBL_SALON_EVT"/>:</td>
                                            <td nowrap>
                                                <s:textfield name="libelleSalon" id="libelleSalon" value="%{libelleSalon}"
                                                             maxLength="255" size="32" theme="simple"/>
                                            </td>
                                          </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </td>
                    <td>
                        <fieldset>
                            <legend><s:text name="LSR_DET_LBL_DIVISION_SEMAINE"/>:</legend>
                            <table style="width: 100%">
                                <tr>
                                    <td>
                                      <table>
                                        <tr>
                                          <td nowrap align="center"><s:text name="LSR_DET_LBL_PERIODE_APPLICATION"/></td>
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
                                          <s:iterator  value="weekDays" id="we">
                                            <td nowrap align="center"><s:property /> </td>
                                          </s:iterator>
                                        </tr>
                                        <tr>
                                            <td nowrap align="center"><s:text name="LSR_DET_LBL_SEMAINE"/>:</td>
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
                                            <td nowrap align="center"><s:text name="LSR_DET_LBL_WEEKEND"/>:</td>
                                            <td nowrap align="center">
                                                <s:radio name="lunWe" list="#{true:''}" theme="simple"/>
                                            </td>
                                            <td nowrap align="center">
                                                <s:radio name="marWe" list="#{true:''}" theme="simple"/>
                                            </td>
                                            <td nowrap align="center">
                                                <s:radio name="merWe" list="#{true:''}" theme="simple"/>
                                            </td>
                                            <td nowrap align="center">
                                                <s:radio name="jeuWe" list="#{true:''}" theme="simple"/>
                                            </td>
                                            <td nowrap  align="center">
                                                <s:radio name="venWe" list="#{true:''}" theme="simple"/>
                                            </td>
                                            <td nowrap align="center">
                                                <s:radio name="samWe" list="#{true:''}" theme="simple"/>
                                            </td>
                                            <td nowrap align="center">
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
    <tr>
      <td>
        <table style="width: 100%">
            <tr style="vertical-align: top">
                <td>
                    <fieldset>
                        <legend><s:text name="LSR_DET_LBL_PRIX"/>:</legend>
                        <table>
                            <tr>
                                <td nowrap="nowrap">
                                        <s:text name="LSR_DET_LBL_PRIX_1DBL"/>
                                        <s:textfield name="prix" value="%{prix}"
                                                     maxLength="15" size="5"
                                                     theme="simple" />
                                   
                                </td>
                                <td nowrap="nowrap">
                                     <s:text name="LSR_DET_LBL_PRIX_SUPPSGL"/>
                                     <s:textfield name="prixSupSGL" value="%{prixSupSGL}"
                                                     maxLength="15" size="5"
                                                     theme="simple" />
                                </td>
                              </tr>
                              <tr>
                                <td nowrap="nowrap">
                                     <s:text name="LSR_DET_LBL_PRIX_SUPPTRP"/>
                                     <s:textfield name="prixSupTRI" value="%{prixSupTRI}"
                                                     maxLength="15" size="5"
                                                     theme="simple" />
                                </td>
                                <s:if test="%{!leisuresRates}">
                                    <td nowrap="nowrap">
                                            <s:text name="LSR_DET_LBL_SUPP_4"/>:
                                            <s:textfield name="prixSupQUAD" id="prixSupQUAD" label="Prix Suppl QUAD" value="%{prixSupQUAD}" maxLength="15" size="5"
                                                         cssClass="formInput" theme="simple"/>
                                    </td>
                                </s:if>
                            </tr>
                            <tr>
                                <td nowrap="nowrap">

                                       <s:text name="LSR_DET_LBL_DEVISE"/>
                                      <s:select list="listsProvider.devises" listKey="codeDevise" listValue="codeDevise"
                                                name="codeDevise" value="%{codeDevise}"
                                                theme="simple"/>

                                </td>
                                <td nowrap="nowrap">
                            			<s:text name="LSR_DET_LBL_COMMISSION"/>
                                        <s:textfield name="valueCommission" id="valueCommission" value="%{valueCommission}"
                                                     maxLength="15" size="5" theme="simple"/>
                                        <s:select list="listsProvider.devises" listKey="codeDevise" listValue="codeDevise"
                                                  headerKey="%" headerValue="%"
                                                  name="uniteCommission" id="uniteCommission" value="%{uniteCommission}"
                                                  theme="simple"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
                <td>
                    <fieldset>
                        <legend><s:text name="LSR_DET_LBL_PETIT_DEJ"/>:</legend>
                        <table>
                        	<tr>
                                <td colspan="2">
                                       <s:text name="LSR_DET_LBL_MEAL_PLAN"/> 
                                       <s:select list="listsProvider.listMealplans" listKey="code" listValue="libelle"
                                                  name="codeMealPlan" value="%{codeMealPlan}"
                                                 onchange="onChangeMealPlan()" theme="simple"/>
                                </td>
                            </tr>
                            <tr>    
                                <td nowrap="nowrap">
                                       <s:text name="LSR_DET_LBL_SUPP_DEMPENS"/> 
                                       <s:textfield name="supplDemPens" id="supplDemPens"  value="%{supplDemPens}" maxLength="15" size="5" theme="simple"/>
                                </td >
                                <td nowrap="nowrap">
                                       <s:text name="LSR_DET_LBL_SUPP_PENS"/> 
                                       <s:textfield name="supplPensCompl" id="supplPensCompl"  value="%{supplPensCompl}" maxLength="15" size="5" theme="simple"/>
                                </td>
                               
                            </tr>
                            <tr>
                                <td nowrap="nowrap">
                                    <s:text name="LSR_DET_LBL_PRIX_PDJ"/>
                                    <s:textfield name="prixPdj" id="prixPdj" label="Pdj" value="%{prixPdj}"
                                                 maxLength="15" size="5"
                                                theme="simple"/>
                               			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                     <s:text name="LSR_DET_LBL_PDJ_INCLUS"/>
                                    <s:checkbox name="pdjInclus" value="%{pdjInclus}" id="pdjInclus"
                                                onclick="corelatePdjInclus()"
                                                theme="simple"
                                                disabled="true"/>
                                </td>
                                <td nowrap="nowrap">
                                    <s:text name="LSR_DET_LBL_PDJ_TYPE"/>
                                    <s:select list="listsProvider.petitDejs" listKey="code" listValue="libelle"
                                              name="codePetitDej" value="%{codePetitDej}"
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
    <s:if test="%{!leisuresRates}">
    <tr>
          <td>
              <fieldset>
                  <legend><s:text name="LSR_DET_LBL_BAGAGE"/>:</legend>
                  <table>
                      <tr>
                          <td nowrap="nowrap">
                              <s:text name="LSR_DET_LBL_BAG_OR"/>:
                                  <s:textfield name="bagageInOrOut" id="bagageInOrOut" label="Bagage in or out" value="%{bagageInOrOut}" maxLength="15" size="5"
                                               cssClass="formInput" theme="simple"/>
                          </td>
                          <td nowrap="nowrap">
                          <s:text name="LSR_DET_LBL_BAG_AND"/>:
                                  <s:textfield name="bagageInAndOut" id="bagageInAndOut" value="%{bagageInAndOut}" label="Bagage in and out"  maxLength="15" size="5"
                                               cssClass="formInput" theme="simple"/>
                          </td>
                      </tr>
                  </table>
                 </fieldset>
          </td>
    </tr>
    </s:if>
  </table>
</s:form>
</td>
</tr>
</table>