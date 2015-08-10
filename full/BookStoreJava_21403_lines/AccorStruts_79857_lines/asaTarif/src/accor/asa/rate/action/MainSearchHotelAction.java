package com.accor.asa.rate.action;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.hotel.model.SearchCriteria;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.util.HotelContextManager;
import com.accor.asa.rate.util.RateScrean;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public abstract class MainSearchHotelAction extends MainAction implements ModelDriven<SearchCriteria>, Preparable {

    private SearchCriteria model;

    /**
     * Main prepare for display
     * @return
     */
    protected String prepareForDisplay() {
        String exitCode;
        try {
            prepareAditionlDataForDisplayPage(getAsaContexte());
            exitCode = Action.INPUT;
        } catch (RatesException ex) {
            exitCode = Action.ERROR;
            addActionError(translateRateException(ex));
        } catch(Exception ex) {
        	Log.major(getAsaContexte().getCodeUtilisateur(), "DetailHomeRateAction", "prepareForDisplay", "Unexpected exception:"+ex.getClass()+": "+ex.getMessage());
        	addActionError("Unexpected error");
        	exitCode=Action.ERROR;
        }
        return exitCode;
    }


    /**
     * Aller sur le formulaire de recherche hotels
     *
     * @return
     */
    public String execute() {
        return prepareForDisplay();
    }

    /**
     * retourne la liste des hotels
     * @return
     * @throws RatesException
     */
    protected List<Hotel> searchHotels() throws RatesException {
        try {
            Contexte contexte = getAsaContexte();
            String login = contexte.getCodeUtilisateur();
            String[] s = {
                    login,
                    StringUtils.isNotBlank(model.getCode())         ? model.getCode() : " ",
                    StringUtils.isNotBlank(model.getCodeMarque())   ? model.getCodeMarque() : " ",
                    StringUtils.isNotBlank(model.getNom())          ? model.getNom() : " ",
                    StringUtils.isNotBlank(model.getVille())        ? model.getVille() : " ",
                    StringUtils.isNotBlank(model.getCodeChaine())   ? model.getCodeChaine() : " ",
                    StringUtils.isNotBlank(model.getCodePays())     ? model.getCodePays() : " ",
                    StringUtils.isNotBlank(model.getCodePlace())    ? model.getCodePlace() : " ",
                    " ",
                    StringUtils.isNotBlank(model.getCodeDirOper())  ? model.getCodeDirOper() : " ",
                    StringUtils.isNotBlank(model.getCodeGroupe())   ? model.getCodeGroupe() : " ",
                    StringUtils.isNotBlank(model.getCodeLocOp())    ? model.getCodeLocOp() : " "
            };
            List<Hotel> hotels = PoolCommunFactory.getInstance().getHotelFacade().searchHotelsWithHabilitation(s, contexte);
            int nbMaxHotel = FilesPropertiesCache.getInstance().getIntValue(FILE_PROPERTIES, "rate.searchHotel.max");
            if (hotels.size() > nbMaxHotel) {
                String[] params = {String.valueOf(nbMaxHotel)};
                throw new RatesUserException(getText("COM_SEARCHHOTEL_MSG_MANYHOTELS",params));
            }
            return hotels;
        } catch(IncoherenceException e) {
            throw new RatesTechnicalException(e);
        } catch(TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
    }

    /**
     * Selectionner un hotel
     *
     * @return
     */
    public String select() {
        Contexte contexte = getAsaContexte();
        String login = contexte.getCodeUtilisateur();
        try {
            if (StringUtils.isNotBlank(model.getIdHotel())) {
                Hotel ho = PoolCommunFactory.getInstance().getHotelFacade().getHotel( model.getIdHotel(), contexte );
                if (ho != null) {
                    contexte.getUtilisateurValue().setHotelName(ho.getName());
                    contexte.setCodePays(ho.getCountryId());
                    contexte.setCodeZoneVente(ho.getSaleZoneCode());
                    setToSession(HOTEL, ho);
                    RateScrean.HotelContext oldHotelContext = (RateScrean.HotelContext) getFromSession(HOTEL_CONTEXT);
                    RateScrean.HotelContext newHotelContext = HotelContextManager.getHotelContext(ho);
                    if (oldHotelContext == null || !oldHotelContext.equals(newHotelContext))
                        model.setUrl(null);
                    setToSession(HOTEL_CONTEXT, newHotelContext);
                    return Action.SUCCESS;
                } else {
                    addActionError(getText("COM_SEARCHHOTEL_MSG_HOTELNOTFOUND"));
                    return Action.ERROR;
                }
            } else {
                addActionError(getText("COM_SEARCHHOTEL_MSG_NOHOTELCODE"));
                return Action.ERROR;
            }
        } catch (Exception e) {
            Log.major(login, "SearchHotelAction", "select", e.getMessage(), e);
            addActionError(e.getMessage());
            return Action.ERROR;
        }
    }
    
    /**
     * Préparer l'action
     *
     * @throws Exception
     */
    public void prepare() throws Exception {
        model = createSearchCriteria();
    }

    /**
     * Add the data for display
     *
     * @param dataRate
     */
    protected abstract void prepareAditionlDataForDisplayPage(Contexte contexte) throws RatesException;

    /**
     * here we can wrap the data into a useful decorator
     *
     * @param dataTaxe
     */
    abstract protected SearchCriteria createSearchCriteria();

    //-----------------------------------------------------------------
    //----------            G E T AND S E T           -----------------
    //-----------------------------------------------------------------

    public SearchCriteria getModel() {
        return model;
    }

    public void setModel(SearchCriteria model) {
        this.model = model;
    }

    public boolean isSearchScreen() {
        return true;
    }
}
