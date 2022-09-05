package com.example.demo.controllers;
import com.example.demo.model.Summoner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummonerController {

    @GetMapping("/summoner")
    public Summoner greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Summoner("Fuck", "You");
    }
}
