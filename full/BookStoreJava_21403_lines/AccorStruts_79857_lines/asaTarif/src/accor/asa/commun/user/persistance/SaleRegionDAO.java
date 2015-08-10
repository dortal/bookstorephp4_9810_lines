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
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.user.metier.ListSaleRegions;
import com.accor.asa.commun.user.metier.SaleRegion;


public class SaleRegionDAO extends DAO {

	private final static String PROC_GET_SALE_REGIONS 		= "proc_sel_region_vente";
	private final static String PROC_SEARCH_SALE_REGIONS	= "admin_search_region_vente";
	private final static String PROC_INSERT_SALE_REGION		= "admin_insert_region_vente";
	private final static String PROC_UPDATE_SALE_REGION		= "admin_update_region_vente";
	private final static String PROC_DELETE_SALE_REGION		= "admin_delete_region_vente";

	private static SaleRegionDAO _instance = new SaleRegionDAO();
	
	private SaleRegionDAO() {}
	
	public static SaleRegionDAO getInstance() {
		return _instance;
	}
	
	/***
	 * Methode de récupération de toutes les régions de vente ( supprimer comprises )
	 * @param contexte
	 * @return List<SaleRegion>
	 * @throws TechnicalException
	 */
	public List<SaleRegion> getSaleRegionsAdmin( final Contexte contexte ) throws TechnicalException {
		return initSaleRegions( true, contexte );
	}
	
	/***
	 * Methode de récupération des régions de vente
	 * @param contexte
	 * @return List<SaleRegion>
	 * @throws TechnicalException
	 */
	public List<SaleRegion> getSaleRegions( final Contexte contexte ) throws TechnicalException {
		
		ListSaleRegions regions = (ListSaleRegions) 
				CacheManager.getInstance().getObjectInCache( ListSaleRegions.class  );
		
		if( regions == null ) {
			regions = new ListSaleRegions( initSaleRegions( false, contexte ) );
			CacheManager.getInstance().setObjectInCache( regions );
		}
		return regions.getElements();
	}
	
	/***
	 * Methode de mise a jour des infos d'une region de vente
	 * @param 	sr
	 * @param 	contexte
	 * @return 
	 * @throws TechnicalException
	 */
	public int updateSaleRegion( final SaleRegion sr, final Contexte contexte ) 
			throws TechnicalException {
		
		int codeRetour = SaleRegion.NO_CONSTRAINT_ERROR;
		try {
			if( sr != null ) {
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( sr.getCode(), false, Types.CHAR  ),
						new SQLParamDescriptor( sr.getLibelle(), false, Types.VARCHAR  )
				};
		
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_UPDATE_SALE_REGION, new SQLParamDescriptorSet( params ), 
						"SaleRegionDAO", "updateSaleRegion", contexte.getContexteAppelDAO() );
			}
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleRegionDAO", "updateSaleRegion", 
					"erreur lors de la mise a jour des infos region de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleRegionDAO", "updateSaleRegion", 
					"erreur lors de la mise a jour des infos region de vente", e );
			throw new TechnicalException( e );
		}
		return codeRetour;
	}
	
	/***
	 * Methode d'insertion d'une nouvelle region de vente
	 * @param 	sr
	 * @param 	contexte
	 * @return 
	 * @throws TechnicalException
	 */
	public int insertSaleRegion( final SaleRegion sr, final Contexte contexte ) 
			throws TechnicalException {
		
		int codeRetour = SaleRegion.NO_CONSTRAINT_ERROR;
		try {
			if( sr != null ) {
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( sr.getCode(), false, Types.CHAR  ),
						new SQLParamDescriptor( sr.getLibelle(), false, Types.VARCHAR  )
				};
		
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_INSERT_SALE_REGION, new SQLParamDescriptorSet( params ), 
						"SaleRegionDAO", "insertSaleRegion", contexte.getContexteAppelDAO() );
			}
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleRegionDAO", "insertSaleRegion", 
					"erreur lors de l insertion d une region de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleRegionDAO", "insertSaleRegion", 
					"erreur lors de l insertion d une region de vente", e );
			throw new TechnicalException( e );
		}
		return codeRetour;
	}

	/***
	 * Methode de suppression logique d'une région de vente
	 * @param code
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	public int deleteSaleRegion( final String code, final Contexte contexte ) 
			throws TechnicalException {
		
		int codeRetour = SaleRegion.NO_CONSTRAINT_ERROR; 
		try {
			if( StringUtils.isNotBlank( code ) ) {
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( code, false, Types.CHAR )
				};
		
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_DELETE_SALE_REGION, new SQLParamDescriptorSet( params ), 
						"SaleRegionDAO", "deleteSaleRegion", contexte.getContexteAppelDAO() );
			}
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleRegionDAO", "deleteSaleRegion", 
					"erreur lors de la suppression d'une region de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleRegionDAO", "deleteSaleRegion", 
					"erreur lors de la suppression d'une region de vente", e );
			throw new TechnicalException( e );
		}
		return codeRetour;
	}

	/***
	 * Methode de recherche de regions de vente
	 * @param sr
	 * @param contexte
	 * @param withDeleted
	 * @return List<SaleRegion>
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<SaleRegion> searchSaleRegions( final SaleRegion sr, final Contexte contexte, 
			final boolean withDeleted ) throws TechnicalException {
		
		List<SaleRegion> regions = null;
		
		try {
			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
				new SQLParamDescriptor( StringUtils.trimToNull( sr.getCode() ), false, Types.CHAR ),
				new SQLParamDescriptor( StringUtils.trimToNull( sr.getLibelle() ), false, Types.VARCHAR ),
				new SQLParamDescriptor( new Boolean( withDeleted ), false, Types.BIT )
			};
			
			regions = (List<SaleRegion>) SQLCallExecuter.getInstance().executeSelectProcSansLimite( 
					PROC_SEARCH_SALE_REGIONS, new SQLParamDescriptorSet( params ), 
					"SaleRegionDAO", "searchSaleRegions", contexte.getContexteAppelDAO(), 
					new SQLResultSetReader() {
						public Object instanciateFromLine( ResultSet rs ) throws TechnicalException, SQLException {
							SaleRegion sr = new SaleRegion();
							sr.setCode( StringUtils.trimToEmpty( rs.getString( "coderegion" ) ) );
							sr.setLibelle( StringUtils.trimToEmpty( rs.getString( "nomregion" ) ) );
							sr.setDeleted( rs.getBoolean( "supprimer" ) );
							return sr;
						}
					}
			);
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleRegionDAO", "searchSaleRegions", 
					"erreur lors de la recherche de regions de vente", e );
			throw new TechnicalException( e );
		}
		
		return regions;
	}
	
	/***
	 * Recuperation des regions de vente
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	private List<SaleRegion> initSaleRegions( final boolean withDeleted, final Contexte contexte ) throws TechnicalException {
		List<SaleRegion> regions = null;
		
		try {
			SQLParamDescriptor param = new SQLParamDescriptor( new Boolean( withDeleted ), false, Types.BIT );
			
			regions = (List<SaleRegion>) SQLCallExecuter.getInstance().executeSelectProc( 
					PROC_GET_SALE_REGIONS, 
					new SQLParamDescriptorSet( param ), "SaleRegionDAO", "initSaleRegions", 
					contexte.getContexteAppelDAO(), new SQLResultSetReader() {
				public Object instanciateFromLine( ResultSet rs ) throws TechnicalException, SQLException {
					SaleRegion sr = new SaleRegion();
					sr.setCode( StringUtils.trimToEmpty( rs.getString( "coderegion" ) ) );
					sr.setLibelle( StringUtils.trimToEmpty( rs.getString( "nomregion" ) ) );
					sr.setDeleted( rs.getBoolean( "supprimer" ) );
					return sr;
				}
			});
			
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleRegionDAO", "initRegions", 
					"erreur lors de la recuperation des regions de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleRegionDAO", "initRegions", 
					"erreur lors de la recuperation des regions de vente", e );
			throw new TechnicalException( e );
		}
		return regions;
	}
}
