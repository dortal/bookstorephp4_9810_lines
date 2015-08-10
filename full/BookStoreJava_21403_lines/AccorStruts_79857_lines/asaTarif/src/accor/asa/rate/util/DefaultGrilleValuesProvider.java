package com.accor.asa.rate.util;

import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.MealPlanRefBean;
import com.accor.asa.commun.reference.metier.MealPlanRefCacheList;
import com.accor.asa.commun.reference.metier.ParamGrilleData;
import com.accor.asa.commun.reference.metier.ParamGrilleMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamGrilleRefBean;
import com.accor.asa.commun.reference.metier.ParamMealPlanMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamMealPlanRefBean;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.model.GrilleBean;

public class DefaultGrilleValuesProvider {

    private GrilleBean grille;
    private Contexte contexte = null;
    private boolean initialized = false;

    private ParamGrilleMappedRefBean paramGrilleMap;
    private ParamMealPlanMappedRefBean paramMealPlanMap;

    public DefaultGrilleValuesProvider(GrilleBean grille, Contexte contexte) {
        this.grille = grille;
        this.contexte = contexte;
    }

    public void init() throws RatesTechnicalException {
        String codeAsaCategory = grille.getHotel().getCodeAsaCategory();
        int idPeriodeValidite = grille.getIdPeriodeValidite();
        int idFamilleTarif = grille.getIdFamilleTarif();
        try {
            paramGrilleMap = ParamGrilleRefBean.getCacheList(contexte).getParamGrille(idPeriodeValidite, codeAsaCategory, false, contexte);
            paramMealPlanMap = ParamMealPlanRefBean.getCacheList(contexte).getMealPlanParametrage(codeAsaCategory, idFamilleTarif, contexte);
            //paramRateLevels = ParamRateLevelRefBean.getCacheList(contexte).getParamData(codeAsaCategory, idFamilleTarif, idPeriodeValidite);
            fillMeaplansInParamGrille(paramGrilleMap, contexte);
            initialized = true;
        } catch (IncoherenceException e) {
            throw new RatesTechnicalException(e);
        }
        catch (TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
    }

    private void fillMeaplansInParamGrille(ParamGrilleMappedRefBean paramGrilleMap, Contexte contexte) throws IncoherenceException, TechnicalException {
        MealPlanRefCacheList cache = MealPlanRefBean.getCacheList(contexte);
        List<String> codesRateLevel = paramGrilleMap.getCodesRateLevels();
        for (String codeRateLevel : codesRateLevel) {
            ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
            MealPlanRefBean mp = cache.getMealplanByCode(data.getCodeMealPlan());
            data.setMealPlan(mp);
        }
    }

    public boolean isDefaultPdjInclu(String codeRateLevel) throws RatesTechnicalException {
        if (!initialized)
            init();
        MealPlanRefBean mp;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            return data.getMealPlan().isPdjInclu();

        mp = paramMealPlanMap.getDefaultMealPlan();
        return mp != null && mp.isPdjInclu();

    }

    public String getDefaultCodeMealplan(String codeRateLevel) throws RatesTechnicalException {
        if (!initialized)
            init();
        MealPlanRefBean mp;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            return data.getCodeMealPlan();

        mp = paramMealPlanMap.getDefaultMealPlan();
        if (mp != null)
            return mp.getCode();
        return "";
    }

    public Double getValueCommission(String codeRateLevel) throws RatesTechnicalException {
        if (!initialized)
            init();
        Double value = null;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            value = data.getValueCommission();
        return value;
    }

    public String getUniteCommission(String codeRateLevel) throws RatesTechnicalException {
        if (!initialized)
            init();
        String value = "";
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            value = data.getUniteCommission();
        return value;
    }

    public boolean isNewContrat(String codeRateLevel) throws RatesTechnicalException {
        if (!initialized)
            init();
        boolean flag = false;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            flag = data.isNewContrat();
        return flag;
    }

    public boolean isBlackOutDates(String codeRateLevel) throws RatesTechnicalException {
        if (!initialized)
            init();
        boolean flag = false;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            flag = data.isBlackOutDates();
        return flag;
    }

    public int getRateLevelValidity(String codeRateLevel) throws RatesTechnicalException {
        if(!initialized)
              init();
        int validite = 1;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            validite = data.isOnYearOnly()?1:2;
        return validite;
    }

    public int getDefaultIdDureeSejour(String codeRateLevel) throws RatesTechnicalException {
        if (!initialized)
            init();
        int id = 1;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            id = data.getIdDureeSejour();
        return id;
    }


    public boolean getDefaultLunWe(String codeRateLevel) throws RatesTechnicalException {
        if(!initialized)
              init();
        boolean isWe = false;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            isWe = data.isLunWe();
        return isWe;
    }

    public boolean getDefaultMarWe(String codeRateLevel) throws RatesTechnicalException {
        if(!initialized)
              init();
        boolean isWe = false;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            isWe = data.isMarWe();
        return isWe;
    }

    public boolean getDefaultMerWe(String codeRateLevel) throws RatesTechnicalException {
        if(!initialized)
              init();
        boolean isWe = false;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            isWe = data.isMerWe();
        return isWe;
    }

    public boolean getDefaultJeuWe(String codeRateLevel) throws RatesTechnicalException {
        if(!initialized)
              init();
        boolean isWe = false;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            isWe = data.isJeuWe();
        return isWe;
    }

    public boolean getDefaultVenWe(String codeRateLevel) throws RatesTechnicalException {
        if(!initialized)
              init();
        boolean isWe = false;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            isWe = data.isVenWe();
        return isWe;
    }

    public boolean getDefaultSamWe(String codeRateLevel) throws RatesTechnicalException {
        if(!initialized)
              init();
        boolean isWe = false;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            isWe = data.isSamWe();
        return isWe;
    }

    public boolean getDefaultDimWe(String codeRateLevel) throws RatesTechnicalException {
        if(!initialized)
              init();
        boolean isWe = false;
        ParamGrilleData data = paramGrilleMap.getParamGrilleData(codeRateLevel);
        if (data != null)
            isWe = data.isDimWe();
        return isWe;
    }



}
