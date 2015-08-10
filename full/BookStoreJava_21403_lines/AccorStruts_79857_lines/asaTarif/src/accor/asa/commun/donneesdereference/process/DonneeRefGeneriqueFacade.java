package com.accor.asa.commun.donneesdereference.process;

import java.util.List;
import java.util.Map;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.donneesdereference.metier.DonneeReference;
import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.process.IncoherenceException;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 14 juin 2005
 * Time: 10:45:58
 * To change this template use File | Settings | File Templates.
 */
public interface DonneeRefGeneriqueFacade {
    /** Le nom du pool d'objet*/
    public static final String POOL_NAME   = "communDonneeRefGeneriqueFacade";
    /**
     * getDonneesRef
     * @param typeDonneeRef
     * @param codeLangue
     * @param context
     * @return une liste d'instance de typeDonneeRef pour Vente (statut = MEL)
     * @throws TechnicalException
     */
    public List getDonneesRef(Class typeDonneeRef, String codeLangue, ContexteAppelDAO context) throws TechnicalException;

    /**
     * getDonneesRefIntoMap
     * @param typeDonneeRef
     * @param codeLangue
     * @param context
     * @return une map d'instance de typeDonneeRef pour Vente (statut = MEL)
     * @throws TechnicalException
     */
    public Map getDonneesRefIntoMap(Class typeDonneeRef, String codeLangue, ContexteAppelDAO context) throws TechnicalException;

    /**
     * getDonneesRefAdmin
     * @param typeDonneeRef
     * @param context
     * @return une liste d'instance de typeDonneeRef pour Admin (langue = GB et statut indifferent)
     * @throws TechnicalException
     */
    public List getDonneesRefAdmin(Class typeDonneeRef, ContexteAppelDAO context) throws TechnicalException;

    /**
     * Insère ou update un objet, en fonction de son état (nouveau ou existant)
     * @param donnee
     * @param context
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void saveDonneeRef(DonneeReference donnee, ContexteAppelDAO context) throws TechnicalException, IncoherenceException;
    /**
     * deleteDonneeRef
     * @param typeDonneeRef
     * @param codeDonneeRef
     * @param context
     * @throws TechnicalException
     * @throws IncoherenceException
     */
    public void deleteDonneeRef(Class typeDonneeRef, String codeDonneeRef, ContexteAppelDAO context) throws TechnicalException, IncoherenceException;

}
