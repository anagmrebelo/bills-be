package com.example.bills.controller;

import com.example.bills.dto.FlatmateDto;
import com.example.bills.dto.FlatmateNameDto;
import com.example.bills.model.Flatmate;
import com.example.bills.service.FlatmateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlatmateController {
    @Autowired
    FlatmateService flatmateService;

    @GetMapping("/flatmates")
    @ResponseStatus(HttpStatus.OK)
    List<Flatmate> getAllFlatmates() {
        return flatmateService.getAllFlatmates();
    }

    @GetMapping("/flatmates/{id}")
    @ResponseStatus(HttpStatus.OK)
    Flatmate getFlatmate(@PathVariable(name = "id") int id) {
        return flatmateService.getFlatmate(id);
    }

    @PostMapping("/flatmates")
    @ResponseStatus(HttpStatus.CREATED)
    Flatmate addFlatmate(@RequestBody @Valid FlatmateDto flatmateDto) {
        return flatmateService.addFlatmate(flatmateDto);
    }

    @PatchMapping("/flatmates/{id}")
    @ResponseStatus(HttpStatus.OK)
    Flatmate patchFlatmate(@PathVariable(name = "id") int id, @PathVariable @Valid FlatmateNameDto flatmateNameDto) {
        return flatmateService.patchFlatmate(id, flatmateNameDto);
    }
}
