import java.io.*;
import java.util.*;

public class SpellChecker {

    public static void main(String[] args) {
        WordValidation validator = new WordValidation("words.txt");

        if (args.length > 0) {
            // Mode 1: words provided as command-line arguments
            checkWordsFromArgs(args, validator);
        } else {
            // Mode 2: words provided from a file via input redirection
            checkWordsFromFile(validator);
        }
    }

    private static void checkWordsFromArgs(String[] args, WordValidation validator) {
        for (String word : args) {
            if (validator.containsWord(word)) {
                System.out.println("'" + word + "' is spelled correctly.");
            } else {
                System.out.println("Not found:  " + word);
                System.out.print("  Suggestions:  ");
                ArrayList<String> suggestions = validator.nearMisses(word);
                for (String s : suggestions) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        }
    }

    private static void checkWordsFromFile(WordValidation validator) {
        Scanner input = new Scanner(System.in);
        HashSet<String> misspelled = new HashSet<>();

        while (input.hasNext()) {
            String word = input.next().replaceAll("[^a-zA-Z]", "");
            if (!word.isEmpty() && !validator.containsWord(word)) {
                if (!misspelled.contains(word)) {
                    misspelled.add(word);
                    System.out.println("Not found: " + word);
                    System.out.print("  Suggestions: ");
                    ArrayList<String> suggestions = validator.nearMisses(word);
                    for (String s : suggestions) {
                        System.out.print(s + " ");
                    }
                    System.out.println();
                }
            }
        }

        input.close();
    }
}
