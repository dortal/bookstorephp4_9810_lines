<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 20 ao�t 2008
  Time: 11:41:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/utils.js"></script>
    <s:include value="/calendar.jsp"/>
</head>
<body onload="init(false)">
		<s:include value="/head.jsp"/>
		<div id="mainDiv">
            <br>
            <s:include value="/rate/infos.jsp"/>
            <br/>
            <table>
                <tr>
                    <td align="center">
                        <s:text name="COM_DET_LBL_PERIODEVALIDITE"/>: <s:property value="grille.periodeValidite.libelle"/> 
                    </td>
                </tr>
            </table>
            <div id="errorDiv">
                <s:actionmessage/>
                <s:actionerror/>
                <s:fielderror/>
            </div>
            <s:set name="urlDetail" value="%{'/rate/'+pagesPath+'/detail.jsp'}"/>
			<s:include value="%{#urlDetail}"/>
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