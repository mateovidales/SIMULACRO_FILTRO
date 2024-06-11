package com.riwi.simulacro_filtro_spring.infraestructure.services;

import org.springframework.data.domain.Page;

import com.riwi.simulacro_filtro_spring.utils.enums.SortType;

public interface CrudService <RQ, RS, ID> {
    public RS create (RQ request);
    public RS get(ID id);
    public Page<RS> getAll(int page, int size, SortType sort);
    public RS update(RQ request, ID id);
    public void delete(ID id);

}
