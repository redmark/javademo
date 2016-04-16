package com.redmark.demo.initializer;

import javax.servlet.ServletContext;

/**
 * Created by dyliz on 2016/4/16.
 */
public class AppInit1 implements AppInit {

    @Override
    public void doSomething(ServletContext sc) {
        System.out.println("i am  AppInit1 ");
    }
}
