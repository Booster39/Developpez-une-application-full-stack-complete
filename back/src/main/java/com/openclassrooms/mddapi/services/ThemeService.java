package com.openclassrooms.mddapi.services;


import com.openclassrooms.mddapi.dtos.ThemeDto;
import com.openclassrooms.mddapi.mapper.ThemeMapper;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ThemeMapper themeMapper;

    public ThemeDto getSubjectById(Long id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theme not found"));
        return themeMapper.toDto(theme);
    }

    public ThemeDto createSubject(ThemeDto themeDto) {
        Theme theme = themeMapper.toEntity(themeDto);
        themeRepository.save(theme);
        return themeMapper.toDto(theme);
    }

    public ThemeDto updateSubject(Long id, ThemeDto themeDto) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theme not found"));
        themeMapper.toEntity(themeDto);
        themeRepository.save(theme);
        return themeMapper.toDto(theme);
    }

    public void deleteSubject(Long id) {
        themeRepository.deleteById(id);
    }

    public List<ThemeDto> getAllSubjects() {
        List<Theme> themes = themeRepository.findAll();
        return themes.stream()
                .map(themeMapper::toDto)
                .collect(Collectors.toList());
    }
}
