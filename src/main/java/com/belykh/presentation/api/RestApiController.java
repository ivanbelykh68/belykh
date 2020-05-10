package com.belykh.presentation.api;

import com.belykh.service.words.WordsService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@Setter
public class RestApiController {

    @Autowired
    private WordsService wordsService;

    @GetMapping
    public String apiInfo() {
        return "belykh";
    }

    @PostMapping("/words")
    public Map<Character, List<String>> processWords(@RequestParam String input, @RequestParam(required = false, defaultValue = "2") Integer minSize) {
        return wordsService.processWords(input, minSize);
    }

}