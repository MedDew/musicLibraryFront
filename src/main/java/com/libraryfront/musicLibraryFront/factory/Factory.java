/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.factory;
import com.libraryfront.musicLibraryFront.Enum.ExceptionType;
import com.libraryfront.musicLibraryFront.exception.CategoryException;
import com.libraryfront.musicLibraryFront.exception.Exception;
import com.libraryfront.musicLibraryFront.exception.GenreException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
/**
 *
 * @author Mehdi
 */
public class Factory 
{
    public Exception getException(ExceptionType exceptionType, HttpStatus statusCode)
    {
        Exception exceptionToUse = null;
        switch(exceptionType)
        {
            case GENRE:
                System.out.println("INSTANTIATE GenreException");
                exceptionToUse = new GenreException(statusCode);
            break;
            
            case CATEGORY:
                System.out.println("INSTANTIATE CategoryException");
                exceptionToUse = new CategoryException(statusCode);
            break;
        }
        
        return exceptionToUse;
    }
}
