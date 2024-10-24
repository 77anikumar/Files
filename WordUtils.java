import java.util.Scanner;
import java.util.ArrayList;

/**
 *	Provides utilities for word games:
 *	Finds all words in the dictionary that match a list of letters, tabulates and prints an
 *  array of words, calculates the score of each word, and finds the highest scoring word.
 *	Uses the FileUtils and Prompt classes.
 *	
 *	@author Ani Kumar
 *	@since October 18, 2024
 */
 
public class WordUtils {
	private String[] words;     // the dictionary of words

	/* Constructor */
	public WordUtils() {
	}

	/** Main */
	public static void main (String [] args) {
		WordUtils wu = new WordUtils();
		wu.run();
	}
	/** Runner method */
	public void run() {
		String letters = Prompt.getString("Please enter a list of letters, from 3 to 12 letters long, without spaces");
		loadWords();
		String [] word = findAllWords(letters);
		System.out.println();
		printWords(word);
		// Score table in alphabetic order according to Scrabble
		int [] scoreTable = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
		String best = bestWord(word,scoreTable);
		System.out.println("\nHighest scoring word: " + best + "\nScore = "
				+ getScore(best, scoreTable) + "\n");
	}

	/**
	 * Load all of the dictionary from a file into words array.
	 */
	private void loadWords() {
		// File containing dictionary of almost 100,000 words.
		String WORD_FILE = "wordList.txt";
		Scanner inFile = FileUtils.openToRead(WORD_FILE);
		ArrayList<String> arrWords = new ArrayList<>();
		while (inFile.hasNext())
			arrWords.add(inFile.next());
		words = new String[arrWords.size()];
		for (int i = 0; i < arrWords.size(); i++)
			words[i] = arrWords.get(i);
	}

	/**
	 * Find all words that can be formed by a list of letters.
	 *
	 * @param letters string containing list of letters
	 * @return array of strings with all words found.
	 */
	public String[] findAllWords(String letters) {
		ArrayList<String> foundWords = new ArrayList<>();
		for (String word : words)
			if (isWordMatch(word, letters))
				foundWords.add(word);
		String[] arrStr = new String[foundWords.size()];
		for (int i = 0; i < foundWords.size(); i++)
			arrStr[i] = foundWords.get(i);
		return arrStr;
	}

	/**
	 * Decides if a word matches a group of letters.
	 *
	 * @param word    The word to test
	 * @param letters A string of letters to compare
	 * @return true if the word matches the letters, false otherwise
	 */
	public boolean isWordMatch(String word, String letters) {
		for (int a = 0; a < word.length(); a++) {
			char c = word.charAt(a);
			if (letters.indexOf(c) > -1)
				letters = letters.substring(0, letters.indexOf(c)) + letters.substring(letters.indexOf(c) + 1);
			else return false;
		}
		return true;
	}

	/**
	 * Print the words found to the screen.
	 *
	 * @param wordList Array containing the words to be printed
	 */
	public void printWords(String[] wordList) {
		for (int i = 0; i < wordList.length; i++) {
			System.out.printf("%-14s", wordList[i]);
			if (i % 5 == 4)
				System.out.println();
		}
	}

	/**
	 * Finds the highest scoring word according to a score table.
	 *
	 * @param wordList   Array of words to check
	 * @param scoreTable Array of 26 integer scores in letter order
	 * @return The word with the highest score
	 */
	public String bestWord(String[] wordList, int[] scoreTable) {
		int maxScore = 0;
		String best = "";
		for (String s : wordList) {
			int score = getScore(s, scoreTable);
			if (score > maxScore) {
				maxScore = score;
				best = s;
			}
		}
		return best;
	}

	/**
	 * Calculates the score of one word according to a score table.
	 *
	 * @param word       The word to score
	 * @param scoreTable An array of 26 integer scores in letter order
	 * @return The integer score of the word
	 */
	public int getScore(String word, int[] scoreTable) {
		int score = 0;
		for (int i = 0; i < word.length(); i++) score += scoreTable[word.charAt(i) - 'a'];
		return score;
	}
}