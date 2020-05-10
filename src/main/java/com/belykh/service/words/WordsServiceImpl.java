package com.belykh.service.words;

import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Setter
public class WordsServiceImpl implements WordsService{

	@Override
	public Map<Character, List<String>> processWords(String input, int minSize){
		if(input == null) return Collections.emptyMap();
		Map<Character, List<String>> resultFilteredAndSorted = Arrays.stream(input.split(" ")) //Splitting input string into swparated words
				.filter(s -> !s.isEmpty()) //Filtering empty words
				.sorted((s1, s2) -> {
					int lengthCmpRes = Comparator.comparingInt(String::length).compare(s2, s1); //Comparing by length desc
					return lengthCmpRes + (1 - lengthCmpRes * lengthCmpRes) * s1.compareTo(s2); //Adding alphabetical comparation asc
				})
				.collect(Collectors.groupingBy(s -> s.charAt(0))).values().stream() //Grouping by first char
				.filter(g -> g.size() >= minSize) //Filtering groups by their size
				.collect(Collectors.toMap(g -> g.get(0).charAt(0), g -> g, (g1, g2) -> g1, TreeMap::new)); //packing to TreeMap to have keys ordered
		return resultFilteredAndSorted;
	}

}
