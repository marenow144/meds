package com.mareknowakowski.meds.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Medicine  {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    @Range(min = 0L)
    private Integer count;
    private String description;
    @OneToMany(mappedBy = "medicine")
    List<Reminder> reminders;
    @ManyToOne
    private User user;

    private Boolean outOfReminderAlreadySent=false;

    public Medicine(String name, @Range(min = 0L) Integer count, String description, List<Reminder> reminders) {
        this.name = name;
        this.count = count;
        this.description = description;
        this.reminders = reminders;
    }


    public Medicine(Long id, String name, @Range(min = 0L) Integer count, String description, List<Reminder> reminders, User user) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.description = description;
        this.reminders = reminders;
        this.user = user;
    }


    public Medicine(Medicine medicine){
        this.id = medicine.getId();
        this.name = medicine.getName();
        this.count = medicine.getCount();
        this.description = medicine.getDescription();
        this.reminders = medicine.getReminders();
        this.user = medicine.getUser();
    }
}
