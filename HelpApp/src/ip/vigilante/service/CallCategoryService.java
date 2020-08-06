package ip.vigilante.service;

import java.util.ArrayList;

import ip.vigilante.db.dao.CallCategoryDAO;
import ip.vigilante.model.CallCategory;

public class CallCategoryService {

	private static CallCategoryService svc;
	
	private CallCategoryService() {}
	
	public static CallCategoryService getInstance() {
		if(svc == null) {
			svc = new CallCategoryService();
		}
		return svc;
	}
	
	public ArrayList<CallCategory> getCategories() {
		return CallCategoryDAO.selectAllCategories();
	}
	
	public CallCategory getCategoryById(int id) {
		return CallCategoryDAO.selectCategoryById(id);
	}
	
	public boolean addCategory(CallCategory category) {
		return CallCategoryDAO.insertCategory(category);
	}
	
	public boolean deleteCategory(int id) {
		return CallCategoryDAO.deleteCategory(id);
	}
	
}
