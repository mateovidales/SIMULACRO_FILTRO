package com.riwi.simulacro_filtro_spring.infraestructure.abstract_service;

import com.riwi.simulacro_filtro_spring.api.dto.request.UserReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.UserResp;
import com.riwi.simulacro_filtro_spring.infraestructure.services.CrudService;

public interface IUserService extends CrudService<UserReq, UserResp, Long> {
    public String FIELD_BY_SORT = "userName";
}
