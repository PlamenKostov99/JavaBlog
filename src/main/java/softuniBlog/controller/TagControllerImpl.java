package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import softuniBlog.entity.Tag;
import softuniBlog.exceptions.EntityNotFound;
import softuniBlog.service.TagServiceImpl;

@RestController
public class TagControllerImpl implements TagController {

  private final TagServiceImpl tagService;

  @Autowired
  public TagControllerImpl(TagServiceImpl tagService) {
    this.tagService = tagService;
  }

  public ResponseEntity<Tag> articlesWithTag(String name) throws EntityNotFound {

    Tag tag = tagService.getByName(name);

    if (tag == null) {
      throw new EntityNotFound();
    }

    return ResponseEntity.accepted().body(tag);
  }
}
