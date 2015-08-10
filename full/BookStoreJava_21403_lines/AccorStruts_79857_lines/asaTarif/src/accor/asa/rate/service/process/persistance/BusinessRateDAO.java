package com.accor.asa.rate.service.process.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.BusinessRateBean;
import com.accor.asa.rate.model.Rate;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class BusinessRateDAO extends RateDAO {

    private static final String SELECT_PROC_NAME            = "tarif_selBusiness";
    private static final String SELECT_BY_KEY_PROC_NAME     = "tarif_selBusinessById";
    private static final String INSERT_PROC_NAME            = "tarif_addBusiness";
    private static final String DELETE_PROC_NAME            = "tarif_delBusiness";
    private static final String IMPORT_PROC_NAME            = "tarif_importBusGrilles";

    private static BusinessRateDAO instance = new BusinessRateDAO();
    
    
    public static BusinessRateDAO getInstance() {
		return instance;
	}

	/**
     * Retourne le nom de la proc
     *
     * @param type
     * @return
     */
    protected String getProcName(int type) {
        switch (type) {
            case SELECT:
                return SELECT_PROC_NAME;
            case SELECT_BY_KEY:
                return SELECT_BY_KEY_PROC_NAME;
            case INSERT:
                return INSERT_PROC_NAME;
            case DELETE:
                return DELETE_PROC_NAME;
            case IMPORT:
                return IMPORT_PROC_NAME;
            default:
                return null;
        }
    }

    /**
     * Retourne les paramètres par proc
     *
     * @param type
     * @param bean
     * @param codeLangue
     * @return
     */
    protected List<SQLParamDescriptorSet> getProcParameters(int type, Rate bean) {
        BusinessRateBean temp = (BusinessRateBean) bean;
        List<SQLParamDescriptorSet> procParameters = new ArrayList<SQLParamDescriptorSet>();
        switch (type) {
            case SELECT:
                SQLParamDescriptor[] selectParams = {
                        new SQLParamDescriptor(temp.getIdGrille(), Types.NUMERIC)
                };
                procParameters.add(new SQLParamDescriptorSet(selectParams));
                break;
            case INSERT:
                for (String chambre:temp.getCodesProduit()) {
                	for(int i=1;i<3;i++)
                	{
	                    if(temp.getPriceForPax(i)!=null) {
	                        SQLParamDescriptor[] insertParams = {
	                                new SQLParamDescriptor(temp.getIdGrille(), Types.NUMERIC),
	                                new SQLParamDescriptor(temp.getCodeRateLevel(), Types.VARCHAR),
	                                new SQLParamDescriptor(chambre, Types.VARCHAR),
	                                new SQLParamDescriptor(temp.getCodeMealPlan(), Types.VARCHAR),
	                                new SQLParamDescriptor(temp.getCodePeriode(), Types.VARCHAR),
	                                new SQLParamDescriptor(temp.getIdDivSemaine(), Types.INTEGER),
	                                new SQLParamDescriptor(new AsaDate(temp.getDateDebut()), Types.DATE),
	                                new SQLParamDescriptor(new AsaDate(temp.getDateFin()), Types.DATE),
	                                new SQLParamDescriptor(temp.getPriceForPax(i), Types.DOUBLE),
	                                new SQLParamDescriptor(i, Types.INTEGER),
	                                new SQLParamDescriptor(temp.getIdDureeSejour(), Types.INTEGER),
	                                new SQLParamDescriptor(temp.getLibelleSalon(), Types.VARCHAR),
	                                new SQLParamDescriptor(temp.getCodePetitDej(), Types.VARCHAR),
	                                new SQLParamDescriptor(temp.getPrixPdj(), Types.DOUBLE),
	                                new SQLParamDescriptor(temp.getCodeDevise(), Types.VARCHAR),
	                                new SQLParamDescriptor(temp.getValueCommission(), Types.DOUBLE),
	                                new SQLParamDescriptor(temp.getUniteCommission(), Types.VARCHAR),
	                                new SQLParamDescriptor(temp.getLunWe()!=null?temp.getLunWe():Boolean.FALSE, Types.BOOLEAN),
	                                new SQLParamDescriptor(temp.getMarWe()!=null?temp.getMarWe():Boolean.FALSE, Types.BOOLEAN),
	                                new SQLParamDescriptor(temp.getMerWe()!=null?temp.getMerWe():Boolean.FALSE, Types.BOOLEAN),
	                                new SQLParamDescriptor(temp.getJeuWe()!=null?temp.getJeuWe():Boolean.FALSE, Types.BOOLEAN),
	                                new SQLParamDescriptor(temp.getVenWe()!=null?temp.getVenWe():Boolean.FALSE, Types.BOOLEAN),
	                                new SQLParamDescriptor(temp.getSamWe()!=null?temp.getSamWe():Boolean.FALSE, Types.BOOLEAN),
	                                new SQLParamDescriptor(temp.getDimWe()!=null?temp.getDimWe():Boolean.FALSE, Types.BOOLEAN),
	                                new SQLParamDescriptor(temp.getOpenNewContrat()!=null?temp.getOpenNewContrat():Boolean.FALSE, Types.BOOLEAN),
	                                new SQLParamDescriptor(temp.getBlackOutDates()!=null?temp.getBlackOutDates():Boolean.FALSE, Types.BOOLEAN),
	                                new SQLParamDescriptor(temp.getNbreNuitsMin(), Types.INTEGER),
	                                new SQLParamDescriptor(temp.getNbreNuitsMax(), Types.INTEGER)
	                        };
	                        procParameters.add(new SQLParamDescriptorSet(insertParams));
	                    }
                	}   
                }
                break;
            case DELETE:
            case SELECT_BY_KEY:
                try {
                    String[] keyValues = StringUtils.split(temp.getKey(), "_");
                    SQLParamDescriptor[] deleteParams = {
                            new SQLParamDescriptor(keyValues[0], Types.NUMERIC),
                            new SQLParamDescriptor(keyValues[1], Types.VARCHAR),
                            new SQLParamDescriptor(keyValues[2], Types.VARCHAR),
                            new SQLParamDescriptor(keyValues[3], Types.VARCHAR),
                            new SQLParamDescriptor(keyValues[4], Types.VARCHAR),
                            new SQLParamDescriptor(new Integer(keyValues[5]), Types.INTEGER),
                            new SQLParamDescriptor(new AsaDate(keyValues[6], AsaDate.ASA), Types.DATE)
                    };
                    procParameters.add(new SQLParamDescriptorSet(deleteParams));
                } catch(ParseException e) {
                    Log.critical("","BusinessRateDAO","getProcParameters",e.getMessage());
                }
                break;
        }
        return procParameters;
    }
    /**
     * Retourne le reader
     *
     * @param type
     * @return
     */
    protected SQLResultSetReader getProcReader(int type) {
        switch (type) {
            case SELECT:
            case SELECT_BY_KEY:
                return new SQLResultSetReader() {
                    public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
                            BusinessRateBean bean = new BusinessRateBean();
                            bean.setIdGrille(rs.getLong("idGrille"));
                            bean.setCodeRateLevel(rs.getString("codeRateLevel"));
                            bean.setCodeProduit(rs.getString("codeProduit"));
                            bean.setCodeMealPlan(rs.getString("codeMealPlan"));
                            bean.setCodePeriode(rs.getString("codePeriode"));
                            bean.setIdDivSemaine((Integer)rs.getObject("idDivSemaine"));
                            bean.setDateDebut(rs.getDate("dateDebut"));
                            bean.setDateFin(rs.getDate("dateFin"));
                            bean.setPriceForPax(1,rs.getDouble("prix1Pax"));
                            bean.setPriceForPax(2,rs.getDouble("prix2Pax"));
                            bean.setIdDureeSejour((Integer)rs.getObject("idDureeSejour"));
                            bean.setLibelleSalon(rs.getString("libelleSalon"));
                            bean.setCodePetitDej(rs.getString("codePetitDej"));
                            bean.setPrixPdj(rs.getDouble("prixPdj"));
                            bean.setCodeDevise(rs.getString("codeDevise"));
                            bean.setValueCommission(rs.getDouble("valueCommission"));
                            bean.setUniteCommission(rs.getString("uniteCommission"));
                            bean.setLunWe(rs.getBoolean("isLunWe"));
                            bean.setMarWe(rs.getBoolean("isMarWe"));
                            bean.setMerWe(rs.getBoolean("isMerWe"));
                            bean.setJeuWe(rs.getBoolean("isJeuWe"));
                            bean.setVenWe(rs.getBoolean("isVenWe"));
                            bean.setSamWe(rs.getBoolean("isSamWe"));
                            bean.setDimWe(rs.getBoolean("isDimWe"));
                            bean.setOpenNewContrat(rs.getBoolean("isOpenNewContrat"));
                            bean.setBlackOutDates(rs.getBoolean("isBlackOutDates"));
                            bean.setNbreNuitsMin(rs.getInt("nbreNuitsMin"));
                            bean.setNbreNuitsMax(rs.getInt("nbreNuitsMax"));
                            bean.setKey(bean.generateBeanKey());
                            return bean;                        
                    }
                };
            default:
                return null;
        }
    }
}
