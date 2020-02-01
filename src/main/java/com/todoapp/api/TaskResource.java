package com.todoapp.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.todoapp.bean.Category;
import com.todoapp.bean.Task;
import com.todoapp.bean.User;
import com.todoapp.resources.DBInfo;
import com.todoapp.resources.Utility;

@Path("/task")
public class TaskResource 
{
	@GET
	@Path("/dashboard")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTasks(@HeaderParam("authToken") String authToken)
	{
		User user = Utility.validateUser(authToken);
		
		if(user.getUsername() == null)
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else
		{
			ArrayList<Category> dashboard = new ArrayList<Category>();
			
//			String query = "select c.category_id,c.category_name,count(*) from tasks t, categories c where t.category_id = c.category_id AND t.username=? group by c.category_id;";
			String query = "SELECT c.category_id,c.category_name,count(t.task_id) as task_count FROM categories c LEFT join tasks t ON c.category_id=t.category_id where c.username=? GROUP by c.category_id";
			try
			{
				Connection con=DBInfo.getConn();	
				PreparedStatement ps=con.prepareStatement(query);
				ps.setString(1, user.getUsername());
				
				ResultSet res = ps.executeQuery();
				
				while(res.next())
				{
					Category dashboardData = new Category();
					dashboardData.setCategoryId(res.getInt(1));
					dashboardData.setCategoryName(res.getString(2));
					dashboardData.setTaskCount(res.getInt(3));
					
					dashboard.add(dashboardData);
				}
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
			return Response.status(200).entity(dashboard).build();
		}
	}
	
	
	@GET
	@Path("getTask/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTasks(@HeaderParam("authToken") String authToken, @PathParam("param") Integer param)
	{
		User user = Utility.validateUser(authToken);
		
		if(user.getUsername() == null)
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else
		{	
			ArrayList<Task> tasks = new ArrayList<Task>();
			String query = "select t.*,c.* from tasks t, categories c where t.username=? and t.category_id = ? and t.category_id = c.category_id;";
			
			try
			{
				Connection con=DBInfo.getConn();	
				PreparedStatement ps=con.prepareStatement(query);
				ps.setString(1, user.getUsername());
				ps.setInt(2, param);
				
				ResultSet res = ps.executeQuery();
				
				while(res.next())
				{
					Task task = new Task();
					task.setTaskId(res.getInt(1));
					task.setTaskDescription(res.getString(2));
					task.setTaskDate(res.getDate(3));
					task.setCompleted(res.getBoolean(4));
					task.setCategoryName(res.getString(8));
					
					tasks.add(task);
				}
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
						
			return Response.status(200).entity(tasks).build();
		}
	}
	
	@POST
	@Path("/addTask")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTask(@HeaderParam("authToken") String authToken, Task task)
	{
		User user = Utility.validateUser(authToken);
		
		if(user.getUsername() == null)
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else
		{
			String query = "insert into tasks(task_description,task_date,is_completed,username,category_id) values(?,?,?,?,?);";
			int isAdded = 0;
			try
			{
				Connection con=DBInfo.getConn();	
				PreparedStatement ps=con.prepareStatement(query);
				ps.setString(1, task.getTaskDescription());
				ps.setDate(2, task.getTaskDate());
				ps.setBoolean(3, false);
				ps.setString(4, user.getUsername());
				ps.setInt(5, task.getCategoryId());
				
				isAdded = ps.executeUpdate();
				
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
			
			return Response.status(200).entity(isAdded).build();
		}
	}
	
	@PUT
	@Path("/updateTask")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTask(@HeaderParam("authToken") String authToken, Task task)
	{
		User user = Utility.validateUser(authToken);
		
		if(user.getUsername() == null)
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else
		{
			String query = "update tasks set task_description=?,task_date=? where task_id=?";
			int isUpdated = 0;
			try
			{
				Connection con=DBInfo.getConn();	
				PreparedStatement ps=con.prepareStatement(query);
				ps.setString(1, task.getTaskDescription());
				ps.setDate(2, task.getTaskDate());
				ps.setInt(3, task.getTaskId());
				
				isUpdated = ps.executeUpdate();
				
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
			
			return Response.status(200).entity(isUpdated).build();
		}
	}
	
	@DELETE
	@Path("/deleteTask/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTask(@HeaderParam("authToken") String authToken, @PathParam("taskId") int taskId)
	{
		User user = Utility.validateUser(authToken);
		
		if(user.getUsername() == null)
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else
		{
			String query = "delete from tasks where task_id=?";
			int isDeleted = 0;
			try
			{
				Connection con=DBInfo.getConn();	
				PreparedStatement ps=con.prepareStatement(query);
				ps.setInt(1, taskId);
				
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
	@Path("/updateTaskStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTaskStatus(@HeaderParam("authToken") String authToken, Task task)
	{
		User user = Utility.validateUser(authToken);
		
		if(user.getUsername() == null)
		{
			return Response.status(Status.UNAUTHORIZED).build();
		}
		else
		{
			String query = "update tasks set is_completed=? where task_id=?";
			int isUpdated = 0;
			try
			{
				Connection con=DBInfo.getConn();	
				PreparedStatement ps=con.prepareStatement(query);
				ps.setBoolean(1, !task.isCompleted());
				ps.setInt(2, task.getTaskId());
				
				isUpdated = ps.executeUpdate();
				
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
			
			return Response.status(200).entity(isUpdated).build();
		}
	}
}