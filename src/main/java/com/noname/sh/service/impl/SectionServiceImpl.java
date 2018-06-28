package com.noname.sh.service.impl;

import com.noname.sh.service.SectionService;
import com.noname.sh.domain.Section;
import com.noname.sh.repository.SectionRepository;
import com.noname.sh.repository.search.SectionSearchRepository;
import com.noname.sh.service.dto.SectionDTO;
import com.noname.sh.service.mapper.SectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Section.
 */
@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final Logger log = LoggerFactory.getLogger(SectionServiceImpl.class);

    private final SectionRepository sectionRepository;

    private final SectionMapper sectionMapper;

    private final SectionSearchRepository sectionSearchRepository;

    public SectionServiceImpl(SectionRepository sectionRepository, SectionMapper sectionMapper, SectionSearchRepository sectionSearchRepository) {
        this.sectionRepository = sectionRepository;
        this.sectionMapper = sectionMapper;
        this.sectionSearchRepository = sectionSearchRepository;
    }

    /**
     * Save a section.
     *
     * @param sectionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SectionDTO save(SectionDTO sectionDTO) {
        log.debug("Request to save Section : {}", sectionDTO);
        Section section = sectionMapper.toEntity(sectionDTO);
        section = sectionRepository.save(section);
        SectionDTO result = sectionMapper.toDto(section);
        sectionSearchRepository.save(section);
        return result;
    }

    /**
     * Get all the sections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sections");
        return sectionRepository.findAll(pageable)
            .map(sectionMapper::toDto);
    }


    /**
     * Get one section by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SectionDTO> findOne(Long id) {
        log.debug("Request to get Section : {}", id);
        return sectionRepository.findById(id)
            .map(sectionMapper::toDto);
    }

    /**
     * Delete the section by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Section : {}", id);
        sectionRepository.deleteById(id);
        sectionSearchRepository.deleteById(id);
    }

    /**
     * Search for the section corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SectionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sections for query {}", query);
        return sectionSearchRepository.search(queryStringQuery(query), pageable)
            .map(sectionMapper::toDto);
    }
}
