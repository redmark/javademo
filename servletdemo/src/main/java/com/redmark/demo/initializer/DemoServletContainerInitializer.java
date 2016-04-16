package com.redmark.demo.initializer;

import com.redmark.demo.servlet.NormalServlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import java.util.*;

/**
 * Created by dyliz on 2016/4/15.
 */
@HandlesTypes(AppInit.class)
public class DemoServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println(ctx);
        Enumeration<String> names = ctx.getAttributeNames();
        Collections.list(names).forEach(n -> System.out.println(n + "=" + ctx.getAttribute(n)));

        ServletRegistration.Dynamic registration = ctx.addServlet("NormalServlet", new NormalServlet());
        registration.addMapping("/NormalServlet");

        System.out.println("============================================");

        Map<String, ? extends ServletRegistration> map = ctx.getServletRegistrations();
        map.forEach((k, v) -> System.out.println(k + "=" + v));

        if (c != null) {
            for (Class<?> aClass : c) {
                try {
                    AppInit o = ((AppInit) aClass.newInstance());
                    o.doSomething(ctx);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}