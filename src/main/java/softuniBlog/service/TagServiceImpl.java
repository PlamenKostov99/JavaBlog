package softuniBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuniBlog.entity.Tag;
import softuniBlog.repository.TagRepository;

import java.util.HashSet;

@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  @Autowired
  public TagServiceImpl(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  @Override
  public Tag getByName(String name) {
    return tagRepository.findByName(name);
  }

  @Override
  public HashSet<Tag> getTagsFromString(String tagString) {
    HashSet<Tag> tags = new HashSet<>();

    String[] tagNames = tagString.split("[ ,]");

    for (String tagName : tagNames) {

      Tag currentTag = this.tagRepository.findByName(tagName);

      if (currentTag == null) {
        currentTag = new Tag(tagName);
        this.tagRepository.saveAndFlush(currentTag);
      }
      tags.add(currentTag);
    }

    return tags;
  }
}
