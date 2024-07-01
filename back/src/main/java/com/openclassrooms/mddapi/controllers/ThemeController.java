package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.ThemeCreateDto;
import com.openclassrooms.mddapi.dtos.ThemeDto;
import com.openclassrooms.mddapi.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/theme")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @GetMapping("/{id}")
    public ThemeDto getSubject(@PathVariable Long id) {
        return themeService.getSubjectById(id);
    }

    @PostMapping
    public ThemeDto createSubject(@RequestBody ThemeCreateDto subjectCreateDto) {
        return themeService.createSubject(subjectCreateDto);
    }

    @PutMapping("/{id}")
    public ThemeDto updateSubject(@PathVariable Long id, @RequestBody ThemeCreateDto subjectUpdateDto) {
        return themeService.updateSubject(id, subjectUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Long id) {
        themeService.deleteSubject(id);
    }

    @GetMapping
    public List<ThemeDto> getAllSubjects() {
        return themeService.getAllSubjects();
    }
}
