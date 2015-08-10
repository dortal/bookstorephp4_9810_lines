package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.donneesdereference.metier.MapMotifsDeStatut;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.MotifStatutRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class MotifStatutRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = null;
	private static final String ADMIN_SELECT_PROC_NAME = "vente_DRef_sel_motif_statut";
	private static final String SELECT_STATUT_AUTORISE_PROC_NAME = "vente_sel_motif_statutAutorise";
	private static final String INSERT_PROC_NAME = "vente_DRef_ins_motif_statut";
	private static final String INSERT_STATUT_AUTORISE_PROC_NAME = "vente_ins_motif_statutAutorise";
	private static final String UPDATE_PROC_NAME = "vente_DRef_upd_motif_statut";
	private static final String DELETE_PROC_NAME = "vente_DRef_del_motif_statut";
	private static final String DELETE_STATUT_AUTORISE_PROC_NAME = "vente_del_motif_statutAutorise";

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
		MotifStatutRefBean temp = (MotifStatutRefBean) bean;
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				SQLParamDescriptor [] adminSelectParams = {
					new SQLParamDescriptor(new Boolean(true))
				};
				return new SQLParamDescriptorSet(adminSelectParams);
			case INSERT :
				SQLParamDescriptor [] insertParams = {
					new SQLParamDescriptor(Integer.valueOf(temp.getId())),
					new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getCodeLangue(), Types.VARCHAR),
					new SQLParamDescriptor(new Boolean(temp.getHasInfo()), Types.BOOLEAN),
				};
				return new SQLParamDescriptorSet(insertParams);
			case UPDATE :
				SQLParamDescriptor [] updateParams = {
					new SQLParamDescriptor(Integer.valueOf(temp.getId()), Types.INTEGER),
					new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR),
					new SQLParamDescriptor(new Boolean(temp.getHasInfo()), Types.BOOLEAN),
				};
				return new SQLParamDescriptorSet(updateParams);
			case DELETE :
				SQLParamDescriptor [] deleteParams = {
					new SQLParamDescriptor(Integer.valueOf(temp.getId()), Types.INTEGER)
				};
				return new SQLParamDescriptorSet(deleteParams);
			default :
				return null;
		}
	}

	protected SQLResultSetReader getProcReader (int type) {
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						MotifStatutRefBean motifStatut = new MotifStatutRefBean();
						motifStatut.setId(rs.getString("CLE"));
						motifStatut.setLibelle(rs.getString("MOTIF"));
						motifStatut.setCodeLangue(rs.getString("CODE_LANGUE"));
						motifStatut.setActif(rs.getString("SUPPRIMER").equals("0"));
						motifStatut.setHasInfo(rs.getBoolean("has_info"));
						return motifStatut;
					}
				};
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return MapMotifsDeStatut.class.getName();
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
	@SuppressWarnings("unchecked")
	public List<MotifStatutRefBean> getAdminRefList( Contexte contexte, RefBean bean ) throws TechnicalException {
		try {
			List<MotifStatutRefBean> lTemp = (List<MotifStatutRefBean>) 
					super.getAdminRefList(contexte, bean);

			final TreeMap<String, MotifStatutRefBean> tmTemp = new TreeMap<String, MotifStatutRefBean>();
			
			for( MotifStatutRefBean motifStatut : lTemp ) {
				tmTemp.put(motifStatut.getId(), motifStatut);
			}

			// Procédure de selection des motifs autorisés en fonction du groupe d'offre et du statut de l'offre
			SQLCallExecuter.getInstance().executeSelectProcSansLimite(
				SELECT_STATUT_AUTORISE_PROC_NAME,
				new SQLParamDescriptorSet(),
				"MotifStatutRefDAO",
				"getAdminRefList",
				contexte.getContexteAppelDAO(),
				new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						String codeMotif = rs.getString("CODE_MOTIF_STATUT");
						MotifStatutRefBean motifStatut = (MotifStatutRefBean) tmTemp.get(codeMotif);

						if (motifStatut != null) {
							Iterator<String> it = motifStatut.getIdsGroupeOffre().iterator();
							String id = rs.getString("CODE_GROUPEOFFRE");
							String s = null;
							boolean existeDeja = false;
							while (it.hasNext()) {
								s = (String) it.next();
								if (s.equals(id)) {
									existeDeja = true;
									break;
								}
							}
							if (!existeDeja)
								motifStatut.getIdsGroupeOffre().add(id);

							it = motifStatut.getIdsStatut().iterator();
							id = rs.getString("STATUT_OFFRE");
							existeDeja = false;
							while (it.hasNext()) {
								s = (String) it.next();
								if (s.equals(id)) {
									existeDeja = true;
									break;
								}
							}
							if (!existeDeja)
								motifStatut.getIdsStatut().add(id);

							tmTemp.put(motifStatut.getId(), motifStatut);
						}

						return null;
					}
				}
			);

			List<MotifStatutRefBean> referenceDataList = new ArrayList<MotifStatutRefBean>(tmTemp.values());

			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), SELECT_STATUT_AUTORISE_PROC_NAME, "SELECT", "");
			LogCommun.debug("MotifStatutRefDAO", "getAdminRefList", "Taille resultat = " + referenceDataList.size());
			return referenceDataList;
		}
		catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	public int insertRef( Contexte contexte, RefBean bean ) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		try {
			int res = super.insertRef(contexte, bean);

			// On remplit la table des statuts autorisés
			MotifStatutRefBean temp = (MotifStatutRefBean) bean;
			insertMotifsAutorises(contexte, temp);

			return res;
		}
		catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	public int updateRef( Contexte contexte, RefBean bean ) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		try {
			int res = super.updateRef(contexte, bean);

			// On vide la table des statuts autorisés et on la re-remplit
			MotifStatutRefBean temp = (MotifStatutRefBean) bean;
			deleteMotifsAutorises(contexte, temp);
			insertMotifsAutorises(contexte, temp);

			return res;
		}
		catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	public int deleteRef( Contexte contexte, RefBean bean ) throws TechnicalException, IncoherenceException, ForeignKeyException {
		try {
			int res = super.deleteRef(contexte, bean);

			// On vide la table des statuts autorisés
			MotifStatutRefBean temp = (MotifStatutRefBean) bean;
			deleteMotifsAutorises(contexte, temp);

			return res;
		}
		catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * Méthodes privées
	 */
	private void insertMotifsAutorises( Contexte contexte, MotifStatutRefBean bean ) throws TechnicalException, SQLException {
		Iterator<String> itGroupesOffre = bean.getIdsGroupeOffre().iterator();
		Iterator<String> itStatuts = null;
		String idGroupeOffre = null;
		String idStatut = null;
		while (itGroupesOffre.hasNext()) {
			idGroupeOffre = (String) itGroupesOffre.next();
			itStatuts = bean.getIdsStatut().iterator();
			while (itStatuts.hasNext()) {
				idStatut = (String) itStatuts.next();
				SQLCallExecuter.getInstance().executeUpdate(
					INSERT_STATUT_AUTORISE_PROC_NAME,
					new SQLParamDescriptorSet(
						new SQLParamDescriptor [] {
							new SQLParamDescriptor(Integer.valueOf(bean.getId()), Types.INTEGER),
							new SQLParamDescriptor(idGroupeOffre, Types.VARCHAR),
							new SQLParamDescriptor(idStatut, Types.VARCHAR)
						}
					),
					"MotifStatutRefDAO",
					"updateMotifsAutorises",
					contexte.getContexteAppelDAO()
				);
			}
		}
	}

	private void deleteMotifsAutorises( Contexte contexte, MotifStatutRefBean bean ) throws TechnicalException, SQLException {
		SQLCallExecuter.getInstance().executeUpdate(
			DELETE_STATUT_AUTORISE_PROC_NAME,
			new SQLParamDescriptorSet(
				new SQLParamDescriptor[] {
					new SQLParamDescriptor(Integer.valueOf(bean.getId()), Types.INTEGER)
				}
			),
			"MotifStatutRefDAO",
			"deleteMotifsAutorises",
			contexte.getContexteAppelDAO()
		);
	}

}