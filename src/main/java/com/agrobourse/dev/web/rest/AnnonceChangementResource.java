package com.agrobourse.dev.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.agrobourse.dev.domain.AnnonceChangement;

import com.agrobourse.dev.repository.AnnonceChangementRepository;
import com.agrobourse.dev.repository.search.AnnonceChangementSearchRepository;
import com.agrobourse.dev.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AnnonceChangement.
 */
@RestController
@RequestMapping("/api")
public class AnnonceChangementResource {

    private final Logger log = LoggerFactory.getLogger(AnnonceChangementResource.class);

    private static final String ENTITY_NAME = "annonceChangement";
        
    private final AnnonceChangementRepository annonceChangementRepository;

    private final AnnonceChangementSearchRepository annonceChangementSearchRepository;

    public AnnonceChangementResource(AnnonceChangementRepository annonceChangementRepository, AnnonceChangementSearchRepository annonceChangementSearchRepository) {
        this.annonceChangementRepository = annonceChangementRepository;
        this.annonceChangementSearchRepository = annonceChangementSearchRepository;
    }

    /**
     * POST  /annonce-changements : Create a new annonceChangement.
     *
     * @param annonceChangement the annonceChangement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new annonceChangement, or with status 400 (Bad Request) if the annonceChangement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/annonce-changements")
    @Timed
    public ResponseEntity<AnnonceChangement> createAnnonceChangement(@RequestBody AnnonceChangement annonceChangement) throws URISyntaxException {
        log.debug("REST request to save AnnonceChangement : {}", annonceChangement);
        if (annonceChangement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new annonceChangement cannot already have an ID")).body(null);
        }
        AnnonceChangement result = annonceChangementRepository.save(annonceChangement);
        annonceChangementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/annonce-changements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /annonce-changements : Updates an existing annonceChangement.
     *
     * @param annonceChangement the annonceChangement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated annonceChangement,
     * or with status 400 (Bad Request) if the annonceChangement is not valid,
     * or with status 500 (Internal Server Error) if the annonceChangement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/annonce-changements")
    @Timed
    public ResponseEntity<AnnonceChangement> updateAnnonceChangement(@RequestBody AnnonceChangement annonceChangement) throws URISyntaxException {
        log.debug("REST request to update AnnonceChangement : {}", annonceChangement);
        if (annonceChangement.getId() == null) {
            return createAnnonceChangement(annonceChangement);
        }
        AnnonceChangement result = annonceChangementRepository.save(annonceChangement);
        annonceChangementSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, annonceChangement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /annonce-changements : get all the annonceChangements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of annonceChangements in body
     */
    @GetMapping("/annonce-changements")
    @Timed
    public List<AnnonceChangement> getAllAnnonceChangements() {
        log.debug("REST request to get all AnnonceChangements");
        List<AnnonceChangement> annonceChangements = annonceChangementRepository.findAll();
        return annonceChangements;
    }

    /**
     * GET  /annonce-changements/:id : get the "id" annonceChangement.
     *
     * @param id the id of the annonceChangement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the annonceChangement, or with status 404 (Not Found)
     */
    @GetMapping("/annonce-changements/{id}")
    @Timed
    public ResponseEntity<AnnonceChangement> getAnnonceChangement(@PathVariable Long id) {
        log.debug("REST request to get AnnonceChangement : {}", id);
        AnnonceChangement annonceChangement = annonceChangementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(annonceChangement));
    }

    /**
     * DELETE  /annonce-changements/:id : delete the "id" annonceChangement.
     *
     * @param id the id of the annonceChangement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/annonce-changements/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnnonceChangement(@PathVariable Long id) {
        log.debug("REST request to delete AnnonceChangement : {}", id);
        annonceChangementRepository.delete(id);
        annonceChangementSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/annonce-changements?query=:query : search for the annonceChangement corresponding
     * to the query.
     *
     * @param query the query of the annonceChangement search 
     * @return the result of the search
     */
    @GetMapping("/_search/annonce-changements")
    @Timed
    public List<AnnonceChangement> searchAnnonceChangements(@RequestParam String query) {
        log.debug("REST request to search AnnonceChangements for query {}", query);
        return StreamSupport
            .stream(annonceChangementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
