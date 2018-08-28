/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.service;

import com.musiclibrary.musiclibraryapi.dto.MusicDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mehdi
 */
@Service
public class MusicService 
{
    private static final String URL_GET_MUSICS = "http://localhost:8090/musics";
    private static final String URL_GET_MUSIC = "http://localhost:8090/musics/{id}";
    private static final String URL_CREATE_MUSIC = "http://localhost:8090/musics/create";
    private static final String URL_UPDATE_MUSIC = "http://localhost:8090/musics/update/{id}";
    private static final String URL_DELETE_MUSIC = "http://localhost:8090/musics/delete/{id}";
    
    public List<MusicDTO> getMusicList(RestTemplate restTemplate)
    {
        List<MusicDTO> musicList =  restTemplate.getForObject(URL_GET_MUSICS, ArrayList.class);
        return musicList;
    }
    
}
