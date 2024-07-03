package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.dtos.ThemeCreateDto;
import com.openclassrooms.mddapi.dtos.ThemeDto;
import com.openclassrooms.mddapi.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/theme")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @GetMapping("/{id}")
    public ResponseEntity<ThemeDto> getSubject(@PathVariable String id)
    {
        try {
            ThemeDto themeDto = themeService.getSubjectById(Long.valueOf(id));
            if (themeDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(themeDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    @GetMapping
    public  ResponseEntity<HashMap<String, List<ThemeDto>>> getAllSubjects() {
        try {
            List<ThemeDto> themeDtos = themeService.getAllSubjects();
            var response = new HashMap<String, List<ThemeDto>>();
            response.put("themes", themeDtos);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
