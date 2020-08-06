package ip.vigilante.admin.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ip.vigilante.model.Login;
import ip.vigilante.service.LoginService;

@Path("/activity")
public class ActivityOverviewControl {
	
	private static LoginService loginSvc;
	private static Calendar calendar;
	
	private static long HOUR_IN_MILLIS = 60 * 60 * 1000;
	private static long DAY_IN_MILLIS = 24 * HOUR_IN_MILLIS;
	
	static {
		loginSvc = LoginService.getInstance();
		calendar = Calendar.getInstance();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<Integer, Long> getActivityDetails() {
		HashMap<Integer, Long> ret = new HashMap<>();
		
		ArrayList<Login> logins = loginSvc.getLogins();
		
		Date from = new Date(System.currentTimeMillis() - DAY_IN_MILLIS);
		Date to = new Date(System.currentTimeMillis());
		
		List<Login> filteredLogins = logins.stream()
				.filter(l -> l.getLoginTime().after(from) && l.getLoginTime().before(to))
				.collect(Collectors.toList());
		
		for(int i = 0; i < 24; ++i) {
			Date time = new Date(from.getTime() + i * HOUR_IN_MILLIS);
			calendar.setTime(time);
			
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			long loginCount = filteredLogins.stream()
					.filter(d -> {
						calendar.setTime(new Date(d.getLoginTime().getTime()));
						int h = calendar.get(Calendar.HOUR_OF_DAY);
						return h == hour;
					})
					.map(l -> l.getUserId())
					.distinct()
					.count();
			
			ret.put(hour, loginCount);
		}
		
		return ret;
	}

}
