package com.mareknowakowski.meds.dtos;

import com.mareknowakowski.meds.domain.Medicine;
import com.mareknowakowski.meds.domain.Symptom;
import com.mareknowakowski.meds.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String deviceId;
    private List<Medicine> medicineList;
    private List<Symptom> symptomList;

    public User convertSelfToUser(){
        return new User(this.id, this.deviceId, this.medicineList, this.symptomList);
    }
    public static UserDTO convertUserToUserDTO(User user){
        return new UserDTO(user.getId(), user.getDeviceId(), user.getMedicineList(), user.getSymptomList());
    }
}
