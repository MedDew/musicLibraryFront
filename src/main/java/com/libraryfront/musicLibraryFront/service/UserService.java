/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.service;

//import com.libraryapi.musicLibraryApI.dto.UserDTO;
//import com.librarymiddleware.musicLibraryAPI.DTO.UserDTO;
import com.musiclibrary.musiclibraryapi.dto.UserDTO;
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
        /*METHOD 1*/
        List<UserDTO> userList = restTemplate.getForObject("http://localhost:8090/users", ArrayList.class);
        
        /*METHOD 2
        ResponseEntity<UserDTO[]> userDTOResponseEntity = restTemplate.getForEntity("http://localhost:8090/users", UserDTO[].class);
        UserDTO[] userList = userDTOResponseEntity.getBody();
        */
        
        /*METHOD 3
        ResponseEntity<List<UserDTO>> userDTOResponseEntity = restTemplate.exchange(
                                                                                "http://localhost:8090/users", 
                                                                                HttpMethod.GET, 
                                                                                null, 
                                                                                new ParameterizedTypeReference<List<UserDTO>>(){}
                                                                               );
        List<UserDTO> userList = userDTOResponseEntity.getBody();
        */
        /*GOES WITH METHOD 2 & 3
        MediaType contentType = userDTOResponseEntity.getHeaders().getContentType();
        HttpStatus statusCode = userDTOResponseEntity.getStatusCode();
        */
        
        return userList;
    }
    
    public UserDTO createUser(RestTemplate restTemplate)
    {
        UserDTO createdUser = restTemplate.getForObject("http://localhost:8090/user", UserDTO.class);
        return createdUser;
    }
}
