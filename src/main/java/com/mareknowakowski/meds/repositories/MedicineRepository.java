package com.mareknowakowski.meds.repositories;

import com.mareknowakowski.meds.domain.Medicine;
import com.mareknowakowski.meds.domain.Reminder;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Long> {
    Optional<List<Medicine>> findAllByCount(Integer count);
    Medicine findByName(String name);

    List<Medicine> findAllByCountLessThanAndOutOfReminderAlreadySent(Integer count, Boolean reminderAlreadySent);

}
