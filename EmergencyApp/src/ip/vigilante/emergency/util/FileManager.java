package ip.vigilante.emergency.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.servlet.http.Part;

public class FileManager {
	
	public static String saveFile(String username, Part part) {
		String returnPath = "images/";
		Random rand = new Random();
		
		try
		{
			String submittedFileName = part.getSubmittedFileName();
			String extension = submittedFileName.substring(submittedFileName.lastIndexOf('.'));

			String fileName;
			String fullFileName;
			File file;
			do {
				String suffix = "" + rand.nextInt();
				fileName = username + suffix + extension;
				fullFileName = "E:\\Projects\\Java\\IP\\EmergencyApp\\WebContent\\images\\" + fileName;
				file = new File(fullFileName);
			} while(file.exists());
			returnPath += fileName;

			InputStream fileContent = part.getInputStream();
			byte[] fileBytes = fileContent.readAllBytes();
			
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				fos.write(fileBytes);
			}
			catch (FileNotFoundException e) {
				System.out.println("File not found" + e);
			}
			catch (IOException ioe) {
				System.out.println("Exception while writing file " + ioe);
			}
			finally {
				try {
					if (fos != null) {
						fos.close();
					}
				}
				catch (IOException ioe) {
					System.out.println("Error while closing stream: " + ioe);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnPath;
	}

}
