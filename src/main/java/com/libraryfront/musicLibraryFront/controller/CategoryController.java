/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.controller;

import com.libraryfront.musicLibraryFront.exception.CategoryException;
import com.libraryfront.musicLibraryFront.service.CategoryService;
import com.musiclibrary.musiclibraryapi.dto.CategoryDTO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.libraryfront.musicLibraryFront.exception.Exception;
import com.libraryfront.musicLibraryFront.exceptionhandler.GenreResponseErrorHandler;
import org.json.JSONObject;

/**
 *
 * @author Mehdi
 */
@Controller
public class CategoryController 
{
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping(path = "/categories")
    public String showCategoryList(RestTemplate restTemplate, ModelMap modelMap)
    {
        List<CategoryDTO> categoryList = categoryService.getCategoryList(restTemplate);
        
        modelMap.addAttribute("categoryList", categoryList);
        
        return "musicCategory/categoryList";
    }
    
    @GetMapping("/categories/{id}")
    public ModelAndView showSpecificCategory(@PathVariable(name = "id") long categoryId, RestTemplate restTemplate)
    {
        CategoryDTO foundCategory = categoryService.findCategoryById(restTemplate, categoryId);
        ModelAndView modelAndView = new ModelAndView("musicCategory/specificCategory");
        modelAndView.addObject("foundCategory", foundCategory);
        
        return modelAndView;
    }
    
    @GetMapping("/categories/create")
    public ModelAndView showCategoryForm(CategoryDTO categoryDTO)
    {
//        View view = 
        ModelAndView modelAndView = new ModelAndView("musicCategory/categoryCreateForm");
        return modelAndView;
    }
    
    @PostMapping("/categories/create")
    public String postCategory(@Valid CategoryDTO categoryDTO, BindingResult bindingResult, RestTemplate restTemplate, ModelMap modelMap)
    {
        if(bindingResult.hasErrors())
        {
            return "musicCategory/categoryCreateForm";
        }
        
        //SETTING THE ERROR HANDLER TO HANDLE THE EXCEPTION SENT FROM THE API
        restTemplate.setErrorHandler(new GenreResponseErrorHandler());
        
        try 
        {
            CategoryDTO createdCategory = categoryService.createCategory(restTemplate, categoryDTO);
            return "redirect:/category/created/"+createdCategory.getId();
        } 
        catch (CategoryException e) 
        {
            //CONVERT THE EXCEPTION RESPONSE TO JSON OBJECT
            JSONObject exceptionJsonObj = new JSONObject(e.getResponse());
            //RECOVER THE BODY FROM THE RESPONSE
            String body = exceptionJsonObj.getString("body");
            //CONVERT THE BODY TO JSON OBJECT
            JSONObject exceptionBodyJSONObj = new JSONObject(body);
            //RECOVER THE ERROR MESSAGE FROM THE EXCEPTION
            String errorMessage = (String) exceptionBodyJSONObj.get("message");
            
            CategoryDTO errorCategoryDTO = new CategoryDTO();
            errorCategoryDTO.setErrorMessage(errorMessage);
            modelMap.addAttribute("errorCategoryDTO", errorCategoryDTO);
            modelMap.addAttribute("isErrorCategoryDTO", true);
            
            return "musicCategory/categoryCreateForm";
        }
        
    }
    
    @GetMapping(path = "/category/created/{categoryId}")
    public ModelAndView showCategoryCreated(RestTemplate restTemplate, @PathVariable long categoryId)
    {
        CategoryDTO createdCategory = categoryService.findCategoryById(restTemplate, categoryId);
        ModelAndView modelAndView = new ModelAndView("/musicCategory/createdCategory");
        modelAndView.addObject("category", createdCategory);
        
        return modelAndView;
    }
}
