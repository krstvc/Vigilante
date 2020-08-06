package ip.vigilante.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import ip.vigilante.db.ConnectionPool;
import ip.vigilante.db.DAOUtil;
import ip.vigilante.model.Login;

@XmlRootElement
public class LoginDAO {
	
	private static ConnectionPool connPool = ConnectionPool.getConnectionPool();

	private static final String SQL_SELECT_ALL_LOGINS = "select * from `ip`.`login`;";
	private static final String SQL_SELECT_ALL_LOGINS_FOR_USER = "select * from `ip`.`login`"
			+ " where `user_id` = ?;";
	private static final String SQL_SELECT_LOGIN_BY_ID = "select * from `ip`.`login`"
			+ " where `id` = ?;";
	private static final String SQL_INSERT_LOGIN = "insert into `ip`.`login`"
			+ " (`user_id`, `login_time`, `logout_time`)"
			+ " values (?, ?, ?);";
	private static final String SQL_UPDATE_LOGIN = "update `ip`.`login`"
			+ " set `logout_time` = ?"
			+ " where `id` = ?;";
	private static final String SQL_COUNT_ONLINE_USERS = "select count(`user_id`) as count from `ip`.`login`"
			+ " where `logout_time` is not null;";
	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static ArrayList<Login> selectAllLogins(){
		ArrayList<Login> ret = new ArrayList<>();
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_LOGINS, false, values);
			set = ps.executeQuery();
			
			while(set.next()) {
				int id = set.getInt("id");
				int userId = set.getInt("user_id");
				Date loginTime = set.getTimestamp("login_time");
				Date logoutTime = set.getTimestamp("logout_time");
				
				Login login = new Login(id, userId, loginTime, logoutTime);
				ret.add(login);
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static ArrayList<Login> selectAllLoginsForUser(int userId){
		ArrayList<Login> ret = new ArrayList<>();
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { userId };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_LOGINS_FOR_USER, false, values);
			set = ps.executeQuery();
			
			while(set.next()) {
				int id = set.getInt("id");
				userId = set.getInt("user_id");
				Date loginTime = set.getTimestamp("login_time");
				Date logoutTime = set.getTimestamp("logout_time");
				
				Login login = new Login(id, userId, loginTime, logoutTime);
				ret.add(login);
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static Login selectLoginById(int id) {
		Login ret = null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_LOGIN_BY_ID, false, values);
			set = ps.executeQuery();
			
			if(set.next()) {
				id = set.getInt("id");
				int userId = set.getInt("user_id");
				Date loginTime = set.getTimestamp("login_time");
				Date logoutTime = set.getTimestamp("logout_time");
				
				Login login = new Login(id, userId, loginTime, logoutTime);
				ret = login;
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static boolean insertLogin(Login login) {
		boolean ret = false;
		
		String loginTime = login.getLoginTime() != null ? df.format(login.getLoginTime()) : null;
		String logoutTime = login.getLogoutTime() != null ? df.format(login.getLogoutTime()) : null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {
				login.getUserId(),
				loginTime,
				logoutTime
		};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_INSERT_LOGIN, true, values);
			
			int affectedRows = ps.executeUpdate();
			if(affectedRows > 0) {
				ret = true;
			}
			
			set = ps.getGeneratedKeys();
			if(set.next()) {
				int id = set.getInt(1);
				login.setId(id);
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static boolean updateLogin(Login login) {
		boolean ret = false;
		
		String logoutTime = login.getLogoutTime() != null ? df.format(login.getLogoutTime()) : null;
		
		Connection conn = null;
		Object[] values = { 
				logoutTime,
				login.getId()
		};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_UPDATE_LOGIN, false, values);
			
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
	
	public static int getOnlineUsersCount() {
		int ret = 0;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_COUNT_ONLINE_USERS, false, values);
			
			set = ps.executeQuery();
			if(set.next()) {
				ret = set.getInt("count");
			}
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}

}
