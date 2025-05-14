package com.aleprimo.JobFlowApp.repositories;



import com.aleprimo.JobFlowApp.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserEntityRepository extends JpaRepository<UserEntity, Long> {
}
