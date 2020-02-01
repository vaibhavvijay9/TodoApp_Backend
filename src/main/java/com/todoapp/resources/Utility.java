package com.todoapp.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.todoapp.bean.Task;
import com.todoapp.bean.User;
import com.todoapp.resources.DBInfo;

public class Utility {
	// method
	public static User validateUser(String token)
	{
		User user = new User(); 
		
		String query="select username,name from users where username = (select username from sessions where token= ?)";
		try
		{
			Connection con=DBInfo.getConn();	
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, token);
			
			ResultSet res = ps.executeQuery();
			
			while(res.next())
			{
				user.setUsername(res.getString(1));
				user.setName(res.getString(2));
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
		return user;
	}
}
