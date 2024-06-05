package com.riwi.filtroSpringBoot.infraestructure.services;



import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.filtroSpringBoot.api.dto.request.SurveyRequest;
import com.riwi.filtroSpringBoot.api.dto.response.SurveyResponse.SurveyResponse;
import com.riwi.filtroSpringBoot.domain.entities.Survey;

import com.riwi.filtroSpringBoot.domain.repositories.SurveyRepository;

import com.riwi.filtroSpringBoot.infraestructure.abstract_services.ISurveyService;
import com.riwi.filtroSpringBoot.infraestructure.helpers.EmailHelper;
import com.riwi.filtroSpringBoot.util.enums.SortType;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SurveyService implements ISurveyService{

     
    @Autowired
    private final SurveyRepository surveyRepository;
    private final EmailHelper emailHelper;
    

    private Survey requestToEntity(SurveyRequest request) {
        Survey survey = new Survey();
        BeanUtils.copyProperties(request, survey);
        return survey;
    }

     @Override
    public Page<SurveyResponse> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;
        PageRequest pagination = PageRequest.of(page, size);
        return this.surveyRepository.findAll(pagination).map(this::entityToResponse);
    }


    private Survey find(Integer id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + id));
    }

    @Override
    public SurveyResponse getById(Integer id) {
        Survey survey = find(id);
        return entityToResponse(survey);
    }

    @Override
public SurveyResponse create(SurveyRequest request) {
    Survey survey = this.requestToEntity(request);

    if (survey.getUser() != null && survey.getUser().getEmail() != null) {
        
        this.emailHelper.sendMail(survey.getUser().getEmail(), survey.getUser().getName(), survey.getCreationDate());
    } else {
        throw new IllegalArgumentException ("Unable to send email because the user or his email address is null or does not exist.");  
      }

    return this.entityToResponse(this.surveyRepository.save(survey));
}

    @Override
    public SurveyResponse update(SurveyRequest request, Integer id) {
        Survey survey = this.find(id);

        if (request.getTitle()!=null)survey.setTitle(request.getTitle());
        if (request.getDescription()!=null)survey.setDescription(request.getDescription());
        if (request.getCreationDate()!=null)survey.setCreationDate(request.getCreationDate());
        survey.setActive(request.isActive()); 
        return this.entityToResponse(this.surveyRepository.save(survey));
    }

    @Override
    public void delete(Integer id) {
        this.surveyRepository.delete(this.find(id));
    }


    /*----------
     * DTO MANAGEMENT ZONE
     * --------
     */

    private SurveyResponse entityToResponse (Survey survey){
        SurveyResponse surveyResponse = new SurveyResponse();
        BeanUtils.copyProperties(survey, surveyResponse);      
        return surveyResponse;
    }
    
}
