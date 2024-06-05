package com.riwi.filtroSpringBoot.infraestructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.riwi.filtroSpringBoot.api.dto.request.OptionQuestionRequest;
import com.riwi.filtroSpringBoot.api.dto.request.QuestionsRequest;
import com.riwi.filtroSpringBoot.api.dto.response.QuestionOptionsResponse.QuestionsResponse;
import com.riwi.filtroSpringBoot.api.dto.response.QuestionOptionsResponse.OptionQuestionResponseInQuestion.OptionsQResponseInQuestion;
import com.riwi.filtroSpringBoot.domain.entities.OptionQuestion;
import com.riwi.filtroSpringBoot.domain.entities.Question;
import com.riwi.filtroSpringBoot.domain.repositories.QuestionsRepository;
import com.riwi.filtroSpringBoot.infraestructure.abstract_services.IQuestionsService;
import com.riwi.filtroSpringBoot.util.enums.SortType;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionsService implements IQuestionsService {

    @Autowired
    private final QuestionsRepository questionsRepository;

    @Override
    public Page<QuestionsResponse> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;
        PageRequest pagination = PageRequest.of(page, size);
        return this.questionsRepository.findAll(pagination).map(this::entityToResponse);
    }

    private Question find(Integer id) {
        return questionsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + id));
    }

    @Override
    public QuestionsResponse getById(Integer id) {
        Question question = find(id);
        return entityToResponse(question);
    }

    @Override
    public QuestionsResponse create(QuestionsRequest request) {
        Question question = this.requestToEntity(request);
        if ("CLOSED".equalsIgnoreCase(request.getType())) {
            List<OptionQuestion> optionQuestions = request.getOptions().stream()
                    .map(this::optionRequestToEntity)
                    .collect(Collectors.toList());
            optionQuestions.forEach(option -> option.setQuestion(question));
            question.setOptionQuestions(optionQuestions);
        }
        return this.entityToResponse(this.questionsRepository.save(question));
    }

    @Override
    public QuestionsResponse update(QuestionsRequest request, Integer id) {
        Question question = this.find(id);
        if (request.getText() != null) question.setText(request.getText());
        if (request.getType() != null) question.setType(request.getType());

        if ("CLOSED".equalsIgnoreCase(request.getType()) && request.getOptions() != null) {
            List<OptionQuestion> optionQuestions = request.getOptions().stream()
                    .map(this::optionRequestToEntity)
                    .collect(Collectors.toList());
            optionQuestions.forEach(option -> option.setQuestion(question));
            question.setOptionQuestions(optionQuestions);
        }

        return this.entityToResponse(this.questionsRepository.save(question));
    }

   
    @Override
    public void delete(Integer id) {
        this.questionsRepository.delete(this.find(id));
    }

    /*----------
     * DTO MANAGEMENT ZONE
     * --------
     */

    private Question requestToEntity(QuestionsRequest request) {
        Question question = new Question();
        BeanUtils.copyProperties(request, question);
        return question;
    }

    private QuestionsResponse entityToResponse(Question question) {
        QuestionsResponse questionsResponse = new QuestionsResponse();
        BeanUtils.copyProperties(question, questionsResponse);
        questionsResponse.setOptionQuestions(optionsQuestionsResponseInQuestions(question.getOptionQuestions()));
        return questionsResponse;
    }

    private OptionQuestion optionRequestToEntity(OptionQuestionRequest request) {
        OptionQuestion option = new OptionQuestion();
        BeanUtils.copyProperties(request, option);
        return option;
    }
    @Override
    public QuestionsResponse updateQuestionText(String newText, Integer id) {
        Question question = this.find(id);
        if (newText != null) question.setText(newText);

        return this.entityToResponse(this.questionsRepository.save(question));
    }

    private List<OptionsQResponseInQuestion> optionsQuestionsResponseInQuestions(List<OptionQuestion> optionQuestions) {
        return optionQuestions.stream()
                .map(option -> {
                    OptionsQResponseInQuestion optionsQuestionsResponseInQuestions = new OptionsQResponseInQuestion();
                    optionsQuestionsResponseInQuestions.setIdOptionQuestion(option.getIdOptionQuestion());
                    optionsQuestionsResponseInQuestions.setIdQuestion(option.getQuestion().getIdQuestion());
                    optionsQuestionsResponseInQuestions.setText(option.getText());
                    optionsQuestionsResponseInQuestions.setActive(option.isActive());
                    return optionsQuestionsResponseInQuestions;
                })
                .collect(Collectors.toList());
    }
}
