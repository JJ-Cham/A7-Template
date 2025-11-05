

import java.util.*;

public class SpellChecker {
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
            // File scanning mode
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
            scanner.close();
                }
            }
        }
    }
}
