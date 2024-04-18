package com.example.bills.service;

import com.example.bills.dto.FlatmateDto;
import com.example.bills.dto.FlatmateNameDto;
import com.example.bills.model.Flat;
import com.example.bills.model.Flatmate;
import com.example.bills.repository.FlatRepository;
import com.example.bills.repository.FlatmateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FlatmateService {
    @Autowired
    FlatmateRepository flatmateRepository;
    @Autowired
    FlatRepository flatRepository;

    public List<Flatmate> getAllFlatmates() {
        return flatmateRepository.findAll();
    }

    public Flatmate getFlatmate(int id) {
        return flatmateRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flatmate not found"));
    }

    public Flatmate addFlatmate(FlatmateDto flatmateDto) {
        Flat flat = flatmateDto.getFlat();
        flatRepository.findById(flat.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flat not found"));

        Flatmate flatmate = new Flatmate(flatmateDto);
        Flatmate dbFlatmate = flatmateRepository.save(flatmate);

        flat.addFlatmate(dbFlatmate);
        flatRepository.save(flat);

        return dbFlatmate;
    }

    public Flatmate patchFlatmate(int id, FlatmateNameDto flatmateNameDto) {
        Flatmate flatmate = flatmateRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flatmate not found"));

        flatmate.setName(flatmateNameDto.getName());
        return flatmateRepository.save(flatmate);
    }
}
