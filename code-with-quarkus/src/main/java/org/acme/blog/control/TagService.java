package org.acme.blog.control;

import org.acme.blog.entity.Tag;
import org.acme.blog.repository.TagRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TagService {

    @Inject
    TagRepository tagRepository;

    public List<Tag> getAllTags() {
        Log.info("Fetching all tags");
        return tagRepository.listAll();
    }

    public Tag getTagById(Long id) {
        Log.infof("Fetching tag with id: %d", id);
        return tagRepository.findById(id);
    }

    @Transactional
    public void addTag(Tag tag) {
        if (tag == null || tag.getName() == null || tag.getName().isEmpty()) {
            Log.error("Cannot add a tag with empty name");
            throw new IllegalArgumentException("Tag name cannot be null or empty");
        }
        Log.infof("Adding new tag: %s", tag.getName());
        tagRepository.persist(tag);
    }

    @Transactional
    public Tag updateTag(Long id, Tag tag) {
        Tag existingTag = tagRepository.findById(id);
        if (existingTag == null) {
            Log.warnf("Tag with id: %d not found", id);
            return null;
        }
        if (tag == null || tag.getName() == null || tag.getName().isEmpty()) {
            Log.error("Cannot update tag with empty name");
            throw new IllegalArgumentException("Tag name cannot be null or empty");
        }
        Log.infof("Updating tag with id: %d", id);
        existingTag.setName(tag.getName());
        return existingTag;  // Panache persistiert automatisch durch den Kontext
    }

    @Transactional
    public boolean deleteTag(Long id) {
        Tag tag = tagRepository.findById(id);
        if (tag != null) {
            Log.infof("Deleting tag with id: %d", id);
            tagRepository.delete(tag);
            return true;
        }
        Log.warnf("Tag with id: %d not found for deletion", id);
        return false;
    }
}
