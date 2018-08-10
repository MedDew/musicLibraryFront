/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.component;

import com.musiclibrary.musiclibraryapi.dto.GenreDTO;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mehdi
 */
@Component
public class GenreDTOComponent 
{
    public void iniGenreDTO(GenreDTO genreDTOToFill, GenreDTO genreDTO)
    {
        genreDTOToFill.setId(genreDTO.getId());
        genreDTOToFill.setGenreName(genreDTO.getGenreName());
    }
}
