package com.aleprimo.JobFlowApp.services.implementations;

import com.aleprimo.JobFlowApp.models.UserEntity;
import com.aleprimo.JobFlowApp.persistence.IUserEntityDAO;
import com.aleprimo.JobFlowApp.services.IUserEntityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserEntityServiceImpl implements IUserEntityService {

    private final IUserEntityDAO userEntityDAO;

    public UserEntityServiceImpl(IUserEntityDAO userEntityDAO) {
        this.userEntityDAO = userEntityDAO;
    }

    @Override
    public List<UserEntity> findAll() {
        return this.userEntityDAO.findAll();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return this.userEntityDAO.findById(id);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return this.userEntityDAO.save(user);
    }

    @Override
    public void deleteById(Long id) {
this.userEntityDAO.deleteById(id);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return this.userEntityDAO.findByEmail(email);
    }
}
