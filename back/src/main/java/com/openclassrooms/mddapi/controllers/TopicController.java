package com.openclassrooms.mddapi.controllers;


import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getSubject(@PathVariable String id)
    {
        try {
            TopicDto topicDto = topicService.getSubjectById(Long.valueOf(id));
            if (topicDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(topicDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    @GetMapping
    public  ResponseEntity<HashMap<String, List<TopicDto>>> getAllSubjects() {
        try {
            List<TopicDto> topicDtos = topicService.getAllSubjects();
            var response = new HashMap<String, List<TopicDto>>();
            response.put("topics", topicDtos);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
