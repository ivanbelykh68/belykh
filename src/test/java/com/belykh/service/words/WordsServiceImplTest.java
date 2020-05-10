package com.belykh.service.words;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordsServiceImplTest {

    @Test
    public void processWords(){
        WordsService service = new WordsServiceImpl();
        for(int wordsCount = 1; wordsCount < 100; wordsCount ++){ //go from 1 to 100 words
        	for(int minLength = 1; minLength < 10; minLength +=3){ //word length from 1 letter
		        for(int maxLength = minLength; maxLength < minLength + 10; maxLength +=3){ // word length up to minLength + 10 letters
					for(int minSize = 0; minSize < maxLength + 10; minSize +=3){ //group size from 0 to 10
						processWords(service, wordsCount, minLength, maxLength, minSize); //test
					}
		        }
	        }
        }
    }

    //processWords test with specific params
    private void processWords(WordsService service, int wordsCount, int wordMinLength, int wordMaxLength, int minSize){
    	//generating words
        List<String> words = new ArrayList<>();
        //filling up the initial words list
        for(int i = 0; i < wordsCount; i++){
            words.add(RandomStringUtils.randomAlphabetic(wordMinLength, wordMaxLength));
        }
        //input text with spaces in the beginning
        StringBuffer inputSB = new StringBuffer(RandomStringUtils.random((int) (3 * Math.random()), ' '));
        for(String word : words){
            inputSB.append(word);
            //few spaces between words
            inputSB.append(RandomStringUtils.random((int) (1 + 10 * Math.random()), ' '));
        }
        String input = inputSB.toString();
        System.out.println(String.format("Testing with %d words length [%d-%d] and min group size of %d. Input: %s", wordsCount, wordMinLength, wordMaxLength, minSize, input));
        //getting the result
        Map<Character, List<String>> result = service.processWords(input, minSize);
	    System.out.println(String.format("Result: %s", result.toString()));
	    //checking keys order
        List<Character> keys = new ArrayList<>(result.keySet());
        List<Character> keysSorted = new ArrayList<>(result.keySet());
        keysSorted.sort(Comparator.naturalOrder());
	    //keys order is fine
        assertEquals(keys, keysSorted);
        for(List<String> group : result.values()){
        	//group size is correct
            assertTrue(group.size() >= minSize);
            if(group.size() > 1){
                for(int i = 0; i < group.size() - 1; i++){
                	//checking words order
                    String word1 = group.get(i);
                    String word2 = group.get(i + 1);
                    //words order is correct
                    assertTrue(word1.length() > word2.length() || ((word1.length() == word2.length()) && word2.compareTo(word1) >= 0));
                    //we had this order in input
                    assertTrue(words.contains(word1));
                    //removing it from our initial order list
                    removeOneWord(words, word1);
                }
            }
            //last order in the list
	        String lastWordInGroup = group.get(group.size() - 1);
            //we had it as well
	        assertTrue(words.contains(lastWordInGroup));
	        //removing from our order list
	        removeOneWord(words, lastWordInGroup);
        }
        //all words from the result
        List<String> allWordsFromResult = result.values().stream().flatMap(g -> g.stream()).collect(Collectors.toList());
        //our initial words list does not contain any of them anymore, because we have just cleaned it
	    assertListDoesntContainAnyWord(words, allWordsFromResult);
	    //if there are still words remain in the initial list, they have limited groups by first char
	    assertListContainsLimitedWordsWithSameFirstLetter(words, minSize);
    }

	private void assertListDoesntContainAnyWord(List<String> words, List<String> wordsToCheck){
    	System.out.println(String.format("assertListDoesntContainAnyWord(%s, %s)", words.toString(), wordsToCheck.toString()));
		assertEquals(0L, words.stream().filter(w -> wordsToCheck.contains(w)).count());
	}

    private void assertListContainsLimitedWordsWithSameFirstLetter(List<String> words, int limit){
	    System.out.println(String.format("assertListContainsLimitedWordsWithSameFirstLetter(%s, %d)", words.toString(), limit));
    	for(String word : words){
    		String firstLetter = word.substring(0, 1);
    		int thisLetterWordsCount = (int) words.stream().filter(w -> w.startsWith(firstLetter)).count();
    		assertTrue(thisLetterWordsCount < limit);
	    }
    }

    private void removeOneWord(List<String> words, String word){
	    Iterator<String> it = words.iterator();
	    while(it.hasNext()) {
		    String nextWord = it.next();
		    if (nextWord.equals(word)) {
		    	//remove first found element and quit
			    it.remove();
			    return;
		    }
	    }
    }

}