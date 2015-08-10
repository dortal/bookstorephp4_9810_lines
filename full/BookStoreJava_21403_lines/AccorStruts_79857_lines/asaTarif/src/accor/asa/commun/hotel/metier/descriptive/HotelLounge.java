package com.accor.asa.commun.hotel.metier.descriptive;

@SuppressWarnings("serial")
public class HotelLounge extends HotelElement {
	
	protected Integer	capacity;
	protected Integer	surface;
	protected Float		height;
	protected String	cover;
	
	protected Integer	nbPaxBoardRoom;
	protected Integer	nbPaxClassRoom;
	protected Integer	nbPaxTheatre;
	protected Integer 	nbPaxUShape;
	protected Integer	nbPaxVShape;
	protected Integer	nbPaxBanquet;
	protected Integer	nbPaxBanquet2;
	protected Integer	nbPaxBanquet3;
	protected Integer	nbPaxBanquet4;
	
	protected String	buffet;
	protected String	danceBuffet;
	protected String	cocktail;
	protected String	expo;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append( "[capacity=" ).append( capacity ).append( "], " );
		sb.append( "[surface=" ).append( surface ).append( "], " );
		sb.append( "[height=" ).append( height ).append( "], " );
		sb.append( "[cover=" ).append( cover ).append( "], " );
		sb.append( "[nbPaxBoardRoom=" ).append( nbPaxBoardRoom ).append( "], " );
		sb.append( "[nbPaxClassRoom=" ).append( nbPaxClassRoom ).append( "], " );
		sb.append( "[nbPaxTheatre=" ).append( nbPaxTheatre ).append( "], " );
		sb.append( "[nbPaxUShape=" ).append( nbPaxUShape ).append( "], " );
		sb.append( "[nbPaxVShape=" ).append( nbPaxVShape ).append( "], " );
		sb.append( "[nbPaxBanquet=" ).append( nbPaxBanquet ).append( "], " );
		sb.append( "[nbPaxBanquet2=" ).append( nbPaxBanquet2 ).append( "], " );
		sb.append( "[nbPaxBanquet3=" ).append( nbPaxBanquet3 ).append( "], " );
		sb.append( "[nbPaxBanquet4=" ).append( nbPaxBanquet4 ).append( "], " );
		sb.append( "[buffet=" ).append( buffet ).append( "], " );
		sb.append( "[danceBuffet=" ).append( danceBuffet ).append( "], " );
		sb.append( "[cocktail=" ).append( cocktail ).append( "], " );
		sb.append( "[expo=" ).append( expo ).append( "], " );
		return sb.toString();
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getSurface() {
		return surface;
	}

	public void setSurface(Integer surface) {
		this.surface = surface;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getNbPaxBoardRoom() {
		return nbPaxBoardRoom;
	}

	public void setNbPaxBoardRoom(Integer nbPaxBoardRoom) {
		this.nbPaxBoardRoom = nbPaxBoardRoom;
	}

	public Integer getNbPaxClassRoom() {
		return nbPaxClassRoom;
	}

	public void setNbPaxClassRoom(Integer nbPaxClassRoom) {
		this.nbPaxClassRoom = nbPaxClassRoom;
	}

	public Integer getNbPaxTheatre() {
		return nbPaxTheatre;
	}

	public void setNbPaxTheatre(Integer nbPaxTheatre) {
		this.nbPaxTheatre = nbPaxTheatre;
	}

	public Integer getNbPaxUShape() {
		return nbPaxUShape;
	}

	public void setNbPaxUShape(Integer nbPaxUShape) {
		this.nbPaxUShape = nbPaxUShape;
	}

	public Integer getNbPaxVShape() {
		return nbPaxVShape;
	}

	public void setNbPaxVShape(Integer nbPaxVShape) {
		this.nbPaxVShape = nbPaxVShape;
	}

	public Integer getNbPaxBanquet() {
		return nbPaxBanquet;
	}

	public void setNbPaxBanquet(Integer nbPaxBanquet) {
		this.nbPaxBanquet = nbPaxBanquet;
	}

	public Integer getNbPaxBanquet2() {
		return nbPaxBanquet2;
	}

	public void setNbPaxBanquet2(Integer nbPaxBanquet2) {
		this.nbPaxBanquet2 = nbPaxBanquet2;
	}

	public Integer getNbPaxBanquet3() {
		return nbPaxBanquet3;
	}

	public void setNbPaxBanquet3(Integer nbPaxBanquet3) {
		this.nbPaxBanquet3 = nbPaxBanquet3;
	}

	public Integer getNbPaxBanquet4() {
		return nbPaxBanquet4;
	}

	public void setNbPaxBanquet4(Integer nbPaxBanquet4) {
		this.nbPaxBanquet4 = nbPaxBanquet4;
	}

	public void setBuffet(String buffet) {
		this.buffet = buffet;
	}

	public void setDanceBuffet(String danceBuffet) {
		this.danceBuffet = danceBuffet;
	}

	public void setCocktail(String cocktail) {
		this.cocktail = cocktail;
	}

	public void setExpo(String expo) {
		this.expo = expo;
	}
	
	public Boolean haveBuffet() {
		if( buffet == null )
			return null;

		return new Boolean( "1".equals( buffet ) );
	}
	
	public Boolean haveDanceBuffet() {
		if( danceBuffet == null )
			return null;

		return new Boolean( "1".equals( danceBuffet ) );
	}
	
	public Boolean haveCocktail() {
		if( cocktail == null )
			return null;

		return new Boolean( "1".equals( cocktail ) );
	}
	
	public Boolean haveExpo() {
		if( expo == null )
			return null;

		return new Boolean( "1".equals( expo ) );
	}
}
