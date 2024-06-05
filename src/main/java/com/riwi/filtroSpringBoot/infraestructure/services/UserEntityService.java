package com.riwi.filtroSpringBoot.infraestructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.filtroSpringBoot.api.dto.request.UserRequest;
import com.riwi.filtroSpringBoot.api.dto.response.UserResponse.SurveyResponseInUser;
import com.riwi.filtroSpringBoot.api.dto.response.UserResponse.UserResponse;
import com.riwi.filtroSpringBoot.domain.entities.Survey;
import com.riwi.filtroSpringBoot.domain.entities.UserEntity;
import com.riwi.filtroSpringBoot.domain.repositories.UserRepository;
import com.riwi.filtroSpringBoot.infraestructure.abstract_services.IUserEntityService;
import com.riwi.filtroSpringBoot.util.enums.SortType;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserEntityService implements IUserEntityService{
    
    @Autowired
    private final UserRepository userRepository;
    
     private UserEntity requestToEntity(UserRequest request) {
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(request, user);
        return user;
    }
    
     @Override
    public Page<UserResponse> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;
        PageRequest pagination = PageRequest.of(page, size);
        return this.userRepository.findAll(pagination).map(this::entityToResponse);
    }

    private UserEntity find(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserResponse getById(Integer id) {
        UserEntity user = find(id);
        return entityToResponse(user);
    }

    @Override
    public UserResponse create(UserRequest request) {
         UserEntity user = this.requestToEntity(request);
        return this.entityToResponse(this.userRepository.save(user));
    }

    @Override
    public UserResponse update(UserRequest request, Integer id) {
        UserEntity user = this.find(id);
        if (request.getName()!=null) user.setName(request.getName());
        if (request.getEmail()!=null) user.setEmail(request.getEmail());
        if (request.getPassword()!=null)user.setPassword(request.getPassword());
        if (request.getPassword()!=null)user.setPassword(request.getPassword());
        user.setActive(request.isActive()); 
            
        
      
        return this.entityToResponse(this.userRepository.save(user));
    }
    
    //METHOD OF DELETING USER NOT REQUIRED
    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


    /*----------
     * DTO MANAGEMENT ZONE
     * --------
     */

     private UserResponse entityToResponse (UserEntity user){
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        userResponse.setSurveys(surveyResponseInUser(user.getSurveys()));       
        return userResponse;
    }

    
      private List<SurveyResponseInUser> surveyResponseInUser(List<Survey> surveys) {
        return surveys.stream()
                .map(enrollment -> {
                    SurveyResponseInUser surveyResponseInUser = new SurveyResponseInUser();
                    return surveyResponseInUser;
                })
                .collect(Collectors.toList());
    }

    
}
