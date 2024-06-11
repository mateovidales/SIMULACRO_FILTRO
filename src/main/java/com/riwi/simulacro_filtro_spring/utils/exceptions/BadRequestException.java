package com.riwi.simulacro_filtro_spring.utils.exceptions;

public class BadRequestException extends RuntimeException 
{
    public BadRequestException(String error)
    {
        super(error);
    }    
}