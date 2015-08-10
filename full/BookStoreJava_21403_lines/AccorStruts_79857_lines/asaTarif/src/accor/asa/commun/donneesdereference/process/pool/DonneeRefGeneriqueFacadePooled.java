package com.accor.asa.commun.donneesdereference.process.pool;

import java.util.List;
import java.util.Map;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.donneesdereference.metier.DonneeReference;
import com.accor.asa.commun.donneesdereference.persistance.DonneeRefDAO;
import com.accor.asa.commun.donneesdereference.process.DonneeRefGeneriqueFacade;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.process.IncoherenceException;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 juin 2005
 * Time: 10:47:59
 * To change this template use File | Settings | File Templates.
 */
public class DonneeRefGeneriqueFacadePooled implements DonneeRefGeneriqueFacade{
    /**
     * Constructeur
     */
    public DonneeRefGeneriqueFacadePooled() {
    }

    /**
     * getDonneesRef
     * @param typeDonneeRef
     * @param codeLangue
     * @param context
     * @return
     * @throws TechnicalException
     */
    public List getDonneesRef(Class typeDonneeRef, String codeLangue, ContexteAppelDAO context) throws TechnicalException {
        return getDonneeRefDAO(typeDonneeRef).getDonneesRef(codeLangue, context);
    }

    /**
     * getDonneesRefIntoMap
     * @param typeDonneeRef
     * @param codeLangue
     * @param context
     * @return
     * @throws TechnicalException
     */
    public Map getDonneesRefIntoMap(Class typeDonneeRef, String codeLangue, ContexteAppelDAO context) throws TechnicalException {
      return getDonneeRefDAO(typeDonneeRef).getDonneesRefIntoMap(codeLangue, context);
    }

    /**
     * getDonneesRefAdmin
     * @param typeDonneeRef
     * @param context
     * @return
     * @throws TechnicalException
     */
    public List getDonneesRefAdmin(Class typeDonneeRef, ContexteAppelDAO context) throws TechnicalException{
        return getDonneeRefDAO(typeDonneeRef).getDonneesRefAdmin(context);
    }

    /**
     * saveDonneeRef
     * @param donnee
     * @param context
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void saveDonneeRef(DonneeReference donnee, ContexteAppelDAO context) throws TechnicalException, IncoherenceException {
        if (DonneeReference.MODIFIE.equals(donnee.getStatut()))
        {
            getDonneeRefDAO(donnee.getClass()).updateDonneeRef(donnee, context);
        }
        else if (DonneeReference.NOUVEAU.equals(donnee.getStatut()))
        {
            getDonneeRefDAO(donnee.getClass()).insertDonneeRef(donnee, context);
        }
        else LogCommun.warn("", "DonneeRefGeneriqueBean", "saveDonneeRef", "L'objet à enregistrer n'est ni nouveau ni modifié");
    }

    /**
     * deleteDonneeRef
     * @param typeDonneeRef
     * @param codeDonneeRef
     * @param context
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void deleteDonneeRef(Class typeDonneeRef, String codeDonneeRef, ContexteAppelDAO context) throws TechnicalException, IncoherenceException {
        getDonneeRefDAO(typeDonneeRef).deleteDonneeRef(codeDonneeRef, context);
    }

    /**
     * getDonneeRefDAO
     * @param typeDonneeRef
     * @return
     * @throws TechnicalException
     */
    private DonneeRefDAO getDonneeRefDAO(Class typeDonneeRef) throws TechnicalException
    {
        String typeName =typeDonneeRef.getName();
        String shortTypeName = typeName.substring(typeName.lastIndexOf(".") + 1);
        String daoClassName = "com.accor.asa.commun.donneesdereference.persistance." + shortTypeName + "DAO";
        try
        {
            Class daoClass = Class.forName(daoClassName);
            DonneeRefDAO dao = (DonneeRefDAO)daoClass.newInstance();
            return dao;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new TechnicalException ("Pas de classe DAO pour les donnes de reference de type : " + typeDonneeRef.getName() + "\n" + e.getMessage());
        } catch (InstantiationException e) {
            throw new TechnicalException ("impossible d'instancier la class DAO : " + daoClassName + "\n" + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new TechnicalException ("impossible d'instancier la class DAO : " + daoClassName + "\n" + e.getMessage());
        }
    }
}
