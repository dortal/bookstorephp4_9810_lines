package com.accor.asa.rate.action;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.hotel.metier.Room;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.decorator.LeisureRateDecorator;
import com.accor.asa.rate.model.LeisureRateBean;
import com.accor.asa.rate.model.Rate;
import com.accor.asa.rate.model.RateBean;
import com.accor.asa.rate.model.RateContractInfo;
import com.accor.asa.rate.service.PoolRateFactory;
import com.accor.asa.rate.service.process.RateFacade;
import com.accor.asa.rate.util.ContractableRateStatusManager;
import com.accor.asa.rate.util.FormatCompareUtil;
import com.accor.asa.rate.util.RateFormListsProvider;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class ListLeisureRateAction extends ListHomeRateAction {

    /**
     * Load la liste des contrats
     *
     * @param rate
     * @param contracts
     */
    private void putContractsToRate(LeisureRateDecorator rate, List<RateContractInfo> contracts) {
        for (RateContractInfo contract : contracts) {
            if (rate.getCodeRateLevel().equals(contract.getCodeRateLevel()) &&
                contract.getDateDebut() != null && contract.getDateFin() != null &&
                (FormatCompareUtil.isDateBetween(rate.getDateDebut(), contract.getDateDebut(), contract.getDateFin()) ||
                 FormatCompareUtil.isDateBetween(rate.getDateFin(), contract.getDateDebut(), contract.getDateFin()) ) ) {
                rate.addContract(contract);
                break;
            }
        }
    }

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
                LeisureRateBean bean = new LeisureRateBean();
                bean.setIdGrille(getGrille().getIdGrille());
                List<RateBean> rates = PoolRateFactory.getInstance().getRateFacade().getRatesList(RateFacade.LEISURE_RATE_KEY, bean, contexte);
                List<RateContractInfo> contracts = PoolRateFactory.getInstance().getRateFacade().getContractsPeriodes(getGrille().getIdGrille(), contexte);
                RateFormListsProvider listsProvider = new RateFormListsProvider(getGrille(),contexte);
                List<Room> rooms = listsProvider.getRooms();
                StringBuffer sb;
                List<Rate> ratesDecorators = new ArrayList<Rate>();
                for (RateBean rate : rates) {
                    LeisureRateDecorator dec = new LeisureRateDecorator((LeisureRateBean) rate, contexte);
                    putContractsToRate(dec, contracts);
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
            return SUCCESS;
        } catch (RatesException e) {
            Log.critical(contexte.getCodeUtilisateur(), "LeisureRateAction", "initListe", e.getMessage());
            addActionError(translateRateException(e));
            return ERROR;
        }
    }

    /**
     * Ratourne la clé rate
     *
     * @return
     */
    protected String getRateTypeKey() {
        return RateFacade.LEISURE_RATE_KEY;
    }

}
