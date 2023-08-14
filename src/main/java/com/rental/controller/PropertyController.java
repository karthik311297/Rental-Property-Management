package com.rental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/properties")
public class PropertyController
{
    @GetMapping("/unoccupied")
    public ResponseEntity<String> getAllUnoccupiedProperties()
    {
        return ResponseEntity.ok("");
    }
}
