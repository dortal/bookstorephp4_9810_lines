package com.accor.asa.rate.action;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.hotel.metier.Room;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.decorator.ChildRateDecorator;
import com.accor.asa.rate.model.ChildRateBean;
import com.accor.asa.rate.model.Rate;
import com.accor.asa.rate.model.RateBean;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.service.process.RateFacade;
import com.accor.asa.rate.util.ContractableRateStatusManager;
import com.accor.asa.rate.util.RateFormListsProvider;
import com.opensymphony.xwork2.Action;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class ListChildRateAction extends ListHomeRateAction {

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
                ChildRateBean bean = new ChildRateBean();
                bean.setIdGrille(getGrille().getIdGrille());
                List<RateBean> rates = PoolRateFactory.getInstance().getRateFacade().getRatesList(RateFacade.CHILD_RATE_KEY, bean, contexte);
                RateFormListsProvider listsProvider = new RateFormListsProvider(getGrille(),contexte);
                List<Room> rooms = listsProvider.getRooms();
                StringBuffer sb;
                List<Rate> ratesDecorators = new ArrayList<Rate>();
                for (RateBean rate : rates) {
                    ChildRateBean childRateBean = (ChildRateBean) rate;
                    ChildRateDecorator dec = new ChildRateDecorator(childRateBean);
                    dec.setLibProduit(dec.getCodeProduit());
                    for (Room room : rooms) {
                        if(room.getCode().equals(dec.getCodeProduit())) {
                            sb = new StringBuffer(room.getNom());
                            sb.append(" (").append(dec.getCodeProduit()).append(")");
                            dec.setLibProduit(sb.toString());
                            break;
                        }
                    }
                    dec.setRowReadOnly(ContractableRateStatusManager.getAccesModeForRate(dec, isScreenReadOnly(), contexte));
                    ratesDecorators.add(dec);
                }
                setRates(ratesDecorators);
            }
            return Action.SUCCESS;
        } catch (RatesException e) {
            Log.critical(contexte.getCodeUtilisateur(), "ChildRateAction", "initListe", e.getMessage());
            addActionError(translateRateException(e));
            return Action.ERROR;
        }
    }

    /**
     * Ratourne la clé rate
     *
     * @return
     */
    protected String getRateTypeKey() {
        return RateFacade.CHILD_RATE_KEY;
    }
}
