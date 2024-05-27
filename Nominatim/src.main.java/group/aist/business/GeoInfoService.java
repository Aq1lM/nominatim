package group.aist.business;

import group.aist.dto.AddGeoInfoRequest;

/**
 * @author AqilM
 *
 * Service Interface containing functions for GeoInfo
 */
public interface GeoInfoService {
	
	void add(AddGeoInfoRequest addGeoInfoRequest);
}
