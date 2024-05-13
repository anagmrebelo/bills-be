package com.example.bills.controllers;

import com.example.bills.dtos.FlatmateNameDto;
import com.example.bills.models.Flatmate;
import com.example.bills.security.models.User;
import com.example.bills.security.services.UserService;
import com.example.bills.services.FlatmateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlatmateController {
    @Autowired
    UserService userService;
    @Autowired
    FlatmateService flatmateService;

    @GetMapping("/flatmates")
    @ResponseStatus(HttpStatus.OK)
    List<Flatmate> getAllFlatmates() {
        User user = fetchUser();
        return flatmateService.getAllFlatmates(user);
    }

    @GetMapping("/flatmates/{id}")
    @ResponseStatus(HttpStatus.OK)
    Flatmate getFlatmate(@PathVariable(name = "id") int id) {
        User user = fetchUser();
        return flatmateService.getFlatmate(id, user);
    }

    @PostMapping("/flatmates")
    @ResponseStatus(HttpStatus.CREATED)
    Flatmate addFlatmate(@RequestBody @Valid Flatmate flatmate) {
        User user = fetchUser();
        return flatmateService.addFlatmate(flatmate, user);
    }

    @PatchMapping("/flatmates/{id}")
    @ResponseStatus(HttpStatus.OK)
    Flatmate patchFlatmate(@PathVariable(name = "id") int id, @RequestBody @Valid FlatmateNameDto flatmateNameDto) {
        User user = fetchUser();
        return flatmateService.patchFlatmate(id, flatmateNameDto, user);
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
