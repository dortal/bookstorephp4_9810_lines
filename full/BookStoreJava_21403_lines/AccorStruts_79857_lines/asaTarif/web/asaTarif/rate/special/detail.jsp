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
	function getCurrentMenuIndex () {
		return 1;
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

    function onChangeMarche() {
        var ckd = rateForm['tousMarches'].checked;
        if (ckd) {
            for (var i = 1; i < 6; i++)
                selectValueInCombo(rateForm['codePays' + i], "");
            for (var i = 1; i < 4; i++)
                selectValueInCombo(rateForm['codeContinent' + i], "");
        }
        for (var i = 1; i < 6; i++)
            rateForm['codePays' + i].disabled = ckd
        for (var i = 1; i < 4; i++)
            rateForm['codeContinent' + i].disabled = ckd
    }

    function onSave() {
        setFormReadOnly(rateForm, false);
        rateForm.submit();
    }
    function init(withErrors) {
        changeDateDebut();
        changeDateFin();
        onChangeMarche();
    }
</script>
<s:form action="listSpecialRate.action" name="listForm" method="GET">
    <s:hidden name="codeEcran" value="%{codeEcran}"/>
    <s:hidden name="idPeriodeValidite" value="%{idPeriodeValidite}"/>
</s:form>
<s:form  name="rateForm" id="detailForm" validate="true" action="detailSpecialRate!save.action">
	<s:hidden name="codeEcran" value="%{codeEcran}"/>
	<s:hidden name="idPeriodeValidite" value="%{idPeriodeValidite}"/>
	<s:hidden name="key" value="%{key}"/>
<table style="width: 800px">
<tr>
    <td>
        <table style="width: 100%">
            <tr style="vertical-align: top">
                <td>
                    <fieldset>
                        <legend><s:text name="SPE_DET_LBL_OFFRES"/> :</legend>
                        <table>
                            <tr>
                                <td><s:text name="SPE_DET_LBL_OFFRESPECIALE"/></td>
                                <td>
                                    <s:select list="listsProvider.offreSpeciales" listKey="code" listValue="libelle"
                                              name="codeOffreSpeciale"
                                              theme="simple"/>
                                </td>

                            </tr>
                            <tr>
                                <td><s:text name="SPE_DET_LBL_OBLIGATOIRE"/></td>
                                <td>
                                    <s:checkbox name="obligatoire" theme="simple"/>
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
            <tr>
                <td>
                    <fieldset>
                        <legend><s:text name="SPE_DET_LBL_PERIODES"/> :</legend>
                        <table>
                            <tr>
                                <td nowrap><s:text name="SPE_DET_LBL_DE"/></td>
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
                                <td nowrap align="right"><s:text name="SPE_DET_LBL_A"/>:</td>
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
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <table style="width: 100%">
            <tr>
                <td>
                    <fieldset>
                        <legend><s:text name="SPE_DET_LBL_MARCHES"/> :</legend>
                        <table width="100%" style="border-collapse: collapse">
                            <tr>
                                <td><s:text name="SPE_DET_LBL_MARCHEEMETEUR"/></td>
                                <td colspan="3">
                                    <s:text name="SPE_DET_LBL_TOUS"/>
                                    <s:checkbox name="tousMarches" theme="simple"
                                                onclick="onChangeMarche()"></s:checkbox>
                                </td>
                            </tr>
                            <tr>
                                <td rowspan="2" valign="top"><s:text name="SPE_DET_LBL_MARCHEEMETEURPAYS"/></td>
                                <td valign="top"><s:text name="SPE_DET_LBL_MARCHEPAYS"/> 1<br>
                                    <s:select list="listsProvider.pays" listKey="code" listValue="libelle"
                                              headerKey="" headerValue=""
                                              name="codePays1"
                                              theme="simple"/>
                                </td>
                                <td><s:text name="SPE_DET_LBL_MARCHEPAYS"/> 2<br>
                                    <s:select list="listsProvider.pays" listKey="code" listValue="libelle"
                                              headerKey="" headerValue=""
                                              name="codePays2"
                                              theme="simple"/>
                                </td>
                                <td><s:text name="SPE_DET_LBL_MARCHEPAYS"/> 3<br>
                                    <s:select list="listsProvider.pays" listKey="code" listValue="libelle"
                                              headerKey="" headerValue=""
                                              name="codePays3"
                                              theme="simple"/>
                                </td>
                            </tr>
                            <tr>
                                <td><s:text name="SPE_DET_LBL_MARCHEPAYS"/> 4<br>
                                    <s:select list="listsProvider.pays" listKey="code" listValue="libelle"
                                              headerKey="" headerValue=""
                                              name="codePays4"
                                              theme="simple"/>
                                </td>
                                <td><s:text name="SPE_DET_LBL_MARCHEPAYS"/> 5<br>
                                    <s:select list="listsProvider.pays" listKey="code" listValue="libelle"
                                              headerKey="" headerValue=""
                                              name="codePays5"
                                              theme="simple"/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td valign="top"><s:text name="SPE_DET_LBL_MARCHEEMETEURCONTINENT"/></td>
                                <td valign="top">
                                    <s:text name="SPE_DET_LBL_MARCHECONTINENT"/> 1<br>
                                    <s:select list="listsProvider.continents" listKey="code" listValue="libelle"
                                              headerKey="" headerValue=""
                                              name="codeContinent1"
                                              theme="simple"/>
                                </td>
                                <td><s:text name="SPE_DET_LBL_MARCHECONTINENT"/> 2<br>
                                    <s:select list="listsProvider.continents" listKey="code" listValue="libelle"
                                              headerKey="" headerValue=""
                                              name="codeContinent2"
                                              theme="simple"/>
                                </td>
                                <td><s:text name="SPE_DET_LBL_MARCHECONTINENT"/> 3<br>
                                    <s:select list="listsProvider.continents" listKey="code" listValue="libelle"
                                              headerKey="" headerValue=""
                                              name="codeContinent3"
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
<tr>
    <td>
        <table style="width: 100%">
            <tr>
                <td>
                    <fieldset>
                        <legend><s:text name="SPE_DET_LBL_PRIXTITRE"/> :</legend>
                        <table>
                            <tr>
                                <td><s:text name="SPE_DET_LBL_TYPEPRIX"/>:</td>
                                <td>
                                    <s:select list="listsProvider.typesPrix" listKey="code" listValue="libelle"
                                              name="idTypePrix"
                                              theme="simple"></s:select>
                                </td>
                            </tr>
                            <tr>
                                <td><s:text name="SPE_DET_LBL_PRIX"/>:</td>
                                <td>
                                    <s:textfield name="prix" size="5" cssClass="formInput" theme="simple"/>
                                    <s:select list="listsProvider.devises" listKey="codeDevise" listValue="codeDevise"
                                              name="codeDevise" value="%{codeDevise}" emptyOption="true"
                                              theme="simple"/>
                                </td>
                            </tr>
                            <tr>
                                <td><s:text name="SPE_DET_LBL_UNITEPRIX"/>:</td>
                                <td>
                                    <s:select list="listsProvider.unitesPrix" listKey="code" listValue="libelle"
                                              name="idUnitePrix"
                                              theme="simple"></s:select>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>

                        </table>
                    </fieldset>
                </td>
                <td valign="top">
                    <fieldset>
                        <legend><s:text name="SPE_DET_LBL_COMMENTAIRE"/> :</legend>
                        <s:textarea name="commentaire" cols="100" rows="5" theme="simple"></s:textarea>
                    </fieldset>
                </td>
            </tr>
        </table>
    </td>
</tr>
</table>
</s:form>