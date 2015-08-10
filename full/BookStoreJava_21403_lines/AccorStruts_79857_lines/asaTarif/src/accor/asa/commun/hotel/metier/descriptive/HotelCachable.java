package com.accor.asa.commun.hotel.metier.descriptive;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.hotel.metier.HotelContact;
import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class HotelCachable extends AbstractCachable implements CachableInterface {

	private Hotel				hotel;
	private HotelAccounting 	accounting;
	private HotelBank			bank;
	private HotelDescriptive	descriptive;
	private HotelMarketing		marketing;
	
	private List<Element>				meetingRooms;
	private List<HotelAccess>			access;
	private List<HotelBar>				bars;
	private List<HotelContact>			staff;	
	private List<HotelInterestCenter>	interestCenters;
	private List<HotelLounge>			lounges;
	private List<HotelProduct>			products;
	private List<HotelRestaurant>		restaurants;
	
	public HotelCachable( final String rid, final String lang ) {
		this.params 	= new String[2];
		this.params[0] 	= rid;
		this.params[1]	= lang;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[hotel=" ).append( hotel ).append( "], " );
		sb.append( "[accounting=" ).append( accounting ).append( "], " );
		sb.append( "[bank=" ).append( bank ).append( "], " );
		sb.append( "[descriptive=" ).append( descriptive ).append( "], " );
		sb.append( "[marketing=" ).append( marketing ).append( "], " );
		sb.append( "[meetingRooms=" ).append( meetingRooms ).append( "], " );
		sb.append( "[access=" ).append( access ).append( "], " );
		sb.append( "[bars=" ).append( bars ).append( "], " );
		sb.append( "[staff=" ).append( staff ).append( "], " );
		sb.append( "[interestCenters=" ).append( interestCenters ).append( "], " );
		sb.append( "[lounges=" ).append( lounges ).append( "], " );
		sb.append( "[products=" ).append( products ).append( "], " );
		sb.append( "[restaurants=" ).append( restaurants ).append( "], " );
		return sb.toString();
	}
	
	@Override
	// Non applicable dans ce cas
	public Object getElements() {
		// TODO Auto-generated method stub
		return null;
	}

	public HotelAccounting getAccounting() {
		return accounting;
	}

	public void setAccounting(HotelAccounting accounting) {
		this.accounting = accounting;
	}

	public HotelBank getBank() {
		return bank;
	}

	public void setBank(HotelBank bank) {
		this.bank = bank;
	}

	public HotelMarketing getMarketing() {
		return marketing;
	}

	public void setMarketing(HotelMarketing marketing) {
		this.marketing = marketing;
	}

	public List<HotelInterestCenter> getInterestCenters() {
		return interestCenters;
	}

	public void setInterestCenters(List<HotelInterestCenter> interestCenters) {
		this.interestCenters = interestCenters;
	}

	public List<HotelAccess> getAccess() {
		return access;
	}

	public void setAccess(List<HotelAccess> access) {
		this.access = access;
	}

	public List<HotelProduct> getProducts() {
		return products;
	}

	public void setProducts(List<HotelProduct> products) {
		this.products = products;
	}

	public List<HotelRestaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<HotelRestaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public List<HotelBar> getBars() {
		return bars;
	}

	public void setBars(List<HotelBar> bars) {
		this.bars = bars;
	}

	public List<Element> getMeetingRooms() {
		return meetingRooms;
	}

	public void setMeetingRooms(List<Element> meetingRooms) {
		this.meetingRooms = meetingRooms;
	}

	public List<HotelLounge> getLounges() {
		return lounges;
	}

	public void setLounges(List<HotelLounge> lounges) {
		this.lounges = lounges;
	}

	public HotelDescriptive getDescriptive() {
		return descriptive;
	}

	public void setDescriptive(HotelDescriptive descriptive) {
		this.descriptive = descriptive;
	}

	public List<HotelContact> getStaff() {
		return staff;
	}

	public void setStaff(List<HotelContact> staff) {
		this.staff = staff;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

}
