package com.example.bills.services;

import com.example.bills.dtos.FlatDto;
import com.example.bills.models.Flat;
import com.example.bills.models.Flatmate;
import com.example.bills.repositories.FlatRepository;
import com.example.bills.security.models.User;
import com.example.bills.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FlatService {
    @Autowired
    FlatRepository flatRepository;
    @Autowired
    UserService userService;

    public List<Flat> getAllFlats(User user) {
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            return getAllFlats();
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can access all flats information");
    }

    public List<Flat> getAllFlats() {
        return flatRepository.findAll();
    }

    public Flat getFlat(int id, User user) {
        Flat flat = getFlat(id);

        if (user.getFlat() == null || user.getFlat().getId() != id) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only access to your flat information");
        }

        return flat;
    }

    public Flat getFlat(int id) {
        return flatRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flat not found"));
    }

    public Flat addFlat(FlatDto flatDto, User user) {
        if (user.getFlat() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has a flat");
        }

        Flat flat = new Flat(flatDto);
        Flat createdFlat = flatRepository.save(flat);
        user.setFlat(flat);
        userService.saveUser(user);

        return createdFlat;
    }
}
