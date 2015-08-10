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
import com.accor.asa.commun.metier.donneesdereference.ListCibleCommerciales;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.CibleCommercialeRefBean;
import com.accor.asa.commun.reference.metier.RefBean;


public class CibleCommercialeRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = null;
	private static final String ADMIN_SELECT_PROC_NAME = "vente_Select_Ciblecommerciales";
	private static final String INSERT_PROC_NAME = "vente_DRef_insert_Cible";
	private static final String UPDATE_PROC_NAME = "vente_DRef_insert_Cible";
	private static final String DELETE_PROC_NAME = "vente_DRef_delete_Cible";

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
		CibleCommercialeRefBean temp = (CibleCommercialeRefBean) bean;
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				SQLParamDescriptor [] adminSelectParams = {
					new SQLParamDescriptor(ADMIN_LANGUAGE),
					new SQLParamDescriptor(new Boolean(true))
				};
				return new SQLParamDescriptorSet(adminSelectParams);
			case INSERT :
				SQLParamDescriptor [] insertParams = {
					new SQLParamDescriptor(temp.getId()),
					new SQLParamDescriptor(Integer.valueOf(temp.getIdMarche())),
					new SQLParamDescriptor(temp.getLibelle()),
					new SQLParamDescriptor(temp.getIdCompte()),
					new SQLParamDescriptor("0"),
					new SQLParamDescriptor(IS_NEW)
				};
				return new SQLParamDescriptorSet(insertParams);
			case UPDATE :
				// Pas utilisé, méthode redéfinie
				return null;
			case DELETE :
				// Pas utilisé, méthode redéfinie
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
						CibleCommercialeRefBean bean = new CibleCommercialeRefBean();
						bean.setId(rs.getString("ID_CIBLE"));
						bean.setLibelle(rs.getString("LIBELLE_CIBLE"));
						bean.setIdMarche(String.valueOf(rs.getInt("ID_MARCHE")));
						bean.setIdCompte(rs.getString("TYPE_COMPTE"));
						bean.setActif(rs.getString("SUPPRIMER").equals("0"));
						return bean;
					}
				};
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return ListCibleCommerciales.class.getName();
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
	public int updateRef (Contexte context, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		CibleCommercialeRefBean temp = (CibleCommercialeRefBean) bean;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = PoolCommunFactory.getInstance().getConnection();
			statement = connection.prepareCall("{ ? = call " + UPDATE_PROC_NAME + " (?, ?, ?, ?, ?, ?) }");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setString(2, temp.getId());
			statement.setInt(3, Integer.parseInt(temp.getIdMarche()));
			statement.setString(4, temp.getLibelle());
			statement.setString(5, temp.getIdCompte());
			statement.setString(6, temp.getOldId());
			statement.setString(7, IS_NOT_NEW);
			statement.executeUpdate();
			int res = statement.getInt(1);

			LogCommun.traceFonctionnelle(context.getCodeUtilisateur(), UPDATE_PROC_NAME, "UPDATE", temp.toString());
			LogCommun.debug("CibleCommercialeRefDAO", "updateRef", "Code retour = " + res);

			if (res == UPDATE_ERROR) {
				throw new ForeignKeyException();
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
			releaseConnection(context.getContexteAppelDAO(), connection, statement);
		}
	}

	public int deleteRef (Contexte context, RefBean bean) throws TechnicalException, IncoherenceException, ForeignKeyException {
		CibleCommercialeRefBean temp = (CibleCommercialeRefBean) bean;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = PoolCommunFactory.getInstance().getConnection();
			statement = connection.prepareCall("{ ? = call " + DELETE_PROC_NAME + " (?) }");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setString(2, temp.getId());
			statement.executeUpdate();
			int res = statement.getInt(1);

			LogCommun.traceFonctionnelle(context.getCodeUtilisateur(), DELETE_PROC_NAME, "DELETE", temp.toString());
			LogCommun.debug("CibleCommercialeRefDAO", "deleteRef", "Code retour = " + res);

			if (res == DELETE_ERROR) {
				throw new ForeignKeyException();
			}
			return res;
		}
		catch (ForeignKeyException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TechnicalException(e);
		}
		finally {
			releaseConnection(context.getContexteAppelDAO(), connection, statement);
		}
	}

}