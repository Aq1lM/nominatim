package group.aist.dataAccess.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import group.aist.dataAccess.GeoInfoRepository;
import group.aist.dataAccess.impl.helper.JdbcHelper;
import group.aist.entities.GeoInfo;

/**
 * 
 * @author AqilM
 * 
 * Service class for JDBC business logic
 *
 */

public class JdbcGeoInfo implements GeoInfoRepository {
	
	private static Statement statement = null;

	private static final String tableName = "geo_infos";

	private static final String createTable = "CREATE TABLE " + tableName
			+ " (id INT not NULL PRIMARY KEY AUTO_INCREMENT, " + " place_id INT, " + " lat VARCHAR(255), "
			+ " lon VARCHAR(255), " + "display_name VARCHAR(255), " + "category VARCHAR(255));";

	private static final String insertQuery = "INSERT INTO " + tableName
			+ " (PLACE_ID, LAT, LON, DISPLAY_NAME, CATEGORY) VALUES(%d,%s,%s,%s,%s)";

	private static final String select = "SELECT * FROM " + tableName;
	
	static {
		try {
			statement = JdbcHelper.getConnection().createStatement();
			if (!JdbcHelper.tableExists(tableName)) {
				statement.executeUpdate(createTable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * insert GeoInfo object to database
	 * 
	 * @param -> GeoInfo object
	 */
	@Override
	public void insert(GeoInfo geoInfo) {
		
		try {
			statement.executeUpdate(String.format(insertQuery, geoInfo.getPlaceId(), geoInfo.getLat(), geoInfo.getLon(),
					geoInfo.getDisplayName(), geoInfo.getCategory()));

			ResultSet resultSet = statement.executeQuery(select);

			while (resultSet.next()) {
				System.out.println(resultSet.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
