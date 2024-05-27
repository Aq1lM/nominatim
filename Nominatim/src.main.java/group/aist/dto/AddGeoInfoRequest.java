package group.aist.dto;

/**
 * @author AqilM
 * 
 * AddGeoInfoRequest DTO
 *
 */
public class AddGeoInfoRequest {
	
	private int placeId;

	private String lat;
	private String lon;
	private String displayName;
	private String category;

	public AddGeoInfoRequest() {
	}

	public AddGeoInfoRequest(int placeId, String lat, String lon, String displayName, String category) {
		this.placeId = placeId;
		this.lat = lat;
		this.lon = lon;
		this.displayName = displayName;
		this.category = category;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
