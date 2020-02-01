package com.todoapp.api;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.todoapp.bean.Category;
import com.todoapp.bean.User;
import com.todoapp.resources.DBInfo;
import com.todoapp.resources.Utility;

@Path("/category")
public class CategoryResource {

	@POST
	@Path("/addCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTask(@HeaderParam("authToken") String authToken, Category category)
	{
		User user = Utility.validateUser(authToken);
		
		if(user.getUsername() == null)
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else
		{
			String query = "insert into categories(category_name,username) values(?,?)";
			int isAdded = 0;
			try
			{
				Connection con=DBInfo.getConn();	
				PreparedStatement ps=con.prepareStatement(query);
				ps.setString(1, category.getCategoryName());
				ps.setString(2, user.getUsername());
				
				isAdded = ps.executeUpdate();
				
				con.close();
			}
			catch(MySQLIntegrityConstraintViolationException cve)
			{
				isAdded = 2;		//dbResponse = 2 : category already exists FAILURE
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
			
			return Response.status(200).entity(isAdded).build();
		}
	}
	
	
	@DELETE
	@Path("/deleteCategory/{categoryId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTask(@HeaderParam("authToken") String authToken, @PathParam("categoryId") int categoryId)
	{
		User user = Utility.validateUser(authToken);
		
		if(user.getUsername() == null)
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else
		{
			String query = "delete from categories where category_id=?";
			int isDeleted = 0;
			try
			{
				Connection con=DBInfo.getConn();	
				PreparedStatement ps=con.prepareStatement(query);
				ps.setInt(1, categoryId);
				
				isDeleted = ps.executeUpdate();
				
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
			
			return Response.status(200).entity(isDeleted).build();
		}
	}
	
	
	@PUT
	@Path("/updateCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTask(@HeaderParam("authToken") String authToken, Category category)
	{
		User user = Utility.validateUser(authToken);
		
		if(user.getUsername() == null)
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else
		{
			String query = "update categories set category_name=? where category_id=?";
			int isUpdated = 0;
			try
			{
				Connection con=DBInfo.getConn();	
				PreparedStatement ps=con.prepareStatement(query);
				ps.setString(1, category.getCategoryName());
				ps.setInt(2, category.getCategoryId());
				
				isUpdated = ps.executeUpdate();
				
				con.close();
			}
			catch(MySQLIntegrityConstraintViolationException cve)
			{
				isUpdated = 2;		//dbResponse = 2 : category already exists FAILURE
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
			
			return Response.status(200).entity(isUpdated).build();
		}
	}
}
