package com.riwi.simulacro_filtro_spring.infraestructure.abstract_service;

import com.riwi.simulacro_filtro_spring.api.dto.request.CourseReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.CourseResp;
import com.riwi.simulacro_filtro_spring.infraestructure.services.CrudService;

public interface ICourseService extends CrudService<CourseReq, CourseResp, Long> 
{
    public String FIELD_BY_SORT = "courseName";
}
