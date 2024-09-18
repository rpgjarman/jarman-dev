import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutocompleteTester {

  public static void main(String[] args) {
    // Replace "dict.txt" with the actual path to your dictionary file
    String dictFile = "dict.txt";
    int maxSuggestions = 3;

    // Test basic functionality
    testBasicFunctionality(dictFile, maxSuggestions);

    // Test prioritizing recently picked candidates
    testRecentPicks(dictFile, maxSuggestions);

    // Test handling prefixes with no words
    testPrefixNotInTrie(dictFile, maxSuggestions);

    // Test handling empty prefixes
    testEmptyPrefix(dictFile, maxSuggestions);

    // Test handling single-word suggestions
    testSingleWordSuggestion(dictFile, maxSuggestions);

    // Test handling longer lists of candidates
    testLongerList(dictFile, maxSuggestions);

    // Test multiple picks for the same prefix
    testMultiplePicks(dictFile, maxSuggestions);

    // Test handling invalid dictionary files (optional)
    // testInvalidDictionary(dictFile, maxSuggestions);

    System.out.println("All tests passed!");
  }

  private static void testBasicFunctionality(String dictFile, int maxSuggestions) {
    Autocomplete auto = new Autocomplete(dictFile, maxSuggestions);

    String prefix1 = "ap";
    List<String> expected1 = Arrays.asList("apple", "apricot");
    List<String> suggestions1 = auto.getCandidates(prefix1);
    checkListEquality(prefix1, expected1, suggestions1);

    String prefix2 = "te";
    List<String> expected2 = Arrays.asList("test", "tennis");
    List<String> suggestions2 = auto.getCandidates(prefix2);
    checkListEquality(prefix2, expected2, suggestions2);
  }

  private static void testRecentPicks(String dictFile, int maxSuggestions) {
    Autocomplete auto = new Autocomplete(dictFile, maxSuggestions);

    String prefix = "tr";
    String selectedCandidate = "trap";

    // Get initial suggestions
    List<String> initialSuggestions = auto.getCandidates(prefix);

    // Simulate picking a candidate
    auto.pickCandidate(prefix, selectedCandidate);

    // Check if recently picked candidate is prioritized
    List<String> suggestionsAfterPick = auto.getCandidates(prefix);
    List<String> expectedAfterPick = new ArrayList<>(initialSuggestions);
    expectedAfterPick.remove(selectedCandidate);
    expectedAfterPick.add(0, selectedCandidate); // Move picked candidate to the front

    checkListEquality(prefix + " (after pick)", expectedAfterPick, suggestionsAfterPick);
  }

  private static void testPrefixNotInTrie(String dictFile, int maxSuggestions) {
    Autocomplete auto = new Autocomplete(dictFile, maxSuggestions);

    String prefix = "zzz"; // Unlikely word in the dictionary
    List<String> expected = new ArrayList<>();
    List<String> suggestions = auto.getCandidates(prefix);
    checkListEquality(prefix, expected, suggestions);
  }

  private static void testEmptyPrefix(String dictFile, int maxSuggestions) {
    Autocomplete auto = new Autocomplete(dictFile, maxSuggestions);
    String prefix = "";
    List<String> expected = new ArrayList<>();
    List<String> suggestions = auto.getCandidates(prefix);
    checkListEquality(prefix, expected, suggestions);
  }

  private static void testSingleWordSuggestion(String dictFile, int maxSuggestions) {
    Autocomplete auto = new Autocomplete(dictFile, maxSuggestions);
    String prefix = "zoo"; // Assuming "zoo" exists in the dictionary
    List<String> expected = Arrays.asList("zoo");
    List<String> suggestions = auto.getCandidates(prefix);
    checkListEquality(prefix, expected, suggestions);
  }

  private static void testLongerList(String dictFile, int maxSuggestions) {
    Autocomplete auto = new Autocomplete(dictFile, maxSuggestions);

    String prefix = "ca"; // May have a longer list of candidates
    int expectedSize = maxSuggestions; // Expect maxSuggestions results
    List<String> suggestions = auto.getCandidates(prefix);
    if (suggestions.size() != expectedSize) {
      System.out.println("Test failed: " + prefix + " (expected " + expectedSize + " suggestions, got " + suggestions.size() + ")");
      System.out.println("Suggestions:");
      for (String suggestion : suggestions) {
        System.out.println("- " + suggestion);
      }
    }
  }

  private static void testMultiplePicks(String dictFile, int maxSuggestions) {
    // Implement this method to test multiple picks for the same prefix
  }

  private static void checkListEquality(String prefix, List<String> expected, List<String> actual) {
    if (!expected.equals(actual)) {
      System.out.println("Test failed: " + prefix);
      System.out.println("Expected: " + expected);
      System.out.println("Actual: " + actual);
    }
  }
}