package com.example.bills.service;

import com.example.bills.dto.BillDto;
import com.example.bills.model.Flat;
import com.example.bills.model.bill.*;
import com.example.bills.repository.bill.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;
    @Autowired
    FlatService flatService;

    public Bill addBill(String billType, BillDto billDto) {
        Flat flat = billDto.getFlat();
        flatService.getFlat(flat.getId());

        return switch (billType) {
            case "water" -> addWaterBill(billDto);
            case "electricity" -> addElectricityBill(billDto);
            case "gas" -> addGasBill(billDto);
            case "telco" -> addTelcoBill(billDto);
            default ->
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bill Type not supported; Types available: water, electricity, gas, telco");
        };
    }

    public Bill addWaterBill(BillDto billDto) {
        WaterBill waterBill = new WaterBill(billDto);
        return billRepository.save(waterBill);
    }

    public Bill addElectricityBill(BillDto billDto) {
        ElectricityBill electricityBill = new ElectricityBill(billDto);
        return billRepository.save(electricityBill);
    }

    public Bill addGasBill(BillDto billDto) {
        GasBill gasBill = new GasBill(billDto);
        return billRepository.save(gasBill);
    }

    public Bill addTelcoBill(BillDto billDto) {
        TelcoBill telcoBill = new TelcoBill(billDto);
        return billRepository.save(telcoBill);
    }

    public void deleteBill(int id) {
        Optional<Bill> bill = billRepository.findById(id);
        if (bill.isPresent()) {
            billRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Id not valid");
        }
    }

    public List<Bill> getBillsByFlat(int flatId) {
        Flat flat = flatService.getFlat(flatId);

        return billRepository.findAllByFlat(flat);
    }
}
