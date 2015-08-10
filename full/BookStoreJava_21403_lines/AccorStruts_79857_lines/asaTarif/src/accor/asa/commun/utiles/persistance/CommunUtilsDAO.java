package com.accor.asa.commun.utiles.persistance;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.hotel.metier.Room;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.metier.categorie.ListCategories;
import com.accor.asa.commun.metier.chaine.ListChaines;
import com.accor.asa.commun.metier.devise.Devise;
import com.accor.asa.commun.metier.devise.ListDevisesCache;
import com.accor.asa.commun.metier.donneesdereference.*;
import com.accor.asa.commun.metier.marque.ListMarques;
import com.accor.asa.commun.metier.periodedevalidite.ListPeriodeValidite;
import com.accor.asa.commun.metier.periodedevalidite.PeriodeDeValidite;
import com.accor.asa.commun.metier.ratelevel.ListRateLevels;
import com.accor.asa.commun.metier.ratelevel.RateLevel;
import com.accor.asa.commun.metier.segment.ListSegments;
import com.accor.asa.commun.metier.segment.Segment;
import com.accor.asa.commun.metier.state.ListStates;
import com.accor.asa.commun.metier.taxe.TaxeTarsBean;
import com.accor.asa.commun.persistance.*;
import com.accor.asa.rate.RatesTechnicalException;
import com.accor.services.framework.enterprise.EnterpriseException;
import com.accor.services.framework.enterprise.EnterpriseFactory;
import com.accor.services.framework.enterprise.UserContext;
import com.accor.services.framework.enterprise.common.country.Country;
import com.accor.services.framework.enterprise.common.country.CountryService;
import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommunUtilsDAO extends DAO {

    private static CommunUtilsDAO _instance = new CommunUtilsDAO();

    public static CommunUtilsDAO getInstance() {
        return _instance;
    }

    /**
     * Retourne la liste des pays en provenance du cache ou de la BD s il n est pas instancie
     * @param contexte
     * @return List<Pays>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Pays> getPays( final Contexte contexte ) throws TechnicalException {

        ListPays pays = (ListPays) CacheManager.getInstance().getObjectInCache(ListPays.class);

        if (pays == null) {
            pays = loadListPays(contexte);
        }
        return pays == null ? null : pays.getElements();
    }

    // ANO 3971
    /***
     * Methode de recuperation des pays GEO depuis le service ServiceLayer
     * @param contexte
     * @return List<Pays>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Pays> getPaysGEO( final Contexte contexte ) throws TechnicalException {

    	String codeLangue = contexte.getCodeLangue();
        
        ListPaysGEO listePays = (ListPaysGEO) CacheManager.getInstance()
        			.getObjectInCache( ListPaysGEO.class, codeLangue );
        
        if( listePays == null ) {
        	List<Pays> resultat = new ArrayList<Pays>();
            try {
                UserContext ctx = new UserContext();
                ctx.setLocale( getLocaleFromCodeLangue( codeLangue ) );
                CountryService cs = EnterpriseFactory.connect(ctx).getCountryService();

                List<Country> l = cs.getCountries();
                for (Country aL : l) {
                    Pays pays = new Pays();
                    pays.setCode(aL.getCode());
                    pays.setLibelle(aL.getName());
                    resultat.add(pays);
                }


            } catch (EnterpriseException ex) {
                LogCommun.major( contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getPaysGEO",
                        "Erreur lors de la recuperation de la liste des paysGEO", ex );
                throw new TechnicalException(ex);
            }
            listePays = new ListPaysGEO( resultat, codeLangue );
            CacheManager.getInstance().setObjectInCache( listePays );
        }
        return listePays.getElements();
    }

    /**
     * Retourne la liste des etats d'un pays
     * @param codePays
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Element> getStates( final String codePays, final Contexte contexte ) 
    		throws TechnicalException {

    	ListStates states = (ListStates) CacheManager.getInstance()
    			.getObjectInCache( ListStates.class , codePays );
    	
    	if( states == null ) {
    		
    		try {
    			SQLParamDescriptor param = new SQLParamDescriptor(codePays, false, Types.CHAR);
    			
    			List<Element> res = (List<Element>) SQLCallExecuter.getInstance().executeSelectProc(
    					"vente_select_etat", new SQLParamDescriptorSet( param ), 
    					"CommunUtilsDAO", "getStates", contexte.getContexteAppelDAO(), 
    					new SQLResultSetReader() {
    	                      public Object instanciateFromLine(ResultSet rs) throws SQLException {
    	                          Element e = new Element();
    	                          e.setCode( StringUtils.trimToEmpty( rs.getString( "codeetat" ) ) );
    	                          e.setLibelle( StringUtils.trimToEmpty( rs.getString( "nometat" ) ) );
    	                          return e;
    	                      } 
    					} 
    			);
    			if( res != null ) {
    				states = new ListStates( res, codePays );
    				CacheManager.getInstance().setObjectInCache( states );
    			}

    		} catch (SQLException e) {
                LogCommun.major( contexte.getCodeUtilisateur(), "UtilesDAO", "getStates", codePays, e);
                throw new TechnicalException(e);
            }
    	}
        return (states!=null)?states.getElements():null;
    }

    /**
     * Renvoie tous les langues gérées par ASA
     * @param  contexte
     * @return List<Element>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Element> getLanguages( final Contexte contexte ) throws TechnicalException {

        ListLangues langues = (ListLangues)
                CacheManager.getInstance().getObjectInCache(ListLangues.class);

        if (LogCommun.isTechniqueDebug()) {
            LogCommun.debug(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getLanguages::Debut");
        }

        if (langues == null) {

            if (LogCommun.isTechniqueDebug()) {
                LogCommun.debug(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getLanguages::Charge");
            }

            try {
                List<Element> res = (List<Element>) SQLCallExecuter.getInstance()
                		.executeSelectProc("proc_Sellangues",
                        new SQLParamDescriptorSet(), "CommunUtilsDAO", "getLanguages", 
                        contexte.getContexteAppelDAO(), new SQLResultSetReader() {
                            public Object instanciateFromLine(ResultSet rs)
                                    throws TechnicalException, SQLException {
                                Element langue = new Element();
                                langue.setCode(rs.getString("codelangue"));
                                langue.setLibelle(rs.getString("nomlangue"));
                                return langue;
                            }
                        });

                langues = new ListLangues(res);
                CacheManager.getInstance().setObjectInCache(langues);

            } catch (TechnicalException ex) {
                LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getLanguages",
                        "Erreur lors de la recuperation de la liste des langues", ex);
                throw new TechnicalException(ex);
            } catch (SQLException ex) {
                LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getLanguages",
                        "Erreur lors de la recuperation de la liste des langues", ex);
                throw new TechnicalException(ex);
            }
        }
        return langues.getElements();
    }

    /**
     * Renvoie tous les civilités du system
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
	@SuppressWarnings("unchecked")
	public List<Element> getCivilites( final Contexte contexte ) throws TechnicalException {
        
		String codeLangue = contexte.getCodeLangue();
		
		ListCivilites civs = (ListCivilites)
                CacheManager.getInstance().getObjectInCache(ListCivilites.class, codeLangue);
        
		if (civs == null) {
            if (LogCommun.isTechniqueDebug())
                LogCommun.debug(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getCivilites::Charge");
            
            try {
                SQLParamDescriptor[] params = new SQLParamDescriptor[] {
                        new SQLParamDescriptor( codeLangue, false, Types.CHAR ),
                        new SQLParamDescriptor( "gb", false, Types.CHAR )
                };
            	
            	List<Element> res = (List<Element>) SQLCallExecuter.getInstance().executeSelectProc(
            			"proc_Sel_Listecivil", new SQLParamDescriptorSet(params),
                        "CommunUtilsDAO", "getCivilites", contexte.getContexteAppelDAO(),
                        new SQLResultSetReader() {
                            public Object instanciateFromLine(ResultSet rs) throws SQLException {
                                Element civ = new Element();
                                civ.setCode(rs.getString("code_civilite"));
                                civ.setLibelle(rs.getString("nom_civilite"));
                                return civ;
                            }
                        }
            	);
                
            	civs = new ListCivilites( res, codeLangue );
                CacheManager.getInstance().setObjectInCache(civs);

            } catch (TechnicalException ex) {
                LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getCivilites",
                        "Erreur lors de la recuperation de la liste des civilites", ex);
                throw new TechnicalException(ex);
            } catch (SQLException ex) {
                LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getCivilites",
                        "Erreur lors de la recuperation de la liste des civilites", ex);
                throw new TechnicalException(ex);
            }
        }
        return civs.getElements();
    }

    /**
     * Renvoie tous les cibles commerciales du system
     * @param  contexte
     * @return List<CibleCommerciale>
     * @throws TechnicalException
     */
	@SuppressWarnings("unchecked")
	public List<CibleCommerciale> getCiblesCommerciales( final Contexte contexte )
            throws TechnicalException {

        String codeLangue = contexte.getCodeLangue();
		
		ListCibleCommerciales cibles = (ListCibleCommerciales)
                CacheManager.getInstance().getObjectInCache(ListCibleCommerciales.class, codeLangue);

        if (cibles == null) {
            SQLParamDescriptor param = new SQLParamDescriptor(codeLangue, false, Types.CHAR);

            try {
                List<CibleCommerciale> res = (List<CibleCommerciale>) SQLCallExecuter.getInstance()
                		.executeSelectProc( "vente_Select_Ciblecommerciales", 
                		new SQLParamDescriptorSet(param),
                        "CommunUtilsDAO", "getCiblesCommerciales", contexte.getContexteAppelDAO(),
                        new SQLResultSetReader() {
                            public Object instanciateFromLine(ResultSet rs)
                                    throws TechnicalException, SQLException {
                                CibleCommerciale cible = new CibleCommerciale();
                                cible.setCode(rs.getString("ID_CIBLE"));
                                cible.setLibelle(rs.getString("LIBELLE_CIBLE"));
                                cible.setTypeCompte(rs.getString("TYPE_COMPTE"));
                                cible.setIdMarche(String.valueOf(rs.getInt("ID_MARCHE")));
                                return cible;
                            }
                        }
                );
                cibles = new ListCibleCommerciales(res, codeLangue);
                CacheManager.getInstance().setObjectInCache(cibles);

            } catch (SQLException e) {
                LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getCiblesCommerciales",
                        "Erreur lors de la recuperation de la liste des cibles commerciales", e);
                throw new TechnicalException(e);
            }
        }
        return cibles.getElements();
    }

    /**
     * Recupérer la liste des devises
     * @param isLoadFromBase
     * @param contexte
     * @return List<Devise>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Devise> getDevises( final boolean isLoadFromBase, final Contexte contexte )
            throws TechnicalException {

    	ListDevisesCache devises = (ListDevisesCache)
                CacheManager.getInstance().getObjectInCache(ListDevisesCache.class);
        
    	if (devises == null || isLoadFromBase) {
            try {
                List<Devise> res = (List<Devise>) SQLCallExecuter.getInstance().executeSelectProc(
                        "proc_Sel_Devise", new SQLParamDescriptorSet(),
                        "CommunUtilsDAO", "getDevises", contexte.getContexteAppelDAO(),
                        new SQLResultSetReader() {
                            public Object instanciateFromLine(ResultSet rs)
                                    throws SQLException {
                                Devise devise = new Devise();
                                devise.setCodeDevise(rs.getString("CODEDEVISEREPORTING"));
                                devise.setNomDevise(rs.getString("NOMDEVISE"));
                                try {
                                    devise.setCoursUnEuro(rs.getFloat("COURS_UN_EURO"));
                                } catch (Exception ex) {
                                    devise.setCoursUnEuro(-1);
                                }
                                return devise;
                            }
                        }
                );
                devises = new ListDevisesCache(res);
                CacheManager.getInstance().setObjectInCache(devises);
            } catch (SQLException e) {
                LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getDevises",
                        "Erreur lors de la recuperation de la liste des devices", e);
                throw new TechnicalException(e);
            }
        }
        return devises.getElements();
    }

    /**
     * Retourne la liste des devises d'un pays
     *
     * @param codeHotel
     * @param contexte
     * @return List<Devise>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Devise> getDevisesHotelPays( final String codeHotel, final Contexte contexte )
            throws TechnicalException {

    	List<Devise> res;
        try {
            res = (List<Devise>) SQLCallExecuter.getInstance().executeSelectProc(
            		"vente_getDevisesHotelPays",
                    new SQLParamDescriptorSet(new SQLParamDescriptor(codeHotel)),
                    "UtilesDAO", "getDevisesHotelPays",
                    contexte.getContexteAppelDAO(), new SQLResultSetReader() {
                public Object instanciateFromLine(ResultSet rs) throws SQLException {
                    Devise devise = new Devise();
                    devise.setCodeDevise(rs.getString("codeDevise"));
                    devise.setNomDevise(rs.getString("nomDevise"));
                    try {
                        devise.setCoursUnEuro(rs.getFloat("coursUnEuro"));
                    } catch (Exception ex) {
                        devise.setCoursUnEuro(-1);
                    }
                    devise.setDeviseHotel(rs.getBoolean("deviseHotel"));
                    return devise;
                }
            });
        } catch (SQLException e) {
            LogCommun.major(contexte.getCodeUtilisateur(), "UtilesDAO", "getDevisesHotelPays", e.getMessage());
            throw new TechnicalException(e);
        }
        return res;
    }


    /**
     * Recharger la liste des Marques
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
	@SuppressWarnings("unchecked")
	public List<Element> getMarques( final Contexte contexte ) throws TechnicalException {
        
		ListMarques marques = (ListMarques)
                CacheManager.getInstance().getObjectInCache(ListMarques.class);
        
		if (marques == null) {
            try {
                List<Element> res = (List<Element>) SQLCallExecuter.getInstance().executeSelectProc(
                        "proc_Selmarques", new SQLParamDescriptorSet(),
                        "CommunUtilsDAO", "getMarques", contexte.getContexteAppelDAO(),
                        new SQLResultSetReader() {
                            public Object instanciateFromLine(ResultSet rs)
                                    throws SQLException {
                                return new Element(rs.getString("codemarquehot"),
                                        rs.getString("nommarquehot"));
                            }
                        }
                );
                marques = new ListMarques(res);
                CacheManager.getInstance().setObjectInCache(marques);
            } catch (SQLException e) {
                LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getMarques",
                        "Erreur lors de la recuperation de la liste des marques", e);
                throw new TechnicalException(e);
            }
        }
        return marques.getElements();
    }

    /**
     * Recharger la liste des Chaines
     * @param contexte
     * @return List<Element>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
    public List<Element> getChaines( final Contexte contexte ) throws TechnicalException {
        
    	ListChaines chaines = (ListChaines)
                CacheManager.getInstance().getObjectInCache(ListChaines.class);
        
    	if (chaines == null) {
            try {
                List<Element> res = (List<Element>) SQLCallExecuter.getInstance().executeSelectProc(
                        "proc_selChaine", new SQLParamDescriptorSet(),
                        "CommunUtilsDAO", "getChaines", contexte.getContexteAppelDAO(),
                        new SQLResultSetReader() {
                            public Object instanciateFromLine(ResultSet rs)
                                    throws SQLException {
                                return new Element(rs.getString("codechaine"),
                                        rs.getString("nomchaine"));
                            }
                        }
                );
                chaines = new ListChaines(res);
                CacheManager.getInstance().setObjectInCache(chaines);

            } catch (SQLException e) {
                LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getChaines",
                        "Erreur lors de la recuperation de la liste des Chaines", e);
                throw new TechnicalException(e);
            }
        }
        return chaines.getElements();
    }


    /**
     * Recharger la liste des ASA Categories
     * @param contexte
     * @return List<AsaCategory>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<AsaCategory> getCategories( final Contexte contexte ) throws TechnicalException {
        
    	String codeLangue = contexte.getCodeLangue();
    	
    	ListCategories categories = (ListCategories)
                CacheManager.getInstance().getObjectInCache(ListCategories.class, codeLangue);
        
    	if (categories == null) {
            try {
                List<AsaCategory> res = (List<AsaCategory>) SQLCallExecuter.getInstance()
                		.executeSelectProc( "proc_sel_group_hotel",
                        new SQLParamDescriptorSet(new SQLParamDescriptor(codeLangue, false, Types.VARCHAR)),
                        "CommunUtilsDAO", "getCategories", contexte.getContexteAppelDAO(),
                        new SQLResultSetReader() {
                            public Object instanciateFromLine(ResultSet rs)
                                    throws SQLException {
                                return new AsaCategory(rs.getString("codeasacategory"),
                                        rs.getString("nomasacategory"));
                            }
                        }
                );
                categories = new ListCategories(res, codeLangue);
                CacheManager.getInstance().setObjectInCache(categories);

            } catch (SQLException e) {
                LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getCategories",
                        "Erreur lors de la recuperation de la liste des Categories", e);
                throw new TechnicalException(e);
            }
        }
        return categories.getElements();
    }


    /**
     * Methode de recuperation des segments
     * @param contexte
     * @return List<Segment>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Segment> getSegmentPrincipaux( final Contexte contexte ) throws TechnicalException {
        
    	ListSegments segments;
        try {
            String codeLangue = contexte.getCodeLangue();
            segments = (ListSegments) CacheManager.getInstance().getObjectInCache(ListSegments.class, codeLangue);
            if (segments == null) {
                List<Segment> list = (List<Segment>) SQLCallExecuter.getInstance().executeSelectProc(
                        "vente_sel_segments_principaux",
                        new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                                new SQLParamDescriptor(codeLangue, false, Types.CHAR)
                        }),
                        "CommunUtilsDAO", "getSegmentPrincipaux",
                        contexte.getContexteAppelDAO(),
                        new SQLResultSetReader() {
                            public Object instanciateFromLine(ResultSet rs) throws SQLException {
                                Segment segment = new Segment();
                                segment.setCode(rs.getString("CODESEGMENT"));
                                segment.setNom(rs.getString("NOMSEGMENT"));
                                segment.setCodeTypeSegment(StringUtils.trimToEmpty(rs.getString("CODETYPESEGMENT")));
                                return segment;
                            }
                        }
                );
                segments = new ListSegments(list, codeLangue);
                CacheManager.getInstance().setObjectInCache(segments);
            }
        } catch (SQLException e) {
            LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getSegmentPrincipaux",
                    "echec lors de la recuperation de la liste des segments : ", e);
            throw new TechnicalException(e);
        }
        return segments.getElements();
    }

    /**
     * Retourne le code langue d'un utilisateur
     * Utilisé dans le module translate et vente
     * @param login
     * @param contexte
     * @return String
     * @throws TechnicalException
     */
    public String getCodeLangue( final String login, final Contexte contexte ) throws TechnicalException {
        try {
            return (String) SQLCallExecuter.getInstance().
                    executeSelectSingleObjetProc("asa_select_langue_taducteur",
                            new SQLParamDescriptorSet(new SQLParamDescriptor(login)),
                            "CommunUtilsDAO", "getCodeLangue",
                            contexte.getContexteAppelDAO(), new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            return rs.getString("CODE_VALEUR");
                        }
                    });
        } catch (SQLException e) {
            LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getCodeLangue", e.getMessage());
            throw new TechnicalException(e);
        }
    }

    /**
     * Retourne la liste des ratelevels
     * @param contexte
     * @return List<RateLevel>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<RateLevel> getRateLevels( final Contexte contexte ) throws TechnicalException {
        
    	String codeLangue = contexte.getCodeLangue();
    	
    	ListRateLevels listInCache = (ListRateLevels)
                CacheManager.getInstance().getObjectInCache(ListRateLevels.class, codeLangue);
        
    	if (listInCache == null) {
            try {
                SQLParamDescriptor param = new SQLParamDescriptor(codeLangue, false, Types.VARCHAR);
                List<RateLevel> res = (List<RateLevel>) SQLCallExecuter.getInstance()
                		.executeSelectProc("proc_sel_ratelevel_by_famille",
                        new SQLParamDescriptorSet(param),
                        "CommunUtilsDAO", "getRateLevels", contexte.getContexteAppelDAO(),
                        new SQLResultSetReader() {
                            public Object instanciateFromLine(ResultSet rs)
                                    throws TechnicalException, SQLException {
                                RateLevel obj = new RateLevel();
                                obj.setIdFamilleTarif( new Integer( rs.getInt( "IDFAMILLETARIF" ) ) );
                                obj.setLibFamilleTarif(StringUtils.trimToEmpty(rs.getString("CODETYPETARIF")));
                                obj.setCode(StringUtils.trimToEmpty(rs.getString("CODETARIF")));
                                obj.setLibelle(StringUtils.trimToEmpty(rs.getString("NOMTARIF")));
                                obj.setCodeLangue(StringUtils.trimToEmpty(rs.getString("CODELANGUE")));
                                return obj;
                            }
                        });

                listInCache = new ListRateLevels(res, codeLangue);
                CacheManager.getInstance().setObjectInCache(listInCache);
            } catch (SQLException ex) {
                LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getRateLevels",
                        "Erreur lors de la recuperation de la liste des ratelevels", ex);
                throw new TechnicalException(ex);
            }
        }
        return listInCache.getElements();
    }
    /**
     * Recharger la liste des Periodes de validite
     * @param contexte
     * @return List<PeriodeDeValidite>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<PeriodeDeValidite> getPeriodesValidite( final Contexte contexte ) throws TechnicalException {
        
    	ListPeriodeValidite listInCache = (ListPeriodeValidite)
                CacheManager.getInstance().getObjectInCache(ListPeriodeValidite.class);
        
    	if (listInCache == null) {
            try {
                List<PeriodeDeValidite> res = (List<PeriodeDeValidite>) SQLCallExecuter.getInstance()
                		.executeSelectProc("proc_sel_periodevalidite",
                        new SQLParamDescriptorSet(),
                        "CommunUtilsDAO", "getPeriodesValidite", contexte.getContexteAppelDAO(),
                        new SQLResultSetReader() {
                            public Object instanciateFromLine(ResultSet rs)
                                    throws TechnicalException, SQLException {
                                try {
                                    PeriodeDeValidite obj = new PeriodeDeValidite();
                                    obj.setIdPeriodeValidite(rs.getInt("idperiodevalidite"));
                                    obj.setDateDebut(new AsaDate(rs.getString("datedeb"), AsaDate.ASA));
                                    obj.setDateFin(new AsaDate(rs.getString("datefin"), AsaDate.ASA));
                                    obj.setIdGroupeTarifs(rs.getInt("idgroupetarifs"));
                                    return obj;
                                } catch (ParseException e) {
                                    throw new TechnicalException(e);
                                }
                            }
                        });

                listInCache = new ListPeriodeValidite(res);
                CacheManager.getInstance().setObjectInCache(listInCache);
            } catch (SQLException ex) {
                LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getPeriodesValidite",
                        "Erreur lors de la recuperation de la liste des periodes de validite", ex);
                throw new TechnicalException(ex);
            }
        }
        return listInCache.getElements();
    }
    
    /***
     * 
     * @param codeHotel
     * @param contexte
     * @return String
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public String getCodeAsaCategoryForHotel( final String codeHotel, final Contexte contexte) 
    		throws TechnicalException {
    	String asa =null;
    	try{
	        List<String> res = (List<String>) SQLCallExecuter.getInstance().executeSelectProc(
	                "tars_getAsaCatForHotel",
	                new SQLParamDescriptorSet(new SQLParamDescriptor(codeHotel, false, Types.VARCHAR)),
	                "CommunUtilsDAO", "getCodeAsaCategoryForHotel", contexte.getContexteAppelDAO(),
	                new SQLResultSetReader() {
	                    public Object instanciateFromLine(ResultSet rs)
	                            throws SQLException {
	                        return rs.getString("codeasacategory");
	                    }
	                }
	        );
	       if(res != null && !res.isEmpty())
	    	   asa = res.get(0);
	       
	    } catch (SQLException e) {
	    	LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getCodeAsaCategoryForHotel",
	    			"Erreur lors de la recuperation de la liste des Categories", e);
	    	throw new TechnicalException(e);
    	}
    	return asa;
    }
    
    /**
     * Retourne la liste des chambres d'un hotel
     * @param codeHotel
     * @param contexte
     * @return List<Room>
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<Room> getRooms( final String codeHotel, final Contexte contexte )
            throws TechnicalException {
        try {
            return (List<Room>) SQLCallExecuter.getInstance().executeSelectProc(
                    "tarif_select_rooms",
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(codeHotel, false, Types.VARCHAR),
                            new SQLParamDescriptor(contexte.getCodeLangue(), false, Types.VARCHAR)
                    }),
                    "CommunUtilsDAO", "getRooms",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            Room r= new Room(rs.getString("codeproduit"),
                                            rs.getString("nomproduit"),
                                            new Integer( rs.getInt( "nbpers" ) ) );
                            r.setCodeType(rs.getString("codeTypeProduit"));
                            return r;
                        }
                    }
            );
        } catch (SQLException e) {
            LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getRooms",
                    "echec lors de la recuperation de la liste des chambres de l'hotel: "+codeHotel, e);
            throw new TechnicalException(e);
        }
    }
    
    /***
     * 
     * @param codeLangue
     * @return Locale
     */
    private Locale getLocaleFromCodeLangue(String codeLangue) {
        Locale loc = Locale.ENGLISH;

        if (!codeLangue.equals("GB"))
            loc = new Locale(codeLangue.toLowerCase(), "");

        return loc;
    }

    @SuppressWarnings("unchecked")
	public List<Continent> getContinents( final Contexte contexte ) throws TechnicalException {

        ListContinents lcontinents = (ListContinents) CacheManager.getInstance().getObjectInCache(ListContinents.class);
        if (lcontinents == null) {
           lcontinents=loadContinentsCache(contexte);
        }
        return lcontinents == null ? null : lcontinents.getElements();
    }
    
    @SuppressWarnings("unchecked")
	private ListContinents loadContinentsCache(final Contexte contexte) throws TechnicalException {
    	 ListContinents lcontinents;
    	 try {
         	List<Continent>  resultat = (List<Continent>) SQLCallExecuter.getInstance().executeSelectProc(
                     "tars_selContinents", new SQLParamDescriptorSet(), "CommunUtilsDAO", "getContinents", 
                     contexte.getContexteAppelDAO(), new SQLResultSetReader() {
                         public Object instanciateFromLine(ResultSet rs)
                                 throws TechnicalException, SQLException {
                             Continent conti = new Continent();
                             conti.setCode(rs.getString("cont_code"));
                             conti.setLibelle(rs.getString("cont_nom"));
                             return conti;
                         }
                     }
             );
         	lcontinents = new ListContinents(resultat);
            CacheManager.getInstance().setObjectInCache(lcontinents);

         } catch (TechnicalException ex) {
             LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getContinents",
                     "Erreur lors de la recuperation de la liste des continents", ex);
             throw new TechnicalException(ex);
         } catch (SQLException ex) {
             LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getContinents",
                     "Erreur lors de la recuperation de la liste des pays", ex);
             throw new TechnicalException(ex);
         }
         return lcontinents;
    }
    
    @SuppressWarnings("unchecked")
	private ListPays loadListPays(final Contexte contexte) throws TechnicalException {
    	ListPays pays;
    	try {
        	List<Pays>  resultat = (List<Pays>) SQLCallExecuter.getInstance().executeSelectProc(
                    "proc_Selpays", new SQLParamDescriptorSet(), "CommunUtilsDAO", "getPays",
                    contexte.getContexteAppelDAO(), new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs)
                                throws TechnicalException, SQLException {
                            Pays pays = new Pays();
                            pays.setCode(rs.getString("codepays"));
                            pays.setLibelle(rs.getString("nomPays"));
                            pays.setFlagEtat( new Integer( rs.getInt( "flagetat" ) ) );
                            pays.setIndicatifTelephone(rs.getString("indictel"));
                            pays.setIndicatifTelex(rs.getString("indictelex"));
                            return pays;
                        }
                    }
            );
            pays = new ListPays(resultat);
            CacheManager.getInstance().setObjectInCache(pays);

        } catch (TechnicalException ex) {
            LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getPays",
                    "Erreur lors de la recuperation de la liste des pays", ex);
            throw new TechnicalException(ex);
        } catch (SQLException ex) {
            LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getPays",
                    "Erreur lors de la recuperation de la liste des pays", ex);
            throw new TechnicalException(ex);
        }
        return pays;
    }

    public Continent getContinentByCode(String codeContinent, Contexte contexte)
            throws TechnicalException {
    	 ListContinents lcontinents = (ListContinents) CacheManager.getInstance().getObjectInCache(ListContinents.class);
         if (lcontinents == null) {
            lcontinents=loadContinentsCache(contexte);
         }
         return lcontinents == null ? null : lcontinents.getContinentByCode(codeContinent);
    }
    
    public Pays getPaysByCode(String codePays, Contexte contexte)
            throws TechnicalException {
    	 ListPays pays = (ListPays) CacheManager.getInstance().getObjectInCache(ListPays.class);
         if (pays == null) {
             pays = loadListPays(contexte);
         }
         return pays == null ? null : pays.getPaysByCode(codePays);
    }
    
    /**
     * Retourne la liste des tarifs d'un hotel, d'une famille de tarif
     * et d'une periode de validite
     * @param codeHotel
     * @param idFamilleTarif
     * @param idPeriodeValidite
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<RateLevel> getRateLevelsForGrille( String codeAsaCategory, int idFamilleTarif, int idPeriodeValidite, Contexte contexte )
            throws TechnicalException {
        try {
            return (List<RateLevel>)SQLCallExecuter.getInstance().executeSelectProc(
                    "tarif_selRateLevelsForGrille",
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(codeAsaCategory, false, Types.VARCHAR),
                            new SQLParamDescriptor( new Integer( idFamilleTarif ), false, Types.INTEGER),
                            new SQLParamDescriptor( new Integer( idPeriodeValidite ), false, Types.INTEGER),
                            new SQLParamDescriptor(contexte.getCodeLangue(), false, Types.VARCHAR)
                    }),
                    "RateInfoDAO", "getListRateLevels",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            RateLevel rl= new RateLevel();
                            rl.setCode(rs.getString("code"));
                             rl.setLibelle(rs.getString("nom"));
                             rl.setCodeTypeTarif(rs.getString("type"));
                             return rl;               
                        }
                    }
            );
        } catch (SQLException e) {
            LogCommun.major(contexte.getCodeUtilisateur(), "RateInfoDAO", "getRateLevelsList",
                    "echec lors de la recuperation de la liste des ratelevels pour asacategory: "+codeAsaCategory, e);
            throw new TechnicalException(e);
        }
    }
    
    /**
     * Retourne la liste des taxes d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws TechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<TaxeTarsBean> getTaxes( final String codeHotel, final Contexte contexte )
            throws TechnicalException {
        try {
            return (List<TaxeTarsBean>) SQLCallExecuter.getInstance().executeSelectProc(
                    "tarif_select_taxes",
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(codeHotel, false, Types.VARCHAR),
                            new SQLParamDescriptor(contexte.getCodeLangue(), false, Types.VARCHAR)
                    }),
                    "CommunUtilsDAO", "getTaxes",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {

                            return new TaxeTarsBean(rs.getString("codeTaxe"),
                                        rs.getString("nomTaxe"),
                                        rs.getString("typeTaxe"),
                                        new Integer( rs.getInt( "flagTTC" ) ) );
                        }
                    }
            );
        } catch (SQLException e) {
            LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getTaxes",
                    "echec lors de la recuperation de la liste des taxe de l'hotel: "+codeHotel, e);
            throw new TechnicalException(e);
        }
    }

    /**
     * Retourne la liste des tarifs d'un hotel
     * @param codeHotel
     * @param contexte
     * @return
     * @throws RatesTechnicalException
     */
    @SuppressWarnings("unchecked")
	public List<RateLevel> getRateLevelsForHotel( String codeHotel, Contexte contexte )
            throws TechnicalException{
        try {
            return (List<RateLevel>)SQLCallExecuter.getInstance().executeSelectProc(
                    "tarif_selRateLevelsForHotel",
                    new SQLParamDescriptorSet(new SQLParamDescriptor[]{
                            new SQLParamDescriptor(codeHotel, false, Types.VARCHAR),
                            new SQLParamDescriptor(contexte.getCodeLangue(), false, Types.VARCHAR)
                    }),
                    "RateInfoDAO", "getRateLevelsForHotel",
                    contexte.getContexteAppelDAO(),
                    new SQLResultSetReader() {
                        public Object instanciateFromLine(ResultSet rs) throws SQLException {
                            RateLevel rl= new RateLevel();
                            rl.setCode(rs.getString("code"));
                             rl.setLibelle(rs.getString("nom"));
                             rl.setCodeTypeTarif(rs.getString("type"));
                             return rl;
                        }
                    }
            );
        } catch (SQLException e) {
            LogCommun.major(contexte.getCodeUtilisateur(), "CommunUtilsDAO", "getRateLevelsForHotel",
                    "echec lors de la recuperation de la liste des ratelevels pour l hotel: "+codeHotel, e);
            throw new TechnicalException(e);
        }
    }
}
