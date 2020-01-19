package com.mareknowakowski.meds.dtos;

import com.mareknowakowski.meds.domain.Symptom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
@AllArgsConstructor
@Getter
@Setter
public class SymptomDTO {
    private Long id;
    private String name;
    private String description;
    private Double lasted;
    private String occured;
    private String user;

    public Symptom convertSelftoSymptom(){
        return new Symptom(this.name, this.description, this.lasted, this.occured);

    }
    public static SymptomDTO convertSymptomToSymptomDTO(Symptom symptom){
        return new SymptomDTO(symptom.getId(), symptom.getName(), symptom.getDescription(), symptom.getLasted(),symptom.getOccured(), symptom.getUser().getDeviceId());
    }

}
