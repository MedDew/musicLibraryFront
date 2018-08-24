/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author Mehdi
 */
public class GenreException extends HttpClientErrorException
{
    private Map<String, Object> response;

    public GenreException(HttpStatus statusCode) 
    {
        super(statusCode);
    }

    public Map<String, Object> getResponse() 
    {
        return response;
    }

    public void setResponse(Map<String, Object> response) 
    {
        this.response = response;
    }
}
