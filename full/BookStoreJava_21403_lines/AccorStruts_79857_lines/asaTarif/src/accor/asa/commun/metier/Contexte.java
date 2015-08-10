package  com.accor.asa.commun.metier;

import com.accor.asa.commun.habilitation.metier.Profil;
import com.accor.asa.commun.habilitation.metier.Role;
import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.user.metier.User;

/**
 * Structure rassemblant les valeurs specifiques a la session d'un
 * utilisateur et necessaire a l'execution de certains services
 * des DAO.
 * Exemple : code langue, information d'habilitation, etc.
 *
 * <b>Modifications</b> 10/5/2001 par A.CHETBANI: ajout des attributs codePays, codeZoneVente, codeProfil,
 *                                          codeIntervenant et codeTerritoire
 * <br><b>Modifications</b> 06/06/2001 par D.DREISTADT : Fusion avec ContexteUtilisateurValue
 * <br><b>Modifications</b> 08/08/2001 par A.CHETBANI: ajout de l'attribut codeLangueRechercheHotel
 * <br><b>Modifications</b> 07/11/2001 par D.DREISTADT : Ajout de l'attribut timestamp pour la gestion du lock optimiste
 *
 * @author Jerome Pietri
 */
@SuppressWarnings("serial")
public class Contexte
        implements java.io.Serializable {

	private User 	utilisateurValue = null;
    private Profil  profil;
    private String codeLangue = null;

    private String codeSessionHttp = null;

    private Long   codeDossierCourant = null;
    private String timestampCompte  = null;
    private String timestampContact = null;
    private String timestampDossier = null;
	private String timestampHotel   = null;
    private ContexteAppelDAO contexteAppelDAO;
    // <EVO 4361> 
    private boolean typeOffreSupprime=false;

    /**
     * put your documentation comment here
     * @return
     */
    public User getUtilisateurValue () {
        return  utilisateurValue;
    }

    public void setUtilisateurValue (User unUtilisateurValue) {
        utilisateurValue = unUtilisateurValue;
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getCodeLangue () {
        return  (codeLangue!=null?
                    codeLangue:
                    (utilisateurValue!=null?
                        utilisateurValue.getLanguageCode():
                        null
                    )
                );
    }

    /**
     * put your documentation comment here
     * @param code
     */
    public void setCodeLangue (String code) {
        codeLangue = code;
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getCodePays () {
        return  (utilisateurValue!=null?utilisateurValue.getCountryCode():null);
    }

    /**
     * put your documentation comment here
     * @param code
     */
    public void setCodePays (String code) {
        utilisateurValue.setCountryCode(code);
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getCodeHotel () {
        return  (utilisateurValue!=null?utilisateurValue.getHotelId():null);
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getNomHotel () {
        return  (utilisateurValue!=null?utilisateurValue.getHotelName():null);
    }


    /**
     * put your documentation comment here
     * @param code
     */
    public void setCodeHotel (String code) {
        utilisateurValue.setHotelId(code);
    }

    /**
     * put your documentation comment here
     * @return
     */
    public Role getRole () {
        if( profil != null ) {
        	return profil.getRole();
        }
    	return null;
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getCodeRole () {
        if( getRole() != null ) {
        	return getRole().getCode();
        }
    	return null;
    }

    /**
     * put your documentation comment here
     * @return
     */
    public Role getRoleVente () {
        if( profil != null ) {
        	return profil.getRoleVente();
        }
    	return null;
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getCodeRoleVente () {
        if( getRoleVente() != null ) {
        	return getRoleVente().getCode();
        }
    	return null;
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getCodeUtilisateur () {
        return (utilisateurValue!=null?utilisateurValue.getLogin():null);
    }

    /**
     * put your documentation comment here
     * @param code
     */
    public void setCodeUtilisateur (String code) {
        utilisateurValue.setLogin(code);
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getCodeZoneVente () {
        return  (utilisateurValue!=null?utilisateurValue.getSaleZoneId():null);
    }

    /**
     * put your documentation comment here
     * @param code
     */
    public void setCodeZoneVente (String code) {
        utilisateurValue.setSaleZoneId(code);
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getCodeProfil () {
        return  (utilisateurValue!=null?utilisateurValue.getProfileId():null);
    }

    /**
     * put your documentation comment here
     * @return
     */
    public Integer getCodeIntervenant () {
        return  (utilisateurValue!=null?utilisateurValue.getId():null);
    }

    /**
     * put your documentation comment here
     * @param code
     */
    public void setCodeIntervenant (Integer code) {
        utilisateurValue.setId(code);
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getCodeTerritoire () {
        return (utilisateurValue!=null?utilisateurValue.getDefaultTerritoryId():null);
    }

    /**
     * put your documentation comment here
     * @param code
     */
    public void setCodeTerritoire (String code) {
        utilisateurValue.setDefaultTerritoryId(code);
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getNom () {
        return  (utilisateurValue!=null?utilisateurValue.getLabel():null);
    }


    public String getTimestampCompte() {
        return timestampCompte;
    }

    public void setTimestampCompte(String newValue) {
        timestampCompte = newValue;
    }

    public String getTimestampContact() {
        return timestampContact;
    }

    public void setTimestampContact(String newValue) {
        timestampContact = newValue;
    }

    public String getTimestampDossier() {
        return timestampDossier;
    }

    public void setTimestampDossier(String newValue) {
        timestampDossier = newValue;
    }

    public Long getCodeDossierCourant() {
        return codeDossierCourant;
    }

    public void setCodeDossierCourant(Long newValue) {
        codeDossierCourant = newValue;
        setTypeOffreSupprime(false);
        
    }
    
    

    public final boolean isTypeOffreSupprime() {
        return typeOffreSupprime;
    }
    public final void setTypeOffreSupprime(boolean typeOffreSupprime) {
        this.typeOffreSupprime = typeOffreSupprime;
    }
    public String getCodeSessionHttp() {
        return codeSessionHttp;
    }

    public void setCodeSessionHttp(String newValue) {
        codeSessionHttp = newValue;
    }

    /**
     * put your documentation comment here
     * @param     String codeLangue
     */
    public Contexte (String codeLangue) {
        setCodeLangue(codeLangue);
        utilisateurValue = new User();
    }

    /**
     * put your documentation comment here
     * @param     UtilisateurValue utilisateurValue
     */
    public Contexte( User utilisateurValue ) {
        setUtilisateurValue(utilisateurValue);
    }

    /**
     * put your documentation comment here
     */
    public Contexte () {
        utilisateurValue = new User();
    }

    public String toString() {
        return codeLangue + ", " +
            utilisateurValue + ", " + timestampCompte + ", " + timestampContact
            + ", " + timestampDossier + ", " + codeSessionHttp;
    }
    
    public ContexteAppelDAO getContexteAppelDAO()
    {
    	if (contexteAppelDAO == null) contexteAppelDAO = new ContexteAppelDAO(getCodeUtilisateur());
    	return contexteAppelDAO;
    }
    
    
	public String getTimestampHotel() {
		return timestampHotel;
	}

	public void setTimestampHotel(String string) {
		timestampHotel = string;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}
	
}



