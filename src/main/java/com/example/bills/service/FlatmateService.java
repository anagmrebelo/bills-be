package com.example.bills.service;

import com.example.bills.repository.FlatmateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlatmateService {
    @Autowired
    FlatmateRepository flatmateRepository;
}
