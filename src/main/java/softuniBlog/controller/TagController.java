package softuniBlog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import softuniBlog.entity.Tag;
import softuniBlog.exceptions.EntityNotFound;

public interface TagController {

    @GetMapping("/tag/{name}")
    ResponseEntity<Tag> articlesWithTag(@PathVariable String name) throws EntityNotFound;
}
