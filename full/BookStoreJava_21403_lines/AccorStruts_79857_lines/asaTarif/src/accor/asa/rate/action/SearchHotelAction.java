package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.hotel.model.SearchCriteria;
import com.accor.asa.commun.hotel.model.SearchHotelCriteria;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.common.Log;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletDispatcherResult;

import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@Results({
    @Result(name = Action.INPUT,    type = ServletDispatcherResult.class, value = "/hotel/searchHotel.jsp"),
    @Result(name = Action.SUCCESS,  type = ServletDispatcherResult.class, value = "/hotel/selectedHotel.jsp"),
    @Result(name = Action.ERROR,    type = ServletDispatcherResult.class, value = "/error.jsp")
})
public class SearchHotelAction extends MainSearchHotelAction {

    protected List<Hotel> hotels;

    /**
     * Chercher des hotels
     *
     * @return
     */
    public String search() {
        Contexte contexte = getAsaContexte();
        String login = contexte.getCodeUtilisateur();
        try {
            setHotels(searchHotels());
            return prepareForDisplay();
        } catch (RatesException e) {
            if (e instanceof RatesUserException) {
                addActionError(e.getMessage());
                return Action.INPUT;
            } else {
                Log.major(login, "SearchHotelAction", "search", e.getMessage(), e);
                addActionError(e.getMessage());
                return Action.ERROR;
            }
        }
    }

    /**
     * Add the data for display
     *
     * @param dataRate
     */
    protected void prepareAditionlDataForDisplayPage(Contexte contexte)
            throws RatesException {
        try {
            SearchHotelCriteria model = (SearchHotelCriteria)getModel();
            model.getListsProvider().initSearchHotel();
        } catch(TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
    }

    /**
     * here we can wrap the data into a useful decorator
     *
     * @param dataTaxe
     */
    protected SearchCriteria createSearchCriteria() {
        return new SearchHotelCriteria(getAsaContexte());
    }

    //-----------------------------------------------------------------
    //----------            G E T AND S E T           -----------------
    //-----------------------------------------------------------------

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}
