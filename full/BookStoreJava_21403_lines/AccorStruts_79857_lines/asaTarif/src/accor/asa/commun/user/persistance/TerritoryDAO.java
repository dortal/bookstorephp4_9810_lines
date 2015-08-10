package com.accor.asa.commun.user.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.user.metier.ListTerritories;
import com.accor.asa.commun.user.metier.ListTypeTerritories;
import com.accor.asa.commun.user.metier.Territory;
import com.accor.asa.commun.user.metier.UserLight;

public class TerritoryDAO extends DAO {

	
	private final static String PROC_GET_TERRITORIES 		= "proc_sel_territoire";
	private final static String PROC_GET_TYPE_TERRITORIES 	= "proc_sel_type_territoire";
	private final static String PROC_SEARCH_TERRITORIES		= "proc_search_territoire";
	private final static String PROC_INSERT_TERRITORY		= "admin_insert_territoire";
	private final static String PROC_UPDATE_TERRITORY		= "admin_update_territoire";
	private final static String PROC_DELETE_TERRITORY 		= "admin_delete_territoire";
	private final static String PROC_GET_ACCOUNT_TERRITORY	= "proc_get_compte_by_territoire";
	private final static String PROC_GET_USER_TERRITORY		= "proc_get_user_by_territoire";
	
	private static TerritoryDAO _instance = new TerritoryDAO();
	
	private TerritoryDAO() {}
	
	public static TerritoryDAO getInstance() {
		return _instance;
	}

	/***
	 * Methode de récupération de tous les territoires ( supprimer compris )
	 * @param contexte
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	public List<Territory> getTerritoriesAdmin( final Contexte contexte ) throws TechnicalException {
		return initTerritories( true, contexte );
	}
	
	/***
	 * Methode de récupération des territoires 
	 * @param contexte
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	public List<Territory> getTerritories( final Contexte contexte ) throws TechnicalException {

		ListTerritories territories = (ListTerritories) 
			CacheManager.getInstance().getObjectInCache( ListTerritories.class );
		
		if( territories == null ) {
			territories = new ListTerritories( initTerritories( false, contexte ) );
			CacheManager.getInstance().setObjectInCache( territories );
		}
		return territories.getElements();
	}
	
	/***
	 * Methode de suppression logique d'un territoire
	 * @param code
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	public int deleteTerritory( final String code, final Contexte contexte ) throws TechnicalException {
		int codeRetour = Territory.NO_CONSTRAINT_ERROR;
		try {
			if( StringUtils.isNotBlank( code ) ) {
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( code, false, Types.CHAR )
				};
				
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_DELETE_TERRITORY, new SQLParamDescriptorSet( params ), 
						"TerritoryDAO", "deleteTerritory", contexte.getContexteAppelDAO() );
			}
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "TerritoryDAO", "deleteTerritory", e.getMessage(), e );
			throw new TechnicalException(e);
		}
		return codeRetour;
	}
	
	/***
	 * Methode de mise a jour des infos d'un territoire
	 * @param t
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	public int updateTerritory( final Territory t, final Contexte contexte ) throws TechnicalException {
		
		int codeRetour = Territory.NO_CONSTRAINT_ERROR;
		try {
			if( t!= null ) {
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( t.getCode(), false, Types.CHAR ),
						new SQLParamDescriptor( t.getLibelle(), false, Types.VARCHAR ),
						new SQLParamDescriptor( t.getTypeCode(), false, Types.SMALLINT ),
						new SQLParamDescriptor( t.getManagerID(), false, Types.INTEGER ),
						new SQLParamDescriptor( new Boolean( t.isByDefault() ), false, Types.BIT )
				};
				
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_UPDATE_TERRITORY, new SQLParamDescriptorSet( params ), 
						"TerritoryDAO", "updateTerritory", contexte.getContexteAppelDAO() );
			}
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "TerritoryDAO", "updateTerritory", e.getMessage(), e );
			throw new TechnicalException(e);
		}
		return codeRetour;
	}
	
	/***
	 * Methode d'insertion d'un nouveau territoire
	 * @param t
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	public int insertTerritory( final Territory t, final Contexte contexte ) throws TechnicalException {
		
		int codeRetour = Territory.NO_CONSTRAINT_ERROR;
		try {
			if( t != null ) {
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( t.getCode(), false, Types.CHAR ),
						new SQLParamDescriptor( t.getLibelle(), false, Types.VARCHAR ),
						new SQLParamDescriptor( t.getTypeCode(), false, Types.SMALLINT ),
						new SQLParamDescriptor( t.getManagerID(), false, Types.INTEGER ),
						new SQLParamDescriptor( new Boolean( t.isByDefault() ), false, Types.BIT )
				};
				
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_INSERT_TERRITORY, new SQLParamDescriptorSet( params ), 
						"TerritoryDAO", "insertTerritory", contexte.getContexteAppelDAO() );
			}
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "TerritoryDAO", "insertTerritory", e.getMessage(), e );
			throw new TechnicalException(e);
		}
		return codeRetour;
	}

	/***
	 * Methode de recuperation des types de territoires
	 * @param contexte
	 * @return List<Element>
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<Element> getTypeTerritories( final Contexte contexte ) throws TechnicalException {
		
		String codeLangue = contexte.getCodeLangue();
		
		ListTypeTerritories types = (ListTypeTerritories) 
			CacheManager.getInstance().getObjectInCache( ListTypeTerritories.class, codeLangue );
		
		if( types == null ) {
			try {
				SQLParamDescriptor param = new SQLParamDescriptor( codeLangue, false, Types.CHAR );
				
				List<Element> res = (List<Element>) SQLCallExecuter.getInstance()
						.executeSelectProc( PROC_GET_TYPE_TERRITORIES, 
						new SQLParamDescriptorSet( param ), "TerritoryDAO", "getTypeTerritories", 
						contexte.getContexteAppelDAO(),
						new SQLResultSetReader() {
								public Object instanciateFromLine( ResultSet rs ) throws SQLException {
									Element e = new Element();
									e.setCode( StringUtils.trimToEmpty( rs.getString( "code_type_territoire" ) ) );
									e.setLibelle( StringUtils.trimToEmpty( rs.getString( "libelle_type_territoire" ) ) );
									return e;
								}
						}
				);
				
				if( res != null ) {
					types = new ListTypeTerritories( res, codeLangue );
					CacheManager.getInstance().setObjectInCache( types );
				}
			} catch( SQLException e ) {
				LogCommun.major( contexte.getCodeUtilisateur(), "TerritoryDAO", "getTypeTerritories", e.getMessage(), e );
				throw new TechnicalException(e);
			}
		}
		return types.getElements();
	}
	
	/***
	 * Methode de recuperation des noms de comptes appartenant a un territoire
	 * @param idTerritory
	 * @param contexte
	 * @return List<Element>
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<Element> getAccounts( final String idTerritory, final Contexte contexte ) throws TechnicalException {
		
		List<Element> accounts = null;
		
		try {
			SQLParamDescriptor param = new SQLParamDescriptor( idTerritory, false, Types.CHAR );
			
			accounts = (List<Element>) SQLCallExecuter.getInstance()
					.executeSelectProc( PROC_GET_ACCOUNT_TERRITORY, 
					new SQLParamDescriptorSet( param ), "TerritoryDAO", "getComptes", contexte.getContexteAppelDAO(), 
					new SQLResultSetReader() {
						public Object instanciateFromLine( ResultSet rs ) throws SQLException {
							Element e = new Element();
							e.setCode( rs.getString( "code_societe" ) );
							e.setLibelle( rs.getString( "usual_name" ) );
							return e;
						}
					}
			);
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "TerritoryDAO", "getComptes", e.getMessage(), e );
			throw new TechnicalException(e);
		}
		return accounts;
	}
	
	/***
	 * Methode de recuperation de l'utilisateur lie a ce territoire
	 * @param idTerritory
	 * @param contexte
	 * @return UserLight
	 * @throws TechnicalException
	 */
	public UserLight getUser( final String idTerritory, final Contexte contexte ) throws TechnicalException {
		
		UserLight user = null;
		
		try {
			SQLParamDescriptor param = new SQLParamDescriptor( idTerritory, false, Types.CHAR );
			
			user = (UserLight) SQLCallExecuter.getInstance().executeSelectSingleObjetProc( PROC_GET_USER_TERRITORY, 
					new SQLParamDescriptorSet( param ), "TerritoryDAO", "getUser", contexte.getContexteAppelDAO(), 
					new SQLResultSetReader() {
						public Object instanciateFromLine( ResultSet rs ) throws SQLException {
							UserLight u = new UserLight();
							u.setLogin(  StringUtils.trimToEmpty( rs.getString( "login" ) ) );
							u.setFirstName( StringUtils.trimToEmpty( rs.getString( "nom" ) ) );
							u.setLastName( StringUtils.trimToEmpty( rs.getString( "prenom" ) ) );
							u.setCivility( StringUtils.trimToEmpty( rs.getString( "civilite" ) ) );
							return u;
						}
					}
			);
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "TerritoryDAO", "getUser", e.getMessage(), e );
			throw new TechnicalException(e);
		}
		return user;
	}
	
	/***
	 * Methode de recherche d'une liste de territoires
	 * @param t
	 * @param contexte
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<Territory> searchTerritories( final Territory t, final Contexte contexte, final boolean withDeleted ) 
			throws TechnicalException {
		
		List<Territory> territories = null;
		
		try {
			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
					new SQLParamDescriptor( StringUtils.trimToNull( t.getCode() ), false, Types.CHAR ),
					new SQLParamDescriptor( StringUtils.trimToNull( t.getLibelle() ), false, Types.VARCHAR ),
					new SQLParamDescriptor( StringUtils.trimToNull( t.getManagerFirstName() ), false, Types.VARCHAR ),
					new SQLParamDescriptor( t.getManagerID(), false, Types.INTEGER ),
					new SQLParamDescriptor( new Boolean( withDeleted ), false,Types.BOOLEAN )
			};
			
			territories = (List<Territory>) SQLCallExecuter.getInstance()
					.executeSelectProcSansLimite( PROC_SEARCH_TERRITORIES, 
					new SQLParamDescriptorSet( params ), "TerritoryDAO", "searchTerritories", contexte.getContexteAppelDAO(), 
					new SQLResultSetReader() {
						public Object instanciateFromLine( ResultSet rs ) throws SQLException {
							Territory t = new Territory();
							t.setCode( StringUtils.trimToEmpty( rs.getString( "id_territoire" ) ) );
							t.setLibelle( StringUtils.trimToEmpty( rs.getString( "libelle" ) ) );
							t.setManagerID( rs.getInt( "id_intervenant" ) );
	                        t.setManagerCivility( StringUtils.trim( rs.getString( "civilite_intervenant" ) ) );
							t.setManagerFirstName( StringUtils.trimToEmpty( rs.getString( "nom_intervenant" ) ) );           	
	                        t.setManagerLastName( StringUtils.trimToEmpty( rs.getString( "prenom_intervenant" ) ) );         	
	                        t.setTypeCode( rs.getInt( "code_type_territoire" ) );
	                        t.setTypeLabel( StringUtils.trimToEmpty( rs.getString( "libelle_type_territoire" ) ) );
	                        t.setDeleted( rs.getBoolean( "supprimer" ) );
	                        t.setByDefault( rs.getBoolean( "territoire_par_defaut" ) );
							return t;
						}
			});
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "TerritoryDAO", "searchTerritories", e.getMessage(), e );
			throw new TechnicalException(e);
		}
		return territories;
	}

	/***
	 * Methode de recuperation des territoires
	 * @param withDeleted
	 * @param contexte
	 * @return List<Territory>
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	private List<Territory> initTerritories( final boolean withDeleted, final Contexte contexte ) 
			throws TechnicalException {
		List<Territory> territories = null;
		
		try {
			SQLParamDescriptor param = new SQLParamDescriptor( new Boolean( withDeleted), false, Types.BIT );
			
			territories = (List<Territory>) SQLCallExecuter.getInstance()
					.executeSelectProcSansLimite( PROC_GET_TERRITORIES, 
					new SQLParamDescriptorSet( param ), "TerritoryDAO", "initTerritories", contexte.getContexteAppelDAO(), 
					new SQLResultSetReader() {
						public Object instanciateFromLine( ResultSet rs ) throws SQLException {
							Territory t = new Territory();
							t.setCode( StringUtils.trimToEmpty( rs.getString( "id_territoire" ) ) );
							t.setLibelle( StringUtils.trimToEmpty( rs.getString( "libelle" ) ) );
							t.setManagerID( rs.getInt( "id_intervenant" ) );
	                        t.setManagerCivility( StringUtils.trimToEmpty( rs.getString( "civilite_intervenant" ) ) );
							t.setManagerFirstName( StringUtils.trimToEmpty( rs.getString( "nom_intervenant" ) ) );           	
	                        t.setManagerLastName( StringUtils.trimToEmpty( rs.getString( "prenom_intervenant" ) ) );         	
	                        t.setTypeCode( rs.getInt( "code_type_territoire" ) );
	                        t.setTypeLabel( StringUtils.trimToEmpty( rs.getString( "libelle_type_territoire" ) ) );
	                        t.setDeleted( rs.getBoolean( "supprimer" ) );
	                        t.setByDefault( rs.getBoolean( "territoire_par_defaut" ) );
							return t;
						}
			});
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "TerritoryDAO", "initTerritories", e.getMessage(), e );
			throw new TechnicalException(e);
		}
		return territories;
	}
}
