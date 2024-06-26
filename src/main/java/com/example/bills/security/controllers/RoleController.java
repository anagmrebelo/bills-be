package com.example.bills.security.controllers;

import com.example.bills.security.dtos.RoleToUserDto;
import com.example.bills.security.models.Role;
import com.example.bills.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    UserService userService;

    @PostMapping("/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public Role addRole(@RequestBody Role role) {
        return userService.saveRole(role);
    }

    @PostMapping("/roles/addToUser")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRoleToUser(@RequestBody RoleToUserDto roleToUserDTO) {
        userService.addRoleToUser(roleToUserDTO.getUsername(), roleToUserDTO.getRoleName());
    }
}
