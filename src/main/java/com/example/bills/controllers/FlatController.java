package com.example.bills.controllers;

import com.example.bills.dtos.FlatDto;
import com.example.bills.models.Flat;
import com.example.bills.security.models.User;
import com.example.bills.security.services.UserService;
import com.example.bills.services.FlatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlatController {
    @Autowired
    FlatService flatService;
    @Autowired
    UserService userService;
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
    Flat addFlat(@RequestBody @Valid FlatDto flatDto) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(username);
        return flatService.addFlat(flatDto, user);
    }
}
