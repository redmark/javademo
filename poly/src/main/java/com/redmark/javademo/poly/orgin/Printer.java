package com.redmark.javademo.poly.orgin;

import com.redmark.javademo.poly.obj.B;
import com.redmark.javademo.poly.obj.E;

public class Printer {

    public void printIt(B b) {
        System.out.println("it is b");
    }

//    public void printIt(obj.B b) {
//        if (b.getClass().equals(obj.B.class)) {
//            System.out.println("it is b");
//        } else if (b.getClass().equals(obj.E.class)) {
//            obj.E e = (obj.E) b;
//            printIt(e);
//        }
//    }

    public void printIt(E e) {
        System.out.println("it is e");
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
        printer.printIt(be);    //it is b
    }
}
