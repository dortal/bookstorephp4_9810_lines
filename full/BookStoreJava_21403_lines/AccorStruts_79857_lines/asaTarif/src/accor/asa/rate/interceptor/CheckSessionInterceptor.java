package com.accor.asa.rate.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.action.MainAction;
import com.accor.asa.rate.common.Log;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class CheckSessionInterceptor extends AbstractInterceptor  implements StrutsStatics {
    /**
     * Constantes
     */
    public static final String WELCOME_ACTION   = "welcome";

    public String intercept(ActionInvocation next) throws Exception {
        final ActionContext context = next.getInvocationContext ();
        HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
        HttpSession session =  request.getSession ();
        if (next.getAction() instanceof com.accor.asa.rate.action.LoginAction) {
            return next.invoke();
        } else if(session!=null){
            Contexte contexte = (Contexte)session.getAttribute(MainAction.CONTEXTE);
            if (contexte!=null) {
                String codeLangue = request.getParameter (MainAction.REQUEST_LOCALE);
                if(StringUtils.isNotBlank(codeLangue)) {
                    contexte.setCodeLangue(codeLangue);
                    session.setAttribute(MainAction.CONTEXTE,contexte);
                    request.setAttribute(MainAction.REQUEST_LOCALE,codeLangue.toLowerCase());
                }
                long t1 = System.currentTimeMillis();
                String nextInvoke = next.invoke();
                if (Log.isDebug) {
                    long t2 = System.currentTimeMillis();
                    StringBuffer sb = new StringBuffer("Action ");
                    sb.append(next.getAction().getClass().getName()).
                            append(" ==> took ").append((t2 - t1)).append(" millisecs");
                    Log.debug("CheckSessionInterceptor", "intercept", sb.toString());
                }
                return nextInvoke;
            } else {
                return WELCOME_ACTION;
            }
        } else {
            return WELCOME_ACTION;
        }
    }
    public void init() {
    }
    public void destroy() {
    }
}
