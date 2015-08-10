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
import com.accor.asa.rate.model.ChildRateBean;
import com.accor.asa.rate.model.ChildRateRowBean;
import com.accor.asa.rate.model.ChildRateServiceData;
import com.accor.asa.rate.model.Rate;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class ChildRateDAO extends RateDAO {
	private static final String SELECT_PROC_NAME            = "tarif_selChild";
    private static final String SELECT_BY_KEY_PROC_NAME     = "tarif_selChildById";
    private static final String INSERT_PROC_NAME            = "tarif_addChild";
	private static final String DELETE_PROC_NAME            = "tarif_delChild";

	private static ChildRateDAO instance = new ChildRateDAO();

	public static ChildRateDAO getInstance() {
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

		List<SQLParamDescriptorSet> procParameters = new ArrayList<SQLParamDescriptorSet>();
		ChildRateBean temp = (ChildRateBean) bean;
		switch (type) {
		case SELECT:
			SQLParamDescriptor[] selectParams = { new SQLParamDescriptor(temp.getIdGrille(), Types.NUMERIC) };
			procParameters.add(new SQLParamDescriptorSet(selectParams));
			break;
		case INSERT:
			String[] codesProduit = temp.getCodesProduit();
			int agesLength = temp.getServices().size();
            for (String aCodesProduit : codesProduit) {
                for (int j = 0; j < agesLength; j++) {
                    ChildRateServiceData service = temp.getService(j);
                    SQLParamDescriptor[] insertParams = {
                            new SQLParamDescriptor(temp.getIdGrille(), Types.NUMERIC),
                            new SQLParamDescriptor(temp.getCodeRateLevel(), Types.VARCHAR),
                            new SQLParamDescriptor(aCodesProduit, Types.VARCHAR),
                            new SQLParamDescriptor(new AsaDate(temp.getDateDebut()), Types.DATE),
                            new SQLParamDescriptor(new AsaDate(temp.getDateFin()), Types.DATE),
                            new SQLParamDescriptor(service.getMinAge(), Types.INTEGER),
                            new SQLParamDescriptor(service.getMaxAge(), Types.INTEGER),
                            new SQLParamDescriptor(temp.getCodeDevise(), Types.VARCHAR),
                            new SQLParamDescriptor(temp.getMaxChild(), Types.INTEGER),
                            new SQLParamDescriptor(temp.getMaxAdult(), Types.INTEGER),
                            new SQLParamDescriptor(temp.isChambreSepare() ? "1" : "0", Types.VARCHAR),
                            new SQLParamDescriptor(service.getAccomodationIdTypePrix(), Types.INTEGER),
                            new SQLParamDescriptor(service.getAccomodationMontant(), Types.NUMERIC),
                            new SQLParamDescriptor(service.getBreakfastIdTypePrix(), Types.INTEGER),
                            new SQLParamDescriptor(service.getBreakfastMontant(), Types.NUMERIC),
                            new SQLParamDescriptor(service.getMealIdTypePrix(), Types.INTEGER),
                            new SQLParamDescriptor(service.getMealMontant(), Types.NUMERIC)
                    };
                    procParameters.add(new SQLParamDescriptorSet(insertParams));
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
                        new SQLParamDescriptor(new AsaDate(keyValues[3], AsaDate.ASA), Types.DATE),
						new SQLParamDescriptor(keyValues[4].equals("true") ? "1" : "0", Types.VARCHAR) };
				procParameters.add(new SQLParamDescriptorSet(deleteParams));
			} catch (ParseException e) {
				Log.critical("", "BusinessRateDAO", "getProcParameters", e.getMessage());
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
					ChildRateRowBean bean = new ChildRateRowBean();
                    bean.setIdGrille(rs.getLong("idGrille"));
					bean.setCodeRateLevel(rs.getString("codeRateLevel"));
					bean.setCodeProduit(rs.getString("codeProduit"));
                    bean.setDateDebut(rs.getDate("dateDebut"));
                    bean.setDateFin(rs.getDate("dateFin"));
                    bean.setMinAge(rs.getInt("minAge"));
                    bean.setMaxAge(rs.getInt("maxAge"));
                    bean.setCodeDevise(rs.getString("codeDevise"));
                    bean.setMaxChild(rs.getInt("maxChild"));
                    bean.setMaxAdult(rs.getInt("maxAdult"));
					bean.setChambreSepare(rs.getBoolean("isChambreSepare"));
                    bean.setAccomodationIdTypePrix(rs.getInt("accomodationIdTypePrix"));
                    bean.setAccomodationMontant(rs.getDouble("accomodationMontant"));
                    bean.setBreakfastIdTypePrix(rs.getInt("breakfastIdTypePrix"));
                    bean.setBreakfastMontant(rs.getDouble("breakfastMontant"));
                    bean.setMealIdTypePrix(rs.getInt("mealIdTypePrix"));
                    bean.setMealMontant(rs.getDouble("mealMontant"));
					bean.setKey(bean.generateBeanKey());
					return bean;
				}
			};
		default:
			return null;
		}
	}

	
}
