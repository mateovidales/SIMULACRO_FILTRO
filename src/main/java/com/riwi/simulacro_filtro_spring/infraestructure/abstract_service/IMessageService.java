package com.riwi.simulacro_filtro_spring.infraestructure.abstract_service;

import java.util.List;

import com.riwi.simulacro_filtro_spring.api.dto.request.MessageReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.MessageResp;
import com.riwi.simulacro_filtro_spring.infraestructure.services.CrudService;

public interface IMessageService extends CrudService<MessageReq, MessageResp, Long> 
{
    public String FIELD_BY_SORT = "sendDate";

    List<MessageResp> getMessagesBetweenUsers(Long senderId, Long receiverId);
    List<MessageResp> getMessagesByCourseId(Long courseId);
}
