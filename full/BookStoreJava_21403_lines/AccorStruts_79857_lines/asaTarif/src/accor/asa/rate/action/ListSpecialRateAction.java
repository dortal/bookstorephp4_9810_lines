package com.accor.asa.rate.action;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.decorator.SpecailRateDecorator;
import com.accor.asa.rate.model.Rate;
import com.accor.asa.rate.model.RateBean;
import com.accor.asa.rate.model.SpecialRateBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.service.process.RateFacade;
import com.accor.asa.rate.util.ContractableRateStatusManager;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class ListSpecialRateAction extends ListHomeRateAction {


    /**
     * Initaliser la liste des tarifs
     *
     * @return
     */
    @Override
    protected String initListe() {
        Contexte contexte = getAsaContexte();
        try {
            if (getGrille().getIdGrille() != null) {
                SpecialRateBean bean = new SpecialRateBean();
                bean.setIdGrille(getGrille().getIdGrille());
                List<RateBean> rates = PoolRateFactory.getInstance().getRateFacade().getRatesList(RateFacade.SPECIAL_RATE_KEY, bean, contexte);
                SpecialRateBean srate;
                List<Rate> ratesDecorators = new ArrayList<Rate>();
                for (RateBean rate1 : rates) {
                    srate = (SpecialRateBean) rate1;
                    SpecailRateDecorator dec = new SpecailRateDecorator(srate, contexte);
                    dec.setRowReadOnly(ContractableRateStatusManager.getAccesModeForRate(dec, isScreenReadOnly(), contexte));
                    ratesDecorators.add(dec);
                }
                setRates(ratesDecorators);
            }
            return SUCCESS;
        } catch (RatesTechnicalException e) {
            Log.critical(contexte.getCodeUtilisateur(), "ListSpecialRateAction", "initListe", e.getMessage());
            addActionError(e.getMessage());
            return ERROR;
        }
    }

    /**
     * Ratourne la clé rate
     *
     * @return
     */
    protected String getRateTypeKey() {
        return RateFacade.SPECIAL_RATE_KEY;
    }

}
