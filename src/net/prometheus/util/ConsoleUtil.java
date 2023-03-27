package net.prometheus.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUtil {
	
	public static String readLine() {
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(reader);
		
		try {
			
			return bufferedReader.readLine();
			
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		
		return null;
	}
	
}
