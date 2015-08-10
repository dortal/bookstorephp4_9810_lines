<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 10 sept. 2008
  Time: 12:26:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/utils.js"></script>
    <script type="text/javascript">
        function getCurrentMenuIndex () {
            return 2;
        }
        function onChangeTaxe() {
            document.getElementById("tdCodeTaxe").innerHTML = rateForm['code'].value;
        }
        function onChangeRateLevels() {
            var rateLevels = rateForm['rateLevels'];
            var listProd = "";
            for (var i = 0; i < rateLevels.length; i++)
                if (rateLevels[i].selected)
                    listProd += (listProd == "") ? rateLevels[i].value : ", " + rateLevels[i].value;
            document.getElementById("tdRateLevels").innerHTML = listProd;

        }
        function onChangeDateDebut() {
            var date = rateForm['dateDebut'].value;
            if (date != '' && validDate(date))
                document.getElementById("tdDateDebut").innerHTML = getDayFromDate(stringToDate(date));
        }
        function onChangeDateFin() {
            var date = rateForm['dateFin'].value;
            if (date != '' && validDate(date))
                document.getElementById("tdDateFin").innerHTML = getDayFromDate(stringToDate(date));
        }
        function onChangeTypePrix() {
            var typePrix  = rateForm['idTypePrix'].value;
            var isTypePrixPourcentage  = typePrix==<s:property value="typePrixPourcentage"/>;
            if (isTypePrixPourcentage)
                rateForm['codeDevise'].value = '';
            rateForm['codeDevise'].disabled  = isTypePrixPourcentage;
        }
        function corelateSupprime() {
            var supprime = <s:property value="supprime"/>;
            if(supprime) {
                rateForm['code'].disabled           = supprime;
                rateForm['idTypePrix'].disabled     = supprime;
                rateForm['idUniteTaxe'].disabled    = supprime;
                rateForm['inclus'].disabled         = supprime;
                rateForm['montant'].disabled        = supprime;
                rateForm['codeDevise'].disabled     = supprime;
                rateForm['rateLevels'].disabled     = supprime;
            }
        }
        function onSave() {
            setFormReadOnly(rateForm,false);
            rateForm.submit();
        }
        function init(withErrors) {
            onChangeTaxe();
            onChangeRateLevels();
            onChangeDateDebut();
            onChangeDateFin();
            onChangeTypePrix();
            corelateSupprime();
        }
    </script>
    <s:include value="/calendar.jsp"/>
</head>
<body onload="init(false)">
		<s:include value="/head.jsp"/>
		<div id="mainDiv">
            <br>
            <s:include value="/taxe/infos.jsp"/>
            <br/>
            <div id="errorDiv">
                <s:actionmessage/>
                <s:actionerror/>
                <s:fielderror/>
            </div>
            <s:form action="listTaxe.action" name="listForm" method="GET">
                    <s:hidden name="idTaxe" value="%{idTaxe}"/>
            </s:form>
            <table width="800px">
                <tr>
                    <td>
                <s:form  name="rateForm" id="detailForm" validate="true" action="detailTaxe!save.action">
                <s:hidden name="idTaxe" value="%{idTaxe}"/>
                <s:hidden name="supprime" value="%{supprime}"/>
                <table width="100%">
                    <tr>
                        <td>
                            <table style="width: 100%">
                                <tr style="vertical-align: top">
                                    <td>
                                        <fieldset>
                                            <legend><s:text name="TAX_DET_LBL_TAXES"/> :</legend>
                                            <table>
                                                <tr>
                                                    <td>
                                                        <s:select list="listsProvider.tarsHotelTaxes" listKey="code" listValue="code+' >> '+nom+' >> '+type"
                                                                  name="code" id="code"
                                                                  onchange="onChangeTaxe();"
                                                                  theme="simple"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td id="tdCodeTaxe" class="HighLight1" align="center"></td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </td>
                                </tr>
                                <tr style="vertical-align: top">
                                    <td>
                                        <fieldset>
                                            <legend><s:text name="TAX_DET_LBL_DATES"/> :</legend>
                                            <table>
                                                <tr>
                                                    <td>
                                                        <table>
                                                            <tr>
                                                                <td nowrap><s:text name="TAX_DET_LBL_DATEDU"/> </td>
                                                                <td id="tdDateDebut" nowrap align="center"></td>
                                                                <td nowrap>
                                                                    <s:textfield id="dateDebut"
                                                                                 name="dateDebut"
                                                                                 maxLength="10" size="10"
                                                                                 onchange="onChangeDateDebut();"
                                                                                 theme="simple"/>
                                                                    <img id="bDateDebut" src="images/picto_calendrier.gif" border="0"
                                                                            onclick="calShow(calID('dateDebut'),event);">
                                                                </td>
                                                                <td nowrap="nowrap" align="right"><s:text name="TAX_DET_LBL_DATEAU"/> :</td>
                                                                <td id="tdDateFin" nowrap align="center"></td>
                                                                <td nowrap>
                                                                    <s:textfield id="dateFin"
                                                                                 name="dateFin"
                                                                                 maxLength="10" size="10"
                                                                                 onchange="onChangeDateFin()"
                                                                                 theme="simple"/>
                                                                    <img id="bDateFin" src="images/picto_calendrier.gif" border="0"
                                                                            onclick="calShow(calID('dateFin'),event);">
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fieldset>
                                            <legend><s:text name="TAX_DET_LBL_AMOUNTTITLE"/> :</legend>
                                            <table>
                                                <tr>
                                                    <td colspan="2">
                                                        <s:text name="TAX_DET_LBL_INCLUS"/>
                                                        <s:checkbox name="inclus" id="inclus" value="%{inclus}"
                                                                    theme="simple"/>
                                                    </td>
                                                    <td width="20"></td>
                                                    <td><s:text name="TAX_DET_LBL_UNIT"/> :</td>
                                                    <td align="left" colspan="4">
                                                        <s:select list="listsProvider.unitesTaxe" listKey="code" listValue="libelle"
                                                                  name="idUniteTaxe" value="%{idUniteTaxe}"
                                                                  theme="simple"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><s:text name="TAX_DET_LBL_AMOUNT"/> :</td>
                                                    <td>
                                                        <s:textfield name="montant" size="5" cssClass="formInput" theme="simple"/>
                                                    </td>
                                                    <td width="20"></td>
                                                    <td><s:text name="TAX_DET_LBL_TYPE"/> :</td>
                                                    <td>
                                                        <s:select list="listsProvider.typesPrix" listKey="code" listValue="libelle"
                                                                  name="idTypePrix" value="%{idTypePrix}"
                                                                  onchange="onChangeTypePrix();"
                                                                  theme="simple"/>
                                                    </td>
                                                    <td width="20"></td>
                                                    <td><s:text name="TAX_DET_LBL_CURRENCY"/> :</td>
                                                    <td align="left">
                                                        <s:select list="listsProvider.devises" listKey="codeDevise" listValue="codeDevise"
                                                                  headerKey="" headerValue=""
                                                                  name="codeDevise" value="%{codeDevise}"
                                                                  theme="simple"/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <fieldset>
                                <legend><s:text name="TAX_DET_LBL_RATELEVELS"/> :</legend>
                                <table>
                                    <tr>
                                        <td>
                                            <s:select list="listsProvider.tarsHotelRateLevels" listKey="code" listValue="code+' >> '+libelle"
                                                      name="rateLevels" id="rateLevels"
                                                      multiple="true" size="11"
                                                      onchange="onChangeRateLevels();"
                                                      theme="simple"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="tdRateLevels" class="HighLight2" align="center"></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </td>
                    </tr>
                </table>
            </s:form>
            </td>
            </tr>
            </table>            <table>
                <tr>
                    <td align="center">
                        <table>
                            <tr>
                                <td align="right" width="200">
                                    <s:if test="!screenReadOnly">
                                        <input type="button" value="<s:text name="ALL_ALL_SAVE"/>" class="Button" style="width:100px" onclick="onSave()">
                                    </s:if>
                                </td>
                                <td width="10">
                                </td>
                                <td  width="200">
                                    <s:if test="!screenReadOnly">
                                        <input type="button" value="<s:text name="ALL_ALL_CANCEL"/>" class="Button" style="width:100px" onclick="listForm.submit()">
                                    </s:if>
                                    <s:else>
                                        <input type="button" value="<s:text name="ALL_ALL_BACK"/>" class="Button" style="width:100px" onclick="listForm.submit()">
                                    </s:else>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
	</body>
</html>
