package com.accor.asa.commun.utiles;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.collections.FastHashMap;
import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.log.LogCommun;

public class FilesPropertiesCache {
    /**
     * Singleton *
     */
    private static final FilesPropertiesCache ourInstance = new FilesPropertiesCache();
    /**
     * Fast Jakarta Map contenant tous les Properties
     */
    private FastHashMap allProperties;
    /**
     * Retourne l'instance du singleton FilesPropertiesCache
     * Le mot-clé synchronized est omis intentionnellement, dans la
     * mesure ou il est impossible d'initialiser deux fois le singleton
     * ce qui ne justifie pas la création d'un goulet d'etranglement de
     * synchonisation sur cet objet qui est TRES fréquement utilisé
     * pendant la durée de vie d'une application cliente.
     *
     * @return
     */
    public static FilesPropertiesCache getInstance() {
        return ourInstance;
    }

    /**
     * reload le properties de la clee p dans la Hashtable total
     *
     * @param p : nom du fichier sans ".properties" / clee de la Hashtable total
     */
    public void reloadProperties(String p) {
        if (LogCommun.isTechniqueDebug()) LogCommun.debug("FilesPropertiesCache", "reloadProperties", new StringBuffer("M_reloadProperties(").append(p).append(")").toString());
        allProperties.setFast(false);
        allProperties.put(p, loadFromFile(p));
        allProperties.setFast(true);
    }

    /**
     * Retourne la valeur de la clée "key" de la propriete "p"
     *
     * @param p
     * @param key
     * @return
     */
    public String getValue(String p, String key) {
        initialize(p);
        String ret = (String) ((Properties) allProperties.get(p)).get(key);
        /*
        if (LogCommun.isTechniqueDebug()) {
            String msg = new StringBuffer(" File=").append(p).append(".properties").append(" Key=").append(key).append(" Value=").append(ret).toString();
            LogCommun.debug("FilesPropertiesCache", "getValue", msg);
        }
        */
        if (null == ret) {
            LogCommun.major("COMMUN", "FilesPropertiesCache", "getValue", new StringBuffer("Unable to find key [").append(key).append("] from file [").append(p).append(".properties]").toString());
            ret = "";
        }
        return StringUtils.trim(ret);
    }

    /**
     * Retourne la valeur de la clée "key" de la propriete "p"
     * <br>Si non définie, retourne la valeur par défaut
     * @param p
     * @param key
     * @param valeurParDefaut
     * @return
     */
    public String getValue(String p, String key, String defaultValue) {
        String retour = getValue(p, key);
        if (StringUtils.isBlank(retour))
            return defaultValue;
        else
            return retour;
    }

    /**
     * Retourne la valeur de la clée "key" de la propriete "p"
     * parsé en int
     * @param p
     * @param key
     * @return
     */
    public int getIntValue(String p, String key) {
         int tailleMaxGrandeListe = -1;
         try {
             tailleMaxGrandeListe = Integer.parseInt(getValue(p, key));
         } catch (Exception e) {
             LogCommun.major("COMMUN", "FilesPropertiesCache", "getTailleMaxGrandeListe", new StringBuffer("Erreur de Parsing [").append(key).append("] from file [").append(p).append(".properties]").toString());
         }
         return tailleMaxGrandeListe;
     }

    /**
     * Retourne les clefs de propriétés "p".
     *
     * @param p
     * @return
     */
    public Set<?> keySet(String p) {
        initialize(p);
        return ((Properties) allProperties.get(p)).keySet();
    }

    /**
     * Retourne les clefs de propriétés "p".
     *
     * @param p
     * @return
     */
    public Enumeration<?> keys(String p) {
        initialize(p);
        return ((Properties) allProperties.get(p)).keys();
    }

    /**
     * Test si le fichier de propriétés "p" contient la clée "key".
     *
     * @param p
     * @param key
     * @return boolean
     */
    public boolean containsKey(String p, String key) {
        initialize(p);
        if (LogCommun.isTechniqueDebug()) {
            LogCommun.debug("FilesPropertiesCache", "containsKey", new StringBuffer("P_p (filename) : ").append(p).append(".properties").toString());
            LogCommun.debug("FilesPropertiesCache", "containsKey", new StringBuffer("P_key          : ").append(key).toString());
            LogCommun.debug("FilesPropertiesCache", "containsKey", new StringBuffer("V_getFast()    : ").append(allProperties.getFast()).toString());
        }
        return ((Properties) allProperties.get(p)).containsKey(key);
    }

    /**
     * Méthode qui permet de retourner le ficher de propriétés sous forme de Properties.
     *
     * @param p le nom du fichier de propriétés.
     * @return Properties
     */
    public Properties getProperty(String p) {
        initialize(p);
        return (Properties) allProperties.get(p);
    }

    // * * * * * * * * * * * * * * * * * * * * * * * * * *
    // Privates methods
    /**
     * Constructeur
     */
    private FilesPropertiesCache() {
        this.allProperties = new FastHashMap();
    }

    /**
     * Initialise le fichier de properties en properties a partir
     * du fichier p
     *
     * @param p : nom du fichier sans ".properties" / clee de la Hashtable total
     */
    public void initialize(String p) {
        if (!allProperties.containsKey(p)) {
            if (LogCommun.isTechniqueDebug())
                LogCommun.debug("FilesPropertiesCache", "initialize", new StringBuffer("M_initialize(").append(p).append(".properties)").toString());
            allProperties.setFast(false);
            allProperties.put(p, loadFromFile(p));
            allProperties.setFast(true);
        }
    }

    /**
     * load le fichier de propriete p (sans ".properties")
     *
     * @param p
     * @return
     */
    private Properties loadFromFile(String p) {
        if (LogCommun.isTechniqueDebug()) LogCommun.debug("FilesPropertiesCache", "loadFromFile", new StringBuffer("M_loadFromFile(").append(p).append(".properties)").toString());
        Properties prop = new Properties();
        try {
            synchronized (prop) {
                prop = GenericResourceLoader.getProperties(p + ".properties");
                // Parser les valeurs de type ${xxxx}
                prop = parseComplexExpression(prop);
            }
        } catch (IOException ex) {
            LogCommun.major("COMMUN", "FilesPropertiesCache", "loadFromFile", new StringBuffer("IOException : ").append(ex.getMessage()).toString());
        }
        return prop;
    }

    /**
     * Permet de modifier ou ajouter une property (uniquement en memoire)
     * @param fileName
     * @param property
     * @param value
     */
    public void saveProperty(String fileName, String property, String value) {
        try {
            ((Properties) allProperties.get(fileName)).setProperty(property, value);
        } catch (Exception ex) {
            LogCommun.major("COMMUN", "FilesPropertiesCache", "saveProperty", new StringBuffer("Exception : ").append(ex.getMessage()).toString());
        }
    }

    /**
     * la chaine de caractère DOLLAR_CHAR
     */
    public static final char DOLLAR_CHAR = '$';
    /**
     * la chaine de caractère startaccolade
     */
    public static final char STARTACCOL_CHAR = '{';
    /**
     * la chaine de caractère endaccolade
     */
    public static final char ENDACCOL = '}';
    /**
     * la start alias props
     */
    public static final String START_ALIAS = String.valueOf(DOLLAR_CHAR) + String.valueOf(STARTACCOL_CHAR);

    /**
     * Remplacer les expression ${XXXXXX} dans une valeur d'un
     *
     * @param value la valeur de props à parser
     * @return String la valeur
     */
    protected final String parseComplexExpression(final String value, Properties pIn) {
        final StringBuffer response = new StringBuffer();
        int prev = 0;
        int pos;
        while ((pos = value.indexOf(START_ALIAS, prev)) >= 0) {
            int posend = value.indexOf(ENDACCOL, pos);
            if (posend == -1) {
                StringBuffer msg = new StringBuffer("value ").append(value);
                msg.append(" has not end ").append(ENDACCOL);
                if (LogCommun.isTechniqueDebug())
                    LogCommun.debug("FilesPropertiesCache", "parseComplexExpression", msg.toString());
            }
            int diff = pos - prev;
            if (diff > 0) {
                response.append(value.substring(prev, pos));
            }
            final String expKey = value.substring(pos + START_ALIAS.length(), posend);
            final String valueReplace = pIn.getProperty(expKey);
            if (StringUtils.isNotEmpty(valueReplace)) {
                if (valueReplace.equals(expKey)) {
                    if (LogCommun.isTechniqueDebug())
                        LogCommun.debug("FilesPropertiesCache", "parseComplexExpression", "l'expression " + STARTACCOL_CHAR + expKey + ENDACCOL + "pas de valeur");
                    response.append(DOLLAR_CHAR).append(STARTACCOL_CHAR).append(expKey).append(ENDACCOL);
                } else {
                    response.append(valueReplace);
                }
            } else {
                response.append(DOLLAR_CHAR).append(STARTACCOL_CHAR).append(expKey).append(ENDACCOL);
            }
            prev = posend + 1;
        }
        if (prev < value.length()) {
            response.append(value.substring(prev));
        } else {
            response.append("");
        }
        return response.toString();
    }

    /**
     * Remplacer les expression ${XXXXXX} dans un objet Properties
     *
     * @param pIn
     * @return
     */
    protected final Properties parseComplexExpression(Properties pIn) {
        if (pIn != null && pIn.size() > 0) {
            String key;
            Enumeration<?> keyList = pIn.propertyNames();
            while (keyList.hasMoreElements()) {
                key = (String) keyList.nextElement();
                pIn.setProperty(key, parseComplexExpression(pIn.getProperty(key), pIn));
            }
        }
        return pIn;
    }
}

