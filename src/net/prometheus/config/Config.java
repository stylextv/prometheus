package net.prometheus.config;

public class Config {
	
	private String relationshipAPI;
	private String profilesFile;
	private String unexploredProfilesFile;
	
	private int threadAmount;
	private long profileFetchDelay;
	private long profileSavePeriod;
	
	public Config(String relationshipAPI, String profilesFile, String unexploredProfilesFile, int threadAmount, long profileFetchPeriod, long profileSavePeriod) {
		this.relationshipAPI = relationshipAPI;
		this.profilesFile = profilesFile;
		this.unexploredProfilesFile = unexploredProfilesFile;
		this.threadAmount = threadAmount;
		this.profileFetchDelay = profileFetchPeriod;
		this.profileSavePeriod = profileSavePeriod;
	}
	
	public String getRelationshipAPI() {
		return relationshipAPI;
	}
	
	public String getProfilesFile() {
		return profilesFile;
	}
	
	public String getUnexploredProfilesFile() {
		return unexploredProfilesFile;
	}
	
	public int getThreadAmount() {
		return threadAmount;
	}
	
	public long getProfileFetchDelay() {
		return profileFetchDelay;
	}
	
	public long getProfileSavePeriod() {
		return profileSavePeriod;
	}
	
}
