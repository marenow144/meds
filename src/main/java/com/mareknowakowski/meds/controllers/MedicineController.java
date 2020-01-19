package com.mareknowakowski.meds.controllers;


import com.mareknowakowski.meds.domain.Medicine;
import com.mareknowakowski.meds.dtos.MedicineDTO;
import com.mareknowakowski.meds.exceptions.DtoMappingException;
import com.mareknowakowski.meds.exceptions.SavingException;
import com.mareknowakowski.meds.services.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MedicineController {

    private final MedicineService medicineService;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/medicine", consumes = "application/json", produces = "application/json")
    public ResponseEntity createMedicine(@RequestBody MedicineDTO medicineDTO){
        Medicine medicine = medicineDTO.convertSelftoMedicine();
        Long createdId = medicineService.saveMedicine(medicine, medicineDTO.getUser());
        return new ResponseEntity<>(createdId, HttpStatus.OK);
    }
    @CrossOrigin(origins = "*")
    @GetMapping(path="/medicine/{id}", produces = "application/json")
    public ResponseEntity getMedicine(@PathVariable String id){
        try {
            MedicineDTO medicineDTO = MedicineDTO.convertMedicineToMedicineDTO(medicineService.getMedicineDetailsById(Long.parseLong(id)));
            return new ResponseEntity<>(medicineDTO, HttpStatus.OK);
        }catch (ChangeSetPersister.NotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>("Medicine with this id was not found", HttpStatus.NOT_FOUND);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/medicine/userId={deviceId}", produces = "application/json")
    public ResponseEntity getAllMedicines(@PathVariable String deviceId){
        List<Medicine> medicines = medicineService.findAllMedicines(deviceId);
        List<MedicineDTO> medicinesResponse = new ArrayList<>();
        medicines
                .forEach(medicine -> {
                    medicinesResponse.add(MedicineDTO.convertMedicineToMedicineDTO(medicine));
                });
        return new ResponseEntity<>(medicinesResponse, HttpStatus.OK);
    }
    @CrossOrigin(origins = "*")
    @PutMapping(path = "/medicine/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity updateMedicine(@RequestBody MedicineDTO medicineDTO, @PathVariable String id){
        Medicine medicine = medicineDTO.convertSelftoMedicine();
        try {
            medicine = medicineService.updateMedicine(medicine, Long.parseLong(id));
            return new ResponseEntity<>(MedicineDTO.convertMedicineToMedicineDTO(medicine), HttpStatus.OK);
        }catch (ChangeSetPersister.NotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>("Medicine with this id was not found", HttpStatus.NOT_FOUND);
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/medicine/{id}", produces="application/json")
    public ResponseEntity deleteMedicine(@PathVariable String id){
        try {
            medicineService.deleteMedicine(Long.parseLong(id));
            return new ResponseEntity<>(id, HttpStatus.OK);
        }catch (ChangeSetPersister.NotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>("Medicine with this id was not found", HttpStatus.NOT_FOUND);
        }
    }



}
