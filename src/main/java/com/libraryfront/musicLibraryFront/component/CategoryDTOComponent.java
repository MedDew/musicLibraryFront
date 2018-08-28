/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.component;

import com.musiclibrary.musiclibraryapi.dto.CategoryDTO;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mehdi
 */
@Component
public class CategoryDTOComponent 
{
    public void initCategoryDTO(CategoryDTO categoryDTOToFill, CategoryDTO categoryDTO)
    {
        categoryDTOToFill.setId(categoryDTO.getId());
        categoryDTOToFill.setCategoryName(categoryDTO.getCategoryName());
    }
}
