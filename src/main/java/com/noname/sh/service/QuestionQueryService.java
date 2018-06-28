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

import com.noname.sh.domain.Question;
import com.noname.sh.domain.*; // for static metamodels
import com.noname.sh.repository.QuestionRepository;
import com.noname.sh.repository.search.QuestionSearchRepository;
import com.noname.sh.service.dto.QuestionCriteria;

import com.noname.sh.service.dto.QuestionDTO;
import com.noname.sh.service.mapper.QuestionMapper;

/**
 * Service for executing complex queries for Question entities in the database.
 * The main input is a {@link QuestionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionDTO} or a {@link Page} of {@link QuestionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionQueryService extends QueryService<Question> {

    private final Logger log = LoggerFactory.getLogger(QuestionQueryService.class);

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final QuestionSearchRepository questionSearchRepository;

    public QuestionQueryService(QuestionRepository questionRepository, QuestionMapper questionMapper, QuestionSearchRepository questionSearchRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.questionSearchRepository = questionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link QuestionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionDTO> findByCriteria(QuestionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Question> specification = createSpecification(criteria);
        return questionMapper.toDto(questionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuestionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionDTO> findByCriteria(QuestionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Question> specification = createSpecification(criteria);
        return questionRepository.findAll(specification, page)
            .map(questionMapper::toDto);
    }

    /**
     * Function to convert QuestionCriteria to a {@link Specification}
     */
    private Specification<Question> createSpecification(QuestionCriteria criteria) {
        Specification<Question> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Question_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Question_.title));
            }
            if (criteria.getAnswerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAnswerId(), Question_.answers, Answer_.id));
            }
            if (criteria.getSectionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSectionId(), Question_.section, Section_.id));
            }
        }
        return specification;
    }

}
