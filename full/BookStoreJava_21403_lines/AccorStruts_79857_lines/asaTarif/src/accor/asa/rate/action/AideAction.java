package com.accor.asa.rate.action;

import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.utiles.StaticDocs;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.common.Log;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;
import com.accor.commun.internationalisation.TranslatorImagePath;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.Preparable;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

import java.io.IOException;

/**
 * Cette servlet permet de récupérer l'accès à l'aide en ligne depuis n'importe quelle page de l'application.
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Results({
    @Result(name = Action.SUCCESS, type = ServletDispatcherResult.class, value = "/enter.jsp"),
    @Result(name = Action.ERROR,   type = ServletDispatcherResult.class, value = "/error.jsp")
})
public class AideAction  extends MainAction implements Preparable {

    private static String DEFAULT_URL = "main.jsp";

    private String page;
    private String url;
    private String rootHelp;
    private String rootRecommandation;


    /**
     * Préparer l'action
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void prepare() throws Exception {
        setRootHelp(StaticDocs.getInstance().getAideUrl( StaticDocs.MODULE_TARIF, "" ));
        setRootRecommandation(StaticDocs.getInstance().getRecommandationUrl());

    }


    public String execute() {
        setUrl(DEFAULT_URL);
        return Action.SUCCESS;
    }

    /**
     * Acces to the login form
     *
     * @return
     */
    public String displayAide() {
        Contexte contexte = getAsaContexte();
		String codeLangue = contexte.getCodeLangue();
        try {
			if (codeLangue == null)
                throw new RatesTechnicalException("Language code is null");
			String page = getPage();
			if (page == null) {
			  throw new RatesTechnicalException("Page code is null");
			} else {
                //todo: Implementer un service pour recuperer l'ecran du cache
                //todo: avec l'url de la page d'aide.
                page =  "Aide_en_ligne.html";
			    if(StringUtils.isBlank(page))
                    throw new RatesTechnicalException("Page screen is null");
            }
            Translator trans = TranslatorFactory.getTranslator(codeLangue, true);
			// Récupérer le nom du répertoire dans lequel se trouve l'aide dans la langue demandée
			String repNom = ((TranslatorImagePath)trans).getImagePath();
            String urlDemandee = getRootHelp() + repNom + "/" + page;
            java.net.URL aideUrl = new java.net.URL( urlDemandee );
            try {
	          aideUrl.openStream();
            } catch( IOException e ) {
	          urlDemandee = getRootHelp() + page;
            }
            setUrl(urlDemandee);
            return Action.SUCCESS;
        } catch (Exception e) {
          		Log.major(contexte.getCodeUtilisateur(),"AideAction", "doGet", e.getMessage(), e);
          		return Action.ERROR;
        }
    }


    /**
     * Acces to the login form
     *
     * @return
     */
    public String displayRecommandation() {
        Contexte contexte = getAsaContexte();
		String codeLangue = contexte.getCodeLangue();
		try {
          Translator trans = TranslatorFactory.getTranslator(codeLangue, true);
          String urlRecommandation = DEFAULT_URL;
            // Test si la variable nom_chaine est positionné.
            // Si c'est le cas, renvoie vers la page de recommandation, sinon, vers main.htm
            Hotel hotel = (Hotel)getFromSession(HOTEL);
            if(hotel != null ) {
                // Récupérer le nom du répertoire dans lequel se trouve l'aide dans la langue demandée
                String repNom = ((TranslatorImagePath) trans).getImagePath();
                String fileName = (hotel.getChainCode() + ".html").toLowerCase();
                urlRecommandation = new StringBuffer(getRootRecommandation()).append(repNom).append("/").append(fileName).toString();
                if(StaticDocs.getInstance().checkUrl(urlRecommandation)==null)
                        urlRecommandation = getRootRecommandation() + fileName;
            }
            setUrl(urlRecommandation);
            return Action.SUCCESS;
        } catch (Exception e) {
          		Log.major(contexte.getCodeUtilisateur(),"AideAction", "doGet", e.getMessage(), e);
          		return Action.ERROR;
        }
    }

    //====================  GETTER AND SETTER ===================================

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRootHelp() {
        return rootHelp;
    }

    public void setRootHelp(String rootHelp) {
        this.rootHelp = rootHelp;
    }

    public String getRootRecommandation() {
        return rootRecommandation;
    }

    public void setRootRecommandation(String rootRecommandation) {
        this.rootRecommandation = rootRecommandation;
    }
}
