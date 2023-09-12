package com.local.blog.com.local.blog.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CatProxyLoggingHandler implements InvocationHandler {

    private final RealCat realCat; // 부가 기능을 제공할 원본 객체
    private static final Logger log = LoggerFactory.getLogger(CatProxyLoggingHandler.class);

    public CatProxyLoggingHandler(RealCat realCat) { // 다이나믹 프록시로부터 전달받은 요청을 다시 원본 객체에
        this.realCat = realCat; // 위임해야하므로 원본 객체를 주입받음.
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("catch")){
           log.info("쥐색기 잡기 : Executing {} method ",method.getName());
        }
        return method.invoke(realCat, args); // 원본 객체에 위임. 모든 인터페이스의 메소드 호출에 적용
    }
}
