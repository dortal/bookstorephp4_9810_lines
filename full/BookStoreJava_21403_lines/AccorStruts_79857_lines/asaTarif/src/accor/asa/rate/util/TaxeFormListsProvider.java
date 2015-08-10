package com.accor.asa.rate.util;

import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.devise.Devise;
import com.accor.asa.commun.metier.ratelevel.RateLevel;
import com.accor.asa.commun.metier.taxe.TaxeTarsBean;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.TypePrixRefBean;
import com.accor.asa.commun.reference.metier.UniteTaxeRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesParamException;
import com.accor.asa.rate.RatesTechnicalException;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class TaxeFormListsProvider {

    private Contexte contexte;
    private Hotel    hotel;

    private List<TaxeTarsBean>      tarsHotelTaxes;
    private List<RateLevel>         tarsHotelRateLevels;
    private List<UniteTaxeRefBean>  unitesTaxe;
    private List<TypePrixRefBean>   typesPrix;
    private List<Devise>            devises;

    //==========================   CONSTRUCTOR =====================================

    public TaxeFormListsProvider(Hotel hotel, Contexte contexte) {
        super();
        this.hotel = hotel;
        this.contexte = contexte;
    }

    //==========================   INIT FOR SERVICE  ==================================

    public void initTaxe() throws RatesException {
        initTarsHotelTaxes();
        initTarsHotelRateLevels();
        initTypesPrix();
        initUnitesTaxe();
        initDevises();
    }

    //==========================   INIT  ATTRIBUTES  ==================================

    public void initTarsHotelTaxes() throws RatesException {
		try {
			String codeHotel = hotel.getCode();
			tarsHotelTaxes = PoolCommunFactory.getInstance().getCommunUtilsFacade().getTaxes(codeHotel, contexte);
			if (tarsHotelTaxes == null || tarsHotelTaxes.isEmpty())
				throw new RatesParamException("Aucune taxes disponible.", "COM_PRM_MSG_NOTAXES");
		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		} catch (IncoherenceException ex) {
			throw new RatesTechnicalException(ex);
		}
	}

    public void initTarsHotelRateLevels() throws RatesException {
		try {
			String codeHotel = hotel.getCode();
			tarsHotelRateLevels = PoolCommunFactory.getInstance().getCommunUtilsFacade().getRateLevelsForHotel(codeHotel, contexte);
			if (tarsHotelRateLevels == null || tarsHotelRateLevels.isEmpty())
				throw new RatesParamException("Aucun tars ratelevel disponible.", "COM_PRM_MSG_NOTARSHOTELRATELEVEL");
		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		} catch (IncoherenceException ex) {
			throw new RatesTechnicalException(ex);
		}
	}

    public void initTypesPrix() throws RatesException {
        try {
            typesPrix = TypePrixRefBean.getCacheList(contexte).getElementsWithOutCriteria(false, true,true);
        } catch(TechnicalException ex) {
            throw new RatesTechnicalException(ex);
        } catch(IncoherenceException ex) {
            throw new RatesTechnicalException(ex);
        }
    }

    public void initUnitesTaxe() throws RatesException {
        try {
            unitesTaxe = UniteTaxeRefBean.getCacheList(contexte).getElements();
            if (unitesTaxe == null || unitesTaxe.isEmpty())
                throw new RatesParamException("No price unit available.", "COM_PRM_MSG_NOUNITSTAXE");
        } catch(TechnicalException ex) {
            throw new RatesTechnicalException(ex);
        } catch(IncoherenceException ex) {
            throw new RatesTechnicalException(ex);
        }
    }

    public void initDevises() throws RatesException {
		try {
			String codeHotel = hotel.getCode();
			devises = PoolCommunFactory.getInstance().getCommunUtilsFacade().getDevisesHotelPays(codeHotel, contexte);
			if (devises == null || devises.isEmpty())
				throw new RatesParamException("Aucune devise disponible.", "COM_PRM_MSG_NOCURRENCY");

		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		} catch (IncoherenceException ex) {
			throw new RatesTechnicalException(ex);
		}
	}

    //==========================   GETTER  ATTRIBUTES  ==================================

    public List<TaxeTarsBean> getTarsHotelTaxes()  throws RatesException{
        if(tarsHotelTaxes ==null)
            initTarsHotelTaxes();
        return tarsHotelTaxes;
    }

    public List<RateLevel> getTarsHotelRateLevels()  throws RatesException{
        if(tarsHotelRateLevels ==null)
            initTarsHotelRateLevels();
        return tarsHotelRateLevels;
    }

    public List<TypePrixRefBean> getTypesPrix() throws RatesException{
		if(typesPrix==null)
			initTypesPrix();
		return typesPrix;
	}

    public List<UniteTaxeRefBean> getUnitesTaxe()  throws RatesException{
        if(unitesTaxe==null)
            initUnitesTaxe();
        return unitesTaxe;
    }

    public List<Devise> getDevises() throws RatesException {
        if (devises == null)
            initDevises();
        return devises;
    }

}