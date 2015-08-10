package com.accor.asa.commun.habilitation.process;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.habilitation.metier.AsaModule;
import com.accor.asa.commun.habilitation.metier.Droit;
import com.accor.asa.commun.habilitation.metier.HabilitationException;
import com.accor.asa.commun.habilitation.metier.Profil;
import com.accor.asa.commun.habilitation.metier.Role;
import com.accor.asa.commun.habilitation.metier.acces.Acces;
import com.accor.asa.commun.habilitation.metier.acces.AccesRoleMap;
import com.accor.asa.commun.habilitation.metier.ecran.GroupeEcran;
import com.accor.asa.commun.habilitation.metier.optionmenu.GroupeOptionMenu;
import com.accor.asa.commun.habilitation.metier.optionmenu.OptionMenu;
import com.accor.asa.commun.habilitation.metier.optionmenu.OptionMenuRole;
import com.accor.asa.commun.habilitation.metier.optionmenu.OptionsMenuRoleMap;
import com.accor.asa.commun.habilitation.metier.visibilite.AxeVisibilite;
import com.accor.asa.commun.habilitation.metier.visibilite.TypeVisibilite;
import com.accor.asa.commun.habilitation.metier.visibilite.Visibilite;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.TimestampException;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.user.metier.UserLight;
import com.accor.asa.vente.commun.habilitation.metier.ObjetMaitreHabilitation;
import com.accor.asa.vente.commun.habilitation.metier.TypeObjetMaitreHabilitation;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 16 juin 2005
 * Time: 16:18:43
 * To change this template use File | Settings | File Templates.
 */
public interface HabilitationFacade {
    /** Le nom du pool d'objet*/
    public static final String POOL_NAME   = "communHabilitationFacade";

    /**
     * récupère la pondération pour un couple (axe, groupe ecran),
     * @param contexte
     * @param codeAxe
     * @param codeGroupeEcran
     * @return la pondération pour un coupe (axe, groupe ecran)
     * @throws TechnicalException
     */
    public Integer getPonderationPourAxe( final String codeAxe, final String codeGroupeEcran, 
    		final Contexte contexte ) throws TechnicalException;

    /**
     * Renvoie toutes les options du menu de vente
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuVente( final Contexte contexte ) 
    		throws TechnicalException;
    	
    /**
     * Renvoie toutes les options du menu de tarif
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuTarif( final Contexte contexte ) 
    		throws TechnicalException;
    	
    /**
     * Renvoie toutes les options du menu d'admin
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuAdmin( final Contexte contexte ) 
    		throws TechnicalException;
    	
    /**
     * Renvoie toutes les options du menu de translate
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuTranslate( final Contexte contexte ) 
    		throws TechnicalException;
    	
    /**
     * Renvoie les toutes les options de menu pour tous les roles
     * @param contexte
     * @param codeRole
     * @return List<OptionMenuRole>
     * @throws TechnicalException
     */
    public List<OptionMenuRole> getAllOptionsMenuRole( final Contexte contexte ) 
    		throws TechnicalException;
    
    /**
     * Renvoie les options de menu pour le role donne en parametre
     * @param contexte
     * @param codeRole
     * @return Set<String>
     * @throws TechnicalException
     */
    public Set<String> getOptionsMenuRole( final String codeRole, final Contexte contexte ) throws TechnicalException;
    
    /***
     * Renvoie tous les roles de vente
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    public List<Role> getRolesVente( final Contexte contexte ) throws TechnicalException;
    
    /***
     * Renvoie tous les roles qui n'appartiennent pas a Vente
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    public List<Role> getOthersRoles( final Contexte contexte ) throws TechnicalException;

    /***
     * Renvoie l'ensemble des roles du systeme 
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    public List<Role> getRolesAdmin( final Contexte contexte ) throws TechnicalException;

    /***
     * Renvoie l objet Role associe au codeRole
     * @param codeRole
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public Role getRole( final String codeRole, final Contexte contexte ) 
    		throws HabilitationException, TechnicalException;

    /***
     * Methode d'enregistrement de la connexion Utilisateur
     * @param login
     * @param module
     * @param contexte
     * @throws TechnicalException
     */
    public void connexion( final String login, final String module, final Contexte contexte ) 
    		throws TechnicalException;

    /***
     * Methode de suppression de la log de la connexion Utilisateur
     * @param login
     * @param module
     * @param contexte
     * @throws TechnicalException
     */
    public void deconnexion( final String login, final String module, final Contexte contexte ) 
    		throws TechnicalException;

    /**
     * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
     * @param codeCompte
     * @param timestamp
     * @return nouveau timestamp
     * @throws TimestampException si le timestamp a changé
     * @throws TechnicalException
     */
    public void checkVerrouOptimisteCompte( final Integer codeCompte, final String timestamp, 
    		final Contexte contexte ) throws TechnicalException, TimestampException;

    /**
     * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
     * @param codeContact
     * @param timestamp
     * @return nouveau timestamp
     * @throws TimestampException si le timestamp a changé
     * @throws TechnicalException
     */
    public void checkVerrouOptimisteContact( final Integer codeContact, final String timestamp,
    		final Contexte contexte ) throws TechnicalException, TimestampException;
    
    /**
     * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
     * @param codeDossier
     * @param timestamp
     * @return nouveau timestamp
     * @throws TimestampException si le timestamp a changé
     * @throws TechnicalException
     */
    public void checkVerrouOptimisteDossier( final Long codeDossier, final String timestamp,
    		final Contexte contexte ) throws TechnicalException, TimestampException;
    
    /**
     * Renvoie l'estampille du compte passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     * @param codeCompte
     * @return
     * @throws TechnicalException
     */
    public String getVerrouOptimisteCompte( final Integer codeCompte, final Contexte contexte ) 
    		throws TechnicalException;
    
    /**
     * Renvoie l'estampille du contact passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     * @param codeContact
     * @return
     * @throws TechnicalException
     */
    public String getVerrouOptimisteContact( final Integer codeContact, final Contexte contexte ) 
    		throws TechnicalException;
    
    /**
     * Renvoie l'estampille du dossier passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     * @param codeDossier
     * @return
     * @throws TechnicalException
     */
    public String getVerrouOptimisteDossier( final Long codeDossier, final Contexte contexte ) 
    		throws TechnicalException;
    
    /**
     * Renvoie l'estampille de l'hotel passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     * @param codeDossier
     * @return
     * @throws TechnicalException
     */
	public String getVerrouOptimisteHotel( final String codeHotel, final Contexte contexte ) 
			throws TechnicalException;
	
    /***
     * récupère le droit possible pour un couple (axe, groupe ecran)
     * <br> voir table habil_acces
     * @param codeRole
     * @param codeAxe
     * @param codeGroupeEcran
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public Droit getDroit( final String codeRole, final String codeAxe, 
    		final String codeGroupeEcran, final Contexte contexte ) throws TechnicalException;
    
    /***
     * Récupère de droit max pour un ecran et le profil/role contenu dans le contexte
     * @param codeEcran
     * @param contexte
     * @return
     * @throws HabilitationException
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public Droit getDroit( final String codeEcran, final Contexte contexte ) 
    		throws HabilitationException, IncoherenceException, TechnicalException;
    
    /**
     * Renvoie la liste des axes pour le profil demandé en se basant sur le cache
     * si possible. Sinon, effectue la lecture en base et met à jour le cache.
     * @param contexte
     * @param codeProfil
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getListeAxesProfilFromCache( final String codeProfil, final Contexte contexte )
            throws TechnicalException;
    
    /**
     * Renvoie la liste des axes pour le profil demandé depuis la base. ( table AXE_VISIBILITE )
     * @param contexte
     * @param codeProfil
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
	public List<AxeVisibilite> getListeAxesProfilAdmin( final String codeProfil, final Contexte contexte )
            throws TechnicalException;

   /**
    * Renvoie le code du groupeEcran associe a l ecran
    * @param codeEcran
    * @return
    * @throws TechnicalException
    * @throws IncoherenceException
    */
   public String getCodeGroupeEcran( final String codeEcran ) throws TechnicalException,
           IncoherenceException;

   /**
   *
   * @param contexte
   * @param objMaitreHabil
   * @param codeAxe
   * @return
   * @throws TechnicalException
   * @throws IncoherenceException
   */
  public boolean isObjetVisiblePourAxe( final ObjetMaitreHabilitation objMaitreHabil, final String codeAxe, 
		  final Contexte contexte ) throws TechnicalException, IncoherenceException;

  /**
  *
  * @param codeEcran
  * @return
  * @throws TechnicalException
  * @throws IncoherenceException
  */
  public TypeObjetMaitreHabilitation getTypeObjetHabilitePourEcran( final String codeEcran ) 
 		throws TechnicalException, IncoherenceException;

	/***
	 * Renvoie une Map contenant les droits associes a chaque Axe*groupeEcran pour un Role
	 * @param codeRole
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	 public AccesRoleMap getAccesByRole( final String codeRole, final Contexte contexte ) 
	 		throws TechnicalException;

	/***
	 * Renvoie la liste des groupes d'ecrans declares avec leur appartenance ( vente, tarif ... )
	 * @param contexte
	 * @return List<GroupeEcran>
	 * @throws TechnicalException
	 */
    public List<GroupeEcran> getGroupeEcrans( AsaModule module, final Contexte contexte ) throws TechnicalException;

   /**
	* Sauvegarde les droits d'acces pour un role
	* @param droitAcces
	* @param contexte
	* @exception TechnicalException
	*/
	public void saveAccesByRole( final List<Acces> droitAcces, final Contexte contexte ) 
	 		throws TechnicalException;

   /***
	* Mise a jour des infos d'un role
	* @param codeRole
	* @param responsableDossier
	* @param reattributionDossier
	* @param contexte
	* @throws TechnicalException
	*/
	public void saveInfoRole( final String codeRole, final boolean responsableDossier, 
			final boolean reattributionDossier, final Contexte contexte ) throws TechnicalException;

   /***
	* Enregistre les options du menu par role
	* @param table de hashage (codeRole) -> codeOption
	* @param module
	* @param tableOptionsMenuRole
	* @param contexte
	* @throws TechnicalException
	* @throws SQLException
	*/
	public void setOptionMenuByRole( final AsaModule module, final OptionsMenuRoleMap optionsMenuRole, 
			final Contexte contexte ) throws TechnicalException, SQLException;

	/***
	 * renvoie la liste des types de visibilites ( table TYPE_VALEUR_VISIBILITE )
	 * @param contexte
	 * @return List<TypeVisibilite>
	 * @throws TechnicalException
	 */
	public List<TypeVisibilite> getTypesVisibilite( final Contexte contexte ) throws TechnicalException;

    /**
     * Renvoie tous les axes de visibilite du module Vente ( table AXE_VISIBILITE )
     * @param module 
     * @param contexte
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getAxesVisibiliteVente( final Contexte contexte ) throws TechnicalException;

    /**
     * Renvoie tous les axes de visibilite des modules tarif / admin / translate ( table AXE_VISIBILITE )
     * @param module 
     * @param contexte
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getOtherAxesVisibilite( final Contexte contexte ) throws TechnicalException;

    /***
     * Ajoute une valeur de visibilite a l'utilisateur.
     * @param v
     * @param codeLangue
     * @param contexte
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public void addVisibilite( final Visibilite v, final Contexte contexte ) 
    	throws IncoherenceException, TechnicalException;

    /***
     * Renvoie les valeurs de visibilite d'un utilisateur pour un axe et un type de valeur 
     * @param login
     * @param codeAxe
     * @param codeType
     * @param contexte
     * @return List<Visibilite>
     * @throws TechnicalException
     */
    public List<Visibilite> getVisibilites( final String login, final String codeAxe, final String codeType, 
    		final Contexte contexte ) throws TechnicalException;

    /***
     * Renvoie la liste des axes de visibilite sur vente sur lesquels
     *   l'utilisateur a une visibilité complète (All)
     * @param login
     * @param contexte
     * @return List<String>
     * @throws TechnicalException
     */
    public List<String> getAxesWithVisibiliteAll( final String login, final Contexte contexte )
            throws TechnicalException;

    /**
     * Renvoie les valeurs de visibilite d'un utilisateur
     * @param Identifiant de l'utilisateur
     * @return List<Visibilite>
     */
    public List<Visibilite> getVisibilites( final String login, final Contexte contexte )
            throws TechnicalException;

    /***
     * Supprime une valeur de visibilite d'un utilisateur
     * @param login
     * @param codeAxe
     * @param codeType
     * @param contexte
     * @throws TechnicalException
     */
    public void deleteVisibilites( final String login, final String codeAxe, final Contexte contexte ) 
    		throws TechnicalException;
    
    /**
     * Recherche les infos d un profil dont le code est passe en parametre
     *
     * @param code
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public Profil getProfil( String code, Contexte contexte ) throws TechnicalException, HabilitationException;

    /**
     * Recherche de tous les profils
     * 20030227 : ajout de cache pour les perfs
     *
     * @param contexte utilisé pour le login seulement
     * @return List<Profil>
     */
    public List<Profil> getProfils( Contexte contexte ) throws TechnicalException;

    /**
     * Recherche de tous les profils ( supprimer inclus )
     * @param contexte 
     * @return List<Profil>
     */
    public List<Profil> getProfilsAdmin( final Contexte contexte ) throws TechnicalException;

    /***
     * methode de mise a jour d'un profil et de ses axes de visibilites
     * @param p
     * @param contexte
     * @param newProfil
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void updateProfil( Profil p, final Contexte contexte, final boolean newProfil ) 
    		throws TechnicalException, IncoherenceException;

    /***
     * Methode suppression logique d'un profil
     * @param p
     * @param contexte
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void deleteProfil( final Profil p, final Contexte contexte ) 
    		throws TechnicalException, IncoherenceException;
    
	/***
	 * Methode de recuperation des utilisateurs lie a ce profil
	 * @param idTerritory
	 * @param contexte
	 * @return List<UserLight>
	 * @throws TechnicalException
	 */
	public List<UserLight> getUsersForProfile( final String codeProfil, final Contexte contexte ) throws TechnicalException;
}

