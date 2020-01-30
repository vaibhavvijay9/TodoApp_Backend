package com.todoapp.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/hello")
public class Demo {
	
	@GET
	public Response getMsg()
	{
		String message = "Hey, Welcome to Todo App!";
		
		return Response.status(200).entity(message).build();
	}
}
