package com.mareknowakowski.meds.controllers;


import com.mareknowakowski.meds.domain.Symptom;
import com.mareknowakowski.meds.dtos.SymptomDTO;
import com.mareknowakowski.meds.services.SymptomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SymptomController {
    private final SymptomService symptomService;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/symptom", consumes = "application/json", produces = "application/json")
    public ResponseEntity createSymptom(@RequestBody SymptomDTO symptomDTO){
        Symptom symptom = symptomDTO.convertSelftoSymptom();
        Long createdId = symptomService.saveSymptom(symptom, symptomDTO.getUser());
        return new ResponseEntity<>(createdId, HttpStatus.OK);
    }
    @CrossOrigin(origins = "*")
    @GetMapping(path="/symptom/{id}", produces = "application/json")
    public ResponseEntity getSymptom(@PathVariable String id){
        try {
            SymptomDTO symptomDTO = SymptomDTO.convertSymptomToSymptomDTO(symptomService.getSymptomDetailsById(Long.parseLong(id)));
            return new ResponseEntity<>(symptomDTO, HttpStatus.OK);
        }catch (ChangeSetPersister.NotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>("Medicine with this id was not found", HttpStatus.NOT_FOUND);
        }
    }
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/symptom/userId={deviceId}", produces = "application/json")
    public ResponseEntity getAllSymptoms(@PathVariable String deviceId){
        List<Symptom> symptoms = symptomService.findAllSymptoms(deviceId);
        List<SymptomDTO> symptomResponse = new ArrayList<>();
        symptoms
                .forEach(symptom -> {
                    symptomResponse.add(SymptomDTO.convertSymptomToSymptomDTO(symptom));
                });
        return new ResponseEntity<>(symptomResponse, HttpStatus.OK);
    }
    @CrossOrigin(origins = "*")
    @PutMapping(path = "/symptom/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity updateSymptom(@RequestBody SymptomDTO symptomDTO, @PathVariable String id) {
        Symptom symptom = symptomDTO.convertSelftoSymptom();
        try {
            symptom = symptomService.updateSymptom(symptom, Long.parseLong(id));
            return new ResponseEntity<>(SymptomDTO.convertSymptomToSymptomDTO(symptom), HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Medicine with this id was not found", HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/symptom/{id}", produces="application/json")
    public ResponseEntity deleteSymptom(@PathVariable String id){
        try {
            symptomService.deleteSymptom(Long.parseLong(id));
            return new ResponseEntity<>(id, HttpStatus.OK);
        }catch (ChangeSetPersister.NotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>("Medicine with this id was not found", HttpStatus.NOT_FOUND);
        }
    }

}
