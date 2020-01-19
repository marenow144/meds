package com.mareknowakowski.meds.services;

import com.google.common.collect.Lists;
import com.mareknowakowski.meds.domain.Medicine;
import com.mareknowakowski.meds.domain.Reminder;
import com.mareknowakowski.meds.domain.User;
import com.mareknowakowski.meds.exceptions.SavingException;
import com.mareknowakowski.meds.repositories.MedicineRepository;
import com.mareknowakowski.meds.repositories.ReminderRepository;
import com.mareknowakowski.meds.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final ReminderRepository reminderRepository;
    private final UserService userService;

    public Long saveMedicine(Medicine medicine, String userId) {
        User user = userService.createOrFindUser(userId);
        medicine.setUser(user);
        medicine.setOutOfReminderAlreadySent(false);
        medicineRepository.save(medicine);
        medicine.getReminders()
                .forEach((Reminder r)-> {
                    r.setMedicine(medicine);
                    reminderRepository.save(r);
                });

        return medicine.getId();
    }
    public Medicine getMedicineDetailsById(Long id) throws ChangeSetPersister.NotFoundException {
        return medicineRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public List<Medicine> findAllMedicines(String userId){
        User user = userService.createOrFindUser(userId);
        return user.getMedicineList();
    }
    public Medicine updateMedicine(Medicine medicine, Long id) throws ChangeSetPersister.NotFoundException {
        Medicine medicineToUpdate = medicineRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        medicineToUpdate.setName(medicine.getName());
        medicineToUpdate.setDescription(medicine.getDescription());
        medicineToUpdate.setCount(medicine.getCount());
        medicineToUpdate.setReminders(medicine.getReminders());
        medicineRepository.save(medicineToUpdate);
        return medicineToUpdate;
    }
    public void deleteMedicine(Long medicineId) throws ChangeSetPersister.NotFoundException {
        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        medicineRepository.delete(medicine);
    }
    public void lowerCountByOne(Long medicineId)throws ChangeSetPersister.NotFoundException {
        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        medicine.setCount(medicine.getCount()-1);
        medicineRepository.save(medicine);
    }
    public void setAlreadySentFlagToTrue(Long medicineId) throws ChangeSetPersister.NotFoundException {
        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        medicine.setOutOfReminderAlreadySent(true);
        medicineRepository.save(medicine);
    }
}
