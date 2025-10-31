//import SpellingOperations;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class WordValidation implements SpellingOperations{
    private HashSet<String> dictionary;

    //constructor
    public WordValidation(String words) {
        Scanner input = new Scanner(words);
        dictionary = new HashSet<>();
        while (input.hasNext()) {
            String word = input.next().toLowerCase();
            dictionary.add(word);
        }
        input.close();
    }

    public boolean containsWord(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    //nearmisses
    public Set<String> nearMisses(String word){

        //deletions
        for (int i = 0; i < word.length(); i++) {
            String deletion = word.substring(0, i) + word.substring(i + 1);
            if (dictionary.contains(deletion)) {
                //add to nearmisses set
            }
        }
        //insertions
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String insertion = word.substring(0, i) + c + word.substring(i);
                if (dictionary.contains(insertion)) {
                    //add to nearmisses set
                }
            }
        }

        //substitutions
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (word.charAt(i) != c) {
                    String substitution = word.substring(0, i) + c + word.substring(i + 1);
                    if (dictionary.contains(substitution)) {
                        //add to nearmisses set
                    }
                }
            }
        }
        //transpositions

        //splits 

    }


    //main method 
    public static void main(String[] args) {
        //String words = "apple banana orange grape mango";
        WordValidation validator = new WordValidation("words.txt");

        System.out.println(validator.containsWord("banana")); // true
        System.out.println(validator.containsWord("pear"));   // false

        //testing nearmisses
        Set<String> misses = validator.nearMisses("banan");

    }
}