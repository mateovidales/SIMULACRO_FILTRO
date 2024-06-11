package com.riwi.simulacro_filtro_spring.infraestructure.abstract_service;

import java.util.List;

import com.riwi.simulacro_filtro_spring.api.dto.request.AssignmentReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.AssignmentResp;
import com.riwi.simulacro_filtro_spring.infraestructure.services.CrudService;

public interface IAssignmentService extends CrudService<AssignmentReq, AssignmentResp, Long> 
{
    public String FIELD_BY_SORT = "dueDate";
    
    List<AssignmentResp> getAssignmentsByLessonId(Long id);
}
