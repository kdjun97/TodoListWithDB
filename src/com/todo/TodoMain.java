package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;


public class TodoMain {
	
	public static void start() {
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
//		l.importData("todolist.txt");
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "mydb.db");
		Menu.displaymenu();
		
		do {
			Menu.prompt();
			isList = false;
			String choices = sc.nextLine();
			String choice = choices.split(" ")[0];
			String splitTest[] = choices.split(" ");

			/*
			System.out.println("********");
			for (int i=0; i<splitTest.length; i++)
				System.out.println(splitTest[i]);
			System.out.println("********");
			*/

			
			switch (choice) {
			case "help": // help 명령어 시 display menu 기능 호출
				Menu.displaymenu();
				break;

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(1);
				break;
				
			case "ls_cate":
				TodoUtil.listAllCategory(l);
				break;

			case "ls_name":
				System.out.println("제목순으로 정렬완료! (오름차순)");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목순으로 정렬완료! (내림차순)");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬완료! (오름차순)");
				TodoUtil.listAll(l, "due", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜순으로 정렬완료! (내림차순)");
				TodoUtil.listAll(l, "due", 0);
				break;
				
			case "ls_star":
				System.out.println("중요도 순으로 정렬완료! (오름차순)");
				TodoUtil.listAll(l, "star", 0);
				break; 
				
			case "ls_star_desc":
				System.out.println("중요도 순으로 정렬완료! (내림차순)");
				TodoUtil.listAll(l, "star", 1);
				break; 
				
			case "find":
				TodoUtil.findKeyWord(l, splitTest); 
				break;
				
			case "find_cate":
				TodoUtil.findCate(l, splitTest);
				break;
				
			case "comp":
				TodoUtil.completeCheck(l, splitTest);
				break;

			case "ls_comp":
				TodoUtil.completeItem(l);
				break;
				
			case "doing":
				TodoUtil.doingCheck(l, splitTest);
				break;

			case "ls_doing":
				TodoUtil.doingItem(l);
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("명령어 오류. [도움말 명령어: help]");
				break;
			}
			
			if(isList) l.listAll(l);
		} while (!quit);
		TodoUtil.saveList(l, "todolist.txt");
	}
	
	
}
