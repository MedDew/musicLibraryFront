/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.service;

import com.libraryapi.musicLibraryApI.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mehdi
 */
@Service
public class UserService 
{
    public List<UserDTO> getUserList(RestTemplate restTemplate)
    {
        List<UserDTO> userList = restTemplate.getForObject("http://localhost:8080/users", ArrayList.class);
        return userList;
    }
}
