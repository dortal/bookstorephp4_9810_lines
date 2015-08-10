package com.accor.asa.commun.persistance;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.process.PoolCommunFactory;
/**
 * Cette classe établit une connexion SQL (au constructeur) 
 * et execute la proc fournie en paramètre (excuteUpdate)
 * 
 * Elle traite en outre les erreurs SQLException et NamingException
 * et renseigne la valeur de retour du paramètre de sortie de la proc (facultatif)
 */
public class SQLCallExecuter extends DAO {

	private static SQLCallExecuter s_instance = new SQLCallExecuter();
	private boolean avecLimite = true;

	/** constructeur privé pour empêcher son utilisation ; utiliser getInstance()*/
	private SQLCallExecuter() {
	}

	/**
	 * Permet de récupérer l'unique instance de cette classe (singleton)
	 */
	public static SQLCallExecuter getInstance() {
		return s_instance;
	}
	
	/**
	 * récupère une connexion dans le pool weblogic (Si une transaction a déjà été commencée dans le Thread courant, 
	 * Weblogic renvoie automatiquement cette connexion, sinon Weblogic renvoie l'une des connexions disponibles dans le pool) 
	 */
	public Connection createTarifTARSNewConnection(String DAOName, String methodName, ContexteAppelDAO contexte) throws TechnicalException, SQLException {
		Connection cnt = null;
		Exception error = null;
		try {
            cnt = PoolCommunFactory.getInstance().getConnectionTarifTARS();
		} catch (SQLException e) {
			error = e;
			throw e;
		} catch (Exception e) {
			error = e;
			throw new TechnicalException(e);
		} finally {
			if (error != null) {
				LogCommun.major(contexte.getLogin(), DAOName, methodName, "Initialisation échouée :" + error.getMessage(), error);
			}
		}
		return cnt;
	}

	/**
	 * récupère une connexion dans le pool weblogic (Si une transaction a déjà été commencée dans le Thread courant, 
	 * Weblogic renvoie automatiquement cette connexion, sinon Weblogic renvoie l'une des connexions disponibles dans le pool) 
	 */
	public Connection createNewConnection(String DAOName, String methodName, ContexteAppelDAO contexte) throws TechnicalException, SQLException {
		Connection cnt = null;
		Exception error = null;
		try {
            cnt = PoolCommunFactory.getInstance().getConnection();
		} catch (SQLException e) {
			error = e;
			throw e;
		} catch (Exception e) {
			error = e;
			throw new TechnicalException(e);
		} finally {
			if (error != null) {
				LogCommun.major(contexte.getLogin(), DAOName, methodName, "Initialisation échouée :" + error.getMessage(), error);
			}
		}
		return cnt;
	}

	/**
	 * intialisation de la connexion independamment des objets à mettre à jour.
	 */
	private CallableStatement createNewStatement(Connection cnt, String procedure, String DAOName, String methodName, ContexteAppelDAO contexte)
		throws TechnicalException, SQLException {
		CallableStatement cs = null;
		Exception error = null;
		try {
			cs = cnt.prepareCall(procedure);
		}
		catch (SQLException e) {
			error = e;
			throw e;
		}
		finally {
			if (error != null) {
				LogCommun.major(contexte.getLogin(), DAOName, methodName, "Initialisation échouée :" + error.getMessage(), error);
			}
		}
		return cs;
	}

	public int executeUpdate(String procName, SQLParamDescriptorSet paramDescriptor, String DAOName, String methodName, ContexteAppelDAO contexte)
		throws TechnicalException, SQLException {
		Connection cnt = null;
		try {
			cnt = createNewConnection(DAOName, methodName, contexte);
			return executeUpdate(cnt, procName, paramDescriptor, DAOName, methodName, contexte);
		} finally {
			releaseConnection(cnt, null);
		}
	}

    /**
     * Execution d'une proc update sur une connexion ASAEXTRACTIONSPMS.
     * @param procName
     * @param paramDescriptor
     * @param DAOName
     * @param methodName
     * @param contexte
     * @return
     * @throws TechnicalException
     * @throws SQLException
     */
    public int executeUpdatePMS(String procName, SQLParamDescriptorSet paramDescriptor, String DAOName, String methodName, ContexteAppelDAO contexte)
        throws TechnicalException, SQLException {
        Connection cnt = null;
        try {
            cnt = PoolCommunFactory.getInstance().getConnectionExtractionPms();
            return executeUpdate(cnt, procName, paramDescriptor, DAOName, methodName, contexte);
        } catch (SQLException sqle) {
            LogCommun.major(contexte.getLogin(), DAOName, methodName, "erreur lors de l'execution de la proc : " + procName, sqle);
            throw new TechnicalException(sqle);
        } catch (Exception e) {
            LogCommun.major(contexte.getLogin(), DAOName, methodName, "erreur lors de l'execution de la proc : " + procName, e);
            throw new TechnicalException(e);
        } finally {
			releaseConnection(cnt, null);
		}
    }

    /**
     * Execution d'une proc avec un code retour.
     * @param procName
     * @param paramDescriptor
     * @param DAOName
     * @param methodName
     * @param contexte
     * @return
     * @throws TechnicalException
     * @throws SQLException
     */
    public int executeUpdateWithReturnCode(String procName, SQLParamDescriptorSet paramDescriptor, String DAOName, String methodName, ContexteAppelDAO contexte)
        throws TechnicalException, SQLException {
    	Connection cnt       = null;
		try {
            // Creation de la connection
            cnt = createNewConnection(DAOName, methodName, contexte);
			return executeUpdateWithReturnCode(cnt, procName, paramDescriptor, DAOName, methodName, contexte);
		} finally {
			releaseConnection(cnt, null);
		}
    }
    /**
     * Execution d'une proc sur une connexion ASAEXTRACTIONSPMS avec un code retour.
     * @param procName
     * @param paramDescriptor
     * @param DAOName
     * @param methodName
     * @param contexte
     * @return
     * @throws TechnicalException
     * @throws SQLException
     */
    public int executeUpdatePMSWithReturnCode(String procName, SQLParamDescriptorSet paramDescriptor, String DAOName, String methodName, ContexteAppelDAO contexte)
        throws TechnicalException, SQLException {
    	Connection cnt       = null;
		try {
            // Creation de la connection
            cnt = PoolCommunFactory.getInstance().getConnectionExtractionPms();
			return executeUpdateWithReturnCode(cnt, procName, paramDescriptor, DAOName, methodName, contexte);
        } catch (SQLException sqle) {
            LogCommun.major(contexte.getLogin(), DAOName, methodName, "erreur lors de l'execution de la proc : " + procName, sqle);
            throw new TechnicalException(sqle);
        } catch (Exception e) {
            LogCommun.major(contexte.getLogin(), DAOName, methodName, "erreur lors de l'execution de la proc : " + procName, e);
            throw new TechnicalException(e);
        } finally {
			releaseConnection(cnt, null);
		}
    }


    public int executeUpdateWithReturnCode(Connection conn, String procName, SQLParamDescriptorSet paramDescriptor, String DAOName, String methodName, ContexteAppelDAO contexte)
    	throws TechnicalException, SQLException {
        CallableStatement cs;

        // on crée la string d'appel de la procédure
        String procedure = createOutputableCallableProc(procName, paramDescriptor.size());
        // Log les infos
        LogCommun.debug(DAOName, methodName, new StringBuffer(procedure).
                                                append(" parameters: ").
                                                append(paramDescriptor).
                                                toString());
        // 1) initialisation de la connexion
        cs = createNewStatement(conn, procedure, DAOName, methodName, contexte);
        // 2) positionnement des parametres SQL sur le statement
        setProcParameters(paramDescriptor, cs);
        // 3) exécution avec fermeture de connexion
        executeSimpleUpdate(cs, DAOName, methodName, contexte);
        // 4) récupération des parametres OUT
        readOutProcParameters(paramDescriptor, cs);
        // 5) Retourne le code retour
        return cs.getInt(1);
    }


    /**
	 * utilisé dans le cadre de la mise à jour d'un seul objet.
	 * utilise un tableau de paramètres SQL correspondants à l'objet à modifier.
	 */
	public int executeUpdate( Connection cnt, String procName, SQLParamDescriptorSet paramDescriptor, 
			String DAOName, String methodName, ContexteAppelDAO contexte )throws TechnicalException, SQLException {

	    CallableStatement cs = null;
		try {
    	    // on crée la string d'appel de la procédure
    		String procedure = createUpdateCallableProc(procName, paramDescriptor.size());
    
    		LogCommun.debug(DAOName, methodName, procedure);
    		LogCommun.debug(DAOName, methodName, "parameters : " + paramDescriptor);
    
    		// 1) initialisation de la connexion
    		cs = createNewStatement(cnt, procedure, DAOName, methodName, contexte);
    
    		// 2) positionnement des parametres SQL sur le statement 
    		setProcParameters(paramDescriptor, cs);
    
    		// 3) exécution avec fermeture de connexion
    		int result = executeSimpleUpdate(cs, DAOName, methodName, contexte);
    
    		// 4) récupération des parametres OUT
    		readOutProcParameters(paramDescriptor, cs);
    
    		return result;
		} finally {
		  releaseConnection(null, cs);
	    }
	}

	/**
	 * Même fonctionnalité que la méthode executeSelectProc(), 
	 * mais on suppose ici que la proc renvoie un résultSet avec une seule ligne.
	 * executeSelectSingleObjetProc retourne donc un seul objet et non pas une liste.
	 * @return null si le resultSet renvoyé par la proc est vide 
	 */
	public Object executeSelectSingleObjetProc(
		String procName,
		SQLParamDescriptorSet paramDescriptor,
		String DAOName,
		String methodName,
		ContexteAppelDAO contexte,
		SQLResultSetReader reader)
		throws TechnicalException, SQLException {
		List<?> resultats = executeSelectProc(procName, paramDescriptor, DAOName, methodName, contexte, reader);
		if (resultats.isEmpty())
			return null;
		// fonctionnalité mappée du tronc commun pour limiter les changements : si il y a plusieurs lignes on ignore les premieres et on ramene la derniere
		else
			return resultats.get(resultats.size() - 1);
	}
	
	/**
	 * Même fonctionnalité que la méthode executeSelectProc(), 
	 * mais on s'affranchi de la limite à 700 lignes (pour cet appel uniquement, pas d'incidence sur les appels suivants).
	 */
	public List<?> executeTARSSelectProcSansLimite(
		String procName,
		SQLParamDescriptorSet paramDescriptor,
		String DAOName,
		String methodName,
		ContexteAppelDAO contexte,
		SQLResultSetReader reader)
		throws TechnicalException, SQLException {
		try {
			avecLimite = false;
			return executeTarifTARSSelectProc(procName, paramDescriptor, DAOName, methodName, contexte, reader);
		}
		finally {
			avecLimite = true;
		}
	}
	
	/**
	 * Même fonctionnalité que la méthode executeSelectProc(), 
	 * mais on s'affranchi de la limite à 700 lignes (pour cet appel uniquement, pas d'incidence sur les appels suivants).
	 */
	public List<?> executeSelectProcSansLimite(
		String procName,
		SQLParamDescriptorSet paramDescriptor,
		String DAOName,
		String methodName,
		ContexteAppelDAO contexte,
		SQLResultSetReader reader)
		throws TechnicalException, SQLException {
		try {
			avecLimite = false;
			return executeSelectProc(procName, paramDescriptor, DAOName, methodName, contexte, reader);
		}
		finally {
			avecLimite = true;
		}
	}

    /**
     * exécute une procédure de lecture (SELECT).
     * utilise un tableau de paramètres SQL correspondants aux critères de recherche et au resultset de retour.
     * @return une liste d'objets correspondant aux lignes du resultSet retourn par la proc.
     * Ces objets sont instancié avec la méthode instanciateFromLine du SQLResultSetReader passé en paramètre.
     */
	public List<?>  executeSelectProc( Connection connection, String procName, SQLParamDescriptorSet paramDescriptor, 
	    String DAOName, String methodName, ContexteAppelDAO contexte, SQLResultSetReader reader ) 
	    throws TechnicalException {
    
	  CallableStatement cs = null;

	  try {
        String procedure = createReadCallableProc(procName, paramDescriptor.size());
        if (LogCommun.isTechniqueDebug()) {
            LogCommun.debug(DAOName, methodName, procedure);
            LogCommun.debug(DAOName, methodName, "parameters : " + paramDescriptor);
        }
        cs = createNewStatement(connection, procedure, DAOName, methodName, contexte);
        // on a une limite à 700 lignes, comme pour le tronc commun
        if (avecLimite)
          setRowCount(connection, 700);
        setProcParameters(paramDescriptor, cs);
    
        ResultSet rs = cs.executeQuery();
        List<Object>  result = new ArrayList<Object>();
        while (rs.next()) {
          result.add(reader.instanciateFromLine(rs));
        }
        // inutile: LogCommun.debug(DAOName, methodName, "resultat:" + result);
        return result;

	  } catch (SQLException sqle) {
	    LogCommun.major(contexte.getLogin(), DAOName, methodName, "erreur lors de l'execution de la proc : " + procName, sqle);
	    throw new TechnicalException(sqle);
	  } finally {
	    try {
	      if (connection != null)
	        setRowCount(connection, 0);
	    } catch (SQLException sqle) {
	      LogCommun.major(contexte.getLogin(), "SQLCallExecuter", "executeSelectProc", "erreur lors de la remis a 0 du rowcount", sqle);
	      throw new TechnicalException(sqle);
	    } finally { // ANO 4055
	        releaseConnection(null, cs);
	    }
      }
	}
  
	public List<?>  executeSelectProc(String procName, SQLParamDescriptorSet paramDescriptor, String DAOName, String methodName, ContexteAppelDAO contexte, SQLResultSetReader reader)
		throws TechnicalException, SQLException {
		Connection connection = null;
		try {
			connection = createNewConnection(DAOName, methodName, contexte);
			return executeSelectProc( connection, procName, paramDescriptor, DAOName, methodName, contexte, reader);
		} catch (SQLException sqle) {
			LogCommun.major(contexte.getLogin(), DAOName, methodName, "erreur lors de l'execution de la proc : " + procName, sqle);
			throw new TechnicalException(sqle);
		} finally {
			releaseConnection(connection, null);
		}
	}

	public List<?> executeTarifTARSSelectProc(String procName, SQLParamDescriptorSet paramDescriptor, String DAOName, String methodName, ContexteAppelDAO contexte, SQLResultSetReader reader)
	throws TechnicalException, SQLException {
	Connection connection = null;
	try {
		connection = createTarifTARSNewConnection(DAOName, methodName, contexte);
		return executeSelectProc( connection, procName, paramDescriptor, DAOName, methodName, contexte, reader);
	} catch (SQLException sqle) {
		LogCommun.major(contexte.getLogin(), DAOName, methodName, "erreur lors de l'execution de la proc : " + procName, sqle);
		throw new TechnicalException(sqle);
	} finally {
		releaseConnection(connection, null);
	}
}
	
	// TECH gestion des multiple RS
	public List<?>[] executeMultipleSelectProc( Connection connection, String procName, SQLParamDescriptorSet paramDescriptor, 
		    String DAOName, String methodName, ContexteAppelDAO contexte, SQLResultSetReader[] readers, boolean withReturnValue ) 
		    throws TechnicalException {
	    
		  CallableStatement cs = null;

		  try {
		    List<?>[] results = new List<?>[readers.length];  
	        String procedure = null; 
	            if (withReturnValue) {
	                procedure = createOutputableCallableProc(procName,paramDescriptor.size());
	            } else {
	                procedure = createReadCallableProc(procName, paramDescriptor.size());
	            }
	        
	        if (LogCommun.isTechniqueDebug()) {
	            LogCommun.debug(DAOName, methodName, procedure);
	            LogCommun.debug(DAOName, methodName, "parameters : " + paramDescriptor);
	        }
	        cs = createNewStatement(connection, procedure, DAOName, methodName, contexte);
	        // on a une limite à 700 lignes, comme pour le tronc commun
	        if (avecLimite)
	          setRowCount(connection, 700);
	        setProcParameters(paramDescriptor, cs);
	    
	        
	        
	        boolean moreRes = cs.execute();
	        int nbUpdate = cs.getUpdateCount();
	        int nbRS = 0;
	        
	        for( int i=0; moreRes || ( nbUpdate != -1 ); 
	             moreRes = cs.getMoreResults(), nbUpdate = cs.getUpdateCount() ) {
	          if( moreRes ) {
	            ResultSet rs= cs.getResultSet();
	            nbRS++;
	            List<Object> result = new ArrayList<Object>();
		        while (rs.next()) {
		          result.add(readers[i].instanciateFromLine(rs));
		        }
		        results[i]=result;
	            i++ ;
	          }
	        }
	        
	        readOutProcParameters(paramDescriptor, cs);
	        // inutile: LogCommun.debug(DAOName, methodName, "resultat:" + result);
	        return results;

		  } catch (SQLException sqle) {
		    LogCommun.major(contexte.getLogin(), DAOName, methodName, "erreur lors de l'execution de la proc : " + procName, sqle);
		    throw new TechnicalException(sqle);
		  } finally {
		    try {
		      if (connection != null)
		        setRowCount(connection, 0);
		    } catch (SQLException sqle) {
		      LogCommun.major(contexte.getLogin(), "SQLCallExecuter", "executeSelectProc", "erreur lors de la remis a 0 du rowcount", sqle);
		      throw new TechnicalException(sqle);
		    } finally {
		    	releaseConnection(null, cs);
		    }
	      }
		}

	public List<?>[] executeMultipleSelectProc(String procName, SQLParamDescriptorSet paramDescriptor, String DAOName, String methodName, 
	        ContexteAppelDAO contexte, SQLResultSetReader[] readers, boolean withReturnValue)
	throws TechnicalException, SQLException {
	Connection connection = null;
	try {
		connection = createNewConnection(DAOName, methodName, contexte);
		return executeMultipleSelectProc( connection, procName, paramDescriptor, DAOName, methodName, contexte, readers,withReturnValue);
	} catch (SQLException sqle) {
		LogCommun.major(contexte.getLogin(), DAOName, methodName, "erreur lors de l'execution de la proc : " + procName, sqle);
		throw new TechnicalException(sqle);
	} finally {
		releaseConnection(connection, null);
	}
}
    /**
     * Exécute une procédure de lecture (SELECT) sur une connexion ASAEXTRACTIONSPMS
     * utilise un tableau de paramètres SQL correspondants aux critères de recherche et au resultset de retour.
     * @param procName
     * @param paramDescriptor
     * @param DAOName
     * @param methodName
     * @param contexte
     * @param reader
     * @return
     * @throws TechnicalException
     * @throws SQLException
     */
    public List<?> executeSelectProcExtractionPMS(String procName, SQLParamDescriptorSet paramDescriptor, String DAOName, String methodName, ContexteAppelDAO contexte, SQLResultSetReader reader)
        throws TechnicalException, SQLException {
        Connection connection = null;
        try {
            connection = PoolCommunFactory.getInstance().getConnectionExtractionPms();
            return executeSelectProc( connection, procName, paramDescriptor, DAOName, methodName, contexte, reader);
        } catch (SQLException sqle) {
            LogCommun.major(contexte.getLogin(), DAOName, methodName, "erreur lors de l'execution de la proc : " + procName, sqle);
            throw new TechnicalException(sqle);
        } catch (Exception e) {
            LogCommun.major(contexte.getLogin(), DAOName, methodName, "erreur lors de l'execution de la proc : " + procName, e);
            throw new TechnicalException(e);
        } finally {
            releaseConnection(connection, null);
        }
    }


    /**
	 * utilisé dans le cadre de la mise à jour d'une liste d'objets. 
	 * La connexion n'est fermée qu'après la mise à jour de tous les objets.
	 * utilise un tableau de tableau de paramètres SQL, chaque tableau correspondant 
	 * aux paramètres d'un objet. 
	 */
	public void executeListeUpdate(String procName, SQLParamDescriptorSet[] paramDescriptor, String DAOName, String methodName, ContexteAppelDAO contexte)
		throws TechnicalException, SQLException {

		if (paramDescriptor == null || paramDescriptor.length == 0)
			return;

		Connection cnt = null;
		CallableStatement cs = null;
		try {
			// on crée la string d'appel de la procédure
			String procedure = createUpdateCallableProc(procName, paramDescriptor[0].size());

			LogCommun.debug(DAOName, methodName, procedure);

			// 1) initialisation de la connexion
			cnt = createNewConnection(DAOName, methodName, contexte);
			cs = createNewStatement(cnt, procedure, DAOName, methodName, contexte);

			int nbAppels = paramDescriptor.length;
			for (int i = 0; i < nbAppels; i++) {
				LogCommun.debug(DAOName, methodName, "executing with parameters : " + paramDescriptor[i]);
				// 2) positionnement des parametres SQL sur le statement
				setProcParameters(paramDescriptor[i], cs);
				// 3) exécution sans fermeture de connexion
				executeSimpleUpdate(cs, DAOName, methodName, contexte);
				// 4) récupération des parametres OUT
				readOutProcParameters(paramDescriptor[i], cs);
			}
		} finally {
			// 5) fermeture de la connexion
			releaseConnection(cnt, cs);
		}
	}

	private int executeSimpleUpdate(CallableStatement cs, String DAOName, String methodName, ContexteAppelDAO contexte) throws SQLException {
		int returnUpdateValue = -1;
		try {
			returnUpdateValue = cs.executeUpdate();
			return returnUpdateValue;
		}
		catch (SQLException e) {
			LogCommun.info(contexte.getLogin(), DAOName, methodName, e.getMessage());
			throw e;
		}
	}

	private void readOutProcParameters(SQLParamDescriptorSet paramDescriptor, CallableStatement cs) throws SQLException {
		int nb = paramDescriptor.size();
		for (int i = 0; i < nb; i++) {
			SQLParamDescriptor param = paramDescriptor.getParameter(i);
			int indexSql = i + 1; // les index SQL commencent à 1
			if (param.isReturn()) {
				Object result = cs.getObject(indexSql);
				if (result instanceof BigDecimal)
					result = new Long(((BigDecimal) result).longValue());
				param.setValue(result);
			}
		}
	}

	/**
	 * Cette méthode parcourt le tableau d'objets de type SQLParamDescriptor, 
	 * determine le type de du paramètre SQL et en fonction de celui-ci appelle le
	 * setter approprié sur l'objet CallableStatement
	 */
	private void setProcParameters(SQLParamDescriptorSet paramDescriptor, CallableStatement cs) throws SQLException {
		int nb = paramDescriptor.size();
		for (int i = 0; i < nb; i++) {
			SQLParamDescriptor param = paramDescriptor.getParameter(i);
			int indexSql = i + 1; // les index SQL commencent à 1
			int typeParam = param.getSqlType();

			Object value = param.getValue();
			if (value == null) {
				if (typeParam == -1)
					throw new UnsupportedOperationException("Le type n'est pas renseigné et la valeur est NULL (paramètre numéro " + indexSql + ")");
				// traitement specifique au paramètre de retour
				if (param.isReturn())
					cs.registerOutParameter(indexSql, typeParam);
				else
					cs.setNull(indexSql, typeParam);
			}
			else {
				// conversion spéciale pour les "asaDate"
				if (value instanceof AsaDate) {
					java.util.Date date = ((AsaDate) value).getDate();
					java.sql.Date dateSQL = new java.sql.Date(date.getTime());
					value = dateSQL;
				}
				// !! ne fonctionnera pas pour les conversions non implicites!!
				if (typeParam == -1) {
					cs.setObject(indexSql, value);
				}
				else {
				    if (value instanceof Boolean) {
				        cs.setBoolean(indexSql,((Boolean)value).booleanValue()); // EVO 4267
				    } else {
				        cs.setObject(indexSql, value, typeParam);
				    }
				}
				// traitement specifique au paramètre de retour
				if (param.isReturn())
					cs.registerOutParameter(indexSql, typeParam);
			}
		}
	}

	/**
	 * Cette methode crée une string du type :
	 * "{call [nom proc] (?,?,?,...)} en fonction du nom de la proc et du 
	 * nombre de paramètres.
	 */
	private String createUpdateCallableProc(String procName, int nbParams) {
		return "{ call " + createCallableProcBody(procName, nbParams) + "}";
	}

	private String createOutputableCallableProc(String procName, int nbParams) {
		return "{ ? = call " + createCallableProcBody(procName, nbParams-1) + "}";
	}
	
	/**
	 * Cette methode crée une string du type :
	 * "exec [nom proc] (?,?,?,...) en fonction du nom de la proc et du 
	 * nombre de paramètres. 
	 * La proc appelée doit retourner un resultset.
	 */
	private String createReadCallableProc(String procName, int nbParams) {
		return "exec " + createCallableProcBody(procName, nbParams);
	}

	private String createCallableProcBody(String procName, int nbParams) {
		StringBuffer proc = new StringBuffer();
		proc.append(procName);
		proc.append(" (");
		for (int i = 0; i < nbParams; i++) {
			if (i > 0)
				proc.append(",");
			proc.append("?");
		}
        proc.append(" )");
        return proc.toString();
	}
}
