package com.aleprimo.JobFlowApp.persistence.implementations;


import com.aleprimo.JobFlowApp.models.UserEntity;
import com.aleprimo.JobFlowApp.persistence.IUserEntityDAO;

import com.aleprimo.JobFlowApp.repositories.IUserEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserEntityDAOimpl implements IUserEntityDAO {

private final IUserEntityRepository userEntityRepository;

    public UserEntityDAOimpl(IUserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public List<UserEntity> findAll() {
        return this.userEntityRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return this.userEntityRepository.findById(id);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return this.userEntityRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
       this.userEntityRepository.deleteById(id);
    }

    @Override
    public  Optional<UserEntity> findByEmail(String email) {
        return this.userEntityRepository.findByEmail(email);
    }
}
