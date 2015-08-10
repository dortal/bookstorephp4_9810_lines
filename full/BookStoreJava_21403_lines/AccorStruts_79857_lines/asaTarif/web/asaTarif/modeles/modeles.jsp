<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 5 déc. 2008
  Time: 10:58:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <script type="text/javascript" language="Javascript">
        var ODD_CLASSNAME = "Odd";
        var EVEN_CLASSNAME = "Even";
        var HIGHLIGHT_CLASSNAME = "HighlightRow";
        function getCurrentMenuIndex () {
            return 6;
        }
        function changeLanguage() {
            var form = document.getElementById("changeForm");
            if(form.codeLangue!=null)
                form.submit();
        }
        function displayModel() {
            var form = document.getElementById("changeForm");
            var url = form.fileUrlSelected.value;
            if (url!="") {
                location.href = url;
            }
        }
    </script>
</head>
    <body>
        <s:include value="/head.jsp"/>
        <div id="mainDiv">
            <br><br><br>
            <fieldset style="width: 600px">
                <legend><s:text name="RATES_MODELES_LBL_TITRE"/>:</legend>
                <s:form id="changeForm" action="/modelBlankDocument.action">
                    <table width="100%" leftmargin="5" topmargin="2">
                        <tr>
                            <td width="20"></td>
                            <td><s:text name="RATES_MODELES_LBL_CODELANGUE"/>:</td>
                            <td>
                                <s:select list="langues" listKey="code" listValue="libelle"
                                    name="codeLangue" value="codeLangue" onchange="javascript:changeLanguage();"/>
                            </td>
                            <td width="20"></td>
                        </tr>
                        <tr>
                            <td width="20"></td>
                            <td><s:text name="RATES_MODELES_LBL_DOCUMENT"/>:</td>
                            <td>
                                <s:select list="blankDocuments" listKey="fileUrl" listValue="fileName"
                                    name="fileUrlSelected" value="fileUrlSelected"/>
                            </td>
                            <td width="20"></td>
                        </tr>
                        <tr>
                            <td width="20"></td>
                            <td colspan="2" align="right">
                                <a href="#" onclick="displayModel()"><s:text name="ALL_ALL_AFFICHER"/></a>
                            </td>
                            <td width="20"></td>
                        </tr>
                    </table>
                </s:form>
            </fieldset>
            <br><br><br>
        </div>
    </body>
</html>
