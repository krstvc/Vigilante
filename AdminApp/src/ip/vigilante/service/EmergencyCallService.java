package ip.vigilante.service;

import java.util.ArrayList;

import ip.vigilante.db.dao.EmergencyCallDAO;
import ip.vigilante.model.EmergencyCall;

public class EmergencyCallService {

	private static EmergencyCallService svc;
	
	private EmergencyCallService() {}
	
	public static EmergencyCallService getInstance() {
		if(svc == null) {
			svc = new EmergencyCallService();
		}
		return svc;
	}
	
	public ArrayList<EmergencyCall> getCalls() {
		return EmergencyCallDAO.selectAllCalls();
	}
	
	public EmergencyCall getCallById(int id) {
		return EmergencyCallDAO.selectCallById(id);
	}
	
	public boolean addCall(EmergencyCall call) {
		return EmergencyCallDAO.insertCall(call);
	}
	
	public boolean deleteCall(int id) {
		return EmergencyCallDAO.deleteCall(id);
	}
	
}
