package com.korai.study.ch13;

//람다  - 추상메서드를 하나만 가지고있는 인터페이스를 익명객체로 성생할때
// 편리하게 사용할 수 있도록 만들어진 문법  , 인터페이스안에 추상메서드가 1개있어야함

public class Lambda01 {
    public static void main(String[] args) {
        KeyboardEvent k1 = new KeyboardEvent() {
            @Override
            public void keyUp() {
                System.out.println("키업");
            }

            @Override
            public void keyDown() {
                System.out.println("키다운");
            }
        };

        k1.keyDown();
        k1.keyUp();

        Speaker speaker = () -> System.out.println("사운드 재생");
        {

            speaker.soundOn();
        }
    }
}
interface KeyboardEvent {
    void keyUp();
    void keyDown();
}

interface Speaker{
    void soundOn();
}
