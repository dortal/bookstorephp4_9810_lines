<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<link href="${pageContext.request.contextPath}/css/asaRate.css" rel="stylesheet" type="text/css">
	</head>
	<body class="DefaultStyle">
		<div class="CadreBlanc">
			<s:actionerror/>
			<table style="width: 100%">
				<tr>
					<td>
						<form action="displayListContracts" name="displayForm">
							<s:hidden name="idGrille"/>
							<s:hidden name="codeRateLevel"/>
							<s:text name="COM_CTR_LBL_LISTESTATUT"/>:
                            <s:select name="status" list="listStatus" listKey="code" listValue="%{getText(cleTranslate)}" 
                                      headerKey="" headerValue="Tous"
                                      theme="simple" onchange="document.forms[0].submit()"/>
						</form>
					</td>
                    <td style="text-align: right">
                        <a href="javascript:window.close()"><s:text name="ALL_ALL_BACK"/></a>
                    </td>
                    <td style="text-align: right">
						<a href="javascript:window.print()"><s:text name="ALL_ALL_PRINT_NOW"/></a>
					</td>
				</tr>
			</table>
			<br/>
			<table class="List" style="width: 100%">
				<thead>
					<tr>
						<th><s:text name="COM_CTR_LBL_CODE"/></th>
						<th><s:text name="COM_CTR_LBL_NOM"/></th>
						<th><s:text name="COM_CTR_LBL_DATEDEBUT"/></th>
						<th><s:text name="COM_CTR_LBL_DATEFIN"/></th>
						<th><s:text name="COM_CTR_LBL_STATUT"/></th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="contracts" status="listStatus" id="r">
						<tr>
							<td width="50" align="center">
								<s:property value="code"/>
							</td>
							<td align="center">
								<s:property value="owner"/> - <s:property value="operation"/>
							</td>
							<td align="center"><s:property value="dateDebut"/></td>
							<td align="center"><s:property value="dateFin"/></td>
							<td align="center"><s:property value="status"/></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</body>
</html>