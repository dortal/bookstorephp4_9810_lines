package com.accor.asa.commun.log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

import com.accor.asa.commun.utiles.GenericResourceLoader;


/**
 *
 * à utiliser dans tous les logs
 */
public final class LogHelper {
    // pas d'instanciation
    private LogHelper() {}

    // pour tester toutes les categories
    private static final ArrayList<Category> s_allCategories = new ArrayList<Category>();

    // niveaux de debug par categorie
    public static boolean isTechniqueDebug = true;
    public static boolean isFonctionelleDebug = true;
    public static boolean isTransfertDebug = true;
    public static boolean isHabilitationsDebug = true;
    public static boolean isTCDebug = true;
    public static boolean isQBEDebug = true;
    public static boolean isOptimDebug = true;
    public static boolean isBatchDebug = true;
    public static boolean isReportingDebug = true;
    public static boolean isLocalDebug = true;

    /**
     * La categorie technique sert a tracer les anomalies
     *              (mises en oeuvre avec des exceptions)
     */
    private static Category categorieTechnique = createCategory("technique");

    /**
     *  La categorie fonctionnelle sert a tracer les services
     *              occasionant des ecritures en base
     */
    private static Category categorieFonctionnelle = createCategory("fonctionnelle");

    /**
     *  Uniquement pour mesurer le temps passer dans Tomcat
     */
    private static Category categorieOptim = createCategory("optim");

    /**
     *  Uniquement pour tracer le transfert
     */
    private static Category categorieTransfert = createCategory("transfert");

    /**
     *  Uniquement pour mesurer les appels TC
     */
    private static Category categorieTC = createCategory("tc");

    /**
     *  Uniquement pour mesurer les appels passant par le pool QBE
     */
    private static Category categorieQBE = createCategory("qbe");


    /**
     * log pour les calculs d'habilitations (getDroit dans HabilitationFacadeBean)
     */
    private static Category categorieHabilitations = createCategory("habilitations");

    /**
     * log pour tracer les opérations effectuées lors des batchs périodiques
     */
    private static Category categorieBatch = createCategory("batch");


    /**
     *  Uniquement pour mesurer les appels passant par le pool QBE
     */
    private static Category categorieReporting = createCategory("reporting");

    /**
     *  Uniquement pour mesurer les appels passant par le pool QBE
     */
    private static Category categorieLocal = createCategory("local");

    
    private static Category createCategory(String name) {
        Category cat = Category.getInstance(name);
        s_allCategories.add(cat);
        return cat;
    }

    /**
     * initialisation
     *
     */
    static {
        try {
            System.out.println("Tentative initilisation proprietes de logging par fichier");
            initialisationProprietesParFichier();
            System.out.println("Initialisation par fichier OK");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Initialisation par fichier echouee. Initialisation statique.");
            initialisationStatiqueProprietes();
        }
        try {
            System.out.println("Log de test dans toutes les catégories");
            for (Iterator<Category> iter = s_allCategories.iterator(); iter.hasNext();) {
                Category cat = iter.next();
                cat.info("#### TEST DE LOG OK sur "+ cat.getName() + "####");
            }
        } catch (Exception e){
            System.out.println("Erreur pendant log de test" + e);
        }
    }

    private static void initialisationProprietesParFichier()  {
        // Configuration du logging a partir du fichier de proprietes
        try {
            PropertyConfigurator.configure(GenericResourceLoader.getProperties("logging.properties"));
        } catch (IOException e) {
            System.err.println("Erreur de configuration de LOG4J : " + e);
            e.printStackTrace();
        }
        // initialisation des niveaux de debug
        isTechniqueDebug = ( categorieTechnique.isDebugEnabled() || categorieTechnique.isInfoEnabled() );
        isFonctionelleDebug = ( categorieFonctionnelle.isDebugEnabled() || categorieFonctionnelle.isInfoEnabled() );
        isTransfertDebug = ( categorieTransfert.isDebugEnabled() || categorieTransfert.isInfoEnabled() );
        isHabilitationsDebug = ( categorieHabilitations.isDebugEnabled() || categorieHabilitations.isInfoEnabled() );
        isTCDebug = ( categorieTC.isDebugEnabled() || categorieTC.isInfoEnabled() );
        isQBEDebug = ( categorieQBE.isDebugEnabled() || categorieQBE.isInfoEnabled() );
        isOptimDebug = ( categorieOptim.isDebugEnabled() || categorieOptim.isInfoEnabled() );
        isBatchDebug = ( categorieBatch.isDebugEnabled() || categorieBatch.isInfoEnabled() );
        isReportingDebug = ( categorieReporting.isDebugEnabled() || categorieReporting.isInfoEnabled() );
        isLocalDebug = ( categorieLocal.isDebugEnabled() || categorieLocal.isInfoEnabled() );
    }


    /**
     * Uniquement appelé en cas d'échec de l'initialisation par fichier logging.properties
     */
    private static void initialisationStatiqueProprietes() {
        // Pour supprimer les traces de debug : categorieTechnique.setPriority(Priority.ERROR);
        categorieTechnique.setLevel(Level.DEBUG);
        categorieHabilitations.setLevel(Level.DEBUG);
        categorieFonctionnelle.setLevel(Level.INFO);
        Appender appenderTechnique = new FileAppender();

        PatternLayout pattern = new PatternLayout("%t|%d{dd/MM/yy|HH:mm:ss,SSS}|%m%n");
        Appender appenderFonctionnel = new ConsoleAppender(pattern);
        Appender appenderOptim = new ConsoleAppender(pattern);
        Appender appenderTransfert = new ConsoleAppender(pattern);
        Appender appenderTC = new ConsoleAppender(pattern);
        Appender appenderQBE = new ConsoleAppender(pattern);
        Appender appenderHabil = new ConsoleAppender(pattern);
        Appender appenderBatch = new ConsoleAppender(pattern);
        Appender appenderReporting = new ConsoleAppender(pattern);
        Appender appenderLocal = new ConsoleAppender(pattern);

        categorieTechnique.addAppender(appenderTechnique);
        categorieHabilitations.addAppender(appenderHabil);
        categorieFonctionnelle.addAppender(appenderFonctionnel);
        categorieOptim.addAppender(appenderOptim);
        categorieTransfert.addAppender(appenderTransfert);
        categorieTC.addAppender(appenderTC);
        categorieQBE.addAppender(appenderQBE);
        categorieBatch.addAppender(appenderBatch);
        categorieReporting.addAppender(appenderReporting);
        categorieLocal.addAppender(appenderLocal);
    }


    /**
     * Tracer une erreur mineure (Erreur metier)
     */
    public static void minor(String module, String codeUtilisateur, String objet, String methode, String message)  {
        categorieTechnique.error(
                formatterMessageTechnique(module, codeUtilisateur, "3", objet, methode, message, "")
        );
    }

    /**
     * Tracer une erreur mineure (Erreur metier)
     */
    public static void minor(String module, String codeUtilisateur, String objet, String methode, String message, Exception exception) {
        categorieTechnique.error(formatterMessageTechnique(module, codeUtilisateur, "3", objet, methode, message, ""), exception);
    }

    /**
     * Tracer une erreur majeure (Erreur AccesDonnes)
     */
    public static void major(String module, String codeUtilisateur, String objet, String methode, String message) {
        categorieTechnique.error(formatterMessageTechnique(module, codeUtilisateur, "4", objet, methode, message, ""));
    }

    /**
     * Tracer une erreur majeure (Erreur AccesDonnes)
     */
    public static void major(String module, String codeUtilisateur, String objet, String methode, String message, Throwable exception) {
        categorieTechnique.error(formatterMessageTechnique(module, codeUtilisateur, "4", objet, methode, message, ""), exception);
    }

    /**
     * Tracer une erreur critique (Erreur Tronc Commun, BD)
     */
    public static void critical(String module, String codeUtilisateur, String objet, String methode, String message)  {
        categorieTechnique.error(formatterMessageTechnique(module, codeUtilisateur, "5", objet, methode, message, ""));
    }

    /**
     * Tracer une erreur critique (Erreur Tronc Commun, BD)
     */
    public static void critical(String module, String codeUtilisateur, String objet, String methode, String message, Exception exception) {
        categorieTechnique.error(formatterMessageTechnique(module, codeUtilisateur, "5", objet, methode, message, ""), exception);
    }

    /**
     * Tracer un service metier occasionnant une ecriture en base
     *
     * @param codeUtilisateur
     * @param labelObject Label de l'objet fournissant le service metier
     * @param service Service effectuant une ecriture en base
     */
    public static void traceFonctionnelle(
        String module, String codeUtilisateur, String nomTable,
        String typeAction, String cle,
        String messageComplementaire) {
        categorieFonctionnelle.info(formatterMessageFonctionnel(
                module, codeUtilisateur, nomTable, typeAction, cle, messageComplementaire)
        );
    }

    /**
     * Tracer un service metier occasionnant un ecriture en base
     *
     * @param codeUtilisateur
     * @param labelObject Label de l'objet fournissant le service metier
     * @param service Service effectuant une ecriture en base
     */
    public static void traceFonctionnelle(
        String module, String codeUtilisateur, String nomTable,
        String typeAction, String cle) {
        categorieFonctionnelle.info(formatterMessageFonctionnel(
                module, codeUtilisateur, nomTable, typeAction, cle, null)
        );
    }

    /**
     * Affichage de debugging
     */
    public static void debug(String module, String objet, String methode, String message) {
        categorieTechnique.debug(formatterMessageTechnique(module, "MSG_DEBUG", "0",
                objet, methode, message, ""));
    }

    /**
     * Affichage de warning
     */
    public static void warn(String module, String codeUtilisateur,String objet, String methode, String message) {
        categorieTechnique.warn(formatterMessageTechnique(module, codeUtilisateur, "1",
                objet, methode, message, ""));
    }

    /**
     * Affichage d'information
     */
    public static void info(String module, String codeUtilisateur, String objet, String methode, String message) {
        categorieTechnique.info(formatterMessageTechnique(module, codeUtilisateur, "0",
                objet, methode, message, ""));
    }

    /**
     * @parameter codeUtilisateur : login de l'utilisateur réalisant l'action
     * @parameter nomTable : nom de la table sur laquelle est réalisée l'action
     * @parameter typeAction : type de l'ordre SQL INS, UPD ou DEL
     * @parameter cle : cle dans la table affectée. Si la clé est multiple,les colonnes sont séparées par des ,
     * @parameter messageComplementaire : informations supplémentaires si nécessaire
     */
    private static String formatterMessageFonctionnel(String module, String codeUtilisateur, String nomTable,
                                                      String typeAction, String cle, String messageComplementaire) {
        StringBuffer sb = new StringBuffer();
        sb.append(codeUtilisateur);
        sb.append("|" + module + "|");
        sb.append(nomTable);
        sb.append("|");
        sb.append(typeAction);
        sb.append("|");
        sb.append(cle);
        sb.append("|");
        if(messageComplementaire != null) {
            sb.append(messageComplementaire);
        }
        return sb.toString();
    }

    private static String formatterMessageTechnique(String module, String codeUtilisateur, String codeSeverite,
                                                    String labelObjet, String service, String message, String trace) {
        StringBuffer sb = new StringBuffer();
        sb.append(codeUtilisateur);
        sb.append("|");
        sb.append(codeSeverite);
        sb.append("|" + module + "|");
        sb.append(labelObjet);
        sb.append("|");
        sb.append(service);
        sb.append("|");
        sb.append(message);
        sb.append("|");
        sb.append(trace);
        return sb.toString();
    }

    private static String formatterMessageOptim(String labelObjet, String service, String params) {
        StringBuffer sb = new StringBuffer();
        sb.append(", Object: ");
        sb.append(labelObjet);
        sb.append(", Service: ");
        sb.append(service);
        if(params != null) {
            sb.append(" (");
            sb.append(params);
            sb.append(") ");
        }
        return sb.toString();
    }

    private static String formatterMessageTransfert(String codeUtilisateur, String labelObjet,
                                                    String nomMethode, String service, String params) {
        StringBuffer sb = new StringBuffer();
        sb.append(codeUtilisateur);
        sb.append("|");
        sb.append(labelObjet);
        sb.append("|");
        sb.append(nomMethode);
        sb.append("|");
        sb.append(service);
        if(params != null) {
            sb.append("|");
            sb.append(params);
        }
        return sb.toString();
    }

    private static String formatterMessageTC(String module, String codeUtilisateur, String url) {
        StringBuffer sb = new StringBuffer();
        sb.append( codeUtilisateur );
        sb.append( "|" + module + "|Url: " );
        sb.append( url );

        return sb.toString();
    }

    /**
     * @param codeUtilisateur :  Login de l'utilisateur effectuant l'appel
     * @param query : requete dynamique ou appel d'une procedure stockee via le pool QBE
     * @param nbRows : nb enregistrements retourner par l appel
     */
    private static String formatterMessageQBE(String module, String codeUtilisateur, String query, int nbRows ) {
        StringBuffer sb = new StringBuffer();
        sb.append( codeUtilisateur );
        sb.append( "|" + module + "|Query: " );
        sb.append( query );
        sb.append( "|Nb rows: " );
        sb.append( nbRows );

        return sb.toString();
    }

    /**
     * @param batchName : Nom du batch en cours d'éxécution
     * @param dsName :  Nom du Datasource utilisé
     * @param message : soit l'appel à la procedure stockée avec ses paramètres, soit le resultat de l'appel précédent
     */
    private static String formatterMessageBatch( String batchName, String dsName, String message ) {
        StringBuffer sb = new StringBuffer();
        sb.append( batchName + "|" );
        sb.append( dsName + "|" );
        sb.append( message );

        return sb.toString();
    }

    /**
     * @param codeUtilisateur :  Login de l'utilisateur effectuant l'appel
     * @param message 
     */
    private static String formatterMessageReporting( String codeUtilisateur, String message ) {
        StringBuffer sb = new StringBuffer();
        sb.append( codeUtilisateur ).append( "|" ).append( message );
        return sb.toString();
    }

    /**
     * @param procName : appel d'une procedure stockee via le pool Local
     * @param procParams : liste des parametres d appels de la proc
     */
    private static String formatterMessageLocal( String procName, String procParams ) {
        StringBuffer sb = new StringBuffer();
        sb.append( procName ).append( " " ).append( procParams );
        return sb.toString();
    }

    
    
    /**
     * Tracer un service metier occasionnant un ecriture en base
     *
     * @param codeUtilisateur
     * @param labelObject Label de l'objet fournissant le service metier
     * @param service Service effectuant une ecriture en base
     */
    public static void traceOptim(String labelObjet, String service, String params) {
        categorieOptim.error(formatterMessageOptim(labelObjet, service, params));
    }

    /**
     * Tracer un appel TC
     *
     * @param url
     * @param labelObject Label de l'objet fournissant le service metier
     * @param service Service effectuant une ecriture en base
     */
    public static void traceTC(String module, String codeUtilisateur, String url ) {
        categorieTC.error( formatterMessageTC( module, codeUtilisateur, url ) );
    }

    /**
     * Tracer un appel passant par le pool QBE
     *
     * @param codeUtilisateur :  Login de l'utilisateur effectuant l'appel
     * @param query : requete dynamique ou appel d'une procedure stockee via le pool QBE
     * @param nbRows : nb enregistrements retourner par l appel
     */
    public static void traceQBE(String module, String codeUtilisateur, String query, int nbRows ) {
        categorieQBE.error( formatterMessageQBE( module, codeUtilisateur, query, nbRows ) );
    }

    /**
     * Debugging du transfert
     * @param codeUtilisateur
     * @param labelObject Label de l'objet fournissant le service metier
     * @param service Service effectuant une ecriture en base
     */
    public static void debugTransfert(String labelObjet, String nomMethode, String message) {
        categorieTransfert.debug(formatterMessageTransfert("DEBUG", labelObjet, nomMethode, message, null));
    }


    /**
     * Tracer un envoi a TARS
     * @param codeUtilisateur
     * @param labelObject Label de l'objet fournissant le service metier
     * @param service Service effectuant une ecriture en base
     */
    public static void traceTransfert(String login, String labelObjet, String nomMethode, String service, String params) {
        categorieTransfert.info(formatterMessageTransfert(login, labelObjet, nomMethode, service, params));
    }

    /**
     * uniquement pour les calculs d'habilitations (optimisation)
     */
    public static void traceHabilitations( String message ) {
        categorieHabilitations.info(message);
    }

    /**
     * Tracer un appel JDBC éxécuter lors d'un batch périodique
     * @param batchName : Nom du batch en cours d'éxécution
     * @param dsName :  Nom du Datasource utilisé
     * @param message : soit l'appel à la procedure stockée avec ses paramètres, soit le resultat de l'appel précédent
     */
    public static void traceBatch( String batchName, String dsName, String message ) {
        categorieBatch.error( formatterMessageBatch( batchName, dsName, message  ) );
    }

    /**
     * Tracer un appel passant par le pool Reporting
     *
     * @param codeUtilisateur :  Login de l'utilisateur effectuant l'appel
     * @param query : requete dynamique ou appel d'une procedure stockee via le pool Reporting
     * @param nbRows : nb enregistrements retourner par l appel
     */
    public static void traceReporting( String codeUtilisateur, String message ) {
        categorieReporting.error( formatterMessageReporting( codeUtilisateur, message ) );
    }

    /**
     * Tracer un appel passant par le pool Local
     *
     * @param procName : appel d'une procedure stockee via le pool Local
     * @param procParams : liste des parametres d appels de la proc
     */
    public static void traceLocal( String procName, String procParams ) {
        categorieLocal.error( formatterMessageLocal( procName, procParams ) );
    }

}