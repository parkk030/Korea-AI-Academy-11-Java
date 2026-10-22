package com.korai.study.Q;

// 연습문제: exercises/ch03_static.md 의 "4. 코드 작성" - 영화관 예매 시스템
public class PracticeStatic {
    public static void main(String[] args) {
        // 아래 main 은 수정하지 말고, 티켓/영화관 클래스를 완성하세요.
        System.out.println("main 시작");
        티켓 t1 = 영화관.예매("인터스텔라", 25);
        티켓 t2 = 영화관.예매("인터스텔라", 30);
        티켓 t3 = 영화관.예매("인터스텔라", 15);
        티켓 t4 = 영화관.예매("인터스텔라", 40);
        System.out.println(t1.좌석번호 + "번 좌석 " + t1.영화제목 + " " + t1.가격 + "원");
        System.out.println(t3.좌석번호 + "번 좌석 " + t3.영화제목 + " " + t3.가격 + "원");
        System.out.println("남은 좌석: " + 영화관.남은좌석);
        System.out.println("총매출: " + 영화관.총매출);
        if (t4 == null) {
            System.out.println("t4는 예매 실패");
        }
    }
}

class 티켓 {
    int 좌석번호;
    String 영화제목;
    int 가격;

    티켓(int 좌석번호, String 영화제목, int 가격) {
        // TODO: "티켓 생성자 호출" 출력 후, this 를 사용해 세 필드에 값을 저장하세요.
        this.좌석번호=좌석번호;
        this.영화제목=영화제목;
        this.가격=가격;
    }
}

class 영화관 {
    static int 좌석번호 = 1;
    static int 남은좌석 = 3;
    static int 총매출;


    // TODO: static 블록에서 "영화관 오픈" 을 출력하세요.
    static{
        System.out.println("영화관 오픈");
    }

    static int 가격계산(int 나이) {
        // TODO: 20세 미만 8000, 그 외 12000
        if(나이<20){
        return 8000;
        }
        else{
            return 12000;
        }

    }

    static 티켓 예매(String 영화제목, int 나이) {
        // TODO 1: 남은좌석이 0이면 "<영화제목> 매진" 출력 후 null 반환
        // TODO 2: 남은좌석 1 감소, 총매출에 가격 추가
        // TODO 3: 좌석번호를 1씩 증가시키며 새 티켓을 만들어 반환
        if(남은좌석==0){
            System.out.println("<영화제목> 매진");
            return null;
        }
        남은좌석--;
        총매출 += 가격계산(나이);

        return new 티켓(좌석번호++, 영화제목, 가격계산(나이));
    }
}
