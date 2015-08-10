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
import com.accor.asa.rate.model.Rate;
import com.accor.asa.rate.model.SpecialRateBean;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class SpecialRateDAO extends RateDAO{
    private static final String SELECT_PROC_NAME            = "tarif_selOffreSpeciale";
    private static final String SELECT_BY_KEY_PROC_NAME     = "tarif_selOffreSpecialeById";
    private static final String INSERT_PROC_NAME            = "tarif_addOffreSpeciale";
	private static final String DELETE_PROC_NAME            = "tarif_delOffreSpeciale";

     private static SpecialRateDAO instance = new SpecialRateDAO();
     
     
        public static SpecialRateDAO getInstance() {
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
	        SpecialRateBean temp = (SpecialRateBean) bean;
	        List<SQLParamDescriptorSet> procParameters = new ArrayList<SQLParamDescriptorSet>();
	        switch (type) {
	            case SELECT:
	                SQLParamDescriptor[] selectParams = {
	                        new SQLParamDescriptor(temp.getIdGrille(), Types.NUMERIC)
	                };
	                procParameters.add(new SQLParamDescriptorSet(selectParams));
	                break;
	            case INSERT:
	            	String txt = temp.getCommentaire();
	            	String txt1=txt;
	            	String txt2=null;
	            	if(txt != null && txt.length()>255) {
	            		txt1=txt.substring(0,255);
	            		txt2=txt.substring(255);
	            	}
                    if(temp.isTousMarches()) {
                        temp.setCodePays1(null);
                        temp.setCodePays2(null);
                        temp.setCodePays3(null);
                        temp.setCodePays4(null);
                        temp.setCodePays5(null);
                        temp.setCodeContinent1(null);
                        temp.setCodeContinent2(null);
                        temp.setCodeContinent3(null);
                    }
                    if(temp.getPrix()==null || temp.getPrix()==0) {
                        temp.setIdUnitePrix(null);
                        temp.setCodeDevise(null);
                    }
                    SQLParamDescriptor[] insertParams = {
                            new SQLParamDescriptor(temp.getIdGrille(), Types.NUMERIC),
                            new SQLParamDescriptor(temp.getCodeOffreSpeciale(), Types.SMALLINT),
                            new SQLParamDescriptor(new AsaDate(temp.getDateDebut()), Types.DATE),
                            new SQLParamDescriptor(new AsaDate(temp.getDateFin()), Types.DATE),
                            new SQLParamDescriptor(temp.isObligatoire(), Types.BIT),
                            new SQLParamDescriptor(temp.getPrix(), Types.DOUBLE),
                            new SQLParamDescriptor(temp.getIdUnitePrix(), Types.INTEGER),
                            new SQLParamDescriptor(temp.getIdTypePrix(), Types.INTEGER),
                            new SQLParamDescriptor(temp.getCodeDevise(),Types.VARCHAR),
                            new SQLParamDescriptor(temp.isTousMarches(), Types.BIT),
                            new SQLParamDescriptor(temp.getCodePays1(),Types.VARCHAR),
                            new SQLParamDescriptor(temp.getCodePays2(),Types.VARCHAR),
                            new SQLParamDescriptor(temp.getCodePays3(),Types.VARCHAR),
                            new SQLParamDescriptor(temp.getCodePays4(),Types.VARCHAR),
                            new SQLParamDescriptor(temp.getCodePays5(),Types.VARCHAR),
                            new SQLParamDescriptor(temp.getCodeContinent1(),Types.VARCHAR),
                            new SQLParamDescriptor(temp.getCodeContinent2(),Types.VARCHAR),
                            new SQLParamDescriptor(temp.getCodeContinent3(),Types.VARCHAR),
                            new SQLParamDescriptor(txt1,Types.VARCHAR),
                            new SQLParamDescriptor(txt2,Types.VARCHAR),
                    };
                    procParameters.add(new SQLParamDescriptorSet(insertParams));
                    break;
	            case DELETE:
                case SELECT_BY_KEY:
                    try {
	                    String[] keyValues = StringUtils.split(temp.getKey(), "_");
	                    SQLParamDescriptor[] deleteParams = {
	                            new SQLParamDescriptor(keyValues[0], Types.NUMERIC),
	                            new SQLParamDescriptor(keyValues[1], Types.INTEGER),
	                            new SQLParamDescriptor(new AsaDate(keyValues[2], AsaDate.ASA), Types.DATE)
	                    };
	                    procParameters.add(new SQLParamDescriptorSet(deleteParams));
	                } catch(ParseException e) {
	                    Log.critical("","SpecialRateDAO","getProcParameters",e.getMessage());
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
	                            SpecialRateBean bean = new SpecialRateBean();
	                            bean.setIdGrille(rs.getLong("idGrille"));
	                            bean.setCodeOffreSpeciale(rs.getInt("codeOffreSpeciale"));
	                            bean.setDateDebut(rs.getDate("dateDebut"));
	                            bean.setDateFin(rs.getDate("dateFin"));
	                            bean.setObligatoire(rs.getBoolean("isObligatoire"));
	                            bean.setPrix(rs.getDouble("prix"));
	                            bean.setIdUnitePrix((Integer)rs.getObject("idUnitePrix"));
	                            bean.setIdTypePrix((Integer)rs.getObject("idTypePrix"));
	                            bean.setCodeDevise(rs.getString("codeDevise"));
	                            bean.setTousMarches(rs.getBoolean("isTousMarches"));
	                            bean.setCodePays1(rs.getString("codePays1"));
	                            bean.setCodePays2(rs.getString("codePays2"));
	                            bean.setCodePays3(rs.getString("codePays3"));
	                            bean.setCodePays4(rs.getString("codePays4"));
	                            bean.setCodePays5(rs.getString("codePays5"));
	                            bean.setCodeContinent1(rs.getString("codeContinent1"));
	                            bean.setCodeContinent2(rs.getString("codeContinent2"));
	                            bean.setCodeContinent3(rs.getString("codeContinent3"));
	                            bean.setCommentaire(rs.getString("commentaire1"));
	                            String txt2=rs.getString("commentaire2");
	                            if(txt2 != null)
	                            	bean.setCommentaire(bean.getCommentaire()+txt2);
	                            bean.setKey(bean.generateBeanKey());
	                            return bean;                        
	                    }
	                };
	            default:
	                return null;
	        }
	    }
}
