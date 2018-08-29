/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.controller;

import com.libraryfront.musicLibraryFront.service.MusicService;
import com.musiclibrary.musiclibraryapi.dto.MusicDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
