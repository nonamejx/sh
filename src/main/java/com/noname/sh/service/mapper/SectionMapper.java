package com.noname.sh.service.mapper;

import com.noname.sh.domain.*;
import com.noname.sh.service.dto.SectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Section and its DTO SectionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SectionMapper extends EntityMapper<SectionDTO, Section> {


    @Mapping(target = "questions", ignore = true)
    Section toEntity(SectionDTO sectionDTO);

    default Section fromId(Long id) {
        if (id == null) {
            return null;
        }
        Section section = new Section();
        section.setId(id);
        return section;
    }
}
