package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import com.todo.db.DbConnect;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n카테고리 > ");
		category = sc.nextLine();

		System.out.print("제목 > ");
		title = sc.nextLine();
		if (list.isDuplicate(title.trim())) {
			System.out.printf("제목 중복!!");
			return;
		}
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감 > ");
		due_date = sc.nextLine();

		int star;
		System.out.print("중요도 > ");
		star = sc.nextInt();
		if(star <= 0 || star >5)
			star=1;
		
		TodoItem t = new TodoItem(category, title, desc, due_date, star); // 오버라이드한 컨스트럭터
		if (list.addItem(t) > 0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n삭제할 항목의 번호를 입력하시오 > ");
		String choices = sc.nextLine();
		String splitTest[] = choices.split(" ");

		for (int i=0; i<splitTest.length; i++)
		{
			int n = Integer.parseInt(splitTest[i]); // 번호			
			if (l.deleteItem(n) > 0)
				System.out.println(n + "번 삭제되었습니다.");
			else
				System.out.println("삭제 오류!");
		}		
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n수정할 항목의 번호를 입력하시오 > ");
		int n = sc.nextInt();
		sc.nextLine();

		TodoItem temp = l.getList().get(n-1); // target
		//System.out.println(temp.toString());
		
		System.out.print("새 제목 > ");
		String new_title = sc.nextLine();

		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됨!");
			return;
		}
		
		System.out.print("새 카테고리 > ");
		String new_category = sc.nextLine();
		
		System.out.print("새 내용 > ");
		String new_description = sc.nextLine().trim();
		
		System.out.print("새 마감일자 > ");
		String new_due_date = sc.nextLine();

		System.out.print("새 중요도 > ");
		int new_star = sc.nextInt();
		if (new_star <= 0 || new_star > 5) // exception
			new_star=1;

		//l.deleteItem(temp);
		
		TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date, new_star);
		t.setId(n);
		if (l.updateItem(t) > 0)
			System.out.println("수정되었습니다");
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("[전체 목록, 총 " + l.getList().size() + "개]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.print(item.toString());
		}
	}
	
	public static void listAll(int num)
	{
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		int ct=0;
		if (num == 1)
		{
			Statement stmt;
			Connection conn = DbConnect.getConnection();
			
			try {
				stmt = conn.createStatement();
				String sql = "SELECT * FROM TodoList";
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
		            int id = rs.getInt("id");
		            String category = rs.getString("category");
		            String title = rs.getString("title");
		            String description = rs.getString("description");
		            String due = rs.getString("due");
		            String current_date = rs.getString("current_date");
		            int is_completed = rs.getInt("is_completed");
		            int star = rs.getInt("star");
		            int doing = rs.getInt("doing");
		            
		            TodoItem t = new TodoItem(id, category, title, description, due, current_date, is_completed, star, doing);
		            list.add(t);
		            ct++;
				}			
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.printf("[전체 목록 총 %d개]\n",ct);
			for (TodoItem item : list)
				System.out.print(item.toString());
		}
	}
	

	public static void listAllCategory(TodoList l) {		
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.println(item + " ");
			count++;
		}
		
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}
	
	public static void findKeyWord(TodoList l, String[] keyWord)
	{	
		int count=0;
		int len = keyWord.length;
		for (int i=1; i<len; i++)
		{
			for (TodoItem item : l.getList(keyWord[i]))
			{
				System.out.print(item.toString());
				count++;
			}
		}
		/*
		for (TodoItem item : l.getList(keyWord))
		{
			System.out.println(item.toString());
			count++;
		}*/
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void findCate(TodoList l, String[] keyWord)
	{
		int count=0;
		int len=keyWord.length;
		for (int i=1; i<len; i++)
		{
			for (TodoItem item : l.getListCategory(keyWord[i])) {
				System.out.print(item.toString());
				count++;
			}			
		}
		
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void completeCheck(TodoList l, String[] keyWord)
	{
		int len = keyWord.length;
		for (int i=1; i<len; i++)
		{
			int num = Integer.parseInt(keyWord[i]); // 번호
			l.checkItem(num);
			System.out.println(num + "번 완료 체크하였습니다.");
		}
	}
	
	public static void doingCheck(TodoList l, String[] keyWord)
	{
		int len = keyWord.length;
		for (int i=1; i<len; i++)
		{
			int num = Integer.parseInt(keyWord[i]); // 번호
			l.checkDoingItem(num);
			System.out.println(num + "번 진행중 체크하였습니다.");
		}
	}
	
	public static void saveList(TodoList l,String filename) {
		try {
			Writer w = new FileWriter(filename);
			
			for(TodoItem item:l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.print("저장 완료.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void completeItem(TodoList l)
	{
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		Connection conn = DbConnect.getConnection();
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM TodoList WHERE is_completed=1";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
	            int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("description");
	            String due = rs.getString("due");
	            String current_date = rs.getString("current_date");
	            int is_completed = rs.getInt("is_completed");
	            int star = rs.getInt("star");
	            int doing = rs.getInt("doing");
	            
	            TodoItem t = new TodoItem(id, category, title, description, due, current_date, is_completed, star, doing);
	            list.add(t);
			}			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int ct=0;
		
		for (TodoItem item : list)
		{
			System.out.print(item.toString());
			ct++;
		}
		System.out.printf("총 %d개의 항목이 완료되었습니다\n",ct);
	}
	
	public static void doingItem(TodoList l)
	{
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		Connection conn = DbConnect.getConnection();
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM TodoList WHERE doing=1";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
	            int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("description");
	            String due = rs.getString("due");
	            String current_date = rs.getString("current_date");
	            int is_completed = rs.getInt("is_completed");
	            int star = rs.getInt("star");
	            int doing = rs.getInt("doing");
	            
	            TodoItem t = new TodoItem(id, category, title, description, due, current_date, is_completed, star, doing);
	            list.add(t);
			}			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int ct=0;
		
		for (TodoItem item : list)
		{
			System.out.print(item.toString());
			ct++;
		}
		System.out.printf("총 %d개의 항목이 완료되었습니다\n",ct);
	}
	
	public static ArrayList<TodoItem> loadList(TodoList l,String filename) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		Connection conn = DbConnect.getConnection();
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM TodoList";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
	            int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("description");
	            String due = rs.getString("due");
	            String current_date = rs.getString("current_date");
	            int is_completed = rs.getInt("is_completed");
	            int star = rs.getInt("star"); // 중요도
	            int doing = rs.getInt("doing"); // 진행중
	            
	            TodoItem t = new TodoItem(id, category, title, description, due, current_date, is_completed, star, doing);
	            list.add(t);
			}			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
