package com.accor.asa.commun.hotel.util;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.metier.donneesdereference.Pays;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.GroupeTarifRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;

import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class SearchHotelListsProvider {
    private Contexte contexte;

    protected List<Element>     marques;
    protected List<Element>     chaines;
    protected List<AsaCategory> categories;
    protected List<Pays>        pays;


    private int idGroupeTarif;
    protected List<GroupeTarifRefBean> groupesTarif;
    protected List<PeriodeValiditeRefBean> periodesValidite;

    //==========================   CONSTRUCTOR =====================================


    public SearchHotelListsProvider(Contexte contexte) {
        super();
        this.contexte = contexte;
    }
    //==========================   INIT FOR SERVICE  ==================================
    public void initSearchHotel() throws TechnicalException {
        initMarques();
        initChaines();
        initCategories();
        initPays();
    }
    public void initSearchValidation(int idGroupesTarif) throws TechnicalException {
        setIdGroupeTarif(idGroupesTarif);
        initMarques();
        initChaines();
        initCategories();
        initPays();
        initGroupesTarif();
        initPeriodesValidite(idGroupesTarif);
    }
    //==========================   INIT  ATTRIBUTES  ==================================
    public void initMarques() throws TechnicalException {
        try {
            marques = PoolCommunFactory.getInstance().getCommunUtilsFacade().getMarques(contexte);
            if (marques == null || marques.isEmpty())
                throw new TechnicalException("No brand available.");
        } catch(IncoherenceException ex) {
            throw new TechnicalException(ex);
        }
    }
    public void initChaines() throws TechnicalException {
        try {
            chaines = PoolCommunFactory.getInstance().getCommunUtilsFacade().getChaines(contexte);
            if (chaines == null || chaines.isEmpty())
                throw new TechnicalException("No Chain available.");
        } catch(IncoherenceException ex) {
            throw new TechnicalException(ex);
        }
    }
    public void initCategories() throws TechnicalException {
        try {
            categories = PoolCommunFactory.getInstance().getCommunUtilsFacade().getCategories(contexte);
            if (categories == null || categories.isEmpty())
                throw new TechnicalException("No categories available.");
        } catch(IncoherenceException ex) {
            throw new TechnicalException(ex);
        }
    }
    public void initPays() throws TechnicalException {
        try {
            pays = PoolCommunFactory.getInstance().getCommunUtilsFacade().getPays(contexte);
            if (pays == null || pays.isEmpty())
                throw new TechnicalException("No contry available.");
        } catch(IncoherenceException ex) {
            throw new TechnicalException(ex);
        }
    }
    public void initGroupesTarif() throws TechnicalException {
        try {
            groupesTarif = GroupeTarifRefBean.getCacheList(contexte).getElements();
            if (groupesTarif == null || groupesTarif.isEmpty())
                throw new TechnicalException("No tariffs group available.");
        } catch(IncoherenceException ex) {
            throw new TechnicalException(ex);
        }
    }
    public void initPeriodesValidite(int idGroupesTarif) throws TechnicalException {
        try {
            periodesValidite = PeriodeValiditeRefBean.getCacheList(contexte).getPeriodesForGroupeTarif(idGroupesTarif);
            if (periodesValidite == null || periodesValidite.isEmpty())
                throw new TechnicalException("No validity periods available.");
        } catch(IncoherenceException ex) {
            throw new TechnicalException(ex);
        }
    }
    //==========================   GETTER  ATTRIBUTES  ==================================
    public List<Element> getMarques()  throws TechnicalException{
        if(marques==null)
            initMarques();
        return marques;
    }
    public List<Element> getChaines()  throws TechnicalException{
        if(chaines==null)
            initChaines();
        return chaines;
    }
    public List<AsaCategory> getCategories()  throws TechnicalException{
        if(categories==null)
            initCategories();
        return categories;
    }
    public List<Pays> getPays()  throws TechnicalException{
        if(pays==null)
            initPays();
        return pays;
    }

    public int getIdGroupeTarif() {
        return idGroupeTarif;
    }

    public void setIdGroupeTarif(int idGroupeTarif) {
        this.idGroupeTarif = idGroupeTarif;
    }

    public List<GroupeTarifRefBean> getGroupesTarif()  throws TechnicalException{
        if(groupesTarif==null)
            initGroupesTarif();
        return groupesTarif;
    }

    public List<PeriodeValiditeRefBean> getPeriodesValidite()  throws TechnicalException{
        if(periodesValidite==null)
            initPeriodesValidite(getIdGroupeTarif());
        return periodesValidite;
    }
}
