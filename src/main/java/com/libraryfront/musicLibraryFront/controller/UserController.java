/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.controller;

//import com.libraryapi.musicLibraryApI.dto.UserDTO;
//import com.librarymiddleware.musicLibraryAPI.DTO.UserDTO;
import com.libraryfront.musicLibraryFront.component.UserDTOComponent;
import com.libraryfront.musicLibraryFront.service.UserService;
import com.musiclibrary.musiclibraryapi.dto.UserDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    private UserDTOComponent userDTOComponent;  
    
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
    
    @GetMapping("/users/create")
    public String showUserForm(UserDTO userDTO, Model model)
    {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//HH:mm:ss
//        LocalDateTime dt = LocalDateTime.now();
//        String localDateTime = dt.format(formatter);
//        userDTO.setCreationDate(localDateTime);
//        System.err.println("localDateTime : "+localDateTime+" userDTO : "+userDTO);
//        model.addAttribute(localDateTime);
        return "userCreateForm";
    }
    
    @GetMapping(path = "/users/{id}")
    public String showSpecificUser(RestTemplate restTemplate, @PathVariable(name = "id") long userId, Model model)
    {
        UserDTO foundUser = userService.findUserById(restTemplate, userId);
        model.addAttribute("user", foundUser);
        
        return "specificUser";
    }
            
    @PostMapping(path = "/users/create")
    public String postUser(@Valid UserDTO userDTO, BindingResult bindingResult,  RestTemplate restTemplate)
    {
        if(bindingResult.hasErrors())
        {
            System.err.println("userDTO : "+userDTO);
            return "userForm";
        }
        System.err.println("userDTO to create ==>  "+userDTO);
        UserDTO createdUser = userService.createUser(restTemplate, userDTO);

//        return null;
        return "redirect:/user/created/"+createdUser.getId();
    }
    
    @GetMapping(path = "/user/created/{id}")
    public String showUserCreated(RestTemplate restTemplate , @PathVariable(name = "id") long userId, Model model)
    {
        UserDTO createdUser = userService.findUserById(restTemplate, userId);
        model.addAttribute("user", createdUser);
        return "createdUser";
    }
    
    @GetMapping("/users/update/{id}")
    public String showUpdateUserForm(RestTemplate restTemplate, UserDTO userDTO, @PathVariable(name = "id") long userId)//, Model model
    {
        UserDTO userFound = userService.findUserById(restTemplate, userId);
        userDTOComponent.initUserDTO(userDTO, userFound);
//        model.addAttribute("user", userFound);
        return "userUpdateForm";
    }
    
    @PostMapping(path = "/users/update/{id}")
    public String putUser(RestTemplate restTemplate, @Valid UserDTO userDTO, BindingResult bindingResult, @PathVariable(name = "id") long userId)
    {
        if(bindingResult.hasErrors())
        {
            return "userUpdateForm";
        }
        
        UserDTO updatedUser = userService.modifyUser(restTemplate, userDTO, userId);
        
        return "redirect:/users/updated/"+updatedUser.getId();
    }
    
    @GetMapping(path = "/users/updated/{id}")
    public String showUpdatedUser(RestTemplate restTemplate, @PathVariable long id, Model model)
    {
        UserDTO updatedUser = userService.findUserById(restTemplate, id);
        model.addAttribute("user", updatedUser);
        
        return "updatedUser";
    }
    
    @GetMapping("/users/delete/{id}")
    public String showDeleteUser(RestTemplate restTemplate, UserDTO userDTO, @PathVariable(name = "id") long userId) 
    {
        UserDTO userToDelete = userService.findUserById(restTemplate, userId);
        userDTOComponent.initUserDTO(userDTO, userToDelete);
        
        return "userDeleteForm";
    }
    
    @PostMapping("/users/delete/{id}")
    public String eraseUser(RestTemplate restTemplate, UserDTO userDTO, @PathVariable long id, Model model)
    {
        UserDTO userDeleted = userService.eraseUser(restTemplate, id);
        model.addAttribute("user", userDeleted);
        
        return "deletedUser";
    }
}
