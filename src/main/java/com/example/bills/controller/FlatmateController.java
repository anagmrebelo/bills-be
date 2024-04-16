package com.example.bills.controller;

import com.example.bills.service.FlatmateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlatmateController {
    @Autowired
    FlatmateService flatmateService;
}
