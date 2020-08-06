package ip.vigilante.admin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CredentialsManager {
	
	private static MessageDigest md;
	
	static {
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static String getHashedPassword(String password) {
		/*md.reset();
		md.update(password.getBytes());
		String hash = new String(md.digest());
		return hash;*/
		return password;
	}

}
