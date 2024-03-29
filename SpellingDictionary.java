import java.io.*;
import java.util.*;
//import org.junit.Test;
//import static org.junit.Assert.*;


public class SpellingDictionary implements SpellingOperations {
    HashSet<String> dictionary = new HashSet<String>();

    /*
    TODO:
     Implement the SpellingOperations interface
     Ignore punctuation and capitalization
     Implement the nearMisses method to return a list of all valid words that are one edit away from the query
     */

    /**
     * Constructor to create a spelling dictionary
     * @param fileName the name of the file to read the dictionary from
     * @throws FileNotFoundException if file is not found then throw exception
     */
    public SpellingDictionary(String fileName) throws FileNotFoundException {
        try {
            Scanner scan = new Scanner(new File(fileName));
            while (scan.hasNextLine()) {
                dictionary.add(scan.nextLine().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot locate file.");
            System.exit(-1);
            throw new FileNotFoundException();
        }
    }

    /**
     * Check if the word is in the dictionary
     * @param query the word to check
     * @return true if the word is in the dictionary, false otherwise
     */
    public boolean containsWord(String query) {
        query = removePunctuations(query).toLowerCase();
        return dictionary.contains(query);
    }

    /**
     * Suggest corrections for a misspelled word
     * @param query the word to suggest corrections for
     * @return a list of suggestions for the misspelled word
     */
    public ArrayList<String> nearMisses(String query) {
        ArrayList<String> nearMisses = new ArrayList<>();
        String word = query.toLowerCase();
        // Deletions
        for (int i = 0; i < word.length(); i++) {
            String deleted = word.substring(0, i) + word.substring(i + 1);
            if (dictionary.contains(deleted) && !nearMisses.contains(deleted) && !deleted.equals(word)) {
                nearMisses.add(deleted);
            }
        }
        // Insertions
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String inserted = word.substring(0, i) + c + word.substring(i);
                if (dictionary.contains(inserted) && !nearMisses.contains(inserted) && !inserted.equals(word)) {
                    nearMisses.add(inserted);
                }
            }
        }
        // Substitutions
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String substituted = word.substring(0, i) + c + word.substring(i + 1);
                if (dictionary.contains(substituted) && !nearMisses.contains(substituted) && !substituted.equals(word)) {
                    nearMisses.add(substituted);
                }
            }
        }
        // Transpositions
        for (int i = 0; i < word.length() - 1; i++) {
            String transposed = word.substring(0, i) + word.charAt(i + 1) + word.charAt(i) + word.substring(i + 2);
            if (dictionary.contains(transposed) && !nearMisses.contains(transposed) && !transposed.equals(word)) {
                nearMisses.add(transposed);
            }
        }
        // Splits
        for (int i = 1; i < word.length(); i++) {
            String firstPart = word.substring(0, i);
            String secondPart = word.substring(i);
            String split = firstPart + " " + secondPart;
            if (dictionary.contains(firstPart) && dictionary.contains(secondPart) && !nearMisses.contains(split) && !split.equals(word)) {
                nearMisses.add(firstPart + " " + secondPart);
            }
        }

        if (query.endsWith("s") && dictionary.contains(query.substring(0,query.length()-1) + "'s")){
            nearMisses.add(query.substring(0, query.length()-1) + "'s");
        }
        return nearMisses;
    }

    /**
     * Remove punctuations from a string
     * @param s the string to remove punctuations from
     * @return the string without punctuations
     */
    public String removePunctuations(String s) {
        String res = "";
        for (Character c : s.toCharArray()) {
            if(Character.isLetterOrDigit(c))
                res += c;
        }
        return res;
    }

    public static void main(String[] args) throws FileNotFoundException {
        SpellingDictionary sp = new SpellingDictionary("words.txt");

    }

}