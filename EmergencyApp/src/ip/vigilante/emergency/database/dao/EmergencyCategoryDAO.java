package ip.vigilante.emergency.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ip.vigilante.emergency.database.ConnectionPool;
import ip.vigilante.emergency.database.DAOUtil;
import ip.vigilante.emergency.model.EmergencyCategory;

public class EmergencyCategoryDAO {
	
	private static ConnectionPool connPool = ConnectionPool.getConnectionPool();

	private static final String SQL_SELECT_ALL_CATEGORIES = "select * from `ip`.`emergency_category`"
			+ " where `is_deleted` = false;";
	private static final String SQL_SELECT_CATEGORY_BY_ID = "select * from `ip`.`emergency_category`"
			+ " where `id` = ?;";
	private static final String SQL_INSERT_CATEGORY = "insert into `ip`.`emergency_category`"
			+ " (`category`, `is_deleted`)"
			+ " values (?, ?);";
	private static final String SQL_DELETE_CATEGORY = "update `ip`.`emergency_category`"
			+ " set `is_deleted` = true"
			+ " where `id` = ?;";
	
	
	/**
	 * @return	List of all emergency categories from database
	 */
	public static ArrayList<EmergencyCategory> selectAllCategories(){
		ArrayList<EmergencyCategory> ret = new ArrayList<>();
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_CATEGORIES, false, values);
			set = ps.executeQuery();
			
			while(set.next()) {
				int id = set.getInt("id");
				String categoryStr = set.getString("category");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				EmergencyCategory category = new EmergencyCategory(id, categoryStr, isDeleted);
				ret.add(category);
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	/**
	 * @param id	Unique ID of an emergency category
	 * @return		Emergency category matched by the ID
	 */
	public static EmergencyCategory selectCategoryById(int id) {
		EmergencyCategory ret = null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_CATEGORY_BY_ID, false, values);
			set = ps.executeQuery();
			
			if(set.next()) {
				id = set.getInt("id");
				String categoryStr = set.getString("category");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				EmergencyCategory category = new EmergencyCategory(id, categoryStr, isDeleted);
				ret = category;
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	/**
	 * @param category	New emergency category to be inserted in database
	 * @return			True if the emergency category was inserted successfully, false otherwise
	 */
	public static boolean insertCategory(EmergencyCategory category) {
		boolean ret = false;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {
				category.getCategory(),
				category.isDeleted()
		};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_INSERT_CATEGORY, true, values);
			
			int affectedRows = ps.executeUpdate();
			if(affectedRows > 0) {
				ret = true;
			}
			
			set = ps.getGeneratedKeys();
			if(set.next()) {
				int id = set.getInt(1);
				category.setId(id);
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	/**
	 * @param id	Unique ID of an emergency category to be deleted
	 * @return		True if the category was deleted successfully, false otherwise
	 */
	public static boolean deleteCategory(int id) {
		boolean ret = false;
		
		Connection conn = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_DELETE_CATEGORY, false, values);
			
			int affectedRows = ps.executeUpdate();
			if(affectedRows > 0) {
				ret = true;
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}

}
