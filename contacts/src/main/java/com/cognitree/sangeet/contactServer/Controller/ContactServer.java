package com.cognitree.sangeet.contactServer.Controller;


import com.cognitree.sangeet.contactServer.Model.Contact;
import com.cognitree.sangeet.contactServer.Model.User;
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
        User currentUser = authenticate(loginDetails);
        if (currentUser == null) {
            return Response.status(401).entity("Not authorized").build();
        }

        return Response.ok(contactService.getAllContacts(currentUser.getId())).build();
    }

    @GET
    @Path("/contacts/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContact(
            @QueryParam("search") String searchValue,
            @QueryParam("value") String value,
            @HeaderParam("Authorization") String loginDetails) {
        User currentUser = authenticate(loginDetails);
        if (currentUser == null) {
            return Response.status(401).entity("Not authorized").build();
        }

        List<Contact> contactList;

        if (searchValue.equals("name")) {
            contactList = this.contactService.getContactByName(value, currentUser.getId());
        }
        else if (searchValue.equals("number")) contactList = this.contactService.getContactByNumber(value, currentUser.getId());
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
        User currentUser = authenticate(loginDetails);
        if (currentUser == null) {
            return Response.status(401).entity("Not authorized").build();
        }

        contact.setUserId(currentUser.getId());
        if (this.contactService.addContact(contact) == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Values not acceptable").build();
        }

        return Response.ok(contact).build();
    }

    @DELETE
    @Path("/contacts/{number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteContact(@PathParam("number") long number, @HeaderParam("Authorization") String loginDetails) {
        User currentUser = authenticate(loginDetails);
        if (currentUser == null) {
            return Response.status(401).entity("Not authorized").build();
        }

        if (this.contactService.deleteContact(number, currentUser.getId())) {
            return Response.ok().build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("No such contact").build();
    }

    public User authenticate(String authDetails) {
        if (authDetails == null) return null;
        String[] decodedValue = decode(authDetails).split(":");

        List<User> users = this.userService.getAllUser();

        for (User possibleUser: users) {
            if (possibleUser.getName().equals(decodedValue[0]) && possibleUser.getPass().equals(decodedValue[1])) {
                return possibleUser;
            }
        }

        return null;
    }

    public String decode(String encodedValue) {
        String userPass = encodedValue.split(" ")[1];
        return new String(Base64.getDecoder().decode(userPass));
    }
}