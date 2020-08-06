package ip.vigilante.help.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesManager {
	
	private static File PROPERTIES_FILE = new File(
			System.getProperty("user.home") + File.separator 
			+ "Documents" + File.separator 
			+ "Vigilante" + File.separator 
			+ "config.properties");
	
	private Properties props;
	
	private static PropertiesManager manager;
	
	private PropertiesManager() {
		props = new Properties();
		
		try {
			if(PROPERTIES_FILE.exists()) {
				FileReader reader = new FileReader(PROPERTIES_FILE);
				props.load(reader);
				reader.close();
			} else {
				PROPERTIES_FILE.getParentFile().mkdirs();
				PROPERTIES_FILE.createNewFile();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PropertiesManager getInstance() {
		if(manager == null) {
			manager = new PropertiesManager();
		}
		return manager;
	}
	
	public String getProperty(String key) {
		String val = props.getProperty(key);
		return val != null ? val : "";
	}
	
	public String getEncodedProperty(String key) {
		String prop = props.getProperty(key);
		return prop != null ? URLEncoder.encode(prop, StandardCharsets.UTF_8) : "";
	}

}
