package net.prometheus.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.prometheus.util.gson.GsonTypeAdapter;

public class GsonUtil {
	
	public static <T> T fromJson(String json, Class<T> objectClass, Gson gson) {
		try {
			
			return gson.fromJson(json, objectClass);
			
		} catch(Exception exception) {}
		
		return null;
	}
	
	public static String toJson(Object object, Gson gson) {
		try {
			
			return gson.toJson(object);
			
		} catch(Exception exception) {}
		
		return null;
	}
	
	public static Gson buildGson(GsonTypeAdapter<?>... typeAdapters) {
		GsonBuilder builder = new GsonBuilder();
		
		builder.setPrettyPrinting();
		
		for(GsonTypeAdapter<?> typeAdapter : typeAdapters) {
			
			Type type = typeAdapter.getType();
			builder.registerTypeAdapter(type, typeAdapter);
		}
		
		return builder.create();
	}
	
}
