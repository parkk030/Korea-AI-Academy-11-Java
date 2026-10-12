package com.korai.study.ch09.service;

import com.korai.study.ch09.repository.CarRepository;

import java.util.ArrayList;

public class InitService implements Runnable{ //ctrl i - run생성

    private static CarRepository carRepository;

    public InitService() {
        if(carRepository==null){
            run();
        }

    }

    @Override
    public void run() {
        System.out.println("프로그램 초기 설정 시작...");
        carRepository = new CarRepository(new ArrayList<>());
        System.out.println("프로그램 초기 설정 완료.");
    }

    public static CarRepository getCarRepository() {
        return carRepository;
    }
}
