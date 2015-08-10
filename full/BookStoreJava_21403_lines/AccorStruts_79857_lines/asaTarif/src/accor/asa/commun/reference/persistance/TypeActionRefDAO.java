package com.accor.asa.commun.reference.persistance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.TypeActionRefBean;
import com.accor.asa.vente.action.metier.ListTypeAction;

public class TypeActionRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = null;
	private static final String ADMIN_SELECT_PROC_NAME = "vente_sel_type_action_all";
	private static final String INSERT_PROC_NAME = "vente_add_type_action";
	private static final String UPDATE_PROC_NAME = "vente_upd_type_action_libelle";
	private static final String DELETE_PROC_NAME = null;

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
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				return new SQLParamDescriptorSet();
			case INSERT :
				// Pas utilisé, méthode redéfinie
				return null;
			case UPDATE :
				// Pas utilisé, méthode redéfinie
				return null;
			case DELETE :
				// Pas utilisé tout court
				return null;
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
						TypeActionRefBean bean = new TypeActionRefBean();
						bean.setId(String.valueOf(rs.getInt("code")));
						bean.setLibelle(rs.getString("libelle"));
						bean.setActif(!rs.getBoolean("flag_delete"));
						bean.setDispoCompte(rs.getBoolean("dispoCompte"));
						bean.setDefautCompte(rs.getBoolean("defautCompte"));
						bean.setDispoContact(rs.getBoolean("dispoContact"));
						bean.setDefautContact(rs.getBoolean("defautContact"));
						bean.setDispoOffre(rs.getBoolean("dispoOffre"));
						bean.setDefautOffre(rs.getBoolean("defautOffre"));
						bean.setDispoDossier(rs.getBoolean("dispoDossier"));
						bean.setDefautDossier(rs.getBoolean("defautDossier"));
						return bean;
					}
				};
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return ListTypeAction.class.getName();
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
	public int insertRef (Contexte context, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		TypeActionRefBean temp = (TypeActionRefBean) bean;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = PoolCommunFactory.getInstance().getConnection();
			statement = connection.prepareCall("{ ? = call " + INSERT_PROC_NAME + " (?) }");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setString(2, temp.getLibelle());
			statement.executeUpdate();
			int id = statement.getInt(1);

			LogCommun.traceFonctionnelle(context.getCodeUtilisateur(), INSERT_PROC_NAME, "INSERT", bean.toString());
			LogCommun.debug("TypeActionRefDAO", "insertRef", "Nouvel ID = " + id);

			updateActivationTypeAction(context, connection, id, temp.isActif());
			updateTypeAction(context, connection, "vente_upd_type_action_compte", id, temp.getDispoCompte(), temp.getDefautCompte());
			updateTypeAction(context, connection, "vente_upd_type_action_contact", id, temp.getDispoContact(), temp.getDefautContact());
			updateTypeAction(context, connection, "vente_upd_type_action_offre", id, temp.getDispoOffre(), temp.getDefautOffre());
			updateTypeAction(context, connection, "vente_upd_type_action_dossier", id, temp.getDispoDossier(), temp.getDefautDossier());

			return id;
		}
		catch (TechnicalException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TechnicalException(e);
		}
		finally {
			releaseConnection(context.getContexteAppelDAO(), connection, statement);
		}
	}

	public int updateRef (Contexte context, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		TypeActionRefBean temp = (TypeActionRefBean) bean;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = PoolCommunFactory.getInstance().getConnection();
			statement = connection.prepareCall("{ ? = call " + UPDATE_PROC_NAME + " (?, ?) }");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setInt(2, Integer.parseInt(temp.getId()));
			statement.setString(3, temp.getLibelle());
			statement.executeUpdate();
			int res = statement.getInt(1);

			LogCommun.traceFonctionnelle(context.getCodeUtilisateur(), UPDATE_PROC_NAME, "UPDATE", bean.toString());
			LogCommun.debug("TypeActionRefDAO", "updateRef", "Code retour = " + temp.getId());

			updateActivationTypeAction(context, connection, Integer.parseInt(temp.getId()), temp.isActif());
			updateTypeAction(context, connection, "vente_upd_type_action_compte", Integer.parseInt(temp.getId()), temp.getDispoCompte(), temp.getDefautCompte());
			updateTypeAction(context, connection, "vente_upd_type_action_contact", Integer.parseInt(temp.getId()), temp.getDispoContact(), temp.getDefautContact());
			updateTypeAction(context, connection, "vente_upd_type_action_offre", Integer.parseInt(temp.getId()), temp.getDispoOffre(), temp.getDefautOffre());
			updateTypeAction(context, connection, "vente_upd_type_action_dossier", Integer.parseInt(temp.getId()), temp.getDispoDossier(), temp.getDefautDossier());

			return res;
		}
		catch (TechnicalException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TechnicalException(e);
		}
		finally {
			releaseConnection(context.getContexteAppelDAO(), connection, statement);
		}
	}

	/**
	 * Méthodes privées
	 */
	private void updateActivationTypeAction (Contexte context, final Connection cnt, final int id, boolean actif) throws TechnicalException {
		CallableStatement cs = null;
		try {
			cs = cnt.prepareCall("{ call vente_activation_type_action (?, ?) }");
			cs.setInt(1, id);
			cs.setBoolean(2, actif);
			int retour = cs.executeUpdate();
			if (retour != 1) {
				throw new TechnicalException("Error while activating type action");
			}
			LogCommun.traceFonctionnelle(context.getCodeUtilisateur(), "vente_activation_type_action", "UPDATE", Integer.toString(id));
			LogCommun.debug("TypeActionRefDAO", "updateActivationCycleAction", "Code retour = " + retour);
		}
		catch (TechnicalException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new TechnicalException(ex);
		}
	}

	private void updateTypeAction (final Contexte context, final Connection cnt, String nomProc, final int id, final boolean estDispo, final boolean estParDefaut) throws TechnicalException {
		CallableStatement cs = null;
		try {
			cs = cnt.prepareCall("{ call " + nomProc + " (?, ?, ?) }");
			cs.setInt(1, id);
			cs.setBoolean(2, estDispo);
			cs.setBoolean(3, estParDefaut);
			int retour = cs.executeUpdate();
			if (retour != 1) {
				throw new TechnicalException("Error while updating type action");
			}
			LogCommun.traceFonctionnelle(context.getCodeUtilisateur(), nomProc, "UPDATE", Integer.toString(id));
			LogCommun.debug("TypeActionRefDAO", "updateCycleAction", "Code retour = " + retour);
		}
		catch (TechnicalException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new TechnicalException(ex);
		}
	}

}