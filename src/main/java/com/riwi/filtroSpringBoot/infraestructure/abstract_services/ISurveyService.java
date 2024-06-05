package com.riwi.filtroSpringBoot.infraestructure.abstract_services;

import org.springframework.stereotype.Service;

import com.riwi.filtroSpringBoot.api.dto.request.SurveyRequest;
import com.riwi.filtroSpringBoot.api.dto.response.SurveyResponse.SurveyResponse;


@Service
public interface ISurveyService extends CrudService<SurveyRequest, SurveyResponse, Integer>{
    
}
