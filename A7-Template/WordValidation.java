//package a5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * WordValidation implements the SpellingOperations interface.
 * It loads a list of valid words into a HashSet and provides methods
 * to check if a word is valid and to generate near-miss suggestions.
 */
public class WordValidation implements SpellingOperations {

    private HashSet<String> dictionary;

    /**
     * Constructor: builds a dictionary HashSet from the given filename.
     * @param filename The name of the file containing valid words.
     */
    public WordValidation(String filename) {
        dictionary = new HashSet<>();
        try (Scanner file = new Scanner(new File(filename))) {
            while (file.hasNext()) {
                String word = file.next().toLowerCase().replaceAll("[^a-z]", "");
                if (!word.isEmpty()) {
                    dictionary.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Cannot open dictionary file: " + filename);
            System.exit(1);
        }
    }

    /**
     * Checks if a word is in the dictionary.
     */
    @Override
    public boolean containsWord(String query) {
        if (query == null) return false;
        return dictionary.contains(query.toLowerCase());
    }

    /**
     * Returns a list of all valid dictionary words that are one edit away
     * (deletion, insertion, substitution, transposition, or split).
     */
    @Override
    public ArrayList<String> nearMisses(String query) {
        HashSet<String> results = new HashSet<>();
        if (query == null || query.isEmpty()) return new ArrayList<>(results);

        query = query.toLowerCase();
        int n = query.length();

        // 1. Deletions
        for (int i = 0; i < n; i++) {
            String candidate = query.substring(0, i) + query.substring(i + 1);
            if (dictionary.contains(candidate)) results.add(candidate);
        }

        // 2. Insertions
        for (int i = 0; i <= n; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String candidate = query.substring(0, i) + c + query.substring(i);
                if (dictionary.contains(candidate)) results.add(candidate);
            }
        }

        // 3. Substitutions
        for (int i = 0; i < n; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (query.charAt(i) == c) continue;
                String candidate = query.substring(0, i) + c + query.substring(i + 1);
                if (dictionary.contains(candidate)) results.add(candidate);
            }
        }

        // 4. Transpositions
        for (int i = 0; i < n - 1; i++) {
            if (query.charAt(i) != query.charAt(i + 1)) {
                String candidate = query.substring(0, i)
                        + query.charAt(i + 1)
                        + query.charAt(i)
                        + query.substring(i + 2);
                if (dictionary.contains(candidate)) results.add(candidate);
            }
        }

        // 5. Splits (two valid words)
        for (int i = 1; i < n; i++) {
            String left = query.substring(0, i);
            String right = query.substring(i);
            if (dictionary.contains(left) && dictionary.contains(right)) {
                results.add(left + " " + right);
            }
        }

        return new ArrayList<>(results);
    }

 
    public static void main(String[] args) {
        WordValidation wv = new WordValidation("words.txt");

        String testWord = "caxtle"; // intentional typo for "cattle"
        System.out.println("Contains 'castle'? " + wv.containsWord("castle"));
        System.out.println("Suggestions for '" + testWord + "': " + wv.nearMisses(testWord));
    }
}





