//package a5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordValidation implements SpellingOperations {
    private HashSet<String> dictionary;

    public WordValidation(String filename) {
        dictionary = new HashSet<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase().replaceAll("[^a-z]", "");
                if (!word.isEmpty()) {
                    dictionary.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Dictionary file not found: " + filename);
        }
    }

    @Override
    public boolean containsWord(String query) {
        return dictionary.contains(query.toLowerCase());
    }

    @Override
    public ArrayList<String> nearMisses(String query) {
        query = query.toLowerCase();
        HashSet<String> suggestions = new HashSet<>();

        // Deletions
        for (int i = 0; i < query.length(); i++) {
            String candidate = query.substring(0, i) + query.substring(i + 1);
            if (dictionary.contains(candidate)) suggestions.add(candidate);
        }

        // Insertions
        for (int i = 0; i <= query.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String candidate = query.substring(0, i) + c + query.substring(i);
                if (dictionary.contains(candidate)) suggestions.add(candidate);
            }
        }

        // Substitutions
        for (int i = 0; i < query.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != query.charAt(i)) {
                    String candidate = query.substring(0, i) + c + query.substring(i + 1);
                    if (dictionary.contains(candidate)) suggestions.add(candidate);
                }
            }
        }

        // Transpositions
        for (int i = 0; i < query.length() - 1; i++) {
            char[] chars = query.toCharArray();
            char temp = chars[i];
            chars[i] = chars[i + 1];
            chars[i + 1] = temp;
            String candidate = new String(chars);
            if (dictionary.contains(candidate)) suggestions.add(candidate);
        }

        // Splits
        for (int i = 1; i < query.length(); i++) {
            String first = query.substring(0, i);
            String second = query.substring(i);
            if (dictionary.contains(first) && dictionary.contains(second)) {
                suggestions.add(first + " " + second);
            }
        }

        return new ArrayList<>(suggestions);
    }
 
    
    public static void main(String[] args) {
        WordValidation validator = new WordValidation("words.txt");

        System.out.println("Test containsWord: " + validator.containsWord("cattle")); // true

        System.out.println("Deletions: " + validator.nearMisses("catttle")); // should include "cattle"
        System.out.println("Insertions: " + validator.nearMisses("catle")); // should include "cattle"
        System.out.println("Substitutions: " + validator.nearMisses("caxtle")); // should include "cattle"
        System.out.println("Transpositions: " + validator.nearMisses("cattel")); // should include "cattle"
        System.out.println("Splits: " + validator.nearMisses("cattell")); // should include "cat tell"
    }
}




