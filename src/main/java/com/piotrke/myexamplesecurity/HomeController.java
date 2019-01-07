package com.piotrke.myexamplesecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String showHomePage() {
        return "Siemanko, tutaj mogą być tylko zalogowaniu użytkownicy.";
    }
}
