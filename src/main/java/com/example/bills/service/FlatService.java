package com.example.bills.service;

import com.example.bills.model.Flat;
import com.example.bills.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FlatService {
    @Autowired
    FlatRepository flatRepository;

    public List<Flat> getAllFlats() {
        return flatRepository.findAll();
    }

    public Flat getFlat(int id) {
        return flatRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flat not found"));
    }

    public Flat addFlat(Flat flat) {
        return flatRepository.save(flat);
    }
}
