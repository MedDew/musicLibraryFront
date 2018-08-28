/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.service;

import com.libraryfront.musicLibraryFront.exception.GenreException;
import com.musiclibrary.musiclibraryapi.dto.CategoryDTO;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mehdi
 */
@Service
public class CategoryService 
{
   private static final String URL_GET_CATEGORIES = "http://localhost:8090/categories";
   private static final String URL_GET_CATEGORY = "http://localhost:8090/categories/{id}";
   private static final String URL_CREATE_CATEGORY = "http://localhost:8090/categories/create";
   private static final String URL_UPDATE_CATEGORY = "http://localhost:8090/categories/update/{id}";
   private static final String URL_DELETE_CATEGORY = "http://localhost:8090/categories/delete/{id}";
   
   public List<CategoryDTO> getCategoryList(RestTemplate restTemplate)
   {
       List<CategoryDTO> categoryList = restTemplate.getForObject(URL_GET_CATEGORIES, ArrayList.class);
       
       return categoryList;
   }
   
   public CategoryDTO findCategoryById(RestTemplate restTemplate, long id)
   {
       ResponseEntity<CategoryDTO> categoryResult = restTemplate.getForEntity(URL_GET_CATEGORY, CategoryDTO.class, id);
       CategoryDTO foundCategory = categoryResult.getBody();
       
       return foundCategory;
   }
   
   public CategoryDTO createCategory(RestTemplate restTemplate, CategoryDTO categoryDTO)
   {
        //SET THE HEADER TO GET THE REQUEST BODY AS APPLICATION JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
       
        //CONVERT genreDTO OBJECT TO JSON OBJECT TO BE SENT TO THE WEBSERVICE 
        JSONObject jsonObj = new JSONObject(categoryDTO);
        
        // DATA ATTCHED TO THE REQUEST
        HttpEntity<String> requestBody = new HttpEntity<>(jsonObj.toString(), headers);
        
        ResponseEntity<CategoryDTO> categoryResult = restTemplate.postForEntity(URL_CREATE_CATEGORY, requestBody, CategoryDTO.class);
        CategoryDTO createdCategory = categoryResult.getBody();
        
        return createdCategory;        
   }
   
   public CategoryDTO modifyCategory(RestTemplate restTemplate, CategoryDTO categoryDTO, long categoryId)
   {
        //SET THE HEADER TO GET THE REQUEST BODY AS APPLICATION JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        //CONVERT genreDTO OBJECT TO JSON OBJECT TO BE SENT TO THE WEBSERVICE 
        JSONObject jsonObj = new JSONObject(categoryDTO);
        
        // DATA ATTCHED TO THE REQUEST
        HttpEntity<String> requestBody = new HttpEntity<>(jsonObj.toString(), headers);
       
       ResponseEntity<CategoryDTO> categoryResult = restTemplate.exchange(URL_UPDATE_CATEGORY, HttpMethod.PUT, requestBody, CategoryDTO.class, categoryId);
       CategoryDTO updatedCategory = categoryResult.getBody();
       
       return updatedCategory; 
   }
}
