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
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n카테고리 > ");
		category = sc.nextLine();

		System.out.print("제목 > ");
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("제목 중복!!");
			return;
		}
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감 > ");
		due_date = sc.nextLine();
		
		TodoItem t = new TodoItem(category, title, desc, due_date); // 오버라이드한 컨스트럭터
		list.addItem(t);
		System.out.println("추가 완료!");
	}

	public static void deleteItem(TodoList l) {
		String choice;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n삭제할 항목의 번호를 입력하시오 > ");
//		String title = sc.nextLine();
		int n = sc.nextInt();
		TodoItem temp = l.getList().get(n-1); // target
		System.out.println(n + ". " + temp.toString());
		System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ");
		choice = sc.next();
		
		if (choice.equals("y"))
		{
			l.deleteItem(temp);
			System.out.println("삭제되었습니다.");			
		}
		else
			System.out.println("취소하였습니다");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n수정할 항목의 번호를 입력하시오 > ");
		int n = sc.nextInt();
		sc.nextLine();

//		String title = sc.nextLine().trim();
		TodoItem temp = l.getList().get(n-1); // target
		System.out.println(n + ". " + temp.toString());
		
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
		
		l.deleteItem(temp);
		l.addItem(new TodoItem(new_category, new_title, new_description, new_due_date));
		System.out.println("수정되었습니다");
	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 " + l.getList().size() + "개]");
		int count=0;
		for (TodoItem item : l.getList()) {
			System.out.print(++count + ". " + item.toString());
		}
	}
	
	public static void listAllCategory(TodoList l) {
		HashSet<String> temp = new HashSet<String>();
		
		for (TodoItem item : l.getList()) 
			temp.add(item.getCate());
		
		Iterator<String> iter = temp.iterator(); 
		int count = 0;
		
		while(iter.hasNext()){
            System.out.print(iter.next());
            if (++count != temp.size())
            	System.out.print(" / ");
        }
		
		System.out.println("\n총 " + temp.size() + "개의 카테고리가 등록되어 있습니다.");
	}
	
	public static void findKeyWord(TodoList l, String keyWord)
	{	
		int num=0;
		int count=0;
		for (TodoItem item : l.getList())
		{
			num++;
			if (item.getTitle().contains(keyWord) || item.getDesc().contains(keyWord))
			{
				System.out.print(num + ". " + item.toString());				
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void findCate(TodoList l, String keyWord)
	{
		int num=0;
		int count=0;
		for (TodoItem item : l.getList())
		{
			num++;
			if (item.getCate().contains(keyWord)) // 카테고리 존재하면
			{
				System.out.print(num + ". " + item.toString());				
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
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
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String date = st.nextToken();
				
				TodoItem t = new TodoItem(category,title,desc,due_date);
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
