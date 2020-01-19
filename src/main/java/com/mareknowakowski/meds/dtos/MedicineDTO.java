package com.mareknowakowski.meds.dtos;

import com.mareknowakowski.meds.domain.Medicine;
import com.mareknowakowski.meds.domain.Reminder;
import com.mareknowakowski.meds.exceptions.DtoMappingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@AllArgsConstructor
@Getter
@Setter
public class MedicineDTO {

    private Long id;
    private String name;
    private Integer count;
    private String description;
    private List<ReminderDTO> reminders;
    private String user;

    public Medicine convertSelftoMedicine() {
        List<Reminder> reminders = new ArrayList<>();
        for (ReminderDTO reminderDTO : this.reminders) {
           // reminders.add(reminderDTO.convertSelftoReminder());
            reminderDTO.getDays().forEach((day)->{
                reminders.add(new Reminder(reminderDTO.getHour(),day));
            });
            //reminders.add(new Reminder())
        }
        return new Medicine(this.name, this.count, this.description, reminders);
    }

    public static MedicineDTO convertMedicineToMedicineDTO(Medicine medicine) {
        List<ReminderDTO> reminders = new ArrayList<>();
        List<String> days = new ArrayList<>();
        Set<String> hours = new TreeSet<>();
        Map<String,List<String>> map = new HashMap<>();
        for (Reminder reminder : medicine.getReminders()) {
            hours.add(reminder.getHour());
        }
        for(String hour: hours) {
            for(Reminder reminder: medicine.getReminders()) {
                if (!map.containsKey(hour)) {
                    map.put(hour, new ArrayList<>());
                }
                map.get(hour).add(reminder.getDay());
            }
        }

        for(String key: map.keySet()) {
            reminders.add(new ReminderDTO(key, map.get(key)));
        }
            //            List<String> remindersAsString= new ArrayList<>();
//            remindersAsString.add(reminder.getDay());

//            reminders.add(reminder.getDay(), reminder.getHour());
           // reminders.add(ReminderDTO.convertReminderToReminderDTO(reminder));
//            ReminderDTO reminderDTO = new ReminderDTO();
//            reminderDTO.setDays(remindersAsString);
//            reminderDTO.setHour(reminder.getHour());
//            reminders.add(reminderDTO);


        return new MedicineDTO(medicine.getId(), medicine.getName(), medicine.getCount(), medicine.getDescription(), reminders, medicine.getUser().getDeviceId());
    }

}
