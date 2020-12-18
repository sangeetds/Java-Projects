package com.cognitree.sangeet.library.Controller;

import com.cognitree.sangeet.library.Enum.Credentials;
import com.cognitree.sangeet.library.Model.Book;
import com.cognitree.sangeet.library.Service.LibraryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;

@Path("/library")
public class Library {

    private final LibraryService libraryService = new LibraryService();

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(@HeaderParam("Authorization") String user) {
        if (!decode(user)) {
            return Response.status(401, "Not Authorized").build();
        }

        return Response.ok().entity(libraryService.getAllBooks()).build();
    }

    @GET
    @Path("/books/available")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableBooks(@HeaderParam("Authorization") String user) {
        if (!decode(user)) {
            return Response.status(401, "Not Authorized").build();
        }

        return Response.ok().entity(libraryService.getAvailableBooks()).build();
    }

    @GET
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(
            @PathParam("id") long id,
            @HeaderParam("Authorization") String user
    ) {
        if (!decode(user)) {
            return Response.status(401, "Not Authorized").build();
        }

        Book book = libraryService.getBook(id);

        if (book == null) {
            return Response.status(400).entity("Book not available").build();
        }

        return Response.ok(book).build();
    }

    @PUT
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnBook(
            @PathParam("id") long id,
            @DefaultValue("reserve") @QueryParam("action") String name,
            @HeaderParam("Authorization") String user
    ) {
        if (!decode(user)) {
            return Response.status(401, "Not Authorized").build();
        }

        Book book;

        if (name.equals("return")) {
            book = this.libraryService.returnBook(id);
        } else book = libraryService.reserveBook(id);

        if (book != null) {
            return Response.ok(book).build();
        }

        return Response.status(400, "Bad Request").build();
    }

    public boolean decode(String user) {
        String userPass = user.split(" ")[1];
        String base64 = new String(Base64.getDecoder().decode(userPass));

        return authorize(base64.split(":"));
    }

    private boolean authorize(String[] userPass) {
        return userPass[0].equalsIgnoreCase(Credentials.user) && userPass[1].equalsIgnoreCase(Credentials.user);
    }
}