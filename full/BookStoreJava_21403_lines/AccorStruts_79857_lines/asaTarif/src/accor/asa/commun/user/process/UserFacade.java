package com.accor.asa.commun.user.process;

import java.util.List;

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

public interface UserFacade {

	public static final String POOL_NAME = "communUserFacade";
	
	/***
	 * Methode de récupération de tous les bureaux de vente ( supprimer compris )
	 * @param contexte
	 * @return List<SaleOffice>
	 * @throws TechnicalException
	 */
	public List<SaleOffice> getSaleOfficesAdmin( final Contexte contexte ) throws TechnicalException;

	/***
	 * Methode de récupération des bureaux de vente
	 * @param contexte
	 * @return List<SaleOffice>
	 * @throws TechnicalException
	 */
	public List<SaleOffice> getSaleOffices( final Contexte contexte ) throws TechnicalException;

	/***
	 * Methode de creation/modification d'un bureau de vente
	 * @param so
	 * @param contexte
	 * @param newOffice
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void updateSaleOffice( final SaleOffice so, final Contexte contexte, final boolean newOffice ) 
			throws TechnicalException, IncoherenceException;

	/***
	 * Methode de suppression d'un bureau de vente
	 * @param so
	 * @param contexte
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void deleteSaleOffice( final SaleOffice so, final Contexte contexte ) 
			throws TechnicalException, IncoherenceException ;

	/***
	 * Methode de recherche de bureaux de vente
	 * @param so
	 * @param contexte
	 * @param withDeleted
	 * @return List<SaleOffice>
	 * @throws TechnicalException
	 */
	public List<SaleOffice> searchSaleOffices( final SaleOffice so, final Contexte contexte, 
			final boolean withDeleted ) throws TechnicalException;

	/***
	 * Methode de récupération de toutes les régions de vente ( supprimer comprises )
	 * @param contexte
	 * @return List<SaleRegion>
	 * @throws TechnicalException
	 */
	public List<SaleRegion> getSaleRegionsAdmin( final Contexte contexte ) throws TechnicalException;

	/***
	 * Methode de récupération des régions de vente
	 * @param contexte
	 * @return List<SaleRegion>
	 * @throws TechnicalException
	 */
	public List<SaleRegion> getSaleRegions( final Contexte contexte ) throws TechnicalException;

	/***
	 * Methode de creation/modification d'une region de vente
	 * @param sr
	 * @param contexte
	 * @param newRegion
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void updateSaleRegion( final SaleRegion sr, final Contexte contexte, final boolean newRegion ) 
			throws TechnicalException, IncoherenceException;

	/***
	 * Methode de suppression logique d'une région de vente
	 * @param sr
	 * @param contexte
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void deleteSaleRegion( final SaleRegion sr, final Contexte contexte ) 
			throws TechnicalException, IncoherenceException;

	/***
	 * Methode de recherche de regions de vente
	 * @param sr
	 * @param contexte
	 * @param withDeleted
	 * @return List<SaleRegion>
	 * @throws TechnicalException
	 */
	public List<SaleRegion> searchSaleRegions( final SaleRegion sr, final Contexte contexte, 
			final boolean withDeleted ) throws TechnicalException;
	
	/***
	 * Methode de récupération de toutes les zones de vente ( supprimer comprises )
	 * @param contexte
	 * @return List<SaleZone>
	 * @throws TechnicalException
	 */
	public List<SaleZone> getSaleZonesAdmin( final Contexte contexte ) throws TechnicalException;

	/***
	 * Methode de récupération des zones de vente
	 * @param contexte
	 * @return List<SaleZone>
	 * @throws TechnicalException
	 */
	public List<SaleZone> getSaleZones( final Contexte contexte ) throws TechnicalException;

    /**
     * Renvoie les bureaux de vente visibles pour un utilisateur
     * @param login
     * @param contexte
     * @return
     * @throws TechnicalException
     */
	public List<SaleOffice> getSalesOfficeForUser( final String login, Contexte contexte )
            throws TechnicalException;

    /***
	 * Methode de creation/modification d'une zone de vente
	 * @param sz
	 * @param contexte
	 * @param newZone
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void updateSaleZone( final SaleZone sz, final Contexte contexte, final boolean newZone ) 
			throws TechnicalException, IncoherenceException;

	/***
	 * Methode de suppression logique d'une zone de vente
	 * @param sz
	 * @param contexte
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void deleteSaleZone( final SaleZone sz, final Contexte contexte ) 
			throws TechnicalException, IncoherenceException;

	/***
	 * Methode de recherche d'une liste de zone de vente
	 * @param sz
	 * @param contexte
	 * @param withDeleted
	 * @return List<SaleZone>
	 * @throws TechnicalException
	 */
	public List<SaleZone> searchSaleZones( final SaleZone sz, final Contexte contexte, 
			final boolean withDeleted ) throws TechnicalException;
	
	/***
	 * Methode de récupération de tous les territoires ( supprimer compris )
	 * @param contexte
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	public List<Territory> getTerritoriesAdmin( final Contexte contexte ) throws TechnicalException;
	
	/***
	 * Methode de récupération des territoires 
	 * @param contexte
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	public List<Territory> getTerritories( final Contexte contexte ) throws TechnicalException;
	
	/***
	 * Methode de suppression logique d'un territoire
	 * @param t
	 * @param contexte
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void deleteTerritory( final Territory t, final Contexte contexte ) 
			throws TechnicalException, IncoherenceException;

	/***
	 * Methode de creation/modification d'un territoire
	 * @param t
	 * @param contexte
	 * @param newTerritory
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 */
	public void updateTerritory( final Territory t, final Contexte contexte, final boolean newTerritory ) 
			throws TechnicalException, IncoherenceException;

	/***
	 * Methode de recuperation des types de territoires
	 * @param contexte
	 * @return List<Element>
	 * @throws TechnicalException
	 */
	public List<Element> getTypeTerritories( final Contexte contexte ) throws TechnicalException;

	/***
	 * Methode de recuperation des noms de comptes appartenant a un territoire
	 * @param idTerritory
	 * @param contexte
	 * @return List<Element>
	 * @throws TechnicalException
	 */
	public List<Element> getAccountsForTerritory( final String idTerritory, final Contexte contexte ) 
			throws TechnicalException;

	/***
	 * Methode de recuperation de l'utilisateur lie a ce territoire
	 * @param idTerritory
	 * @param contexte
	 * @return UserLight
	 * @throws TechnicalException
	 */
	public UserLight getUserForTerritory( final String idTerritory, final Contexte contexte ) 
			throws TechnicalException;

	/***
	 * Methode de recherche d'une liste de territoires
	 * @param t
	 * @param contexte
	 * @param withDeleted
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	public List<Territory> searchTerritories( final Territory t, final Contexte contexte, final boolean withDeleted )
			throws TechnicalException;


	/***
	 * Methode de recuperation des informations lies a un utilisateur
	 * @param login
	 * @param contexte
	 * @return
	 * @throws UtilisateurInexistantException
	 * @throws TechnicalException
	 */
	public User getUser( final String login, final Contexte contexte ) 
			throws UtilisateurInexistantException, TechnicalException;
	
    /***
     * Methode de creation/modification d'un utilisateur
     * @param user
     * @param contexte
     * @throws IncoherenceException
     * @throws TechnicalException
     */
    public void updateUser( User user, final Contexte contexte, final boolean newUser )
    		throws TechnicalException, IncoherenceException;

    /***
     * Methode de suppression logique d'un utilisateur
     * @param user
     * @param contexte
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void deleteUser( final User user, final Contexte contexte ) 
    		throws TechnicalException, IncoherenceException;

    /***
     * Methode de changement du mot de passe de l'utilisateur donne
     * @param login
     * @param password
     * @param contexte
     * @throws TechnicalException
     */
    public void setPassword( final String login, final String password, final Contexte contexte ) 
    		throws TechnicalException;

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
			throws TechnicalException, TropDUtilisateursException;

    /***
     * Renvoie si le password actuel de l'utilisateur est toujours le passord par defaut
     * @param login
     * @param password
     * @return
     * @throws TechnicalException
     */
    public boolean isDefaultPassword( final String login, final String password, final Contexte contexte ) 
			throws TechnicalException;

    /***
     * Methode de verification du mot de passe de l utilisateur
     * @param login
     * @param password
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    public boolean checkPassword( final String login, final String password, final Contexte contexte ) 
    		throws TechnicalException;

	/***
	 * Methode de recuperation d un bureau de vente a partir de son code
	 * @param codeOffice
	 * @param contexte
	 * @return
	 */
	public SaleOffice getSaleOffice( final String codeOffice, final Contexte contexte ) 
			throws TechnicalException;

	/***
	 * Methode de recuperation d une region de vente a partir de son code
	 * @param codeRegion
	 * @param contexte
	 * @return
	 */
	public SaleRegion getSaleRegion( final String codeRegion, final Contexte contexte ) 
			throws TechnicalException;

	/***
	 * Methode de recuperation d une zone de vente a partir de son code
	 * @param codeZone
	 * @param contexte
	 * @return
	 */
	public SaleZone getSaleZone( final String codeZone, final Contexte contexte ) 
			throws TechnicalException;

    /**
     * Retourne la liste des territoires d'un utilisateur.
     * @param accountManager
     * @param contexte
     * @return List<Territory>
     * @throws TechnicalException
     */
	public List<Territory> getTerritories( final User accountManager, final Contexte contexte )
            throws TechnicalException;
}
