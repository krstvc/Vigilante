package ip.vigilante.emergency.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import ip.vigilante.emergency.database.ConnectionPool;
import ip.vigilante.emergency.database.DAOUtil;
import ip.vigilante.emergency.model.EmergencyCall;

public class EmergencyCallDAO {
	
	private static ConnectionPool connPool = ConnectionPool.getConnectionPool();
	
	private static final String SQL_SELECT_ALL_CALLS = "select * from `ip`.`emergency_call`"
			+ " where `is_deleted` = false;";
	private static final String SQL_SELECT_CALL_BY_ID = "select * from `ip`.`emergency_call`"
			+ " where `id` = ?;";
	private static final String SQL_INSERT_CALL = "insert into `ip`.`emergency_call`"
			+ " (`call_category_id`, `title`, `description`, `image_uri`, `time`, `location`, `is_deleted`)"
			+ " values (?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_DELETE_CALL = "update `ip`.`emergency_call`"
			+ " set `is_deleted` = true"
			+ " where `id` = ?;";
	
	
	/**
	 * @return	List of all emergency calls from database
	 */
	public static ArrayList<EmergencyCall> selectAllCalls(){
		ArrayList<EmergencyCall> ret = new ArrayList<>();
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_CALLS, false, values);
			set = ps.executeQuery();
			
			while(set.next()) {
				int id = set.getInt("id");
				int categoryId = set.getInt("call_category_id");
				String title = set.getString("title");
				String desc = set.getString("description");
				String img = set.getString("image_uri");
				Date time = new Date(set.getTime("time").getTime());
				String location = set.getString("location");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				EmergencyCall call = new EmergencyCall(id, categoryId, title, desc, img, time, location, isDeleted);
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
	 * @param id	Unique ID of an emergency call
	 * @return		Emergency call matched by the ID
	 */
	public static EmergencyCall selectCallById(int id) {
		EmergencyCall ret = null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_CALL_BY_ID, false, values);
			set = ps.executeQuery();
			
			if(set.next()) {
				id = set.getInt("id");
				int categoryId = set.getInt("call_category_id");
				String title = set.getString("title");
				String desc = set.getString("description");
				String img = set.getString("image_uri");
				Date time = new Date(set.getTime("time").getTime());
				String location = set.getString("location");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				EmergencyCall call = new EmergencyCall(id, categoryId, title, desc, img, time, location, isDeleted);
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
	 * @param call	New emergency call to be inserted to database
	 * @return		True if the call was inserted successfully, false otherwise
	 */
	public static boolean insertCall(EmergencyCall call) {
		boolean ret = false;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {
				call.getCallCategoryId(),
				call.getTitle(),
				call.getDescription(),
				call.getImageURI(),
				call.getTime(),
				call.getLocation(),
				call.isDeleted()
		};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_INSERT_CALL, true, values);
			
			int affectedRows = ps.executeUpdate();
			if(affectedRows > 0) {
				ret = true;
			}
			
			set = ps.getGeneratedKeys();
			if(set.next()) {
				int id = set.getInt(1);
				call.setId(id);
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
	 * @param id	Unique ID of an emergency call to be deleted
	 * @return		True if the call was deleted successfully, false otherwise
	 */
	public static boolean deleteCall(int id) {
		boolean ret = false;
		
		Connection conn = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_DELETE_CALL, false, values);
			
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
