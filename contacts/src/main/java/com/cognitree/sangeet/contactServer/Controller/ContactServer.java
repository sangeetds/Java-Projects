package com.cognitree.sangeet.contactServer.Controller;


import com.cognitree.sangeet.contactServer.Enums.Credentials;
import com.cognitree.sangeet.contactServer.Model.Contact;
import com.cognitree.sangeet.contactServer.Service.ContactService;
import com.cognitree.sangeet.contactServer.Service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.List;

@Path("/api")
public class ContactServer {

    private final ContactService contactService = new ContactService();
    private final UserService userService = new UserService();

    @GET
    @Path("/contacts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllContacts(@HeaderParam("Authorization") String loginDetails) {
        if (!authenticate(loginDetails)) {
            return Response.status(401).entity("Not authorized").build();
        }

        return Response.ok(contactService.getAllContacts()).build();
    }

    @GET
    @Path("/contacts/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContact(
            @QueryParam("search") String searchValue,
            @QueryParam("value") String value,
            @HeaderParam("Authorization") String loginDetails) {
        if (!authenticate(loginDetails)) {
            return Response.status(401).entity("Not authorized").build();
        }

        List<Contact> contactList;

        if (searchValue.equals("name")) {
            contactList = this.contactService.getContactByName(value);
        }
        else if (searchValue.equals("number")) contactList = this.contactService.getContactByNumber(value);
        else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong query params").build();
        }

        if (contactList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Contacts Found").build();
        }

        return Response.ok(contactList).build();
    }

    @POST
    @Path("/contacts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addContact(@HeaderParam("Authorization") String loginDetails, Contact contact) {
        if (!authenticate(loginDetails)) {
            return Response.status(401).entity("Not authorized").build();
        }

        if (this.contactService.addContact(contact) == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Values not acceptable").build();
        }

        return Response.ok(contact).build();
    }

    @DELETE
    @Path("/contacts/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteContact(@PathParam("userId") long id, @HeaderParam("Authorization") String loginDetails) {
        if (!authenticate(loginDetails)) {
            return Response.status(401).entity("Not authorized").build();
        }

        if (this.contactService.deleteContact(id)) {
            return Response.ok().build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("No such contact").build();
    }

    public boolean authenticate(String authDetails) {
        if (authDetails == null) return false;
        String[] decodedValue = decode(authDetails).split(":");

        return decodedValue[0].equalsIgnoreCase(Credentials.user) && decodedValue[1].equalsIgnoreCase(Credentials.pass);
    }

    public String decode(String encodedValue) {
        String userPass = encodedValue.split(" ")[1];
        return new String(Base64.getDecoder().decode(userPass));
    }
}