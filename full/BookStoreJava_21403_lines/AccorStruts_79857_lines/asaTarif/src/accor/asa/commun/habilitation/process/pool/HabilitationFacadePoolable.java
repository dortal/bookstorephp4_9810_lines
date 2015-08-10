package com.accor.asa.commun.habilitation.process.pool;

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
import com.accor.asa.commun.habilitation.process.HabilitationFacade;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.TimestampException;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.pool.GlobalPoolException;
import com.accor.asa.commun.process.pool.GlobalPoolable;
import com.accor.asa.commun.user.metier.UserLight;
import com.accor.asa.vente.commun.habilitation.metier.ObjetMaitreHabilitation;
import com.accor.asa.vente.commun.habilitation.metier.TypeObjetMaitreHabilitation;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 16 juin 2005
 * Time: 16:19:01
 * To change this template use File | Settings | File Templates.
 */
public class HabilitationFacadePoolable extends GlobalPoolable implements HabilitationFacade {

    /* init du pool en static */
    static {
        initPool(HabilitationFacade.POOL_NAME);
    }

    //========================== METHODES METIERS =======================================================


    /**
     * récupère la pondération pour un couple (axe, groupe ecran),
     * @param contexte
     * @param codeAxe
     * @param codeGroupeEcran
     * @return la pondération pour un coupe (axe, groupe ecran)
     * @throws TechnicalException
     */
    public Integer getPonderationPourAxe( final String codeAxe, final String codeGroupeEcran, 
    		final Contexte contexte ) throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getPonderationPourAxe( codeAxe, codeGroupeEcran, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getPonderationPourAxe","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getPonderationPourAxe","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }

    /**
     * Renvoie toutes les options du menu de vente
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuVente( final Contexte contexte ) throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getAllOptionMenuVente(contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getAllOptionMenuVente","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getAllOptionMenuVente","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    	
    /**
     * Renvoie toutes les options du menu de tarif
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuTarif( final Contexte contexte ) throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getAllOptionMenuTarif(contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getAllOptionMenuTarif","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getAllOptionMenuTarif","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    	
    /**
     * Renvoie toutes les options du menu d'admin
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuAdmin( final Contexte contexte ) throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getAllOptionMenuAdmin(contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getAllOptionMenuAdmin","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getAllOptionMenuAdmin","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    	
    /**
     * Renvoie toutes les options du menu de translate
     * @params Contexte
     * @return Map<GroupeOptionMenu,List<OptionMenu>>
     * @throws TechnicalException
     */
    public Map<GroupeOptionMenu,List<OptionMenu>> getAllOptionMenuTranslate( final Contexte contexte ) throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getAllOptionMenuTranslate(contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getAllOptionMenuTranslate","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getAllOptionMenuTranslate","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    	
    /**
     * Renvoie les toutes les options de menu pour tous les roles
     * @param contexte
     * @param codeRole
     * @return List<OptionMenuRole>
     * @throws TechnicalException
     */
    public List<OptionMenuRole> getAllOptionsMenuRole( final Contexte contexte ) 
    		throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getAllOptionsMenuRole(contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getAllOptionsMenuRole","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getAllOptionsMenuRole","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /**
     * Renvoie les options de menu pour le role donne en parametre
     * @param contexte
     * @param codeRole
     * @return Set<String>
     * @throws TechnicalException
     */
    public Set<String> getOptionsMenuRole( final String codeRole, final Contexte contexte ) throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getOptionsMenuRole(codeRole,contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getOptionsMenuRole","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getOptionsMenuRole","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /***
     * Renvoie tous les roles de vente
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    public List<Role> getRolesVente( final Contexte contexte ) throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getRolesVente(contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getRolesVente","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getRolesVente","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /***
     * Renvoie tous les roles qui n'appartiennent pas a Vente
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    public List<Role> getOthersRoles( final Contexte contexte ) throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getOthersRoles(contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getOthersRoles","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getOthersRoles","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }

    /***
     * Renvoie l'ensemble des roles du systeme 
     * @param contexte
     * @return List<Role>
     * @throws TechnicalException
     */
    public List<Role> getRolesAdmin( final Contexte contexte ) throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getRolesAdmin(contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getRoles","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getRoles","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /***
     * Renvoie l objet Role associe au codeRole
     * @param codeRole
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public Role getRole( final String codeRole, final Contexte contexte ) 
    		throws HabilitationException, TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getRole(codeRole, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getRole","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getRole","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /***
     * Methode d'enregistrement de la connexion Utilisateur
     * @param login
     * @param module
     * @param contexte
     * @throws TechnicalException
     */
    public void connexion( final String login, final String module, final Contexte contexte ) 
    		throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            objectPooled.connexion(login, module, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"connexion","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"connexion","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }

    /***
     * Methode de suppression de la log de la connexion Utilisateur
     * @param login
     * @param module
     * @param contexte
     * @throws TechnicalException
     */
    public void deconnexion( final String login, final String module, final Contexte contexte ) 
    		throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            objectPooled.deconnexion(login, module, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"deconnexion","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"deconnexion","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }

    /**
     * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
     * @param codeCompte
     * @param timestamp
     * @return nouveau timestamp
     * @throws TimestampException si le timestamp a changé
     * @throws TechnicalException
     */
    public void checkVerrouOptimisteCompte( final Integer codeCompte, final String timestamp, 
    		final Contexte contexte ) throws TechnicalException, TimestampException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            objectPooled.checkVerrouOptimisteCompte(codeCompte, timestamp, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"checkVerrouOptimisteCompte","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"checkVerrouOptimisteCompte","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }

    /**
     * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
     * @param codeContact
     * @param timestamp
     * @return nouveau timestamp
     * @throws TimestampException si le timestamp a changé
     * @throws TechnicalException
     */
    public void checkVerrouOptimisteContact( final Integer codeContact, final String timestamp,
    		final Contexte contexte ) throws TechnicalException, TimestampException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            objectPooled.checkVerrouOptimisteContact(codeContact, timestamp, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"checkVerrouOptimisteContact","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"checkVerrouOptimisteContact","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /**
     * Vérifie si l'estampille n'a pas changé pour le compte passé en paramètre (Verrouillage optimiste)
     * @param codeDossier
     * @param timestamp
     * @return nouveau timestamp
     * @throws TimestampException si le timestamp a changé
     * @throws TechnicalException
     */
    public void checkVerrouOptimisteDossier( final Long codeDossier, final String timestamp,
    		final Contexte contexte ) throws TechnicalException, TimestampException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            objectPooled.checkVerrouOptimisteDossier(codeDossier, timestamp, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"checkVerrouOptimisteDossier","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"checkVerrouOptimisteDossier","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /**
     * Renvoie l'estampille du compte passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     * @param codeCompte
     * @return
     * @throws TechnicalException
     */
    public String getVerrouOptimisteCompte( final Integer codeCompte, final Contexte contexte ) 
    		throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getVerrouOptimisteCompte(codeCompte, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getVerrouOptimisteCompte","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getVerrouOptimisteCompte","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /**
     * Renvoie l'estampille du contact passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     * @param codeContact
     * @return
     * @throws TechnicalException
     */
    public String getVerrouOptimisteContact( final Integer codeContact, final Contexte contexte ) 
    		throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getVerrouOptimisteContact(codeContact, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getVerrouOptimisteContact","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getVerrouOptimisteContact","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /**
     * Renvoie l'estampille du dossier passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     * @param codeDossier
     * @return
     * @throws TechnicalException
     */
    public String getVerrouOptimisteDossier( final Long codeDossier, final Contexte contexte ) 
    		throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getVerrouOptimisteDossier(codeDossier, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getVerrouOptimisteDossier","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getVerrouOptimisteDossier","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /**
     * Renvoie l'estampille de l'hotel passé en paramètre (Verrouillage optimiste)
     * Doit être appelée avant la lecture en base
     * @param codeDossier
     * @return
     * @throws TechnicalException
     */
	public String getVerrouOptimisteHotel( final String codeHotel, final Contexte contexte ) 
			throws TechnicalException{
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getVerrouOptimisteHotel(codeHotel, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getVerrouOptimisteHotel","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getVerrouOptimisteHotel","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
	}
	
    /***
     * récupère le droit possible pour un couple (axe, groupe ecran)
     * <br> voir table acces
     * @param codeRole
     * @param codeAxe
     * @param codeGroupeEcran
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public Droit getDroit( final String codeRole, final String codeAxe, 
    		final String codeGroupeEcran, final Contexte contexte ) throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getDroit(codeRole, codeAxe, codeGroupeEcran, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getDroit","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getDroit","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
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
    		throws HabilitationException, IncoherenceException, TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getDroit(codeEcran, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getDroit","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getDroit","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /**
     * Renvoie la liste des axes pour le profil demandé en se basant sur le cache
     * si possible. Sinon, effectue la lecture en base et met à jour le cache.
     * @param contexte
     * @param codeProfil
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getListeAxesProfilFromCache( final String codeProfil, final Contexte contexte )
            throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getListeAxesProfilFromCache(codeProfil, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getListeAxesProfilfromCache","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getListeAxesProfilfromCache","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
    }
    
    /**
     * Renvoie la liste des axes pour le profil demandé depuis la base. ( table AXE_VISIBILITE )
     * @param contexte
     * @param codeProfil
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
	public List<AxeVisibilite> getListeAxesProfilAdmin( final String codeProfil, final Contexte contexte )
            throws TechnicalException {
        HabilitationFacadePooled objectPooled = null;
        try {
            objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
            return objectPooled.getListeAxesProfilAdmin(codeProfil, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getListeAxesProfilAdmin","exception au call en mode pool", e);
            throw new TechnicalException(e);
        } catch (TechnicalException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(),"HabilitationFacadePoolable",
            		"getListeAxesProfilAdmin","TechnicalException", e);
            throw e;
        } finally {
            returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
        }
	}
          
    
	/**
	 * Renvoie le code du groupeEcran associe a l ecran
	 * @param codeEcran
	 * @return
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
    public String getCodeGroupeEcran( final String codeEcran ) throws TechnicalException,
    		IncoherenceException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
    		objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
    		return objectPooled.getCodeGroupeEcran(codeEcran);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable",
    				"getCodeGroupeEcran","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable",
    				"getCodeGroupeEcran","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

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
		  final Contexte contexte ) throws TechnicalException, IncoherenceException {
      
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.isObjetVisiblePourAxe(objMaitreHabil, codeAxe, contexte);
    	} catch (GlobalPoolException e) {
        	LogCommun.minor("","HabilitationFacadePoolable",
        		"isObjetVisiblePourAxe","exception au call en mode pool", e);
        	throw new TechnicalException(e);
    	} catch (TechnicalException e) {
        	LogCommun.minor("","HabilitationFacadePoolable",
        		"isObjetVisiblePourAxe","TechnicalException", e);
        	throw e;
    	} finally {
        	returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

    /**
     *
     * @param codeEcran
     * @return
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public TypeObjetMaitreHabilitation getTypeObjetHabilitePourEcran( final String codeEcran ) 
 			throws TechnicalException, IncoherenceException {
      
    	HabilitationFacadePooled objectPooled = null;
    	try {
    		objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
    		return objectPooled.getTypeObjetHabilitePourEcran(codeEcran);
    	} catch (GlobalPoolException e) {
        	LogCommun.minor("","HabilitationFacadePoolable",
        		"getTypeObjetHabilitePourEcran","exception au call en mode pool", e);
        	throw new TechnicalException(e);
    	} catch (TechnicalException e) {
        	LogCommun.minor("","HabilitationFacadePoolable",
        		"getTypeObjetHabilitePourEcran","TechnicalException", e);
          throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

	/**
	 * Sauvegarde les droits d'acces pour un role
	 * @param droitAcces
	 * @param contexte
	 * @exception TechnicalException
	 */
	 public void saveAccesByRole( final List<Acces> droitAcces, final Contexte contexte ) 
	 		throws TechnicalException {
         HabilitationFacadePooled objectPooled = null;
		 try {
			 objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
			 objectPooled.saveAccesByRole( droitAcces, contexte );
		 } catch (GlobalPoolException e) {
			 LogCommun.minor("","HabilitationFacadePoolable","saveAccesByRole","exception au call en mode pool", e);
			 throw new TechnicalException(e);
		 } catch (TechnicalException e) {
			 LogCommun.minor("","HabilitationFacadePoolable","saveAccesByRole","TechnicalException", e);
			 throw e;
		 } finally {
			 returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
		 }
	 }
	 
    /***
     * Renvoie une Map contenant les droits associes a chaque Axe*groupeEcran pour un Role
     * @param codeRole
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public AccesRoleMap getAccesByRole( final String codeRole, final Contexte contexte ) 
			throws TechnicalException {
	 
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getAccesByRole( codeRole, contexte );
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getAccesByRole","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getAccesByRole","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

	/***
	 * Renvoie la liste des groupes d'ecrans declares avec leur appartenance ( vente, tarif ... )
	 * @param contexte
	 * @return List<GroupeEcran>
	 * @throws TechnicalException
	 */
    public List<GroupeEcran> getGroupeEcrans( AsaModule module, final Contexte contexte ) throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getGroupeEcrans( module, contexte );
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getGroupeEcrans","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getGroupeEcrans","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

    /***
	* Mise a jour des infos d'un role
	* @param codeRole
	* @param responsableDossier
	* @param reattributionDossier
	* @param contexte
	* @throws TechnicalException
	*/
	public void saveInfoRole( final String codeRole, final boolean responsableDossier, 
			final boolean reattributionDossier, final Contexte contexte ) throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	objectPooled.saveInfoRole( codeRole, responsableDossier, reattributionDossier, contexte );
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","saveInfoRole","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","saveInfoRole","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
	}

	 /***
	  * Enregistre les options du menu par role
	  * @param table de hashage (codeRole) -> codeOption
	  * @param module
	  * @param tableOptionsMenuRole
	  * @param contexte
	  * @throws TechnicalException
	  */
	 public void setOptionMenuByRole( final AsaModule module, final OptionsMenuRoleMap optionsMenuRole, 
			final Contexte contexte ) throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	objectPooled.setOptionMenuByRole(module, optionsMenuRole, contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","setOptionMenuByRole","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","setOptionMenuByRole","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
	 }

	/***
	 * renvoie la liste des types de visibilites ( table TYPE_VALEUR_VISIBILITE )
	 * @param contexte
	 * @return List<TypeVisibilite>
	 * @throws TechnicalException
	 */
	public List<TypeVisibilite> getTypesVisibilite( final Contexte contexte ) throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getTypesVisibilite(contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getTypesVisibilite","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getTypesVisibilite","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
	}

    /**
     * Renvoie tous les axes de visibilite du module Vente  ( table AXE_VISIBILITE )
     * @param module 
     * @param contexte
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getAxesVisibiliteVente( final Contexte contexte ) throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getAxesVisibiliteVente(contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getAxesVisibiliteVente","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getAxesVisibiliteVente","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

    /**
     * Renvoie tous les axes de visibilite des modules tarif / admin / translate ( table AXE_VISIBILITE )
     * @param module 
     * @param contexte
     * @return List<AxeVisibilite>
     * @throws TechnicalException
     */
    public List<AxeVisibilite> getOtherAxesVisibilite( final Contexte contexte ) throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getOtherAxesVisibilite(contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getOtherAxesVisibilite","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getOtherAxesVisibilite","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }


    /***
     * Ajoute une valeur de visibilite a l'utilisateur.
     * @param v
     * @param codeLangue
     * @param contexte
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public void addVisibilite( final Visibilite v, final Contexte contexte ) 
    		throws IncoherenceException, TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	objectPooled.addVisibilite(v, contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","addVisibilite","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","addVisibilite","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

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
    		final Contexte contexte ) throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getVisibilites(login, codeAxe, codeType, contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getVisibilites","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getVisibilites","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

    /***
     * Renvoie la liste des axes de visibilite sur vente sur lesquels
     *   l'utilisateur a une visibilité complète (All)
     * @param login
     * @param contexte
     * @return List<String>
     * @throws TechnicalException
     */
    public List<String> getAxesWithVisibiliteAll( final String login, final Contexte contexte )
            throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getAxesWithVisibiliteAll(login, contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getAxesWithVisibiliteAll","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getAxesWithVisibiliteAll","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

    /**
     * Renvoie les valeurs de visibilite d'un utilisateur
     * @param Identifiant de l'utilisateur
     * @return List<Visibilite>
     */
    public List<Visibilite> getVisibilites( final String login, final Contexte contexte )
            throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getVisibilites(login, contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getVisibilites","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getVisibilites","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

    /***
     * Supprime une valeur de visibilite d'un utilisateur
     * @param login
     * @param codeAxe
     * @param codeType
     * @param contexte
     * @throws TechnicalException
     */
    public void deleteVisibilites( final String login, final String codeAxe, final Contexte contexte ) 
    		throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	objectPooled.deleteVisibilites(login, codeAxe, contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","deleteVisibilites","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","deleteVisibilites","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }
    

    /**
     * Recherche les infos d un profil dont le code est passe en parametre
     *
     * @param code
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public Profil getProfil( String code, Contexte contexte ) throws TechnicalException, HabilitationException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getProfil(code, contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getProfil","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getProfil","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

    /**
     * Recherche de tous les profils
     * 20030227 : ajout de cache pour les perfs
     *
     * @param contexte utilisé pour le login seulement
     * @return List<Profil>
     */
    public List<Profil> getProfils( final Contexte contexte ) throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getProfils(contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getProfils","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getProfils","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

    /**
     * Recherche de tous les profils ( supprimer inclus )
     * @param contexte 
     * @return List<Profil>
     */
    public List<Profil> getProfilsAdmin( final Contexte contexte ) throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getProfilsAdmin(contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getProfilsAdmin","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getProfilsAdmin","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

    /***
     * methode de mise a jour d'un profil et de ses axes de visibilites
     * @param p
     * @param contexte
     * @param newProfil
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void updateProfil( Profil p, final Contexte contexte, final boolean newProfil ) 
    		throws TechnicalException, IncoherenceException  {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	objectPooled.updateProfil(p, contexte, newProfil);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","updateProfil","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","updateProfil","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }

    /***
     * Methode suppression logique d'un profil
     * @param p
     * @param contexte
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void deleteProfil( final Profil p, final Contexte contexte ) 
    		throws TechnicalException, IncoherenceException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	objectPooled.deleteProfil(p, contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","deleteProfil","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","deleteProfil","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
    }
    
	/***
	 * Methode de recuperation des utilisateurs lie a ce profil
	 * @param idTerritory
	 * @param contexte
	 * @return List<UserLight>
	 * @throws TechnicalException
	 */
	public List<UserLight> getUsersForProfile( final String codeProfil, final Contexte contexte ) 
			throws TechnicalException {
    	HabilitationFacadePooled objectPooled = null;
    	try {
        	objectPooled = (HabilitationFacadePooled)getObjectPooled(HabilitationFacade.POOL_NAME);
        	return objectPooled.getUsersForProfile(codeProfil, contexte);
    	} catch (GlobalPoolException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getUsersForProfile","exception au call en mode pool", e);
    		throw new TechnicalException(e);
    	} catch (TechnicalException e) {
    		LogCommun.minor("","HabilitationFacadePoolable","getUsersForProfile","TechnicalException", e);
    		throw e;
    	} finally {
    		returnObjectToPool(HabilitationFacade.POOL_NAME,objectPooled);
    	}
	}
}

