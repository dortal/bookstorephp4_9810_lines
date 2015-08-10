<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 6 oct. 2008
  Time: 12:13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/asaRate.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" language="Javascript">
        var ODD_CLASSNAME = "Odd";
        var EVEN_CLASSNAME = "Even";
        var HIGHLIGHT_CLASSNAME = "HighlightRow";
        function getCurrentMenuIndex () {
            return 1;
        }
        function addRate() {
            var form = document.getElementById("changeForm");
           form.target = "_self";
           form.action = "detailBlackOutDates!showCreateNew.action";
           form.key.value = "";
           form.submit();
        }
        function displayRate(key) {
            var form = document.getElementById("changeForm");
            form.target = "_self";
            form.action = "detailBlackOutDates!showEdit.action";
            form.key.value = key;
            form.submit();
        }
        function editRate(key) {
            var form = document.getElementById("changeForm");
            form.target = "_self";
            form.action = "detailBlackOutDates!showEdit.action";
            form.key.value = key;
            form.submit();
        }
        function duplicateRate(key) {
            var form = document.getElementById("changeForm");
            form.target = "_self";
            form.action = "detailBlackOutDates!showDuplicate.action";
            form.key.value = key;
            form.submit();
        }
        function deleteRate(key) {
            if(confirm('<s:text name="COM_RAT_MSG_ALERTDELETE"/>')) {
                var form = document.getElementById("changeForm");
                form.target = "_self";
                form.action = "listBlackOutDates!delete.action";
                form.key.value = key;
                form.submit();
            }
        }
    </script>
</head>
    <body onload="javascript:initStyle()">
        <div id="mainDiv">
            <br>
            <s:include value="infos.jsp"/>
            <br/>
            <s:form id="changeForm" action="listBlackOutDates.action" method="GET">
                <s:hidden name="screenReadOnly" value="%{screenReadOnly}"/>
                <s:hidden name="idGrille" value="%{idGrille}"/>
                <s:hidden name="key"/>
            </s:form>
            <div id="errorDiv">
                <s:actionmessage/>
                <s:actionerror/>
                <s:fielderror/>
            </div>
            <s:if test="rates.size > 0">
                <table width="100%" class="List">
                  <thead>
                    <tr>
                        <th colspan="2"><s:text name="BOD_LST_LBL_PERIODES"/></th>
                        <th colspan="3" rowspan="2"></th>
                    </tr>
                    <tr>
                        <th><s:text name="BOD_LST_LBL_DU"/></th>
                        <th><s:text name="BOD_LST_LBL_AU"/></th>
                    </tr>
                  </thead>
                  <tbody>
                    <s:iterator value="rates" status="listStatus" id="rate">
                        <s:if test="#listStatus.odd == true">
                            <tr id="tr<s:property value="key"/>" class="Even">
                        </s:if>
                        <s:else>
                            <tr id="tr<s:property value="key"/>" class="Odd">
                        </s:else>
                        <td align="center"><s:property value="#rate.dateDebut"/></td>
                        <td align="center"><s:property value="#rate.dateFin"/></td>
                        <td>
                            <s:if test="not screenReadOnly">
                                <s:url  value="images/picto_edit.jpg" id="img"/>
                                <a href="javascript:editRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                            </s:if>
                            <s:else>
                                <s:url  value="images/picto_detail.jpg" id="img"/>
                                <a href="javascript:displayRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                            </s:else>
                        </td>
                        <td>
                            <s:if test="not screenReadOnly">
                                <s:url  value="images/picto_duplicate.jpg" id="img"/>
                                <a href="javascript:duplicateRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                            </s:if>
                        </td>
                        <td>
                            <s:if test="not screenReadOnly">
                                <s:url  value="images/picto_delete.jpg" id="img"/>
                                <a href="javascript:deleteRate('<s:property value="key"/>')"><img src="<s:property value= "#img"/>" border="0"></a>
                            </s:if>
                        </td>
                        </tr>
                    </s:iterator>
                  </tbody>
               </table>
            </s:if>            <br/>
            <div style="text-align: center">
                <s:if test="not screenReadOnly">
                    <input type="button" class="Button" id="addButton" value="<s:text name="ALL_ALL_ADD"/>" onclick="addRate();"/>
                </s:if>
                <input type="button" class="Button" id="addButton" value="<s:text name="ALL_ALL_BACK"/>" onclick="window.close();"/>
            </div>
            <br/>
        </div>
	</body>
</html>