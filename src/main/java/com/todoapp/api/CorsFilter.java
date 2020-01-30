package com.todoapp.api;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MultivaluedMap;

@Provider
public class CorsFilter implements ContainerResponseFilter 
{
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException 
    {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        headers.add("Access-Control-Allow-Origin", "*");        
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        // value of token is set in 'authToken' variable at front-end, it can be any name.
        headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authToken");
        headers.add("Access-Control-Allow-Credentials", "true");
    }
}