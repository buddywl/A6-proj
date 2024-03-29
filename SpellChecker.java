import java.io.FileNotFoundException;
import java.util.*;

/**
 * SpellChecker class to check the spelling of the words
 */
public class SpellChecker {
    private static SpellingDictionary dictionary;

    /**
     * Main method to run the spell checker
     * @param args the words to check
     * @throws FileNotFoundException if file is not found then throw exception
     */
    public static void main(String[] args) throws FileNotFoundException {
        loadDictionary();
        if (args.length == 0) {
            try{
                Scanner scanner = new Scanner(System.in);
                Set<String> encounteredMisspellings = new HashSet<>();
                while (scanner.hasNext()) {
                    String word = scanner.next();
                    if (!dictionary.containsWord(word) && !encounteredMisspellings.contains(word)) {
                        System.out.println("Not found: " + word);
                        System.out.println("  Suggestions: " + suggestCorrections(word));
                        encounteredMisspellings.add(word);
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.err.println("Cannot locate file.");
                System.exit(-1);
            }
        } else {
            for (String word : args) {
                if (!dictionary.containsWord(word)) {
                    System.out.println("Not found: " + word);
                    System.out.println("  Suggestions: " + suggestCorrections(word));
                } else {
                    System.out.println("'" + word + "' is spelled correctly.");
                }
            }
        }

    }



    /**
     * Load the dictionary from the file
     * @throws FileNotFoundException if file is not found then throw exception
     */
    private static void loadDictionary() throws FileNotFoundException {
        dictionary = new SpellingDictionary("words.txt"); // Provide the correct filename
    }


    /**
     * Suggest corrections for a misspelled word
     * @param word the word to suggest corrections for
     * @return a list of suggested corrections
     */
    private static ArrayList<String> suggestCorrections(String word) {
        return dictionary.nearMisses(word);
    }
}
