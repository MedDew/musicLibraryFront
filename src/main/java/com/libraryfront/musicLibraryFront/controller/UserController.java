/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.controller;

import com.libraryapi.musicLibraryApI.dto.UserDTO;
import com.libraryfront.musicLibraryFront.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String showUserList(Model model, RestTemplate restTemplate)
    {
        List<UserDTO> userList = userService.getUserList(restTemplate);
        model.addAttribute("users", userList);
        return "userList";
    }
}
