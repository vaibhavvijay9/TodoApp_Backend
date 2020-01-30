package com.todoapp.bean;

import java.util.ArrayList;

import com.todoapp.bean.Task;
import com.todoapp.bean.User;

public class ResponseBean {
	private String message;
	private String token;
	private ArrayList<Task> tasks;
	private User user;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ResponseBean() {
		super();
	}
	public ResponseBean(String message, String token, ArrayList<Task> tasks, User user) {
		super();
		this.message = message;
		this.token = token;
		this.tasks = tasks;
		this.user = user;
	}
}
