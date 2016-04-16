package com.redmark.demo.initializer;

import javax.servlet.ServletContext;

/**
 * Created by dyliz on 2016/4/16.
 */
public interface AppInit {
    public void doSomething(ServletContext sc);
}
