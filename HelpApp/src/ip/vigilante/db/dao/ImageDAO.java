package ip.vigilante.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ip.vigilante.db.ConnectionPool;
import ip.vigilante.db.DAOUtil;
import ip.vigilante.model.Image;

public class ImageDAO {
	
	private static ConnectionPool connPool = ConnectionPool.getConnectionPool();

	private static final String SQL_SELECT_ALL_IMAGES_FOR_POST = "select * from `ip`.`image`"
			+ " where `post_id` = ? and `is_deleted` = false;";
	private static final String SQL_SELECT_IMAGE_BY_ID = "select * from `ip`.`image`"
			+ " where `id` = ?;";
	private static final String SQL_INSERT_IMAGE = "insert into `ip`.`image`"
			+ " (`post_id`, `image_uri`, `is_deleted`)"
			+ " values (?, ?, ?);";
	private static final String SQL_DELETE_IMAGE = "update `ip`.`image`"
			+ " set `is_deleted` = true"
			+ " where `id` = ?;";
	
	
	public static ArrayList<Image> selectAllImagesForPost(int postId){
		ArrayList<Image> ret = new ArrayList<>();
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { postId };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_ALL_IMAGES_FOR_POST, false, values);
			set = ps.executeQuery();
			
			while(set.next()) {
				int id = set.getInt("id");
				postId = set.getInt("post_id");
				String imageURI = set.getString("image_uri");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				Image img = new Image(id, postId, imageURI, isDeleted);
				ret.add(img);
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static Image selectImageById(int id) {
		Image ret = null;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_SELECT_IMAGE_BY_ID, false, values);
			set = ps.executeQuery();
			
			if(set.next()) {
				id = set.getInt("id");
				int postId = set.getInt("post_id");
				String imageURI = set.getString("image_uri");
				boolean isDeleted = set.getBoolean("is_deleted");
				
				Image img = new Image(id, postId, imageURI, isDeleted);
				ret = img;
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static boolean insertImage(Image image) {
		boolean ret = false;
		
		Connection conn = null;
		ResultSet set = null;
		Object[] values = {
				image.getPostId(),
				image.getImageURI(),
				image.isDeleted()
		};
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_INSERT_IMAGE, true, values);
			
			int affectedRows = ps.executeUpdate();
			if(affectedRows > 0) {
				ret = true;
			}
			
			set = ps.getGeneratedKeys();
			if(set.next()) {
				int id = set.getInt(1);
				image.setId(id);
			}
			
			ps.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connPool.checkIn(conn);
		}
		
		return ret;
	}
	
	public static boolean deleteImage(int id) {
		boolean ret = false;
		
		Connection conn = null;
		Object[] values = { id };
		
		try {
			conn = connPool.checkOut();
			PreparedStatement ps = DAOUtil.prepareStatement(conn, SQL_DELETE_IMAGE, false, values);
			
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
