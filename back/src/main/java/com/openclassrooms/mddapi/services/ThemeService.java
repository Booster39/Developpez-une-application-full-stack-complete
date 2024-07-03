package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.ThemeCreateDto;
import com.openclassrooms.mddapi.dtos.ThemeDto;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ThemeDto getSubjectById(Long id) {
        Theme theme = themeRepository.findById(String.valueOf(id)).orElseThrow(() -> new RuntimeException("Theme not found"));
        return modelMapper.map(theme, ThemeDto.class);
    }

    public ThemeDto createSubject(ThemeCreateDto subjectCreateDto) {
        Theme theme = modelMapper.map(subjectCreateDto, Theme.class);
        themeRepository.save(theme);
        return modelMapper.map(theme, ThemeDto.class);
    }

    public ThemeDto updateSubject(Long id, ThemeCreateDto subjectUpdateDto) {
        Theme theme = themeRepository.findById(String.valueOf(id)).orElseThrow(() -> new RuntimeException("Theme not found"));
        modelMapper.map(subjectUpdateDto, theme);
        themeRepository.save(theme);
        return modelMapper.map(theme, ThemeDto.class);
    }

    public void deleteSubject(Long id) {
        themeRepository.deleteById(String.valueOf(id));
    }

    public List<ThemeDto> getAllSubjects() {
        List<Theme> themes = themeRepository.findAll();
        return themes.stream()
                .map(theme -> modelMapper.map(theme, ThemeDto.class))
                .collect(Collectors.toList());
    }
}
