package com.korai.study.ch13;

import java.util.Objects;

//익명클래스
public class AnonymousClass01 {
    // 오브젝트 클래스를 상속받은 이름없는 클래스를 정의와 동시에 생성
    Object obj = new Object() {};

    class A extends Object {}
    Object obj2 = new A();

    class B {}
    Object obj3 = new B();

    class C {}
    Object obj4 = new C() {};

    abstract class D extends C {}
    D obj5 = new D() {};

    interface E {}
    E obj6  = new E() {};
}
