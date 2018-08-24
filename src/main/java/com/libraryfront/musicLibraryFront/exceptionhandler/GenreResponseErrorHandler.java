/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.exceptionhandler;

import com.libraryfront.musicLibraryFront.Enum.ExceptionType;
import com.libraryfront.musicLibraryFront.exception.Exception;
import com.libraryfront.musicLibraryFront.factory.Factory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
        //Exception genreException = new Exception(response.getStatusCode());
        
        //RECOVER VALUES OF THE EXCEPTION TYPE TO SEARCH IN
        //BY FILTERING  IN THE ENUM ACCORDING TO THE responseBody
        ExceptionType.getExceptionTypeDefaultList().stream().forEach((ex) -> System.out.println("ExceptionType : "+ex+" | ex.name() : "+ex.name()+" "+ex.name().getClass().getSimpleName()+" | ex.toString() : "+ex.toString()+" "+ex.toString().getClass().getSimpleName()+" | ExceptionType Type : "+ex.getClass().getSimpleName()) );
        JSONObject jsonResponseBody = new JSONObject(responseBody);
        String message = (String) jsonResponseBody.get("message");
        String str1 = "CATGEORY";
        String str2 = "Category";
        boolean isStr1 = message.contains(str1);
        boolean isStr2 = message.contains(str2);
        
        //FIND THE RIGHT ExecptionType FROM THE message BY FILTERING AMONG THE Enum DEFAULT ExceptionType
        System.out.println("isStr1 : "+isStr1+" isStr2 : "+isStr2);
        Optional<ExceptionType> exceptionType = ExceptionType.getExceptionTypeDefaultList().stream().filter((ex) -> message.contains(ex.toString())).findAny();
        
        //RECOVER THE RIGHT ExceptionType TO USE
        Factory factory = new Factory();
        Exception exceptionToUse =  factory.getException(exceptionType.get(), response.getStatusCode());
        
        /*
         * JUST TO TEST THE FILTERING OF THE EXCEPTION TYPE
        JSONObject jsonObj = new JSONObject(responseBody);
        String errorMessage = (String) jsonObj.get("message");
        Boolean isCategory = errorMessage.contains("Category");
        */
        
        Map<String, Object> responseProperties = new HashMap<>();
        responseProperties.put("status", response.getStatusCode().toString());
        responseProperties.put("body", responseBody);
        responseProperties.put("headers", response.getHeaders());
        
        //genreException.setResponse(responseProperties);
        exceptionToUse.setResponse(responseProperties);
        
//        throw genreException;
        throw exceptionToUse;
    }
}
