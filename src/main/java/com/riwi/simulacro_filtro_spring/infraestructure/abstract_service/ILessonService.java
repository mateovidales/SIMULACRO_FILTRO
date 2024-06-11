package com.riwi.simulacro_filtro_spring.infraestructure.abstract_service;

import java.util.List;

import com.riwi.simulacro_filtro_spring.api.dto.request.LessonReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.LessonResp;
import com.riwi.simulacro_filtro_spring.infraestructure.services.CrudService;

public interface ILessonService extends CrudService<LessonReq, LessonResp, Long> 
{
    public String FIELD_BY_SORT = "lessonTitle";
    List<LessonResp> getLessonsByCourseId(Long courseId);
}
