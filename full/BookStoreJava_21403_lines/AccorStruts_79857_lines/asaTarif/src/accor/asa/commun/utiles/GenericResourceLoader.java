package com.accor.asa.commun.utiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.accor.asa.commun.log.LogCommun;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 27 juin 2005
 * Time: 14:30:51
 * To change this template use File | Settings | File Templates.
 */
public class GenericResourceLoader {

    /**
     * <p>Utility method to get an InputStream for a given resoure path</p>
     *
     * @param pathToResource        path relative to the calling context.
     *                              For example: loadResource(/app.properties, false)
     *                              for an EAR/APP-INF/classes/app.properties
     * @param useContextClassLoader if <code>true</code> then use the
     *                              current Thread's context ClassLoader
     * @return an input stream for reading the resource, or <code>null</code>
     *         if the resource could not be found
     * @see java.lang.Thread#getContextClassLoader()
     */
    private static InputStream loadResource(String pathToResource, boolean useContextClassLoader) {
        if (LogCommun.isTransfertDebug()) LogCommun.debug("GenericResourceLoader", "loadResource", "loadResource with pathToResource : " + pathToResource + " useContextClassLoader " + useContextClassLoader);
        if (useContextClassLoader) {
            if (LogCommun.isTransfertDebug()) LogCommun.debug("GenericResourceLoader", "loadResource", "using classloader : " + Thread.currentThread().getContextClassLoader().getClass().getName());
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(pathToResource);
        } else {
            if (LogCommun.isTransfertDebug()) LogCommun.debug("GenericResourceLoader", "loadResource", "using classloader : " + GenericResourceLoader.class.getClassLoader().getClass().getName());
            return GenericResourceLoader.class.getClassLoader().getResourceAsStream(pathToResource);
        }
    }

    /**
     * <p>Utility method to get a Properties object for a given resoure path</p>
     *
     * @param pathToResource path relative to the calling context.
     *                       For example: loadResource(/app.properties, false)
     *                       for an EAR/APP-INF/classes/app.properties
     * @return an Properties object read from pathToResource
     * @throws IOException if the resource could not be found
     */
    public static Properties getProperties(String pathToResource) throws IOException {
        return getProperties(pathToResource, true);
    }

    /**
     * <p>Utility method to get a Properties object for a given resoure path</p>
     *
     * @param pathToResource        path relative to the calling context.
     *                              For example: loadResource(/app.properties, false)
     *                              for an EAR/APP-INF/classes/app.properties
     * @param useContextClassLoader if <code>true</code> then use the
     *                              current Thread's context ClassLoader
     * @return an Properties object read from pathToResource
     * @throws IOException if the resource could not be found
     * @see java.lang.Thread#getContextClassLoader()
     */
    public static Properties getProperties(String pathToResource, boolean useContextClassLoader)
            throws IOException {
        InputStream input = loadResource(pathToResource, useContextClassLoader);
        if (input == null) {
            throw new IOException("Resource " + pathToResource + " not found");
        }
        Properties p = new Properties();
        p.load(input);
        return p;
    }
}
