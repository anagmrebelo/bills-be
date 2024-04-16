package com.example.bills.service;

import com.example.bills.dto.FlatDto;
import com.example.bills.model.Flat;
import com.example.bills.model.Flatmate;
import com.example.bills.repository.FlatRepository;
import com.example.bills.repository.FlatmateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FlatServiceTest {
    @Autowired
    FlatRepository flatRepository;
    @Autowired
    FlatmateRepository flatmateRespository;
    @Autowired
    FlatService flatService;

    private Flat flatOne;
    private Flat flatTwo;

    @BeforeEach
    void setUp() {
        Flatmate flatmate = new Flatmate(1);
        flatmate = flatmateRespository.save(flatmate);

        flatOne = new Flat("Gran Via");
        flatTwo = new Flat("Sagrada");

        flatRepository.saveAll(List.of(flatOne, flatTwo));
    }

    @AfterEach
    void tearDown() {
        flatRepository.deleteAll();
        flatRepository.flush();
    }

    @Test
    void getAllFlats() {
        List<Flat> flatsResult = flatService.getAllFlats();
        assertEquals(2, flatsResult.size());
        assertTrue(flatsResult.contains(flatOne));
        assertTrue(flatsResult.contains(flatTwo));
    }

    @Test
    void getValidFlat() {
        Flat flatResult = flatService.getFlat(flatOne.getId());
        assertEquals(flatOne, flatResult);
    }

    @Test
    void getInValidFlat() {
        assertThrows(ResponseStatusException.class, () -> flatService.getFlat(100));
    }

    @Test
    void addFlat() {
        String name = "Loreto";
        Flat flatResult = flatService.addFlat(new FlatDto(name));

        Optional<Flat> createdFlat = flatRepository.findById(flatResult.getId());
        assertTrue(createdFlat.isPresent());
        assertEquals(name, createdFlat.get().getName());
    }
}