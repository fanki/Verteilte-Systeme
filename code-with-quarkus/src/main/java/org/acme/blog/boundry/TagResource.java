package org.acme.blog.boundry;

import org.acme.blog.entity.Tag;
import org.acme.blog.control.TagService;
import org.acme.blog.dto.TagDTO;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/tag")
public class TagResource {

    @Inject
    TagService tagService;

    @GET
    public List<TagDTO> getTags() {
        return tagService.getAllTags().stream()
            .map(tag -> new TagDTO(tag.getName()))
            .collect(Collectors.toList());
    }

    @POST
    public Response addTag(@Valid TagDTO tagDTO) {
        Tag tag = new Tag(tagDTO.name());
        tagService.addTag(tag);
        return Response.status(Response.Status.CREATED).entity(tag).build();
    }

    @GET
    @Path("/{id}")
    public Response getTagById(@PathParam("id") Long id) {
        Tag tag = tagService.getTagById(id);
        if (tag == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Tag not found").build();
        }
        return Response.ok(tag).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTag(@PathParam("id") Long id, @Valid TagDTO tagDTO) {
        Tag tag = new Tag(tagDTO.name());
        Tag updatedTag = tagService.updateTag(id, tag);
        if (updatedTag == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Tag not found").build();
        }
        return Response.ok(updatedTag).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTag(@PathParam("id") Long id) {
        boolean deleted = tagService.deleteTag(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Tag not found").build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
