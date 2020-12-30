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

/**
 * Path configuring class which handles all the API requests.
 */
@Path("/api")
public class ContactResource {

    /**
     * Service objects which facilitates the interaction between the controller and the
     * database activities
     */
    private final ContactService contactService = new ContactService();
    private final UserService userService = new UserService();

    /**
     * Function to add a new user into the database which will have their own contacts
     * list.
     *
     * @param user takes in a JSON object through the request which is converted into an
     *             user object by jersey. It is then added to the database via userService.
     * @return Response which contains the information about the new user added.
     */
    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        User newUser = this.userService.addUser(user);

        return Response.status(201).entity(newUser).build();
    }

    /**
     * Function to delete a particular user. Needs authorisation to perform any operations.
     *
     * @param id the id of the user which has to be deleted
     * @param loginDetails authorisation details without which it is impossible to carry
     *                     any operations. It contains user and password encoded to make
     *                     secure authorisation.
     * @return Response object which tells whether the status of the operation and the
     * details regarding it.
     */
    @DELETE
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(
            @PathParam("id") long id,
            @HeaderParam("Authorization") String loginDetails
    ) {
        User currentUser = authenticate(loginDetails);
        if (currentUser == null || currentUser.getId() != id) {
            return Response.status(401).entity("Not authorized").build();
        }

        this.userService.deleteUser(id);

        return Response.status(204).build();
    }


    /**
     * Function to return the contacts list of a particular user. Can be filtered with the
     * help of query params. Needs authorisation to perform any operations.
     *
     * @param searchType query param, the filter type, can be left empty to get unfiltered list
     * @param value query param, the filter value through which the list will be searched or
     *              filtered
     * @param loginDetails authorisation details without which it is impossible to carry
     *                     any operations. It contains user and password encoded to make
     *                     secure authorisation.
     * @return Response object which tells whether the status of the operation and the
     * details regarding it.
     */
    @GET
    @Path("/contacts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContact(
            @QueryParam("search") String searchType,
            @QueryParam("value") String value,
            @HeaderParam("Authorization") String loginDetails) {
        User currentUser = authenticate(loginDetails);
        if (currentUser == null) {
            return Response.status(401).entity("Not authorized").build();
        }

        List<Contact> contactList;

        if (searchType == null || value == null) {
            return Response.ok(this.contactService.getAllContacts(currentUser.getId())).build();
        }
        else if (searchType.equals("name")) {
            contactList = this.contactService.getContactByName(value, currentUser.getId());
        }
        else if (searchType.equals("number")) contactList = this.contactService.getContactByNumber(value, currentUser.getId());
        else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong query params").build();
        }

        return Response.ok(contactList).build();
    }

    /**
     * Function to add a new contact for a particular user. Needs authorisation to perform any operations.
     * @param loginDetails authorisation details without which it is impossible to carry
     *                     any operations. It contains user and password encoded to make
     *                     secure authorisation.
     * @param contact which contains the details of the contact and is assigned the userId
     *                against the user who made this request.
     * @return Response object which tells whether the status of the operation and the
     * details regarding it.
     */
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
        this.contactService.addContact(contact);

        return Response.status(204).entity(contact).build();
    }

    /**
     * Needs authorisation to perform any operations.
     * @param number the number against which the contact will be found and deleted.
     * @param loginDetails authorisation details without which it is impossible to carry
     *                     any operations. It contains user and password encoded to make
     *                     secure authorisation.
     * @return Response object which tells whether the status of the operation and the
     * details regarding it.
     */
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

    /**
     * Function for secure authorisation and decode the user-pass authorization header.
     * Matches the user details against the stored registered user details
     * @param authDetails which contains the encoded string
     * @return User if login details matches with any registered user else returns an
     * null object
     */
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

    /**
     * Function responsible for decoding the encoded string.
     * @param encodedValue the encoded value
     * @return the decoded value
     */
    public String decode(String encodedValue) {
        String userPass = encodedValue.split(" ")[1];
        return new String(Base64.getDecoder().decode(userPass));
    }
}