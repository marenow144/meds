package com.mareknowakowski.meds.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Map;

@Entity
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Reminder {
    @Id
    @GeneratedValue
    private Long id;
    private String hour;
    private String day;
    @ManyToOne
    private Medicine medicine;

    private static final Map<Integer, String> DAYS_MAPPING = Map.of(
            0, "sunday",
            1, "monday",
            2, "tuesday",
            3, "wednesday",
            4, "thursday",
            5, "friday",
            6, "saturday"

    );

    public static String getDayMapping(Integer day){
        return DAYS_MAPPING.get(day);
    }

    public Reminder(String hour, String day) {
        this.hour = hour;
        this.day = day;
    }
}
