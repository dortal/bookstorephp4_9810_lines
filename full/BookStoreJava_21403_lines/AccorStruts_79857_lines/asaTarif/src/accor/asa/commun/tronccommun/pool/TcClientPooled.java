package com.accor.asa.commun.tronccommun.pool;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpRecoverableException;
import org.apache.commons.httpclient.MethodRetryHandler;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.tronccommun.TcClient;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.commun.utiles.Proprietes;
import com.vsi.xml.XmlParser;

/**
 * Objet mis en pool et utilisé pour invoker le tronc commun et parser le retour.
 */
public final class TcClientPooled implements TcClient,MethodRetryHandler  {
    private static String xmlHeader = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><accortronccommun mode=\"input\" version=\"1.00\"><fonction nom=\"";
    private final static String FILE_NAME_GLOBAL_CONF = "global";
    private URL urlTroncCommun = null;
    //Contient l'url du TC du début jusqu'au dernier /
    private static String debutURL_TC = "";

    /** key donnant le max active du pool */
    public static final String CONF_MAXACTIVE_KEY = "tcclient.pool.maxactive";
    /** val par defaut pour max connection per host */
    public static final int DEFAULT_MAX_CONNECTION_PER_HOST = 20;
    /** header content type */
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    /** header content type value pour le xml */
    public static final String HEADER_CONTENT_TYPE_XML_VALUE = "text/xml";
    /** nom header http Connection */
    public static final String HEADER_CONNECTION = "Connection";
    /** valeur "Keep-Alive" */
    public static final String HEADER_KEEP_ALIVE_VALUE = "Keep-Alive";    
    /** key pour timeout de connection */
    public static final String CONNECTION_TIMEOUT_KEY = "invoker.http.connectionTimeOut";    
    /** key pour timeout de read des datas */
    public static final String DATA_TIMEOUT_KEY = "invoker.http.dataTimeOut";
    /** val par defaut pour CTO */
    public static final int DEFAULT_CONNECTION_TIME_OUT = 5000;    
    
    private static MultiThreadedHttpConnectionManager mcm;
    static
    {
        try
        {
            // init du MultiThreadedHttpConnectionManager
            mcm = new MultiThreadedHttpConnectionManager();
            int maxConnPerHost = -1;  
            try {                
                maxConnPerHost = Integer.parseInt(FilesPropertiesCache.getInstance().getValue(FILE_NAME_GLOBAL_CONF,CONF_MAXACTIVE_KEY));
            } catch (Exception e) {
                maxConnPerHost = DEFAULT_MAX_CONNECTION_PER_HOST;
            }
                
            if (LogCommun.isTechniqueDebug()) {
                LogCommun.debug("TcClientPooled", "TcClientPooled", "init de MultiThreadedHttpConnectionManager avec maxConnPerHost " + maxConnPerHost);
            }
            
            mcm.setMaxConnectionsPerHost( maxConnPerHost );
        }
        catch ( Exception e )
        {
            LogCommun.major("COMMUN", "TcClientPooled", "TcClientPooled", "impossible de faire init de MultiThreadedHttpConnectionManager", e);            
        }
    }
    /*
    private class FilteredInputStream extends FilterInputStream {
            FilteredInputStream(InputStream is) {
                super(is);
            }

            public void close() throws IOException {
                try {
                    super.close();
                } finally {
                    this.in.close();
                }
            }
    }
    */
    /**
     * see name
     */
    public TcClientPooled() {
        if (LogCommun.isTechniqueDebug()) LogCommun.debug("TcClientPooled", "TcClientPooled", "Constructeur...");
        String url = null;
        String parser;
        try {
            url = FilesPropertiesCache.getInstance().getValue(FILE_NAME_GLOBAL_CONF,"tc.URL");
            debutURL_TC = url.substring(0, url.lastIndexOf('/'));
            if (LogCommun.isTechniqueDebug())
                LogCommun.debug("TcClientPooled", "TcClientPooled", "tc.URL = " + url);
            urlTroncCommun = new URL(url);

            parser = FilesPropertiesCache.getInstance().getValue(Proprietes.PROPERTIES_FILE_NAME,Proprietes.DEFAULT_SAX_PARSER);
            if (!StringUtils.isEmpty(parser) && !parser.equals(XmlParser.getDefaultParserClassName()))
                XmlParser.setDefaultParserClassName(parser);
        } catch (MalformedURLException exc) {
            LogCommun.major("COMMUN", "TcClientPooled", "TcClientPooled", "Malformed URl, properties tc.URL = " + url, exc);
        } catch (NumberFormatException exc) {
            LogCommun.major("COMMUN", "TcClientPooled", "TcClientPooled", "fichier properties introuvable", exc);
        } catch (Exception e) {
            LogCommun.major("COMMUN", "TcClientPooled", "TcClientPooled", "Erreur construction URL TC, property tc.URL = " + url, e);
        }
    }

    /**
     * @param procedureName
     * @param parametersNames
     * @param queryParameters
     * @param codeUtilisateur
     * @return
     * @throws TechnicalException
     */
    public final InputStream call(String procedureName,
                                  String[] parametersNames,
                                  String[] queryParameters,
                                  String codeUtilisateur)
            throws TechnicalException {
        String debugUrl = buildHttpRequestFromParams(procedureName, parametersNames, queryParameters);
        return executeCommand(procedureName, parametersNames, queryParameters, codeUtilisateur, debugUrl);
    }

    /**
     * @param procedureName
     * @param parametersNames
     * @param queryParameters
     * @param codeUtilisateur
     * @param debugUrl
     * @return
     * @throws TechnicalException
     */
    private InputStream executeCommand(String procedureName,
                                       String[] parametersNames,
                                       String[] queryParameters,
                                       String codeUtilisateur,
                                       String debugUrl)
            throws TechnicalException {
        if (LogCommun.isTechniqueDebug()) {
            LogCommun.debug("TcClientPooled", "executeCommand", "debugUrl=" + debugUrl);
            LogCommun.debug("TcClientPooled", "executeCommand", "codeUtilisateur=" + codeUtilisateur);
            LogCommun.debug("TcClientPooled", "executeCommand", "procedureName=" + procedureName);
            for (int i = 0; i < parametersNames.length; i++)
                LogCommun.debug("TcClientPooled", "executeCommand", "parametersNames[" + i + "]: " + parametersNames[i] + " = " + queryParameters[i]);
        }
        LogCommun.traceTC(codeUtilisateur, debugUrl);
        
        String argString = ""; // default
        String xmlCommand = generatexmlQuery(procedureName, queryParameters, parametersNames);
        if (xmlCommand != null) argString = xmlCommand.trim();
        
        final HttpClient httpClient = new HttpClient( mcm );
        int timeOut = DEFAULT_CONNECTION_TIME_OUT;
        try {
            timeOut= Integer.parseInt(FilesPropertiesCache.getInstance().getValue(FILE_NAME_GLOBAL_CONF,CONNECTION_TIMEOUT_KEY));
        } catch (NumberFormatException e) {
            
        }
                
        httpClient.setConnectionTimeout( timeOut);
        
        HostConfiguration hc = new HostConfiguration();
        
        hc.setHost( urlTroncCommun.getHost(), urlTroncCommun.getPort() );
        httpClient.setHostConfiguration( hc );
        try
        {            

            PostMethod mozillaPost = new PostMethod();
            mozillaPost.setMethodRetryHandler(this);
            
            mozillaPost.setRequestHeader( HEADER_CONTENT_TYPE,
                                          HEADER_CONTENT_TYPE_XML_VALUE );
            // on le force pour EAServer
            mozillaPost.setRequestHeader( HEADER_CONNECTION,
                                          HEADER_KEEP_ALIVE_VALUE );
            mozillaPost.setPath( urlTroncCommun.getPath() );
            mozillaPost.setRequestBody( new ByteArrayInputStream( argString.getBytes() ) );

            httpClient.executeMethod( mozillaPost );
            return this.createConnectionReleasingInputStream( mozillaPost );
        } catch (Exception e) {
            LogCommun.major(codeUtilisateur, "TCConnection", "executeCommand", "Erreur de communication avec le Tronc Commun", e);
            throw new TechnicalException(e);
        }
    }

    /**
     * La requete sous forme de trame XML
     *
     * @param procedureName
     * @param queryParameters
     * @param parametersNames
     * @return
     */
    private String generatexmlQuery(String procedureName, String[] queryParameters, String[] parametersNames) {
        StringBuffer returnValue = new StringBuffer();
        returnValue.append(xmlHeader);
        returnValue.append(procedureName);
        returnValue.append("\">");
        for (int i = 0; i < parametersNames.length; i++) {
            returnValue.append("<parametre nom=\"").append(parametersNames[i]).append("\">");
            if (queryParameters[i] != null) {
                returnValue.append(queryParameters[i]);
            }
            returnValue.append("</parametre>");
        }
        returnValue.append("</fonction>");
        returnValue.append("</accortronccommun>");
        return returnValue.toString();
    }

    /**
     * buildHttpRequestFromParams
     *
     * @param procedureName
     * @param parametersNames
     * @param queryParameters
     * @return
     */
    private static String buildHttpRequestFromParams(String procedureName, String[] parametersNames, String[] queryParameters) {
        StringBuffer reqHttp = new StringBuffer(debutURL_TC);
        reqHttp.append("/HTMLServlet?TcVersion=2.0&TcMethodName=");
        reqHttp.append(procedureName);
        for (int i = 0; i < parametersNames.length; i++) {
            reqHttp.append("&").append(parametersNames[i]).append("=");
            if (queryParameters[i] != null) {
                reqHttp.append(queryParameters[i]);
            }
        }
        return reqHttp.toString();
    }

    /**
     * appel par le tronc commun de la procédure donnée
     * <br>teste le résultat
     * <br>logue l'url pour test
     *
     * @throws TechnicalException en cas d'erreur
     */
    public Object createObjectFromCommand(Class<?> objectFactoryClass,
                                          String methodName,
                                          String[] paramNames,
                                          String[] paramValues,
                                          String login)
            throws TechnicalException {
        String strDebugUrl = buildHttpRequestFromParams(methodName, paramNames, paramValues);
        InputStream streamresult = executeCommand(methodName, paramNames, paramValues, login, strDebugUrl);
        Object tcRes;
        try {
            tcRes = callMethodOnClassObject(null, objectFactoryClass, "createObject",
                    new Class[]{InputStream.class, },
                    new Object[]{streamresult, });
            testResultatTroncCommun(tcRes, objectFactoryClass, strDebugUrl);
        } catch (Exception e) {
            throw new TechnicalException("erreur lors de l'appel à ObjectFactory.createObject: " + strDebugUrl);
        }
        return tcRes;
    }

    /**
     * helpers sur introspection
     *
     * @param c
     * @param name
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     *
     */
    private Object callStaticGetter(Class<?> c, String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return callMethodOnClassObject(null, c, name, null, null);
    }

    /**
     * @param o
     * @param c
     * @param name
     * @param paramClasses
     * @param params
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Object callMethodOnClassObject(Object o, Class<?> c, String name, Class<?>[] paramClasses, Object[] params)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method m = c.getMethod(name, paramClasses);
        return m.invoke(o, params);
    }

    /**
     * @param o
     * @param name
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Object callGetterOnObject(Object o, String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (o == null)
            return null;
        else {
            Class<?> c = o.getClass();
            return callMethodOnClassObject(o, c, name, null, null);
        }
    }

    /**
     * Test du résultat du tc
     * utilise l'introspection car la classe change à chaque package
     *
     * @param resultatTC
     * @param objectFactoryClass
     * @param debugUrl
     * @throws TechnicalException
     */
    private void testResultatTroncCommun(Object resultatTC, Class<?> objectFactoryClass, String debugUrl) throws TechnicalException {
        try {
            if (resultatTC == null) {
                StringBuffer sb = new StringBuffer();
                sb.append(" ObjectFacory error : ");
                sb.append(callStaticGetter(objectFactoryClass, "getDefaultFactoryLastError"));
                sb.append(" ObjectFactory exception : ");
                sb.append(callStaticGetter(objectFactoryClass, "getDefaultFactoryLastException"));
                throw new TechnicalException(new StringBuffer(sb.toString()).append(" : ").append(debugUrl).toString());
            } else {
                Class<?> resClass = resultatTC.getClass();
                Method mGetMetaResp = resClass.getMethod("getMeta_response");
                Object metaResponse = mGetMetaResp.invoke(resultatTC);
                if (metaResponse != null) {
                    Integer i = (Integer) callGetterOnObject(metaResponse, "getReturncode");
                    int retValue = i.intValue();
                    if (retValue == -1) {
                        String errMsg = (String) callGetterOnObject(metaResponse, "getErrormessage");
                        throw  new TechnicalException(new StringBuffer(errMsg).append(" : ").append(debugUrl).toString());
                    }
                } else {
                    throw new TechnicalException(new StringBuffer("erreur appel tc (+pas de log possible)").append(" :").append(debugUrl).toString());
                }
            }
        } catch (Exception e) {
            throw new TechnicalException(new StringBuffer("erreur appel tc (+erreur durant le log)").append(" : ").append(debugUrl).append("\n").append(e.getMessage()).toString());
        }
    }
    
    /**
     * Method createConnectionReleasingInputStream
     * permet de recycler la connection http de ma method
     * @param method
     * @return InputStream
     * @throws IOException see name
     */
    private InputStream createConnectionReleasingInputStream( final HttpMethodBase method )
        throws IOException
    {
        return new FilterInputStream( method.getResponseBodyAsStream() )
        {
            public void close()
                throws IOException
            {
                try
                {
                    super.close();
                }
                finally
                {
                    method.releaseConnection();
                }
            }
        };
    }
	/** (non-Javadoc)
	 * @see org.apache.commons.httpclient.MethodRetryHandler#retryMethod(org.apache.commons.httpclient.HttpMethod, org.apache.commons.httpclient.HttpConnection, org.apache.commons.httpclient.HttpRecoverableException, int, boolean)
	 */
	public boolean retryMethod(HttpMethod arg0, HttpConnection arg1, HttpRecoverableException arg2, int executionCount, boolean requestSent) {
	    if (executionCount>=3) { // pas plus de trois reessaie
	        return false;
	    } else {
	        return true;
	    }
	    

	}
}
