package com.noname.sh.service.mapper;

import com.noname.sh.domain.Section;
import com.noname.sh.service.dto.SectionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Section and its DTO SectionDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface SectionMapper extends EntityMapper<SectionDTO, Section> {

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
