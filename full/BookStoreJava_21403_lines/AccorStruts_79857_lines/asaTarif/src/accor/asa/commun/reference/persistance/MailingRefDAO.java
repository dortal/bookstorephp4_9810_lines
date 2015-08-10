package com.accor.asa.commun.reference.persistance;

import java.sql.CallableStatement;
import java.sql.Connection;
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
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.MailingRefBean;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.vente.contacts.metier.ListMailings;

public class MailingRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = null;
	private static final String ADMIN_SELECT_PROC_NAME = "vente_DRef_select_mailing";
	private static final String INSERT_PROC_NAME = "vente_DRef_insert_mailing";
	private static final String INSERT_PAYS_PROC_NAME = "vente_DRef_insert_mailing_pays";
	private static final String UPDATE_PROC_NAME = "vente_DRef_insert_mailing";
	private static final String DELETE_PROC_NAME = "vente_DRef_delete_mailing";

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
				// Pas utilisé, méthode redéfinie
				return null;
			case INSERT :
				// Pas utilisé, méthode redéfinie
				return null;
			case UPDATE :
				// Pas utilisé, méthode redéfinie
				return null;
			case DELETE :
				// Pas besoin de redéfinir deleteRefData() car on n'a pas besoin de récupérer le code retour
				// (on peut supprimer un mailing même s'il est lié à un contact)
				MailingRefBean temp = (MailingRefBean) bean;
				SQLParamDescriptor [] params = {
					new SQLParamDescriptor(Integer.valueOf(temp.getId())),
					new SQLParamDescriptor(null, false, Types.VARCHAR)
				};
				return new SQLParamDescriptorSet(params);
			default :
				return null;
		}
	}

	protected SQLResultSetReader getProcReader (int type) {
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				// Pas utilisé, méthode redéfinie
				return null;
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return ListMailings.class.getName();
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
	public List<MailingRefBean> getAdminRefList( Contexte contexte, RefBean bean ) throws TechnicalException {
		try {
			final TreeMap<String, MailingRefBean> temp = new TreeMap<String, MailingRefBean>();

			String procName = ADMIN_SELECT_PROC_NAME;
			SQLCallExecuter.getInstance().executeSelectProc(
				procName,
				new SQLParamDescriptorSet(),
				"MailingRefDAO",
				"getAdminRefList",
				contexte.getContexteAppelDAO(),
				new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						String id = rs.getString("CODE");
						String libelle = rs.getString("LIBELLE_MAILINGS");
						String idPays = rs.getString("CODE_PAYS");
						String actif = rs.getString("SUPPRIMER");

						// Pour trier par libellé
						MailingRefBean bean = temp.get(libelle.toLowerCase() + id);

						if (bean == null) {
							bean = new MailingRefBean();
							bean.setId(id);
							bean.setLibelle(libelle);
							bean.setActif(actif.equals("0"));
						}
						bean.addPays(idPays);

						temp.put(bean.getLibelle().toLowerCase() + bean.getId(), bean);

						// Et parce qu'il faut bien retourner quelque chose...
						return null;
					}
				});
			List<MailingRefBean> refDataList = new ArrayList<MailingRefBean>(temp.values());

			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "SELECT", "");
			LogCommun.debug("MailingRefDAO", "getAdminRefList", "Taille resultat = " + refDataList.size());
			return refDataList;
		}
		catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	public int insertRef( Contexte contexte, RefBean bean ) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		MailingRefBean temp = (MailingRefBean) bean;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = PoolCommunFactory.getInstance().getConnection();
			statement = connection.prepareCall("{ ? = call " + INSERT_PROC_NAME + " (?, ?, ?, ?) }");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setInt(2, Integer.parseInt(temp.getId()));
			statement.setString(3, temp.getLibelle());
			statement.setInt(4, Integer.parseInt("0"));
			statement.setString(5, IS_NEW);
			statement.executeUpdate();
			int res = statement.getInt(1);

			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), INSERT_PROC_NAME, "INSERT", bean.toString());
			LogCommun.debug("MailingRefDAO", "insertRef", "Code retour = " + res);

			Iterator<String> it = temp.getIdsPays().iterator();
			while (it.hasNext()) {
				String idPays = it.next();
				insertPaysMailing(contexte, temp.getId(), idPays);
			}

			return res;
		}
		catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
				throw new DuplicateKeyException(e);
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
			releaseConnection(connection, statement);
		}
	}

	public int updateRef( Contexte contexte, RefBean bean ) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		MailingRefBean temp = (MailingRefBean) bean;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = PoolCommunFactory.getInstance().getConnection();

			// Mise à jour du nouveau mailing
			statement = connection.prepareCall("{ ? = call " + UPDATE_PROC_NAME + " (?, ?, ?, ?) }");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setInt(2, Integer.parseInt(temp.getId()));
			statement.setString(3, temp.getLibelle());
			statement.setInt(4, Integer.parseInt(temp.getOldId()));
			statement.setString(5, IS_NOT_NEW);
			statement.executeUpdate();
			int res = statement.getInt(1);

			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), UPDATE_PROC_NAME, "UPDATE", bean.toString());
			LogCommun.debug("MailingRefDAO", "updateRef", "Code retour = " + res);

			if (res == UPDATE_ERROR) {
				throw new ForeignKeyException();
			}
			// Si tout s'est bien passé
			else {
				// Suppression des pays de l'ancien mailing
				MailingRefBean oldMailing = new MailingRefBean();
				oldMailing.setId(temp.getOldId());
				deleteRef(contexte, oldMailing);

				// Insertion des pays du nouveau mailing
				Iterator<String> it = temp.getIdsPays().iterator();
				while (it.hasNext()) {
					insertPaysMailing(contexte, temp.getId(), it.next());
				}
			}

			return res;
		}
		catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
				throw new DuplicateKeyException(e);
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
			releaseConnection(connection, statement);
		}
	}

	/**
	 * Cette méthode lie un mailing à un pays
	 * @param context Le contexte DAO
	 * @param idMailing L'identifiant du mailing à insérer
	 * @param idPays L'identifiant du pays à insérer
	 * @throws SQLException
	 * @throws TechnicalException
	 */
	private void insertPaysMailing( Contexte contexte, String idMailing, String idPays ) throws SQLException, TechnicalException {
		SQLParamDescriptor [] params = new SQLParamDescriptor[2];
		params[0] = new SQLParamDescriptor(Integer.valueOf(idMailing));
		params[1] = new SQLParamDescriptor(idPays);

		int res = SQLCallExecuter.getInstance().executeUpdate(
			INSERT_PAYS_PROC_NAME,
			new SQLParamDescriptorSet(params),
			"MailingRefDAO",
			"insertPaysMailing",
			contexte.getContexteAppelDAO());

		LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), INSERT_PAYS_PROC_NAME, "INSERT", idMailing + "|" + idPays);
		LogCommun.debug("MailingRefDAO", "insertPaysMailing", "Code retour = " + res);
	}

}