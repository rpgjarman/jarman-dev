/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. Robert Jarman
*/
import java.util.*;

// Part 1: Create a class called Autocomplete
// Part 1(a): This class should extend the abstract class AbstractAutocomplete, which extends the class Trie.
// Part 1(b): The value type of the generic T can be a collection of strings (e.g. List<String>).
public class Autocomplete extends AbstractAutocomplete<List<String>>{
    private String dict_file; // Stores the dictionary file name
    private Map<String, List<String>> recentPicks; // Stores recent picks with prefix and count
    int max; // Maximum number of suggestions

    // Part 1(c): You must create a constructor that takes two parameters, dict_file and max, and calls its super constructor, which reads all words in the dictionary file (e.g., dict.txt).
    // Constructor
    public Autocomplete(String dict_file, int max){
        super(dict_file, max);
        this.dict_file = dict_file;
        this.max = max;
        recentPicks = new HashMap<>();
    }
    
    // Part 2: Override the getCandidates() method that takes a prefix and returns a list of candidate words matching the prefix.
    // Method to get candidates for a given prefix
    @Override
    public List<String> getCandidates(String prefix) {
        // Part 2(a): The maximum number of candidates in teh list must be the return value of the getMax() method
        max = getMax();

        // candidates list for the inputted prefix
        List<String> candidates = new ArrayList<>();
        TrieNode<List<String>> node = find(prefix);
        if (node != null){
            // traverse the trie to find candidates
            findCandidatesRecursive(node, prefix, candidates);
        } else {
            return null;
        }

        // Part 2(b): The most recently selected candidate should appear as the 1st item, the 2nd most recently selected candidate should appear as the 2nd candidate, and so on.
        List<String> selectedCandidates = recentPicks.get(prefix);
        if (selectedCandidates == null) {
            selectedCandidates = new ArrayList<>();
            recentPicks.put(prefix, selectedCandidates);
        }

        // Part 2(c): The rest of the candidate list should be filled with the shortest words matching the prefix
        ListIterator<String> iterator = candidates.listIterator();
        while(iterator.hasNext()){
            String word = iterator.next();
            if(selectedCandidates.contains(word)){
                iterator.remove();
            }
        }

        // Part 2(d): If there is more than one candidate with the same lengths, they should be sorted alphabetically.
        // Part 2(e): Make sure the same candidate does not appear more than once
        // Sort candidates in natural order
        Collections.sort(candidates);

        // Then sort by length
        Collections.sort(candidates, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });

        // Limit the number of candidates to maxSuggestions
        List<String> allCandidates = new ArrayList<>();
        allCandidates.addAll(selectedCandidates);
        allCandidates.addAll(candidates);

        allCandidates = allCandidates.subList(0, Math.min(max, allCandidates.size()));
        return allCandidates;
    }

    // Recursive method to find candidates
    private void findCandidatesRecursive(TrieNode<List<String>> node, String currentString, List<String> candidates) {
        if (node.isEndState()) {
            candidates.add(currentString);
        }
        for (Map.Entry<Character, TrieNode<List<String>>> entry : node.getChildrenMap().entrySet()) {
            findCandidatesRecursive(entry.getValue(), currentString + entry.getKey(), candidates);
        }
    }
    
    // Part 3: Override the pickCandidate() method that takes a prefix and a selected candidate, and saves the
    // information. This is the most recently selected candidate for that particular prefix. It must appear as the first
    // time in the candidate list when the same prefix is entered next time
    @Override
    public void pickCandidate(String prefix, String candidate) {
        List<String> selectedCandidates = recentPicks.get(prefix);
        if (selectedCandidates == null) {
            selectedCandidates = new ArrayList<>();
            recentPicks.put(prefix, selectedCandidates);
        }
        
        // If the candidate is valid, add it to the front of the selected candidates list
        if (getCandidates(prefix).contains(candidate)) {
            if (selectedCandidates.contains(candidate)) {
                selectedCandidates.remove(candidate); 
            }
            selectedCandidates.add(0, candidate); 
        }
        recentPicks.put(prefix, selectedCandidates);
    }
}