package com.mareknowakowski.meds.repositories;

import com.mareknowakowski.meds.domain.Reminder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReminderRepository extends CrudRepository<Reminder, Long> {

    public List<Reminder> findByDayAndHour(String day, String hour);
}
