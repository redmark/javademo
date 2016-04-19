package com.redmark.demo.validtion;

import javax.validation.MessageInterpolator;
import java.util.Locale;

/**
 *
 * refer to org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator
 * Created by dyliz on 2016/4/20.
 */
public class MyInterpolator implements MessageInterpolator {
    MessageInterpolator targetInterpolator;
    Locale locale;
    public MyInterpolator(MessageInterpolator targetInterpolator,Locale locale) {
        this.targetInterpolator = targetInterpolator;
        this.locale=locale;
    }

    @Override
    public String interpolate(String message, Context context) {
        return this.targetInterpolator.interpolate(message, context, this.locale);
    }

    @Override
    public String interpolate(String message, Context context, Locale locale) {
        return this.targetInterpolator.interpolate(message, context, locale);
    }
}
