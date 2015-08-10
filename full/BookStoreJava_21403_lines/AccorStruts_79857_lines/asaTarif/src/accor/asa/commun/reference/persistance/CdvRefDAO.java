package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.CdvRefBean;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.vente.cdv.metier.MapCdvs;

public class CdvRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = null;
	private static final String ADMIN_SELECT_PROC_NAME = "admin_cdv_sel_cdv";
	private static final String INSERT_PROC_NAME = null;
	private static final String INSERT_GROUPE_OFFRE_PROC_NAME = "admin_cdv_ins_cdv_gpoffre";
	private static final String UPDATE_PROC_NAME = "admin_cdv_upd_cdv";
	private static final String DELETE_PROC_NAME = "admin_cdv_del_cdv";
	private static final String DELETE_GROUPE_OFFRE_PROC_NAME = "admin_cdv_del_cdv_gpoffre";

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

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		CdvRefBean temp = (CdvRefBean) bean;
		switch (type) {
		case SELECT:
			return null;
		case ADMIN_SELECT:
			return null;
		case INSERT:
			return null;
		case UPDATE:
			return null;
		case DELETE:
			SQLParamDescriptor[] deleteParams = { new SQLParamDescriptor(new Integer(temp.getId()), false, Types.SMALLINT) };
			return new SQLParamDescriptorSet(deleteParams);
		default:
			return null;
		}
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case SELECT:
			return null;
		case ADMIN_SELECT:
			return null;
		default:
			return null;
		}
	}

	protected String getCacheClassName() {
		return MapCdvs.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return null;
	}

	

	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return null;
	}

	/**
	 * Redéfinitions des méthodes mères
	 */
	public List<CdvRefBean> getAdminRefList(Contexte contexte, RefBean bean) throws TechnicalException {
		try {
			final TreeMap<String, CdvRefBean> temp = new TreeMap<String, CdvRefBean>();

			String procName = ADMIN_SELECT_PROC_NAME;
			SQLCallExecuter.getInstance().executeSelectProc(procName, new SQLParamDescriptorSet(), "CdvRefDAO", "getAdminRefList", contexte.getContexteAppelDAO(), new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					String id = rs.getString("code");
					String libelle = rs.getString("libelle_gb");
					String libelleLong = rs.getString("libelle_long_gb");
					String libelleCourt = rs.getString("libelle_court_gb");
					String codeTars = rs.getString("code_tars");
					boolean actif = !rs.getBoolean("supprimer");

					CdvRefBean bean = (CdvRefBean) temp.get(id);
					if (bean == null) {
						bean = new CdvRefBean();
						bean.setId(id);
						bean.setLibelle(libelle);
						bean.setLibelleLong(libelleLong);
						bean.setLibelleCourt(libelleCourt);
						bean.setCodeTars(codeTars);
						bean.setActif(actif);
					}
					String idGroupeOffre = rs.getString("id_groupe_offre");
					if (idGroupeOffre != null) {
						String libelleGroupeOffre = rs.getString("code_groupe_offre");
						bean.addGroupeOffre(new Element(idGroupeOffre, libelleGroupeOffre));
					}

					temp.put(bean.getId(), bean);
					return null;
				}
			});
			List<CdvRefBean> refDataList = new ArrayList<CdvRefBean>(temp.values());

			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "SELECT", "");
			LogCommun.debug("CdvRefDAO", "getAdminRefList", "Taille resultat = " + refDataList.size());
			return refDataList;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	public int updateRef(Contexte contexte, RefBean bean) throws TechnicalException {
		CdvRefBean temp = (CdvRefBean) bean;
		try {
			// MAJ du libellé et du libellé long
			SQLCallExecuter.getInstance().executeUpdate(
					UPDATE_PROC_NAME,
					new SQLParamDescriptorSet(new SQLParamDescriptor[] { new SQLParamDescriptor(Integer.valueOf(bean.getId()), Types.SMALLINT),
							new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR), new SQLParamDescriptor(temp.getLibelleLong(), Types.VARCHAR),
							new SQLParamDescriptor(temp.getLibelleCourt(), Types.VARCHAR), new SQLParamDescriptor(temp.getCodeTars(), Types.VARCHAR) }), "CdvRefDAO", "updateRef",
					contexte.getContexteAppelDAO());
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), UPDATE_PROC_NAME, "UPDATE", bean.toString());
			LogCommun.debug("CdvRefDAO", "updateRef", "");

			// Suppression des groupes offre
			SQLCallExecuter.getInstance().executeUpdate(DELETE_GROUPE_OFFRE_PROC_NAME,
					new SQLParamDescriptorSet(new SQLParamDescriptor[] { new SQLParamDescriptor(Integer.valueOf(bean.getId()), Types.SMALLINT) }), "CdvRefDAO", "updateRef",
					contexte.getContexteAppelDAO());
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), DELETE_GROUPE_OFFRE_PROC_NAME, "DELETE", bean.getId());
			LogCommun.debug("CdvRefDAO", "updateRef", bean.getId());

			// Insertion des nouveaux groupes offre
			Iterator<Element> it = temp.getGroupesOffre().iterator();
			Element groupeOffre = null;
			while (it.hasNext()) {
				groupeOffre =  it.next();
				SQLCallExecuter.getInstance().executeUpdate(
						INSERT_GROUPE_OFFRE_PROC_NAME,
						new SQLParamDescriptorSet(new SQLParamDescriptor[] { new SQLParamDescriptor(Integer.valueOf(bean.getId()), Types.SMALLINT),
								new SQLParamDescriptor(groupeOffre.getCode(), Types.INTEGER) }), "CdvRefDAO", "updateRef", contexte.getContexteAppelDAO());
				LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), INSERT_GROUPE_OFFRE_PROC_NAME, "INSERT", bean.getId() + "-" + groupeOffre.getCode());
				LogCommun.debug("CdvRefDAO", "updateRef", bean.getId() + "-" + groupeOffre.getCode());
			}

			return 0;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

}