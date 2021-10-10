package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int is_completed;


    public TodoItem(String title, String desc){
    	this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
    }
    
    public TodoItem(String category, String title, String desc, String due_date) // 오버로드
    {
    	this.category=category;
    	this.title=title;
    	this.desc=desc;
    	this.due_date=due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
        this.is_completed=0; // default
    }
    
    public TodoItem(int id, String category, String title, String desc, String due_date, String current_date, int is_completed) // 오버로드
    {
    	this.id = id;
    	this.category=category;
    	this.title=title;
    	this.desc=desc;
    	this.due_date=due_date;
        this.current_date= current_date;
        this.is_completed=is_completed;
    }
    
    public int getIsCompleted() {
    	return this.is_completed;
    }

    public void setIsCompleted(int temp) {
    	this.is_completed = temp;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int temp) {
        this.id = temp;
    }
    
    public String getCategory() {
        return this.category;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getCate() {
    	return this.category;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return this.current_date;
    }
    
    public String getDue_date() {
        return this.due_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    public String toSaveString() {
    	return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "\n";
    }
    
    @Override
    public String toString() {
    	String temp="";
    	if (this.is_completed == 1)
    		temp = this.getId() + ". " + "[" + this.getCategory() + "] " + this.getTitle() + "[V]"+ " - " + this.getDesc() + " - " + this.getDue_date() + " - " + this.getCurrent_date() + "\n";
    	else
    		temp = this.getId() + ". " + "[" + this.getCategory() + "] " + this.getTitle() + " - " + this.getDesc() + " - " + this.getDue_date() + " - " + this.getCurrent_date() + "\n";
    	return temp;
    }
}
