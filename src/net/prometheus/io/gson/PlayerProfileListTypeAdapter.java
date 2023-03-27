package net.prometheus.io.gson;

import java.io.IOException;
import java.util.UUID;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import net.prometheus.profile.PlayerProfile;
import net.prometheus.profile.PlayerProfileList;
import net.prometheus.util.gson.GsonTypeAdapter;

public class PlayerProfileListTypeAdapter extends GsonTypeAdapter<PlayerProfileList> {
	
	public PlayerProfileListTypeAdapter() {
		super(PlayerProfileList.class);
	}
	
	@Override
	public PlayerProfileList read(JsonReader reader) throws IOException {
		PlayerProfileList profiles = new PlayerProfileList();
		
		reader.beginArray();
		
		while(true) {
			
			JsonToken token = reader.peek();
			
			if(token != JsonToken.BEGIN_OBJECT) break;
			
			reader.beginObject();
			reader.nextName();
			String s = reader.nextString();
			reader.nextName();
			String name = reader.nextString();
			
			long updatedAt = System.currentTimeMillis();
			
			token = reader.peek();
			
			if(token == JsonToken.NAME) {
				
				reader.nextName();
				token = reader.peek();
				
				if(token == JsonToken.NUMBER) updatedAt = reader.nextLong();
				else reader.skipValue();
			}
			
			reader.endObject();
			
			UUID profileID = UUID.fromString(s);
			PlayerProfile profile = new PlayerProfile(profileID, name, updatedAt);
			
			profiles.add(profile);
		}
		
		reader.endArray();
		
		return profiles;
	}
	
	@Override
	public void write(JsonWriter writer, PlayerProfileList profiles) throws IOException {
		writer.beginArray();
		
		for(PlayerProfile profile : profiles.getProfiles()) {
			
			UUID profileID = profile.getUniqueID();
			String name = profile.getName();
			long updatedAt = profile.getUpdatedAt();
			
			String s = profileID.toString();
			
			writer.beginObject();
			writer.name("uniqueID");
			writer.value(s);
			writer.name("name");
			writer.value(name);
			writer.name("updatedAt");
			writer.value(updatedAt);
			writer.endObject();
		}
		
		writer.endArray();
	}
	
}
