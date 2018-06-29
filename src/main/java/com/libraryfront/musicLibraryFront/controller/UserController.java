/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.controller;

//import com.libraryapi.musicLibraryApI.dto.UserDTO;
//import com.librarymiddleware.musicLibraryAPI.DTO.UserDTO;
import com.libraryfront.musicLibraryFront.service.UserService;
import com.musiclibrary.musiclibraryapi.dto.UserDTO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mehdi
 */
@Controller
public class UserController 
{
    @Autowired
    private UserService userService;
    
    @GetMapping(path = "/users")
//    @ResponseBody
    public String showUserList(Model model, RestTemplate restTemplate)
    {
        /*METHOD 1*/
        List<UserDTO> userList = userService.getUserList(restTemplate);
        
        /*METHOD 2
        UserDTO[] userList = userService.getUserList(restTemplate);
        for(int i = 0;i < userList.length; i++)
        {
            UserDTO u = userList[i];
            System.out.println("Id : "+u.getId()+" "+u.getFirstName()+" "+u.getLastName()+" "+u.getCreationDate()+" "+u.getLastLoginDate());
        } 
        */
        
        /*METHOD 3
        List<UserDTO> userList = userService.getUserList(restTemplate);
        userList.forEach((u) -> {
            System.out.println("UserDTO : "+u);
        });
        userList.forEach((u) -> {
            System.out.println("Id : "+u.getId()+" FirstName : "+u.getFirstName()+" LastName : "+u.getLastName()+" CreationDate : "+u.getCreationDate());
            System.out.println("Id : "+u.getClass().getName());
        });
        */
        
        model.addAttribute("users", userList);
//        return userList;
        return "userList";
    }
    
    @GetMapping("/user")
    public String shoUserForm(UserDTO userDTO)
    {
        return "userForm";
    }
    
    @PostMapping(path = "/user")
    public String postUser(@Valid UserDTO userDTO, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "userForm";
        }
        
        return null;
    }
}
