package com.mareknowakowski.meds.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Scan {

    @Id
    @GeneratedValue
    private Long id;
    private final String pathToPhoto;
    private String section;



}
