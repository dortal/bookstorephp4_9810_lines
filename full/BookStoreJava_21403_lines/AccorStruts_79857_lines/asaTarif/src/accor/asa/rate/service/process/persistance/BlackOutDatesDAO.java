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
import com.accor.asa.rate.model.BlackOutDatesBean;
import com.accor.asa.rate.model.Rate;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class BlackOutDatesDAO extends RateDAO {

    private static final String SELECT_PROC_NAME            = "tarif_selBOD";
    private static final String SELECT_BY_KEY_PROC_NAME     = "tarif_selBODById";
    private static final String INSERT_PROC_NAME            = "tarif_addBOD";
    private static final String DELETE_PROC_NAME            = "tarif_delBOD";

    private static BlackOutDatesDAO instance = new BlackOutDatesDAO();


    public static BlackOutDatesDAO getInstance() {
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
        BlackOutDatesBean temp = (BlackOutDatesBean) bean;
        List<SQLParamDescriptorSet> procParameters = new ArrayList<SQLParamDescriptorSet>();
        switch (type) {
            case SELECT:
                SQLParamDescriptor[] selectParams = {
                        new SQLParamDescriptor(temp.getIdGrille(), Types.NUMERIC)
                };
                procParameters.add(new SQLParamDescriptorSet(selectParams));
                break;
            case INSERT:
                SQLParamDescriptor[] insertParams = {
                        new SQLParamDescriptor(temp.getIdGrille(), Types.NUMERIC),
                        new SQLParamDescriptor(new AsaDate(temp.getDateDebut()), Types.DATE),
                        new SQLParamDescriptor(new AsaDate(temp.getDateFin()), Types.DATE)
                };
                procParameters.add(new SQLParamDescriptorSet(insertParams));
                break;
            case DELETE:
            case SELECT_BY_KEY:
                try {
                    String[] keyValues = StringUtils.split(temp.getKey(), "_");
                    SQLParamDescriptor[] deleteParams = {
                            new SQLParamDescriptor(keyValues[0], Types.NUMERIC),
                            new SQLParamDescriptor(new AsaDate(keyValues[1], AsaDate.ASA), Types.DATE)
                    };
                    procParameters.add(new SQLParamDescriptorSet(deleteParams));
                } catch(ParseException e) {
                    Log.critical("","BlackOutDatesDAO","getProcParameters",e.getMessage());
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
                            BlackOutDatesBean bean = new BlackOutDatesBean();
                            bean.setIdGrille(rs.getLong("idGrille"));
                            bean.setDateDebut(rs.getDate("dateDebut"));
                            bean.setDateFin(rs.getDate("dateFin"));
                            bean.setKey(bean.generateBeanKey());
                            return bean;
                    }
                };
            default:
                return null;
        }
    }
}
