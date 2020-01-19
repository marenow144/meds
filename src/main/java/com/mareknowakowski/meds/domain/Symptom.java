package com.mareknowakowski.meds.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Symptom {


    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private Double lasted;
    private String occured;

    @ManyToOne
    private User user;


    public Symptom(String name, String description, Double lasted,  String occured) {
        this.name = name;
        this.description = description;
        this.lasted = lasted;
        this.occured = occured;
    }

    public Symptom(Symptom symptom) { ;
        this.name = symptom.getName();
        this.description = symptom.getDescription();
        this.lasted = symptom.getLasted();
        this.occured = symptom.getOccured();
        this.user = symptom.getUser();
    }
}
