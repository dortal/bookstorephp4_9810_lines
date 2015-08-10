package com.accor.asa.commun.hotel.metier;

import java.io.Serializable;


@SuppressWarnings("serial")
public class HotelStaff implements Serializable {

	private String frontOfficeManager 		= null;
	private String generalManager 			= null;
	private String assistGeneralManager		= null;
	private String groupsManager 			= null;
	private String roomDivisionManager 		= null;
	private String reservationManager 		= null;
	private String executiveChief 			= null;
	private String banquetingManager 		= null;
	private String banquetingManagerMail 	= null;
	private String marketingManager 		= null;
	private String marketingManagerMail 	= null;
	

	public HotelStaff() {}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[frontOfficeManager=" ).append( frontOfficeManager ).append( "], " );
		sb.append( "[generalManager=" ).append( generalManager ).append( "], " );
		sb.append( "[assistGeneralManager=" ).append( assistGeneralManager ).append( "], " );
		sb.append( "[groupsManager=" ).append( groupsManager ).append( "], " );
		sb.append( "[roomDivisionManager=" ).append( roomDivisionManager ).append( "], " );
		sb.append( "[reservationManager=" ).append( reservationManager ).append( "], " );
		sb.append( "[executiveChief=" ).append( executiveChief ).append( "], " );
		sb.append( "[banquetingManager=" ).append( banquetingManager ).append( "], " );
		sb.append( "[banquetingManagerMail=" ).append( banquetingManagerMail ).append( "], " );
		sb.append( "[marketingManager=" ).append( marketingManager ).append( "], " );
		sb.append( "[marketingManagerMail=" ).append( marketingManagerMail ).append( "], " );
		return sb.toString();
	}

	public String getFrontOfficeManager() {
		return frontOfficeManager;
	}

	public void setFrontOfficeManager(String frontOfficeManager) {
		this.frontOfficeManager = frontOfficeManager;
	}

	public String getGeneralManager() {
		return generalManager;
	}

	public void setGeneralManager(String generalManager) {
		this.generalManager = generalManager;
	}

	public String getAssistGeneralManager() {
		return assistGeneralManager;
	}

	public void setAssistGeneralManager(String assistGeneralManager) {
		this.assistGeneralManager = assistGeneralManager;
	}

	public String getGroupsManager() {
		return groupsManager;
	}

	public void setGroupsManager(String groupsManager) {
		this.groupsManager = groupsManager;
	}

	public String getRoomDivisionManager() {
		return roomDivisionManager;
	}

	public void setRoomDivisionManager(String roomDivisionManager) {
		this.roomDivisionManager = roomDivisionManager;
	}

	public String getReservationManager() {
		return reservationManager;
	}

	public void setReservationManager(String reservationManager) {
		this.reservationManager = reservationManager;
	}

	public String getExecutiveChief() {
		return executiveChief;
	}

	public void setExecutiveChief(String executiveChief) {
		this.executiveChief = executiveChief;
	}

	public String getBanquetingManager() {
		return banquetingManager;
	}

	public void setBanquetingManager(String banquetingManager) {
		this.banquetingManager = banquetingManager;
	}

	public String getBanquetingManagerMail() {
		return banquetingManagerMail;
	}

	public void setBanquetingManagerMail(String banquetingManagerMail) {
		this.banquetingManagerMail = banquetingManagerMail;
	}

	public String getMarketingManager() {
		return marketingManager;
	}

	public void setMarketingManager(String marketingManager) {
		this.marketingManager = marketingManager;
	}

	public String getMarketingManagerMail() {
		return marketingManagerMail;
	}

	public void setMarketingManagerMail(String marketingManagerMail) {
		this.marketingManagerMail = marketingManagerMail;
	}
}
