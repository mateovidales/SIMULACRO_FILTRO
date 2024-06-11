package com.riwi.simulacro_filtro_spring.infraestructure.abstract_service;

import java.util.List;

import com.riwi.simulacro_filtro_spring.api.dto.request.SubmissionReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.SubmissionResp;
import com.riwi.simulacro_filtro_spring.infraestructure.services.CrudService;

public interface ISubmissionService extends CrudService<SubmissionReq, SubmissionResp, Long> 
{
    public String FIELD_BY_SORT = "submissionDate";

    List<SubmissionResp> getSubmissionsByAssignmentId(Long id);
    List<SubmissionResp> getSubmissionsByUserId(Long id);
}