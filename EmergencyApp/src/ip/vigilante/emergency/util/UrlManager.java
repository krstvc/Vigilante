package ip.vigilante.emergency.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlManager {
	
	public static final String COUNTRY_SERVICE_BASE_URL = "https://restcountries.eu/rest/v2/region/europe";
	public static final String BATTUTA_BASE_URL = "http://battuta.medunes.net/api/";
	public static final String OPENWEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";
	public static final String RSS_FEED_URL = "https://europa.eu/newsroom/calendar.xml_en?field_nr_events_by_topic_tid=151";
	
	public static final String DEFAULT_AVATAR_SRC = "https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png";
	
	public static final String BATTUTA_KEY = "00000000000000000000000000000000";
	public static final String WEATHER_KEY = "f6fb7a99f512dc4e4d3c5f65aec73a96";
	public static final String LEAFLET_TOKEN = "pk.eyJ1Ijoia3JzdHZjIiwiYSI6ImNrYnNsbzU5NTAwbXoyc2xpdmx1cnF2ZXAifQ.ksKrgxHuPiBKlvDH-AHb0g";
	
	
	public static String getRegionsUrl(String countryCode) {
		return BATTUTA_BASE_URL + "region/"
				+ URLEncoder.encode(countryCode, StandardCharsets.UTF_8) + "/all/?key=" + BATTUTA_KEY;
	}
	
	public static String getRegionsUrlJsonp(String countryCode, String callback) {
		return getRegionsUrl(countryCode) + "&callback=" + URLEncoder.encode(callback, StandardCharsets.UTF_8);
	}
	
	public static String getCitiesUrl(String countryCode, String region) {
		return BATTUTA_BASE_URL + "city/" 
				+ URLEncoder.encode(countryCode, StandardCharsets.UTF_8) + "/search/?region=" 
				+ URLEncoder.encode(region, StandardCharsets.UTF_8) + "&key="
				+ URLEncoder.encode(BATTUTA_KEY, StandardCharsets.UTF_8);
	}
	
	public static String getCitiesUrlJsonp(String countryCode, String region, String callback) {
		return getCitiesUrl(countryCode, region) + "&callback=" + URLEncoder.encode(callback, StandardCharsets.UTF_8);
	}

}
