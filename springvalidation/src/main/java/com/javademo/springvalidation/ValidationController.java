package com.javademo.springvalidation;

import org.hibernate.validator.constraints.Email;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

/**
 * Created by redmark on 5/19/16.
 */
@RestController
@Validated
public class ValidationController {

    public class Foo {
        String bar;

        @Email
        public String getBar() {
            return bar;
        }

        public void setBar(String bar) {
            this.bar = bar;
        }

        public Foo(String bar) {
            this.bar = bar;
        }
    }

    @RequestMapping(value = "/foo/{bar}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Foo getFoo(@PathVariable  @Pattern(regexp = "^[1][3-8]\\d{9}$", message = "invalid mobile number") String bar) {
        return new Foo(bar);
    }

    @RequestMapping(value = "/foo",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Foo setFoo(@RequestBody @Valid Foo foo) {
        return new Foo(foo.bar);
    }
}
