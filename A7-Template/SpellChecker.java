package a5;

import java.util.*;

/**
 * The SpellChecker class uses WordValidation to check spelling.
 * It can check words given on the command line or read from standard input.
 */
public class SpellChecker {

    /**
     * Main method that runs the spell checker.
     *
     * @param args optional words to check from the command line
     */
    public static void main(String[] args) {
        WordValidation validator = new WordValidation("words.txt");

        if (args.length > 0) {
            // Command-line mode
            for (String raw : args) {
                String word = raw.toLowerCase().replaceAll("[^a-z]", "");
                if (validator.containsWord(word)) {
                    System.out.println("'" + word + "' is spelled correctly.");
                } else {
                    System.out.println("Not found: " + word);
                    ArrayList<String> suggestions = validator.nearMisses(word);
                    if (!suggestions.isEmpty()) {
                        System.out.println("  Suggestions: " + String.join(" ", suggestions));
                    } else {
                        System.out.println("  No suggestions found.");
                    }
                }
            }
        } else {
            // Input mode
            Scanner scanner = new Scanner(System.in);
            HashSet<String> checked = new HashSet<>();

            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase().replaceAll("[^a-z]", "");
                if (!validator.containsWord(word) && !checked.contains(word)) {
                    checked.add(word);
                    System.out.println("Not found: " + word);
                    ArrayList<String> suggestions = validator.nearMisses(word);
                    if (!suggestions.isEmpty()) {
                        System.out.println("  Suggestions: " + String.join(" ", suggestions));
                    } else {
                        System.out.println("  No suggestions found.");
                    }
                }
            }
            scanner.close();
        }
    }
}
