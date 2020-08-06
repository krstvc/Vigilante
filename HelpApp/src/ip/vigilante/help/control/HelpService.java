package ip.vigilante.help.control;

import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;

import ip.vigilante.help.util.PropertiesManager;
import ip.vigilante.model.EmergencyCall;
import ip.vigilante.service.EmergencyCallService;

@Path("/help")
public class HelpService {

	private static PropertiesManager props;
	private static EmergencyCallService callSvc;
	private static String BASE_APP_URL;
	
	static {
		props = PropertiesManager.getInstance();
		callSvc = EmergencyCallService.getInstance();
		BASE_APP_URL = props.getProperty("help_app_url");
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<EmergencyCall> getEmergencyCalls() {
		return callSvc.getCalls();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCallById(@PathParam("id") int id) {
		EmergencyCall call = callSvc.getCallById(id);
		
		return Response.status(200)
				.entity(call)
				.build();
	}
	
	@PUT
	@Path("/{id}")
	public Response report(@PathParam("id") int id) {
		EmergencyCall call = callSvc.getCallById(id);
		
		if(call != null) {
			int reportCount = call.getReportCount() + 1;
			call.setReportCount(reportCount);
			
			callSvc.updateCall(call);
			
			return Response.status(200).build();
		} else {
			return Response.status(404).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") int id) {
		boolean deleted = callSvc.deleteCall(id);
		
		if(deleted) {
			return Response.status(200).build();
		} else {
			return Response.status(404).build();
		}
	}
	
	@GET
	@Path("/rss")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRssFeed() {
		SyndFeed feed = new SyndFeedImpl();
		
		feed.setFeedType("rss_2.0");
		feed.setLink(BASE_APP_URL);
		
		feed.setTitle("Emergency calls");
		feed.setDescription("Calls for help");
		
		ArrayList<EmergencyCall> calls = callSvc.getCalls();
		
		ArrayList<SyndEntry> entries = new ArrayList<SyndEntry>();
		
		for(EmergencyCall call : calls) {
			SyndEntry entry = new SyndEntryImpl();
			
			entry.setTitle(call.getTitle());        
			entry.setLink(BASE_APP_URL + "/api/help/" + call.getId());
			entry.setPublishedDate(call.getTime());
			
			Element image = new Element("image", Namespace.getNamespace("image", "http://web.resource.org/rss/1.0/modules/image/"));
			image.addContent(call.getImageURI());
			entry.getForeignMarkup().add(image);
			
			SyndContent description = new SyndContentImpl();
			description.setType("text/html");
			description.setValue(call.getDescription());
			entry.setDescription(description);
			
			entries.add(entry);
		}
		
		feed.setEntries(entries);
		SyndFeedOutput syndFeedOutput = new SyndFeedOutput();
		
		String rss = "";
		try {
			rss = syndFeedOutput.outputString(feed);
		} catch (FeedException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
		return Response.status(200).entity(rss).build();
	}
	
}
