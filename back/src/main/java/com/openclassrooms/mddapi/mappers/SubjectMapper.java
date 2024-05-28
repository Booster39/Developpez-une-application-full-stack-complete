package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.SubjectDto;
import com.openclassrooms.mddapi.dtos.SubjectDto;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.Subject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubjectMapper  implements EntityMapper<SubjectDto, Subject>{
    @Override
    public Subject toEntity(SubjectDto dto) {
        if ( dto == null ) {
            return null;
        }

        Subject.SubjectBuilder subject = Subject.builder();

        subject.id( dto.getId() );
        subject.topic(dto.getTopic());

        return subject.build();
    }

    @Override
    public SubjectDto toDto(Subject entity) {
        if ( entity == null ) {
            return null;
        }

        SubjectDto subjectDto = new SubjectDto();

        subjectDto.setId( entity.getId() );
        subjectDto.setTopic(entity.getTopic());


        return subjectDto;
    }


    @Override
    public List<Subject> toEntity(List<SubjectDto> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectDto> toDto(List<Subject> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
