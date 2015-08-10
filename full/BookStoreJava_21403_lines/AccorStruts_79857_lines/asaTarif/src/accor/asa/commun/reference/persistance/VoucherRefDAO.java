package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.VoucherRefBean;

public class VoucherRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = "vente_select_type_vouchers";
	private static final String ADMIN_SELECT_PROC_NAME = "vente_DRef_sel_voucher";
	private static final String INSERT_PROC_NAME = "vente_DRef_ins_voucher";
	private static final String UPDATE_PROC_NAME = "vente_DRef_upd_voucher";
	private static final String DELETE_PROC_NAME = "vente_DRef_del_voucher";

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
		VoucherRefBean temp = (VoucherRefBean) bean;
		switch (type) {
			case SELECT :
				SQLParamDescriptor [] selectParams = {
						new SQLParamDescriptor(codeLangue)
					};
				return new SQLParamDescriptorSet(selectParams);
			case ADMIN_SELECT :
				SQLParamDescriptor [] adminSelectParams = {
					new SQLParamDescriptor(new Boolean(true))
				};
				return new SQLParamDescriptorSet(adminSelectParams);
			case INSERT :
				SQLParamDescriptor [] insertParams = {
					new SQLParamDescriptor(Integer.valueOf(temp.getId())),
					new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR),
					new SQLParamDescriptor(new Boolean(temp.getSupplementInclus())),
					new SQLParamDescriptor(temp.getCodeLangue(), Types.VARCHAR)
				};
				return new SQLParamDescriptorSet(insertParams);
			case UPDATE :
				SQLParamDescriptor [] updateParams = {
					new SQLParamDescriptor(Integer.valueOf(temp.getId())),
					new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR),
					new SQLParamDescriptor(new Boolean(temp.getSupplementInclus()))
				};
				return new SQLParamDescriptorSet(updateParams);
			case DELETE :
				SQLParamDescriptor [] deleteParams = {
					new SQLParamDescriptor(temp.getId())
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
						VoucherRefBean bean = new VoucherRefBean();
						bean.setId(Integer.toString(rs.getInt("CODE_TYPE_VOUCHERS")));
						bean.setLibelle(rs.getString("LABEL_TYPE_VOUCHERS"));
						bean.setSupplementInclus(rs.getBoolean("SUPPLEMENT_INCLUS"));
						return bean;
					}
				};
			case ADMIN_SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						VoucherRefBean bean = new VoucherRefBean();
						bean.setId(Integer.toString(rs.getInt("CODE")));
						bean.setLibelle(rs.getString("LABEL"));
						bean.setSupplementInclus(rs.getBoolean("SUPPLEMENT_INCLUS"));
						bean.setActif(rs.getString("SUPPRIMER").equals("0"));
						return bean;
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
	
	

}