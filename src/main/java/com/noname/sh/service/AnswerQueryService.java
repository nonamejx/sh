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

import com.noname.sh.domain.Answer;
import com.noname.sh.domain.*; // for static metamodels
import com.noname.sh.repository.AnswerRepository;
import com.noname.sh.repository.search.AnswerSearchRepository;
import com.noname.sh.service.dto.AnswerCriteria;

import com.noname.sh.service.dto.AnswerDTO;
import com.noname.sh.service.mapper.AnswerMapper;

/**
 * Service for executing complex queries for Answer entities in the database.
 * The main input is a {@link AnswerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnswerDTO} or a {@link Page} of {@link AnswerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnswerQueryService extends QueryService<Answer> {

    private final Logger log = LoggerFactory.getLogger(AnswerQueryService.class);

    private final AnswerRepository answerRepository;

    private final AnswerMapper answerMapper;

    private final AnswerSearchRepository answerSearchRepository;

    public AnswerQueryService(AnswerRepository answerRepository, AnswerMapper answerMapper, AnswerSearchRepository answerSearchRepository) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
        this.answerSearchRepository = answerSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AnswerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnswerDTO> findByCriteria(AnswerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Answer> specification = createSpecification(criteria);
        return answerMapper.toDto(answerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnswerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnswerDTO> findByCriteria(AnswerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Answer> specification = createSpecification(criteria);
        return answerRepository.findAll(specification, page)
            .map(answerMapper::toDto);
    }

    /**
     * Function to convert AnswerCriteria to a {@link Specification}
     */
    private Specification<Answer> createSpecification(AnswerCriteria criteria) {
        Specification<Answer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Answer_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Answer_.title));
            }
            if (criteria.getCorrectAnswer() != null) {
                specification = specification.and(buildSpecification(criteria.getCorrectAnswer(), Answer_.correctAnswer));
            }
            if (criteria.getQuestionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getQuestionId(), Answer_.question, Question_.id));
            }
        }
        return specification;
    }

}
