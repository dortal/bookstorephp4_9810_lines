<%--
  Created by IntelliJ IDEA.
  User: SDEKOUM
  Date: 1 oct. 2008
  Time: 11:21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <style type="text/css">
            <s:include value="../css/asaRate.css"/>
        </style>
    </head>
	<body class="Imprimable">
    <table>
        <tr><td colspan="20" align="center"><b><s:property value="getText('COM_EXP_LBL_PERIODEVALIDITE')"/> :</b> <s:property value="periodeValidite.libelle"/></td></tr>
    </table>
    <s:if test="hotelExports.size > 0">
        <s:iterator value="hotelExports" status="h">
            <table>
                <tr>
                    <td colspan="2"></td>
                    <td colspan="18"><b><s:property value="getText('COM_EXP_LBL_HOTEL')"/> :</b> <s:property value="name"/> - (<s:property value="code"/>)</td>
                </tr>
            </table>
            <s:iterator value="grilles" status="g">
                <table>
                    <tr>
                        <td colspan="2"></td>
                        <td colspan="18"><b><s:property value="getText('COM_EXP_LBL_FAMILLETARIF')"/> :</b> <s:property value="familleTarif.libelle"/></td>
                    </tr>
                    <tr>
                        <td colspan="2"></td>
                        <td colspan="18"><b><s:property value="getText('COM_EXP_LBL_STATUT')"/> :</b> <s:property value="statutGrille.libelle"/></td>
                    </tr>
                </table>
                <s:if test="rates.size > 0">
                    <s:if test="businessGrille">
                        <s:if test="adagioContext">
                            <table width="100%" class="ImprimList">
                                <thead>
                                    <tr>
                                        <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_PRODUCT')"/></th>
                                        <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_RATE')"/></th>
                                        <th colspan="2"><s:property value="getText('VAL_EXP_LBL_PRIODES')"/></th>
                                        <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_PERIODE_TYPE')"/></th>
                                        <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_DUREE_SEJOUR')"/></th>
                                        <th colspan="6"><s:property value="getText('VAL_EXP_LBL_PRIX')"/></th>
                                        <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_PERIODE_APPLICATION')"/></th>
                                        <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_COMMISSION')"/></th>
                                        <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_SALON_EVT')"/></th>
                                    </tr>
                                    <tr>
                                        <th><s:property value="getText('VAL_EXP_LBL_FROM')"/></th>
                                        <th><s:property value="getText('VAL_EXP_LBL_TO')"/></th>
                                        <th><s:property value="getText('VAL_EXP_LBL_1PAX')"/></th>
                                        <th><s:property value="getText('VAL_EXP_LBL_2PAX')"/></th>
                                        <th><s:property value="getText('VAL_EXP_LBL_3PAX')"/></th>
                                        <th><s:property value="getText('VAL_EXP_LBL_4PAX')"/></th>
                                        <th><s:property value="getText('VAL_EXP_LBL_5PAX')"/></th>
                                        <th><s:property value="getText('VAL_EXP_LBL_6PAX')"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <s:iterator value="rates" status="listStatus">
                                        <s:if test="#listStatus.odd == true">
                                            <tr id="tr<s:property value="key"/>" class="Even">
                                        </s:if>
                                        <s:else>
                                            <tr id="tr<s:property value="key"/>" class="Odd">
                                        </s:else>

                                        <td><s:property value="libProduit"/></td>
                                        <td><s:property value="codeRateLevel"/></td>
                                        <td align="center"><s:property value="dateDebut"/></td>
                                        <td align="center"><s:property value="dateFin"/></td>
                                        <td><s:property value="periodeGenerique.libelle"/></td>
                                        <td><s:property value="dureeSejour.libelle"/></td>
                                        <td align="right" nowrap="nowrap"><s:property value="prix1"/> </td>
                                        <td align="right" nowrap="nowrap"><s:property value="prix2"/> </td>
                                        <td align="right" nowrap="nowrap"><s:property value="prix3"/> </td>
                                        <td align="right" nowrap="nowrap"><s:property value="prix4"/> </td>
                                        <td align="right" nowrap="nowrap"><s:property value="prix5"/> </td>
                                        <td align="right" nowrap="nowrap"><s:property value="prix6"/> </td>

                                        <td><s:property value="divisionSemaine.libelle"/></td>
                                        <td align="right" nowrap="nowrap">
                                                <s:property value="commission"/>
                                        </td>
                                            <td><s:property value="libelleSalon"/></td>
                                        </tr>
                                    </s:iterator>
                                </tbody>
                            </table>
                        </s:if>
                        <s:else>
                            <table width="100%" class="ImprimList">
                              <thead>
                                <tr>
                                    <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_PRODUCT')"/></th>
                                    <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_RATE')"/></th>
                                    <th colspan="2"><s:property value="getText('VAL_EXP_LBL_PRIODES')"/></th>
                                    <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_PERIODE_TYPE')"/></th>
                                    <th colspan="2"><s:property value="getText('VAL_EXP_LBL_PRIX')"/></th>
                                    <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_PERIODE_APPLICATION')"/></th>
                                    <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_COMMISSION')"/></th>
                                    <th colspan="3"><s:property value="getText('VAL_EXP_LBL_PETITDEJEUNER')"/></th>
                                    <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_SALON_EVT')"/></th>
                                    <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_DUREE_SEJOUR')"/></th>
                                    <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_BLACKOUTDATES')"/></th>
                                    <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_OPENNEWCONTRACT')"/></th>
                                    <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_NBRENUITIEES')"/></th>
                                    <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_WEEKEND')"/></th>
                                </tr>
                                <tr>
                                    <th><s:property value="getText('VAL_EXP_LBL_FROM')"/></th>
                                    <th><s:property value="getText('VAL_EXP_LBL_TO')"/></th>
                                    <th><s:property value="getText('VAL_EXP_LBL_1PAX')"/></th>
                                    <th><s:property value="getText('VAL_EXP_LBL_2PAX')"/></th>
                                    <th><s:property value="getText('VAL_EXP_LBL_PRIXPETITDEJEUNER')"/></th>
                                    <th><s:property value="getText('VAL_EXP_LBL_TYPEPETITDEJEUNER')"/></th>
                                    <th><s:property value="getText('VAL_EXP_LBL_INCLUS')"/></th>
                                </tr>
                              </thead>
                              <tbody>
                                <s:iterator value="rates" status="listStatus">
                                  <s:set name="ds" scope="page" value=""></s:set>
                                    <tr id="tr<s:property value="key"/>">
                                        <td><s:property value="libProduit"/></td>
                                        <td><s:property value="codeRateLevel"/></td>
                                        <td align="center"><s:property value="dateDebut"/></td>
                                        <td align="center"><s:property value="dateFin"/></td>
                                        <td><s:property value="periodeGenerique.libelle"/></td>
                                        <td align="right">
                                          <s:property value="prix1"/>
                                        </td>
                                        <td align="right">
                                                <s:property value="prix2"/>
                                        </td>
                                        <td><s:property value="divisionSemaine.libelle"/></td>
                                        <td align="right">
                                                <s:property value="commission"/>
                                        </td>
                                        <td align="right"><s:property value="prixPdj"/></td>
                                        <td><s:property value="petitDejeuner.libelle"/></td>
                                        <td align="center" valign="center">
                                            <s:if test="mealPlan.pdjInclu">
                                                <s:text name="ALL_ALL_OUI"/>
                                            </s:if>
                                            <s:else>
                                                <s:text name="ALL_ALL_NON"/>
                                            </s:else>
                                        </td>
                                        <td><s:property value="libelleSalon"/></td>
                                        <td><s:property value="dureeSejour.libelle"/></td>
                                        <td align="center">
                                            <s:if test="blackOutDates">
                                                <s:text name="ALL_ALL_OUI"/>
                                            </s:if>
                                            <s:else>
                                                <s:text name="ALL_ALL_NON"/>
                                            </s:else>
                                        </td>
                                        <td align="center">
                                            <s:if test="openNewContrat">
                                                <s:text name="ALL_ALL_OUI"/>
                                            </s:if>
                                            <s:else>
                                                <s:text name="ALL_ALL_NON"/>
                                            </s:else>
                                        </td>
                                        <td align="center"><s:property value="nbreNuitsMin"/> - <s:property value="nbreNuitsMax"/></td>
                                        <td>
                                          <s:property value="weekendDays"/>
                                        </td>
                                    </tr>
                                </s:iterator>
                              </tbody>
                           </table>
                        </s:else>
                    </s:if>
                    <s:else>
                        <table width="100%" class="ImprimList">
                          <thead>
                            <tr>
                                <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_PRODUCT')"/></th>
                                <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_RATE')"/></th>
                                <th colspan="2"><s:property value="getText('VAL_EXP_LBL_PRIODES')"/></th>
                                <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_PERIODE_TYPE')"/></th>
                                <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_SALON_EVT')"/></th>
                                <th colspan="3"><s:property value="getText('VAL_EXP_LBL_PRIX')"/></th>
                                <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_TYPE_REPAS')"/></th>
                                <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_COMMISSION')"/></th>
                                <th colspan="2"><s:property value="getText('VAL_EXP_LBL_PETITDEJEUNER')"/></th>
                                <th colspan="2"><s:property value="getText('VAL_EXP_LBL_SUPPLEMENTS')"/></th>
                                <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_PERIODE_APPLICATION')"/></th>
                                <th rowspan="2"><s:property value="getText('VAL_EXP_LBL_WEEKEND')"/></th>
                            </tr>
                            <tr>
                                <th><s:property value="getText('VAL_EXP_LBL_FROM')"/></th>
                                <th><s:property value="getText('VAL_EXP_LBL_TO')"/></th>
                                <th><s:property value="getText('VAL_EXP_LBL_PRIX_1DBL')"/></th>
                                <th><s:property value="getText('VAL_EXP_LBL_PRIX_SUPPSGL')"/></th>
                                <th><s:property value="getText('VAL_EXP_LBL_PRIX_SUPPTRP')"/></th>
                                <th><s:property value="getText('VAL_EXP_LBL_PRIXPETITDEJEUNER')"/></th>
                                <th><s:property value="getText('VAL_EXP_LBL_INCLUS')"/></th>
                                <th><s:property value="getText('VAL_EXP_LBL_SUPP_DEMPENS')"/></th>
                                <th><s:property value="getText('VAL_EXP_LBL_SUPP_PENS')"/></th>
                            </tr>
                          </thead>
                          <tbody>
                            <s:iterator value="rates" status="listStatus">
                                <tr id="tr<s:property value="key"/>">
                                     <td><s:property value="libProduit"/></td>
                                    <td><s:property value="codeRateLevel"/></td>
                                    <td align="center"><s:property value="dateDebut"/></td>
                                    <td align="center"><s:property value="dateFin"/></td>
                                    <td><s:property value="periodeGenerique.getLibelle()"/></td>
                                    <td><s:property value="libelleSalon"/></td>
                                    <td align="right"><s:property value="prixLabel"/></td>
                                    <td align="right"><s:property value="prixSupSGLLabel"/></td>
                                    <td align="right"><s:property value="prixSupTRILabel"/></td>
                                    <td align="center"><s:property value="codeMealPlan"/></td>
                                    <td align="right"><s:property value="commission"/></td>
                                    <td align="right"><s:property value="prixPdj"/></td>
                                    <td align="center">
                                        <s:if test="pdjIncluded">
                                            <s:text name="ALL_ALL_OUI"/>
                                        </s:if>
                                        <s:else>
                                            <s:text name="ALL_ALL_NON"/>
                                        </s:else>
                                    </td>
                                    <td><s:property value="supplDemPens"/></td>
                                    <td><s:property value="supplPensCompl"/></td>
                                    <td><s:property value="divisionSemaine.libelle"/></td>
                                    <td><s:property value="weekendDays"/></td>
                                </tr>
                            </s:iterator>
                          </tbody>
                       </table>
                    </s:else>
                </s:if>
             </s:iterator>
            <table>
                <tr><td colspan="20"><hr></td></tr>
            </table>
        </s:iterator>
    </s:if>
	</body>
</html>