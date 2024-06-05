package com.riwi.filtroSpringBoot.domain.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "optionQuestion")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_question_id", length = 11)
    private int idOptionQuestion;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "active", nullable = false)
    private boolean active;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_question_id", referencedColumnName = "question_id")
    private Question question;

    
    
   
    
}
