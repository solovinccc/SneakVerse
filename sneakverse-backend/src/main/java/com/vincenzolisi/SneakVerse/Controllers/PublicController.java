package com.vincenzolisi.SneakVerse.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {
    
    @GetMapping("/public/test")
    public String test() { return "OK"; }
}
