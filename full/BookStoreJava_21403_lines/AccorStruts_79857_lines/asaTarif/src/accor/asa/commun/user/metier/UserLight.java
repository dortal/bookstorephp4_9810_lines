package com.accor.asa.commun.user.metier;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.metier.Coordinates;

@SuppressWarnings("serial")
public class UserLight implements Serializable {

    protected Integer 		id;
	protected String 		login;
	protected String 		hotelId;
	protected String 		hotelName;
	protected String 		civility;
	protected String 		firstName;
	protected String 		lastName;
	protected String 		saleZoneId;
	protected Coordinates	coordinates		= new Coordinates();

	public String toString() {
		StringBuffer sb = new StringBuffer(); 
		sb.append( "[id=" ).append( id ).append( "], " );
		sb.append( "[login=" ).append( login ).append( "], " );
		sb.append( "[hotelId=" ).append( hotelId ).append( "], " );
		sb.append( "[hotelName=" ).append( hotelName ).append( "], " );
		sb.append( "[firstName=" ).append( firstName ).append( "], " );
		sb.append( "[lastName=" ).append( lastName ).append( "], " );
		sb.append( "[civility=" ).append( civility ).append( "], " );
		sb.append( "[saleZoneId=" ).append( saleZoneId ).append( "], " );
		sb.append( "[coordinates=" ).append( coordinates ).append( "], " );
		return sb.toString();
	}

    /**
     * Renvoie le label de l'utilisateur : Nom de l'hotel ou Civilite+Prénom+Nom
     * @return
     */
    public String getLabel() {
        if( StringUtils.isBlank( hotelId ) ) {
            return StringUtils.trimToEmpty( civility ) 
            		+ " " + StringUtils.trimToEmpty( firstName ) 
            		+ " " + StringUtils.trimToEmpty( lastName );
        }
        // correction 3216 : on retourne le vrai nom issu de TARS
        return hotelName; //Cas d'un hotel 
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getCivility() {
		return civility;
	}

	public void setCivility(String civility) {
		this.civility = civility;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getSaleZoneId() {
		return saleZoneId;
	}

	public void setSaleZoneId(String saleZoneId) {
		this.saleZoneId = saleZoneId;
	}

	public String getMail() {
		return coordinates.getMail();
	}

	public String getFax() {
		return coordinates.getFax();
	}

	public String getPhone() {
		return coordinates.getPhone();
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
}
