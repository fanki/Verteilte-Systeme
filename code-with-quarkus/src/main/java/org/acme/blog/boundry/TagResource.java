package org.acme.blog.boundry;

import org.acme.blog.entity.Tag;
import org.acme.blog.control.TagService;
import org.acme.blog.dto.TagDTO;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.stream.Collectors;

@Path("/tags")
public class TagResource {

    @Inject
    TagService tagService;

    @GET
    @PermitAll
    public List<TagDTO> getTags() {
        return tagService.getAllTags().stream()
            .map(tag -> new TagDTO(tag.getName()))
            .collect(Collectors.toList());
    }

    @POST
    @RolesAllowed({"Admin", "Author"})
    public Response addTag(@Valid TagDTO tagDTO, @Context UriInfo uriInfo) {
        if (tagDTO.name() == null || tagDTO.name().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Tag name cannot be empty").build();
        }

        Tag tag = new Tag(tagDTO.name());
        tagService.addTag(tag);

        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(tag.getId())).build())
                       .entity(new TagDTO(tag.getName()))
                       .build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response getTagById(@PathParam("id") Long id) {
        Tag tag = tagService.getTagById(id);
        if (tag == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Tag not found").build();
        }
        return Response.ok(new TagDTO(tag.getName())).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Admin")
    public Response updateTag(@PathParam("id") Long id, @Valid TagDTO tagDTO) {
        if (tagDTO.name() == null || tagDTO.name().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Tag name cannot be empty").build();
        }

        Tag existingTag = tagService.getTagById(id);
        if (existingTag == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Tag not found").build();
        }

        existingTag.setName(tagDTO.name());
        Tag updatedTag = tagService.updateTag(id, existingTag);
        return Response.ok(new TagDTO(updatedTag.getName())).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Admin")
    public Response deleteTag(@PathParam("id") Long id) {
        boolean deleted = tagService.deleteTag(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Tag not found").build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
