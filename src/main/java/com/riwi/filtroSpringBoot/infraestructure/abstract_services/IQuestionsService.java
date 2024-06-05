package com.riwi.filtroSpringBoot.infraestructure.abstract_services;

import com.riwi.filtroSpringBoot.api.dto.request.QuestionsRequest;
import com.riwi.filtroSpringBoot.api.dto.response.QuestionOptionsResponse.QuestionsResponse;


public interface IQuestionsService extends CrudService<QuestionsRequest, QuestionsResponse, Integer>{
    QuestionsResponse updateQuestionText(String newText, Integer id);
}
