package com.accor.asa.commun.utiles;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * tags pour le style et le jeu de caractères
 * <br>paramètre obligatoire : type
 * <ul>
 * <li>top : haut de page (remplace la directive weblogic page content)
 * <li>content : content-type avec charset
 * <li>style : css
 * </ul>
 * 
 * (fait dans le cadre de la correction de l'ano 2375)
 * 
 * @see ConvertJSP
 * 
 * @author Vincent GAUTIER
 */
public class HeadTag extends TagSupport {

   public static final String CHARSET_PROPNAME = "asa.html.content.charset";
   public static final String DEFAULT_CHARSET = "ISO-8859-1";

   public static final String PARAM_TYPE_TOP = "top";
   public static final String PARAM_TYPE_CONTENT = "content";
   public static final String PARAM_TYPE_STYLE = "style";

   private static String m_charset;
   
   static {
      m_charset = FilesPropertiesCache.getInstance().getValue(Proprietes.PROPERTIES_FILE_NAME,CHARSET_PROPNAME,DEFAULT_CHARSET);
      if ( m_charset == null || m_charset.length() == 0 )
         m_charset = DEFAULT_CHARSET;
   }
   public static final String getCharset() {
      return m_charset;
   }
   
   public HeadTag( ) {
   }

   public int doStartTag() throws JspException {
      try {
         JspWriter out = pageContext.getOut();
         out.println( printTypeLine() );
         return SKIP_BODY;
      }
      catch (IOException exc) {
         throw new JspException(exc.toString());
      }
   }

   /**
    * ecrit la ligne en fonction du type
    */
   private String printTypeLine() {
      String s = "";
      if ( m_type == null )
         ;
      else if ( m_type.equalsIgnoreCase(PARAM_TYPE_TOP) ) {
         // remplace le tag <%@ page contentType="text/html; charset=ISO-8859-1" %>
         // dans lequel on ne peut définir dynamiquement le charset         
         s = "<!-- charset set to "+m_charset+" -->";
         pageContext.getResponse().setContentType("text/html; charset="+m_charset);
      }
      else if ( m_type.equalsIgnoreCase(PARAM_TYPE_CONTENT) )
         s = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset="+m_charset+"\">";
      else if ( m_type.equalsIgnoreCase(PARAM_TYPE_STYLE) ) {
         HttpServletRequest request = (HttpServletRequest)pageContext.getRequest(); 
         String root = request.getContextPath();
         s = "<link rel=\"stylesheet\" href=\""+root+"/styles/styleaccor.css\" type=\"text/css\">";
      }
      return s;
   }


   private String m_type;

   public String getType() {
      return m_type;
   }

   public void setType(String type) {
      if ( type == null || 
           ( !type.equalsIgnoreCase(PARAM_TYPE_CONTENT) 
           && !type.equalsIgnoreCase(PARAM_TYPE_STYLE)
           && !type.equalsIgnoreCase(PARAM_TYPE_TOP) ))
         throw new RuntimeException("erreur dans le tag HeadTag : type '" + type + "' non supporte");
      m_type = type;
   }
   
   /* accesseurs pour l'outil de conversion 
    * @see ConvertJSP
    */
    
   public static String getTopTagString( ) { return getTagStringForType(PARAM_TYPE_TOP); }
   public static String getContentTagString( ) { return getTagStringForType(PARAM_TYPE_CONTENT); }
   public static String getStyleTagString( ) { return getTagStringForType(PARAM_TYPE_STYLE); }

   private static String getTagStringForType( String type ) {
      return "<asa:pageHead type=\""+type+"\"/>";
   }

}
