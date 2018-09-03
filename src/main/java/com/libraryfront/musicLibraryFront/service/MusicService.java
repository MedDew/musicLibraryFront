/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.service;

import com.musiclibrary.musiclibraryapi.dto.MusicDTO;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
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
    
    public MusicDTO findMusicById(RestTemplate restTemplate, long id )
    {
        ResponseEntity<MusicDTO> musicResult = restTemplate.getForEntity(URL_GET_MUSIC, MusicDTO.class, id);
        MusicDTO foundMusic = musicResult.getBody();
        
        return foundMusic;
    }
    
    public MusicDTO createMusic(RestTemplate restTemplate, MusicDTO musicDTO )
    {
        //SET THE HEADER TO GET THE REQUEST BODY AS APPLICATION JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);//MediaType.APPLICATION_JSON Throws a handleHttpMessageNotReadable exception
        
        JSONObject jsonObj = new JSONObject(musicDTO);

        // DATA ATTCHED TO THE REQUEST
        HttpEntity<String> requestBody = new HttpEntity<>(jsonObj.toString(), headers);
        MusicDTO createdMusic = restTemplate.postForObject(URL_CREATE_MUSIC, requestBody, MusicDTO.class);
        
        return createdMusic;
    }
}
