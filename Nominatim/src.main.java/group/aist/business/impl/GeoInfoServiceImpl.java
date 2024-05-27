package group.aist.business.impl;

import group.aist.business.GeoInfoService;
import group.aist.dataAccess.GeoInfoRepository;
import group.aist.dto.AddGeoInfoRequest;
import group.aist.entities.GeoInfo;
import group.aist.mapper.GeoInfoMapper;

/**
 * @author AqilM
 * 
 * Service class containing business logic for GeoInfo
 */

public class GeoInfoServiceImpl implements GeoInfoService {

	private final GeoInfoMapper geoInfoMapper;
	private final GeoInfoRepository geoInfoRepository;

	public GeoInfoServiceImpl(GeoInfoMapper geoInfoMapper, GeoInfoRepository geoInfoRepository) {

		this.geoInfoMapper = geoInfoMapper;
		this.geoInfoRepository = geoInfoRepository;
	}

	/**
	 * add AddGeoInfoRequest to repository
	 * 
	 * @param AddGeoInfoRequest
	 */
	@Override
	public void add(AddGeoInfoRequest addGeoInfoRequest) {

		GeoInfo geoInfo = this.geoInfoMapper.forRequest(addGeoInfoRequest);

		this.geoInfoRepository.insert(geoInfo);
	}

}
