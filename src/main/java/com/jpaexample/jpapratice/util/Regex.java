package com.jpaexample.jpapratice.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    static final private String pwPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{9,12}$";
    static final private Pattern pattern = Pattern.compile(pwPattern);

    public static boolean isValidPassword(String password) {
        return pattern.matcher(password).find();
    }
}
