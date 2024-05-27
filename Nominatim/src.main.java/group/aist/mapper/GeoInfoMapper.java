package group.aist.mapper;

import group.aist.dto.AddGeoInfoRequest;
import group.aist.entities.GeoInfo;

/**
 * @author AqilM
 * 
 * Service interface for mapping
 */
public interface GeoInfoMapper {
	GeoInfo forRequest(AddGeoInfoRequest addGeoInfoRequest);
}
