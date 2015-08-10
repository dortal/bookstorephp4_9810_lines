package com.accor.asa.commun.persistance;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.PoolableConnection;

import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.commons.tools.jdbc.ConnectionPool;
import com.accor.commons.tools.jdbc.ConnectionPoolFactory;
import com.accor.commons.tools.jdbc.JDBCConfig;
import com.accor.commons.tools.jdbc.LDAPJDBCConfig;
import com.accor.commons.tools.jdbc.config.JdomConfigParser;

/**
 * Created on 13 oct. 2005
 * @TODO expliquer la class
 * @author <a href="mailto:laurent.frobert@consulting-for.accor.com">lfrobert</a>
 * @version $Id: AsaJDBCConfig.java 22155 2008-11-28 10:15:21Z jlatinus $
 */
public class AsaJDBCConfig {
    
    public static final String ASA_POOL = "asaPool";
    public static final String ASA_QBE_POOL = "asaQBEPool";
    public static final String TARS_VENTE_POOL = "tarsVentePool";
    public static final String TARS_LOCAL_POOL = "tarsLocalPool";
    public static final String TARS_TARIF_POOL = "tarsTarifPool";
    public static final String ASA_REPORTING_POOL = "asaReportPool";
    public static final String ASA_EXTRACTIONPMS_POOL = "asaExtracteurPool";

    public static final String JDBC_PROPERTIES_FILE = "jdbcpools";
    public static final String CONNCACHES_CONF_FILE = "conncaches.xml";

    protected static final AsaJDBCConfig config = new AsaJDBCConfig();

    protected ConnectionPool connectionPool=null;
    protected JDBCConfig jdbcconfig;
    //protected Properties prop = null;
    
    protected AsaJDBCConfig() {
        // chargement
        LogCommun.debug(getClass().getName(),"constructeur","chargement");
                

        try {
            Properties p = FilesPropertiesCache.getInstance().getProperty(AsaJDBCConfig.JDBC_PROPERTIES_FILE);
            
            LDAPJDBCConfig ldapconf =
                new LDAPJDBCConfig(p.getProperty("startUrlLDAP"), p.getProperty("endUrlLDAP"));
           
            ldapconf.addUserPwd(p.getProperty(new StringBuffer("jdbc.pool.").append(ASA_POOL).append(".login").toString()),
                   p.getProperty(new StringBuffer("jdbc.pool.").append(ASA_POOL).append(".password").toString()));
           
           ldapconf.addUserPwd(p.getProperty(new StringBuffer("jdbc.pool.").append(ASA_QBE_POOL).append(".login").toString()),
                   p.getProperty(new StringBuffer("jdbc.pool.").append(ASA_QBE_POOL).append(".password").toString()));
           
           ldapconf.addUserPwd(p.getProperty(new StringBuffer("jdbc.pool.").append(TARS_VENTE_POOL).append(".login").toString()),
                   p.getProperty(new StringBuffer("jdbc.pool.").append(TARS_VENTE_POOL).append(".password").toString()));
           
           ldapconf.addUserPwd(p.getProperty(new StringBuffer("jdbc.pool.").append(TARS_LOCAL_POOL).append(".login").toString()),
                   p.getProperty(new StringBuffer("jdbc.pool.").append(TARS_LOCAL_POOL).append(".password").toString()));
           
           ldapconf.addUserPwd(p.getProperty(new StringBuffer("jdbc.pool.").append(TARS_TARIF_POOL).append(".login").toString()),
                   p.getProperty(new StringBuffer("jdbc.pool.").append(TARS_TARIF_POOL).append(".password").toString()));
           
           ldapconf.addUserPwd(p.getProperty(new StringBuffer("jdbc.pool.").append(ASA_REPORTING_POOL).append(".login").toString()),
                   p.getProperty(new StringBuffer("jdbc.pool.").append(ASA_REPORTING_POOL).append(".password").toString()));
           
           ldapconf.addUserPwd(p.getProperty(new StringBuffer("jdbc.pool.").append(ASA_EXTRACTIONPMS_POOL).append(".login").toString()),
                   p.getProperty(new StringBuffer("jdbc.pool.").append(ASA_EXTRACTIONPMS_POOL).append(".password").toString()));
           
           this.jdbcconfig = ldapconf;
           
            
           final String driverName = p.getProperty("jdbc.drivername");
            
           Driver d = (Driver)Class.forName(driverName).newInstance();
           InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream( CONNCACHES_CONF_FILE );
           this.connectionPool = ConnectionPoolFactory.getInstance(this.jdbcconfig,
                                d,
                                ConnectionPoolFactory.POOL_COMMONS_DBCP,
                                new JdomConfigParser(in));

        } catch (Exception e) {            
            LogCommun.major("",getClass().getName(),"init",
            "erreur lors de la lecture du fichier de conf jdbc."+e.getMessage());
            return;
        }
         
    }
    
    public static AsaJDBCConfig getInstance() {        
        return config;
    }
    	    
    public ConnectionPool getConnectionPool() {
        return this.connectionPool;
    }
    
    public static Connection reallyCloseConnection(Connection c, boolean returnANewConnection, String poolName) throws SQLException,Exception {
        Connection newConnection = null;
        try {
            ((PoolableConnection)c).reallyClose();
        } catch (Exception e) {
            LogCommun.major("","AsaJDBCConfig","reallyCloseConnection",
                    "erreur lors du reallyClose :"+e.getMessage());
        }
        if (returnANewConnection) {
            newConnection=AsaJDBCConfig.getInstance().getConnectionPool().getConnection(poolName);
        }
        return newConnection;
    }
}
