package com.pl.project.resources;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class CustomHeaders implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext
            containerResponseContext) throws IOException {
        String origin = containerRequestContext.getHeaderString("origin");
        if (origin != null)
            containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
        else
            containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        containerResponseContext.getHeaders().add("Access-Control-Expose-Headers", "Location");

        containerRequestContext.getHeaders().add("Access-Control-Request-Headers", "origin, content-type, accept, authorization");
    }
}



