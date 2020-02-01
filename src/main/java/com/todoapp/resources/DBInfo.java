package com.todoapp.resources;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBInfo {
	public static Connection con;
	
	static
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConn()
	{
		try
		{
			con=DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/q81zCjWlOo","q81zCjWlOo","KyC71cdmR2");	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	
	public static void close()
	{
		try
		{
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
