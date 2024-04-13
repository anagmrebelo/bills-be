package com.example.bills.service;

import com.example.bills.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlatService {
    @Autowired
    FlatRepository flatRepository;
}
