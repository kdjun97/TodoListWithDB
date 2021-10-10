package com.todo.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;


import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;
import com.todo.db.DbConnect;

public class TodoList {
	Connection conn;
	
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection();
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM TodoList";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
	            String c = rs.getString("category");
	            list.add(c);
			}			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyWord)
	{
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM TodoList WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,keyWord);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("description");
	            String due = rs.getString("due");
	            String current_date = rs.getString("current_date");
	            TodoItem t = new TodoItem(category,title, description, due);
	            t.setId(id);
	            t.setCurrent_date(current_date);
	            list.add(t);
			}			
			pstmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM TodoList ORDER BY " + orderby;
			if (ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("description");
	            String due = rs.getString("due");
	            String current_date = rs.getString("current_date");
	            TodoItem t = new TodoItem(category,title, description, due);
	            t.setId(id);
	            t.setCurrent_date(current_date);
	            list.add(t);
			}			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyWord)
	{
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyWord = "%"+keyWord+"%";
		try {
			String sql = "SELECT * FROM TodoList WHERE title LIKE ? or description LIKE ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyWord);
			pstmt.setString(2, keyWord);
			ResultSet rs = pstmt.executeQuery();		
			
			while(rs.next())
			{
				int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("description");
	            String due = rs.getString("due");
	            String current_date = rs.getString("current_date");
	            TodoItem t = new TodoItem(category,title, description, due);
	            t.setId(id);
	            t.setCurrent_date(current_date);
	            list.add(t);
			}			
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int addItem(TodoItem t) {
		String sql = "INSERT INTO TodoList (title, description, category, current_date, due)"
					+ " VALUES (?,?,?,?,?);";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			count = pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
		//list.add(t);
	}

	public void deleteItem(TodoItem t) {
		
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM TodoList";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("description");
				String due = rs.getString("due");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(title, description, category, due);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		//return new ArrayList<TodoItem>(list);
	}
	
	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM TodoList;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]",l.getCount());
		for (TodoItem item : l.getList())
			System.out.println(item.toString());
	}
	
	public void importData(String filename)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "INSERT INTO TodoList (title, description, category, due, current_date)"
					+ " VALUES (?,?,?,?,?);";
			int records = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,  title);
				pstmt.setString(2,  description);
				pstmt.setString(3,  category);
				pstmt.setString(4,  current_date);
				pstmt.setString(5,  due);
				int count = pstmt.executeUpdate();
				if (count > 0) records++;
				pstmt.close();
				
			}
			
			System.out.println(records + " records read!!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		String sql = "SELECT * FROM TodoList WHERE title=?;";
		int count = 0;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,title);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				pstmt.close();			
				return true;
			}

			pstmt.close();			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int deleteItem(int index) {
		String sql = "DELETE FROM TodoList WHERE id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int updateItem(TodoItem t)
	{
		String sql = "UPDATE TodoList SET title=?, description=?, category=?, current_date=?, due=?"
					+ " WHERE id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
