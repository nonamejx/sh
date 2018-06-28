package com.noname.sh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.noname.sh.service.SectionService;
import com.noname.sh.web.rest.errors.BadRequestAlertException;
import com.noname.sh.web.rest.util.HeaderUtil;
import com.noname.sh.web.rest.util.PaginationUtil;
import com.noname.sh.service.dto.SectionDTO;
import com.noname.sh.service.dto.SectionCriteria;
import com.noname.sh.service.SectionQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Section.
 */
@RestController
@RequestMapping("/api")
public class SectionResource {

    private final Logger log = LoggerFactory.getLogger(SectionResource.class);

    private static final String ENTITY_NAME = "section";

    private final SectionService sectionService;

    private final SectionQueryService sectionQueryService;

    public SectionResource(SectionService sectionService, SectionQueryService sectionQueryService) {
        this.sectionService = sectionService;
        this.sectionQueryService = sectionQueryService;
    }

    /**
     * POST  /sections : Create a new section.
     *
     * @param sectionDTO the sectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sectionDTO, or with status 400 (Bad Request) if the section has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sections")
    @Timed
    public ResponseEntity<SectionDTO> createSection(@Valid @RequestBody SectionDTO sectionDTO) throws URISyntaxException {
        log.debug("REST request to save Section : {}", sectionDTO);
        if (sectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new section cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SectionDTO result = sectionService.save(sectionDTO);
        return ResponseEntity.created(new URI("/api/sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sections : Updates an existing section.
     *
     * @param sectionDTO the sectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sectionDTO,
     * or with status 400 (Bad Request) if the sectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the sectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sections")
    @Timed
    public ResponseEntity<SectionDTO> updateSection(@Valid @RequestBody SectionDTO sectionDTO) throws URISyntaxException {
        log.debug("REST request to update Section : {}", sectionDTO);
        if (sectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SectionDTO result = sectionService.save(sectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sections : get all the sections.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of sections in body
     */
    @GetMapping("/sections")
    @Timed
    public ResponseEntity<List<SectionDTO>> getAllSections(SectionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Sections by criteria: {}", criteria);
        Page<SectionDTO> page = sectionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sections/:id : get the "id" section.
     *
     * @param id the id of the sectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sections/{id}")
    @Timed
    public ResponseEntity<SectionDTO> getSection(@PathVariable Long id) {
        log.debug("REST request to get Section : {}", id);
        Optional<SectionDTO> sectionDTO = sectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sectionDTO);
    }

    /**
     * DELETE  /sections/:id : delete the "id" section.
     *
     * @param id the id of the sectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sections/{id}")
    @Timed
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        log.debug("REST request to delete Section : {}", id);
        sectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sections?query=:query : search for the section corresponding
     * to the query.
     *
     * @param query the query of the section search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sections")
    @Timed
    public ResponseEntity<List<SectionDTO>> searchSections(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Sections for query {}", query);
        Page<SectionDTO> page = sectionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
