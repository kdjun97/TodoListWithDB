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
    private int is_completed; // 1=true
    private int star; // 중요도 (별점) 1~5 , 5점이 제일 중요한거
    private int doing; // 진행중인 할 일 / 1=true


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
        this.doing=0; // default
    }
    
    public TodoItem(String category, String title, String desc, String due_date, int star) // 오버로드
    {
    	this.category=category;
    	this.title=title;
    	this.desc=desc;
    	this.due_date=due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
        this.is_completed=0; // default
        this.star=star;
        this.doing=0; // default
    }
    
    public TodoItem(int id, String category, String title, String desc, String due_date, String current_date, int is_completed, int star, int doing) // 오버로드
    {
    	this.id = id;
    	this.category=category;
    	this.title=title;
    	this.desc=desc;
    	this.due_date=due_date;
        this.current_date= current_date;
        this.is_completed=is_completed;
        this.star=star;
        this.doing=doing;
    }
    
    public int getIsCompleted() {
    	return this.is_completed;
    }

    public void setIsCompleted(int temp) {
    	this.is_completed = temp;
    }
    
    public int getDoing() {
    	return this.doing;
    }
    
    public void setDoing(int temp) {
    	this.doing = temp;
    }
    
    public int getStar() {
    	return this.star;
    }
    
    public void setStar(int temp) {
    	this.star = temp;
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
    	String starString="";
    	String completeString="";
    	String doingString="";
    	
    	switch(this.getStar()) 
    	{
    		case 1:
    			starString="★☆☆☆☆";
    			break;
    		case 2:
    			starString="★★☆☆☆";
    			break;
    		case 3:
    			starString="★★★☆☆";
    			break;
    		case 4:
    			starString="★★★★☆";
    			break;
    		case 5:
    			starString="★★★★★";
    			break;
    		default:
    			starString="Error!";
    			break;
    	}
    	
    	if (this.is_completed == 1)
    		completeString = "[V]";
    	else
    		completeString = "";
    	
    	if (this.doing == 1)
    		doingString = "[…]";
    	else
    		doingString = "";
    	
    	return this.getId() + ". " + "[" + this.getCategory() + "] " + this.getTitle() + completeString + doingString + " - " + starString + " - " + this.getDesc() + " - " + this.getDue_date() + " - " + this.getCurrent_date() + "\n";
    }
}
