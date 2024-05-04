package com.example.bills.services;

import com.example.bills.dtos.FlatDto;
import com.example.bills.models.Flat;
import com.example.bills.models.Flatmate;
import com.example.bills.repositories.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public Flat addFlat(FlatDto flatDto) {
        Flat flat = new Flat(flatDto);
        return flatRepository.save(flat);
    }

    public void addFlatmate(Flat flat, Flatmate flatmate) {
        flat.addFlatmate(flatmate);
        flatRepository.save(flat);
    }
}
