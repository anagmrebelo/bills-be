package com.example.bills.controller;

import com.example.bills.model.Flat;
import com.example.bills.service.FlatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlatController {
    @Autowired
    FlatService flatService;
    @GetMapping("/flats")
    @ResponseStatus(HttpStatus.OK)
    List<Flat> getAllFlats() {
        return flatService.getAllFlats();
    }

    @GetMapping("/flats/{id}")
    @ResponseStatus(HttpStatus.OK)
    Flat getFlat(@PathVariable(name = "id") int id) {
        return flatService.getFlat(id);
    }

    @PostMapping("/flats")
    @ResponseStatus(HttpStatus.CREATED)
    Flat addFlat(@RequestBody @Valid Flat flat) {
        return flatService.addFlat(flat);
    }
}
