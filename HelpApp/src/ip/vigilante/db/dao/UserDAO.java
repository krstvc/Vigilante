package ip.vigilante.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ip.vigilante.db.ConnectionPool;
import ip.vigilante.db.DAOUtil;
import ip.vigilante.model.User;

public class UserDAO {
	
	private static ConnectionPool connPool = ConnectionPool.getConnectionPool();
	
	private static final String SQL_SELECT_ALL_USERS = "select * from `ip`.`user`"
			+ " where `is_deleted` = false;";
	private static final String SQL_SELECT_USER_BY_ID = "select * from `ip`.`user`"
			+ " where `id` = ? and `is_deleted` = false;";
	private static final String SQL_SELECT_USER_BY_USERNAME = "select * from `ip`.`user`"
			+ " where `username` = ? and `is_deleted` = false;";
	private static final String SQL_SELECT_USER_BY_EMAIL = "select * from `ip`.`user`"
			+ " where `email` = ? and `is_deleted` = false;";
	private static final String SQL_INSERT_USER = "insert into `ip`.`user`"
			+ " (`username`, `password_hash`, `name`, `surname`, `email`,"
			+ " `image_uri`, `country_code`, `region`, `city`, `subscribed_app`, `subscribed_mail`,"
			+ " `login_count`, `is_admin`, `is_approved`, `is_blocked`, `is_logged`, `is_deleted`)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_UPDATE_USER = "update `ip`.`user`"
			+ " set `username`= ?, `password_hash` = ?, `name` = ?, `surname` = ?, `email` = ?,"
			+ " `image_uri` = ?, `country_code` = ?, `region` = ?, `city` = ?, `subscribed_app` = ?, `subscribed_mail` = ?,"
			+ " `login_count` = ?, `is_admin` = ?, `is_approved` = ?, `is_blocked` = ?, `is_logged` = ?, `is_deleted` = ?"
			+ " where `id` = ?;";
	private static final String SQL_DELETE_USER = "update `ip`.`user`"
			+ " set `is_deleted` = true"
			+ " where `id` = ?;";
	private static final String SQL_USER_COUNT = "select count(*) as count from `ip`.`user` where `is_deleted` = false;";
	private static final String SQL_ONLINE_USER_COUNT = "select count(*) as count from `ip`.`user` where `is_deleted` = false and `is_logged` = true;";
	
	
	public static ArrayList<User> selectAllUsers(){
		ArrayList<User> ret = new ArrayList<>();
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_USERS, false, values);
			set = ps.executeQuery();
			
			while(set.next()) {
				int id = set.getInt("id");
				String username = set.getString("username");
				String pwHash = set.getString("password_hash");
				String name = set.getString("name");
				String surname = set.getString("surname");
				String email = set.getString("email");
				String imageURI = set.getString("image_uri");
				String countryCode = set.getString("country_code");
				String region = set.getString("region");
				String city = set.getString("city");
				int loginCount = set.getInt("login_count");
				boolean isSubApp = set.getBoolean("subscribed_app");
				boolean isSubMail = set.getBoolean("subscribed_mail");
				boolean isAdmin = set.getBoolean("is_admin");
				boolean isApproved = set.getBoolean("is_approved");
				boolean isBlocked = set.getBoolean("is_blocked");
				boolean isLogged = set.getBoolean("is_logged");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				User user = new User(id, username, pwHash, name, surname, email, imageURI, countryCode, region, city,
						isSubApp, isSubMail, isAdmin, isApproved, isBlocked, isLogged, isDeleted, loginCount);
				ret.add(user);
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static User selectUserById(int id) {
		User ret = null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_USER_BY_ID, false, values);
			set = ps.executeQuery();
			
			if(set.next()) {
				id = set.getInt("id");
				String username = set.getString("username");
				String pwHash = set.getString("password_hash");
				String name = set.getString("name");
				String surname = set.getString("surname");
				String email = set.getString("email");
				String imageURI = set.getString("image_uri");
				String countryCode = set.getString("country_code");
				String region = set.getString("region");
				String city = set.getString("city");
				int loginCount = set.getInt("login_count");
				boolean isSubApp = set.getBoolean("subscribed_app");
				boolean isSubMail = set.getBoolean("subscribed_mail");
				boolean isAdmin = set.getBoolean("is_admin");
				boolean isApproved = set.getBoolean("is_approved");
				boolean isBlocked = set.getBoolean("is_blocked");
				boolean isLogged = set.getBoolean("is_logged");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				User user = new User(id, username, pwHash, name, surname, email, imageURI, countryCode, region, city,
						isSubApp, isSubMail, isAdmin, isApproved, isBlocked, isLogged, isDeleted, loginCount);
				ret = user;
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static User selectUserByUsername(String username) {
		User ret = null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { username };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_USER_BY_USERNAME, false, values);
			set = ps.executeQuery();
			
			if(set.next()) {
				int id = set.getInt("id");
				username = set.getString("username");
				String pwHash = set.getString("password_hash");
				String name = set.getString("name");
				String surname = set.getString("surname");
				String email = set.getString("email");
				String imageURI = set.getString("image_uri");
				String countryCode = set.getString("country_code");
				String region = set.getString("region");
				String city = set.getString("city");
				int loginCount = set.getInt("login_count");
				boolean isSubApp = set.getBoolean("subscribed_app");
				boolean isSubMail = set.getBoolean("subscribed_mail");
				boolean isAdmin = set.getBoolean("is_admin");
				boolean isApproved = set.getBoolean("is_approved");
				boolean isBlocked = set.getBoolean("is_blocked");
				boolean isLogged = set.getBoolean("is_logged");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				User user = new User(id, username, pwHash, name, surname, email, imageURI, countryCode, region, city,
						isSubApp, isSubMail, isAdmin, isApproved, isBlocked, isLogged, isDeleted, loginCount);
				ret = user;
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static User selectUserByEmail(String email) {
		User ret = null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { email };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_USER_BY_EMAIL, false, values);
			set = ps.executeQuery();
			
			if(set.next()) {
				int id = set.getInt("id");
				String username = set.getString("username");
				String pwHash = set.getString("password_hash");
				String name = set.getString("name");
				String surname = set.getString("surname");
				email = set.getString("email");
				String imageURI = set.getString("image_uri");
				String countryCode = set.getString("country_code");
				String region = set.getString("region");
				String city = set.getString("city");
				int loginCount = set.getInt("login_count");
				boolean isSubApp = set.getBoolean("subscribed_app");
				boolean isSubMail = set.getBoolean("subscribed_mail");
				boolean isAdmin = set.getBoolean("is_admin");
				boolean isApproved = set.getBoolean("is_approved");
				boolean isBlocked = set.getBoolean("is_blocked");
				boolean isLogged = set.getBoolean("is_logged");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				User user = new User(id, username, pwHash, name, surname, email, imageURI, countryCode, region, city,
						isSubApp, isSubMail, isAdmin, isApproved, isBlocked, isLogged, isDeleted, loginCount);
				ret = user;
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static boolean insertUser(User user) {
		boolean ret = false;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {
				user.getUsername(),
				user.getPasswordHash(),
				user.getName(),
				user.getSurname(),
				user.getEmail(),
				user.getImageURI(),
				user.getCountryCode(),
				user.getRegion(),
				user.getCity(),
				user.isSubscribedToAppNotifications(),
				user.isSubscribedToMailNotifications(),
				user.getLoginCount(),
				user.isAdmin(),
				user.isApproved(),
				user.isBlocked(),
				user.isLogged(),
				user.isDeleted()
		};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_INSERT_USER, true, values);
			
			int affectedRows = ps.executeUpdate();
			if(affectedRows > 0) {
				ret = true;
			}
			
			set = ps.getGeneratedKeys();
			if(set.next()) {
				int id = set.getInt(1);
				user.setId(id);
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static boolean updateUser(User user) {
		boolean ret = false;
		
		Connection conn = null;
		Object[] values = { 
				user.getUsername(),
				user.getPasswordHash(),
				user.getName(),
				user.getSurname(),
				user.getEmail(),
				user.getImageURI(),
				user.getCountryCode(),
				user.getRegion(),
				user.getCity(),
				user.isSubscribedToAppNotifications(),
				user.isSubscribedToMailNotifications(),
				user.getLoginCount(),
				user.isAdmin(),
				user.isApproved(),
				user.isBlocked(),
				user.isLogged(),
				user.isDeleted(),
				user.getId()
		};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_UPDATE_USER, false, values);
			
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
	
	public static boolean deleteUser(int id) {
		boolean ret = false;
		
		Connection conn = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_DELETE_USER, false, values);
			
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
	
	public static int getUserCount() {
		int ret = 0;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_USER_COUNT, false, values);
			
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
	
	public static int getOnlineUserCount() {
		int ret = 0;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_ONLINE_USER_COUNT, false, values);
			
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
