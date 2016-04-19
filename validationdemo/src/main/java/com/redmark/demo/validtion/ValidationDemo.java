package com.redmark.demo.validtion;


import javax.validation.*;
import java.util.Locale;
import java.util.Set;

/**
 * Created by dyliz on 2016/4/19.
 */
public class ValidationDemo {
    public static void main(String[] args) {

        funDefaultLocale(); // vm default locale


        Locale.setDefault(Locale.KOREA); // set default locale to kr
        funDefaultLocale();
        funDesiredLocle(Locale.SIMPLIFIED_CHINESE);
        funDesiredLocle(Locale.ENGLISH);
        funDesiredLocle(Locale.FRANCE);
        funDesiredLocle(new Locale("hu"));
        funDesiredLocle(Locale.ITALIAN); // hibernate provider not provide it message , the default locale of vm will be used

    }

    public static void funDefaultLocale() {
        Foo foo = new Foo();

        ValidatorFactory f = Validation.buildDefaultValidatorFactory();

        Validator validator = f.getValidator();
        Set<ConstraintViolation<Foo>> constrain = validator.validate(foo);
        constrain.forEach(v -> System.out.println(v.getMessage()));
    }

    public static void funDesiredLocle(Locale locale) {
        Foo foo = new Foo();

        Configuration<?> configure = Validation.byDefaultProvider().configure();
        MessageInterpolator defaultMessageInterpolator = configure.getDefaultMessageInterpolator();
        ValidatorFactory f = configure.messageInterpolator(new MyInterpolator(defaultMessageInterpolator, locale)).buildValidatorFactory();

        Validator validator = f.getValidator();
        Set<ConstraintViolation<Foo>> constrain = validator.validate(foo);
        constrain.forEach(v -> System.out.println(v.getMessage()));
    }

}
