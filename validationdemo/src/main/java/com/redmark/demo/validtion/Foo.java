package com.redmark.demo.validtion;

import javax.validation.constraints.NotNull;

/**
 * Created by dyliz on 2016/4/19.
 */
public class Foo {
    private String name;

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
