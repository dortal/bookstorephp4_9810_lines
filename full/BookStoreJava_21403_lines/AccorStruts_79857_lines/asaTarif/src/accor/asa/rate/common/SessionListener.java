package com.accor.asa.rate.common;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.accor.asa.commun.habilitation.metier.AsaModule;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.PoolCommunFactory;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class SessionListener implements HttpSessionBindingListener {

    private String login;

    public SessionListener(String login) {
        this.login = login;
    }

    public void valueBound(HttpSessionBindingEvent event) {
        Log.debug("SessionListener", "valueBound", login + " : " + event.getName() + " : " + event.getSession());
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        try {
            Log.debug(login, "[SessionListener][valueUnbound]", login + " : " + event.getName() + " : " + event.getSession());
            Contexte contexte = new Contexte();
            contexte.setCodeUtilisateur( login );
            PoolCommunFactory.getInstance().getHabilitationFacade().deconnexion( login, AsaModule.NAME_APP_TARIF, contexte );
        }
        catch (Exception e) {
            Log.major(login, "SessionListener", "valueUnbound", "Deconnexion de l'utilisateur : " + login, e);
        }
    }


}
