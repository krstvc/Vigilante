package ip.vigilante.emergency.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ip.vigilante.emergency.database.ConnectionPool;
import ip.vigilante.emergency.database.DAOUtil;
import ip.vigilante.emergency.model.CallCategory;

public class CallCategoryDAO {
	
	private static ConnectionPool connPool = ConnectionPool.getConnectionPool();

	private static final String SQL_SELECT_ALL_CATEGORIES = "select * from `ip`.`call_category`"
			+ " where `is_deleted` = false;";
	private static final String SQL_SELECT_CATEGORY_BY_ID = "select * from `ip`.`call_category`"
			+ " where `id` = ?;";
	private static final String SQL_INSERT_CATEGORY = "insert into `ip`.`call_category`"
			+ " (`category`, `is_deleted`)"
			+ " values (?, ?);";
	private static final String SQL_DELETE_CATEGORY = "update `ip`.`call_category`"
			+ " set `is_deleted` = true"
			+ " where `id` = ?;";
	
	
	/**
	 * @return	List of all emergency call categories from database
	 */
	public static ArrayList<CallCategory> selectAllCategories(){
		ArrayList<CallCategory> ret = new ArrayList<>();
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_CATEGORIES, false, values);
			set = ps.executeQuery();
			
			while(set.next()) {
				int id = set.getInt("id");
				String category = set.getString("category");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				CallCategory call = new CallCategory(id, category, isDeleted);
				ret.add(call);
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
	 * @param id	Unique ID
	 * @return		Call category matched by the ID
	 */
	public static CallCategory selectCategoryById(int id) {
		CallCategory ret = null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_CATEGORY_BY_ID, false, values);
			set = ps.executeQuery();
			
			if(set.next()) {
				id = set.getInt("id");
				String category = set.getString("category");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				CallCategory call = new CallCategory(id, category, isDeleted);
				ret = call;
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
	 * @param category	New call category to be inserted in database
	 * @return			True if the call category was inserted successfully, false otherwise
	 */
	public static boolean insertCategory(CallCategory category) {
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
	 * @param id	Unique ID of a call category to be deleted
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
