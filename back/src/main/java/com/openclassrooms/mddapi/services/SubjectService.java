package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.SubjectCreateDto;
import com.openclassrooms.mddapi.dtos.SubjectDto;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SubjectDto getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("Subject not found"));
        return modelMapper.map(subject, SubjectDto.class);
    }

    public SubjectDto createSubject(SubjectCreateDto subjectCreateDto) {
        Subject subject = modelMapper.map(subjectCreateDto, Subject.class);
        subjectRepository.save(subject);
        return modelMapper.map(subject, SubjectDto.class);
    }

    public SubjectDto updateSubject(Long id, SubjectCreateDto subjectUpdateDto) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("Subject not found"));
        modelMapper.map(subjectUpdateDto, subject);
        subjectRepository.save(subject);
        return modelMapper.map(subject, SubjectDto.class);
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

    public List<SubjectDto> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(subject -> modelMapper.map(subject, SubjectDto.class))
                .collect(Collectors.toList());
    }
}
