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
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.UniteTaxeCacheList;
import com.accor.asa.commun.reference.metier.UniteTaxeRefBean;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class UniteTaxeRefDAO  extends RefDAO{
	private static final String SELECT_PROC_NAME = "ref_selAllUniteTaxe";
	private static final String ADMIN_SELECT_PROC_NAME = "ref_selAdminUniteTaxe";
	private static final String INSERT_PROC_NAME = "ref_addUniteTaxe";
	private static final String UPDATE_PROC_NAME = "ref_updUniteTaxe";
	private static final String DELETE_PROC_NAME = "ref_delUniteTaxe";

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


	protected String getCacheClassName() {
		return UniteTaxeCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(UniteTaxeCacheList.class, codeLangue);
	}



	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		UniteTaxeRefBean temp = (UniteTaxeRefBean)bean;
		switch (type) {
		case SELECT:
			SQLParamDescriptor[] selectParams = { new SQLParamDescriptor(codeLangue)};
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:

			SQLParamDescriptor[] adminSelectParams = {};
			return new SQLParamDescriptorSet(adminSelectParams);
		case INSERT:
		case UPDATE:
			SQLParamDescriptor[] writeParams = {
					new SQLParamDescriptor(Integer.valueOf(temp.getCode()),false,Types.INTEGER),
					new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.isDefault(), false, Types.BIT)
			};
			return new SQLParamDescriptorSet(writeParams);
		case DELETE:
			SQLParamDescriptor[] deleteParams = {
							new SQLParamDescriptor(Integer.valueOf(temp.getCode()),false,Types.INTEGER),
			};
			return new SQLParamDescriptorSet(deleteParams);
		default:
			return null;
		}
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case ADMIN_SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
                    UniteTaxeRefBean bean = new UniteTaxeRefBean();
                    bean.setCode(String.valueOf(rs.getInt("idUniteTaxe")));
                    bean.setName(rs.getString("nomUniteTaxe"));
                    bean.setLibelle(bean.getName());
                    bean.setDefault(rs.getBoolean("isDefault"));
                    bean.setActif(!rs.getBoolean("supprime"));
                    return bean;
				}
			};
		case SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					UniteTaxeRefBean bean = new UniteTaxeRefBean();
					bean.setCode(String.valueOf(rs.getInt("code")));
					bean.setName(rs.getString("name"));
					bean.setLibelle(rs.getString("libelle"));
					bean.setDefault(rs.getBoolean("isDefault"));
					bean.setActif(!rs.getBoolean("supprime"));
					return bean;
				}

			};
		default:
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new UniteTaxeCacheList((List<UniteTaxeRefBean>) data, contexte);
	}
}