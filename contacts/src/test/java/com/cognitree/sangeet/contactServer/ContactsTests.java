package com.cognitree.sangeet.contactServer;

import com.cognitree.sangeet.contactServer.Controller.ContactResource;
import com.cognitree.sangeet.contactServer.Model.Contact;
import com.cognitree.sangeet.contactServer.Model.User;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ContactsTests extends JerseyTest {

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(ContactResource.class);
    }

    @Test
    public void testUserAddRequestReturnsNewUser() {
        User user = new User(1, "user", "user");
        Response response = target("/api/user").request().post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(201, response.getStatus());
        assertEquals(user, response.getEntity());
    }

    @Test
    public void testDeleteUserWithoutAuthorisationReturnUnauthorisedResponse() {
        Response response = target("/api/user/1").request().delete();

        assertEquals(401, response.getStatus());
        assertEquals("Not authorized", response.getEntity());
    }

    @Test
    public void testDeleteUserWithAuthorisationReturnOkResponse() {
        Response response = target("/api/user/1").request().header("Authorization", "Basic dXNlcjp1c2Vy").delete();

        assertEquals(204, response.getStatus());
    }

    @Test
    public void testDeleteUserWithAuthorisationWrongPathReturnWrongPathResponse() {
        Response response = target("/api/user/4").request().header("Authorization", "Basic dXNlcjp1c2Vy").delete();

        assertEquals(401, response.getStatus());
        assertEquals("Not authorized", response.getEntity());
    }

    @Test
    public void testGetContactsListWithAuthorisationWithoutFilterReturnOkResponse() {
        Response response = target("/api/contacts").request().header("Authorization", "Basic dXNlcjp1c2Vy").get();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetContactsListWithOutAuthorisationWithoutFilterReturnUnauthorisedResponse() {
        Response response = target("/api/contacts").request().get();

        assertEquals(401, response.getStatus());
        assertEquals("Not authorized", response.getEntity());
    }

    @Test
    public void testGetContactsListWithAuthorisationWithNameFilterReturnOkResponse() {
        Response response = target("/api/contacts").queryParam("search", "name").queryParam("value", "a").request().header("Authorization", "Basic dXNlcjp1c2Vy").get();

        assertEquals(200, response.getStatus());
    }


    @Test
    public void testGetContactsListWithAuthorisationWithNumberFilterReturnOkResponse() {
        Response response = target("/api/contacts").queryParam("search", "number").queryParam("value", "1").request().header("Authorization", "Basic dXNlcjp1c2Vy").get();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetContactsListWithAuthorisationWithWrongFilterReturnParamErrorResponse() {
        Response response = target("/api/contacts").queryParam("search", "non").queryParam("value", "a").request().header("Authorization", "Basic dXNlcjp1c2Vy").get();

        assertEquals(400, response.getStatus());
        assertEquals("Wrong query params", response.getEntity());
    }

    @Test
    public void testAddContactsWithoutAuthorisationReturnUnauthorisedResponse() {
        Contact contact = new Contact("Sangeet", 9998887775L, "Ranchi", "8th May", "sangeet@email.com");
        Response response = target("/api/contacts").request().post(Entity.entity(contact, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(401, response.getStatus());
        assertEquals("Not authorized", response.getEntity());
    }

    @Test
    public void testAddContactsWithAuthorisationReturnContacts() {
        Contact contact = new Contact("Sangeet", 9998887775L, "Ranchi", "8th May", "sangeet@email.com");
        Response response = target("/api/contacts").request().header("Authorization", "Basic dXNlcjp1c2Vy").post(Entity.entity(contact, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(204, response.getStatus());
        assertEquals(contact, response.getEntity());
    }

    @Test
    public void testDeleteContactWithAuthorisationReturnsSuccessResponse() {
        Response response = target("/api/contacts/9998887775").request().header("Authorization", "Basic dXNlcjp1c2Vy").delete();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testDeleteContactWithAuthorisationReturnsBadRequestResponse() {
        Response response = target("/api/contacts/999888775").request().header("Authorization", "Basic dXNlcjp1c2Vy").delete();

        assertEquals(400, response.getStatus());
        assertEquals("No such contact", response.getEntity());
    }

    @Test
    public void testDeleteContactWithOutAuthorisationReturnsUnAuthorizedResponse() {
        Response response = target("/api/contacts/9998887775").request().delete();

        assertEquals(401, response.getStatus());
        assertEquals("Not authorized", response.getEntity());
    }
}
