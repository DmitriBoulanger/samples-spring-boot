package de.dbo.samples.springboot.jbehave2.app1.web.shopper;

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
import de.dbo.samples.springboot.jbehave2.app1.domain.Shopper;

/**
 * Shopper Service
 */
@RestController
public class ShopperService {

    private final Map<String, Shopper> shopperIdMap   = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Shopper> shopperNameMap = Collections.synchronizedMap(new HashMap<>());

    /**
     * Creates and stores a new {@link Shopper}.
     * If a shopper with the same name already exists, this one will be returned.
     *
     * @param request is used to extract the request url
     * @param shopper the input {@link Shopper} entity (the shopper id will be ignored)
     * @return the new created or existing {@link Shopper}
     */
    @RequestMapping(value = "/shoppers", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ResponseEntity<?> createNewShopper(final HttpServletRequest request,
            @RequestBody final Shopper shopper) {

        if (StringUtils.isEmpty(shopper.getName())) {
            return ResponseEntity.badRequest().body(new ErrorObject("Name must not be empty!"));
        }

        Shopper persistentShopper = shopperNameMap.computeIfAbsent(shopper.getName(), this::createShopper);
        shopperIdMap.putIfAbsent(persistentShopper.getId(), persistentShopper);

        try {
            return ResponseEntity
                    .created(new URI(extractUrl(request) + persistentShopper.getId()))
                    .body(persistentShopper);
        }
        catch(URISyntaxException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Returns the {@link Shopper} with the specified id
     *
     * @param id the {@link Shopper} ID
     * @return {@link Shopper}
     */
    @RequestMapping(value = "/shoppers/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getShopper(@PathVariable final String id) {

        Shopper customer = shopperIdMap.get(id);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Creates a new {@link Shopper} with a random UUID and the specified name
     *
     * @param name customer name
     * @return new {@link Shopper}
     */
    private Shopper createShopper(String name) {
        return new Shopper(UUID.randomUUID().toString(), name);
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
