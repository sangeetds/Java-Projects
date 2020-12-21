package com.cognitree.sangeet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class Contacts {
    @GET
    public String getC() {
        return "Hello";
    }
}
