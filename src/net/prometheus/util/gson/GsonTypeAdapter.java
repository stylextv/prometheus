package net.prometheus.util.gson;

import java.lang.reflect.Type;

import com.google.gson.TypeAdapter;

public abstract class GsonTypeAdapter<T> extends TypeAdapter<T> {
	
	private Type type;
	
	public GsonTypeAdapter(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
}
