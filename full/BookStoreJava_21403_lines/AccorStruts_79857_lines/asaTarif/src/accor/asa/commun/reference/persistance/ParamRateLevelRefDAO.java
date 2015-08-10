package com.accor.asa.commun.reference.persistance;

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
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.ParamRateLevelCacheList;
import com.accor.asa.commun.reference.metier.ParamRateLevelMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamRateLevelRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class ParamRateLevelRefDAO extends RefDAO{
	
	private static final String SELECT_PROC_NAME = "refparam_selAllRateLevels";
	private static final String ADMIN_SELECT_PROC_NAME = "refparam_selAdmRateLevel";
	private static final String INSERT_PROC_NAME = "refparam_addRateLevels";
	private static final String UPDATE_PROC_NAME = null;
	private static final String DELETE_PROC_NAME = "refparam_deleteRateLevels";
	private static final String DUPLICATE_ASA_PROC_NAME="refparam_duplicateRateLevels";
	private static final String DUPLICATE_PV_PROC_NAME="refparam_duplicateRL_PV";
	private static final String EXPORT_PROC_NAME="refparam_selExpRateLevel";
	
	public static final int DUPLICATE=100;
	public static final int DUPLICATE_PV=101;
	public static final int LOAD_FOR_EXPORT = 102;
	
	protected String getProcName(int type) {
		switch (type) {
		case SELECT:
			return SELECT_PROC_NAME;
		case ADMIN_SELECT:
			return ADMIN_SELECT_PROC_NAME;
		case INSERT:
			return INSERT_PROC_NAME;
		case UPDATE:
			return UPDATE_PROC_NAME;
		case DELETE:
			return DELETE_PROC_NAME;
		case DUPLICATE:
			return DUPLICATE_ASA_PROC_NAME;
		case DUPLICATE_PV:
			return DUPLICATE_PV_PROC_NAME;
		case LOAD_FOR_EXPORT:
			return EXPORT_PROC_NAME;	
		
		default:
			return null;
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ParamRateLevelCacheList((List<ParamRateLevelRefBean>) data);
	}


	protected String getCacheClassName() {
		return ParamRateLevelCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface)CacheManager.getInstance().getObjectInCache(ParamRateLevelCacheList.class);
	}

	

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		
		switch (type) {
		case SELECT:
			SQLParamDescriptor[] selectParams = {
			};
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
			ParamRateLevelRefBean temp = (ParamRateLevelRefBean)bean;
			SQLParamDescriptor[] selAdminParams = {
					new SQLParamDescriptor( new Integer(temp.getIdFamilleTarif()),false, Types.INTEGER),
					new SQLParamDescriptor( new Integer(temp.getIdPeriodeValidite()),false, Types.INTEGER)
			};
			return new SQLParamDescriptorSet(selAdminParams);
		case DELETE:
			ParamRateLevelMappedRefBean mapped = (ParamRateLevelMappedRefBean)bean;
			SQLParamDescriptor[] delAdminParams = {
					new SQLParamDescriptor(mapped.getCodeAsaCategory(),false,Types.VARCHAR),
					new SQLParamDescriptor( new Integer(mapped.getIdFamilleTarif()),false, Types.INTEGER),
					new SQLParamDescriptor( new Integer(mapped.getIdPeriodeValidite()),false, Types.INTEGER)
			};
			return new SQLParamDescriptorSet(delAdminParams);
		case DUPLICATE:
			mapped = (ParamRateLevelMappedRefBean)bean;
			SQLParamDescriptor[] duplicateParams = {
					new SQLParamDescriptor(mapped.getCodeAsaCategory(),false,Types.VARCHAR),
					new SQLParamDescriptor( new Integer(mapped.getIdFamilleTarif()),false, Types.INTEGER),
					new SQLParamDescriptor( new Integer(mapped.getIdPeriodeValidite()),false, Types.INTEGER),
					new SQLParamDescriptor( mapped.getOldId(),false, Types.VARCHAR)
			};
			return new SQLParamDescriptorSet(duplicateParams);
		case DUPLICATE_PV:
			mapped = (ParamRateLevelMappedRefBean)bean;
			SQLParamDescriptor[] duplicatePVParams = {
					new SQLParamDescriptor( new Integer(mapped.getIdFamilleTarif()),false, Types.INTEGER),
					new SQLParamDescriptor( new Integer(mapped.getIdPeriodeValidite()),false, Types.INTEGER),
					new SQLParamDescriptor( new Integer(mapped.getOldId()),false, Types.INTEGER)
			};
			return new SQLParamDescriptorSet(duplicatePVParams);
		case LOAD_FOR_EXPORT:
			temp = (ParamRateLevelRefBean)bean;
			SQLParamDescriptor[] expAdminParams = {
					new SQLParamDescriptor( new Integer(temp.getIdPeriodeValidite()),false, Types.INTEGER)
			};
			return new SQLParamDescriptorSet(expAdminParams);
			
		
		default:
			break;
		}
		return null;
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs)
						throws TechnicalException, SQLException {
					ParamRateLevelRefBean bean =buildStandardParamRateLevelBeanFromRS(rs);
					return bean;
				}
			};
		
		case ADMIN_SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs)
						throws TechnicalException, SQLException {
					ParamRateLevelRefBean bean =buildStandardParamRateLevelBeanFromRS(rs);
					PeriodeValiditeRefBean perValidite = PeriodeValiditeRefDAO.buildStandardPeriodeValiditeFromRS(rs);
					bean.setPeriodeValidite(perValidite);
					return bean;
				}
			};
		case LOAD_FOR_EXPORT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs)
						throws TechnicalException, SQLException {
					ParamRateLevelRefBean bean =buildStandardParamRateLevelBeanFromRS(rs);
					PeriodeValiditeRefBean perValidite = PeriodeValiditeRefDAO.buildStandardPeriodeValiditeFromRS(rs);
					bean.setPeriodeValidite(perValidite);
					FamilleTarifRefBean ft = FamilleTarifRefDAO.buildStandardFamilleTarifRefDAOFromRS(rs);
					bean.setFamilleTarif(ft);
					return bean;
				}
			};	
		
		default:
			return null;
		}
	}

	public static ParamRateLevelRefBean buildStandardParamRateLevelBeanFromRS(ResultSet rs) throws SQLException
	{
		ParamRateLevelRefBean bean = new ParamRateLevelRefBean();
		bean.setCodeAsaCategory(rs.getString("codeAsaCategory"));
		bean.setIdFamilleTarif(rs.getInt("idFamilleTarif"));
		bean.setIdPeriodeValidite(rs.getInt("idPeriodeValidite"));
		bean.setCodeRateLevel(rs.getString("codeRateLevel"));
		return bean;
	}


	public int updateRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		deleteRef(contexte, bean);
		return insertRef(contexte, bean);
	}


	public int insertRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		ParamRateLevelMappedRefBean temp = (ParamRateLevelMappedRefBean) bean;
		String asaCategoryCode = temp.getCodeAsaCategory();
		int idFamilleTarif = temp.getIdFamilleTarif();
		int idPeriodeValidite = temp.getIdPeriodeValidite();
		Connection conn=null;
		try {
			conn = PoolCommunFactory.getInstance().getConnection();
			List<String> codesRL = temp.getCodesRateLevels();

			for (String codeRateLevel:codesRL) {
				SQLCallExecuter.getInstance().executeUpdate(conn,
						INSERT_PROC_NAME,
						new SQLParamDescriptorSet(
								new SQLParamDescriptor[]{
								new SQLParamDescriptor(asaCategoryCode, false, Types.VARCHAR),
								new SQLParamDescriptor(new Integer(idFamilleTarif), false, Types.INTEGER),
								new SQLParamDescriptor(new Integer(idPeriodeValidite), false, Types.INTEGER),
								new SQLParamDescriptor(codeRateLevel, false, Types.VARCHAR)
								}), "ParamRateLevelRefDAO", "insertRef", contexte.getContexteAppelDAO());
				LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), INSERT_PROC_NAME, "ADD", asaCategoryCode + "," + idFamilleTarif+"_"+idPeriodeValidite+"_"+codeRateLevel);
			}

			return 0;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		catch( Exception e ) {
			LogCommun.major( contexte.getCodeUtilisateur() , "ParamRateLevelRefDAO", "insertRef",
					"Exception lors de la recuperation de la connection", e );
			throw new TechnicalException( e );
		}
			finally {
				releaseConnection( conn, null );
			}
	}
	
	
	
	


	
}
