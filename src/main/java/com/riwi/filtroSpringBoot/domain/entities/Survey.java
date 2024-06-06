package com.riwi.filtroSpringBoot.domain.entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "survey")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Survey {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id", length = 11)
    private int idSurvey;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_creator_id", referencedColumnName = "user_id")
    private UserEntity user;


    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List <Question> questions = new ArrayList<>(); 
}
