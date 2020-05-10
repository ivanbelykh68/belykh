package com.belykh.service.words;

import java.util.List;
import java.util.Map;

public interface WordsService {

	/**
	 * Groups the words by first char and pack into Map of Lists with the first char as a key.
	 * Keys are sorted in alphabetic order.
	 * Groups contain at least minSize elements.
	 * Groups ordering principal: by length desc, alphabetic asc if length is the same
	 * @param input Sentence with words separated by space
	 * @return The map of lists of words grouped by first char. Example: {'a' -> ['alpha', 'ant'], 'b' -> ['bible', 'bob', 'by']}
	 */
	Map<Character, List<String>> processWords(String input, int minSize);
}
