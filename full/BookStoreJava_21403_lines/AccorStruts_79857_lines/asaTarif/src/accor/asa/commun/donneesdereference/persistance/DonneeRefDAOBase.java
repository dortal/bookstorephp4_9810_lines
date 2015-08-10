package com.accor.asa.commun.donneesdereference.persistance;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.donneesdereference.metier.DonneeReference;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.commun.utiles.Proprietes;

/*
 * class de base detous les DAOs spécifiques à chaque classe de donnee de reference.
 * Chaque sous classe est chargée par l'EJB DonneeRefGeneriqueBean de facon dynamique.
 * cet EJB utilise le polymorphisme pour appeler les DAO de chaque type. 
 */
public abstract class DonneeRefDAOBase implements DonneeRefDAO {
    private static long DELAI_RAFFRAICHISSEMENT_CACHE;
    /** Un cache des donnees de référence, pour chaque code langue*/
    private Map donneeRefCacheMap = new HashMap();
    /** les dates de dernier chargement des caches, pour chaque code langue */
    private Map donneeRefCacheRefreshDates = new HashMap();

    /** Un cache des donnees de référence, sous forme de Map, pour chaque code langue*/
    private Map donneeRefCacheMap2 = new HashMap();
    /** les dates de dernier chargement des caches, pour chaque code langue */
    private Map donneeRefCacheRefreshDates2 = new HashMap();
    
    static {
    	try {
			DELAI_RAFFRAICHISSEMENT_CACHE = Long.parseLong(FilesPropertiesCache.getInstance().getValue(Proprietes.PROPERTIES_FILE_NAME,Proprietes.DELAI_RAFRAICHISSEMENT_DONNEES_REF_COMMUN));
    	}
    	catch (NumberFormatException te) {
			LogCommun.major("DonneeRefDAOBase", "DonneeRefDAOBase", "initialisation", "Pas de valeur pour la propriété commun.rafraichissementDonneesRefCommunes");    		
    		DELAI_RAFFRAICHISSEMENT_CACHE = 180000;
    	}
    }
    
    /*
     * lit en base et renvoie toutes les valeurs pour une donnee de référence
     */
    public final List getDonneesRef(String codeLangue, ContexteAppelDAO context) throws TechnicalException {
        Date dateDebutMethod = new Date();
        Date donneeRefCacheRefreshDate = (Date) donneeRefCacheRefreshDates.get(codeLangue);
        if (donneeRefCacheRefreshDate == null || (dateDebutMethod.getTime() - donneeRefCacheRefreshDate.getTime()) > DELAI_RAFFRAICHISSEMENT_CACHE) {
            try {
                String shortClassName = getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
                List result =
                    SQLCallExecuter.getInstance().executeSelectProc(
                        getSelectProcName(),
                        new SQLParamDescriptorSet(new SQLParamDescriptor(codeLangue)),
                        shortClassName,
                        "super.getDonneeRef",
                        context,
                        getSQLResultSetReader());
                donneeRefCacheMap.put(codeLangue, result);
                donneeRefCacheRefreshDates.put(codeLangue, new Date());
            }
            catch (SQLException e) {
                throw new TechnicalException(e);
            }
        }
        return (List) donneeRefCacheMap.get(codeLangue);
    }

    public final Map getDonneesRefIntoMap( String codeLangue, ContexteAppelDAO context ) throws TechnicalException {
      Date dateDebutMethod = new Date();
      Date donneeRefCacheRefreshDate = (Date) donneeRefCacheRefreshDates2.get(codeLangue);

      if (donneeRefCacheRefreshDate == null || 
         (dateDebutMethod.getTime() - donneeRefCacheRefreshDate.getTime()) > DELAI_RAFFRAICHISSEMENT_CACHE) {
        HashMap map = new HashMap();
        Object obj;
        List info = getDonneesRef( codeLangue, context );
        for( Iterator it = info.iterator(); it.hasNext(); ) {
        	obj = it.next();
          if( obj instanceof DonneeReference ) {
          	map.put( ( ( DonneeReference ) obj ).getCode(), obj ); 
          }
        }
        donneeRefCacheMap2.put( codeLangue, map );
        donneeRefCacheRefreshDates2.put(codeLangue, new Date());
      }
      return (Map) donneeRefCacheMap2.get( codeLangue );
    }
    
    public List getDonneesRefAdmin(ContexteAppelDAO context) throws TechnicalException {
        try {
            String shortClassName = getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
            List result =
                SQLCallExecuter.getInstance().executeSelectProc(
                    getSelectAdminProcName(),
                    new SQLParamDescriptorSet(new SQLParamDescriptor[0]),
                    shortClassName,
                    "super.getDonneeRef",
                    context,
                    getSQLResultSetReader());
            return result;
        }
        catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    protected abstract String getSelectAdminProcName();
    public abstract String getSelectProcName();
    public abstract SQLResultSetReader getSQLResultSetReader();

    /*
     * @throws IncoherenceException si le type du paramètre ne correspond pas au type attendu.
     * (chaque implémentation de DonneeRefDAO est spécifique pour un type de donnee de reference)
     */
    public final void insertDonneeRef(DonneeReference donnee, ContexteAppelDAO contexte) throws TechnicalException, IncoherenceException {
        try {
            SQLCallExecuter.getInstance().executeUpdate(getInsertProcName(), getInsertParameters(donnee), "DonneeRefGeneriqueDAO", "insertDonneeRef", contexte);
        }
        catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public abstract String getInsertProcName();
    public abstract SQLParamDescriptorSet getInsertParameters(DonneeReference donnee);

    /*
     * @throws IncoherenceException si le type du paramètre ne correspond pas au type attendu.
     * (chaque implémentation de DonneeRefDAO est spécifique pour un type de donnee de reference)
     */
    public final void updateDonneeRef(DonneeReference donnee, ContexteAppelDAO context) throws TechnicalException, IncoherenceException {
        try {
            SQLCallExecuter.getInstance().executeUpdate(getUpdateProcName(), getUpdateParameters(donnee), "DonneeRefGeneriqueDAO", "updateDonneeRef", context);
        }
        catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public abstract String getUpdateProcName();
    public abstract SQLParamDescriptorSet getUpdateParameters(DonneeReference donnee);

    public final void deleteDonneeRef(String codeDonneeRef, ContexteAppelDAO context) throws TechnicalException {
        try {
            SQLCallExecuter.getInstance().executeUpdate(
                getDeleteProcName(),
                new SQLParamDescriptorSet(new SQLParamDescriptor(codeDonneeRef)),
                "DonneeRefGeneriqueDAO",
                "deleteDonneeRef",
                context);
        }
        catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public abstract String getDeleteProcName();
}