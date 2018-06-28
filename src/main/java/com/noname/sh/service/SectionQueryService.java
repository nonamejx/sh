package com.noname.sh.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.noname.sh.domain.Section;
import com.noname.sh.domain.*; // for static metamodels
import com.noname.sh.repository.SectionRepository;
import com.noname.sh.repository.search.SectionSearchRepository;
import com.noname.sh.service.dto.SectionCriteria;

import com.noname.sh.service.dto.SectionDTO;
import com.noname.sh.service.mapper.SectionMapper;

/**
 * Service for executing complex queries for Section entities in the database.
 * The main input is a {@link SectionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SectionDTO} or a {@link Page} of {@link SectionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SectionQueryService extends QueryService<Section> {

    private final Logger log = LoggerFactory.getLogger(SectionQueryService.class);

    private final SectionRepository sectionRepository;

    private final SectionMapper sectionMapper;

    private final SectionSearchRepository sectionSearchRepository;

    public SectionQueryService(SectionRepository sectionRepository, SectionMapper sectionMapper, SectionSearchRepository sectionSearchRepository) {
        this.sectionRepository = sectionRepository;
        this.sectionMapper = sectionMapper;
        this.sectionSearchRepository = sectionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SectionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SectionDTO> findByCriteria(SectionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Section> specification = createSpecification(criteria);
        return sectionMapper.toDto(sectionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SectionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SectionDTO> findByCriteria(SectionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Section> specification = createSpecification(criteria);
        return sectionRepository.findAll(specification, page)
            .map(sectionMapper::toDto);
    }

    /**
     * Function to convert SectionCriteria to a {@link Specification}
     */
    private Specification<Section> createSpecification(SectionCriteria criteria) {
        Specification<Section> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Section_.id));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), Section_.text));
            }
            if (criteria.getAudioName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAudioName(), Section_.audioName));
            }
            if (criteria.getImageName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageName(), Section_.imageName));
            }
            if (criteria.getImageTag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageTag(), Section_.imageTag));
            }
            if (criteria.getPartNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPartNumber(), Section_.partNumber));
            }
            if (criteria.getQuestionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getQuestionId(), Section_.questions, Question_.id));
            }
        }
        return specification;
    }

}
