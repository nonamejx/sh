package com.noname.sh.service;

import com.noname.sh.service.dto.SectionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Section.
 */
public interface SectionService {

    /**
     * Save a section.
     *
     * @param sectionDTO the entity to save
     * @return the persisted entity
     */
    SectionDTO save(SectionDTO sectionDTO);

    /**
     * Get all the sections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SectionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" section.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SectionDTO> findOne(Long id);

    /**
     * Delete the "id" section.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the section corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SectionDTO> search(String query, Pageable pageable);
}
