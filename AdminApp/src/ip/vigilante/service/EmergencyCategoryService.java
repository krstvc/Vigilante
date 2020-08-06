package ip.vigilante.service;

import java.util.ArrayList;

import ip.vigilante.db.dao.EmergencyCategoryDAO;
import ip.vigilante.model.EmergencyCategory;

public class EmergencyCategoryService {
	
	private static EmergencyCategoryService svc;
	
	private EmergencyCategoryService() {}
	
	public static EmergencyCategoryService getInstance() {
		if(svc == null) {
			svc = new EmergencyCategoryService();
		}
		return svc;
	}
	
	public ArrayList<EmergencyCategory> getCategories() {
		return EmergencyCategoryDAO.selectAllCategories();
	}
	
	public EmergencyCategory getCategoryById(int id) {
		return EmergencyCategoryDAO.selectCategoryById(id);
	}
	
	public boolean addCategory(EmergencyCategory cat) {
		return EmergencyCategoryDAO.insertCategory(cat);
	}
	
	public boolean deleteCategory(int id) {
		return EmergencyCategoryDAO.deleteCategory(id);
	}

}
