package com.accor.asa.commun.process;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.persistance.AsaJDBCConfig;
import com.accor.asa.commun.services.mail.Body;
import com.accor.asa.commun.services.mail.Email;
import com.accor.asa.commun.services.mail.MailSenderFactory;
import com.accor.asa.commun.services.mail.MalformedInternetAddressException;
import com.accor.asa.commun.utiles.FilesPropertiesCache;

/**
 * AbstractPoolFactory connection de sql commun à tous les modules
 * <br>
 * Gère la récupération de connexions aux bases de données (JDBC via Weblogic), commun à tous les modules
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 juin 2005
 * Time: 14:38:16
 * To change this template use File | Settings | File Templates.
 * $Id: AbstractPoolFactory.java 22239 2008-12-03 16:19:38Z srobert $
 */
public class AbstractPoolFactory {
    public static final String ASA_DATASOURCE = "asaDataSource";
    public static final String ASA_QBE_DATASOURCE = "asaQBEDataSource";
    public static final String TARS_VENTE_DATASOURCE = "tarsVenteDataSource";
    public static final String TARS_LOCAL_DATASOURCE = "tarsLocalDataSource";
    public static final String TARS_TARIF_DATASOURCE = "tarsTarifDataSource";
    public static final String ASA_REPORTING_DATASOURCE = "asaReportingDataSource";
    public static final String ASA_EXTRACTIONPMS_DATASOURCE = "asaExtracteurDataSource";

    /**
     * Un map pour stocker le time d'envoi de mail de supervision
     */
    public static final Map<String, Long> delaiRelanceMail = new HashMap<String, Long>();


    /**
     * Constructeur
     */
    protected AbstractPoolFactory() {
    }

    /**
     * connexions JDBC vers ASAV2 pour executer des procedures stockees
     * @return
     * @throws SQLException, Exception
     */
    public Connection getConnection() throws Exception, SQLException {
           return getConnection(AsaJDBCConfig.ASA_POOL);
    }


    /**
     * getConnectionQBE
     * @return
     * @throws Exception
     * @throws SQLException
     */
    public Connection getConnectionQBE() throws Exception, SQLException {
        return getConnection(AsaJDBCConfig.ASA_QBE_POOL);
    }

    /**
     * getConnectionReporting
     * @return
     * @throws Exception
     * @throws SQLException
     */
    public Connection getConnectionReporting() throws Exception, SQLException {
        return getConnection(AsaJDBCConfig.ASA_REPORTING_POOL);
    }

    /**
     * getConnectionExtractionPms
     * @return
     * @throws Exception
     * @throws SQLException
     */
    public Connection getConnectionExtractionPms() throws Exception, SQLException {
        return getConnection(AsaJDBCConfig.ASA_EXTRACTIONPMS_POOL);
    }

    /**
     * Renvoie une connexion JDBC vers TARS
     * @return
     * @throws Exception
     * @throws SQLException
     */
    public Connection getConnectionTarifTARS() throws Exception, SQLException {
        return getConnection(AsaJDBCConfig.TARS_TARIF_POOL);
    }

    /**
     * Renvoie une connexion JDBC vers TARS pour les transferts clients et contrats
     * @return
     * @throws Exception
     * @throws SQLException
     */
    public Connection getConnectionVenteTARS() throws Exception, SQLException {
        return getConnection(AsaJDBCConfig.TARS_VENTE_POOL);
    }

    /**
     * Renvoie une connexion JDBC vers TARS pour les transferts CDV
     * @return
     * @throws Exception
     * @throws SQLException
     */
    public Connection getConnectionLocalTARS() throws Exception, SQLException {
        return getConnection(AsaJDBCConfig.TARS_LOCAL_POOL);
    }

    /**
     * Envoie d'un mail en cas de bloccage suivi
     * d'une initialisation d'un pool
     * @param poolJdbcName
     * @throws Exception
     */
    private void envoyerMessage(String poolName, String typeMail)  throws Exception {
        long startsAgain = Long.parseLong(FilesPropertiesCache.getInstance().getValue(AsaJDBCConfig.JDBC_PROPERTIES_FILE,"mail.startsAgain"));
        Long lastSendMail = (Long)delaiRelanceMail.get(poolName);
        if ( lastSendMail==null ||
                (System.currentTimeMillis()-lastSendMail.longValue()>startsAgain) ) {
            delaiRelanceMail.put(poolName, new Long(System.currentTimeMillis()));
            String mailAdress, mailSubject, mailBody;
            mailAdress = FilesPropertiesCache.getInstance().getValue(AsaJDBCConfig.JDBC_PROPERTIES_FILE,"mail.adress");
            mailSubject= FilesPropertiesCache.getInstance().getValue(AsaJDBCConfig.JDBC_PROPERTIES_FILE,"mail.subject");
            mailBody   = FilesPropertiesCache.getInstance().getValue(AsaJDBCConfig.JDBC_PROPERTIES_FILE,"mail.body."+typeMail);
            StringBuffer corps = new StringBuffer(mailBody);
            corps.append("\nNom  = ").append(poolName);
            Email email = new Email(Integer.MAX_VALUE);
            try {
                email.setFrom(mailAdress, mailAdress);
                email.addTo(mailAdress, mailAdress);
            } catch (MalformedInternetAddressException e) {
                throw e;
            }
            email.setSubject(mailSubject);
            email.addBody("<pre>"+corps+"</pre>", Body.TEXT_HTML, Body.ISO_8859_1);
            MailSenderFactory.getInstance().send(email);
        }
    }

    /**
     * Retourne un connection
     * @param poolName
     * @return
     * @throws Exception
     * @throws SQLException
     */
    public Connection getConnection(String poolJdbcName) throws Exception, SQLException {
        boolean isretryGetObjectPoolActive = "1".equals(FilesPropertiesCache.getInstance().getValue(AsaJDBCConfig.JDBC_PROPERTIES_FILE,"retryGetObjectPool.active"));
        if (isretryGetObjectPoolActive) {
            int retryGetObjectPoolMax = FilesPropertiesCache.getInstance().getIntValue(AsaJDBCConfig.JDBC_PROPERTIES_FILE,"retryGetObjectPool.max");
            int nbEssais        = 0;
            boolean isGetOk     = false;
            boolean isMaxTryOk  = false;
            Connection cnt      = null;
            while (nbEssais<retryGetObjectPoolMax && !isGetOk) {
                try {
                    cnt = AsaJDBCConfig.getInstance().getConnectionPool().getConnection(poolJdbcName);
                    isGetOk = true;
                } catch (Exception  e) {
                    if (e instanceof NoSuchElementException) {
                        nbEssais++;
                        if (nbEssais == retryGetObjectPoolMax)
                            isMaxTryOk = true;
                        LogCommun.major("","AbstractPoolFactory","getConnection",">>> Try getConnection="+nbEssais);
                    } else {
                        throw e;
                    }
                }
            }
            // Toujours rien
            if (!isGetOk && isMaxTryOk) {
                try {
                    // Initialisation du pool
                    AsaJDBCConfig.getInstance().getConnectionPool().refreshPool(poolJdbcName);
                    // Retourne la connection
                    cnt = AsaJDBCConfig.getInstance().getConnectionPool().getConnection(poolJdbcName);
                    //Envoie de email
                    envoyerMessage(poolJdbcName, "init");
                } catch (Exception e) {
                    //Envoie de email
                    envoyerMessage(poolJdbcName, "error");
                    throw e;
                }
            }
            return cnt;
        } else {
            return AsaJDBCConfig.getInstance().getConnectionPool().getConnection(poolJdbcName);
        }
    }
}
