package com.riwi.filtroSpringBoot.infraestructure.abstract_services;

import org.springframework.stereotype.Service;

import com.riwi.filtroSpringBoot.api.dto.request.UserRequest;
import com.riwi.filtroSpringBoot.api.dto.response.UserResponse.UserResponse;

@Service
public interface IUserEntityService extends CrudService<UserRequest, UserResponse, Integer>{

    
    
}
