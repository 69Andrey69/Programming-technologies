package org.example;
import java.util.*;
class Dictionary {    private Map<String, Set<String>> synonyms;
    public Dictionary() {
        synonyms = new LinkedHashMap<>();    }

    public void add(String term, String synonym) {        if (synonyms.containsKey(term)) {
        Set<String> existingSynonyms = synonyms.get(term);
        if (!existingSynonyms.contains(synonym)) {
            existingSynonyms.add(synonym);
        } else {                System.out.println("\nСиноним \"" + synonym + "\" уже существует.");
        }        } else {
        Set<String> newSynonyms = new LinkedHashSet<>();            newSynonyms.add(synonym);
        synonyms.put(term, newSynonyms);        }
    }
    public Set<String> get(String term) {
        return synonyms.getOrDefault(term, Collections.emptySet());    }
    public void printAll() {
        for (Map.Entry<String, Set<String>> entry : synonyms.entrySet()) {            String term = entry.getKey();
            Set<String> synonyms = entry.getValue();            System.out.println(term + ": " + synonyms);
        }    }
}