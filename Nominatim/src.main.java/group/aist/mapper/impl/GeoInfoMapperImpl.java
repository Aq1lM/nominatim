package group.aist.mapper.impl;

import group.aist.dto.AddGeoInfoRequest;
import group.aist.entities.GeoInfo;
import group.aist.mapper.GeoInfoMapper;

/**
 * @author AqilM
 * 
 * Service class for business logic of mapping
 */
public class GeoInfoMapperImpl implements GeoInfoMapper{

	/**
	 * Convert AddGeoInfoRequest object to GeoInfo object
	 * 
	 * @param AddGeoInfoRequest object
	 */
	@Override
	public GeoInfo forRequest(AddGeoInfoRequest addGeoInfoRequest) {
		GeoInfo geoInfo = new GeoInfo();
		geoInfo.setPlaceId(addGeoInfoRequest.getPlaceId());
		geoInfo.setLat(addGeoInfoRequest.getLat());
		geoInfo.setLon(addGeoInfoRequest.getLon());
		geoInfo.setDisplayName(addGeoInfoRequest.getDisplayName());
		geoInfo.setCategory(addGeoInfoRequest.getCategory());
		
		return geoInfo;
	}
}
