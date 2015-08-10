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
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.TypePrestationRefBean;
import com.accor.asa.vente.dossier.metier.MapTypesPrestationsParGroupe;

public class TypePrestationRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = null;
	private static final String ADMIN_SELECT_PROC_NAME = "vente_Dref_Select_Prestations";
	private static final String INSERT_PROC_NAME = "vente_DRef_insert_prestation";
	private static final String UPDATE_PROC_NAME = "vente_DRef_insert_prestation";
	private static final String DELETE_PROC_NAME = "vente_DRef_delete_prestation";

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
		TypePrestationRefBean temp = (TypePrestationRefBean) bean;
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
					 new SQLParamDescriptor(temp.getLibelle()),
					 new SQLParamDescriptor(Integer.valueOf(temp.getIdGroupePrestation())),
					 new SQLParamDescriptor(Integer.valueOf("0")),
					 new SQLParamDescriptor(IS_NEW),
					 new SQLParamDescriptor(Boolean.valueOf(temp.getAffaire())),
					 new SQLParamDescriptor(Boolean.valueOf(temp.getLoisir()))
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
						TypePrestationRefBean bean = new TypePrestationRefBean();
						bean.setId(rs.getString("CODE"));
						bean.setLibelle(rs.getString("LIBELLE_TYPE_PRESTATION"));
						bean.setIdGroupePrestation(rs.getString("CODE_GRP_PRESTA"));
						bean.setAffaire(rs.getBoolean("ISAFFAIRE"));
						bean.setLoisir(rs.getBoolean("ISLOISIR"));
						bean.setActif(rs.getString("SUPPRIMER").equals("0"));
						return bean;
					}
				};
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return MapTypesPrestationsParGroupe.class.getName();
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
		TypePrestationRefBean temp = (TypePrestationRefBean) bean;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = PoolCommunFactory.getInstance().getConnection();
			statement = connection.prepareCall("{ ? = call " + UPDATE_PROC_NAME + " (?, ?, ?, ?, ?, ?, ?) }");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setInt(2, Integer.parseInt(temp.getId()));
			statement.setString(3, temp.getLibelle());
			statement.setInt(4, Integer.parseInt(temp.getIdGroupePrestation()));
			statement.setInt(5, Integer.parseInt(temp.getOldId()));
			statement.setString(6, IS_NOT_NEW);
			statement.setBoolean(7, temp.getAffaire());
			statement.setBoolean(8, temp.getLoisir());
			statement.executeUpdate();
			int res = statement.getInt(1);
			
			LogCommun.traceFonctionnelle(context.getCodeUtilisateur(), UPDATE_PROC_NAME, "UPDATE", bean.toString());
			LogCommun.debug("TypePrestationRefDAO", "updateRef", "Code retour = " + res);

			if (res == UPDATE_ERROR) {
				throw new ForeignKeyException();
			}
			
			return res;
		}
		catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
				throw new DuplicateKeyException(e);
			}
			else if (e.getErrorCode() == SYBASE_INTEGRITY_CONSTRAINT_ERROR_CODE) {
				throw new IncoherenceException(e);
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
		TypePrestationRefBean temp = (TypePrestationRefBean) bean;
		Connection connection = null;
		CallableStatement statement = null;
		try {
			connection = PoolCommunFactory.getInstance().getConnection();
			statement = connection.prepareCall("{ ? = call " + DELETE_PROC_NAME + " (?) }");
			statement.registerOutParameter(1, java.sql.Types.INTEGER);
			statement.setInt(2, Integer.parseInt(temp.getId()));
			statement.executeUpdate();
			int res = statement.getInt(1);

			LogCommun.traceFonctionnelle(context.getCodeUtilisateur(), DELETE_PROC_NAME, "DELETE", bean.toString());
			LogCommun.debug("TypePrestationRefDAO", "deleteRef", "Code retour = " + res);

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