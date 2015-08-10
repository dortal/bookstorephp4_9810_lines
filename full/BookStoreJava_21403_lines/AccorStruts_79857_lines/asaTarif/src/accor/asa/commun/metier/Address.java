/*
 * put your module comment here
 * formatted with JxBeauty (c) johann.langhofer@nextra.at
 */


package  com.accor.asa.commun.metier;


/**
 * @description   Classe contenant l'adresse du créature du compte.
 * @pattern       enum
 * @author        A.CHETBANI
 * @extends
 * @creation date 18/4/2001
 * @Modifications
 */
@SuppressWarnings("serial")
public class Address
        implements java.io.Serializable {
    /**
     *  paramètres.
     */
    private String address1;
    private String address2;
    private String address3;
    private String zipCode;
    private String POBox;
    private String city;
    private String stateId;
    private String stateName;
    private String countryId;
    private String countryName;

    public String toString() {
 		StringBuffer sb = new StringBuffer();
 		sb.append( "[address1=" ).append( address1 ).append( "], " );
		sb.append( "[address2=" ).append( address2 ).append( "], " );
		sb.append( "[address3=" ).append( address3 ).append( "], " );
		sb.append( "[zipCode=" ).append( zipCode ).append( "], " );
		sb.append( "[POBox=" ).append( POBox ).append( "], " );
 		sb.append( "[city=" ).append( city ).append( "], " );
		sb.append( "[stateId=" ).append( stateId ).append( "], " );
		sb.append( "[stateName=" ).append( stateName ).append( "], " );
		sb.append( "[countryId=" ).append( countryId ).append( "], " );
		sb.append( "[countryName=" ).append( countryName ).append( "], " );
		return sb.toString();
    }

	/**
     *  Constructeur.
     */
    public Address () {
    }

    /**
     * Get the Adresse1 property.
     */
    public String getAddress1 () {
        return  address1;
    }

    /**
     * Set the Adresse1 property.
     */
    public void setAddress1 (String newAddress1) {
        address1 = newAddress1;
    }

    /**
     * Get the Adresse2 property.
     */
    public String getAddress2 () {
        return  address2;
    }

    /**
     * Set the Adresse2 property.
     */
    public void setAddress2 (String newAddress2) {
        address2 = newAddress2;
    }

    /**
     * Get the Adresse3 property.
     */
    public String getAddress3 () {
        return  address3;
    }

    /**
     * Set the Adresse3 property.
     */
    public void setAddress3 (String newAddress3) {
        address3 = newAddress3;
    }

    /**
     * Get the CodePostal property.
     */
    public String getZipCode () {
        return  zipCode;
    }

    /**
     * Set the CodePostal property.
     */
    public void setZipCode (String newZipCode) {
        zipCode = newZipCode;
    }

    /**
     * Get the Ville property.
     */
    public String getCity () {
        return  city;
    }

    /**
     * Set the Ville property.
     */
    public void setCity (String newCity) {
        city = newCity;
    }

    /**
     * Get the Bp property.
     */
    public String getPOBox () {
        return  POBox;
    }

    /**
     * Set the Bp property.
     */
    public void setPOBox (String newPOBox) {
        POBox = newPOBox;
    }

    /**
     * Get the Pays property.
     */
    /**
     * Set the Pays property.
     */
    /**
     * Get the Etat property.
     */
    /**
     * Set the Etat property.
     */
    public void setStateId (String newStateId) {
        stateId = newStateId;
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getStateId () {
        return  stateId;
    }

    /**
     * put your documentation comment here
     * @param newIdPays
     */
    public void setCountryId (String newCountryId) {
        countryId = newCountryId;
    }

    /**
     * put your documentation comment here
     * @return
     */
    public String getCountryId () {
        return  countryId;
    }

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}



