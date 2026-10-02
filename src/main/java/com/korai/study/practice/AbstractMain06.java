package com.korai.study.practice;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.List;

public class AbstractMain06 {
    public static void main(String[] args) {

       // List<RemoteControl> remoteControls = new ArrayList<>();
       // remoteControls.add(new TvRemoteControl());
        // remoteControls.add(new MonitorRemoteControl());

        List<RemoteControl> remoteControls = List.of(
                new TvRemoteControl(),
                new MonitorRemoteControl(),
                new TvRemoteControl(),
                new MonitorRemoteControl()
        );

        //for(int i ; remoteControls.size(); i++){
        // RemoteControl r = remoteControls.get(i)
        // r.powerOn();
        // }

        for(RemoteControl rcl : remoteControls){
            rcl.powerOn();
        }



    }
}

interface Sensor{ //추상객체 - 추상메서드만으로 구성
    void on(); //추상메서드
    void off();
    void send();
}

abstract class RemoteControl implements Sensor{ //추상클래스  - 생성불가
    String modelName;
    void showModelName(){
        System.out.println(modelName);
    }
    abstract void powerOn(); // abstract// 자녀클래스에서 무조건 구현해야함 - 강제성부여.
}

abstract class MouseRemoteControl extends RemoteControl {

}
class TvRemoteControl extends RemoteControl{
    @Override
    void powerOn() {
        System.out.println("TV 회로에 맞게 전원 공급");
    }

    @Override
    public void on() {
        System.out.println("켜진다");
    }

    @Override
    public void off() {

    }

    @Override
    public void send() {

    }
}

class MonitorRemoteControl extends RemoteControl{
    @Override
    void powerOn() {
        System.out.println("모니터 회로에 맞게 전원 공급");
    }

    @Override
    public void on() {

    }

    @Override
    public void off() {

    }

    @Override
    public void send() {

    }
}