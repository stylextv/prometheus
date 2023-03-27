package net.prometheus.profile;

public class PlayerProfileName {
	
	private static final String CONSONANT_REGEX = "[bcdfghjklmnpqrstvwxyz]";
	private static final String VOWEL_REGEX = "[aeiou]";
	private static final String LEGAL_NAME_REGEX = "[a-zA-Z0-9\\_]{3,16}";
	
	private static final int REPEATED_CHARACTER_SCORE = 1;
	private static final int CONSONANT_CHARACTER_SCORE = -7;
	private static final int VOWEL_CHARACTER_SCORE = -5;
	private static final int DEFAULT_CHARACTER_SCORE = -10;
	private static final int ILLEGAL_NAME_SCORE = 1000;
	
	public static int score(String name) {
		int score = 0;
		
		if(!isLegal(name)) score =  ILLEGAL_NAME_SCORE;
		
		char character = ' ';
		
		for(char c : name.toCharArray()) {
			
			score += characterScore(c, character);
			character = c;
		}
		
		return score;
	}
	
	private static final int characterScore(char character, char previousCharacter) {
		int score = character == previousCharacter ? REPEATED_CHARACTER_SCORE : 0;
		
		if(isVowel(character)) {
			
			score += VOWEL_CHARACTER_SCORE;
			
		} else if(isConsonant(character)) {
			
			score += CONSONANT_CHARACTER_SCORE;
			
		} else {
			
			score += DEFAULT_CHARACTER_SCORE;
		}
		
		return score;
	}
	
	private static final boolean isVowel(char character) {
		String s = String.valueOf(character).toLowerCase();
		
		return s.matches(VOWEL_REGEX);
	}
	
	private static final boolean isConsonant(char character) {
		String s = String.valueOf(character).toLowerCase();
		
		return s.matches(CONSONANT_REGEX);
	}
	
	public static boolean isLegal(String name) {
		return name.matches(LEGAL_NAME_REGEX);
	}
	
}
