package util;

import java.sql.Connection;

public class DBtest {
    // 코드가 실행될 수 있도록 main 메서드 안에 넣어주어야 합니다.
    public static void main(String[] args) {
        
        // 1. DBManager.getInstance() 형태로 괄호() 없이 클래스명으로 직접 호출합니다.
        Connection conn = DBManager.getInstance();
        
        if (conn != null) {
            System.out.println("접속완료");
        } else {
            System.out.println("실패");
        }
    }
}
