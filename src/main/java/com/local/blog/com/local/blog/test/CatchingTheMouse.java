package com.local.blog.com.local.blog.test;

import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

public class CatchingTheMouse {
    public static void main(String[] args) {

        Cat realCat = new RealCat();

        Cat catProxy = (Cat) Proxy.newProxyInstance(
                Cat.class.getClassLoader(), // 프록시 로딩에 사용할 클래스로더
                new Class[]{Cat.class}, // 원본객체의 interface
                new CatProxyLoggingHandler((RealCat) realCat) // 부가기능을 위임할 원본객체
        );
            System.out.println("쥐 : 찍찍");
            String mouseSound = "찍찍";

            catProxy.speak(mouseSound);
            catProxy.catchMouseChase(mouseSound);
            catProxy.catchMouseTrap(mouseSound);
            catProxy.catchMouseAttack(mouseSound);

            System.out.println("고양이가 쥐를 잡았습니다.");
    }
}


       /* Cat catProxy = (Cat) Enhancer.create(
                RealCat.class,
                new CatProxyMethodInterceptor(new RealCat())
        );*/



/*
        Cat catProxy = (Cat) Proxy.newProxyInstance(
                Cat.class.getClassLoader(), // 프록시 로딩에 사용할 클래스로더
                new Class[]{Cat.class}, // 원본객체의 interface
                new CatProxyLoggingHandler((RealCat) realCat) // 부가기능을 위임할 원본객체
        );
*/
