package com.aleprimo.JobFlowApp.services;

import com.aleprimo.JobFlowApp.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserEntityService {

    List<UserEntity> findAll();
    Optional<UserEntity> findById(Long id);
    UserEntity save(UserEntity user);
    void deleteById(Long id);


}
