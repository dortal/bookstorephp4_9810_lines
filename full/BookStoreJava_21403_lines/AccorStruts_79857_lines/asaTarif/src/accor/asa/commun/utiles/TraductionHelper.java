package com.accor.asa.commun.utiles;

import java.util.HashMap;
import java.util.Map;

import com.accor.asa.commun.log.LogCommun;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;


/**
  * helper pour la traduction : pour l'instant centralise la traduction des "oui" / "non"
  * <br>
  * peut être utilisé dans les JSP
  *
  */
public class TraductionHelper {
    
    // singleton
    private static TraductionHelper _instance = new TraductionHelper();
    
    // clefs utilisées
    private static final String CLEF_TRANSLATE_OUI = "ALL_ALL_OUI";
    private static final String CLEF_TRANSLATE_NON = "ALL_ALL_NON";

    /**
      * clef = valeur à traduire
      * valeur = Boolean vrai si on traduit par "OUI"
      */
    private Map<String, Boolean> _valeurEtTraduction;


    private TraductionHelper( ) {
        _valeurEtTraduction = new HashMap<String, Boolean>();
        ajouterValeurATraduire("0", false );
        ajouterValeurATraduire("1", true );
        ajouterValeurATraduire("2", false );
        ajouterValeurATraduire("NON", false);
        ajouterValeurATraduire("FALSE", false);
        ajouterValeurATraduire("FAUX", false);
        ajouterValeurATraduire("OUI", true);
        ajouterValeurATraduire("TRUE", true);
        ajouterValeurATraduire("VRAI", true);
    }
    
    
    private void ajouterValeurATraduire( String valeur, boolean traduitParOui ) {
        _valeurEtTraduction.put( valeur, new Boolean(traduitParOui) );
    }
    
    public static TraductionHelper getInstance() {
        return _instance;
    }
    


	/**
	 * @return : une String contenant la traduction des termes oui ,non ,vrai,faux,true,false,0,1
	 * <br>0 correspond à faux
	 */
	public String getTraductionOuiNon(String codeUtilisateur, String codeLangue, String codeaTraduire) {
        Translator trans = TranslatorFactory.getTranslator(codeLangue, true);
		try {
			if (codeaTraduire == null || "".equals(codeaTraduire))
			    return codeaTraduire;
			Boolean traduitParOui = (Boolean)_valeurEtTraduction.get(codeaTraduire.toUpperCase());
			String result = null;
			
			if ( traduitParOui != null )
			    result = trans.getLibelle(traduitParOui.booleanValue()?CLEF_TRANSLATE_OUI:CLEF_TRANSLATE_NON);
			if ( result == null )
			    result = codeaTraduire;
			return result;
		} catch (Exception e) {
			LogCommun.critical(codeUtilisateur,"TraductionHelper", "getTraductionVraiFaux", e.getMessage(), e);
			// à faire = lors du redesign de exceptions avec Commun, utiliser la "bonne" TechnicalException
            throw new RuntimeException(e.getMessage());
		}
    }

	public String getTraductionOuiNon(String codeUtilisateur, String codeLangue, boolean valeur) {
        return getTraductionOuiNon(codeUtilisateur, codeLangue, "" + valeur);
	}

	public String getTraductionOuiNon(String codeUtilisateur, String codeLangue, int valeur) {
        return getTraductionOuiNon(codeUtilisateur, codeLangue, "" + valeur);
	}





}