/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libraryfront.musicLibraryFront.component;

import com.musiclibrary.musiclibraryapi.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mehdi
 */
@Component
public class UserDTOComponent 
{
    public void initUserDTO(UserDTO userToFill, UserDTO userDTO)
    {
        userToFill.setId(userDTO.getId());
        userToFill.setCreationDate(userDTO.getCreationDate());
        userToFill.setLastLoginDate(userDTO.getLastLoginDate());
        userToFill.setFirstName(userDTO.getFirstName());
        userToFill.setLastName(userDTO.getLastName());
        userToFill.setIsLogged(userDTO.getIsLogged());
    }
}