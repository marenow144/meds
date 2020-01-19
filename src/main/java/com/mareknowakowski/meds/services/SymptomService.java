package com.mareknowakowski.meds.services;

import com.google.common.collect.Lists;
import com.mareknowakowski.meds.domain.Symptom;
import com.mareknowakowski.meds.domain.User;
import com.mareknowakowski.meds.repositories.SymptomRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SymptomService {
    private final SymptomRepository symptomRepository;
    private final UserService userService;
    public Long saveSymptom(Symptom symptom, String deviceId){
        User user = userService.createOrFindUser(deviceId);
        symptom.setUser(user);
        symptomRepository.save(symptom);
        return symptom.getId();
    }
    public Symptom getSymptomDetailsById(Long id) throws ChangeSetPersister.NotFoundException {
        return symptomRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public List<Symptom> findAllSymptoms(String deviceId){
        User user = userService.createOrFindUser(deviceId);
        return user.getSymptomList();
        //return Lists.newArrayList(symptomRepository.findAll());
    }
    public Symptom updateSymptom(Symptom symptom, Long id) throws ChangeSetPersister.NotFoundException {
        //symptomRepository.delete(symptomRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new));
        Symptom symptomToUpdate = symptomRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        symptomToUpdate.setDescription(symptom.getDescription());
        symptomToUpdate.setLasted(symptom.getLasted());
        symptomToUpdate.setName(symptom.getName());
        symptomToUpdate.setOccured(symptom.getOccured());
        symptomRepository.save(symptomToUpdate);
        return symptomToUpdate;
    }
    public void deleteSymptom(Long symptomId) throws ChangeSetPersister.NotFoundException {
        Symptom symptom = symptomRepository.findById(symptomId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        symptomRepository.delete(symptom);
    }

}
