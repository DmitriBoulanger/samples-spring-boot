package de.ityx.response.services.rcase.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.ityx.response.services.customer.Customer;
import de.ityx.response.services.rcase.Case;
import de.ityx.response.services.rcase.domain.CaseRepository;
import de.ityx.response.services.session.ErrorObject;

/**
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 *
 */
@RestController
public class CaseService {
    private static final Logger log     = LoggerFactory.getLogger(CaseService.class);

    private final AtomicLong    counter = new AtomicLong();

    @Autowired
    private CaseRepository      caseRepository;

    @PostConstruct
    public void init() {
        log.info("Initialized. CaseRepositoty: " + caseRepository);
    }

    /**
     *
     * Returns the {@link Case} with the specified id
     *
     * @param id the {@link Case} id
     * @return {@link Case}
     */
    @RequestMapping(value = "/cases/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getCaseById(@PathVariable final String id) {
        final Case rcase = this.caseRepository.findOne(id);
        if (rcase != null) {
            return ResponseEntity.ok(rcase);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Lists all available Cases
     *
     * @return
     */
    @RequestMapping(value = "/cases", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Case>> getCasess() {

        //Elasicsearch Loading
        final Iterable<Case> all = this.caseRepository.findAll();
        final List<Case> list = StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * Creates and stores a new {@link Case}.
     *
     * @param request is used to extract the request url
     * @param customer the input {@link Case} entity (the id will be ignored)
     * @return {@link Customer} with saved id
     */
    @RequestMapping(value = "/cases", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> createNewCase(HttpServletRequest request,
            @RequestBody final Case rcase) throws NamingException, URISyntaxException {

        if (rcase.getProject() == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ErrorObject("project not exist."));
        }
        if (rcase.getMessages() == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ErrorObject("messages not exist."));
        }
        if (rcase.getCustomer() == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ErrorObject("customer not exist."));
        }
        rcase.setCaseId(this.counter.incrementAndGet());
        rcase.setId(UUID.randomUUID().toString());

        this.caseRepository.save(rcase);
        return ResponseEntity.created(new URI(request.getRequestURL() + "/" + rcase.getId())).body(rcase);
    }
}
