package com.accor.asa.commun.reference.metier;
import com.accor.asa.commun.metier.categorie.AsaCategory;


@SuppressWarnings("serial")
public class ParamTransfertRefBean extends RefBean{

		private String codeAsaCategory;
		private int  idFamilleTarif;
		private int idPeriodeValidite;
		
		private AsaCategory asaCategory;
		private FamilleTarifRefBean familleTarif;
		private PeriodeValiditeRefBean periodeValidite;
		
		public ParamTransfertRefBean() {
			super();
		}
		
		public String getCodeAsaCategory() {
			return codeAsaCategory;
		}
		public void setCodeAsaCategory(String codeAsaCategory) {
			this.codeAsaCategory = codeAsaCategory;
		}
		public int getIdFamilleTarif() {
			return idFamilleTarif;
		}
		public void setIdFamilleTarif(int idFamilleTarif) {
			this.idFamilleTarif = idFamilleTarif;
		}
		public AsaCategory getAsaCategory() {
			return asaCategory;
		}
		public void setAsaCategory(AsaCategory asaCategory) {
			this.asaCategory = asaCategory;
			if(asaCategory==null)
				setCodeAsaCategory(null);
			else
				setCodeAsaCategory(asaCategory.getCode());
		}
		public FamilleTarifRefBean getFamilleTarif() {
			return familleTarif;
		}
		public void setFamilleTarif(FamilleTarifRefBean familleTarif) {
			if(familleTarif==null)
				setIdFamilleTarif(-1);
			else
				setIdFamilleTarif(Integer.parseInt(familleTarif.getId()));
			this.familleTarif = familleTarif;
		}
		
		public ParamTransfertRefBean(String codeAsaCategory, int idFamilleTarif) {
			super();
			this.codeAsaCategory = codeAsaCategory;
			this.idFamilleTarif = idFamilleTarif;
		}
		public String getId() {
			return getCodeAsaCategory()+"_"+getIdFamilleTarif();
		}
		public String getCode() {
			return super.getId();
		}
		public int getIdPeriodeValidite() {
			return idPeriodeValidite;
		}
		public void setIdPeriodeValidite(int idPeriodeValidite) {
			this.idPeriodeValidite = idPeriodeValidite;
		}
		public PeriodeValiditeRefBean getPeriodeValidite() {
			return periodeValidite;
		}
		public void setPeriodeValidite(PeriodeValiditeRefBean periodeValidite) {
			this.periodeValidite = periodeValidite;
			if(periodeValidite==null)
				setIdPeriodeValidite(-1);
			else
				setIdPeriodeValidite(Integer.parseInt(periodeValidite.getCode()));
		}
		
		
}
