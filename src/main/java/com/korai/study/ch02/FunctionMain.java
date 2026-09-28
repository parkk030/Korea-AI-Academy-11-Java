package com.korai.study.ch02;

public class FunctionMain {



    public static void main(String[] args) {
        // 함수 - 반복적인 작업을 다시 사용할 수 있도록 정의(도구제작)
        // 1. 재사용성
        // 2. 정리
        // 3. 클래스 안에 정의

        class 업무일지기능 {

            String date;
            String name;
            String content;

            void 업무일지출력(){

                System.out.println("업무일지 [ " +date + " ]");
                System.out.println("이름 : "+name);
                System.out.println("내용 : "+content);
                System.out.println();

            }
        }

        업무일지기능 one = new 업무일지기능();
        one.date = "2026-09-25";
        one.name = "가나다";
        one.content = "자바 수업 진행하기";
        one.업무일지출력();

        업무일지기능 two = new 업무일지기능();
        two.date = "2026-09-26";
        two.name = "가나다";
        two.content = "함수 수업 진행하기";
        two.업무일지출력();


        class 업무일지기능2{
            void 업무일지출력(String date, String name, String content){
                System.out.println("업무일지 [ " +date + " ]");
                System.out.println("이름 : "+name);
                System.out.println("내용 : "+content);
                System.out.println();
            }

            //2026-01-01   -> 26년1월1일
            String 날짜표기변환(String date){
                String[] splitDate = date.split("-");
                String year= splitDate[0];
                String month= splitDate[1];
                String day= splitDate[2];
                return year +"년 "+month + "월 " + day + "일" ;
            }
        }



        업무일지기능2 three = new 업무일지기능2();
        three.업무일지출력("2026-09-26","안녕","자습");
        three.업무일지출력(three.날짜표기변환("2026-09-29"),"안녕2","자습3");

    }
}
