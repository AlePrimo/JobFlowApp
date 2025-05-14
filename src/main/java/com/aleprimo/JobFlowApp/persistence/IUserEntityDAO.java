package com.aleprimo.JobFlowApp.persistence;

import com.aleprimo.JobFlowApp.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserEntityDAO {

    List<UserEntity> findAll();
    Optional<UserEntity> findById(Long id);
    UserEntity save(UserEntity user);
    void deleteById(Long id);


}
