package com.accor.asa.rate.util;

import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.hotel.metier.Room;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.devise.Devise;
import com.accor.asa.commun.metier.donneesdereference.Continent;
import com.accor.asa.commun.metier.donneesdereference.Pays;
import com.accor.asa.commun.metier.ratelevel.RateLevel;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.ParamDureeSejourRefBean;
import com.accor.asa.commun.reference.metier.DivisionSemaineRefBean;
import com.accor.asa.commun.reference.metier.DureeSejourRefBean;
import com.accor.asa.commun.reference.metier.MealPlanRefBean;
import com.accor.asa.commun.reference.metier.OffreSpecialeRefBean;
import com.accor.asa.commun.reference.metier.ParamMealPlanMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamMealPlanRefBean;
import com.accor.asa.commun.reference.metier.ParamPeriodeGenFamTarifMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamPeriodeGenFamTarifRefBean;
import com.accor.asa.commun.reference.metier.PeriodeGeneriqueRefBean;
import com.accor.asa.commun.reference.metier.PetitDejRefBean;
import com.accor.asa.commun.reference.metier.TypePrixRefBean;
import com.accor.asa.commun.reference.metier.UnitePrixRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.RatesParamException;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.service.PoolRateFactory;

public class RateFormListsProvider {

    private Contexte contexte;
    private GrilleBean grille;

    private List<Room>      rooms;
	private List<RateLevel> rateLevels;
	private List<Devise>    devises;

	private List<PetitDejRefBean>           petitDejs;
	private List<DivisionSemaineRefBean>    divisionSemaines;
	private List<DureeSejourRefBean>        dureesSejours;
	private List<DureeSejourRefBean>        dureesSejoursSalons;
	private ParamMealPlanMappedRefBean      paramMealPlanMap;
	private ParamPeriodeGenFamTarifMappedRefBean paramPerGenFamarifMap;
	
    private List<OffreSpecialeRefBean>  offreSpeciales;
    private List<TypePrixRefBean>       typesPrix;
    private List<UnitePrixRefBean>      unitesPrix;
    private List<Pays>                  pays;
    private List<Continent>             continents;

    //==========================   CONSTRUCTOR =====================================

    public RateFormListsProvider(GrilleBean grille, Contexte contexte) {
		super();
		this.grille = grille;
		this.contexte = contexte;
	}

    //==========================   INIT FOR SERVICE  ==================================

    public void initBusiness() throws RatesException {
		initRooms();
		initRateLevels();
		initDevises();
		initPetitDejs();
		initDivisionsSemaines();
		initPeriodesGenerique();
	}
	
	public void initAdagio() throws RatesException {
		initBusiness();
		initDureesSejours();
	}
	
	public void initLeisure() throws RatesException {
		initRooms();
		initRateLevels();
		initDevises();
		initPetitDejs();
		initDivisionsSemaines();
		initMealPlans();
		initPeriodesGenerique();
	}

    public void initSpecial() throws RatesException {
        initOffreSpeciales();
        initTypesPrix();
        initDevises();
        initPays();
        initContinents();
    }

    public void initChild() throws RatesException {
		initRooms();
		initRateLevels();
		initDevises();
        initTypesPrix();
	}

    //==========================   INIT  ATTRIBUTES  ==================================

    public void initRooms() throws RatesException {
		try {
			String codeHotel = grille.getCodeHotel();
			rooms = PoolCommunFactory.getInstance().getCommunUtilsFacade().getRooms(codeHotel, contexte);
			if (rooms == null || rooms.isEmpty())
				throw new RatesParamException("Aucun type de chambres disponible.", "COM_PRM_MSG_NOROOMS");
		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		} catch (IncoherenceException ex) {
			throw new RatesTechnicalException(ex);
		}
	}

	public void initRateLevels() throws RatesException {
		String codeAsaCategory = grille.getHotel().getCodeAsaCategory();
		int idPeriodeValidite = grille.getIdPeriodeValidite();
		int idFamilleTarif = grille.getIdFamilleTarif();
		rateLevels = PoolRateFactory.getInstance().getRateFacade().getRateLevelsList(codeAsaCategory, idFamilleTarif, idPeriodeValidite, contexte);
		if (rateLevels == null || rateLevels.isEmpty())
			throw new RatesParamException("Aucun ratelevel disponible.", "COM_PRM_MSG_NORATELEVELS");
	}

	public void initDevises() throws RatesException {
		try {
			String codeHotel = grille.getCodeHotel();
			devises = PoolCommunFactory.getInstance().getCommunUtilsFacade().getDevisesHotelPays(codeHotel, contexte);
			if (devises == null || devises.isEmpty())
				throw new RatesParamException("Aucune devise disponible.", "COM_PRM_MSG_NOCURRENCY");

		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		} catch (IncoherenceException ex) {
			throw new RatesTechnicalException(ex);
		}
	}

	public void initDivisionsSemaines() throws RatesException {
		try {
			divisionSemaines = DivisionSemaineRefBean.getCacheList(contexte).getElements();
			if (divisionSemaines.isEmpty())
				throw new RatesParamException("No week division available.", "COM_PRM_MSG_NODIVWEEK");

		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		} catch (IncoherenceException ex) {
			throw new RatesTechnicalException(ex);
		}
	}

	public void initPetitDejs() throws RatesException {
		try {
			petitDejs = PetitDejRefBean.getCacheList(contexte).getElements();
			if (petitDejs.isEmpty())
				throw new RatesParamException("No breakfast available.", "COM_PRM_MSG_NOBREAKFAST");

		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		} catch (IncoherenceException ex) {
			throw new RatesTechnicalException(ex);
		}
	}
	public void initMealPlans() throws RatesException {
		String codeAsaCategory = grille.getHotel().getCodeAsaCategory();
		int idFamilleTarif = grille.getIdFamilleTarif();
		try {
			paramMealPlanMap = ParamMealPlanRefBean.getCacheList(contexte).getMealPlanParametrage(codeAsaCategory, idFamilleTarif, contexte);

		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		} catch (IncoherenceException ex) {
			throw new RatesTechnicalException(ex);
		}
	}
	public void initPeriodesGenerique() throws RatesException {
		
		int idFamilleTarif = grille.getIdFamilleTarif();
		try {
			paramPerGenFamarifMap = ParamPeriodeGenFamTarifRefBean.getCacheList(contexte).getParametrageForFamilleTarif(idFamilleTarif, contexte);
			
		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		} catch (IncoherenceException ex) {
			throw new RatesTechnicalException(ex);
		}
	}
	
	public void initDureesSejours() throws RatesException {
		try {
            dureesSejours=ParamDureeSejourRefBean.getCacheList(contexte).getDureesSejourForHotel(grille.getHotel().getChainCode(), false, contexte);
            if (dureesSejours.isEmpty())
                throw new RatesParamException("No length of stay available.", "COM_PRM_MSG_NODUREESEJOUR");
            dureesSejoursSalons= ParamDureeSejourRefBean.getCacheList(contexte).getDureesSejourForHotel(grille.getHotel().getChainCode(), true, contexte);
                if (dureesSejours.isEmpty())
                    throw new RatesParamException("No length of stay available for saloons.", "COM_PRM_MSG_NODUREESEJOUR_SALON");
		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		} catch (IncoherenceException ex) {
			throw new RatesTechnicalException(ex);
		}
	}

    public void initTypesPrix() throws RatesException {
        try {
            typesPrix = TypePrixRefBean.getCacheList(contexte).getElements();
        } catch(TechnicalException ex) {
            throw new RatesTechnicalException(ex);
        } catch(IncoherenceException ex) {
            throw new RatesTechnicalException(ex);
        }
    }

    public void initUnitesPrix() throws RatesException {
        try {
            unitesPrix = UnitePrixRefBean.getCacheList(contexte).getElements();
            if (unitesPrix == null || unitesPrix.isEmpty())
                throw new RatesParamException("No price unit available.", "COM_PRM_MSG_NOUNITSPRICE");
        } catch(TechnicalException ex) {
            throw new RatesTechnicalException(ex);
        } catch(IncoherenceException ex) {
            throw new RatesTechnicalException(ex);
        }
    }

    public void initOffreSpeciales() throws RatesException {
        try {
            offreSpeciales = OffreSpecialeRefBean.getCacheList(contexte).getElements();
        } catch(TechnicalException ex) {
            throw new RatesTechnicalException(ex);
        } catch(IncoherenceException ex) {
            throw new RatesTechnicalException(ex);
        }
    }

    public void initPays() throws RatesException {
        try {
            pays = PoolCommunFactory.getInstance().getCommunUtilsFacade().getPays(contexte);
            if (pays == null || pays.isEmpty())
                throw new RatesParamException("No contry available.", "COM_PRM_MSG_NOCOUNTRIES");
        } catch(TechnicalException ex) {
            throw new RatesTechnicalException(ex);
        } catch(IncoherenceException ex) {
            throw new RatesTechnicalException(ex);
        }
    }

    public void initContinents() throws RatesException {
        try {
            continents = PoolCommunFactory.getInstance().getCommunUtilsFacade().getContinents(contexte);
            if (continents == null || continents.isEmpty())
                throw new RatesParamException("No contient available.", "COM_PRM_MSG_NOCONTINENTS");
        } catch(TechnicalException ex) {
            throw new RatesTechnicalException(ex);
        } catch(IncoherenceException ex) {
            throw new RatesTechnicalException(ex);
        }
    }

    //==========================   GETTER  ATTRIBUTES  ==================================

    public List<Room> getRooms() throws RatesException {
		if (rooms == null)
			initRooms();
		return rooms;
	}

	public List<RateLevel> getRateLevels() throws RatesException {
		if (rateLevels == null)
			initRateLevels();
		return rateLevels;
	}

	public List<Devise> getDevises() throws RatesException {
		if (devises == null)
			initDevises();
		return devises;
	}

	public List<PetitDejRefBean> getPetitDejs() throws RatesException {
		if (petitDejs == null)
			initPetitDejs();
		return petitDejs;
	}

	public List<DivisionSemaineRefBean> getDivisionSemaines() throws RatesException {
		if (divisionSemaines == null)
			initDivisionsSemaines();
		return divisionSemaines;
	}

	public List<PeriodeGeneriqueRefBean> getPeriodesGeneriques() throws RatesException{
		if(paramPerGenFamarifMap==null)
			initPeriodesGenerique();
		return paramPerGenFamarifMap.getPeriodes();
	}

	public String getDefaultCodePeriodeGenerique() throws RatesException{
		if(paramPerGenFamarifMap==null)
			initPeriodesGenerique();
		String code = paramPerGenFamarifMap.getCodeDefaultPeriode();
		if (code == null)
			code = "";
		return code;
	}
	
	public List<MealPlanRefBean> getListMealplans() throws RatesException{
		if(paramMealPlanMap==null)
			initMealPlans();
		return paramMealPlanMap.getMealPlans();
	}
	
	public List<DureeSejourRefBean> getDureesSejour() throws RatesException
	{
		if(dureesSejours==null)
			initDureesSejours();
		return dureesSejours;
	}
	
	public List<DureeSejourRefBean> getDureesSejourForSalons() throws RatesException
	{
		if(dureesSejoursSalons==null)
			initDureesSejours();
		return dureesSejoursSalons;

	}

    public List<OffreSpecialeRefBean> getOffreSpeciales() throws RatesException{
        if(offreSpeciales==null)
            initOffreSpeciales();
        return offreSpeciales;
    }

    public List<TypePrixRefBean> getTypesPrix() throws RatesException{
		if(typesPrix==null)
			initTypesPrix();
		return typesPrix;
	}

    public List<UnitePrixRefBean> getUnitesPrix()  throws RatesException{
        if(unitesPrix==null)
            initUnitesPrix();
        return unitesPrix;
    }

    public List<Pays> getPays()  throws RatesException{
        if(pays==null)
            initPays();
        return pays;
    }

    public List<Continent> getContinents() throws RatesException{
        if(continents==null)
            initContinents();
        return continents;
    }

}

