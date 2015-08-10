package com.accor.asa.commun.versioninfo.presentation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.jsp.JspWriter;

import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.utiles.FilesPropertiesCache;
import com.accor.asa.commun.utiles.HtmlFormatHelper;
import com.accor.asa.commun.utiles.process.CommunUtilsFacade;
import com.accor.asa.commun.versioninfo.metier.DatabaseVersionInformation;

/**
 * helper pour afficher les informations de version de l'application
 * 
 * 
 * @author Vincent GAUTIER
 */
public class VersionInfoHTMLHelper {
       
    // définition des sources possibles
    // pools
    public static final int INFO_POOL_ASA = 0;
    public static final int INFO_POOL_QBE = 1;
    public static final int INFO_POOL_REPORT = 2;
    public static final int INFO_POOL_PMS = 4;
    // autres
    public static final int INFO_TC = 5;
    public static final int INFO_APPLI = 6;



    private static VersionInfoHTMLHelper s_instance;
    private VersionInfoHTMLHelper(){}
    public static VersionInfoHTMLHelper getInstance() {
        if ( s_instance == null )
            s_instance = new VersionInfoHTMLHelper();
        return s_instance;
    }
    
    /**
     * printTableDbInfo 
     * @param out
     * @param login
     * @param codeInfo
     * @param titre
     * @throws IOException
     */
    public void printTableDbInfo( JspWriter out, String login, int codeInfo, String titre ) throws IOException {
        out.println("<TR></TR><TR><TD COLSPAN='2' align='left'><B>"+titre+"</B></TD></TR>");
        DatabaseVersionInformation info = null;
        ContexteAppelDAO ctx = new ContexteAppelDAO(login);
        try {
            CommunUtilsFacade facade = PoolCommunFactory.getInstance().getCommunUtilsFacade();
            info = facade.getVersionInfoForSource(ctx, codeInfo);
            if (info != null)
                printTableResult( out, info ); 
                
        }
        catch (Exception e) {
            out.println("<TR><TD colspan='2' bgcolor='red'>Erreur sur " + titre + " : "+e.getMessage()+"</TD></TR>");
            out.println("<TR><TD></TD><TD bgcolor='red'>");
            printErrorStackInTextarea( out, e );
            out.println("</TD></TR>");
        }        
    }

    private void printErrorStackInTextarea( JspWriter out, Exception e ) throws IOException {
        // impression de la stack
        out.println("<TEXTAREA NAME='errorstack' COLS=80 ROWS=10 >");
        e.printStackTrace(new PrintWriter(out));
        out.println("</TEXTAREA>");
    }
    
    public void printSystemPropertiesInfo( JspWriter out, String titre ) throws IOException {
        out.println("<TR><TD colspan='2' align='middle'><B>"+titre+"</B></TD></TR>");
        Enumeration<?> allProps = System.getProperties().propertyNames();
        while (allProps.hasMoreElements()) {
            String name = (String)allProps.nextElement();
            String value = HtmlFormatHelper.getInstance().decouperLigne(System.getProperty(name,""),100);
            printLigne(out, name, value);
        }
    }

    public void printPropertiesInfo( JspWriter out, String proprietesFileNameName, String titre ) throws IOException {
        out.println("<TR><TD colspan='2' align='middle'><B>"+titre+"</B></TD></TR>");
        try {
                Enumeration<?> allProps = FilesPropertiesCache.getInstance().keys(proprietesFileNameName);
                while (allProps.hasMoreElements()) {
                    String name = (String)allProps.nextElement();
                    String value = HtmlFormatHelper.getInstance().decouperLigne(FilesPropertiesCache.getInstance().getValue(proprietesFileNameName,name,""),100);
                    printLigne(out, name, value);
                }
        }
        catch (Exception e) {            
            out.println("<TR><TD>Erreur (props #"+proprietesFileNameName+") : "+e.getMessage()+"</TD><TD>");
            printErrorStackInTextarea( out, e );
            out.println("</TD></TR>");
        }
    }
    
    private void printTableResult( JspWriter out, DatabaseVersionInformation info ) 
        throws IOException {
        printLigne(out,"date",info.execDate);
        printLigne(out,"user (connexion)",info.userName);
        printLigne(out,"user (login)",info.loginName);
        printLigne(out,"serveur BD",info.serverName);
        printLigne(out,"base BD",info.dbName);
        printLigne(out,"version ASA",info.asaVersion);
    }
    
    private void printLigne( JspWriter out, String libelle, String valeur) 
        throws IOException {
        out.println("<TR><TD valign='top'>"+libelle+"</TD><TD>"+valeur+"</TD></TR>");
    }


}
