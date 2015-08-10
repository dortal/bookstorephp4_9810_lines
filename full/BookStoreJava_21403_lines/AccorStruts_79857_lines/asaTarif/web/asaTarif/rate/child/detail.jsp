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
    function getCurrentMenuIndex() {
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
    function init(withErrors) {
        changeProduct();
        changeRateLevel();
        changeDateDebut();
        changeDateFin();
        onCkAgeActive(0);
        onCkAgeActive(1);
        onCkAgeActive(2);
    }
    function onCkAgeActive(idx) {
        var ckd = rateForm['ageActiv' + idx].checked;
        rateForm['minAge' + idx].disabled = !ckd;
        rateForm['maxAge' + idx].disabled = !ckd;
        for (var j = 0; j < 3; j++) {
            document.getElementById('idTypePrix' + idx + j).disabled = !ckd;
            document.getElementById('montant' + idx + j).disabled = !ckd;
            onChangeTypePrix(idx, j)
        }
    }
    function onChangeTypePrix(age, service) {
        var v = document.getElementById('idTypePrix' + age + service).value;
        if (v == 0) {
            document.getElementById('montant' + age + service).disabled = true;
            document.getElementById('montant' + age + service).value = ""
        } else {
            document.getElementById('montant' + age + service).disabled = false;
        }
    }
    function onSave() {
        setFormReadOnly(rateForm, false);
        rateForm.submit();
    }
</script>
<s:form action="listChildRate.action" name="listForm"  method="GET">
    <s:hidden name="codeEcran" value="%{codeEcran}"/>
    <s:hidden name="idPeriodeValidite" value="%{idPeriodeValidite}"/>
</s:form>
<s:form id="detailForm" validate="true" action="detailChildRate!save.action" name="rateForm">
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
                        <legend>
                            <s:text name="ENF_DET_LBL_ROOM_RATE"/> :
                        </legend>
                        <table>
                            <tr>
                                <td>
                                    <s:select list="listsProvider.rooms" listKey="code"
                                              listValue="code+' >> '+nom+' >> '+nbPers"
                                              name="codesProduit"
                                              multiple="true" size="3"
                                              onchange="changeProduct()"
                                              theme="simple"/>
                                </td>
                                <td>
                                    <s:select list="listsProvider.rateLevels" listKey="code"
                                              listValue="code+' >> '+libelle"
                                              name="codeRateLevel" id="codeRateLevel"
                                              onchange="changeRateLevel();"
                                              theme="simple"/>
                                </td>
                            </tr>
                            <tr>
                                <td id="tdCodeProduit" class="Highlight1" align="center">&nbsp;</td>
                                <td id="tdRateLevel" class="Highlight1" align="center">&nbsp;</td>
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
                        <legend>
                            <s:text name="ENF_DET_LBL_PERIODES"/> :
                        </legend>
                        <table>
                            <tr>
                                <td nowrap>
                                    <s:text name="ENF_DET_LBL_FROM"/>
                                </td>
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
                                <td nowrap align="right">
                                    <s:text name="ENF_DET_LBL_TO"/>
                                </td>
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
                    </fieldset>
                </td>
                <td>
                    <fieldset>
                        <legend>
                            <s:text name="ENF_DET_LBL_ATTRIBUTES"/> :
                        </legend>
                        <table>
                            <tr>
                                <td nowrap="nowrap" width="50%">
                                    <s:text name="ENF_DET_LBL_NB_MAX_CHILD"/>
                                    <s:textfield name="maxChild" maxLength="2" size="2" theme="simple"/>
                                </td>
                                <td nowrap="nowrap">
                                    <s:text name="ENF_DET_LBL_NB_MAX_ADULT"/>
                                    <s:textfield name="maxAdult" maxLength="2" size="2" theme="simple"/>
                                </td>

                            </tr>
                            <tr>
                                <td nowrap="nowrap" width="50%">
                                    <s:text name="ENF_DET_LBL_CHAMBRE_SEPARE"/>
                                    <s:checkbox name="chambreSepare" theme="simple" label="Chambre separe"/>
                                </td>
                                <td nowrap="nowrap">
                                    <s:text name="ENF_DET_LBL_DEVISE"/>
                                    <s:select list="listsProvider.devises" name="codeDevise" listKey="codeDevise"
                                              listValue="codeDevise"
                                              value="%{codeDevise}"
                                              label="a" theme="simple"/>
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
    <td colspan="2" align="center" class="InfoMessage">
        <br/>
        <s:text name="ENF_DET_LBL_RATE_MSG"/>
        <br/><br/>
    </td>
</tr>
<tr>
<td colspan="2">
<table style="border-collapse: collapse; text-align: center">
<tr>
    <td style="border-bottom: 1px solid Gray">
        <s:text name="ENF_DET_LBL_AGE"/>
    </td>
    <td style="border-bottom: 1px solid Gray">
        <s:text name="ENF_DET_LBL_AGE_FROM"/>
    </td>
    <td style="border-bottom: 1px solid Gray">
        <s:text name="ENF_DET_LBL_AGE_TO"/>
    </td>
    <td style="border-bottom: 1px solid Gray">
        <s:text name="ENF_DET_LBL_ACCOMMODATION"/>
    </td>
    <td style="border-bottom: 1px solid Gray">
        <s:text name="ENF_DET_LBL_BREAKFAST"/>
    </td>
    <td style="border-bottom: 1px solid Gray">
        <s:text name="ENF_DET_LBL_MEAL"/>
    </td>
</tr>

<tr>
    <td width="50">
        <s:checkbox name="ageActiv0" label="" theme="simple" onclick="onCkAgeActive(0)"></s:checkbox>
    </td>
    <td width="50">
        <s:textfield name="minAge0" theme="simple" size="2"></s:textfield>
    </td>
    <td width="50">
        <s:textfield name="maxAge0" theme="simple" size="2"></s:textfield>
    </td>
    <td width="200">
        <s:select list="listsProvider.typesPrix" listKey="code" listValue="libelle"
                  name="accomodationIdTypePrix0"
                  onchange="onChangeTypePrix(0,0)"
                  theme="simple" id='idTypePrix00'/>
        <s:textfield name="accomodationMontant0" theme="simple" size="3" id="montant00"/>
    </td>
    <td width="200">
        <s:select list="listsProvider.typesPrix" listKey="code" listValue="libelle"
                  name="breakfastIdTypePrix0"
                  onchange="onChangeTypePrix(0,1)"
                  theme="simple" id='idTypePrix01'/>
        <s:textfield name="breakfastMontant0" theme="simple" size="3" id="montant01"/>
    </td>
    <td width="200">
        <s:select list="listsProvider.typesPrix" listKey="code" listValue="libelle"
                  name="mealIdTypePrix0"
                  onchange="onChangeTypePrix(0,2)"
                  theme="simple" id='idTypePrix02'/>
        <s:textfield name="mealMontant0" cssClass="formInput" theme="simple" size="3" id="montant02"/>
    </td>
</tr>


<tr>
    <td width="50">
        <s:checkbox name="ageActiv1" label="" theme="simple" onclick="onCkAgeActive(1)"></s:checkbox>
    </td>
    <td width="50">
        <s:textfield name="minAge1" theme="simple" size="2"></s:textfield>
    </td>
    <td width="50">
        <s:textfield name="maxAge1" theme="simple" size="2"></s:textfield>
    </td>
    <td width="200">
        <s:select list="listsProvider.typesPrix" listKey="code" listValue="libelle"
                  name="accomodationIdTypePrix1"
                  onchange="onChangeTypePrix(1,0)"
                  theme="simple" id='idTypePrix10'/>
        <s:textfield name="accomodationMontant1" theme="simple" size="3" id="montant10"/>
    </td>
    <td width="200">
        <s:select list="listsProvider.typesPrix" listKey="code" listValue="libelle"
                  name="breakfastIdTypePrix1"
                  onchange="onChangeTypePrix(1,1)"
                  theme="simple" id='idTypePrix11'/>
        <s:textfield name="breakfastMontant1" theme="simple" size="3" id="montant11"/>
    </td>
    <td width="200">
        <s:select list="listsProvider.typesPrix" listKey="code" listValue="libelle"
                  name="mealIdTypePrix1"
                  onchange="onChangeTypePrix(1,2)"
                  theme="simple" id='idTypePrix12'/>
        <s:textfield name="mealMontant1" cssClass="formInput" theme="simple" size="3" id="montant12"/>
    </td>
</tr>

<tr>
    <td width="50">
        <s:checkbox name="ageActiv2" label="" theme="simple" onclick="onCkAgeActive(2)"></s:checkbox>
    </td>
    <td width="50">
        <s:textfield name="minAge2" theme="simple" size="2"></s:textfield>
    </td>
    <td width="50">
        <s:textfield name="maxAge2" theme="simple" size="2"></s:textfield>
    </td>
    <td width="200">
        <s:select list="listsProvider.typesPrix" listKey="code" listValue="libelle"
                  name="accomodationIdTypePrix2"
                  onchange="onChangeTypePrix(2,0)"
                  theme="simple" id='idTypePrix20'/>
        <s:textfield name="accomodationMontant2" theme="simple" size="3" id="montant20"/>
    </td>
    <td width="200">
        <s:select list="listsProvider.typesPrix" listKey="code" listValue="libelle"
                  name="breakfastIdTypePrix2"
                  onchange="onChangeTypePrix(2,1)"
                  theme="simple" id='idTypePrix21'/>
        <s:textfield name="breakfastMontant2" theme="simple" size="3" id="montant21"/>
    </td>
    <td width="200">
        <s:select list="listsProvider.typesPrix" listKey="code" listValue="libelle"
                  name="mealIdTypePrix2"
                  onchange="onChangeTypePrix(2,2)"
                  theme="simple" id='idTypePrix22'/>
        <s:textfield name="mealMontant2" cssClass="formInput" theme="simple" size="3" id="montant22"/>
    </td>
</tr>

</table>
</td>
</tr>
</table>
</s:form>