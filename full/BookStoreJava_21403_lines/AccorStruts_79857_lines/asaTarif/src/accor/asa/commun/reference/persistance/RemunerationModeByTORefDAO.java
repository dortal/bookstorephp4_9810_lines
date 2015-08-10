package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.ElementWithState;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.ParamRefBean;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.RemunerationModeByTOCachList;
import com.accor.asa.commun.reference.metier.TypeOffreRefBean;
import com.accor.asa.commun.reference.process.RefFacade;
import com.accor.commun.internationalisation.Translator;
import com.accor.commun.internationalisation.TranslatorFactory;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 7 août 2007
 * Time: 15:25:58
 */
public class RemunerationModeByTORefDAO extends RefDAO {

    private static final String ADMIN_SELECT_PROC_NAME  = "ref_selRemunerationModeByTO";
    private static final String INSERT_PROC_NAME        = "ref_addRemunerationModeByTO";
    private static final String DELETE_PROC_NAME        = "ref_delRemunerationModeByTO";

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

    /**
     * Retourne la liste des modes de remuneration
     * par type offre
     *
     * @param context
     * @param bean
     * @return
     * @throws com.accor.asa.commun.TechnicalException
     */
    public List<ParamRefBean> getRefList( Contexte contexte ) throws TechnicalException {
        return getAdminRefList( contexte, new RefBean() );
    }

    /**
     * Retourne la liste des modes de remuneration
     * par type offre
     *
     * @param context
     * @param bean
     * @return
     * @throws com.accor.asa.commun.TechnicalException
     */
	@SuppressWarnings("unchecked")
	public List<ParamRefBean> getAdminRefList( Contexte contexte, RefBean bean )
            throws TechnicalException {
        try {
            List<ElementWithState> listJointure = (List<ElementWithState>) SQLCallExecuter.getInstance()
            		.executeSelectProc( ADMIN_SELECT_PROC_NAME,
                    new SQLParamDescriptorSet(),
                    "RemunerationModeByTORefDAO", "getAdminRefList",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            ElementWithState obj = new ElementWithState();
                            obj.setCode(StringUtils.trimToEmpty(rs.getString("idRemunerationMode")));
                            obj.setLibelle(StringUtils.trimToEmpty(rs.getString("libRemunerationMode")));
                            obj.setCodeGroupe(StringUtils.trimToEmpty(rs.getString("idTypeOffre")));
                            obj.setState(rs.getBoolean("isDefault"));
                            return obj;
                        }
                    }
            );
            List<ParamRefBean> temp = new ArrayList<ParamRefBean>();
            List<TypeOffreRefBean> listTO = (List<TypeOffreRefBean>) PoolCommunFactory.getInstance()
            		.getRefFacade().getRefList( RefFacade.TYPE_OFFRE_REF_KEY, contexte );
            
            if( listTO!=null && listTO.size()>0 ) {
                Translator translator = TranslatorFactory.getTranslator("GB", true);
                LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), ADMIN_SELECT_PROC_NAME, "SELECT", "");
                ParamRefBean prb;
                for( TypeOffreRefBean tof : listTO ) {
                    prb = new ParamRefBean();
                    prb.setCode(tof.getId());
                    prb.setLibelle(tof.getLibelle());
                    prb.setCodeGroupe(tof.getIdGroupeOffre());
                    prb.setLibelleGroupe(translator.getLibelle(tof.getIdGroupeOffre()));
                    if( listJointure!=null && listJointure.size()>0 ) {
                        for( ElementWithState element : listJointure ) {
                            if (element.getCodeGroupe().equals(prb.getCode()))
                                prb.addParam(element);
                        }
                    }
                    temp.add(prb);
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
     * Update la liste des modes de remuneration
     * par type offre
     *
     * @param context
     * @param bean
     * @return
     * @throws TechnicalException
     */
    public int updateRef( Contexte contexte, RefBean bean )
            throws TechnicalException {
        ParamRefBean temp = (ParamRefBean) bean;
        try {
            // Delete
            SQLCallExecuter.getInstance().executeUpdate(
                    DELETE_PROC_NAME,
                    new SQLParamDescriptorSet(new SQLParamDescriptor(temp.getCode(), Types.VARCHAR)),
                    "RemunerationModeByTORefDAO",
                    "updateRef",
                    contexte.getContexteAppelDAO()
            );
            LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), DELETE_PROC_NAME, "DEL", temp.getCode());
            // Add
            ElementWithState el;
            if (temp.getParams().size() > 0) {
                for (int i = 0, size = temp.getParams().size(); i < size; i++) {
                    el = (ElementWithState) temp.getParams().get(i);
                    SQLCallExecuter.getInstance().executeUpdate(
                            INSERT_PROC_NAME,
                            new SQLParamDescriptorSet(
                                    new SQLParamDescriptor[] {
                                        new SQLParamDescriptor(temp.getCode(), Types.VARCHAR),
                                        new SQLParamDescriptor(new Integer(el.getCode()), Types.SMALLINT),
                                        new SQLParamDescriptor(Boolean.valueOf(el.isState()), Types.BOOLEAN)
                            }),
                            "RemunerationModeByTORefDAO",
                            "updateRef",
                            contexte.getContexteAppelDAO()
                    );
                    LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), INSERT_PROC_NAME, "ADD", temp.getCode()+","+el.getCode());
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    protected String getCacheClassName () {
        return RemunerationModeByTOCachList.class.getName();
    }

    protected CachableInterface getObjectInCache (String codeLangue) {
        return (CachableInterface) CacheManager.getInstance().getObjectInCache(RemunerationModeByTOCachList.class);
    }


	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new RemunerationModeByTOCachList((List<ParamRefBean>) data);
	}
    
    
}
