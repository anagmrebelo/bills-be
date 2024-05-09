package com.example.bills.services;

import com.example.bills.dtos.FlatmateNameDto;
import com.example.bills.models.Flat;
import com.example.bills.models.Flatmate;
import com.example.bills.repositories.FlatmateRepository;
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

    public Flatmate getFlatmate(Integer id) {
        return flatmateRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flatmate not found"));
    }

    public Flatmate addFlatmate(Flatmate flatmate) {
        if (flatmate.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add flatmate id, it is auto generated");
        }

        Flat flat = flatService.getFlat(flatmate.getFlat().getId());
        flatmate.setFlat(flat);

        validateFlatmateCreation(flat);

        return flatmateRepository.save(flatmate);
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

    public List<Flatmate> getFlatmatesByFlatId(int id) {
        return flatmateRepository.findAllByFlatId(id);
    }
}
