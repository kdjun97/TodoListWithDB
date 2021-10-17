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
        System.out.println("[ls_name]       : 리스트를 이름순으로 정렬(오름차순)");
        System.out.println("[ls_name_desc]  : 리스트를 이름순으로 정렬(내림차순)");
        System.out.println("[ls_date]       : 리스트를 마감날짜순으로 정렬(오름차순)");
        System.out.println("[ls_date_desc]  : 리스트를 마감날짜순으로 정렬(내림차순)");
        System.out.println("[ls_star]       : 리스트를 중요도순으로 정렬(오름차순)");
        System.out.println("[ls_star_desc]  : 리스트를 중요도순으로 정렬(내림차순)");        
        System.out.println("[ls_doing]      : 현재 진행중인 할 일 리스트");        
        System.out.println("[find <keyword>]: 제목, 설명을 찾아줌");
        System.out.println("[find_cate]     : 카테고리를 찾아줌");
        System.out.println("[comp <번호>]    : 항목 완료 체크하기");
        System.out.println("[doing <번호>]   : 항목 진행중 체크하기");
        System.out.println("[ls_comp]       : 완료된 항목만 출력하기");
        System.out.println("[exit]          : 나가기");
        System.out.println("********************************************");
        System.out.println("Enter your choice >");
    }
    
    public static void prompt()
    {
    	System.out.print("\nCommand > ");
    }
}
