package com.local.blog.com.local.blog.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CatProxyMethodInterceptor implements MethodInterceptor {

    private final RealCat realCat; // 부가 기능을 제공할 원본 객체
    private static final Logger log = LoggerFactory.getLogger(CatProxyLoggingHandler.class);

    public CatProxyMethodInterceptor(RealCat realCat) {
        this.realCat = realCat;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (method.getName().startsWith("catch")){
            log.info("쥐색기 잡기 : Executing {} method ",method.getName());
            return methodProxy.invoke(realCat, args);
        }
        log.info("고양이 : Executing {} method ",method.getName());
        return methodProxy.invoke(realCat,args);
    }
}

