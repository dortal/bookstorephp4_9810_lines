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
import com.accor.asa.commun.reference.metier.DureeSejourRefBean;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.MealPlanRefBean;
import com.accor.asa.commun.reference.metier.ParamGrilleCacheList;
import com.accor.asa.commun.reference.metier.ParamGrilleRefBean;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.SupplementRefBean;

public class ParamGrilleRefDAO extends RefDAO {

	public static final int DUPLICATE_RL=102;
	public static final int DUPLICATE_ASA=103;
	public static final int DUPLICATE_PV=104;
	
	public static final int LOAD_FOR_EXPORT=105;

	private static final String SELECT_PROC_NAME = "refparam_selAllGrille";
	private static final String ADMIN_SELECT_PROC_NAME = "refparam_selAdminParamGrille";
	private static final String INSERT_PROC_NAME = "refparam_addGrille";
	private static final String UPDATE_PROC_NAME = "refparam_updateGrille";
	private static final String DELETE_PROC_NAME = "refparam_delGrille";
	
	private static final String DELETE_SUPPLEMENTS_PROC_NAME = "refparam_delSupplement";
	private static final String INSERT_SUPPLEMENT_PROC_NAME = "refparam_addSupplement";
	
	private static final String DUPLICATE_RL_PROC_NAME = "refparam_duplicateGrilleRL";
	private static final String DUPLICATE_ASA_PROC_NAME = "refparam_duplicateGrilleAsa";
	private static final String DUPLICATE_PV_PROC_NAME = "refparam_duplicateGrillePV";
	private static final String EXPORT_PROC_NAME = "refparam_selExpGrille";
	
	private static final String SEL_RL_FOR_PARAMGRILLE_PROC_NAME = "refparam_selRLforParamGrille";

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
		case DUPLICATE_RL:
			return DUPLICATE_RL_PROC_NAME;
		case DUPLICATE_ASA:
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
		return new ParamGrilleCacheList((List<ParamGrilleRefBean>) data);
	}

	protected String getCacheClassName() {
		return ParamGrilleCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(ParamGrilleCacheList.class);
	}

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		ParamGrilleRefBean temp = (ParamGrilleRefBean) bean;
		switch (type) {
		case SELECT:
			SQLParamDescriptor[] selectParams = {

			};
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
			SQLParamDescriptor[] adminSelectParams = { new SQLParamDescriptor(temp.getCodeAsaCategory(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getIdFamilleTarif(), false, Types.INTEGER), new SQLParamDescriptor(temp.getIdPeriodeValidite(), false, Types.INTEGER) };
			return new SQLParamDescriptorSet(adminSelectParams);
		case INSERT:
		case UPDATE:
            SQLParamDescriptor[] writeParams = { new SQLParamDescriptor(temp.getCodeAsaCategory(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getIdPeriodeValidite(), false, Types.INTEGER),
					new SQLParamDescriptor(temp.getCodeRateLevel(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getIdFamilleTarif(), false, Types.INTEGER),
					new SQLParamDescriptor(temp.getValueCommission(), false, Types.DOUBLE),
					new SQLParamDescriptor(temp.getUniteCommission(), false, Types.VARCHAR), 
					new SQLParamDescriptor(temp.getCodeMealPlan(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getIdDureeSejour(), false, Types.INTEGER),
					new SQLParamDescriptor(temp.isNewContrat(), false, Types.BIT),
					new SQLParamDescriptor(temp.isBlackOutDates(), false, Types.BIT),
					new SQLParamDescriptor(temp.isOneYearOnly(), false, Types.BIT),
					new SQLParamDescriptor(temp.isLunWe(), false, Types.BIT),
					new SQLParamDescriptor(temp.isMarWe(), false, Types.BIT),
					new SQLParamDescriptor(temp.isMerWe(), false, Types.BIT),
					new SQLParamDescriptor(temp.isJeuWe(), false, Types.BIT),
					new SQLParamDescriptor(temp.isVenWe(), false, Types.BIT),
					new SQLParamDescriptor(temp.isSamWe(), false, Types.BIT),
					new SQLParamDescriptor(temp.isDimWe(), false, Types.BIT)
					};
			
			return new SQLParamDescriptorSet(writeParams);
		case DELETE:
			SQLParamDescriptor[] deleteParams = { new SQLParamDescriptor(temp.getCodeAsaCategory(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getIdPeriodeValidite(), false, Types.INTEGER), new SQLParamDescriptor(temp.getCodeRateLevel(), false, Types.VARCHAR) };
			return new SQLParamDescriptorSet(deleteParams);
		
		case DUPLICATE_RL:
			SQLParamDescriptor[] dupicateRLParams = { 
					new SQLParamDescriptor(temp.getCodeAsaCategory(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getIdPeriodeValidite(), false, Types.INTEGER),
					new SQLParamDescriptor(temp.getCodeRateLevel(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getOldId(), false, Types.VARCHAR)};
			return new SQLParamDescriptorSet(dupicateRLParams);
		case DUPLICATE_ASA:
			SQLParamDescriptor[]dupicateAsaParams = { 
					new SQLParamDescriptor(temp.getCodeAsaCategory(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getIdPeriodeValidite(), false, Types.INTEGER),
					new SQLParamDescriptor(temp.getIdFamilleTarif(), false, Types.INTEGER),
					new SQLParamDescriptor(temp.getOldId(), false, Types.VARCHAR)};
			return new SQLParamDescriptorSet(dupicateAsaParams);
		case DUPLICATE_PV:
			SQLParamDescriptor[]dupicatePVParams = { 
					new SQLParamDescriptor(temp.getIdPeriodeValidite(), false, Types.INTEGER),
					new SQLParamDescriptor(temp.getIdFamilleTarif(), false, Types.INTEGER),
					new SQLParamDescriptor(new Integer(temp.getOldId()), false, Types.INTEGER)};
			return new SQLParamDescriptorSet(dupicatePVParams);
		case LOAD_FOR_EXPORT:
			SQLParamDescriptor[] expSelectParams = { 
					new SQLParamDescriptor(temp.getIdPeriodeValidite(), false, Types.INTEGER) };
			return new SQLParamDescriptorSet(expSelectParams);
		default:
			return null;
		}
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case ADMIN_SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamGrilleRefBean bean = buildStandardParamGrileeRefBeanFromRS(rs);
					DureeSejourRefBean ds = DureeSejourRefDAO.buildStandardDureeSejourfromRS(rs);
					bean.setDureeSejour(ds);
					MealPlanRefBean mp = MealPlanRefDAO.buildStandardMealPlanFromRS(rs);
					bean.setMealplan(mp);
					return bean;
				}

			};
		case LOAD_FOR_EXPORT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamGrilleRefBean bean = buildStandardParamGrileeRefBeanFromRS(rs);
					DureeSejourRefBean ds = DureeSejourRefDAO.buildStandardDureeSejourfromRS(rs);
					bean.setDureeSejour(ds);
					MealPlanRefBean mp = MealPlanRefDAO.buildStandardMealPlanFromRS(rs);
					bean.setMealplan(mp);
					FamilleTarifRefBean ft = FamilleTarifRefDAO.buildStandardFamilleTarifRefDAOFromRS(rs);
					bean.setFamilleTarif(ft);
					return bean;
				}

			};
		case SELECT:
		case GET_BY_ID:	
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
                    return buildStandardParamGrileeRefBeanFromRS(rs);
				}
			};
		default:
			return null;
		}

	}

	public static ParamGrilleRefBean buildStandardParamGrileeRefBeanFromRS(ResultSet rs) throws SQLException {
		ParamGrilleRefBean bean = new ParamGrilleRefBean();
		bean.setCodeAsaCategory(rs.getString("codeAsaCategory"));
		bean.setIdPeriodeValidite(rs.getInt("idPeriodeValidite"));
		bean.setCodeRateLevel(rs.getString("codeRateLevel"));
		bean.setIdFamilleTarif(rs.getInt("idFamilleTarif"));
		bean.setValueCommission(rs.getDouble("valueCommission"));
		bean.setUniteCommission(rs.getString("uniteCommission"));
		bean.setCodeMealPlan(rs.getString("codeMealPlan"));
		bean.setIdDureeSejour(rs.getInt("idDureeSejour"));
		bean.setNewContrat(rs.getBoolean("isNewContrat"));
		bean.setBlackOutDates(rs.getBoolean("isBlackOutDates"));
		bean.setOneYearOnly(rs.getBoolean("isOneYearOnly"));
		bean.setLunWe(rs.getBoolean("isLunWe"));
		bean.setMarWe(rs.getBoolean("isMarWe"));
		bean.setMerWe(rs.getBoolean("isMerWe"));
		bean.setJeuWe(rs.getBoolean("isJeuWe"));
		bean.setVenWe(rs.getBoolean("isVenWe"));
		bean.setSamWe(rs.getBoolean("isSamWe"));
		bean.setDimWe(rs.getBoolean("isDimWe"));

		return bean;
	}

	

	public int doAction(int actionType, Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		
		try {
			int res;
			String procName = getProcName(actionType);
			SQLParamDescriptorSet procParams = getProcParameters(actionType, bean, ADMIN_LANGUAGE);

			res = SQLCallExecuter.getInstance().executeUpdate(procName, procParams, "ParamGrilleRefDAO", "doAction "+actionType, contexte.getContexteAppelDAO());
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "UPDATE", bean.toString());
			LogCommun.debug("ParamGrilleRefDAO", "doAction "+actionType, "Code retour = " + res);
			return res;
		} catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
				throw new DuplicateKeyException(e);
			}
			throw new TechnicalException(e);
		}
	}
	
	private int insertSupplementsRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		ParamGrilleRefBean grille = (ParamGrilleRefBean) bean;
		String asaCategoryCode = grille.getCodeAsaCategory();
		String codeRateLevel = grille.getCodeRateLevel();
		int idPeriodeValidite = grille.getIdPeriodeValidite();
		List<SupplementRefBean> supplements = grille.getSupplements();
		if(supplements == null)
			return 0;
		Connection conn=null;
		try {
			conn = PoolCommunFactory.getInstance().getConnection();
            for (SupplementRefBean supp : supplements) {
                SQLCallExecuter.getInstance().executeUpdate(conn,
                        INSERT_SUPPLEMENT_PROC_NAME,
                        new SQLParamDescriptorSet(
                                new SQLParamDescriptor[]{
                                        new SQLParamDescriptor(asaCategoryCode, false, Types.VARCHAR),
                                        new SQLParamDescriptor(codeRateLevel, false, Types.VARCHAR),
                                        new SQLParamDescriptor(idPeriodeValidite, false, Types.INTEGER),
                                        new SQLParamDescriptor(supp.getCode(), false, Types.VARCHAR),
                                }), "ParamGrilleRefDAO", "insertSupplementsRef", contexte.getContexteAppelDAO());
                LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), INSERT_PROC_NAME, "ADD", asaCategoryCode + "," + grille.getCode() + "_" + supp.getCode());
            }

            return 0;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		catch( Exception e ) {
			LogCommun.major( contexte.getCodeUtilisateur() , "ParamGrilleRefDAO", "insertSupplementsRef",
					"Exception lors de la recuperation de la connection", e );
			throw new TechnicalException( e );
		}
			finally {
				releaseConnection( conn, null );
			}
	}

	public int insertRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		super.insertRef(contexte, bean);
		return insertSupplementsRef(contexte, bean);
	}
	
	
	private void deleteSupplements(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException
	{
		ParamGrilleRefBean temp = (ParamGrilleRefBean)bean;
		try {
			int res;
			String procName = DELETE_SUPPLEMENTS_PROC_NAME;
			SQLParamDescriptorSet procParams =new SQLParamDescriptorSet(
					new SQLParamDescriptor[]{
							new SQLParamDescriptor(temp.getCodeAsaCategory(), false, Types.VARCHAR),
							new SQLParamDescriptor( temp.getCodeRateLevel(),false, Types.VARCHAR),
							new SQLParamDescriptor(temp.getIdPeriodeValidite(),false, Types.INTEGER)
							});

			res = SQLCallExecuter.getInstance().executeUpdate(
				procName,
				procParams,
				"ParamGrilleRefDAO",
				"deleteSupplements",
				contexte.getContexteAppelDAO());
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "DELETE", bean.getId());
			LogCommun.debug("ParamGrilleRefDAO", "deleteSupplements", "Code retour = " + res);
			
		}
		catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	public int deleteRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, ForeignKeyException {
		deleteSupplements(contexte, bean);
		return super.deleteRef(contexte, bean);
	}

	public int updateRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		 super.updateRef(contexte, bean);
		 deleteSupplements(contexte, bean);
		 insertSupplementsRef(contexte, bean);
		 return 0;
	}
	
	
	
}
