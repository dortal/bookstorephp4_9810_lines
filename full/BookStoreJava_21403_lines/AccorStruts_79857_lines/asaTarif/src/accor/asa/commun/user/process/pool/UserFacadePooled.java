package com.accor.asa.commun.user.process.pool;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.user.metier.CritereRecherche;
import com.accor.asa.commun.user.metier.SaleOffice;
import com.accor.asa.commun.user.metier.SaleRegion;
import com.accor.asa.commun.user.metier.SaleZone;
import com.accor.asa.commun.user.metier.Territory;
import com.accor.asa.commun.user.metier.User;
import com.accor.asa.commun.user.metier.UserLight;
import com.accor.asa.commun.user.metier.exception.TropDUtilisateursException;
import com.accor.asa.commun.user.metier.exception.UtilisateurInexistantException;
import com.accor.asa.commun.user.persistance.SaleOfficeDAO;
import com.accor.asa.commun.user.persistance.SaleRegionDAO;
import com.accor.asa.commun.user.persistance.SaleZoneDAO;
import com.accor.asa.commun.user.persistance.SearchUserQBEDAO;
import com.accor.asa.commun.user.persistance.TerritoryDAO;
import com.accor.asa.commun.user.persistance.UserDAO;
import com.accor.asa.commun.user.process.UserFacade;

public class UserFacadePooled implements UserFacade {

	public UserFacadePooled() {}
	
	/***
	 * Methode de récupération de tous les bureaux de vente ( supprimer compris )
	 * @param contexte
	 * @return List<SaleOffice>
	 * @throws TechnicalException
	 */
	public List<SaleOffice> getSaleOfficesAdmin( final Contexte contexte ) throws TechnicalException {
		return SaleOfficeDAO.getInstance().getSaleOfficesAdmin( contexte );
	}
	
	/***
	 * Methode de récupération des bureaux de vente
	 * @param contexte
	 * @return List<SaleOffice>
	 * @throws TechnicalException
	 */
	public List<SaleOffice> getSaleOffices( final Contexte contexte ) throws TechnicalException {
		return SaleOfficeDAO.getInstance().getSaleOffices( contexte );
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
		return SaleOfficeDAO.getInstance().getSalesOfficeForUser( login, contexte );
	}
	
	/***
	 * Methode de creation/modification d'un bureau de vente
	 * @param so
	 * @param contexte
	 * @param newOffice
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void updateSaleOffice( final SaleOffice so, final Contexte contexte, final boolean newOffice ) 
			throws TechnicalException, IncoherenceException {
		
		List<SaleOffice> offices = getSaleOfficesAdmin( contexte );
		int codeRetour = SaleOffice.NO_CONSTRAINT_ERROR;
		
		if( newOffice ) {
			if( so.notExist( offices ) ) {
				codeRetour = SaleOfficeDAO.getInstance().insertSaleOffice( so, contexte );
			} else {
				throw new IncoherenceException( 
						SaleOffice.getErrorMessage( SaleOffice.DUPLICATE_SALEOFFICE_CONSTRAINT_ERROR , contexte ) );
			}
		} else {
			if( so.isUnique( offices ) ) {
				codeRetour = SaleOfficeDAO.getInstance().updateSaleOffice( so, contexte );
			} else {
				throw new IncoherenceException( 
						SaleOffice.getErrorMessage( SaleOffice.DUPLICATE_SALEOFFICE_CONSTRAINT_ERROR , contexte ) );
			}
		}
		
		if( codeRetour != SaleOffice.NO_CONSTRAINT_ERROR ) {
			throw new IncoherenceException( SaleOffice.getErrorMessage( codeRetour, contexte ) );
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
		int codeRetour = SaleOfficeDAO.getInstance().deleteSaleOffice( new Integer( so.getCode() ), contexte );
		if( codeRetour != SaleOffice.NO_CONSTRAINT_ERROR ) {
			throw new IncoherenceException( SaleOffice.getErrorMessage( codeRetour, contexte ) );
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
		return SaleOfficeDAO.getInstance().searchSaleOffices( so, contexte, withDeleted );
	}
	
	/***
	 * Methode de récupération de toutes les régions de vente ( supprimer comprises )
	 * @param contexte
	 * @return List<SaleRegion>
	 * @throws TechnicalException
	 */
	public List<SaleRegion> getSaleRegionsAdmin( final Contexte contexte ) throws TechnicalException {
		return SaleRegionDAO.getInstance().getSaleRegionsAdmin( contexte );
	}

	/***
	 * Methode de récupération des régions de vente
	 * @param contexte
	 * @return List<SaleRegion>
	 * @throws TechnicalException
	 */
	public List<SaleRegion> getSaleRegions( final Contexte contexte ) throws TechnicalException {
		return SaleRegionDAO.getInstance().getSaleRegions( contexte );
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
		
		List<SaleRegion> regions = getSaleRegionsAdmin( contexte );
		int codeRetour = SaleRegion.NO_CONSTRAINT_ERROR;
		
		if( newRegion ) {
			if( sr.notExist( regions ) ) {
				codeRetour = SaleRegionDAO.getInstance().insertSaleRegion( sr, contexte );
			} else {
				throw new IncoherenceException( 
						SaleRegion.getErrorMessage( SaleRegion.DUPLICATE_SALEZONE_CONSTRAINT_ERROR, contexte ) );
			}
		} else {
			if( sr.isUnique( regions ) ) {
				codeRetour = SaleRegionDAO.getInstance().updateSaleRegion( sr, contexte );
			} else {
				throw new IncoherenceException( 
						SaleRegion.getErrorMessage( SaleRegion.DUPLICATE_SALEZONE_CONSTRAINT_ERROR, contexte ) );
			}
		}
		
		if( codeRetour != Territory.NO_CONSTRAINT_ERROR ) {
			throw new IncoherenceException( SaleRegion.getErrorMessage( codeRetour, contexte ) );
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
		int codeRetour = SaleRegionDAO.getInstance().deleteSaleRegion( sr.getCode(), contexte );
		if( codeRetour != SaleRegion.NO_CONSTRAINT_ERROR ) {
			throw new IncoherenceException( SaleRegion.getErrorMessage( codeRetour, contexte ) );
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
		return SaleRegionDAO.getInstance().searchSaleRegions( sr, contexte, withDeleted );
	}
	
	/***
	 * Methode de récupération de toutes les zones de vente ( supprimer comprises )
	 * @param contexte
	 * @return List<SaleZone>
	 * @throws TechnicalException
	 */
	public List<SaleZone> getSaleZonesAdmin( final Contexte contexte ) throws TechnicalException {
		return SaleZoneDAO.getInstance().getSaleZonesAdmin( contexte );
	}

	/***
	 * Methode de récupération des zones de vente
	 * @param contexte
	 * @return List<SaleZone>
	 * @throws TechnicalException
	 */
	public List<SaleZone> getSaleZones( final Contexte contexte ) throws TechnicalException {
		return SaleZoneDAO.getInstance().getSaleZones( contexte );
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

		List<SaleZone> zones = getSaleZonesAdmin( contexte );
		int codeRetour = SaleZone.NO_CONSTRAINT_ERROR;
		
		if( newZone ) {
			if( sz.notExist( zones ) ) {
				codeRetour = SaleZoneDAO.getInstance().insertSaleZone( sz, contexte );	
			} else {
				throw new IncoherenceException( 
						SaleZone.getErrorMessage( SaleZone.DUPLICATE_SALEZONE_CONSTRAINT_ERROR , contexte ) );
			}
		} else {
			if( sz.isUnique( zones ) ) {
				codeRetour = SaleZoneDAO.getInstance().updateSaleZone( sz, contexte );	
			} else {
				throw new IncoherenceException( 
						SaleZone.getErrorMessage( SaleZone.DUPLICATE_SALEZONE_CONSTRAINT_ERROR , contexte ) );
			}
		}

		if( codeRetour != SaleZone.NO_CONSTRAINT_ERROR ) {
			throw new IncoherenceException( SaleZone.getErrorMessage( codeRetour, contexte ) );
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
		int codeRetour = SaleZoneDAO.getInstance().deleteSaleZone( sz.getCode(), contexte );
		if( codeRetour != SaleZone.NO_CONSTRAINT_ERROR ) {
			throw new IncoherenceException( SaleZone.getErrorMessage( codeRetour, contexte ) );
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
		return SaleZoneDAO.getInstance().searchSaleZones( sz, contexte, withDeleted );
	}
	
	/***
	 * Methode de récupération de tous les territoires ( supprimer compris )
	 * @param contexte
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	public List<Territory> getTerritoriesAdmin( final Contexte contexte ) throws TechnicalException {
		return TerritoryDAO.getInstance().getTerritoriesAdmin( contexte );
	}
	
	/***
	 * Methode de récupération des territoires 
	 * @param contexte
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	public List<Territory> getTerritories( final Contexte contexte ) throws TechnicalException {
		return TerritoryDAO.getInstance().getTerritories( contexte );
	}
	
	/***
	 * Methode de suppression logique d'un territoire
	 * @param t
	 * @param contexte
	 * @throws TechnicalException
	 */
	public void deleteTerritory( final Territory t, final Contexte contexte ) 
			throws TechnicalException, IncoherenceException {
		int codeRetour = TerritoryDAO.getInstance().deleteTerritory( t.getCode(), contexte );
		if( codeRetour != Territory.NO_CONSTRAINT_ERROR ) {
			throw new IncoherenceException( Territory.getErrorMessage( codeRetour, t.getCode(), contexte ) );
		}
	}

	/***
	 * Methode de creation/modification d'un territoire
	 * @param t
	 * @param contexte
	 * @throws TechnicalException
	 */
	public void updateTerritory( final Territory t, final Contexte contexte, final boolean newTerritory ) 
			throws TechnicalException, IncoherenceException {
		
		int codeRetour = Territory.NO_CONSTRAINT_ERROR; 
		
		if( newTerritory ) {
			List<Territory> territories = getTerritoriesAdmin( contexte );
			if( t.notExist( territories ) ) {
				codeRetour = TerritoryDAO.getInstance().insertTerritory( t, contexte );
			} else {
				throw new IncoherenceException( 
						Territory.getErrorMessage( Territory.DUPLICATE_TERRITORY_CONSTRAINT_ERROR, t.getCode(), contexte ) );
			}
		} else {
			codeRetour = TerritoryDAO.getInstance().updateTerritory( t, contexte );
		}
		
		if( codeRetour != Territory.NO_CONSTRAINT_ERROR ) {
			throw new IncoherenceException( Territory.getErrorMessage( codeRetour, t.getCode(), contexte ) );
		}
	}

	/***
	 * Methode de recuperation des types de territoires
	 * @param contexte
	 * @return List<Element>
	 * @throws TechnicalException
	 */
	public List<Element> getTypeTerritories( final Contexte contexte ) throws TechnicalException {
		return TerritoryDAO.getInstance().getTypeTerritories( contexte );
	}
	
	/***
	 * Methode de recuperation des noms de comptes appartenant a un territoire
	 * @param idTerritory
	 * @param contexte
	 * @return List<Element>
	 * @throws TechnicalException
	 */
	public List<Element> getAccountsForTerritory( final String idTerritory, final Contexte contexte ) 
			throws TechnicalException {
		return TerritoryDAO.getInstance().getAccounts( idTerritory, contexte );
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
		return TerritoryDAO.getInstance().getUser( idTerritory, contexte );
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
		return TerritoryDAO.getInstance().searchTerritories( t, contexte, withDeleted );
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
		return UserDAO.getInstance().getUser( login, contexte );
	}
	
    /***
     * Methode de creation/modification d'un utilisateur
     * @param user
     * @param contexte
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public void updateUser( User user, final Contexte contexte, final boolean newUser )
    		throws TechnicalException, IncoherenceException {
    	
    	int codeRetour = User.NO_CONSTRAINT_ERROR;
    	User connectedUser = contexte.getUtilisateurValue();
    	if( newUser ) {
    		if(connectedUser != null){
    			user.setAuthor(connectedUser.getLogin());
    			user.setUpdater(connectedUser.getLogin());
    		}
			codeRetour = UserDAO.getInstance().insertUser( user, contexte );
    	} else {
    		if(connectedUser != null){
    			user.setUpdater(connectedUser.getLogin());
    		}
			UserDAO.getInstance().updateUser( user, contexte );
    	}

		if( codeRetour != User.NO_CONSTRAINT_ERROR ) {
			throw new IncoherenceException( User.getErrorMessage( codeRetour, contexte ) );
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
    	int codeRetour = UserDAO.getInstance().deleteUser( user.getLogin(), contexte );
    	if( codeRetour != User.NO_CONSTRAINT_ERROR )
    		throw new IncoherenceException( User.getErrorMessage( codeRetour, contexte ) );
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
    	UserDAO.getInstance().setPassword( login, password, contexte );
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
		return SearchUserQBEDAO.getInstance().searchUsers( critere, contexte );
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
    	return UserDAO.getInstance().isDefaultPassword( login, password );
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
    	return UserDAO.getInstance().checkPassword( login, password, contexte );
    }

	
	/***
	 * Methode de recuperation d un bureau de vente a partir de son code
	 * @param codeOffice
	 * @param contexte
	 * @return
	 */
	public SaleOffice getSaleOffice( final String codeOffice, final Contexte contexte ) 
			throws TechnicalException {
		
		List<SaleOffice> offices = getSaleOffices(contexte);
		
		if( offices != null && StringUtils.isNotBlank( codeOffice ) ) {
			for( int i=0, size=offices.size(); i<size; i++ ) {
				if( StringUtils.equals( codeOffice, offices.get(i).getCode() ) ) {
					return offices.get(i);
				}
			}
		}
		return null;
	}
	
	/***
	 * Methode de recuperation d une region de vente a partir de son code
	 * @param codeRegion
	 * @param contexte
	 * @return
	 */
	public SaleRegion getSaleRegion( final String codeRegion, final Contexte contexte ) 
			throws TechnicalException {
		
		List<SaleRegion> regions = getSaleRegions(contexte);
		
		if( regions != null && StringUtils.isNotBlank( codeRegion ) ) {
			for( int i=0, size=regions.size(); i<size; i++ ) {
				if( StringUtils.equals( codeRegion , regions.get(i).getCode() ) ) 
					return regions.get(i);
			}
		}
		return null;
	}

	/***
	 * Methode de recuperation d une zone de vente a partir de son code
	 * @param codeZone
	 * @param contexte
	 * @return
	 */
	public SaleZone getSaleZone( final String codeZone, final Contexte contexte ) 
			throws TechnicalException {
		
		List<SaleZone> zones = getSaleZones(contexte);
		
		if( zones != null && StringUtils.isNotBlank( codeZone ) ) {
			for( int i=0, size=zones.size(); i<size; i++ ) {
				if( StringUtils.equals( codeZone , zones.get(i).getCode() ) ) 
					return zones.get(i);
			}
		}
		return null;
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
		return UserDAO.getInstance().getTerritories( accountManager, contexte );
    }
}
