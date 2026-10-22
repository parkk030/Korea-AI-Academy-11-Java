package com.korai.study.Q;

public class PracticeAccess {
    public static void main(String[] args) {
        // 아래 main 은 수정하지 말고, 안전계좌/점수 클래스를 완성하세요.

        // [문제 3-1]
        안전계좌 a = new 안전계좌("김준일", 5000);
        a.입금(-1000);
        a.출금(10000);
        boolean result = a.입금(3000);
        System.out.println(a.getOwner() + " 잔액: " + a.getBalance());
        System.out.println("입금 결과: " + result);

        // a.잔액 = -100000;   // 완성 후 주석을 풀어 보세요. 컴파일 에러가 나야 정답!

        // [문제 3-2]
        점수 s = new 점수();
        s.setScore(90);
        s.setScore(150);
        System.out.println("점수: " + s.getScore());
    }
}

class 안전계좌 {
    // TODO: 접근 제어자를 알맞게 바꾸세요.
    private String 예금주;
    private int 잔액;

    안전계좌(String 예금주, int 잔액) {
        this.예금주 = 예금주;
        this.잔액 = 잔액;
    }



    // TODO: getOwner(), getBalance() Getter 를 만드세요.
    public String getOwner() {
        return 예금주;
    }

    public int getBalance() {
        return 잔액;
    }

    public boolean 입금(int 금액) {
        // TODO: 0 이하이면 "입금 실패: 잘못된 금액" 출력 후 false
        if(금액<=0){
            System.out.println("입금 실패 : 잘못된 금액");
            return false;
        }
        잔액 += 금액;
        return true;
    }

    boolean 출금(int 금액) {
        // TODO: 잔액보다 많으면 "출금 실패: 잔액 부족" 출력 후 false
        if(잔액<금액){
            System.out.println("출금 실패: 잔액 부족");
            return false;
        }
        잔액 -= 금액;
        return true;
    }
}

class 점수 {
    private int score;

    void setScore(int score) {
        // TODO: 0~100 이 아니면 "잘못된 점수" 출력 후 값을 바꾸지 않기
        if(score<0 || score>100){
            System.out.println("잘못된 점수");
        }
        else{
            this.score += score;
        }
    }

    int getScore() {
        // TODO
        return score;
    }
}
