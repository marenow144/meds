package com.mareknowakowski.meds.dtos;

import com.mareknowakowski.meds.domain.Reminder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReminderDTO {
    private String hour;
    private List<String> days;

//    public Reminder convertSelftoReminder(){
//        return new Reminder(this.hour, this.day);
//
//    }
//    public static ReminderDTO convertReminderToReminderDTO(Reminder reminder){
//        return new ReminderDTO(reminder.getHour(), reminder.getDay());
//    }
}
