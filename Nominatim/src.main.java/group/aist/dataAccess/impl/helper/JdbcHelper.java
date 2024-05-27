package group.aist.dataAccess.impl.helper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author AqilM
 * 
 * JdbcHelper class that provides connection with database
 *
 */

public class JdbcHelper {
	
	private static Connection connection;

	static {
		try {
			Class.forName(JdbcConstants.DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * create connection with database
	 * 
	 * @return connection
	 */

	public static Connection getConnection() {
		
		try {
			connection = DriverManager.getConnection(JdbcConstants.URL, JdbcConstants.USERNAME, JdbcConstants.PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;
	}
	
	/**
	 * check existence of table in database
	 * 
	 * @param tableName - name of table
	 * @return boolean value
	 * @throws SQLException
	 */
	public static boolean tableExists(String tableName) throws SQLException {
		
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet resultSet = metaData.getTables(null, null, tableName, new String[] {"TABLE"});
		
		return resultSet.next();
	}

}
