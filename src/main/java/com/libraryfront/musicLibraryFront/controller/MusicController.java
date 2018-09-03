/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.controller;

import com.libraryfront.musicLibraryFront.service.CategoryService;
import com.libraryfront.musicLibraryFront.service.GenreService;
import com.libraryfront.musicLibraryFront.service.MusicService;
import com.musiclibrary.musiclibraryapi.dto.CategoryDTO;
import com.musiclibrary.musiclibraryapi.dto.GenreDTO;
import com.musiclibrary.musiclibraryapi.dto.MusicDTO;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

/**
 *
 * @author Mehdi
 */
@Controller
public class MusicController 
{
    @Autowired
    private MusicService musicService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired 
    private GenreService genreService;
    
    @GetMapping(path = "/musics")
    public String showMusicList(RestTemplate restTemplate, ModelMap modelMap )
    {
        List<MusicDTO> musicList = musicService.getMusicList(restTemplate);
        modelMap.addAttribute("musicList", musicList);
        
        return "/music/musicList";
    }
    
    @GetMapping(path = "/musics/{id}")
    public ModelAndView showSpecificMusic(RestTemplate restTemplate, @PathVariable(name = "id") long musicId)
    {
        ModelAndView modelAndView = new ModelAndView("/music/specificMusic");
        MusicDTO foundMusic = musicService.findMusicById(restTemplate, musicId);
        modelAndView.addObject("foundMusic", foundMusic);
        
        return modelAndView;
    }
    
    @GetMapping("/musics/create")
    public ModelAndView showMusicForm(MusicDTO musicDTO, RestTemplate restTemplate)
    {
        ModelAndView modelAndView = new ModelAndView("/music/musicCreateForm");
        
        //Recover the Category list
        List<CategoryDTO> categoryList = categoryService.getCategoryList(restTemplate);
        //Convert List to Set : BUT USELESS DUE TO THE UNICITY CONSTRAINT OVER CategoryName
        Set<CategoryDTO> categorySet = new HashSet<>(categoryList);
        
        //Recover the Genre list
        GenreDTO[] genreArr = genreService.getGenreList(restTemplate);
        //Convert List to Set : BUT USELESS DUE TO THE UNICITY CONSTRAINT OVER GenreName
        Set<GenreDTO> genreSet = new HashSet<>(Arrays.asList(genreArr));
                
        
        //Assign The recovered list
        modelAndView.addObject("categorySet", categorySet);
        modelAndView.addObject("genreSet", genreSet);
        
        return modelAndView;
    }
    
    @PostMapping(path = "/musics/create")
    public String postMusic(@Valid MusicDTO musicDTO, BindingResult bindingResult, RestTemplate restTemplate)
    {
        if(bindingResult.hasErrors())
        {
            return "/music/musicCreateForm";
        }
        MusicDTO createdMusic = null;
        try {
            
            createdMusic = musicService.createMusic(restTemplate, musicDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/music/created/"+createdMusic.getId();
    }
    
    @GetMapping(path = "/music/created/{id}")
    public ModelAndView showMusicCreated(RestTemplate restTemplate, @PathVariable(name = "id") long musicId, ModelMap modelMap)
    {
        MusicDTO createdMusic = musicService.findMusicById(restTemplate, musicId);
        ModelAndView modelAndView = new ModelAndView("/music/createdMusic");
        modelMap.addAttribute("music", createdMusic);
        
        return modelAndView;
    }
}
