package net.prometheus.profile;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerProfileList {
	
	private static final int FIRST_PROFILE_INDEX = 0;
	
	private List<PlayerProfile> profiles = new CopyOnWriteArrayList<>();
	private Map<UUID, PlayerProfile> profilesByID = new ConcurrentHashMap<>();
	
	public PlayerProfile removeBest() {
		PlayerProfile profile = best();
		
		remove(profile);
		
		return profile;
	}
	
	public PlayerProfile best() {
		PlayerProfile profile = first();
		
		for(PlayerProfile p : profiles) {
			
			int score = profile.getNameScore();
			int s = p.getNameScore();
			
			if(s > score) profile = p;
		}
		
		return profile;
	}
	
	public void add(PlayerProfile profile) {
		remove(profile);
		
		UUID profileID = profile.getUniqueID();
		
		profilesByID.put(profileID, profile);
		profiles.add(profile);
	}
	
	public void remove(PlayerProfile profile) {
		UUID profileID = profile.getUniqueID();
		
		remove(profileID);
	}
	
	public PlayerProfile remove(UUID profileID) {
		PlayerProfile profile = get(profileID);
		
		profiles.remove(profile);
		profilesByID.remove(profileID);
		
		return profile;
	}
	
	public PlayerProfile first() {
		return profiles.get(FIRST_PROFILE_INDEX);
	}
	
	public PlayerProfile get(UUID profileID) {
		return profilesByID.get(profileID);
	}
	
	public boolean contains(PlayerProfile profile) {
		UUID profileID = profile.getUniqueID();
		
		return contains(profileID);
	}
	
	public boolean contains(UUID profileID) {
		return profilesByID.containsKey(profileID);
	}
	
	public int size() {
		return profiles.size();
	}
	
	public boolean isEmpty() {
		return profiles.isEmpty();
	}
	
	public List<PlayerProfile> getProfiles() {
		return profiles;
	}
	
	public Map<UUID, PlayerProfile> getProfilesByID() {
		return profilesByID;
	}
	
}
