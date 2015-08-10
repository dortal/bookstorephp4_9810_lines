package com.accor.asa.commun.user.process.pool;

import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.pool.GlobalPoolException;
import com.accor.asa.commun.process.pool.GlobalPoolable;
import com.accor.asa.commun.user.metier.CritereRecherche;
import com.accor.asa.commun.user.metier.SaleOffice;
import com.accor.asa.commun.user.metier.SaleRegion;
import com.accor.asa.commun.user.metier.SaleZone;
import com.accor.asa.commun.user.metier.Territory;
import com.accor.asa.commun.user.metier.User;
import com.accor.asa.commun.user.metier.UserLight;
import com.accor.asa.commun.user.metier.exception.TropDUtilisateursException;
import com.accor.asa.commun.user.metier.exception.UtilisateurInexistantException;
import com.accor.asa.commun.user.process.UserFacade;

public class UserFacadePoolable extends GlobalPoolable implements UserFacade {

    /** init du pool en static */
    static {
        initPool(UserFacade.POOL_NAME);
    }

	/***
	 * Methode de récupération de tous les bureaux de vente ( supprimer compris )
	 * @param contexte
	 * @return List<SaleOffice>
	 * @throws TechnicalException
	 */
	public List<SaleOffice> getSaleOfficesAdmin( final Contexte contexte ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getSaleOfficesAdmin(contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getSaleOfficesAdmin", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de récupération des bureaux de vente
	 * @param contexte
	 * @return List<SaleOffice>
	 * @throws TechnicalException
	 */
	public List<SaleOffice> getSaleOffices( final Contexte contexte ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getSaleOffices(contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getSaleOffices", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

    /**
     * Renvoie les bureaux de vente visibles pour un utilisateur
     * @param login
     * @param contexte
     * @return
     * @throws TechnicalException
     */
	public List<SaleOffice> getSalesOfficeForUser( final String login, Contexte contexte )
            throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getSalesOfficeForUser(login, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getSalesOfficeForUser", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Procedure de creation/modification d'un bureau de vente
	 * @param so
	 * @param contexte
	 * @param newOffice
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void updateSaleOffice( final SaleOffice so, final Contexte contexte, final boolean newOffice ) 
			throws TechnicalException, IncoherenceException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            objectPooled.updateSaleOffice( so, contexte, newOffice );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "updateSaleOffice", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de suppression d'un bureau de vente
	 * @param so
	 * @param contexte
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void deleteSaleOffice( final SaleOffice so, final Contexte contexte ) 
			throws TechnicalException, IncoherenceException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            objectPooled.deleteSaleOffice( so, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "deleteSaleOffice", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de recherche de bureaux de vente
	 * @param so
	 * @param contexte
	 * @param withDeleted
	 * @return List<SaleOffice>
	 * @throws TechnicalException
	 */
	public List<SaleOffice> searchSaleOffices( final SaleOffice so, final Contexte contexte, 
			final boolean withDeleted ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.searchSaleOffices(so, contexte, withDeleted);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "searchSaleOffices", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}
	
	/***
	 * Methode de récupération de toutes les régions de vente ( supprimer comprises )
	 * @param contexte
	 * @return List<SaleRegion>
	 * @throws TechnicalException
	 */
	public List<SaleRegion> getSaleRegionsAdmin( final Contexte contexte ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getSaleRegionsAdmin( contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getSaleRegionsAdmin", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de récupération des régions de vente
	 * @param contexte
	 * @return List<SaleRegion>
	 * @throws TechnicalException
	 */
	public List<SaleRegion> getSaleRegions( final Contexte contexte ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getSaleRegions( contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getSaleRegions", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de creation/modification d'une region de vente
	 * @param sr
	 * @param contexte
	 * @param newRegion
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void updateSaleRegion( final SaleRegion sr, final Contexte contexte, final boolean newRegion ) 
			throws TechnicalException, IncoherenceException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            objectPooled.updateSaleRegion( sr, contexte, newRegion );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "updateSaleRegion", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
		
	}

	/***
	 * Methode de suppression logique d'une région de vente
	 * @param sr
	 * @param contexte
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void deleteSaleRegion( final SaleRegion sr, final Contexte contexte ) 
			throws TechnicalException, IncoherenceException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            objectPooled.deleteSaleRegion( sr, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "deleteSaleRegion", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de recherche de regions de vente
	 * @param sr
	 * @param contexte
	 * @param withDeleted
	 * @return List<SaleRegion>
	 * @throws TechnicalException
	 */
	public List<SaleRegion> searchSaleRegions( final SaleRegion sr, final Contexte contexte, 
			final boolean withDeleted ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.searchSaleRegions(sr, contexte, withDeleted);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "searchSaleRegions", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}
	
	/***
	 * Methode de récupération de toutes les zones de vente ( supprimer comprises )
	 * @param contexte
	 * @return List<SaleZone>
	 * @throws TechnicalException
	 */
	public List<SaleZone> getSaleZonesAdmin( final Contexte contexte ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getSaleZonesAdmin( contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getSaleZonesAdmin", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
		
	}

	/***
	 * Methode de récupération des zones de vente
	 * @param contexte
	 * @return List<SaleZone>
	 * @throws TechnicalException
	 */
	public List<SaleZone> getSaleZones( final Contexte contexte ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getSaleZones( contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getSaleZones", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
		
	}

	/***
	 * Methode de creation/modification d'une zone de vente
	 * @param sz
	 * @param contexte
	 * @param newZone
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void updateSaleZone( final SaleZone sz, final Contexte contexte, final boolean newZone ) 
			throws TechnicalException, IncoherenceException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            objectPooled.updateSaleZone( sz, contexte, newZone );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "updateSaleZone", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
		
	}

	/***
	 * Methode de suppression logique d'une zone de vente
	 * @param sz
	 * @param contexte
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void deleteSaleZone( final SaleZone sz, final Contexte contexte ) 
			throws TechnicalException, IncoherenceException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            objectPooled.deleteSaleZone( sz, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "deleteSaleZone", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de recherche d'une liste de zone de vente
	 * @param sz
	 * @param contexte
	 * @param withDeleted
	 * @return List<SaleZone>
	 * @throws TechnicalException
	 */
	public List<SaleZone> searchSaleZones( final SaleZone sz, final Contexte contexte, 
			final boolean withDeleted ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.searchSaleZones(sz, contexte, withDeleted);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "searchSaleZones", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}
	
	/***
	 * Methode de récupération de tous les territoires ( supprimer compris )
	 * @param contexte
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	public List<Territory> getTerritoriesAdmin( final Contexte contexte ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getTerritoriesAdmin( contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getTerritoriesAdmin", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}
	
	/***
	 * Methode de récupération des territoires 
	 * @param contexte
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	public List<Territory> getTerritories( final Contexte contexte ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getTerritories( contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getTerritories", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}
	
	/***
	 * Methode de suppression logique d'un territoire
	 * @param t
	 * @param contexte
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void deleteTerritory( final Territory t, final Contexte contexte ) 
			throws TechnicalException, IncoherenceException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            objectPooled.deleteTerritory( t, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "deleteTerritory", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de creation/modification des infos d'un territoire
	 * @param t
	 * @param contexte
	 * @param newTerritory
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void updateTerritory( final Territory t, final Contexte contexte, final boolean newTerritory ) 
			throws TechnicalException, IncoherenceException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            objectPooled.updateTerritory( t, contexte, newTerritory );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "updateTerritory", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de recuperation des types de territoires
	 * @param contexte
	 * @return List<Element>
	 * @throws TechnicalException
	 */
	public List<Element> getTypeTerritories( final Contexte contexte ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getTypeTerritories( contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getTypeTerritories", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de recuperation des noms de comptes appartenant a un territoire
	 * @param idTerritory
	 * @param contexte
	 * @return List<Element>
	 * @throws TechnicalException
	 */
	public List<Element> getAccountsForTerritory( final String idTerritory, final Contexte contexte ) throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getAccountsForTerritory(idTerritory, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getAccountsForTerritory", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de recuperation de l'utilisateur lie a ce territoire
	 * @param idTerritory
	 * @param contexte
	 * @return UserLight
	 * @throws TechnicalException
	 */
	public UserLight getUserForTerritory( final String idTerritory, final Contexte contexte ) 
			throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getUserForTerritory(idTerritory, contexte);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getUserForTerritory", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de recherche d'une liste de territoires
	 * @param t
	 * @param contexte
	 * @param withDeleted
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	public List<Territory> searchTerritories( final Territory t, final Contexte contexte, final boolean withDeleted ) 
			throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.searchTerritories(t, contexte, withDeleted);
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "searchTerritories", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de recuperation des informations lies a un utilisateur
	 * @param login
	 * @param contexte
	 * @return
	 * @throws UtilisateurInexistantException
	 * @throws TechnicalException
	 */
	public User getUser( final String login, final Contexte contexte ) 
			throws UtilisateurInexistantException, TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getUser( login, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getUser", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}
	
    /***
     * Methode de creation/modification d'un utilisateur
     * @param user
     * @param contexte
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public void updateUser( final User user, final Contexte contexte, final boolean newUser )
    		throws TechnicalException, IncoherenceException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            objectPooled.updateUser( user, contexte, newUser );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "updateUser", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
    }

    /***
     * Methode de suppression logique d'un utilisateur
     * @param user
     * @param contexte
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void deleteUser( final User user, final Contexte contexte ) 
    		throws TechnicalException, IncoherenceException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            objectPooled.deleteUser( user, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "deleteUser", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
    }

    /***
     * Methode de changement du mot de passe de l'utilisateur donne
     * @param login
     * @param password
     * @param contexte
     * @throws TechnicalException
     */
    public void setPassword( final String login, final String password, final Contexte contexte ) 
    		throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            objectPooled.setPassword( login, password, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "setPassword", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
    }

    /**
	 * Recherche multi-criteres sur les utilisateurs
	 * @param Les criteres de recherche @see com.accor.commun.user.metier.CritereRecherche
	 * @param contexte
	 * @return List<User>
	 * @throws com.accor.commun.TechnicalException
	 * @throws com.accor.commun.utilisateur.metier.TropDUtilisateursException si on 
	 *          dépasse le nombre max de lignes (par défaut celui des grandes listes)
	 */
	public List<User> searchUsers( final CritereRecherche critere, final Contexte contexte ) 
			throws TechnicalException, TropDUtilisateursException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.searchUsers( critere, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "searchUsers", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

    /***
     * Renvoie si le password actuel de l'utilisateur est toujours le passord par defaut
     * @param login
     * @param password
     * @return
     * @throws TechnicalException
     */
    public boolean isDefaultPassword( final String login, final String password, final Contexte contexte ) 
    		throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.isDefaultPassword( login, password, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "isDefaultPassword", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
    }

    /***
     * Methode de verification du mot de passe de l utilisateur
     * @param login
     * @param password
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public boolean checkPassword( final String login, final String password, final Contexte contexte ) 
    		throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.checkPassword( login, password, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "checkPassword", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
    }

	/***
	 * Methode de recuperation d un bureau de vente a partir de son code
	 * @param codeOffice
	 * @param contexte
	 * @return
	 */
	public SaleOffice getSaleOffice( final String codeOffice, final Contexte contexte ) 
			throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getSaleOffice( codeOffice, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getSaleOffice", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

    /***
	 * Methode de recuperation d une region de vente a partir de son code
	 * @param codeRegion
	 * @param contexte
	 * @return
	 */
	public SaleRegion getSaleRegion( final String codeRegion, final Contexte contexte ) 
			throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getSaleRegion( codeRegion, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getSaleRegion", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

	/***
	 * Methode de recuperation d une zone de vente a partir de son code
	 * @param codeRegion
	 * @param contexte
	 * @return
	 */
	public SaleZone getSaleZone( final String codeZone, final Contexte contexte ) 
			throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getSaleZone( codeZone, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getSaleZone", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
	}

    /**
     * Retourne la liste des territoires d'un utilisateur.
     * @param accountManager
     * @param contexte
     * @return List<Territory>
     * @throws TechnicalException
     */
	public List<Territory> getTerritories( final User accountManager, final Contexte contexte )
            throws TechnicalException {
        UserFacadePooled objectPooled = null;
        try {
            objectPooled = (UserFacadePooled)getObjectPooled(UserFacade.POOL_NAME);
            return objectPooled.getTerritories( accountManager, contexte );
        } catch (GlobalPoolException e) {
            LogCommun.minor(contexte.getCodeUtilisateur(), "UserFacadePoolable", "getTerritories", "exception au call en mode pool", e);
            throw new TechnicalException(e);
        } finally {
            returnObjectToPool(UserFacadePooled.POOL_NAME,objectPooled);
        }
    }
}
