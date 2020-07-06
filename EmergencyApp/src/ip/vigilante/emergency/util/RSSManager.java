package ip.vigilante.emergency.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RSSManager {
	
	@SuppressWarnings("unchecked")
	public static List<SyndEntry> getFeed() {
		List<SyndEntry> ret = new ArrayList<SyndEntry>();
		try {
			SyndFeedInput input = new SyndFeedInput();
			
			URL url = new URL(UrlManager.RSS_FEED_URL);
			XmlReader reader = new XmlReader(url);
			
			SyndFeed feed = input.build(reader);
			ret = feed.getEntries();
		} catch(IOException | IllegalArgumentException | FeedException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
