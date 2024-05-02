package com.example.bills.service;

import com.example.bills.dto.BillDto;
import com.example.bills.model.Attendance;
import com.example.bills.model.Debt;
import com.example.bills.model.Flat;
import com.example.bills.model.Flatmate;
import com.example.bills.model.bill.*;
import com.example.bills.repository.bill.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;
    @Autowired
    FlatService flatService;
    @Autowired
    FlatmateService flatmateService;
    @Autowired
    DebtService debtService;
    @Autowired
    AttendanceService attendanceService;

    public Bill addBill(String billType, BillDto billDto) {
        Flat flat = billDto.getFlat();
        flatService.getFlat(flat.getId());

        validateFlatmatesAttendances(flat, billDto);

        Bill createdBill =  switch (billType) {
            case "water" -> addWaterBill(billDto);
            case "electricity" -> addElectricityBill(billDto);
            case "gas" -> addGasBill(billDto);
            case "telco" -> addTelcoBill(billDto);
            default ->
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bill Type not supported; Types available: water, electricity, gas, telco");
        };

        flat.closeFlat();

        addDebts(createdBill);

        return createdBill;
    }

    void validateFlatmatesAttendances(Flat flat, BillDto billDto) {
        if (flat.getFlatmateList() == null || flat.getFlatmateList().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You need to add flatmates before adding bills");
        }
        if (attendanceService.getAttendanceByFlatAndMonth(flat.getId(), billDto.getMonth().getValue()).size() != flat.getFlatmateList().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Add attendances for all flatmates before adding bills");
        }
    }

    private void addDebts(Bill bill) {
        List<Attendance> billAttendances = attendanceService.getAttendanceByFlatAndMonth(bill.getFlat().getId(), bill.getMonth().getValue());
        List<Attendance> positiveAttendances = billAttendances.stream().filter(Attendance::getWasPresent).toList();
        BigDecimal amountPerDebt = bill.getAmount().divide(BigDecimal.valueOf(billAttendances.size()), RoundingMode.HALF_UP);

        for (Attendance attendance : positiveAttendances) {
            Debt debt = new Debt(attendance.getFlatmate(), bill, amountPerDebt);
            debtService.addDebt(debt);
        }
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
            debtService.deleteByBill(bill.get());
            billRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Id not valid");
        }
    }

    public List<Bill> getBillsByFlat(int flatId) {
        Flat flat = flatService.getFlat(flatId);

        return billRepository.findAllByFlat(flat);
    }

    public Bill getBillById(int id) {
        return billRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found"));
    }
}
