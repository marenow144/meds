package com.mareknowakowski.meds.repositories;

import com.mareknowakowski.meds.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByDeviceId(String deviceId);

}
