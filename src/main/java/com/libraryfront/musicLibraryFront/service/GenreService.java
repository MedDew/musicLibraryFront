/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.service;

import com.libraryfront.musicLibraryFront.exception.GenreException;
import com.libraryfront.musicLibraryFront.exceptionhandler.GenreResponseErrorHandler;
import com.musiclibrary.musiclibraryapi.dto.GenreDTO;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mehdi
 */
@Service
public class GenreService 
{
    private static final String  URL_GET_GENRES = "http://localhost:8090/genres";
    private static final String  URL_GET_GENRE = "http://localhost:8090/genres/{id}";
    private static final String  URL_CREATE_GENRE = "http://localhost:8090/genres/create";
    private static final String  URL_UPDATE_GENRE = "http://localhost:8090/genres/update/{id}";
    private static final String  URL_DELETE_GENRE = "http://localhost:8090/genres/delete/{id}";
    
    public GenreDTO[] getGenreList(RestTemplate restTemplate)
    {
        ResponseEntity<GenreDTO[]> genreDTOResponseEntity = restTemplate.getForEntity(URL_GET_GENRES, GenreDTO[].class);
        GenreDTO[] genreList = genreDTOResponseEntity.getBody();
        
        MediaType contentType = genreDTOResponseEntity.getHeaders().getContentType();
        HttpStatus statusCode = genreDTOResponseEntity.getStatusCode();
        
        return  genreList;
    }
    
    public GenreDTO findGenreById(RestTemplate restTemplate, long genreId)
    {
        GenreDTO foundGenre = restTemplate.getForObject(URL_GET_GENRE, GenreDTO.class, genreId);
        
        return foundGenre;
    }
    
    public GenreDTO createGenre(RestTemplate restTemplate, GenreDTO genreDTO) throws GenreException
    {
        //SET THE HEADER TO GET THE REQUEST BODY AS APPLICATION JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.err.println("MediaType.APPLICATION_JSON : "+MediaType.APPLICATION_JSON);
        
        
        //CONVERT genreDTO OBJECT TO JSON OBJECT TO BE SENT TO THE WEBSERVICE 
        JSONObject jsonObj = new JSONObject(genreDTO);
        
        // DATA ATTCHED TO THE REQUEST
        HttpEntity<String> requestBody = new HttpEntity<>(jsonObj.toString(), headers);
        
        //SETTING THE ERROR HANDLER TO HANDLE THE EXCEPTION SENT FROM THE API
//        restTemplate.setErrorHandler(new GenreResponseErrorHandler());
//        try 
//        {
            ResponseEntity<GenreDTO> genreResult = restTemplate.postForEntity(URL_CREATE_GENRE, requestBody, GenreDTO.class);
            GenreDTO createdGenre = genreResult.getBody();
            return createdGenre;
        /*} 
        catch (GenreException  e) 
        {
            System.out.println("CAUSE : "+e.getCause());
            System.out.println("MESSAGE : "+e.getMessage());
            System.out.println("STATUS : "+e.getStatusCode());
            System.out.println("RAW STATUS : "+e.getRawStatusCode());
            System.out.println("HEADERS : "+e.getResponseHeaders());
            System.out.println("RESPONSE : "+e.getResponse());
            System.out.println("RESPONSE.BODY : "+e.getResponse().get("body"));
            System.out.println("RESPONSE AS ARRAY : "+e.getResponseBodyAsByteArray());

            JSONObject exceptionJsonObj = new JSONObject(e.getResponse());
            String body = exceptionJsonObj.getString("body");
            JSONObject exceptionBodyJSONObj = new JSONObject(body);
//            String errorMessage = (String) exceptionBodyJSONObj.get("message");
            String errorMessage = exceptionBodyJSONObj.getString("message");
            
            System.out.println("RESPONSE.BODY.MESSAGE : "+errorMessage);
            System.out.println("RESPONSE.BODY.MESSAGE : "+exceptionJsonObj);
            e.printStackTrace();
            
            GenreDTO errorGenreDTO = new GenreDTO();
            errorGenreDTO.setErrorMessage(errorMessage);
            return errorGenreDTO;
        }*/
    }
    
    public GenreDTO modifyGenre(RestTemplate restTemplate, GenreDTO genreDTO, long genreId)
    {
        //SET THE HEADER TO GET THE REQUEST BODY AS APPLICATION JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        //CONVERT genreDTO OBJECT TO JSON OBJECT TO BE SENT TO THE WEBSERVICE 
        JSONObject jsonObj = new JSONObject(genreDTO);
        
        // DATA ATTCHED TO THE REQUEST
        HttpEntity<String> requestBody = new HttpEntity<>(jsonObj.toString(), headers);
        
        ResponseEntity<GenreDTO> genreResult = restTemplate.exchange(URL_UPDATE_GENRE, HttpMethod.PUT, requestBody, GenreDTO.class, genreId);
        
        GenreDTO updatedGenre = genreResult.getBody();
        
        return updatedGenre;
    }
}
