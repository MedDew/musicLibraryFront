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
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mehdi
 */
@Service
public class UserService 
{
    private static final String URL_GET_USERS = "http://localhost:8090/users";
    private static final String URL_GET_USER = "http://localhost:8090/users/{id}";
    private static final String URL_CREATE_USER = "http://localhost:8090/users/create";
    private static final String URL_MODIFY_USER = "http://localhost:8090/users/update/{id}";
    private static final String URL_DELETE_USER = "http://localhost:8090/users/delete/{id}";
    
    //@Autowired
    //private RestTemplate restTemplate;
    
    public List<UserDTO> getUserList(RestTemplate restTemplate)
    {
        /*METHOD 1*/
        List<UserDTO> userList = restTemplate.getForObject(URL_GET_USERS, ArrayList.class);
        
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
    
    public UserDTO createUser(RestTemplate restTemplate, UserDTO userDTO)
    {
        //USELESS IN MY CASE
        //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        
        // /!\DOES NOT WORK FOR POSTING DATA
        //UserDTO createdUser = restTemplate.getForObject(URL_CREATE_USERS, UserDTO.class);
        
        //SET THE HEADER TO GET THE REQUEST BODY AS 
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
//        headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.err.println("MediaType.APPLICATION_JSON : "+MediaType.APPLICATION_JSON);
        
        /*JUST A TEST
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json");
        
        MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
        System.err.println("firstName : "+userDTO.getFirstName()+" lastName : "+userDTO.getLastName()+" creationDate : "+userDTO.getCreationDate());
        data.add("firstName", userDTO.getFirstName());
        data.add("lastName", userDTO.getLastName());
        data.add("creationDate", userDTO.getCreationDate());
        */
        
        
        System.err.println("userDTO ====> : "+userDTO);
        //TEST SAMPLE TO MAKE THE REQUEST WORK
//        String requestJSON = "{\"creationDate\" : \"2018-07-05T12:35:17\", \"firstName\" : \"Elyes\", \"lastName\" : \"Longo\", \"lastLoginDate\" : \"2018-07-05T12:35:17\", \"isLogged\" : true}";
        
        //CONVERT userDTO OBJECT TO JSON OBJECT TO BE SENT TO THE WEBSERVICE 
        JSONObject jsonObj = new JSONObject(userDTO);
        jsonObj.put("lastLoginDate", userDTO.getLastLoginDate());
        jsonObj.put("isLogged", false);
//        System.err.println(" jsonObj.toString() : "+jsonObj.toString());
//        System.err.println(" requestJSON : "+requestJSON);
        
        // DATA ATTCHED TO THE REQUEST
        HttpEntity<String> requestBody = new HttpEntity<>(jsonObj.toString(), headers);
//        System.err.println("requestBody : "+requestBody);
        
        /*JUST A TEST
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        */
        
        // SEND REQUEST WITH POST METHOD
        UserDTO createdUser = restTemplate.postForObject(URL_CREATE_USER, requestBody, UserDTO.class);
        //WORK AS WELL AS THE METHOD USED ABOVE
        //UserDTO createdUser = restTemplate.exchange(URL_CREATE_USER, HttpMethod.POST, requestBody, UserDTO.class).getBody();
        System.err.println("createdUser "+createdUser);
        
        return createdUser;
    }
    
    public UserDTO findUserById(RestTemplate restTemplate, long userId)
    {
        UserDTO foundUser = restTemplate.getForObject(URL_GET_USER, UserDTO.class, userId);
        return foundUser;
    }
    
    public UserDTO modifyUser(RestTemplate restTemplate, UserDTO userDTO, long id)
    {
        restTemplate.put(URL_MODIFY_USER, userDTO, id);
        UserDTO updatedUser = findUserById(restTemplate, id);
        
        return updatedUser;
    }
    
    public UserDTO eraseUser(RestTemplate restTemplate, long id)
    {
        //DO THAT WAY IF YOU DON'T NEED TO RECOVER THE DELETED USER
        //restTemplate.delete(URL_DELETE_USER, id);
        UserDTO deletedUser = restTemplate.exchange(URL_DELETE_USER, HttpMethod.DELETE, null, UserDTO.class, id).getBody();
        
        return deletedUser;
    }
}
