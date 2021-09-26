package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("********************************************");
        System.out.println("명령어를 입력하세요(괄호 안의 명령어)");
        System.out.println("[add]           : 아이템 추가");
        System.out.println("[del]           : 존재하는 아이템 삭제");
        System.out.println("[edit]          : 아이템 업데이트");
        System.out.println("[ls]            : 모든 아이템 목록 보기");
        System.out.println("[ls_cate]       : 모든 카테고리 목록 보기");
        System.out.println("[ls_name_asc]   : 리스트를 이름순으로 정렬(오름차순)");
        System.out.println("[ls_name_desc]  : 리스트를 이름순으로 정렬(내림차순)");
        System.out.println("[ls_date]       : 리스트를 날짜순으로 정렬(오름차순)");
        System.out.println("[ls_date_desc]  : 리스트를 날짜순으로 정렬(내림차순)");
        System.out.println("[find]          : 제목, 설명을 찾아줌");
        System.out.println("[find_cate]     : 카테고리를 찾아줌");
        System.out.println("[exit]          : 나가기");
        System.out.println("********************************************");
        System.out.println("Enter your choice >");
    }
    
    public static void prompt()
    {
    	System.out.print("\nCommand > ");
    }
}
