/**
 * http://stackoverflow.com/questions/37346709/
 */

package com.redmark.javademo.poly.sugguest;

import com.redmark.javademo.poly.obj.B;
import com.redmark.javademo.poly.obj.E;
import com.redmark.javademo.poly.obj.Ex;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Printer {

    private static final Map<Class<?>, BiConsumer<Printer, ?>> map = new HashMap<>();
    private static final BiConsumer<Printer, B> defaultConsumer = (p, b) -> System.out.println("it is b");

    static {
        for (Method m : Printer.class.getDeclaredMethods()) {
            if (m.getName().equals("printIt")) {
                Class<?>[] params = m.getParameterTypes();

                if (params.length == 1 && !params[0].equals(B.class)) {
                    map.put(params[0], (p, b) -> {
                        try {
                            m.invoke(p, b);
                        } catch (Exception e) {
                            // Should never happens
                            throw new RuntimeException("Invalid method mapping");
                        }
                    });
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void printIt(B b) {
        ((BiConsumer<Printer, T>) map.getOrDefault(b.getClass(), defaultConsumer)).accept(this, (T) b);
    }

    public void printIt(E e) {
        System.out.println("it is e");
    }

    public void printIt(Ex ex) {
        System.out.println("it is ex");
    }

    public static void main(String[] args) {
        B b = new B();
        E e = new E();

        B be = e;

        System.out.println("------------dynamic polymorphism ---------------");
        b.printMe();    //i am b
        e.printMe();    //i am e

        be.printMe();   //i am e


        System.out.println("------------static polymorphism ----------------");
        Printer printer = new Printer();

        printer.printIt(b);     //it is b
        printer.printIt(e);     //it is e


        System.out.println("-------------????????????????? -----------------");
        printer.printIt(be);    //it is e

        Ex ex = new Ex();
        B bex = ex;
        printer.printIt(bex);


    }
}
