package com.accor.asa.commun.user.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Address;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Coordinates;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.user.metier.ListSaleOffices;
import com.accor.asa.commun.user.metier.SaleOffice;

public class SaleOfficeDAO extends DAO {

	private final static String PROC_GET_SALE_OFFICES 		= "proc_selBurVente";
	private final static String PROC_SEARCH_SALE_OFFICES	= "admin_search_bureau_vente";
	private final static String PROC_INSERT_SALE_OFFICE		= "admin_insert_bureau_vente";
	private final static String PROC_UPDATE_SALE_OFFICE		= "admin_update_bureau_vente";
	private final static String PROC_DELETE_SALE_OFFICE		= "admin_delete_bureau_vente";

	private static SaleOfficeDAO _instance = new SaleOfficeDAO();
	
	private SaleOfficeDAO() {}
	
	public static SaleOfficeDAO getInstance() {
		return _instance;
	}
	
	/***
	 * Methode de récupération de tous les bureaux de vente ( supprimer compris )
	 * @param contexte
	 * @return List<SaleOffice>
	 * @throws TechnicalException
	 */
	public List<SaleOffice> getSaleOfficesAdmin( final Contexte contexte ) throws TechnicalException {
		return initSaleOffices( true, contexte );
	}
	
	/***
	 * Methode de récupération des bureaux de vente
	 * @param contexte
	 * @return List<SaleOffice>
	 * @throws TechnicalException
	 */
	public List<SaleOffice> getSaleOffices( final Contexte contexte ) throws TechnicalException {
		
		String codeLangue = contexte.getCodeLangue();
		
		ListSaleOffices offices = (ListSaleOffices) 
			CacheManager.getInstance().getObjectInCache( ListSaleOffices.class, codeLangue );
		
		if( offices == null ) {
			offices = new ListSaleOffices( initSaleOffices( false, contexte ), codeLangue );
			CacheManager.getInstance().setObjectInCache( offices );
		}
		return offices.getElements();
	}
	
	/***
	 * Methode de mise a jour des infos d'un bureau de vente
	 * @param so
	 * @param contexte
	 * @return int
	 * @throws TechnicalException
	 */
	public int updateSaleOffice( final SaleOffice so, final Contexte contexte ) 
			throws TechnicalException {
		
		int codeRetour = SaleOffice.NO_CONSTRAINT_ERROR;
		try {
			if( so != null ) {
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( so.getLibelle(), false, Types.VARCHAR  ),
						new SQLParamDescriptor( so.getAddress1(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getAddress2(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getAddress3(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getZipCode(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getCity(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getCountryCode(), false, Types.CHAR ),
						new SQLParamDescriptor( so.getPhone(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getFax(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getMail(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getCode(), false, Types.NUMERIC )
						};
		
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_UPDATE_SALE_OFFICE, new SQLParamDescriptorSet( params ), 
						"SaleOfficeDAO", "updateSaleOffice", contexte.getContexteAppelDAO() );
			}
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleOfficeDAO", "updateSaleOffice", 
					"erreur lors de la mise a jour des infos bureau de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleOfficeDAO", "updateSaleOffice", 
					"erreur lors de la mise a jour des infos bureau de vente", e );
			throw new TechnicalException( e );
		}
		return codeRetour;
	}
	
	/***
	 * Methode d'insertion d'un nouveau bureau de vente
	 * @param 	so
	 * @param 	contexte
	 * @return	int
	 * @throws TechnicalException
	 */
	public int insertSaleOffice( final SaleOffice so, final Contexte contexte ) 
			throws TechnicalException {
		
		int codeRetour = SaleOffice.NO_CONSTRAINT_ERROR;
		
		try {
			if( so != null ) {
				
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( so.getLibelle(), false, Types.VARCHAR  ),
						new SQLParamDescriptor( so.getAddress1(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getAddress2(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getAddress3(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getZipCode(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getCity(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getCountryCode(), false, Types.CHAR ),
						new SQLParamDescriptor( so.getPhone(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getFax(), false, Types.VARCHAR ),
						new SQLParamDescriptor( so.getMail(), false, Types.VARCHAR )
						};
		
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_INSERT_SALE_OFFICE, new SQLParamDescriptorSet( params ), 
						"SaleOfficeDAO", "insertSaleOffice", contexte.getContexteAppelDAO() );
			}
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleOfficeDAO", "insertSaleOffice", 
					"erreur lors de l insertion du bureau de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleOfficeDAO", "insertSaleOffice", 
					"erreur lors de l insertion du bureau de vente", e );
			throw new TechnicalException( e );
		}
		return codeRetour;
	}

	/***
	 * Methode de suppression d'un bureau de vente
	 * @param code
	 * @param contexte
	 * @return	int
	 * @throws 	TechnicalException
	 */
	public int deleteSaleOffice( final Integer code, final Contexte contexte ) 
			throws TechnicalException {
		
		int codeRetour = SaleOffice.NO_CONSTRAINT_ERROR;
		try {
			if( code != null ) {
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( code, false, Types.NUMERIC  )
				};
		
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_DELETE_SALE_OFFICE, new SQLParamDescriptorSet( params ), 
						"SaleOfficeDAO", "deleteSaleOffice", contexte.getContexteAppelDAO() );
			}
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleOfficeDAO", "deleteSaleOffice", 
					"erreur lors de la suppression du bureau de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleOfficeDAO", "deleteSaleOffice", 
					"erreur lors de la suppression du bureau de vente", e );
			throw new TechnicalException( e );
		}
		return codeRetour;
	}

	/***
	 * Methode de recherche de bureaux de vente
	 * @param so
	 * @param contexte
	 * @param withDeleted
	 * @return ListSaleOffices
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<SaleOffice> searchSaleOffices( final SaleOffice so, final Contexte contexte, 
			final boolean withDeleted ) throws TechnicalException {
		
		List<SaleOffice> offices = null; 
		
		try {
			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
				new SQLParamDescriptor( StringUtils.trimToNull( so.getLibelle() ), false, Types.VARCHAR ),
				new SQLParamDescriptor( StringUtils.trimToNull( so.getAddress1() ), false, Types.VARCHAR ),
				new SQLParamDescriptor( StringUtils.trimToNull( so.getZipCode() ), false, Types.VARCHAR ),
				new SQLParamDescriptor( StringUtils.trimToNull( so.getCity() ), false, Types.VARCHAR ),
				new SQLParamDescriptor( StringUtils.trimToNull( so.getCountryCode() ), false, Types.VARCHAR ),
				new SQLParamDescriptor( contexte.getCodeLangue(), false, Types.CHAR ),
				new SQLParamDescriptor( new Boolean( withDeleted ), false, Types.BIT )
			};
			
			offices = (List<SaleOffice>) SQLCallExecuter.getInstance()
					.executeSelectProcSansLimite(
					PROC_SEARCH_SALE_OFFICES, new SQLParamDescriptorSet(params), 
					"SaleOfficeDAO", "searchSaleOffices", contexte.getContexteAppelDAO(), 
					new SQLResultSetReader() {
						public Object instanciateFromLine( ResultSet rs ) throws SQLException {
							SaleOffice so = new SaleOffice();
							so.setCode( StringUtils.trimToEmpty( rs.getString( "code" ) ) );
							so.setLibelle( StringUtils.trimToEmpty( rs.getString( "nom" ) ) );
							
							Address addr = so.getAddress();
							addr.setAddress1( StringUtils.trimToEmpty( rs.getString( "adresse1" ) ) );
							addr.setAddress2( StringUtils.trimToEmpty( rs.getString( "adresse2" ) ) );
							addr.setAddress3( StringUtils.trimToEmpty( rs.getString( "adresse3" ) ) );
							addr.setZipCode( StringUtils.trimToEmpty( rs.getString( "code_postal" ) ) );
							addr.setCity( StringUtils.trimToEmpty( rs.getString( "ville" ) ) );
							addr.setCountryId( StringUtils.trimToEmpty( rs.getString( "code_pays" ) ) );
							addr.setCountryName( StringUtils.trimToEmpty( rs.getString( "lib_pays" ) ) );
							so.setAddress( addr );
							
							Coordinates coord = so.getCoordinates();
							coord.setPhone( StringUtils.trimToEmpty( rs.getString( "telephone" ) ) );
							coord.setFax( StringUtils.trimToEmpty( rs.getString( "fax" ) ) );
							coord.setMail( StringUtils.trimToEmpty( rs.getString( "email" ) ) );
							so.setCoordinates( coord );
							
							so.setDeleted( rs.getBoolean( "supprimer" ) );
							return so;
						}
					}
			);
			
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleOfficeDAO", "searchSaleOffices", 
					"erreur lors de la recherche de bureaux de vente", e );
			throw new TechnicalException( e );
		}
		return offices;
	}
	
	/***
	 * Recupere les bureau de vente
	 * @param withDeleted
	 * @param contexte
	 * @return List<SaleOffice>
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	private List<SaleOffice> initSaleOffices( final boolean withDeleted, final Contexte contexte )
			throws TechnicalException {
		
		List<SaleOffice> offices = null;
		
		try {
			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
					new SQLParamDescriptor( contexte.getCodeLangue(), false, Types.CHAR ),
					new SQLParamDescriptor( new Boolean( withDeleted ), false, Types.BIT )
			};
			
			offices = (List<SaleOffice>) SQLCallExecuter.getInstance()
					.executeSelectProc( PROC_GET_SALE_OFFICES, 
					new SQLParamDescriptorSet( params ), "SaleOfficeDAO", "initSaleOffices", 
					contexte.getContexteAppelDAO(), new SQLResultSetReader() {
				public Object instanciateFromLine( ResultSet rs ) throws TechnicalException, SQLException {
					SaleOffice so = new SaleOffice();
					so.setCode( StringUtils.trimToEmpty( rs.getString( "code_bureau_vente" ) ) );
					so.setLibelle( StringUtils.trimToEmpty( rs.getString( "nom_bureau_vente" ) ) );
					
					Address addr = so.getAddress();
					addr.setAddress1( StringUtils.trimToEmpty( rs.getString( "adresse1" ) ) );
					addr.setAddress2( StringUtils.trimToEmpty( rs.getString( "adresse2" ) ) );
					addr.setAddress3( StringUtils.trimToEmpty( rs.getString( "adresse3" ) ) );
					addr.setZipCode( StringUtils.trimToEmpty( rs.getString( "code_postal" ) ) );
					addr.setCity( StringUtils.trimToEmpty( rs.getString( "ville" ) ) );
					addr.setCountryId( StringUtils.trimToEmpty( rs.getString( "code_pays" ) ) );
					addr.setCountryName( StringUtils.trimToEmpty( rs.getString( "lib_pays" ) ) );
					so.setAddress( addr );
					
					Coordinates coord = so.getCoordinates();
					coord.setPhone( StringUtils.trimToEmpty( rs.getString( "telephone" ) ) );
					coord.setFax( StringUtils.trimToEmpty( rs.getString( "telecopie" ) ) );
					coord.setMail( StringUtils.trimToEmpty( rs.getString( "email" ) ) );
					so.setCoordinates( coord  );
					
					so.setDeleted( rs.getBoolean( "supprimer" ) );
					return so;
				}
			});
			
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleOfficeDAO", "initSaleOffices", 
					"erreur lors de la recuperation des bureaux de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleOfficeDAO", "initSaleOffices", 
					"erreur lors de la recuperation des bureaux de vente", e );
			throw new TechnicalException( e );
		}
		return offices;
	}

    /**
     * Renvoie les bureaux de vente visibles pour un utilisateur
     * @param login
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<SaleOffice> getSalesOfficeForUser( final String login, Contexte contexte )
            throws TechnicalException {
        
    	List<SaleOffice> offices;
        
    	try {
        	SQLParamDescriptor param = new SQLParamDescriptor( login, false, Types.VARCHAR );
    		
    		offices = (List<SaleOffice>) SQLCallExecuter.getInstance().executeSelectProc(
                    "proc_Selburventelight", new SQLParamDescriptorSet( param ),
                    "CommunUtilsDAO", "getSalesOfficeForUser", contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            SaleOffice so = new SaleOffice();
                            so.setCode( rs.getString("codebureauvente") );
                            so.setLibelle( rs.getString("nombureauvente") );
                            return so;
                        }
                    }
            );
        } catch (SQLException e) {
            LogCommun.major( contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getSalesOfficeForUser",
                    "Erreur lors de la recuperation de la liste des bureaux de ventes", e);
            throw new TechnicalException(e);
        }
        return offices;
    }
}
