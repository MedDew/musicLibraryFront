/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.controller;

import com.libraryfront.musicLibraryFront.component.GenreDTOComponent;
import com.libraryfront.musicLibraryFront.exception.GenreException;
import com.libraryfront.musicLibraryFront.exceptionhandler.GenreResponseErrorHandler;
import com.libraryfront.musicLibraryFront.service.GenreService;
import com.musiclibrary.musiclibraryapi.dto.GenreDTO;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mehdi
 */
@Controller
public class GenreController 
{
    @Autowired
    private GenreService genreService;
    
    @Autowired
    private GenreDTOComponent genreDTOComponent;
    
    @GetMapping(path = "genres")
    public String showGenreList(Model model, RestTemplate restTemplate)
    {
        GenreDTO[] genres = genreService.getGenreList(restTemplate);
        //List<GenreDTO> genress = Arrays.asList(genres);
        //WEIRDLY?? DOES NOT WORK 
        //model.addAttribute(genress);
        
        model.addAttribute("genres", genres);
        
        return "musicGenre/genreList";
    }
    
    @GetMapping("genres/{genreId}")
    public String showSpecificGenre(RestTemplate restTemplate, Model model, @PathVariable long genreId)
    {
        GenreDTO foundGenre = genreService.findGenreById(restTemplate, genreId);
        //WEIRDLY DOES NOT WORK
        //model.addAttribute(foundGenre);
        
        model.addAttribute("foundGenre", foundGenre);
        
        return "musicGenre/specificGenre";
    }
    
    @GetMapping(path = "/genres/create")
    public String showGenreForm(GenreDTO genreDTO)
    {
        return "musicGenre/genreCreateForm";
    }
    
    @PostMapping("/genres/create")
    public String postGenre(@Valid GenreDTO genreDTO, BindingResult bindingResult, RestTemplate restTemplate, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "musicGenre/genreCreateForm";
        }
        
        GenreDTO createdGenre = null;
        //SETTING THE ERROR HANDLER TO HANDLE THE EXCEPTION SENT FROM THE API
        restTemplate.setErrorHandler(new GenreResponseErrorHandler());
        
        try 
        {
             createdGenre = genreService.createGenre(restTemplate, genreDTO);
            return "redirect:/genre/created/"+createdGenre.getId();
        } 
        catch (GenreException e) //HttpClientErrorException
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
            String errorMessage = (String) exceptionBodyJSONObj.get("message");
//            String errorMessage = exceptionBodyJSONObj.getString("message");
            
            System.out.println("RESPONSE.BODY.MESSAGE : "+errorMessage);
            System.out.println("RESPONSE.BODY.MESSAGE : "+exceptionJsonObj);
            e.printStackTrace();
            
            GenreDTO errorGenreDTO = new GenreDTO();
            errorGenreDTO.setErrorMessage(errorMessage);
            model.addAttribute("errorGenreDTO", errorGenreDTO);
            model.addAttribute("isErrorGenreDTO", true);
            return "musicGenre/genreCreateForm";
        }
        
//        return "redirect:/genre/created/"+createdGenre.getId();
    }
    
    @GetMapping("/genre/created/{id}")
    public String showGenreCreated(RestTemplate restTemplate, Model model, @PathVariable long id)
    {
        GenreDTO createdGenre = genreService.findGenreById(restTemplate, id);
        
        model.addAttribute("createdGenre", createdGenre);
        
        return "musicGenre/createdGenre";
    }
    
    @GetMapping(path = "/genres/update/{id}")
    public String showUpdateGenreForm(RestTemplate restTemplate, GenreDTO genreDTO, @PathVariable(name = "id") long genreId, Model model)
    {
        //SETTING THE ERROR HANDLER TO HANDLE THE EXCEPTION SENT FROM THE API
        restTemplate.setErrorHandler(new GenreResponseErrorHandler());
        try 
        {
            GenreDTO genreFound = genreService.findGenreById(restTemplate, genreId);
            genreDTOComponent.iniGenreDTO(genreDTO, genreFound);
            return "musicGenre/genreUpdateForm";
            
        } 
        catch (GenreException e) 
        {
            System.out.println("RESPONSE.BODY : "+e.getResponse().get("body"));
            System.out.println("RESPONSE AS ARRAY : "+e.getResponseBodyAsByteArray());
            
            //Recover the exception JSON message
            JSONObject exceptionJsonObj = new JSONObject(e.getResponse());
            String body = exceptionJsonObj.getString("body");
            JSONObject exceptionBodyJSONObj = new JSONObject(body);
            String errorMessage = exceptionBodyJSONObj.getString("message");
            
            //Pass the error message to the view
            GenreDTO errorGenreDTO = new GenreDTO();
            errorGenreDTO.setErrorMessage(errorMessage);
            
            model.addAttribute("errorGenreDTO", errorGenreDTO);
            
            return "musicGenre/genreNotFoundException";
        }
        
    }
    
//    @PutMapping(path = "/genres/update/{id}")
    @PostMapping(path = "/genres/update/{id}")
    public String putGenre(@Valid GenreDTO genreDTO, BindingResult bindingResult, RestTemplate restTemplate, @PathVariable long id, Model model)
    {
        if(bindingResult.hasErrors())
        {
            //Initialze the Genre Update Form again
            //GenreDTO genreFound = genreService.findGenreById(restTemplate, id);
            //genreDTOComponent.iniGenreDTO(genreDTO, genreFound);
            return "musicGenre/genreUpdateForm";
        }
        
        //SETTING THE ERROR HANDLER TO HANDLE THE EXCEPTION SENT FROM THE API
        //FOR THE SMART ASS TRYING TO CHANGE THE ID DIRECTLY FROM THE FORM ACTION 
        restTemplate.setErrorHandler(new GenreResponseErrorHandler());
        
        try 
        {
            GenreDTO updatedGenre = genreService.modifyGenre(restTemplate, genreDTO, id);
            return "redirect:/genres/updated/"+updatedGenre.getId();
        } 
        catch (GenreException e) 
        {
            System.out.println("RESPONSE.BODY : "+e.getResponse().get("body"));
            
            //Recover the exception JSON message
            JSONObject exceptionJsonObj = new JSONObject(e.getResponse());
            String body = exceptionJsonObj.getString("body");
            JSONObject exceptionBodyJSONObj = new JSONObject(body);
            String errorMessage = exceptionBodyJSONObj.getString("message");
            
            //Pass the error message to the view
            GenreDTO errorGenreDTO = new GenreDTO();
            errorGenreDTO.setErrorMessage(errorMessage);
            
            model.addAttribute("errorGenreDTO", errorGenreDTO);
            
            return "musicGenre/genreNotFoundException";
        }
    }
    
    @GetMapping("/genres/updated/{id}")
    public String showUpdatedGenre(RestTemplate restTemplate, Model model, @PathVariable(name = "id") long genreId)
    {
        GenreDTO updatedGenre = genreService.findGenreById(restTemplate, genreId);
        
        model.addAttribute("genre", updatedGenre);
        
        return "/musicGenre/updatedGenre";
    }
}
