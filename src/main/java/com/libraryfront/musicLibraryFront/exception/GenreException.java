/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Mehdi
 */
public class GenreException extends Exception
{
    
    public GenreException(HttpStatus statusCode) 
    {
        super(statusCode);
    }
    
}
