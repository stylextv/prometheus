package net.prometheus.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class TextUtil {
	
	private static final long EXCEPTION_SLEEP_DURATION = 60000;
	
	public static String loadTextFromURL(String url) {
		try {
			
			URL u = new URL(url);
			InputStream stream = u.openStream();
			InputStreamReader reader = new InputStreamReader(stream);
			
			return readTextFromStream(reader);
			
		} catch (IOException exception) {
			ThreadUtil.sleep(EXCEPTION_SLEEP_DURATION);
			
			return loadTextFromURL(url);
		}
	}
	
	public static String loadTextFromFile(String path) {
		try {
			
			FileReader reader = new FileReader(path);
			
			return readTextFromStream(reader);
			
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		
		return null;
	}
	
	public static void saveTextToFile(String text, String path) {
		try {
			
			FileWriter writer = new FileWriter(path);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			
			bufferedWriter.write(text);
			bufferedWriter.close();
			
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	private static String readTextFromStream(InputStreamReader streamReader) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(streamReader);
		StringBuilder stringBuilder = new StringBuilder();
		
		while(true) {
			
			String s = bufferedReader.readLine();
			if(s == null) break;
			
			stringBuilder.append(s);
		}
		
		return stringBuilder.toString();
	}
	
}
