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
import org.springframework.security.core.userdetails.UserDetails;
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
        User user = fetchUser();
        return flatService.getAllFlats(user);
    }

    @GetMapping("/flats/{id}")
    @ResponseStatus(HttpStatus.OK)
    Flat getFlat(@PathVariable(name = "id") int id) {
        User user = fetchUser();
        return flatService.getFlat(id, user);
    }

    @PostMapping("/flats")
    @ResponseStatus(HttpStatus.CREATED)
    Flat addFlat(@RequestBody @Valid FlatDto flatDto) {
        User user = fetchUser();
        return flatService.addFlat(flatDto, user);
    }

    User fetchUser() {
        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            user = userService.getUser(userDetails.getUsername());
        } else {
            user = userService.getUser((String) principal);
        }
        return user;
    }
}
