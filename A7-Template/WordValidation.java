//import SpellingOperations;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
//import java.util.Set;

public class WordValidation implements SpellingOperations{
    private HashSet<String> dictionary;

    //constructor
    public WordValidation(String filename) {
    dictionary = new HashSet<>();
    try {
        Scanner input = new Scanner(new File(filename), "UTF-8");
        while (input.hasNext()) {
            String word = input.next().toLowerCase().replaceAll("[^a-z]", "").trim();
            if (!word.isEmpty()) dictionary.add(word);
        }
        input.close();
    } catch (FileNotFoundException e) {
        System.out.println("Dictionary file not found!");
    }
}

    public boolean containsWord(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    //nearmisses
    public ArrayList<String> nearMisses(String word){
        //Set<String> result = new HashSet<>();
        word = word.toLowerCase();
        ArrayList<String> result = new ArrayList<>();
        HashSet<String> nearmisses = new HashSet<>();

        //deletions
        for (int i = 0; i < word.length(); i++) {
            String deletion = word.substring(0, i) + word.substring(i + 1);
            if (dictionary.contains(deletion) && nearmisses.add(deletion)) {
                //add to nearmisses set
                result.add(deletion);

            }
        }
        //insertions
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String insertion = word.substring(0, i) + c + word.substring(i);
                if (dictionary.contains(insertion) && nearmisses.add(insertion))
                    result.add(insertion);
            }
        }

        //Substitutions
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != word.charAt(i)) {
                    String substitution = word.substring(0, i) + c + word.substring(i + 1);
                    if (dictionary.contains(substitution) && nearmisses.add(substitution))
                        result.add(substitution);
                }
            }
        }

        //Transposition
        for (int i = 0; i < word.length() - 1; i++) {
            char[] chars = word.toCharArray();
            char temp = chars[i];
            chars[i] = chars[i + 1];
            chars[i + 1] = temp;
            String transposed = new String(chars);
            if (dictionary.contains(transposed) && nearmisses.add(transposed))
                result.add(transposed);
        }

        //Splits
        for (int i = 1; i < word.length(); i++) {
            String left = word.substring(0, i);
            String right = word.substring(i);
            if (dictionary.contains(left) && dictionary.contains(right)) {
                String combined = left + " " + right;
                if (nearmisses.add(combined)) result.add(combined);
            }
        }

        return result;

    }


    //main method 
    public static void main(String[] args) {
        
        WordValidation validator = new WordValidation("words.txt");

        System.out.println(validator.containsWord("A")); // true
        //System.out.println(validator.containsWord("peplsp"));   // false

        //testing nearmisses
        // 1. Deletion
        System.out.println("catttle " + validator.nearMisses("catttle"));
        // 2. Insertion
        System.out.println("catle " + validator.nearMisses("catle"));
        // 3. Substitution
        System.out.println("caxtle " + validator.nearMisses("caxtle"));
        // 4. Transposition
        System.out.println("cattel  " + validator.nearMisses("cattel"));
        // 5. Split
        System.out.println("cattell  " + validator.nearMisses("cattell"));

    }
}