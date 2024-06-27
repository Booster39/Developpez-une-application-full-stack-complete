package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.SubjectCreateDto;
import com.openclassrooms.mddapi.dtos.SubjectDto;
import com.openclassrooms.mddapi.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/{id}")
    public SubjectDto getSubject(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @PostMapping
    public SubjectDto createSubject(@RequestBody SubjectCreateDto subjectCreateDto) {
        return subjectService.createSubject(subjectCreateDto);
    }

    @PutMapping("/{id}")
    public SubjectDto updateSubject(@PathVariable Long id, @RequestBody SubjectCreateDto subjectUpdateDto) {
        return subjectService.updateSubject(id, subjectUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
    }

    @GetMapping
    public List<SubjectDto> getAllSubjects() {
        return subjectService.getAllSubjects();
    }
}
