package net.prometheus.profile;

import java.util.UUID;

public class PlayerProfile {
	
	private UUID uniqueID;
	private String name;
	private int nameScore;
	private long updatedAt;
	
	public PlayerProfile(UUID uniqueID, String name, long updatedAt) {
		this.uniqueID = uniqueID;
		this.name = name;
		this.nameScore = PlayerProfileName.score(name);
		this.updatedAt = updatedAt;
	}
	
	@Override
	public String toString() {
		return String.format("PlayerProfile{uniqueID=%s, name=%s, nameScore=%s, updatedAt=%s}", uniqueID, name, nameScore, updatedAt);
	}
	
	public UUID getUniqueID() {
		return uniqueID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNameScore() {
		return nameScore;
	}
	
	public long getUpdatedAt() {
		return updatedAt;
	}
	
}
