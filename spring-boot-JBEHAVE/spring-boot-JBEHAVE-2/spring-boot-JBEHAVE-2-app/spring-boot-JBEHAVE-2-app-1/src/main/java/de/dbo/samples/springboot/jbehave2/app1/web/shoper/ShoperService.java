package de.dbo.samples.springboot.jbehave2.app1.web.shoper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.dbo.samples.springboot.jbehave2.app1.domain.ErrorObject;
import de.dbo.samples.springboot.jbehave2.app1.domain.Shoper;

/**
 * Shoper Service
 */
@RestController
public class ShoperService {

    private final Map<String, Shoper> shoperIdMap   = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Shoper> shoperNameMap = Collections.synchronizedMap(new HashMap<>());

    /**
     * Creates and stores a new {@link Shoper}.
     * If a shoper with the same name already exists, this one will be returned.
     *
     * @param request is used to extract the request url
     * @param shoper the input {@link Shoper} entity (the shoper id will be ignored)
     * @return the new created or existing {@link Shoper}
     */
    @RequestMapping(value = "/shopers", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseEntity<?> createNewShoper(final HttpServletRequest request,
            @RequestBody final Shoper shoper) {

        if (StringUtils.isEmpty(shoper.getName())) {
            return ResponseEntity.badRequest().body(new ErrorObject("Name must not be empty!"));
        }

        Shoper persistentShoper = shoperNameMap.computeIfAbsent(shoper.getName(), this::createShoper);
        shoperIdMap.putIfAbsent(persistentShoper.getId(), persistentShoper);

        try {
            return ResponseEntity
                    .created(new URI(extractUrl(request) + persistentShoper.getId()))
                    .body(persistentShoper);
        }
        catch(URISyntaxException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Returns the {@link Shoper} with the specified id
     *
     * @param id the {@link Shoper} ID
     * @return {@link Shoper}
     */
    @RequestMapping(value = "/shopers/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getShoper(@PathVariable final String id) {

        Shoper customer = shoperIdMap.get(id);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new {@link Shoper} with a random UUID and the specified name
     *
     * @param name customer name
     * @return new {@link Shoper}
     */
    private Shoper createShoper(String name) {
        return new Shoper(UUID.randomUUID().toString(), name);
    }

    /**
     * Extracts the URL String from the HTTP Request
     *
     * @param request
     * @return URL String
     */
    private String extractUrl(final HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        requestURL = requestURL.endsWith("/") ? requestURL : requestURL + "/";
        return requestURL;
    }

}
