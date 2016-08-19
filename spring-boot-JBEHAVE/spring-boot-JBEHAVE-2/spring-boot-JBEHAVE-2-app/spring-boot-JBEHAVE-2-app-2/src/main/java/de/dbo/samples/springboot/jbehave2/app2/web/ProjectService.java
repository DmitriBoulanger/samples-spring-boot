package de.dbo.samples.springboot.jbehave2.app2.web;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.dbo.samples.springboot.jbehave2.app2.domain.Project;

/**
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 *
 */
@RestController
public class ProjectService {

    List<Project> projects = new ArrayList<>();

    public ProjectService() {
        projects.add(new Project(UUID.randomUUID().toString(), "Eis"));
        projects.add(new Project(UUID.randomUUID().toString(), "Auto"));
        projects.add(new Project(UUID.randomUUID().toString(), "Teddybären"));
    }

    /**
     * Lists all available Projects
     *
     * @return
     */
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Project>> getProjects() {

        // TODO: Project Loading

        @SuppressWarnings("unused")
        final ObjectMapper mapper = new ObjectMapper();

        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }
}
