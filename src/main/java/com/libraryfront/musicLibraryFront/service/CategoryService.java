/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.service;

import com.musiclibrary.musiclibraryapi.dto.CategoryDTO;
import java.util.ArrayList;
import java.util.List;
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
}
