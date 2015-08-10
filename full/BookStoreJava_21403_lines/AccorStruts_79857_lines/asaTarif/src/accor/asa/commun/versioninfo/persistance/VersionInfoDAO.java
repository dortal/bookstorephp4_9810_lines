package com.accor.asa.commun.versioninfo.persistance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.versioninfo.metier.DatabaseVersionInformation;
import com.accor.asa.commun.versioninfo.presentation.VersionInfoHTMLHelper;

/**
  * DAO pour la recherche d'informations sur la version de la base et de l'application
  * utilisée pour la JSP d'affichage "about"
  *
  * Pour les procédures passées par le tronc commun, l'AIP correspondant est celui de Vente
  * (Component "Utiles")
  *
  * @author Vincent GAUTIER
  */
public class VersionInfoDAO extends DAO {

    public static final String VERSION_NAME = "3.1.1";
    private static final String PROC_NAME = "proc_meta_get_dbinfo";
    private static final String CLASSNAME = "VersionInfoDAO";
    /**
      * test par JDBC (pool weblogic) 
      @param codePool (voir cette classe) constante définissant le pool (asa, report, ...)
      */
    public DatabaseVersionInformation getDBInfoForPool(ContexteAppelDAO ctx, int codePool) 
        	throws TechnicalException {
        DatabaseVersionInformation ret = null;
        String methodName = "getDBInfoForPool";
        java.util.Date dateDebutMethode = logOptimDebutMethode(methodName);
        if( LogCommun.isTechniqueDebug() ) {
            LogCommun.debug(ctx.getLogin(), CLASSNAME, methodName);
        }

        // récupération de la connexion
        Connection cnt = null;
        CallableStatement stmt = null;
        try {
            switch (codePool) {
                case VersionInfoHTMLHelper.INFO_POOL_ASA :
                    cnt = PoolCommunFactory.getInstance().getConnection();
                    break;                    
                case VersionInfoHTMLHelper.INFO_POOL_QBE :
                    cnt = PoolCommunFactory.getInstance().getConnectionQBE();
                    break;                    
                case VersionInfoHTMLHelper.INFO_POOL_REPORT :
                    cnt = PoolCommunFactory.getInstance().getConnectionReporting();
                    break;                    
                case VersionInfoHTMLHelper.INFO_POOL_PMS :
                    cnt = PoolCommunFactory.getInstance().getConnectionExtractionPms();
                    break;                    
            }
    
            String query = "{call " + PROC_NAME + "}";
            stmt = cnt.prepareCall(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ret =
                    new DatabaseVersionInformation(
                        rs.getDate(1).toString(),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6));
            }
        } catch( Exception e ) {
                throw new TechnicalException(e.getMessage());
        } finally {
            releaseConnection(cnt, stmt);
        }
        logOptimFinMethode(methodName, dateDebutMethode);
        return ret;
    }
    /**
     * Retourne les infos DB
     * @param ctx
     * @return
     * @throws TechnicalException
     */
    public DatabaseVersionInformation getDBInfoForTC(ContexteAppelDAO ctx) 
        throws TechnicalException {
        try {
            DatabaseVersionInformation dvi = (DatabaseVersionInformation) SQLCallExecuter.getInstance().
                    executeSelectSingleObjetProc(PROC_NAME,
                            new SQLParamDescriptorSet(),
                            CLASSNAME, "getDBInfoForTC",
                            ctx, new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            return new DatabaseVersionInformation(
                                    StringUtils.trimToEmpty(rs.getString("date")),
                                    StringUtils.trimToEmpty(rs.getString("server_name")),
                                    StringUtils.trimToEmpty(rs.getString("user_name")),
                                    StringUtils.trimToEmpty(rs.getString("login_name")),
                                    StringUtils.trimToEmpty(rs.getString("db_name")),
                                    StringUtils.trimToEmpty(rs.getString("asa_version"))
                            );
                        }
                    });
            return (dvi!=null)?dvi:new DatabaseVersionInformation("","","","","","");
        } catch (SQLException e) {
            LogCommun.major(ctx.getLogin(),CLASSNAME, "getDBInfoForTC", e.getMessage());
            throw new TechnicalException(e);
        }
    }

    /**
      * @return le numéro de version de l'application (des sources Java)
      */
    public String getSourceVersion() {
        /* @todo obtenir l'info par CVS */
        return VERSION_NAME;
    }
    /**
     * Singleton
     */
    private static VersionInfoDAO s_instance;
    
    private VersionInfoDAO() {
    }

    /**
     * Accesseur du singleton. cf pattern Singleton.
     * @return le singleton ContactsDAO.
     */
    public static VersionInfoDAO getInstance () {
        if (s_instance == null)
            s_instance = new VersionInfoDAO();
        return  s_instance;
    }

}