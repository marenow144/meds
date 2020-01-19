package com.mareknowakowski.meds.services;

import com.mareknowakowski.meds.domain.User;
import com.mareknowakowski.meds.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;
    public User createOrFindUser(String deviceId){
        User retrievedUser = userRepository.findByDeviceId(deviceId);
        if(retrievedUser !=null){
            return retrievedUser;
        }
        User userToSave = new User(deviceId);
        userRepository.save(userToSave);
        return userToSave;
    }
}
