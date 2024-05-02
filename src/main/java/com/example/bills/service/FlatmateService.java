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
    FlatService flatService;

    public List<Flatmate> getAllFlatmates() {
        return flatmateRepository.findAll();
    }

    public Flatmate getFlatmate(int id) {
        return flatmateRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flatmate not found"));
    }

    public Flatmate addFlatmate(FlatmateDto flatmateDto) {
        Flat flat = flatmateDto.getFlat();
        flatService.getFlat(flat.getId());

        validateFlatmateCreation(flat);

        Flatmate flatmate = new Flatmate(flatmateDto);
        Flatmate dbFlatmate = flatmateRepository.save(flatmate);

        flatService.addFlatmate(flat, dbFlatmate);

        return dbFlatmate;
    }

    private void validateFlatmateCreation(Flat flat) {
        if (flat.isClosed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add flatmates after inserting bills");
        }
    }

    public Flatmate patchFlatmate(int id, FlatmateNameDto flatmateNameDto) {
        Flatmate flatmate = flatmateRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flatmate not found"));

        flatmate.setName(flatmateNameDto.getName());
        return flatmateRepository.save(flatmate);
    }
}
