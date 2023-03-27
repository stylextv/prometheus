package net.prometheus;

import java.util.UUID;

import com.google.gson.Gson;

import net.prometheus.config.Config;
import net.prometheus.io.gson.PlayerProfileListTypeAdapter;
import net.prometheus.profile.PlayerProfile;
import net.prometheus.profile.PlayerProfileList;
import net.prometheus.util.ConsoleUtil;
import net.prometheus.util.GsonUtil;
import net.prometheus.util.TextUtil;
import net.prometheus.util.ThreadUtil;

public class Prometheus {
	
	private static final String CONFIG_FILE = "config.json";
	private static final long MIN_PROFILE_SAVE_PERIOD = 1000;
	
	private static final String CONSOLE_LOG_FORMAT = "%-20s %-31s %-35s %-24s";
	private static final char NEW_LINE_SYMBOL = '\n';
	private static final char CARRIAGE_RETURN_SYMBOL = '\r';
	
	private static final Gson GSON = GsonUtil.buildGson(new PlayerProfileListTypeAdapter());
	
	private Config config;
	private PlayerProfileList profiles;
	private PlayerProfileList unexploredProfiles;
	
	public void onStart() {
		System.out.printf(CONSOLE_LOG_FORMAT + NEW_LINE_SYMBOL, "profiles", "unexplored_profiles", "latest_explored_profile", "thread_index");
		
		loadConfig();
		loadProfiles();
		
		int threadAmount = config.getThreadAmount();
		long profileFetchDelay = config.getProfileFetchDelay();
		long profileSavePeriod = config.getProfileSavePeriod();
		
		for(int i = 0; i < threadAmount; i++) {
			
			int threadIndex = i;
			
			ThreadUtil.execute(() -> exploreProfiles(threadIndex));
			ThreadUtil.sleep(profileFetchDelay / threadAmount);
		}
		
		ThreadUtil.execute(() -> {
			
			long lastSavedAt = System.currentTimeMillis();
			
			while(true) {
				
				long time = System.currentTimeMillis();
				long t = time - lastSavedAt;
				
				if(t > profileSavePeriod) {
					
					saveProfiles();
					lastSavedAt = time;
				}
				
				ThreadUtil.sleep(MIN_PROFILE_SAVE_PERIOD);
			}
		});
		
		ConsoleUtil.readLine();
		onStop();
	}
	
	public void onStop() {
		saveProfiles();
		System.exit(0);
	}
	
	private void exploreProfiles(int threadIndex) {
		long profileFetchDelay = config.getProfileFetchDelay();
		
		while(!unexploredProfiles.isEmpty()) {
			
			PlayerProfile profile = unexploredProfiles.removeBest();
			String name = profile.getName();
			
			exploreProfile(profile);
			ThreadUtil.sleep(profileFetchDelay);
			
			int size1 = profiles.size();
			int size2 = unexploredProfiles.size();
			
			System.out.printf(CONSOLE_LOG_FORMAT + CARRIAGE_RETURN_SYMBOL, size1, size2, name, threadIndex);
		}
	}
	
	private void exploreProfile(PlayerProfile profile) {
		UUID profileID = profile.getUniqueID();
		
		exploreProfile(profileID);
	}
	
	private void exploreProfile(UUID profileID) {
		PlayerProfileList friendProfiles = fetchFriendProfiles(profileID);
		
		if(friendProfiles == null) return;
		
		for(PlayerProfile friendProfile : friendProfiles.getProfiles()) {
			
			if(!profiles.contains(friendProfile)) {
				
				unexploredProfiles.add(friendProfile);
			}
			
			profiles.add(friendProfile);
		}
	}
	
	private PlayerProfileList fetchFriendProfiles(UUID profileID) {
		String url = config.getRelationshipAPI();
		String s = profileID.toString();
		
		url = url.formatted(s);
		String json = TextUtil.loadTextFromURL(url);
		
		return GsonUtil.fromJson(json, PlayerProfileList.class, GSON);
	}
	
	private void loadConfig() {
		String json = TextUtil.loadTextFromFile(CONFIG_FILE);
		
		config = GsonUtil.fromJson(json, Config.class, GSON);
	}
	
	private void loadProfiles() {
		String profilesFile = config.getProfilesFile();
		String unexploredProfilesFile = config.getUnexploredProfilesFile();
		
		String json1 = TextUtil.loadTextFromFile(profilesFile);
		String json2 = TextUtil.loadTextFromFile(unexploredProfilesFile);
		
		profiles = GsonUtil.fromJson(json1, PlayerProfileList.class, GSON);
		unexploredProfiles = GsonUtil.fromJson(json2, PlayerProfileList.class, GSON);
	}
	
	private void saveProfiles() {
		String profilesFile = config.getProfilesFile();
		String unexploredProfilesFile = config.getUnexploredProfilesFile();
		
		String json1 = GsonUtil.toJson(profiles, GSON);
		String json2 = GsonUtil.toJson(unexploredProfiles, GSON);
		
		TextUtil.saveTextToFile(json1, profilesFile);
		TextUtil.saveTextToFile(json2, unexploredProfilesFile);
	}
	
	public Config getConfig() {
		return config;
	}
	
	public PlayerProfileList getProfiles() {
		return profiles;
	}
	
	public PlayerProfileList getUnexploredProfiles() {
		return unexploredProfiles;
	}
	
}
