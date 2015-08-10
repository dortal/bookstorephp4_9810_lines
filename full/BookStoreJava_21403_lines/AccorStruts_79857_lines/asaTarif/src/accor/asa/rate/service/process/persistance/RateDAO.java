package com.accor.asa.rate.service.process.persistance;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.TypePrixRefBean;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.asa.rate.RatesUserException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.ChildRateBean;
import com.accor.asa.rate.model.ChildRateRowBean;
import com.accor.asa.rate.model.ChildRateServiceData;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.model.Rate;
import com.accor.asa.rate.model.RateBean;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public abstract class RateDAO extends DAO {

	// Type
	protected static final int SELECT = 1;
	protected static final int INSERT = 2;
	protected static final int DELETE = 3;
	protected static final int SELECT_BY_KEY = 5;
	protected static final int IMPORT = 100;

	protected abstract String getProcName(int type);

	protected abstract List<SQLParamDescriptorSet> getProcParameters(int type, Rate bean);

	protected abstract SQLResultSetReader getProcReader(int type);

	/**
	 * checkProcName
	 * 
	 * @param type
	 * @return
	 * @throws TechnicalException
	 */
	private String checkProcName(int type) throws RatesTechnicalException {
		String procName = getProcName(type);
		if (StringUtils.isBlank(procName))
			throw new RatesTechnicalException("getProcName(" + type + ") : methode non-implementee");
		return procName;
	}

	/**
	 * checkProcParameters
	 * 
	 * @param type
	 * @param RateBean
	 * @param codeLangue
	 * @return
	 * @throws TechnicalException
	 */
	private List<SQLParamDescriptorSet> checkProcParameters(int type, Rate RateBean) throws RatesTechnicalException {
		List<SQLParamDescriptorSet> procParameters = getProcParameters(type, RateBean);
		if (procParameters == null)
			throw new RatesTechnicalException("getProcParameters(" + type + ") : methode non-implementee");
		if (procParameters.size() == 0)
			throw new RatesTechnicalException("getProcParameters(" + type + ") : nombre params egale zero");
		return procParameters;
	}

	/**
	 * checkProcReader
	 * 
	 * @param type
	 * @return
	 * @throws TechnicalException
	 */
	private SQLResultSetReader checkProcReader(int type) throws RatesTechnicalException {
		SQLResultSetReader procReader = getProcReader(type);
		if (procReader == null)
			throw new RatesTechnicalException("getProcReader(" + type + ") : methode non-implementee");
		return procReader;
	}

    private List<RateBean> formatResult(RateBean bean, List<RateBean> result, Contexte contexte)
            throws RatesTechnicalException {
        try {
            if (bean instanceof ChildRateBean) {
                List<RateBean> rates = new ArrayList<RateBean>();
                ChildRateRowBean row;
                for (Rate rate : result) {
                    row = (ChildRateRowBean) rate;
                    String rateKey = row.generateBeanKey();
                    ChildRateBean childRateBean = getExistingChildRateBean(rates, rateKey);
                    if (childRateBean == null) {
                        childRateBean = ChildRateBean.createForRow(row);
                        rates.add(childRateBean);
                    }
                    ChildRateServiceData serviceData = ChildRateServiceData.createFromRow(row);
                    serviceData.setAgeActiv(true);
                    TypePrixRefBean typePrix;
                    typePrix = TypePrixRefBean.getCacheList(contexte).
                            getTypePrixById(serviceData.getAccomodationIdTypePrix());
                    serviceData.setAccomodationLabelTypePrix((typePrix!=null)?typePrix.getLibelle():"Type Prix!!!");

                    typePrix = TypePrixRefBean.getCacheList(contexte).
                            getTypePrixById(serviceData.getBreakfastIdTypePrix());
                    serviceData.setBreakfastLabelTypePrix((typePrix!=null)?typePrix.getLibelle():"Type Prix!!!");

                    typePrix = TypePrixRefBean.getCacheList(contexte).
                            getTypePrixById(serviceData.getMealIdTypePrix());
                    serviceData.setMealLabelTypePrix((typePrix!=null)?typePrix.getLibelle():"Type Prix!!!");
                    childRateBean.addService(serviceData);
                }
                return rates;
            } else {
                return result;
            }
        } catch (IncoherenceException e) {
            throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
    }

    /**
	 * Méthode permettant de récupérer la liste des tarifs
	 * 
	 * @param contexte
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<RateBean> getRatesList(RateBean bean, Contexte contexte) throws RatesTechnicalException {
		try {
			String procName = checkProcName(SELECT);
			List<SQLParamDescriptorSet> procParams = checkProcParameters(SELECT, bean);
			SQLResultSetReader procReader = checkProcReader(SELECT);
			List<RateBean> result = (List<RateBean>) SQLCallExecuter.getInstance().executeSelectProc(procName, procParams.get(0), "RateDAO", "getRateList", contexte.getContexteAppelDAO(), procReader);
			Log.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "SELECT", "");
			Log.debug("RateDAO", "getRateList", "Taille resultat = " + result.size());
            return formatResult(bean, result, contexte);
        } catch (SQLException e) {
			throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
	}

	public RateBean getRateByKey(RateBean bean, Contexte contexte) throws RatesTechnicalException {
		try {
            String procName = checkProcName(SELECT_BY_KEY);
			List<SQLParamDescriptorSet> procParams = checkProcParameters(SELECT_BY_KEY, bean);
			SQLResultSetReader procReader = checkProcReader(SELECT_BY_KEY);
            if (bean instanceof ChildRateBean) {
                ChildRateRowBean row;
                ChildRateBean childRateBean = null;
                List<ChildRateRowBean> rows = (List<ChildRateRowBean>)SQLCallExecuter.getInstance().executeSelectProc(procName, procParams.get(0), "RateDAO", "getRateByKey", contexte.getContexteAppelDAO(), procReader);
                for (Rate rate : rows) {
                    row = (ChildRateRowBean) rate;
                    if (childRateBean == null)
                        childRateBean = ChildRateBean.createForRow(row);
                    ChildRateServiceData serviceData = ChildRateServiceData.createFromRow(row);
                    serviceData.setAgeActiv(true);
                    TypePrixRefBean typePrix;
                    typePrix = TypePrixRefBean.getCacheList(contexte).
                            getTypePrixById(serviceData.getAccomodationIdTypePrix());
                    serviceData.setAccomodationLabelTypePrix((typePrix!=null)?typePrix.getLibelle():"Type Prix!!!");

                    typePrix = TypePrixRefBean.getCacheList(contexte).
                            getTypePrixById(serviceData.getBreakfastIdTypePrix());
                    serviceData.setBreakfastLabelTypePrix((typePrix!=null)?typePrix.getLibelle():"Type Prix!!!");

                    typePrix = TypePrixRefBean.getCacheList(contexte).
                            getTypePrixById(serviceData.getMealIdTypePrix());
                    serviceData.setMealLabelTypePrix((typePrix!=null)?typePrix.getLibelle():"Type Prix!!!");
                    childRateBean.addService(serviceData);
                }
                return childRateBean;
            } else {
                return (RateBean) SQLCallExecuter.getInstance().executeSelectSingleObjetProc(procName, procParams.get(0), "RateDAO", "getRateByKey", contexte.getContexteAppelDAO(), procReader);
            }
        } catch (SQLException e) {
			throw new RatesTechnicalException(e);
		} catch (IncoherenceException e) {
			throw new RatesTechnicalException(e);
        } catch (TechnicalException e) {
            throw new RatesTechnicalException(e);
        }
	}

	/**
	 * Méthode permettant d'insérer une ligne de tarif
	 * 
	 * @param contexte
	 * @param bean
	 * @return
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 * @throws DuplicateKeyException
	 */
	public void insertRate(RateBean bean, Contexte contexte) throws RatesTechnicalException, RatesUserException {
		try {
			String procName = getProcName(INSERT);
			List<SQLParamDescriptorSet> procParams = checkProcParameters(INSERT, bean);
			SQLCallExecuter.getInstance().executeListeUpdate(procName, procParams.toArray(new SQLParamDescriptorSet[] {}), "RateDAO", "insertRate", contexte.getContexteAppelDAO());
			Log.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "INSERT", bean.toString());
		} catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE)
				throw new RatesUserException(e, e.getMessage());
			if (e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE_2 || e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE)
				throw new RatesUserException(e, e.getMessage());
			throw new RatesTechnicalException(e);
		} catch (TechnicalException ex) {
			throw new RatesTechnicalException(ex);
		}
	}

	/**
	 * Méthode permettant de mettre à jour une ligne de tarif
	 * 
	 * @param contexte
	 * @param bean
	 * @return
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 * @throws DuplicateKeyException
	 * @throws ForeignKeyException
	 */
	public void updateRate(RateBean bean, Contexte contexte) throws RatesUserException, RatesTechnicalException {
		if (StringUtils.isNotBlank(bean.getKey()))
			deleteRate(bean, contexte);
		insertRate(bean, contexte);
	}

	/**
	 * Méthode permettant de supprimer une ligne de tarif
	 * 
	 * @param contexte
	 * @param bean
	 * @return
	 * @throws TechnicalException
	 * @throws IncoherenceException
	 * @throws ForeignKeyException
	 */
	public int deleteRate(RateBean bean, Contexte contexte) throws RatesUserException, RatesTechnicalException {
		try {
			String procName = getProcName(DELETE);
			List<SQLParamDescriptorSet> procParams = checkProcParameters(DELETE, bean);
			int res = SQLCallExecuter.getInstance().executeUpdate(procName, procParams.get(0), "RateDAO", "deleteRate", contexte.getContexteAppelDAO());
			Log.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "DELETE", bean.toString());
			Log.debug("RateDAO", "deleteRate", "Code retour = " + res);
			return res;
		} catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE_2 || e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE)
				throw new RatesUserException(e, e.getMessage(), null);
			throw new RatesTechnicalException(e);
		} catch (TechnicalException e) {
			throw new RatesTechnicalException(e);
		}
	}

	public void importGrilleTarif(GrilleBean oldGrilleData, PeriodeValiditeRefBean activePeriodeValidite, Contexte contexte) throws RatesTechnicalException, RatesUserException {
		int idPeriodeValidite = Integer.parseInt(activePeriodeValidite.getCode());
		int idOldPeriodeValidite = oldGrilleData.getIdPeriodeValidite();
		int idFamilleTarif = oldGrilleData.getIdFamilleTarif();
		String codeHotel = oldGrilleData.getCodeHotel();
		try {
			String procName = getProcName(IMPORT);
			SQLParamDescriptorSet procParams = new SQLParamDescriptorSet(new SQLParamDescriptor[] { new SQLParamDescriptor(activePeriodeValidite.getDateDebut(), false, Types.DATE),
					new SQLParamDescriptor(idOldPeriodeValidite, false, Types.INTEGER), new SQLParamDescriptor(idPeriodeValidite, false, Types.INTEGER),
					new SQLParamDescriptor(codeHotel, false, Types.VARCHAR), new SQLParamDescriptor(idFamilleTarif, false, Types.INTEGER),
					new SQLParamDescriptor(contexte.getCodeUtilisateur(), false, Types.VARCHAR) });
			SQLCallExecuter.getInstance().executeUpdate(procName, procParams, "RateDAO", "importGrilleTarif", contexte.getContexteAppelDAO());
			Log.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "IMPORT", "codeHotel=" + codeHotel + "; idPeriodeValidite=" + idPeriodeValidite + "; idFamilleTarif=" + idFamilleTarif);
		} catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE_2 || e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE)
				throw new RatesUserException(e, e.getMessage());
			else if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE)
				throw new RatesUserException(e, e.getMessage());
			throw new RatesTechnicalException(e);
		} catch (TechnicalException e) {
			throw new RatesTechnicalException(e);
		}
	}

    /**
     * @param existingRates
     * @param key
     * @return
     */
    private ChildRateBean getExistingChildRateBean(List<RateBean> existingRates, String key) {
        ChildRateBean result = null;
        for (Rate rate : existingRates) {
            result = (ChildRateBean) rate;
            if (result.getKey().equals(key))
                break;
            result = null;
        }
        return result;
    }
}