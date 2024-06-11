package com.riwi.simulacro_filtro_spring.infraestructure.abstract_service;

import java.util.List;

import com.riwi.simulacro_filtro_spring.api.dto.request.EnrollmentReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.EnrollmentResp;
import com.riwi.simulacro_filtro_spring.infraestructure.services.CrudService;

public interface IEnrollmentService extends CrudService<EnrollmentReq, EnrollmentResp, Long> 
{
    public String FIELD_BY_SORT = "enrollmentName";

    List<EnrollmentResp> getCoursesByUserId(Long id);
    List<EnrollmentResp> getUsersByCourseId(Long id);

}
