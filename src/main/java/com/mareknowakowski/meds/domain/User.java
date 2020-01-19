package com.mareknowakowski.meds.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor

@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String deviceId;
    @OneToMany(mappedBy = "user")
    private List<Medicine> medicineList;
    @OneToMany(mappedBy = "user")
    private List<Symptom> symptomList;

    public User(){

    }

    public User(String deviceId) {
        this.deviceId = deviceId;
    }
}
