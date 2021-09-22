package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n제목 > ");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("제목 중복!!");
			return;
		}
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("추가 완료!");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n삭제할 항목의 제목을 입력하시오 > ");
		String title = sc.nextLine();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n수정할 항목의 제목을 입력하시오 > ");
		String title = sc.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("없는 제목!!!");
			return;
		}

		System.out.print("새 제목 > ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됨!");
			return;
		}
		
		System.out.print("새 내용 > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록]");
		for (TodoItem item : l.getList()) {
			System.out.print(item.toString());
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
	
	public static void loadList(TodoList l,String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			
			while((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String date = st.nextToken();
				
				TodoItem t = new TodoItem(title,desc);
				t.setCurrent_date(date);
				l.addItem(t);
			}
			br.close();
			System.out.println("파일 읽기 완료.");
		} catch (FileNotFoundException e) {
			System.out.println("파일 못찾음.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
