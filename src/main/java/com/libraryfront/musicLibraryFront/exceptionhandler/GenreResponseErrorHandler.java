/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.exceptionhandler;

import com.libraryfront.musicLibraryFront.exception.GenreException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

/**
 *
 * @author Mehdi
 */
public class GenreResponseErrorHandler implements ResponseErrorHandler
{
    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException 
    {
        return errorHandler.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException 
    {
        //Convert an InputStream to String
        String responseBody =  new BufferedReader(new InputStreamReader(response.getBody())).lines().collect(Collectors.joining("\n"));
        GenreException genreException = new GenreException(response.getStatusCode());
        
        JSONObject jsonObj = new JSONObject(responseBody);
        String errorMessage = (String) jsonObj.get("message");
        Boolean isCategory = errorMessage.contains("Category");
        
        Map<String, Object> responseProperties = new HashMap<>();
        responseProperties.put("status", response.getStatusCode().toString());
        responseProperties.put("body", responseBody);
        responseProperties.put("headers", response.getHeaders());
        
        genreException.setResponse(responseProperties);
        
        throw genreException;
    }
}
