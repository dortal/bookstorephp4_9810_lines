package  com.accor.asa.commun.persistance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.commun.utiles.Proprietes;

/**
  * super-classe de toutes les DAO (Data Access Object)
  */
public abstract class DAO {

    protected static final long TRUE_LONG = 123456789987654321L;

    // différents types de verrouillages
    public static final int VERROU_SUR_COMPTE  = 1;
    public static final int VERROU_SUR_CONTACT = 2;
    public static final int VERROU_SUR_DOSSIER = 3;
    public static final int VERROU_SUR_RATTACHEMENTPMS = 4;

    private static final String COTECOTE = "''";

    public static final String EMPTY_STRING = "";

    public static final int SYBASE_DEADLOCK_ERROR_CODE = 1205;
    public static final int SYBASE_PRIMARY_KEY_ERROR_CODE = 2601;
    public static final int SYBASE_FOREIGN_KEY_ERROR_CODE = 546;
    //DDR 22/07/2002 : Bizarre, avt le code renvoyé était 546; maintenant, c'est 547.
    //Dû au nouveau driver ???
    //Autre possiblité :
    //546 est renvoyé quand on insère un enregistrement aui pointe vers un enregistrement inconnu
    //547 est renvoyé quand on tente de supprimer un enregistrement alors que d'autres enregistrements y font référence
    public static final int SYBASE_FOREIGN_KEY_ERROR_CODE_2 = 547;

    public static final int SYBASE_TIMESTAMP_ERROR_CODE = 532;
    private int tailleMaxResultSet = Integer.MAX_VALUE;
    private int tailleMaxResultSetGrandeListe = Integer.MAX_VALUE;

    /**
     * put your documentation comment here
     * @param chaine
     * @return
     */
    public static String emptyStringToNull (String chaine) {
        if (EMPTY_STRING.equals(chaine))
            return  null;
        return  chaine;
    }

    /**
     * put your documentation comment here
     * @param chaine
     * @return
     */
    public static String nullToEmptyString (String chaine) {
        if (chaine == null)
            return  EMPTY_STRING;
        return  chaine;
    }

    protected final boolean isStringEmpty(String s) {
        return s == null || s.trim().length()==0;
    }

    /**
     * put your documentation comment here
     * @param chaineInserable
     * @return
     */
    protected String castCoteForBase (String chaineInserable) {
        String retour = null;
        if (chaineInserable != null) {
            StringBuffer retourBuffer = new StringBuffer(33);
            int precedentIndiceCote = 0;
            int suivantIndiceCote = 0;
            while (true) {
                suivantIndiceCote = chaineInserable.indexOf("'", precedentIndiceCote);
                if (suivantIndiceCote == -1) {
                    break;
                }
                retourBuffer.append(chaineInserable.substring(precedentIndiceCote,
                        suivantIndiceCote));
                retourBuffer.append(COTECOTE);
                precedentIndiceCote = suivantIndiceCote + 1;
            }
            if (precedentIndiceCote == 0)
                retour = chaineInserable;
            else {              //  il faut rajouter la fin de la chaine !
                retourBuffer.append(chaineInserable.substring(precedentIndiceCote));
                retour = retourBuffer.toString();
            }
        }
        return  retour;
    }

    /**
     * Le paramètre de la requête est mis à nul si besoin
     * @param cs
     * @param index
     * @param chaine
     * @exception TechnicalException, SQLException
     */
    protected void setIntOrNull (CallableStatement cs, int index, String chaine) throws TechnicalException,
            SQLException {
        try{
            if (cs == null){
                    String message = "CallableStatement null ?!";
                    TechnicalException exception = new TechnicalException(message);
                    if( LogCommun.isTechniqueDebug() ) {
                      LogCommun.info("MSG_INFO","DAO","setLongOrNull", message);
                    }
                    throw exception;
                }
            if ((chaine == null) || (chaine.trim().equals("")) || (Integer.parseInt(chaine)<0)) {
                cs.setNull(index, Types.INTEGER);
            }
            else {
                cs.setInt(index, Integer.parseInt(chaine));
            }
        } catch(NumberFormatException e){
            String message = "Variable non parsable en int : = "+chaine;
            TechnicalException exception = new TechnicalException(e);
            if( LogCommun.isTechniqueDebug() ) {
              LogCommun.info("MSG_INFO","DAO","setLongOrNull",message);
            }
            throw exception;
        }
    }

    /**
     * Le paramètre de la requête est mis à nul si besoin
     * @param cs
     * @param index
     * @param value
     * @param nullValue : valeur numérique représentant le NULL
     * @exception TechnicalException, SQLException
     */
    protected void setIntOrNull (CallableStatement cs, int index, int value, int nullValue)
        throws TechnicalException, SQLException {
            if (cs == null){
                    String message = "CallableStatement null ?!";
                    TechnicalException exception = new TechnicalException(message);
                    if( LogCommun.isTechniqueDebug() ) {
                      LogCommun.info("MSG_INFO","DAO","setLongOrNull()", message);
                    }
                    throw exception;
                }
            if (value == nullValue) {
                cs.setNull(index, Types.INTEGER);
            }
            else {
                cs.setInt(index, value);
            }
    }

    /**
     * Le paramètre de la requête est mis à nul si besoin
     * @param value
     * @param nullValue
     * @return
     */
    protected Integer getIntOrNull (int value, int nullValue)  {
        try {
            if (value == nullValue) {
                return null;
            } else {
                return new Integer(value);
            }
        } catch( Exception e) {
            return null;
        }
    }


    /**
     * Le paramètre de la requête est mis à nul si besoin
     * @param cs
     * @param index
     * @param chaine
     * @exception TechnicalException, SQLException
     */
    protected void setLongOrNull (CallableStatement cs, int index, String chaine) throws TechnicalException,
            SQLException {
        try{
            if (cs == null){
                String message = "CallableStatement null ?!";
                TechnicalException exception = new TechnicalException(message);
                if( LogCommun.isTechniqueDebug() ) {
                  LogCommun.info("MSG_INFO","DAO","setLongOrNull", message);
                }
                throw exception;
            }
            if ((chaine == null) || (chaine.trim().equals("")) || (Long.parseLong(chaine)<0)) {
                cs.setNull(index, Types.BIGINT);
            }
            else {
                cs.setLong(index, Long.parseLong(chaine));
            }
        } catch(NumberFormatException e){
            String message = "Variable non parsable en long : = "+chaine;
            TechnicalException exception = new TechnicalException(e);
            if( LogCommun.isTechniqueDebug() ) {
              LogCommun.info("MSG_INFO","DAO","setLongOrNull", message);
            }
            throw exception;
        }
    }

    /**
     * Le paramètre de la requête est mis à nul si besoin
     * @param cs
     * @param index
     * @param value
     * @param nullValue : valeur numérique représentant le NULL
     * @exception TechnicalException, SQLException
     */
    protected void setLongOrNull (CallableStatement cs, int index, long value, long nullValue)
        throws TechnicalException, SQLException {
            if (cs == null){
                    String message = "CallableStatement null ?!";
                    TechnicalException exception = new TechnicalException(message);
                    if( LogCommun.isTechniqueDebug() ) {
                      LogCommun.info("MSG_INFO","DAO","setLongOrNull", message);
                    }
                    throw exception;
                }
            if (value == nullValue) {
                cs.setNull(index, Types.BIGINT);
            }
            else {
                cs.setLong(index, value);
            }
    }

    /**
     * Le paramètre de la requête est mis à nul si besoin
     * @param value
     * @param nullValue
     * @return
     */
    protected Long getLongOrNull (long value, long nullValue) {
        try {
            if (value == nullValue) {
                return null;
            } else {
                return new Long(value);
            }
        } catch( Exception e) {
            return null;
        }
    }

    /**
     * put your documentation comment here
     * @param e
     * @exception PersistanceException, SQLException
     */
    public void affineSQLException (SQLException e) throws PersistanceException,
            SQLException {
        switch (e.getErrorCode()) {
            case SYBASE_DEADLOCK_ERROR_CODE:
                throw  new DeadLockException(e);
            case SYBASE_PRIMARY_KEY_ERROR_CODE:
                throw  new PrimaryKeyException(e);
            case SYBASE_FOREIGN_KEY_ERROR_CODE:
            case SYBASE_FOREIGN_KEY_ERROR_CODE_2:
                throw  new ForeignKeyException(e);
            case SYBASE_TIMESTAMP_ERROR_CODE:
                throw  new TimestampException(e);
            default:
                throw  e;
        }
    }

    /**
     * Renvoie une TimestampException si le codeErreur correspond sinon renvoie une Technical
     * @throws TimestampException, TechnicalException
     */
    public boolean isTimestampException( SQLException e ) {
        int errorCode = e.getErrorCode();
        return ( errorCode == SYBASE_TIMESTAMP_ERROR_CODE );
    }

    protected int getTailleMaxResultSet() {
        if(tailleMaxResultSet == Integer.MAX_VALUE) {
            try {
                tailleMaxResultSet = Integer.parseInt(FilesPropertiesCache.getInstance().getValue(Proprietes.PROPERTIES_FILE_NAME,Proprietes.TAILLE_MAX_RSET));
            } catch (NumberFormatException e) {
                if( LogCommun.isTechniqueDebug() ) {
                  LogCommun.info("MSG_INFO","DAO", "getTailleMaxResultSet",
                      "Impossible de récupérer la propriété TAILLE_MAX_RSET");
                }
            }
        }
        return tailleMaxResultSet;
    }

    protected int getTailleMaxResultSetGrandeListe() {
        if(tailleMaxResultSetGrandeListe == Integer.MAX_VALUE) {
            try {
                tailleMaxResultSetGrandeListe = Integer.parseInt( FilesPropertiesCache.getInstance().getValue(
                		Proprietes.PROPERTIES_FILE_NAME, Proprietes.TAILLE_MAX_RSET_GRANDE_LISTE ) );
            } catch (NumberFormatException e) {
                if( LogCommun.isTechniqueDebug() ) {
                  LogCommun.info("MSG_INFO","DAO", "getTailleMaxResultSetGrandeListe",
                      "Impossible de récupérer la propriété TAILLE_MAX_RSET_GRANDE_LISTE");
                }
            }
        }
        return tailleMaxResultSetGrandeListe;
    }


    /**
     * ferme le statement et la connexion (même en cas d'erreur sur le premier)
     */
    private final void terminateConnection (Connection cnt, Statement cs) throws SQLException  {
        try {
            if (cs != null) {
                cs.close();
                cs=null;
            }

        }
        // on doit a tout prix essayer de fermer la connexion
        finally {
            if (cnt != null && !cnt.isClosed())
                cnt.close();
        }
    }

    /**
     * ferme le statement et la connexion (même en cas d'erreur sur le premier)
     * <br> erreur encapsulée en TechnicalException
     * @param contexte pour loguer en cas d'erreur
     */
    protected final void releaseConnection (ContexteAppelDAO contexte, Connection cnt, Statement cs) throws TechnicalException  {
        try {
            terminateConnection(cnt, cs );
            cnt = null;
        }
        catch ( SQLException e) {
            if (contexte!=null)
                LogCommun.major(contexte.getLogin(), "DAO","releaseConnection","Probleme fermeture du statement ou de la connexion", e);
            throw new TechnicalException(e);
        }
    }

    /**
     * ferme le statement et la connexion (même en cas d'erreur sur le premier)
     * <br> erreur encapsulée en TechnicalException
     */
    protected final void releaseConnection (Connection cnt, Statement cs) throws TechnicalException  {
        releaseConnection(null, cnt,cs);
    }

    protected void setRowCount (Connection cnt, int rowCount)
        throws SQLException, TechnicalException{

        PreparedStatement stmt = null;
        try{

            stmt = cnt.prepareStatement("set rowcount "+rowCount);
            stmt.execute();
            // ANO 4055 : on log 
            LogCommun.info("ROWCOUNT", getClassName(),"setRowCount","connection hashcode :"+cnt.hashCode()+" setRowCount "+rowCount);
        }
        finally{
            releaseConnection(null,stmt);
        }
    }

    /**
      * à appeler dans le finally lorsqu'on veut juste fermer un statement
      * @deprecated utiliser releaseConnection(contexte, null,stmt)
      */
    protected final void closeStatementFinally( Statement stmt, String login, String methodName ) {
         if (stmt != null) {
            try {
               stmt.close();
               stmt=null;
            }
            catch (SQLException e) {
                LogCommun.major(login, getClassName(), methodName, "erreur en fermant le statement" , e);
                if( LogCommun.isTechniqueDebug() ) {
                  LogCommun.info(login, getClassName(), methodName,"erreur en fermant le statement :" + e.getMessage());
                }
            }
         }
         else
            stmt = null;
    }

    protected final java.util.Date logOptimDebutMethode(String methodName ) {
        java.util.Date dateDebutMethode = new java.util.Date();
        LogCommun.traceOptim(getClassName(), methodName, "debut méthode" + dateDebutMethode);
        return dateDebutMethode;
    }

    protected final void logOptimFinMethode(String methodName, java.util.Date dateDebut ) {
        LogCommun.traceOptim(getClassName(), methodName, "fin methode :" + (new java.util.Date().getTime() - dateDebut.getTime()));
    }

    private String getClassName( ) {
        return getClass().getName();
    }



}



