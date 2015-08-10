package com.accor.asa.commun.cache.metier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.commun.utiles.process.MessageSenderException;
import com.accor.commons.cache.Cache;
import com.accor.commons.cache.CacheFactory;


/**
 * Classe centrale pour la gestion des caches
 *
 */
public final class CacheManager {

	// singleton
	private static CacheManager instance = null;

	private final String fileConfCache = "accor-cache.properties";

		// liste des caches declarés
	private List<CacheDescriptor> caches;
		// nom virtuel de l'intance
	private String m_clusterInstanceId = null;
	// servlet de rafraichissement d'un cache
	String servletPath;
	// liste des instances geres
	String[] instances;

	public static final String MSG_PROP_CACHE_NAME = "name";
	public static final String MSG_PROP_ORIGINATOR_ID = "originator_id";
	public static final String MSG_PROP_REFRESH_OPTIONS = "options";

	private CacheManager() {
		// on prend un timestamp (différent sur chaque instance...)
		long l = new java.util.Date().getTime();
		m_clusterInstanceId = "REFCM_" + Long.toString(l);
		servletPath = FilesPropertiesCache.getInstance().getValue("refresh-cache","servletPath");
		String instanceList = FilesPropertiesCache.getInstance().getValue("refresh-cache","instanceList");
		instances = instanceList.split("\\|");
	}

	public static CacheManager getInstance(){
		if(instance == null){
			instance = new CacheManager();
		}
		return instance;
	}

	/***
	 * retourne un obj du cache
	 * @param obj
	 * @param params
	 * @return
	 */
	private Object getObject( Class<?> obj, String[] params ) {
		Object key = AbstractCachable.generateKey( params );
		Cache c = CacheFactory.getInstance().getCache( obj );
		return c.retrieve( key );
	}

	/***
	 * retourne un obj du cache
	 * @param obj
	 * @param params
	 * @return
	 */
	public Object getObjectInCache( Class<?> obj, String[] params ) {
		return getObject( obj, params );
	}

	/***
	 * retourne un obj du cache
	 * @param obj
	 * @return
	 */
	public Object getObjectInCache( Class<?> obj ) {
		return getObject( obj, null );
	}

	/***
	 * retourne un obj du cache
	 * @param obj
	 * @param params
	 * @return
	 */
	public Object getObjectInCache( Class<?> obj, String param ) {
		String[] params = null;
		if( param != null ) {
			params = new String[1];
			params[0] = param;
		}
		return getObject( obj, params );
	}

	/***
	 * retire un obj du cache defini par les params
	 * @param obj
	 * @param params
	 * @return
	 */
	public void removeObjectInCache( Class<?> obj, Object[] params ) {

		if( params != null && params.length > 0 ) {
			Object key = AbstractCachable.generateKey( params );
			Cache c = CacheFactory.getInstance().getCache( obj );
			if( LogCommun.isTechniqueDebug() )
				LogCommun.debug("CacheManager", "removeObjectInCache",
						"refresh du cache " + c.getCacheClass() + "_" + key );
			c.remove( key );
		} else
			cleanCache( obj );
	}

	/**
	 * return all the existing cacheables for a class key
	 * @param obj
	 * @return
	 */
	public List<?> getAvailablesCachables(Class<?> obj)
	{
		Cache c = CacheFactory.getInstance().getCache( obj );
		List<?> cacheables= c.cacheables();
		if(cacheables==null)
			cacheables = new ArrayList();
		return cacheables;
	}

	/***
	 * enregistre un obj dans le cache
	 * @param obj
	 * @return
	 */
	public void setObjectInCache( CachableInterface obj ) {
		Cache c = CacheFactory.getInstance().getCache( obj.getClass() );
		c.register( obj );
	}

	/***
	 * vide le cache
	 * @param obj
	 * @return
	 */
	public void cleanCache( Class<?> obj ) {
		Cache c = CacheFactory.getInstance().getCache( obj );
		if( LogCommun.isTechniqueDebug() )
			LogCommun.debug("CacheManager", "cleanCache",
					"refresh du cache " + c.getCacheClass() );
		c.cleanUp();
	}

	/***
	 * retourne la liste des caches configurés sur le serveur
	 *   ( ne doit etre utilise qu uniquement pour asaAdmin )
	 * @return List
	 */
	public List<CacheDescriptor> getCaches() throws TechnicalException {

		if( caches == null ) {
			caches = new ArrayList<CacheDescriptor>();

			InputStream in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream( fileConfCache );
			BufferedReader buff = new BufferedReader( new InputStreamReader( in ) );

			String commentDelim = FilesPropertiesCache.getInstance()
					.getValue("refresh-cache","cache.comment.delimiter");
			String groupeNameDelim = FilesPropertiesCache.getInstance()
					.getValue("refresh-cache","cache.groupe.libelle") + "=";
			String cacheNameDelim = FilesPropertiesCache.getInstance()
					.getValue("refresh-cache","cache.name.libelle") + "=";
			String cacheOptDelim = FilesPropertiesCache.getInstance()
					.getValue("refresh-cache","cache.opt") + "=";
			String cacheClassPrefix = FilesPropertiesCache.getInstance()
					.getValue("refresh-cache","cache.class.prefix");

			int groupeNameDelimSize = groupeNameDelim.length();
			int cacheNameDelimSize = cacheNameDelim.length();
			int cacheOptDelimSize = cacheOptDelim.length();
			int cacheClassPrefixSize = cacheClassPrefix.length()+1;

			try {
				String line;
				String groupeName = null;
				String cacheName = null;
				String cacheOpt = null;
				CacheDescriptor c;
				while( ( line = buff.readLine() ) != null ) {
					line = line.trim();
					if( line.startsWith( commentDelim ) ) {
						if( StringUtils.contains( line, groupeNameDelim ) ) {
							groupeName = line.substring( line.indexOf( groupeNameDelim )
									+ groupeNameDelimSize, line.indexOf( commentDelim,
									line.indexOf( groupeNameDelim ) + groupeNameDelimSize  ) );
						} else
							if( StringUtils.contains( line, cacheNameDelim ) ) {
								cacheName = line.substring( line.indexOf( cacheNameDelim )
										+ cacheNameDelimSize, line.lastIndexOf( commentDelim ) );
							} else
								if( StringUtils.contains( line, cacheOptDelim ) ) {
									cacheOpt = line.substring( line.indexOf( cacheOptDelim )
											+ cacheOptDelimSize, line.lastIndexOf( commentDelim ) );
								}
					}	else {
						if( StringUtils.contains( line, cacheClassPrefix ) ) {
							c = new CacheDescriptor( line.substring( line.indexOf( cacheClassPrefix )
									+ cacheClassPrefixSize, line.lastIndexOf( "=" ) ),
									cacheName, groupeName, cacheOpt );
							caches.add( c );
							cacheName = null;
							cacheOpt = null;
						}
					}
				}
			} catch( IOException e ){
				LogCommun.major("CacheManager","CacheManager","getCaches","Impossible de lire le fichier : " + fileConfCache );
				throw new TechnicalException( e );
			} finally {
				try {
					buff.close();
					in.close();
				} catch( Exception e ) {
					throw new TechnicalException( e );
				}
			}
		}

		return caches;
	}

	/**
	 * retourne l'identifiant de l'instance J2EE de cet objet
	 */
	public String getIdOriginator() {
		//A FAIRE
		if ( m_clusterInstanceId == null )
			throw new NullPointerException("m_clusterInstanceId est null");
		return m_clusterInstanceId;
	}

	/***
	 * Rafraichit un cache sur toutes les instances gérées
	 * @param className
	 * @param idOriginator
	 * @param option
	 * @throws TechnicalException
	 */
	public void refreshInstanceCaches( String className, String idOriginator, Object[] options )
			throws TechnicalException {

		StringBuffer sb=new StringBuffer();

		try {
			Class<?> obj = Class.forName( className );
			if( obj != null ) {
				removeObjectInCache( obj, options );
			}

			for(int i = 0;i < instances.length; i++) {
				String instance = instances[i];

				sb.delete(0, sb.length());
				sb.append("http://").append(instance).append(servletPath);
				sb.append("?");
				sb.append(MSG_PROP_CACHE_NAME).append("=").append(className).append("&");
				sb.append(MSG_PROP_ORIGINATOR_ID).append("=").append(idOriginator);
				if( options != null ) {
					for( int j=0, nbOpts=options.length; j<nbOpts; j++ )
						sb.append("&").append(MSG_PROP_REFRESH_OPTIONS).append(j).append("=").append(options[j]);
				}

				HttpClient httpCli = new HttpClient();
				GetMethod method = new GetMethod( sb.toString() );

				int httpStatut = httpCli.executeMethod(method);
				if( httpStatut != HttpStatus.SC_OK ) {
					StringBuffer msg = new StringBuffer("retour http : ").append( httpStatut );
					LogCommun.major(null,"CacheManager","refreshInstanceCaches",msg.toString());
					throw new MessageSenderException(msg.toString());
				}
			}

		} catch( ClassNotFoundException e ) {
			LogCommun.major( "weblogic", "CacheManager", "refreshInstanceCaches",
					"Impossible de rafraichir le cache : " + className, e );
			throw new TechnicalException( e );
		} catch( Exception e ) {
			LogCommun.major( "weblogic", "CacheManager", "refreshInstanceCaches",
					"Impossible de lire l'url : " + sb.toString(), e );
			throw new TechnicalException( e );
		}
	}
}