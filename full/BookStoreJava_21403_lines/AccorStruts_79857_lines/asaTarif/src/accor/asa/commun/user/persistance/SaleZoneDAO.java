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
import com.accor.asa.commun.user.metier.ListSaleZones;
import com.accor.asa.commun.user.metier.SaleZone;

public class SaleZoneDAO extends DAO {

	private final static String PROC_GET_SALE_ZONES 	= "proc_sel_zone_vente";
	private final static String PROC_SEARCH_SALE_ZONES 	= "admin_search_zone_vente";
	private final static String PROC_INSERT_SALE_ZONE 	= "admin_insert_zone_vente";
	private final static String PROC_UPDATE_SALE_ZONE 	= "admin_update_zone_vente";
	private final static String PROC_DELETE_SALE_ZONE 	= "admin_delete_zone_vente";
	
	private static SaleZoneDAO _instance = new SaleZoneDAO();
	
	private SaleZoneDAO() {}
	
	public static SaleZoneDAO getInstance() {
		return _instance;
	}
	
	/***
	 * Methode de récupération de toutes les zones de vente ( supprimer comprises )
	 * @param contexte
	 * @return List<SaleZone>
	 * @throws TechnicalException
	 */
	public List<SaleZone> getSaleZonesAdmin( final Contexte contexte ) throws TechnicalException {
		return initSaleZones( true, contexte );
	}
	
	/***
	 * Methode de récupération des zones de vente
	 * @param contexte
	 * @return List<SaleZone>
	 * @throws TechnicalException
	 */
	public List<SaleZone> getSaleZones( final Contexte contexte ) throws TechnicalException {
		
		ListSaleZones zones = (ListSaleZones) 
				CacheManager.getInstance().getObjectInCache( ListSaleZones.class  );
		
		if( zones == null ) {
			zones = new ListSaleZones( initSaleZones( false, contexte ) );
			CacheManager.getInstance().setObjectInCache( zones );
		}
		return zones.getElements();
	}
	
	/***
	 * Methode de mise a jour des infos d'une zone de vente
	 * @param 	sz
	 * @param 	contexte
	 * @return
	 * @throws TechnicalException
	 */
	public int updateSaleZone( final SaleZone sz, final Contexte contexte ) 
			throws TechnicalException {
		
		int codeRetour = SaleZone.NO_CONSTRAINT_ERROR;
		try {
			if( sz != null ) {
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( sz.getCode(), false, Types.CHAR  ),
						new SQLParamDescriptor( sz.getLibelle(), false, Types.VARCHAR  ),
						new SQLParamDescriptor( sz.getRegionCode(), false, Types.CHAR  )
				};
		
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_UPDATE_SALE_ZONE, new SQLParamDescriptorSet( params ), 
						"SaleZoneDAO", "updateSaleZone", contexte.getContexteAppelDAO() );
			}
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleZoneDAO", "updateSaleZone", 
					"erreur lors de la mise a jour des infos zone de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleZoneDAO", "updateSaleZone", 
					"erreur lors de la mise a jour des infos zone de vente", e );
			throw new TechnicalException( e );
		}
		return codeRetour;
	}
	
	/***
	 * Methode d'insertion d'une nouvelle zone de vente
	 * @param 	sz
	 * @param 	contexte
	 * @return
	 * @throws TechnicalException
	 */
	public int insertSaleZone( final SaleZone sz, final Contexte contexte ) 
			throws TechnicalException {
		
		int codeRetour = SaleZone.NO_CONSTRAINT_ERROR;
		try {
			if( sz != null ) {
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( sz.getCode(), false, Types.CHAR  ),
						new SQLParamDescriptor( sz.getLibelle(), false, Types.VARCHAR  ),
						new SQLParamDescriptor( sz.getRegionCode(), false, Types.CHAR  )
				};
		
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_INSERT_SALE_ZONE, new SQLParamDescriptorSet( params ), 
						"SaleZoneDAO", "insertSaleZone", contexte.getContexteAppelDAO() );
			}
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleZoneDAO", "insertSaleZone", 
					"erreur lors de l insertion d une zone de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleZoneDAO", "insertSaleZone", 
					"erreur lors de l insertion d une zone de vente", e );
			throw new TechnicalException( e );
		}
		return codeRetour;
	}

	/***
	 * Methode de suppression logique d'une zone de vente
	 * @param code
	 * @param contexte
	 * @throws TechnicalException
	 */
	public int deleteSaleZone( final String code, final Contexte contexte ) 
			throws TechnicalException {

		int codeRetour = SaleZone.NO_CONSTRAINT_ERROR;
		try {
			if( StringUtils.isNotBlank( code ) ) {
				SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( null, true, Types.INTEGER ),
						new SQLParamDescriptor( code, false, Types.CHAR )
				};
		
				codeRetour = SQLCallExecuter.getInstance().executeUpdateWithReturnCode( 
						PROC_DELETE_SALE_ZONE, new SQLParamDescriptorSet( params ), 
						"SaleZoneDAO", "deleteSaleZone", contexte.getContexteAppelDAO() );
			}
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleZoneDAO", "deleteSaleZone", 
					"erreur lors de la suppression d'une zone de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleZoneDAO", "deleteSaleZone", 
					"erreur lors de la suppression d'une zone de vente", e );
			throw new TechnicalException( e );
		}
		return codeRetour;
	}

	/***
	 * Methode de recherche d'une liste de zone de vente
	 * @param sz
	 * @param contexte
	 * @param withDeleted
	 * @return List<SaleZone>
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<SaleZone> searchSaleZones( final SaleZone sz, final Contexte contexte, 
			final boolean withDeleted ) throws TechnicalException {
	
		List<SaleZone> zones = null;
		
		try {
			SQLParamDescriptor[] params = new SQLParamDescriptor[] {
					new SQLParamDescriptor( StringUtils.trimToNull( sz.getCode() ), false, Types.CHAR ),
					new SQLParamDescriptor( StringUtils.trimToNull( sz.getLibelle() ), false, Types.VARCHAR ),
					new SQLParamDescriptor( StringUtils.trimToNull( sz.getRegionCode() ), false, Types.CHAR ),
					new SQLParamDescriptor( new Boolean( withDeleted ), false, Types.BIT )
			};
			
			zones = (List<SaleZone>) SQLCallExecuter.getInstance().executeSelectProcSansLimite( 
					PROC_SEARCH_SALE_ZONES, new SQLParamDescriptorSet( params ), "SaleZoneDAO", 
					"searchSaleZones", contexte.getContexteAppelDAO(), 
					new SQLResultSetReader() {
						public Object instanciateFromLine( ResultSet rs ) throws SQLException {
							SaleZone sz = new SaleZone();
							sz.setCode( StringUtils.trimToEmpty( rs.getString( "codezone" ) ) );
							sz.setLibelle( StringUtils.trimToEmpty( rs.getString( "nomzone" ) ) );
							sz.setRegionCode( StringUtils.trimToEmpty( rs.getString( "coderegion" ) ) );
							sz.setDeleted( rs.getBoolean( "supprimer" ) );
							return sz;
						}
					}
			);
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleZoneDAO", "searchSaleZones", 
					"erreur lors de la recherche de zones de vente", e );
			throw new TechnicalException( e );
		}
		
		return zones;
	}
	
	/***
	 * Recuperation des zones de vente
	 * @param withDeleted
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	private List<SaleZone> initSaleZones( final boolean withDeleted, final Contexte contexte ) throws TechnicalException {
		
		List<SaleZone> zones  = null;
		try {
			SQLParamDescriptor param = new SQLParamDescriptor( new Boolean( withDeleted ), false, Types.BIT );
			
			zones = (List<SaleZone>) SQLCallExecuter.getInstance().executeSelectProc( 
					PROC_GET_SALE_ZONES , 
					new SQLParamDescriptorSet( param ), "SaleZoneDAO", "initSaleZones", 
					contexte.getContexteAppelDAO(), new SQLResultSetReader() {
				public Object instanciateFromLine( ResultSet rs ) throws TechnicalException, SQLException {
					SaleZone sz = new SaleZone();
					sz.setCode( StringUtils.trimToEmpty( rs.getString( "codezonevente" ) ) );
					sz.setLibelle( StringUtils.trimToEmpty( rs.getString( "nomzonevente" ) ) );
					sz.setRegionCode( StringUtils.trimToEmpty( rs.getString( "coderegionvente" ) ) );
					sz.setDeleted( rs.getBoolean( "supprimer" ) );
					return sz;
				}
			});
		} catch( TechnicalException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleZoneDAO", "initSaleZones", 
					"erreur lors de la recuperation des zones de vente", e );
			throw new TechnicalException( e );
		} catch( SQLException e ) {
			LogCommun.major( contexte.getCodeUtilisateur(), "SaleZoneDAO", "initSaleZones", 
					"erreur lors de la recuperation des zones de vente", e );
			throw new TechnicalException( e );
		}
		return zones;
	}
}
