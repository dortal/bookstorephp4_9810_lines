package com.accor.asa.rate.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.habilitation.metier.optionmenu.GroupeOptionMenu;
import com.accor.asa.commun.habilitation.metier.optionmenu.OptionMenu;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.rate.action.MainAction;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class RateScrean implements Serializable {

	public static enum HotelContext {DEFAULT_CONTEXT, ADAGIO_CONTEXT, LEISURE_CONTEXT}
    //===========================================================================================
    //=============                   LISTE DES CODES ECRANS        =============================
    //===========================================================================================
    public static final String RATES_PUBLISHED           = "published";
    public static final String RATES_BUSINESS            = "business";
    public static final String RATES_LEISURE             = "leisure";
    public static final String RATES_TOURISM             = "tourism";
    public static final String RATES_SPECIAL             = "special";
    public static final String RATES_CHILD               = "child";
    //===========================================================================================
    //=============                   LISTE DES OPTIONS MENU        =============================
    //===========================================================================================
    // Groupe Options
    public static final String RATES_MENU_GROUPE_BUSINESSRATES	    = "60";
    public static final String RATES_MENU_GROUPE_TOURISMRATES	    = "61";
    public static final String RATES_MENU_GROUPE_TAXESANDSERVICES	= "62";
    public static final String RATES_MENU_GROUPE_ENDSEIZURE 	    = "63";
    public static final String RATES_MENU_GROUPE_VALIDATERATES	    = "64";
    public static final String RATES_MENU_GROUPE_SEARCHHOTEL	    = "65";
    public static final String RATES_MENU_GROUPE_USERGUIDE	        = "66";

    // Options
    public static final String RATES_MENU_OPTION_PUBLISHEDRATES     = "160";
    public static final String RATES_MENU_OPTION_BUSINESSRATES      = "161";
    public static final String RATES_MENU_OPTION_LEISURERATES       = "162";
    public static final String RATES_MENU_OPTION_TOURISMRATES       = "163";
    public static final String RATES_MENU_OPTION_SPECIALRATES       = "164";
    public static final String RATES_MENU_OPTION_CHILDRATES         = "165";
    public static final String RATES_MENU_OPTION_TAXESANDSERVICES   = "166";
    public static final String RATES_MENU_OPTION_ENDSEIZURE         = "167";
    public static final String RATES_MENU_OPTION_VALIDATERATES      = "168";
    public static final String RATES_MENU_OPTION_SEARCHHOTEL        = "169";
    public static final String RATES_MENU_OPTION_BLANKDOCUMENT      = "170";
    public static final String RATES_MENU_OPTION_RECOMMENDATIONS    = "171";
    public static final String RATES_MENU_OPTION_HELPONLINE         = "172";
    //===========================================================================================
    //=============                   LA LISTE DES ECRANS         ===============================
    //===========================================================================================
    // Groupe ecrans
    public static final String RATES_ECRAN_GROUPE_BUSINESSRATES	    = "60";
    public static final String RATES_ECRAN_GROUPE_TOURISMRATES	    = "61";
    public static final String RATES_ECRAN_GROUPE_TAXESANDSERVICES	= "62";
    public static final String RATES_ECRAN_GROUPE_ENDSEIZURE 	    = "63";
    public static final String RATES_ECRAN_GROUPE_VALIDATERATES	    = "64";
    public static final String RATES_ECRAN_GROUPE_SEARCHHOTEL       = "65";
    // Ecrans
    public static final String RATES_ECRAN_OPTION_PUBLISHEDRATES     = "160";
    public static final String RATES_ECRAN_OPTION_BUSINESSRATES      = "161";
    public static final String RATES_ECRAN_OPTION_LEISURERATES       = "162";
    public static final String RATES_ECRAN_OPTION_TOURISMRATES       = "163";
    public static final String RATES_ECRAN_OPTION_SPECIALRATES       = "164";
    public static final String RATES_ECRAN_OPTION_CHILDRATES         = "165";
    public static final String RATES_ECRAN_OPTION_TAXESANDSERVICES   = "166";
    public static final String RATES_ECRAN_OPTION_ENDSEIZURE         = "167";
    public static final String RATES_ECRAN_OPTION_VALIDATERATES      = "168";
    public static final String RATES_ECRAN_OPTION_SEARCHHOTEL        = "169";

    public static HashMap<String, RateScrean> ecrans = new HashMap<String, RateScrean>();
    public static HashMap<String, String> optionUrls = new HashMap<String, String>();

    static {
        addRateScrean(HotelContext.DEFAULT_CONTEXT, new RateScrean(RATES_PUBLISHED, FamilleTarifRefBean.ID_FAMILLE_PUBLISHED, RATES_ECRAN_OPTION_PUBLISHEDRATES, "COM_RAT_LBL_CHAMBRE",  "COM_RAT_LBL_TITLEPUBLISHED", "business","listBusinessRate.action"));
        addRateScrean(HotelContext.DEFAULT_CONTEXT, new RateScrean(RATES_BUSINESS,  FamilleTarifRefBean.ID_FAMILLE_COMPANY,   RATES_ECRAN_OPTION_BUSINESSRATES,  "COM_RAT_LBL_CHAMBRE",  "COM_RAT_LBL_TITLEBUSINESS",  "business","listBusinessRate.action"));
        addRateScrean(HotelContext.DEFAULT_CONTEXT, new RateScrean(RATES_LEISURE,   FamilleTarifRefBean.ID_FAMILLE_IT,        RATES_ECRAN_OPTION_LEISURERATES,   "COM_RAT_LBL_PERSONNE", "COM_RAT_LBL_TITLELEISURE",   "leisure", "listLeisureRate.action"));
        addRateScrean(HotelContext.LEISURE_CONTEXT, new RateScrean(RATES_LEISURE,   FamilleTarifRefBean.ID_FAMILLE_LEISURE,   RATES_ECRAN_OPTION_LEISURERATES,   "COM_RAT_LBL_PERSONNE", "COM_RAT_LBL_TITLELEISURE",   "leisure", "listLeisureRate.action"));
        addRateScrean(HotelContext.DEFAULT_CONTEXT, new RateScrean(RATES_TOURISM,   FamilleTarifRefBean.ID_FAMILLE_GROUP,     RATES_ECRAN_OPTION_TOURISMRATES,   "COM_RAT_LBL_PERSONNE", "COM_RAT_LBL_TITLETOURISM",   "leisure", "listLeisureRate.action"));
        addRateScrean(HotelContext.DEFAULT_CONTEXT, new RateScrean(RATES_SPECIAL,   FamilleTarifRefBean.ID_FAMILLE_IT,        RATES_ECRAN_OPTION_SPECIALRATES,   "",                     "COM_RAT_LBL_TITLESPECIAL",   "special", "listSpecialRate.action"));
        addRateScrean(HotelContext.LEISURE_CONTEXT, new RateScrean(RATES_SPECIAL,   FamilleTarifRefBean.ID_FAMILLE_LEISURE,   RATES_ECRAN_OPTION_SPECIALRATES,   "",                     "COM_RAT_LBL_TITLESPECIAL",   "special", "listSpecialRate.action"));
        addRateScrean(HotelContext.DEFAULT_CONTEXT, new RateScrean(RATES_CHILD,     FamilleTarifRefBean.ID_FAMILLE_IT,        RATES_ECRAN_OPTION_CHILDRATES,     "",                     "COM_RAT_LBL_TITLECHILD",     "child",   "listChildRate.action"));
        addRateScrean(HotelContext.LEISURE_CONTEXT, new RateScrean(RATES_CHILD,     FamilleTarifRefBean.ID_FAMILLE_LEISURE,   RATES_ECRAN_OPTION_CHILDRATES,     "",                     "COM_RAT_LBL_TITLECHILD",     "child",   "listChildRate.action"));
        addRateScrean(HotelContext.ADAGIO_CONTEXT,  new RateScrean(RATES_PUBLISHED, FamilleTarifRefBean.ID_FAMILLE_PUBLISHED, RATES_ECRAN_OPTION_PUBLISHEDRATES, "COM_RAT_LBL_CHAMBRE",  "COM_RAT_LBL_TITLEPUBLISHED", "adagio",  "listAdagioBusinessRate.action"));
        addRateScrean(HotelContext.ADAGIO_CONTEXT,  new RateScrean(RATES_BUSINESS,  FamilleTarifRefBean.ID_FAMILLE_COMPANY,   RATES_ECRAN_OPTION_BUSINESSRATES,  "COM_RAT_LBL_CHAMBRE",  "COM_RAT_LBL_TITLEBUSINESS",  "adagio",  "listAdagioBusinessRate.action"));
    }

    protected String    codeEcran;
    protected Integer   idFamilleTarif;
    protected String    codeHabilitation;
    protected String    uniteKey;
    protected String    titleKey;
    protected String    path;
    protected String    actionString;

    public RateScrean(String codeEcran, Integer idFamilleTarif, String codeHabilitation, String uniteKey, String titleKey, String path, String actionString) {
        this.codeEcran = codeEcran;
        this.idFamilleTarif = idFamilleTarif;
        this.codeHabilitation = codeHabilitation;
        this.uniteKey = uniteKey;
        this.titleKey = titleKey;
        this.path = path;
        this.actionString = actionString;
    }

    //=============                   GETTER AND SETTER        ===============================
    public String getCodeEcran() {
        return codeEcran;
    }

    public void setCodeEcran(String codeEcran) {
        this.codeEcran = codeEcran;
    }

    public Integer getIdFamilleTarif() {
        return idFamilleTarif;
    }

    public void setIdFamilleTarif(Integer idFamilleTarif) {
        this.idFamilleTarif = idFamilleTarif;
    }

    public String getCodeHabilitation() {
        return codeHabilitation;
    }

    public void setCodeHabilitation(String codeHabilitation) {
        this.codeHabilitation = codeHabilitation;
    }

    public String getUniteKey() {
        return uniteKey;
    }

    public void setUniteKey(String uniteKey) {
        this.uniteKey = uniteKey;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

	public String getActionString() {
		return actionString;
	}

    //=============                   METHODES METIERS         ===============================

    private static String generateKey(String codeEcran, HotelContext contextHotel) {
    	return codeEcran+"_"+contextHotel;
    }
    
    private static void addRateScrean( HotelContext contextHotel, RateScrean rateScrean) {
    	String codeEcran=rateScrean.getCodeEcran();
    	ecrans.put(generateKey(codeEcran, contextHotel), rateScrean);
    }

    public static RateScrean getRateScrean(String code, HotelContext context) {
    	if(context==null)
    		context=HotelContext.DEFAULT_CONTEXT;
    	String key=generateKey(code, context);
    	RateScrean rs= ecrans.get(key);
    	if(rs==null && !context.equals(HotelContext.DEFAULT_CONTEXT)) {
    		key=generateKey(code, HotelContext.DEFAULT_CONTEXT);
    		rs= ecrans.get(key);
    	}
    	return rs;
    }

	public static String getActionString(HttpSession session, String codeScrean) {
		HotelContext context = (HotelContext)session.getAttribute(MainAction.HOTEL_CONTEXT);
		return getRateScrean(codeScrean, context).getActionString();
	}

    /**
     * Retrouver l'url d'un option
     * @param session
     * @param codeOption
     * @return
     */
    private static String getOptionUrl(HttpSession session, String codeOption) {
        HashMap<String, String> optionUrls = new HashMap<String, String>();
        optionUrls.put(RATES_MENU_OPTION_PUBLISHEDRATES,RateScrean.getActionString(session, RateScrean.RATES_PUBLISHED)+"?codeEcran="+RateScrean.RATES_PUBLISHED);
        optionUrls.put(RATES_MENU_OPTION_BUSINESSRATES,RateScrean.getActionString(session, RateScrean.RATES_BUSINESS)+"?codeEcran="+RateScrean.RATES_BUSINESS);
        optionUrls.put(RATES_MENU_OPTION_LEISURERATES,RateScrean.getActionString(session, RateScrean.RATES_LEISURE)+"?codeEcran="+RateScrean.RATES_LEISURE);
        optionUrls.put(RATES_MENU_OPTION_TOURISMRATES,RateScrean.getActionString(session, RateScrean.RATES_TOURISM)+"?codeEcran="+RateScrean.RATES_TOURISM);
        optionUrls.put(RATES_MENU_OPTION_SPECIALRATES,RateScrean.getActionString(session, RateScrean.RATES_SPECIAL)+"?codeEcran="+RateScrean.RATES_SPECIAL);
        optionUrls.put(RATES_MENU_OPTION_CHILDRATES,RateScrean.getActionString(session, RateScrean.RATES_CHILD)+"?codeEcran="+RateScrean.RATES_CHILD);
        optionUrls.put(RATES_MENU_OPTION_TAXESANDSERVICES,"listTaxe.action");
        optionUrls.put(RATES_MENU_OPTION_ENDSEIZURE, "finSaisie.action");
        optionUrls.put(RATES_MENU_OPTION_VALIDATERATES,"validationRates.action");
        optionUrls.put(RATES_MENU_OPTION_SEARCHHOTEL,"searchHotel.action");
        optionUrls.put(RATES_MENU_OPTION_BLANKDOCUMENT,"modelBlankDocument.action");
        optionUrls.put(RATES_MENU_OPTION_RECOMMENDATIONS,"aide!displayRecommandation.action");
        optionUrls.put(RATES_MENU_OPTION_HELPONLINE,"aide!displayAide.action?page=103");

        return optionUrls.get(codeOption);
    }
    /**
     * Afficher le menu d'un groupe options du menu
     * @param session
     * @param codeGroupeOption
     * @return
     */
    public static String displayGroupeOption( HttpSession session, String codeGroupeOption) {
        Contexte contexte = (Contexte)session.getAttribute( MainAction.CONTEXTE);
        Map<GroupeOptionMenu, List<OptionMenu>> mesOptions = (Map<GroupeOptionMenu, List<OptionMenu>>)session.getAttribute( MainAction.MENU_OPTIONS);
        Translator trans = TranslatorFactory.getTranslator(contexte.getCodeLangue(), true);
        GroupeOptionMenu groupeOption;
        List<OptionMenu> optionMenus;
        OptionMenu optionMenu;
        groupeOption = GroupeOptionMenu.getGroupeOptionsMenu( codeGroupeOption, mesOptions );
        StringBuffer sb = new StringBuffer();
        if(groupeOption != null) {
            sb.append("['");
            sb.append( trans.getLibelle( groupeOption.getCleInternationalisation() ) );
            sb.append("', null,\n");
            optionMenus = mesOptions.get( groupeOption );
            //On parcourt les options de ce groupe
            boolean isFirst = true;
            for( int i=0, size=optionMenus.size(); i<size; i++ ) {
                optionMenu = optionMenus.get(i);
                if (StringUtils.isNotBlank(getOptionUrl(session, optionMenu.getCodeOption().toString()))) {
                    sb.append( isFirst?"":",");
                    isFirst = false;
                    sb.append( "['" );
                    sb.append( trans.getLibelle( optionMenu.getCleInternationalisation() ) );
                    sb.append( "', '" );
                    sb.append( getOptionUrl(session, optionMenu.getCodeOption().toString()) );
                    sb.append( "']\n" );
                }
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}
