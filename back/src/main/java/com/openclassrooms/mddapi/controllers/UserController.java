package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.UserCreateDto;
import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.dtos.UserUpdateDto;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Log4j2
@Tag(name = "Users", description = "API pour la gestion des utilisateurs")
public class UserController {



}