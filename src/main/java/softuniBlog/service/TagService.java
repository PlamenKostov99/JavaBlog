package softuniBlog.service;

import softuniBlog.entity.Tag;

import java.util.HashSet;

public interface TagService {

    Tag getByName(String name);

    HashSet<Tag> getTagsFromString(String tagString);
}
