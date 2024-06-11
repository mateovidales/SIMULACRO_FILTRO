package com.riwi.simulacro_filtro_spring.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.simulacro_filtro_spring.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long >{
    
}
