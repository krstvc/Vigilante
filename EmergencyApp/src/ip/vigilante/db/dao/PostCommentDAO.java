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
import ip.vigilante.model.PostComment;

public class PostCommentDAO {
	
	private static ConnectionPool connPool = ConnectionPool.getConnectionPool();

	private static final String SQL_SELECT_ALL_COMMENTS_FOR_POST = "select * from `ip`.`post_comment`"
			+ " where `post_id` = ? and `is_deleted` = false"
			+ " order by `time` asc;";
	private static final String SQL_SELECT_COMMENT_BY_ID = "select * from `ip`.`post_comment`"
			+ " where `id` = ?;";
	private static final String SQL_INSERT_COMMENT = "insert into `ip`.`post_comment`"
			+ " (`post_id`, `user_id`, `content`, `image_uri`, `time`, `is_deleted`)"
			+ " values (?, ?, ?, ?, ?, ?);";
	private static final String SQL_DELETE_COMMENT = "update `ip`.`post comment`"
			+ " set `is_deleted` = true"
			+ " where `id` = ?;";
	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * @param postId	Unique ID of a post
	 * @return			List of all post comments for a single post from database matched by the ID
	 */
	public static ArrayList<PostComment> selectAllCommentsForPost(int postId){
		ArrayList<PostComment> ret = new ArrayList<>();
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { postId };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_COMMENTS_FOR_POST, false, values);
			set = ps.executeQuery();
			
			while(set.next()) {
				int id = set.getInt("id");
				postId = set.getInt("post_id");
				int userId = set.getInt("user_id");
				String content = set.getString("content");
				String imageURI = set.getString("image_uri");
				Date time = set.getTimestamp("time");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				PostComment comment = new PostComment(id, postId, userId, content, imageURI, time, isDeleted);
				ret.add(comment);
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
	 * @param id		Unique ID of a post comment
	 * @return			Post comment matched by the ID
	 */
	public static PostComment selectCommentById(int id) {
		PostComment ret = null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_COMMENT_BY_ID, false, values);
			set = ps.executeQuery();
			
			if(set.next()) {
				id = set.getInt("id");
				int postId = set.getInt("post_id");
				int userId = set.getInt("user_id");
				String content = set.getString("content");
				String imageURI = set.getString("image_uri");
				Date time = set.getTimestamp("time");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				PostComment comment = new PostComment(id, postId, userId, content, imageURI, time, isDeleted);
				ret = comment;
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
	 * @param comment	New post comment to be inserted in database
	 * @return			True if the post comment was inserted successfully, false otherwise
	 */
	public static boolean insertComment(PostComment comment) {
		boolean ret = false;
		
		String time = comment.getTime() != null ? df.format(comment.getTime()) : null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {
				comment.getPostId(),
				comment.getUserId(),
				comment.getContent(),
				comment.getImageURI(),
				time,
				comment.isDeleted()
		};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_INSERT_COMMENT, true, values);
			
			int affectedRows = ps.executeUpdate();
			if(affectedRows > 0) {
				ret = true;
			}
			
			set = ps.getGeneratedKeys();
			if(set.next()) {
				int id = set.getInt(1);
				comment.setId(id);
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
	 * @param id	Unique ID of an post comment to be deleted
	 * @return		True if the post comment was deleted successfully, false otherwise
	 */
	public static boolean deleteComment(int id) {
		boolean ret = false;
		
		Connection conn = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_DELETE_COMMENT, false, values);
			
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
