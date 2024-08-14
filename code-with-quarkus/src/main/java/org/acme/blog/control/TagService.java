package org.acme.blog.control;

import org.acme.blog.entity.Tag;
import org.acme.blog.repository.TagRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TagService {

    @Inject
    TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.listAll();
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public void addTag(Tag tag) {
        tagRepository.persist(tag);
    }

    public Tag updateTag(Long id, Tag tag) {
        Tag existingTag = tagRepository.findById(id);
        if (existingTag != null) {
            existingTag.setName(tag.getName());
            tagRepository.persist(existingTag);
            return existingTag;
        }
        return null;
    }

    public boolean deleteTag(Long id) {
        Tag tag = tagRepository.findById(id);
        if (tag != null) {
            tagRepository.delete(tag);
            return true;
        }
        return false;
    }
}
