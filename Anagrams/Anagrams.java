// Adolfo Pineda
// February 26, 2015
// T.A. Ashley Donaldson: Section AH
//
// This program can be used to create anagrams. An anagram
// is a word or phrase that can be made by rearranging the
// letters of another word or phrase (for instance, meat is
// an anagram for team), and so it uses a sorted dictionary
// of words to create those anagrams. 

import java.util.*;

public class Anagrams {
   private Set<String> words;
   
   // Passes in a sorted list of words that will be used to create anagrams.
   // Throws an IllegalArgumentException if the passed-in list is null.
   public Anagrams(Set<String> dictionary) {
      if (dictionary == null) {
         throw new IllegalArgumentException();
      }
      this.words = new TreeSet<String>(dictionary);
   }
   
   // Passes in a phrase and returns a list of words, in
   // alphabetical order, that can be created using the
   // letters of the passed-in phrase. It will throw an
   // IllegalArgumentException if the passed-in phrase is null. 
   public Set<String> getWords(String phrase) {
      phraseCheck(phrase, 0);
      Set<String> availableWords = new TreeSet<String>();
      LetterInventory phraseInventory = new LetterInventory(phrase);
      for (String s : words) {
         LetterInventory tempInventory = new LetterInventory(s);
         LetterInventory subInventory = phraseInventory.subtract(tempInventory);
         // The first case checks if it's null, but the second case checks
         // if subtracting the current s word from the phrase would be the
         // same as subtracting the sizes of their inventory sizes
         if (subInventory != null && subInventory.size() ==
             phraseInventory.size() - tempInventory.size()) {
            availableWords.add(s);
         }
      }
      return availableWords;
   }
   
   // Passes in a phrase and prints all of the possible anagrams that can be created
   // from the passed-in phrase. The anagrams use all of the letters of the phrase
   // to either create one word or multiple words. The anagrams will be printed as 
   // comma-separated values (ex: if "marty stepp" is passed-in, then, depending
   // on the dictionary of words that was initially passed-in, it will be displayed as
   // [map, step, try]
   // [map, try, step]
   // .... and so on).
   // It will also throw an IllegalArgumentException if the phrase is null.
   public void print(String phrase) {
      print(phrase, 0);
   }
   
   // Passes in a phrase and a max number of words that limit the amount of words
   // that can be included in the list of anagrams. It uses these parameters to 
   // print all of the possible anagrams (ex: using the previous example, the phrase
   // "marty stepp" and a max of 2 will display:
   // [empty, parts]
   // [empty, traps]
   // ... and so on). It will throw an IllegalArgumentException if the phrase is null
   // or the max is less than 0. Also, a max of 0 will print all of the possible anagrams. 
   public void print(String phrase, int max) {
      phraseCheck(phrase, max);
      Set<String> availableWords = getWords(phrase);
      LetterInventory phrLetters = new LetterInventory(phrase);
      printHelper(max, phrLetters, new ArrayList<String>(), availableWords);
   }
   
   // Private helper method that, depending on the max that is passed-in, either prints
   // all of the possible anagrams (if max is 0) or a limited number of anagrams.
   private void printHelper(int max, LetterInventory phrLetters,
                            ArrayList<String> list, Set<String> availableWords) {
      if (phrLetters.size() == 0) {
         System.out.println(list);
      } else if (list.size() < max || max == 0) {
         for (String s : availableWords) {
            LetterInventory letters = new LetterInventory(s);
            LetterInventory subLetters = phrLetters.subtract(letters);
            if (subLetters != null && phrLetters.size() >= letters.size()) {
               list.add(s);
               printHelper(max, subLetters, list, availableWords);
               list.remove(list.size() - 1);
            }
         }
      }
   }
   
   // Private helper method that passes in a phrase and a max
   // and checks to see if the phrase is null or if the max
   // is less than 0. If either of these cases is true, then
   // it will throw an IllegalArgumentException.
   private void phraseCheck(String phrase, int max) {
      if (phrase == null || max < 0) {
         throw new IllegalArgumentException();
      }
   }
   
}