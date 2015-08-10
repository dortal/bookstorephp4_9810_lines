<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 6 oct. 2008
  Time: 12:13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/asaRate.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/utils.js"></script>
    <s:include value="/calendar.jsp"/>
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
        function onSave() {
               setFormReadOnly(rateForm,false);
               rateForm.submit();
        }
        function init(withErrors) {
            changeDateDebut();
            changeDateFin();
        }
    </script>
</head>
<body onload="init(false); initStyle();">
        <div id="mainDiv">
            <br>
            <s:include value="infos.jsp"/>
            <s:form action="listBlackOutDates.action" name="listForm"  method="GET">
                <s:hidden name="screenReadOnly" value="%{screenReadOnly}"/>
                <s:hidden name="idGrille" value="%{idGrille}"/>
            </s:form>
            <div id="errorDiv">
                <s:actionmessage/>
                <s:actionerror/>
                <s:fielderror/>
            </div>
            <table>
                <tr>
                    <td>
                        <s:form name="rateForm" id="detailForm" validate="true" action="detailBlackOutDates!save.action">
                            <s:hidden name="screenReadOnly" value="%{screenReadOnly}"/>
                            <s:hidden name="idGrille" value="%{idGrille}"/>
                            <s:hidden name="key" value="%{key}"/>
                            <table style="width: 100%">
                                <tr style="vertical-align: top">
                                    <td>
                                        <fieldset style="width: 100%">
                                            <legend><s:text name="BOD_DET_LBL_PERIODES"/>:</legend>
                                            <table style="width: 100%">
                                                <tr>
                                                    <td>
                                                        <table>
                                                            <tr>
                                                                <td nowrap><s:text name="BOD_DET_LBL_FROM"/>:</td>
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
                                                                <td nowrap align="right"><s:text name="BOD_DET_LBL_TO"/>:</td>
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
                                            </table>
                                        </fieldset>
                                    </td>
                                </tr>
                            </table>
                        </s:form>
                    </td>
                </tr>
            </table>
            <table>
                <tr>
                    <td align="center">
                        <table>
                            <tr>
                                <td align="right" width="200">
                                    <s:if test="not screenReadOnly">
                                        <input type="button" value="<s:text name="ALL_ALL_SAVE"/>" class="Button" style="width:100px" onclick="onSave()">
                                    </s:if>
                                </td>
                                <td width="10">
                                </td>
                                <td  width="200">
                                    <s:if test="not screenReadOnly">
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
