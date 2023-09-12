package com.local.blog.com.local.blog.test;

import java.lang.reflect.Proxy;

public class CatProxy implements Cat {
    private final RealCat cat;
    private boolean trapSet;
    public CatProxy(RealCat cat) {
        this.cat = cat;
        trapSet = false;
    }

    public void setTrap() {
        System.out.println("CatProxy : 덫 설치");
        trapSet = true;
    }
    @Override
    public void catchMouseTrap(String sound) {
        setTrap();
        System.out.println("CatProxy : 고양이가 덫을 놓았습니다.");
    }
    @Override
    public void speak(String sound) {
        System.out.println("CatProxy : 고양이가 소리를 듣고 반응합니다.");
    }
    @Override
    public void catchMouseAttack(String sound) {
        System.out.println("CatProxy: 고양이가 공격합니다.");
    }
    @Override
    public void catchMouseChase(String sound) {
        System.out.println("CatProxy: 고양이가 쫓아갑니다.");
    }
}
