package com.korai.study.ch02;

import java.time.LocalDate;

public class StaticBasic {

    public static void main(String[] args) {
        //학생관리시스템 system = new 학생관리시스템();
        학생 s1 = 학생관리시스템.학생추가("가나다");
        //학생 s2 = system.학생추가("가나다라");
        학생 s2 = 학생관리시스템.학생추가("가나다라");
        //학생 s3 = system.학생추가("가나다라마");
        학생 s3 = 학생관리시스템.학생추가("가나다라마");
        학생 s4 = new 학생(123,"rks");
        System.out.println(s3.이름+"/"+s4.이름);
    }
}

class 학생 {

    int 학번;
    String 이름;

    학생(int 학번, String 이름) { //클래스명과 일치하는 함수정의 --> 생성자
        System.out.println("생성자 호출");
        this.이름 = 이름;
        this.학번 = 학번;
    }

}

class 학생관리시스템 {
    static int 년도 = LocalDate.now().getYear();
    static int 번호 = 1;

    static {
        System.out.println("학생관리시스템 클래스 로딩");
    }

    static 학생 학생추가(String 이름) {

        return new 학생(년도 * 10000 + 번호++, 이름);

    }

}