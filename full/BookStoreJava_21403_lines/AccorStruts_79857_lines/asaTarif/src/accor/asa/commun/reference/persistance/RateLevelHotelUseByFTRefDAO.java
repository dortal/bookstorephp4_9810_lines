package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.ElementWithState;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.ParamRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 30 juil. 2007
 * Time: 10:43:33
 */
public class RateLevelHotelUseByFTRefDAO extends RefDAO {

    private static final String ADMIN_SELECT_PROC_NAME  = "ref_selRLHotelUseByFT";
    private static final String INSERT_PROC_NAME        = "ref_addRLHotelUseByFT";
    private static final String DELETE_PROC_NAME        = "ref_delRLHotelUseByFT";

    protected String getProcName(int type) {
        switch (type) {
            case SELECT :
                return null;
            case ADMIN_SELECT :
                return null;
            case INSERT :
                return null;
            case UPDATE :
                return null;
            case DELETE :
                return null;
            default :
                return null;
        }
    }

    protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
        switch (type) {
            case SELECT :
                return null;
            case ADMIN_SELECT :
                return null;
            case INSERT :
                return null;
            case UPDATE :
                return null;
            case DELETE :
                return null;
            default :
                return null;
        }
    }

    protected SQLResultSetReader getProcReader(int type) {
        switch (type) {
            case SELECT :
                return null;
            case ADMIN_SELECT :
                return null;
            case INSERT :
                return null;
            case UPDATE :
                return null;
            case DELETE :
                return null;
            default :
                return null;
        }
    }

    protected String getCacheClassName() {
        return null;
    }

    protected CachableInterface getObjectInCache(String codeLangue) {
        return null;
    }
       
    

    @Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return null;
	}

	/**
     * Retourne la liste des rates levels hotel use
     * par famille de tarifs
     *
     * @param context
     * @param bean
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<ParamRefBean> getAdminRefList( Contexte contexte, RefBean bean )
            throws TechnicalException {
        try {
            List<ParamRefBean> temp = new ArrayList<ParamRefBean>();
            List<FamilleTarifRefBean> lft = FamilleTarifRefBean.getCacheList(contexte).getElements();
            if( lft != null && lft.size() > 0 ) {
                ParamRefBean rlRefBean;
                List<ElementWithState> listRlHuByFT;
                for( FamilleTarifRefBean ft : lft ) {
                    rlRefBean = new ParamRefBean();
                    rlRefBean.setCode(ft.getCode());
                    rlRefBean.setLibelle(ft.getLibelle());
                    rlRefBean.setCodeGroupe(String.valueOf(ft.getIdGroupeTarif()));
                    rlRefBean.setActif(true);
                    listRlHuByFT = (List<ElementWithState>) SQLCallExecuter.getInstance()
                    		.executeSelectProc( ADMIN_SELECT_PROC_NAME,
                            new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                                    new SQLParamDescriptor(new Integer(ft.getCode()), false, Types.INTEGER),
                                    new SQLParamDescriptor("GB", false, Types.CHAR)
                            }),
                            "RateLevelHotelUseByFTRefDAO", "getAdminRefList",
                            contexte.getContexteAppelDAO(),
                            new SQLResultSetReader() {
                                public Object instanciateFromLine(ResultSet rs) throws SQLException {
                                    ElementWithState obj = new ElementWithState();
                                    obj.setCode(StringUtils.trimToEmpty(rs.getString("codeTarif")));
                                    obj.setLibelle(StringUtils.trimToEmpty(rs.getString("nomTarif")));
                                    obj.setState(rs.getBoolean("selected"));
                                    return obj;
                                }
                            }
                    );
                    rlRefBean.setParams(listRlHuByFT);
                    temp.add(rlRefBean);
                    LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(), ADMIN_SELECT_PROC_NAME, 
                    		"SELECT",""+ft.getCode() );
                }
            }
            return temp;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        } catch (IncoherenceException e) {
            throw new TechnicalException(e);
        }
    }

    /**
     * Update la liste des rates levels hotel use
     * par famille de tarifs
     *
     * @param context
     * @param bean
     * @return
     * @throws TechnicalException
     */
    public int updateRef( Contexte contexte, RefBean bean ) throws TechnicalException {
        ParamRefBean temp = (ParamRefBean) bean;
        try {
            // Delete
            SQLCallExecuter.getInstance().executeUpdate(
                    DELETE_PROC_NAME,
                    new SQLParamDescriptorSet(new SQLParamDescriptor(Integer.valueOf(temp.getCode()), Types.INTEGER)),
                    "RateLevelHotelUseByFTRefDAO",
                    "updateRef",
                    contexte.getContexteAppelDAO()
            );
            LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(), DELETE_PROC_NAME, "DEL", temp.getCode() );
            // Add
            ElementWithState rl;
            if (temp.getParams().size() > 0) {
                for (int i = 0, size = temp.getParams().size(); i < size; i++) {
                    rl = temp.getParams().get(i);
                    SQLCallExecuter.getInstance().executeUpdate(
                            INSERT_PROC_NAME,
                            new SQLParamDescriptorSet(
                                    new SQLParamDescriptor[] {
                                        new SQLParamDescriptor(Integer.valueOf(temp.getCode()), Types.INTEGER),
                                        new SQLParamDescriptor(rl.getCode(), Types.VARCHAR)
                            }),
                            "RateLevelHotelUseByFTRefDAO",
                            "updateRef",
                            contexte.getContexteAppelDAO()
                    );
                    LogCommun.traceFonctionnelle( contexte.getCodeUtilisateur(), INSERT_PROC_NAME, "ADD", temp.getCode()+","+rl.getCode() );
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

}
