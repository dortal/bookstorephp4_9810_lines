package com.accor.asa.commun.reference.persistance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.TypeOffreRefBean;
import com.accor.asa.vente.dossier.metier.MapTypeOffre;
import com.accor.asa.vente.dossier.metier.MapTypeOffreParGroupeOffre;

public class TypeOffreRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = "vente_select_type_offre";
	private static final String ADMIN_SELECT_PROC_NAME = "vente_DRef_sel_type_offre";
	private static final String INSERT_PROC_NAME = "vente_DRef_ins_type_offre";
	private static final String UPDATE_PROC_NAME = "vente_DRef_upd_type_offre";
	private static final String DELETE_PROC_NAME = "vente_DRef_del_type_offre";

	protected String getProcName (int type) {
		switch (type) {
			case SELECT :
				return SELECT_PROC_NAME;
			case ADMIN_SELECT :
				return ADMIN_SELECT_PROC_NAME;
			case INSERT :
				return INSERT_PROC_NAME;
			case UPDATE :
				return UPDATE_PROC_NAME;
			case DELETE :
				return DELETE_PROC_NAME;
			default :
				return null;
		}
	}

	protected SQLParamDescriptorSet getProcParameters (int type, RefBean bean, String codeLangue) {
		TypeOffreRefBean temp = (TypeOffreRefBean) bean;
		switch (type) {
			case SELECT :
				SQLParamDescriptor [] selectParams = {
					new SQLParamDescriptor(codeLangue, Types.CHAR)
				};
				return new SQLParamDescriptorSet(selectParams);
			case ADMIN_SELECT :
				SQLParamDescriptor [] adminSelectParams = {
					new SQLParamDescriptor(Boolean.TRUE)
				};
				return new SQLParamDescriptorSet(adminSelectParams);
			case INSERT :
				SQLParamDescriptor [] insertParams = {
					new SQLParamDescriptor(temp.getId(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR),
					new SQLParamDescriptor(getCharValue(temp.isExportTars())),
					new SQLParamDescriptor(temp.getIdGroupeOffre()),
					new SQLParamDescriptor(getCharValue(temp.isSuiviConso())),
					new SQLParamDescriptor(temp.getMaxHotels(), Types.INTEGER),
					new SQLParamDescriptor(temp.getDefaultTarsKey(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getDefaultSegment(), Types.VARCHAR),
					new SQLParamDescriptor(Boolean.valueOf(temp.isRfp()), Types.BOOLEAN),
					new SQLParamDescriptor(Boolean.valueOf(temp.isPrecoHotel()), Types.BOOLEAN),
                    new SQLParamDescriptor(Boolean.valueOf(temp.isContratDistrib()), Types.BOOLEAN),
                    new SQLParamDescriptor(Boolean.valueOf(temp.isReporting()), Types.BOOLEAN),
                    new SQLParamDescriptor(new Integer(temp.getCodeFamSegment()), Types.INTEGER)
                };
				return new SQLParamDescriptorSet(insertParams);
			case UPDATE :
				return null;
			case DELETE :
				SQLParamDescriptor [] deleteParams = {
					new SQLParamDescriptor(temp.getId(), Types.VARCHAR),
				};
				return new SQLParamDescriptorSet(deleteParams);
			default :
				return null;
		}
	}

	protected SQLResultSetReader getProcReader (int type) {
		switch (type) {
			case SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						TypeOffreRefBean typeOffre = new TypeOffreRefBean();
						typeOffre.setId( rs.getString( "id_type_offre" ) );
						typeOffre.setLibelle( rs.getString( "libelle" ) );
						typeOffre.setCodeLangue( rs.getString( "codelangue" ) );
                        typeOffre.setActif( rs.getString( "supprimer" ).equals("0") );
						typeOffre.setExportTars( rs.getString( "flag_export_asatars" ).equals("1") );
						typeOffre.setSuiviConso( rs.getString( "flag_suivi_conso" ).equals("1") );
						typeOffre.setRfp( rs.getBoolean( "is_rfp" ) );
						typeOffre.setPrecoHotel( rs.getBoolean( "is_precos_hotel" ) );
                        typeOffre.setContratDistrib( rs.getBoolean( "is_contrat_distrib" ) );
                        typeOffre.setReporting( rs.getBoolean( "is_reporting" ) );
                        int nbHotels = rs.getInt( "nbmax_hotel" );
                        if( ! rs.wasNull() )
                        	typeOffre.setMaxHotels( new Integer( nbHotels ) );
						typeOffre.setIdGroupeOffre( rs.getString( "groupe_offre_id" ) );
						typeOffre.setDefaultTarsKey( rs.getString( "default_tarskey" ) );
						typeOffre.setDefaultSegment( rs.getString( "default_segment" ) );
                        typeOffre.setCodeFamSegment( rs.getString( "codeFamSegment" ) );
                        long numPackage = rs.getLong( "numero_package" );
                        if( ! rs.wasNull() )
                        	typeOffre.setNumeroPackage( new Long( numPackage ) );
						typeOffre.setEngagementVolume( rs.getString( "flag_engagement_volume" ) != null && rs.getString( "flag_engagement_volume" ).equals("1") );
						return typeOffre;
					}
				};
			case ADMIN_SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						TypeOffreRefBean typeOffre = new TypeOffreRefBean();
						typeOffre.setId(rs.getString("ID_TYPE_OFFRE"));
						typeOffre.setLibelle(rs.getString("LIBELLE"));
						typeOffre.setExportTars(rs.getString("FLAG_EXPORT_ASATARS").equals("1"));
						typeOffre.setSuiviConso(rs.getString("FLAG_SUIVI_CONSO").equals("1"));
						typeOffre.setRfp(rs.getBoolean("IS_RFP"));
						typeOffre.setPrecoHotel(rs.getBoolean("IS_PRECOS_HOTEL"));
                        typeOffre.setContratDistrib(rs.getBoolean("IS_CONTRAT_DISTRIB"));
                        typeOffre.setReporting(rs.getBoolean("IS_REPORTING"));
                        int nbHotels = rs.getInt( "NBMAX_HOTEL" );
                        if( ! rs.wasNull() )
                        	typeOffre.setMaxHotels( new Integer( nbHotels ) );
						typeOffre.setIdGroupeOffre(rs.getString("GROUPE_OFFRE_ID"));
						typeOffre.setDefaultTarsKey(rs.getString("DEFAULT_TARSKEY"));
						typeOffre.setDefaultSegment(rs.getString("DEFAULT_SEGMENT"));
                        typeOffre.setCodeFamSegment(rs.getString("CODEFAMSEGMENT"));
                        typeOffre.setActif(rs.getString("SUPPRIME").equals("0"));
						return typeOffre;
					}
				};
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return null;
	}

	protected CachableInterface getObjectInCache (String codeLangue) {
		return null;
	}

	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return null;
	}

	/**
	 * Redéfinitions des méthodes mères
	 */
	public int updateRef (Contexte context, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		TypeOffreRefBean temp = (TypeOffreRefBean) bean;
		Connection connection = null;
		CallableStatement statement = null;
		Integer maxHotels = new Integer(0);
		try {
			if( temp.getMaxHotels() != null )
				maxHotels = temp.getMaxHotels();

			connection = PoolCommunFactory.getInstance().getConnection();
			statement = connection.prepareCall("{ ? = call " + UPDATE_PROC_NAME + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setString(2, temp.getId());
			statement.setString(3, temp.getLibelle());
			statement.setString(4, getCharValue(temp.isExportTars()));
			statement.setString(5, temp.getIdGroupeOffre());
			statement.setString(6, getCharValue(temp.isSuiviConso()));
			statement.setInt(7, maxHotels.intValue());
			statement.setString(8, temp.getDefaultTarsKey());
			statement.setString(9, temp.getDefaultSegment());
			statement.setBoolean(10, temp.isRfp());
			statement.setBoolean(11, temp.isPrecoHotel());
			statement.setBoolean(12, temp.isContratDistrib());
            statement.setBoolean(13, temp.isReporting());
            statement.setInt(14, Integer.parseInt(temp.getCodeFamSegment()));
            statement.executeUpdate();
			int res = statement.getInt(1);

			LogCommun.traceFonctionnelle(context.getCodeUtilisateur(), UPDATE_PROC_NAME, "UPDATE", bean.toString());
			LogCommun.debug("TypeOffreRefDAO", "updateRef", "Code retour = " + res);

			if (res == DELETE_ERROR) {
				throw new ForeignKeyException();
			}
			
			return res;
		}
		catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
				throw new DuplicateKeyException(e);
			}
			else if (e.getErrorCode() == SYBASE_INTEGRITY_CONSTRAINT_ERROR_CODE) {
				throw new IncoherenceException(e);
			}
			else {
				throw new TechnicalException(e);
			}
		}
		catch (ForeignKeyException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TechnicalException(e);
		}
		finally {
			releaseConnection(context.getContexteAppelDAO(), connection, statement);
		}
	}

	public void refreshCacheRefList (Contexte context) throws TechnicalException {
		try {
			String idOriginator = CacheManager.getInstance().getIdOriginator();

			String cache1ClassName = MapTypeOffreParGroupeOffre.class.getName();
			CacheManager.getInstance().refreshInstanceCaches(cache1ClassName, idOriginator, null);
			LogCommun.traceFonctionnelle(context.getCodeUtilisateur(), cache1ClassName, "REFRESH", "");

			String cache2ClassName = MapTypeOffre.class.getName();
			CacheManager.getInstance().refreshInstanceCaches(cache2ClassName, idOriginator, null);
			LogCommun.traceFonctionnelle(context.getCodeUtilisateur(), cache2ClassName, "REFRESH", "");
		}
		catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * Méthodes privées
	 */
	private String getCharValue (boolean b) {
		if (b)
			return "1";
		return "0";
	}

}