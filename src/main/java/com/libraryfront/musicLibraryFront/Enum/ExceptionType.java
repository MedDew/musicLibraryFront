/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.Enum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mehdi
 */
public enum ExceptionType 
{
    GENRE("Genre"),
    CATEGORY("Category");
    
    private String exceptionType;
    
    ExceptionType(String type)
    {
        exceptionType = type;
    }
    
    public static ExceptionType findExceptionType(String type)
    {
        ExceptionType foundExceptionType = null;
        for(ExceptionType e : ExceptionType.values())
        {
            if(type.equals(e.CATEGORY.name()))
            {
                System.out.println("FOUND Category");
                foundExceptionType = e.CATEGORY;
            }
            else if(type.equals(e.GENRE.name()))
            {
                System.out.println("FOUND Genre");
                foundExceptionType = e.GENRE;
            }
        }
        
        return foundExceptionType;
    }
    
    public static Set<ExceptionType> getExceptionTypeDefaultList()
    {
        Set<ExceptionType> defaultExceptionTypeSet = new HashSet<>(Arrays.asList(ExceptionType.values()));
        return defaultExceptionTypeSet;
    }

    @Override
    public String toString() 
    {
//        return super.toString(); //To change body of generated methods, choose Tools | Templates.
        return exceptionType;
    }
    
    
}
