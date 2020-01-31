package com.todoapp.bean;

public class Category {
	private int categoryId;
	private String categoryName;
	private int taskCount;
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getTaskCount() {
		return taskCount;
	}
	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}
	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + ", taskCount=" + taskCount
				+ "]";
	}
	public Category(int categoryId, String categoryName, int taskCount) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.taskCount = taskCount;
	}
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
