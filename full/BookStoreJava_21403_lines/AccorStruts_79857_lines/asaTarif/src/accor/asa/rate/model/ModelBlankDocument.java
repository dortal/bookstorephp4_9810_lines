package com.accor.asa.rate.model;

import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.commun.utiles.StaticDocs;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.common.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class ModelBlankDocument implements Serializable {

    private final static String FILE_NAME_GLOBAL_CONF = "global";
    private final static String MODELES_TARIF_FOLDER  = "modeles";

    private String fileName;
    private String filePath;
    private String fileUrl;
    private String contentType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(fileName).
                append(", ").append(filePath).
                append(", ").append(contentType);
        return sb.toString();
    }
    //====================================  OTHER ====================================
    /**
     * Retourne la liste des fichiers models
     * @param dirName
     * @return
     */
    public static List<ModelBlankDocument> getModelBlankDocuments(String codeLangue)
            throws RatesTechnicalException {
        try {
            //List<ModelBlankDocument> files
            ModelBlankDocumentCachList cacheList = (ModelBlankDocumentCachList)CacheManager.getInstance()
    			.getObjectInCache( ModelBlankDocumentCachList.class , codeLangue );
            if (cacheList==null) {
                List<ModelBlankDocument> files = new ArrayList<ModelBlankDocument>();
                String staticFolder = FilesPropertiesCache.getInstance().getValue(FILE_NAME_GLOBAL_CONF,"docs.home");
                staticFolder = staticFolder.replace('/', File.separatorChar);
                staticFolder = staticFolder.replace('\\', File.separatorChar);
                StringBuffer modelesFolder = new StringBuffer(staticFolder);
                modelesFolder.append(File.separatorChar).
                        append(StaticDocs.MODULE_TARIF).
                        append(File.separatorChar).
                        append(MODELES_TARIF_FOLDER).
                        append(File.separatorChar).
                        append(codeLangue.toLowerCase());
                File dir = new File(modelesFolder.toString());
                if (dir.isDirectory()) {
                    // Retourne que les fichiers properties
                    FilenameFilter filter = new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".xls") || name.endsWith(".doc");
                        }
                    };
                    String[] listFiles = dir.list(filter);
                    if (listFiles!=null && listFiles.length>0) {
                        ModelBlankDocument modelBlank;
                        StringBuffer filePath;
                        StringBuffer fileUrl;
                        for (String nameFile : listFiles) {
                            filePath = new StringBuffer(modelesFolder);
                            filePath.append(File.separatorChar).
                                    append(nameFile);
                            fileUrl = new StringBuffer(FilesPropertiesCache.getInstance().getValue(FILE_NAME_GLOBAL_CONF,"asa.document_url"));
                            fileUrl.append("/").append(StaticDocs.MODULE_TARIF).
                                    append("/").append(MODELES_TARIF_FOLDER).
                                    append("/").append(codeLangue.toLowerCase()).
                                    append("/").append(nameFile);
                            modelBlank = new ModelBlankDocument();
                            modelBlank.setFilePath(filePath.toString());
                            modelBlank.setFileUrl(fileUrl.toString());
                            if (nameFile.endsWith(".xls")) {
                                modelBlank.setFileName(nameFile.substring(0,nameFile.indexOf(".xls")));
                                modelBlank.setContentType("application/vnd.ms-excel");
                            } else {
                                modelBlank.setFileName(nameFile.substring(0,nameFile.indexOf(".doc")));
                                modelBlank.setContentType("application/vnd.ms-word");
                            }
                            files.add(modelBlank);
                        }
                    }
                }
                cacheList = new ModelBlankDocumentCachList(files, codeLangue);
                CacheManager.getInstance().setObjectInCache(cacheList);
            }
            return cacheList.getElements();
        } catch(Exception e) {
            Log.critical("","ModelBlankDocument","getFilesProperties",e.getMessage(),e);
            throw new RatesTechnicalException(e);
        }
    }
}
