package com.example.bills.service;

import com.example.bills.model.Flatmate;
import com.example.bills.repository.FlatmateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlatmateService {
    @Autowired
    FlatmateRepository flatmateRepository;

    public List<Flatmate> getAllFlatmates() {
        return flatmateRepository.findAll();
    }
}
