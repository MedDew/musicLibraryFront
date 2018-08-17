/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.controller;

import com.libraryfront.musicLibraryFront.service.CategoryService;
import com.musiclibrary.musiclibraryapi.dto.CategoryDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

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
}
