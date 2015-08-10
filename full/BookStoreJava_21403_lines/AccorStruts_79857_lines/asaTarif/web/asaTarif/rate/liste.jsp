<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 20 aoï¿½t 2008
  Time: 10:25:51
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
        </script>
	</head>
	<body>
		<s:include value="/head.jsp"/>
        <div id="mainDiv">
            <br>
            <s:include value="/rate/infos.jsp"/>
            <br/>
            <s:form id="changeForm" action="listBusinessRate.action" method="GET">
                <s:hidden name="codeEcran" value="%{codeEcran}"/>
                <s:text name="COM_LST_LBL_PERIODEVALIDITE"/> :
                <s:select list="periodesValidite" listKey="code" listValue="libelle"
                          id="idPeriodeValidite" name="idPeriodeValidite" value="%{idPeriodeValidite}"
                          onchange="javascript:changeValidite();"
                          theme="simple"/>
                <s:hidden name="key"/>
                <s:hidden name="screenReadOnly"/>
            </s:form>
            <div id="errorDiv">
                <s:actionmessage/>
                <s:actionerror/>
                <s:fielderror/>
            </div>
            <s:set name="urlList" value="%{'/rate/'+pagesPath+'/liste.jsp'}"/>
			<s:include value="%{#urlList}"/>
            <br/>
            <div style="text-align: center">
                <s:if test="not screenReadOnly">
                    <input type="button" class="Button" id="addButton" value="<s:text name="ALL_ALL_ADD"/>" onclick="addRate();"/>
                </s:if>
                <s:if test="rates.size > 0">
                    <input type="button" class="Button" id="exportButton" value="<s:text name="ALL_ALL_EXPORT"/>" onclick="exportRate();"/>
                </s:if>
            </div>
            <br/>
        </div>
	</body>
</html>