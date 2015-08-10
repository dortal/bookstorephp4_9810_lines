<%@page import="com.accor.asa.rate.util.RateScrean"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
var menu = [
            <%=RateScrean.displayGroupeOption(session, RateScrean.RATES_MENU_GROUPE_BUSINESSRATES)%>,
            <%=RateScrean.displayGroupeOption(session, RateScrean.RATES_MENU_GROUPE_TOURISMRATES)%>,
            <%=RateScrean.displayGroupeOption(session, RateScrean.RATES_MENU_GROUPE_TAXESANDSERVICES)%>,
            <%=RateScrean.displayGroupeOption(session, RateScrean.RATES_MENU_GROUPE_ENDSEIZURE)%>,
            <%=RateScrean.displayGroupeOption(session, RateScrean.RATES_MENU_GROUPE_VALIDATERATES)%>,
            <%=RateScrean.displayGroupeOption(session, RateScrean.RATES_MENU_GROUPE_SEARCHHOTEL)%>,
            <%=RateScrean.displayGroupeOption(session, RateScrean.RATES_MENU_GROUPE_USERGUIDE)%>
            ];
		if (window.addEventListener) {
			window.addEventListener("load", initMenu, false);
			window.addEventListener("load", initStyle, false);
		} else if (window.attachEvent) {
			window.attachEvent("onload", initMenu);
			window.attachEvent("onload", initStyle);
		}
</script>		