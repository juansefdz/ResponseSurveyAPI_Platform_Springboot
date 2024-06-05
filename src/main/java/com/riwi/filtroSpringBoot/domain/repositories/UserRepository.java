package com.riwi.filtroSpringBoot.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.filtroSpringBoot.domain.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository <UserEntity,Integer> {

    
}