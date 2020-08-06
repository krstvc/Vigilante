package ip.vigilante.service;

import java.util.ArrayList;

import ip.vigilante.db.dao.ImageDAO;
import ip.vigilante.model.Image;

public class ImageService {

	private static ImageService svc;
	
	private ImageService() {}
	
	public static ImageService getInstance() {
		if(svc == null) {
			svc = new ImageService();
		}
		return svc;
	}
	
	public ArrayList<Image> getImagesForPost(int postId) {
		return ImageDAO.selectAllImagesForPost(postId);
	}
	
	public Image getImageById(int id) {
		return ImageDAO.selectImageById(id);
	}
	
	public boolean addImage(Image image) {
		return ImageDAO.insertImage(image);
	}
	
	public boolean deleteImage(int id) {
		return ImageDAO.deleteImage(id);
	}
	
}
