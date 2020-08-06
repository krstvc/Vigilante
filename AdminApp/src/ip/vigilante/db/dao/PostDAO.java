package ip.vigilante.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ip.vigilante.db.ConnectionPool;
import ip.vigilante.db.DAOUtil;
import ip.vigilante.model.EmergencyCategory;
import ip.vigilante.model.Post;
import ip.vigilante.model.PostEmergencyCategory;

public class PostDAO {
	
	private static ConnectionPool connPool = ConnectionPool.getConnectionPool();

	private static final String SQL_SELECT_ALL_POSTS = "select * from `ip`.`post`"
			+ " where `is_deleted` = false"
			+ " order by `time` desc;";
	private static final String SQL_SELECT_ALL_EMERGENCY_ALERT_POSTS = "select * from `ip`.`post`"
			+ " where `is_emergency_alert` = true and `is_deleted` = false"
			+ " order by `time` desc;";
	private static final String SQL_SELECT_POST_BY_ID = "select * from `ip`.`post`"
			+ " where `id` = ?"
			+ " order by `time` desc;";
	private static final String SQL_INSERT_POST = "insert into `ip`.`post`"
			+ " (`user_id`, `title`, `content`, `time`, `link`, `video_uri`, `location`, `is_emergency_alert`, `is_deleted`)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_UPDATE_POST = "update `ip`.`post`"
			+ " set `title` = ?, `content` = ?, `time` = ?, `link` = ?, `video_uri` = ?, `location` = ?, `is_emergency_alert` = ?"
			+ " where `id` = ?;";
	private static final String SQL_DELETE_POST = "update `ip`.`post`"
			+ " set `is_deleted` = true"
			+ " where `id` = ?;";
	private static final String SQL_SELECT_EMERGENCY_CATEGORIES = "select * from `ip`.`emergency_category` ec"
			+ " inner join `ip`.`post_emergency_category` pec on ec.`id` = pec.`emergency_category_id`"
			+ " where pec.`post_id` = ?;";
	private static final String SQL_INSERT_EMERGENCY_CATEGORY = "insert into `ip`.`post_emergency_category`"
			+ " (`post_id`, `emergency_category_id`)"
			+ " values (?, ?);";
	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * @return	List of all posts from database
	 */
	public static ArrayList<Post> selectAllPosts(){
		ArrayList<Post> ret = new ArrayList<>();
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_POSTS, false, values);
			set = ps.executeQuery();
			
			while(set.next()) {
				int id = set.getInt("id");
				int userId = set.getInt("user_id");
				String title = set.getString("title");
				String content = set.getString("content");
				String link = set.getString("link");
				String videoURI = set.getString("video_uri");
				String location = set.getString("location");
				Date time = set.getTimestamp("time");
				boolean isAlert = set.getBoolean("is_emergency_alert");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				Post post = new Post(id, userId, title, content, link, videoURI, location, time, isAlert, isDeleted);
				ret.add(post);
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
	 * @return	List of all posts marked as emergency alert from database
	 */
	public static ArrayList<Post> selectAllEmergencyAlertPosts(){
		ArrayList<Post> ret = new ArrayList<>();
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_EMERGENCY_ALERT_POSTS, false, values);
			set = ps.executeQuery();
			
			while(set.next()) {
				int id = set.getInt("id");
				int userId = set.getInt("user_id");
				String title = set.getString("title");
				String content = set.getString("content");
				String link = set.getString("link");
				String videoURI = set.getString("video_uri");
				String location = set.getString("location");
				Date time = set.getTimestamp("time");
				boolean isAlert = set.getBoolean("is_emergency_alert");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				Post post = new Post(id, userId, title, content, link, videoURI, location, time, isAlert, isDeleted);
				ret.add(post);
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
	 * @param id	Unique ID of a post
	 * @return		Post matched by the ID
	 */
	public static Post selectPostById(int id) {
		Post ret = null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_POST_BY_ID, false, values);
			set = ps.executeQuery();
			
			if(set.next()) {
				id = set.getInt("id");
				int userId = set.getInt("user_id");
				String title = set.getString("title");
				String content = set.getString("content");
				String link = set.getString("link");
				String videoURI = set.getString("video_uri");
				String location = set.getString("location");
				Date time = set.getTimestamp("time");
				boolean isAlert = set.getBoolean("is_emergency_alert");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				Post post = new Post(id, userId, title, content, link, videoURI, location, time, isAlert, isDeleted);
				ret = post;
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
	 * @param post	New post to be inserted in database
	 * @return		True if the post was inserted successfully, false otherwise
	 */
	public static boolean insertPost(Post post) {
		boolean ret = false;
		
		String time = post.getTime() != null ? df.format(post.getTime()) : null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {
				post.getUserId(),
				post.getTitle(),
				post.getContent(),
				time,
				post.getLink(),
				post.getVideoURI(),
				post.getLocation(),
				post.isEmergencyAlert(),
				post.isDeleted()
		};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_INSERT_POST, true, values);
			
			int affectedRows = ps.executeUpdate();
			if(affectedRows > 0) {
				ret = true;
			}
			
			set = ps.getGeneratedKeys();
			if(set.next()) {
				int id = set.getInt(1);
				post.setId(id);
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
	 * @param post	Post to be updated
	 * @return		True if post was updated successfully, false otherwise
	 */
	public static boolean updatePost(Post post) {
		boolean ret = false;
		
		String time = post.getTime() != null ? df.format(post.getTime()) : null;
		
		Connection conn = null;
		Object[] values = { 
				post.getTitle(),
				post.getContent(),
				time,
				post.getLink(),
				post.getVideoURI(),
				post.getLocation(),
				post.isEmergencyAlert(),
				post.getId()
		};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_UPDATE_POST, false, values);
			
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
	
	/**
	 * @param id	Unique ID of an post to be deleted
	 * @return		True if the post was deleted successfully, false otherwise
	 */
	public static boolean deletePost(int id) {
		boolean ret = false;
		
		Connection conn = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_DELETE_POST, false, values);
			
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
	
	/**
	 * @param postId	Unique ID of a post whose categories are selected
	 * @return			List of all emergency categories for a single post matched by the ID
	 */
	public static ArrayList<EmergencyCategory> selectAllEmergencyCategories(int postId){
		ArrayList<EmergencyCategory> ret = new ArrayList<>();
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { postId };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_EMERGENCY_CATEGORIES, false, values);
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
	 * @param pec	Contains unique IDs of a post and an emergency category
	 * @return		True if an emergency category was inserted successfully, false otherwise
	 */
	public static boolean insertEmergencyCategory(PostEmergencyCategory pec){
		boolean ret = false;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {
				pec.getPostId(),
				pec.getEmergencyCategoryId()
		};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_INSERT_EMERGENCY_CATEGORY, true, values);
			
			int affectedRows = ps.executeUpdate();
			if(affectedRows > 0) {
				ret = true;
			}
			
			set = ps.getGeneratedKeys();
			if(set.next()) {
				int id = set.getInt(1);
				pec.setId(id);
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
