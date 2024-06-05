package com.riwi.filtroSpringBoot.infraestructure.abstract_services;

import org.springframework.data.domain.Page;

import com.riwi.filtroSpringBoot.util.enums.SortType;

public interface CrudService<REQUEST, RESPONSE, TYPE> {
    Page<RESPONSE> getAll(int page, int size, SortType sortType);

    RESPONSE getById(TYPE id);

    RESPONSE create(REQUEST request);

    RESPONSE update(REQUEST request, TYPE id);

    void delete(TYPE id);

}
