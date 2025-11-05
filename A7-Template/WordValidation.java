package a5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The WordValidation class implements the SpellingOperations interface.
 * It loads words from a dictionary file and provides methods to check
 * if a word exists and to find near-miss words.
 */
public class WordValidation implements SpellingOperations {
    private HashSet<String> dictionary;

    /**
     * Creates a WordValidation object and loads the dictionary.
     *
     * @param filename the name of the dictionary file
     */
    public WordValidation(String filename) {
        dictionary = new HashSet<>();
        try {
            Scanner file = new Scanner(new File(filename));
            while (file.hasNext()) {
                dictionary.add(file.next().toLowerCase());
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.err.println("Dictionary file not found: " + filename);
        }
    }

    /**
     * Checks if a given word is in the dictionary.
     *
     * @param query the word to check
     * @return true if the word is in the dictionary
     */
    @Override
    public boolean containsWord(String query) {
        return dictionary.contains(query.toLowerCase());
    }

    /**
     * Returns all valid words that are one edit away from the query.
     *
     * @param query the word to check
     * @return a list of valid near-miss words
     */
    @Override
    public ArrayList<String> nearMisses(String query) {
        ArrayList<String> results = new ArrayList<>();
        query = query.toLowerCase();

        // Deletions
        for (int i = 0; i < query.length(); i++) {
            String s = query.substring(0, i) + query.substring(i + 1);
            if (dictionary.contains(s) && !results.contains(s)) {
                results.add(s);
            }
        }

        // Insertions
        for (int i = 0; i <= query.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String s = query.substring(0, i) + c + query.substring(i);
                if (dictionary.contains(s) && !results.contains(s)) {
                    results.add(s);
                }
            }
        }

        // Substitutions
        for (int i = 0; i < query.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String s = query.substring(0, i) + c + query.substring(i + 1);
                if (dictionary.contains(s) && !results.contains(s)) {
                    results.add(s);
                }
            }
        }

        // Transpositions
        for (int i = 0; i < query.length() - 1; i++) {
            String s = query.substring(0, i)
                    + query.charAt(i + 1)
                    + query.charAt(i)
                    + query.substring(i + 2);
            if (dictionary.contains(s) && !results.contains(s)) {
                results.add(s);
            }
        }

        // Splits
        for (int i = 1; i < query.length(); i++) {
            String left = query.substring(0, i);
            String right = query.substring(i);
            if (dictionary.contains(left) && dictionary.contains(right)) {
                String combined = left + " " + right;
                if (!results.contains(combined)) {
                    results.add(combined);
                }
            }
        }

        return results;
    }

    /**
     * Main method for testing WordValidation.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        WordValidation validator = new WordValidation("words.txt");

        System.out.println("Contains 'apple': " + validator.containsWord("apple"));
        System.out.println("Contains 'applz': " + validator.containsWord("applz"));

        System.out.println("\nNear misses for 'caat': " + validator.nearMisses("caat"));
        System.out.println("Near misses for 'cta': " + validator.nearMisses("cta"));
    }
}



