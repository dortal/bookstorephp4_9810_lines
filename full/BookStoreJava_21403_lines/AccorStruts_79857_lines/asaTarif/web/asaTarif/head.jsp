<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 22 mai 2008
  Time: 10:06:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="${pageContext.request.contextPath}/css/asaRate.css" rel="stylesheet" type="text/css">
<s:if test="#session.contexte != null">
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/menu.js"></script>
	<s:include value="/menu.jsp"/>
	<script type="text/javascript">
		function changerLangue() {
			var form = document.getElementById("changeLangueForm");
			if(form.request_locale!=null) {
				form.action = window.location.href;
				form.submit();
			}
		}
	</script>
</s:if>
<div id="headDiv">
	<div id="head">
		<table>
			<tr>
				<td style="text-align: left; width: 200px">
                    <s:if test="#session.hotel!=null &&
                                #session.hotel.chain.code in {'ADG','ALS','ASE','COR','ETP','FOR','HLB','IBI','MER','MGA','MOT','NOV','PUL','RDR','SOF','STD','SUI'} &&
                                !searchScreen">
                        <s:set name="urlImage" value="%{'/images/logo_'+#session.hotel.chain.code+'.gif'}"/>
                        <img src="${pageContext.request.contextPath}<s:property value="%{#urlImage}"/>" alt="Logo Marque"/>
                    </s:if>
                    <s:else>
                        <img src="${pageContext.request.contextPath}/images/logo_accor.jpg" alt="Logo Accor"/>
                    </s:else>
                </td>
				<td class="AsaTitle"><s:text name="COM_HEAD_LBL_TITRE"/></td>
				<td style="text-align: right; width: 200px">
					<img src="${pageContext.request.contextPath}/images/fleche.gif" alt="<s:text name="COM_HEAD_LBL_CONTACTUS"/>"/> <a href="login!goContacts.action"><s:text name="COM_HEAD_LBL_CONTACTUS"/></a><br/>
					<img src="${pageContext.request.contextPath}/images/fleche.gif" alt="<s:text name="COM_HEAD_LBL_LOGIN"/>"/> <a href="login!disconnect.action"><s:text name="COM_HEAD_LBL_LOGIN"/></a><br/>
					<img src="${pageContext.request.contextPath}/images/fleche.gif" alt="<s:text name="COM_HEAD_LBL_INTRACCOR"/>"/> <a href="http://intraccor.accor.net:8080/"><s:text name="COM_HEAD_LBL_INTRACCOR"/></a>
				</td>
			</tr>
		</table>
	</div>
	<br/>
	<s:if test="#session.contexte != null">
		<div id="menu"></div>
		<s:if test="#session.contexte.codeLangue != null">
			<div id="languages">
				<s:form id="changeLangueForm" action="?" theme="simple">
					<s:select list="#session.langues" listKey="code" listValue="libelle"
						name="request_locale" value="#session.contexte.codeLangue" onchange="javascript:changerLangue();"/>
				</s:form>
			</div>
		</s:if>
		<div style="clear: both"></div>
	</s:if>
</div>