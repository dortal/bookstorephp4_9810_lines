package com.accor.asa.commun.reference.persistance.controle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.GroupeTarifRefBean;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.controle.TarifControleInstanceRefBean;
import com.accor.asa.commun.reference.metier.controle.TarifControleRefBean;
import com.accor.asa.commun.reference.persistance.RefDAO;

public class TarifControleRefDAO extends RefDAO{

	private static TarifControleRefDAO instance = new TarifControleRefDAO();
	
	public static final int ACTION_LOAD_CONTROLES_BY_TYPE=100;
	public static final int ACTION_UPDATE_INSTANCE=101;
	
	public static final int ACTION_DEL_PERIODE_VALIDITE=110;
	public static final int ACTION_DEL_FAMILLE_TARIF=115;
	public static final int ACTION_DEL_ASACATEGORY=111;
	public static final int ACTION_DEL_HOTEL=112;
	public static final int ACTION_DEL_PAYS=113;
	public static final int ACTION_DEL_PARAM=114;
	
	public static final int ACTION_INSERT_PERIODE_VALIDITE=120;
	public static final int ACTION_INSERT_FAMILLE_TARIF=125;
	public static final int ACTION_INSERT_ASACATEGORY=121;
	public static final int ACTION_INSERT_HOTEL=122;
	public static final int ACTION_INSERT_PAYS=123;
	public static final int ACTION_INSERT_PARAM=124;
	
	
	public static final String LOAD_CONTROLES_BY_TYPE_PROC_NAME="refctrl_loadCtrlsByType";
	public static final String GET_CONTROLE_BY_ID_PROC_NAME="refctrl_getInstanceById";
	public static final String UPDATE_INSTANCE_PROC_NAME="refctrl_updInstance";
	
	
	public static final String DEL_PERIODE_VALIDITE_PROC_NAME="refctrl_delPeriodeVali";
	public static final String DEL_FAMILLE_TARIF_PROC_NAME="refctrl_delFamTarif";
	public static final String DEL_ASACATEGORY_PROC_NAME="refctrl_delAsaCategory";
	public static final String DEL_HOTEL_PROC_NAME="refctrl_delHotel";
	public static final String DEL_PAYS_PROC_NAME="refctrl_delPays";
	public static final String DEL_PARAM_PROC_NAME="refctrl_delParam";
	
	
	
	public static final String INSERT_PERIODE_VALIDITE_PROC_NAME="refctrl_addPeriodeVali";
	public static final String INSERT_FAMILLE_TARIF_PROC_NAME="refctrl_addFamTarif";
	public static final String INSERT_ASACATEGORY_PROC_NAME="refctrl_addAsaCategory";
	public static final String INSERT_HOTEL_PROC_NAME="refctrl_addHotel";
	public static final String INSERT_PAYS_PROC_NAME="refctrl_addPays";
	public static final String INSERT_PARAM_PROC_NAME="refctrl_insertParam";
	
	
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return null;
	}

	@Override
	protected String getCacheClassName() {
		return null;
	}

	@Override
	protected CachableInterface getObjectInCache(String codeLangue) {
		return null;
	}

	@Override
	protected String getProcName(int type) {
		switch (type) {
		case ACTION_LOAD_CONTROLES_BY_TYPE:
			return LOAD_CONTROLES_BY_TYPE_PROC_NAME;
		case ACTION_UPDATE_INSTANCE:
			return UPDATE_INSTANCE_PROC_NAME;	
		case ACTION_DEL_PERIODE_VALIDITE:
			return DEL_PERIODE_VALIDITE_PROC_NAME;
		case ACTION_INSERT_PERIODE_VALIDITE:
			return INSERT_PERIODE_VALIDITE_PROC_NAME;
		case ACTION_DEL_FAMILLE_TARIF:
			return DEL_FAMILLE_TARIF_PROC_NAME;
		case ACTION_INSERT_FAMILLE_TARIF:
			return INSERT_FAMILLE_TARIF_PROC_NAME;
		case ACTION_INSERT_ASACATEGORY:
			return INSERT_ASACATEGORY_PROC_NAME;
		case ACTION_DEL_ASACATEGORY:
			return DEL_ASACATEGORY_PROC_NAME;
		case ACTION_INSERT_HOTEL:
			return INSERT_HOTEL_PROC_NAME;
		case ACTION_DEL_HOTEL:
			return DEL_HOTEL_PROC_NAME;
		case ACTION_DEL_PAYS:
			return DEL_PAYS_PROC_NAME;
		case ACTION_INSERT_PAYS:
			return INSERT_PAYS_PROC_NAME;
		case ACTION_DEL_PARAM:
			return DEL_PARAM_PROC_NAME;
		case ACTION_INSERT_PARAM:
			return INSERT_PARAM_PROC_NAME;
		default:
			return null;
		}
		
	}

	@Override
	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		switch (type) {
		case ACTION_LOAD_CONTROLES_BY_TYPE:
			GroupeTarifRefBean groupe = (GroupeTarifRefBean)bean;
			SQLParamDescriptor[] params = new SQLParamDescriptor[]{
					new SQLParamDescriptor(Integer.valueOf(groupe.getCode()),false,Types.INTEGER)
			};
			return new SQLParamDescriptorSet(params);
		case ACTION_UPDATE_INSTANCE:
			TarifControleInstanceRefBean instance = (TarifControleInstanceRefBean)bean;
			params = new SQLParamDescriptor[]{
					new SQLParamDescriptor(Integer.valueOf(instance.getCode()),false,Types.INTEGER),
					new SQLParamDescriptor(instance.isBloquant(),false,Types.BIT)
			};
			return new SQLParamDescriptorSet(params);
		default:
			return null;
		}
	}

	@Override
	protected SQLResultSetReader getProcReader(int type) {
		
			switch (type) {
			case ACTION_LOAD_CONTROLES_BY_TYPE:
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws SQLException {
						TarifControleInstanceRefBean instance = new TarifControleInstanceRefBean();
						instance.setCode(rs.getString("idInstance"));
						instance.setIdControle(rs.getInt("idControle"));
						TarifControleRefBean controle = new TarifControleRefBean();
						controle.setCode(String.valueOf(instance.getIdControle()));
						controle.setLibelle(rs.getString("libelle"));
						instance.setControle(controle);
						return instance;
					}
				};
		
		default:
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RefBean getByIdentifier(Contexte contexte, RefBean identifier) throws TechnicalException, IncoherenceException {
		TarifControleInstanceRefBean instance = (TarifControleInstanceRefBean)identifier;
		 try {
	            SQLResultSetReader[] readers = new SQLResultSetReader[7];
	            readers[0] = new SQLResultSetReader() {
	                public Object instanciateFromLine(ResultSet rs) throws SQLException {
			            TarifControleInstanceRefBean instance = new TarifControleInstanceRefBean();
						instance.setCode(rs.getString("idInstance"));
						instance.setIdControle(rs.getInt("idControle"));
						instance.setBloquant(rs.getBoolean("isBloquant"));
						instance.setIdGroupeTarif(rs.getInt("idGroupeTarif"));
						TarifControleRefBean controle = new TarifControleRefBean();
						controle.setCode(String.valueOf(instance.getIdControle()));
						controle.setNameProcedure(rs.getString("nomProc"));
						controle.setIdTypeParam(rs.getInt("idTypeParam"));
						controle.setLibelle(rs.getString("libelle"));
						controle.setLibelleMessage(rs.getString("message"));
						instance.setControle(controle);
	            return instance;
	                }
	            };
	            readers[1] = new SQLResultSetReader() {
	                public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
	                    return StringUtils.trimToEmpty(rs.getString("valeur"));
	                }
	            };
	            readers[2] = new SQLResultSetReader() {
	                public Object instanciateFromLine(ResultSet rs) throws SQLException {
	                    return StringUtils.trimToEmpty(rs.getString("codeAsaCategory"));
	                }
	            };
	            readers[3] = new SQLResultSetReader() {
	                public Object instanciateFromLine(ResultSet rs) throws SQLException {
	                    return StringUtils.trimToEmpty(rs.getString("codePays"));
	                }
	            };
	            readers[4] = new SQLResultSetReader() {
	                public Object instanciateFromLine(ResultSet rs) throws SQLException {

	                    return StringUtils.trimToEmpty(rs.getString("codeHotel"));
	                }
	            };
	            readers[5] = new SQLResultSetReader() {
	                public Object instanciateFromLine(ResultSet rs) throws SQLException {

	                    return StringUtils.trimToEmpty(rs.getString("idPeriodeValidite"));
	                }
	            };
	            readers[6] = new SQLResultSetReader() {
	                public Object instanciateFromLine(ResultSet rs) throws SQLException {

	                    return StringUtils.trimToEmpty(rs.getString("idFamilleTarif"));
	                }
	            };
	            
	            List[] result = SQLCallExecuter.getInstance().executeMultipleSelectProc(GET_CONTROLE_BY_ID_PROC_NAME,
	                    new SQLParamDescriptorSet(new SQLParamDescriptor(new Integer(instance.getCode()), false, Types.INTEGER)),
	                    getClass().getSimpleName(), "getByIdentifier",
	                    contexte.getContexteAppelDAO(), readers, false);
	            if(result[0]==null || result[0].isEmpty())
	            	return null;
	            
	            instance = (TarifControleInstanceRefBean)result[0].get(0);
	            instance.setParamValues((List)result[1]);
	            instance.setCodesAsaCategory((List)result[2]);
	            instance.setCodesPaysExclus((List)result[3]);
	            instance.setCodesHotelsExclus((List)result[4]);
	            instance.setIdsPeriodesValidite(result[5]);
	            instance.setIdsFamTarif(result[6]);
	          
	            return instance;
	        } catch (SQLException ex) {
	            LogCommun.major(contexte.getCodeUtilisateur(), getClass().getSimpleName(), "getByIdentifier", instance.getCode(), ex);
	            throw new TechnicalException(ex);
	        }
	}

	public static TarifControleRefDAO getInstance() {
		return instance;
	}

	public int deleteControleParam(Contexte contexte, int idInstance, int actionType) throws TechnicalException, IncoherenceException, ForeignKeyException {
		try {
			int res = -1;
			String procName = getProcName(actionType);
			SQLParamDescriptorSet procParams=new SQLParamDescriptorSet(new SQLParamDescriptor(Integer.valueOf(idInstance), false, Types.INTEGER));

			res = SQLCallExecuter.getInstance().executeUpdate(procName, procParams, getClass().getSimpleName(), "deleteControleParam", contexte.getContexteAppelDAO());
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "deleteControleParam "+actionType, String.valueOf(idInstance));
			LogCommun.debug(getClass().getSimpleName(), "deleteControleParam "+actionType, "Code retour = " + res);
			return res;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public int insertControleParams(Contexte contexte, TarifControleInstanceRefBean bean, int actionType) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		
		SQLParamDescriptorSet[] params = createInsertParams( bean, actionType);
		if(params==null)
			return 0;
		String procName = getProcName(actionType);
		try {
			SQLCallExecuter.getInstance().executeListeUpdate(procName, params, getClass().getSimpleName(), "insertControleParams "+actionType, contexte.getContexteAppelDAO());
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return 0;
	}
	
	private SQLParamDescriptorSet[] createInsertParams(TarifControleInstanceRefBean bean, int actionType) {
		List<String> values =getListValeurs(bean, actionType);
		if(values == null || values.isEmpty())
			return null;
		SQLParamDescriptorSet[] params = new SQLParamDescriptorSet[values.size()];
		Integer idInstance = Integer.valueOf(bean.getCode());
	
		for (int i = 0; i < values.size(); i++) {
			String value = values.get(i);
			
			boolean intParam = (actionType==ACTION_INSERT_PERIODE_VALIDITE || actionType==ACTION_INSERT_FAMILLE_TARIF);
			SQLParamDescriptor[] pd = new SQLParamDescriptor[] { 
					new SQLParamDescriptor(idInstance, false, Types.INTEGER), 
					new SQLParamDescriptor(intParam?Integer.valueOf(value):value, false, intParam?Types.INTEGER:Types.VARCHAR) 
			};
			params[i] = new SQLParamDescriptorSet(pd);
		}
		return params;
	}
	
	private List<String> getListValeurs(TarifControleInstanceRefBean bean, int actionType)
	{
		switch (actionType) {
		case ACTION_INSERT_PERIODE_VALIDITE:
			return bean.getIdsPeriodesValidite();
		case ACTION_INSERT_FAMILLE_TARIF:
			return bean.getIdsFamTarif();
		case ACTION_INSERT_ASACATEGORY:
			return bean.getCodesAsaCategory();	
		case ACTION_INSERT_HOTEL:
			return bean.getCodesHotelsExclus();
		case ACTION_INSERT_PAYS:
			return bean.getCodesPaysExclus();
		case ACTION_INSERT_PARAM:
			return bean.getParamValues();
		default:
			return null;
		}
	}

	@Override
	public int updateRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		
		TarifControleInstanceRefBean instance= (TarifControleInstanceRefBean)bean;
		int idInstance = Integer.parseInt(instance.getId());
		super.doAction(ACTION_UPDATE_INSTANCE, contexte, instance);
		deleteControleParam(contexte, idInstance, ACTION_DEL_PERIODE_VALIDITE);
		insertControleParams(contexte, instance, ACTION_INSERT_PERIODE_VALIDITE);
		deleteControleParam(contexte, idInstance, ACTION_DEL_FAMILLE_TARIF);
		insertControleParams(contexte, instance, ACTION_INSERT_FAMILLE_TARIF);
		deleteControleParam(contexte, idInstance, ACTION_DEL_ASACATEGORY);
		insertControleParams(contexte, instance, ACTION_INSERT_ASACATEGORY);
		deleteControleParam(contexte, idInstance, ACTION_DEL_HOTEL);
		insertControleParams(contexte, instance, ACTION_INSERT_HOTEL);
		deleteControleParam(contexte, idInstance, ACTION_DEL_PAYS);
		insertControleParams(contexte, instance, ACTION_INSERT_PAYS);
		deleteControleParam(contexte, idInstance, ACTION_DEL_PARAM);
		insertControleParams(contexte, instance, ACTION_INSERT_PARAM);
		return 0;
	}
	
}
