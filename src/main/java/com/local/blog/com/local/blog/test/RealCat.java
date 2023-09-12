package com.local.blog.com.local.blog.test;

public class RealCat implements Cat {

    @Override
    public void speak(String sound) {
        System.out.println("RealCat: 고양이가 소리를 듣고 반응합니다.");
    }

    @Override
    public void catchMouseTrap(String sound) {
        System.out.println("RealCat: 고양이가 덫을 놓습니다.");
    }

    @Override
    public void catchMouseAttack(String sound) {
        System.out.println("RealCat: 고양이가 공격합니다.");
    }

    @Override
    public void catchMouseChase(String sound) {
        System.out.println("RealCat: 고양이가 쫓아갑니다.");
    }
}
