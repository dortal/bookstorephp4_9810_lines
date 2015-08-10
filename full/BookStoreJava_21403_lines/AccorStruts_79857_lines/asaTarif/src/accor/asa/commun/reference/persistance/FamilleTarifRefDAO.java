package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.FamilleTarifCacheList;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.GroupeTarifRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class FamilleTarifRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = "ref_selAllFamilleTarif";
	private static final String ADMIN_SELECT_PROC_NAME = "ref_selAdminFamilleTarif";
	private static final String INSERT_PROC_NAME = "ref_addFamilleTarif";
	private static final String UPDATE_PROC_NAME = "ref_updFamilleTarif";
	private static final String DELETE_PROC_NAME = "ref_delFamilleTarif";

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
		default:
			return null;
		}
	}

	
	
	@SuppressWarnings("unchecked")
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		FamilleTarifCacheList cache = new FamilleTarifCacheList((List<FamilleTarifRefBean>) data, contexte);
		return cache;
	}

	protected String getCacheClassName() {
		return FamilleTarifCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(FamilleTarifCacheList.class, codeLangue);
	}

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		FamilleTarifRefBean temp = (FamilleTarifRefBean) bean;
		switch (type) {
		case SELECT:
			SQLParamDescriptor[] selectParams = { new SQLParamDescriptor(codeLangue) };
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
			SQLParamDescriptor[] adminSelectParams = {

			};
			return new SQLParamDescriptorSet(adminSelectParams);
		case INSERT:
			SQLParamDescriptor[] insertDescriptors = { new SQLParamDescriptor(Integer.valueOf(temp.getId()), false, Types.INTEGER), new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
					new SQLParamDescriptor(new Integer(temp.getIdGroupeTarif()), false, Types.INTEGER), new SQLParamDescriptor(temp.getCodeTypeTarifTars(), false, Types.VARCHAR),
					new SQLParamDescriptor(Boolean.valueOf(temp.isContratDistrib()), false, Types.BIT) };
			return new SQLParamDescriptorSet(insertDescriptors);
		case UPDATE:
			SQLParamDescriptor[] updateDescriptors = { new SQLParamDescriptor(Integer.valueOf(temp.getId()), false, Types.INTEGER), new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getCodeTypeTarifTars(), false, Types.VARCHAR), new SQLParamDescriptor(Boolean.valueOf(temp.isContratDistrib()), false, Types.BIT), };
			return new SQLParamDescriptorSet(updateDescriptors);
		case DELETE:
			SQLParamDescriptor[] deleteDescriptors = { new SQLParamDescriptor(temp.getCode(), false, Types.INTEGER) };
			return new SQLParamDescriptorSet(deleteDescriptors);
		default:
			return null;
		}
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws SQLException {
					FamilleTarifRefBean famille = new FamilleTarifRefBean();
					famille.setCode(String.valueOf(rs.getInt("code")));
					famille.setName(rs.getString("name"));
					famille.setLibelle(rs.getString("libelle"));
					famille.setIdGroupeTarif(rs.getInt("idGroupeTarif"));
					famille.setCodeTypeTarifTars(rs.getString("codeTars"));
					famille.setContratDistrib(rs.getBoolean("isContratDistrib"));
					famille.setActif(!rs.getBoolean("supprime"));
					return famille;
				}
			};
		case ADMIN_SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws SQLException {
					FamilleTarifRefBean famille = buildStandardFamilleTarifRefDAOFromRS(rs);
					GroupeTarifRefBean groupe = GroupeTarifRefDAO.buildStandardGroupeTarifFromRS(rs);
					
					famille.setGroupeTarif(groupe);
					return famille;
				}
			};

		default:
			return null;
		}
	}

	public static FamilleTarifRefBean buildStandardFamilleTarifRefDAOFromRS(ResultSet rs) throws SQLException {

		FamilleTarifRefBean famille = new FamilleTarifRefBean();
		famille.setCode(String.valueOf(rs.getInt("idFamilleTarif")));
		famille.setName(rs.getString("nomFamilleTarif"));
		famille.setLibelle(famille.getName());
		famille.setIdGroupeTarif(rs.getInt("idGroupeTarif"));
		famille.setCodeTypeTarifTars(rs.getString("codeTypeTarifTars"));
		famille.setContratDistrib(rs.getBoolean("isContratDistrib"));
		famille.setActif(!rs.getBoolean("ft_supprime"));
		return famille;
	}
}
