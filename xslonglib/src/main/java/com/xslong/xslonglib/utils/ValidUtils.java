package com.xslong.xslonglib.utils;

import java.util.regex.Pattern;

public abstract class ValidUtils {

    public static boolean isPhone(String phone) {
        if (isEmpty(phone)) return false;
        return phone.matches("^(1[0-9]|[0-9]|14[5|7]|15[0-9]|18[0-9]|16[0-9]|19[0-9])\\d{8}$");
    }

    public static boolean isIDCard(String idcard) {
        if ((idcard == null) || ((idcard.length() != 15) &&
                (idcard.length() != 18))) {
            return false;
        }
        int personalIdLength = idcard.length();
        String regex = "[1-8]{1}[0-9]{" + (personalIdLength - 2) + "}[0-9X]";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(idcard).matches();
    }

    public static boolean isMatchLength(String s, Integer min, Integer max) {
        if (isBlank(s)) return false;
        if (min != null && s.length() < min) return false;
        if (max != null && s.length() > max) return false;
        return true;
    }

    public static boolean isNotEmpty(String s) {
        if (s != null && !s.equals("") && !s.equals("null")) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String s) {
        return !isNotEmpty(s);
    }

    public static boolean isNotBlank(String s) {
        if (isNotEmpty(s) && !s.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isBlank(String s) {
        return !isNotBlank(s);
    }

    public static boolean isNumber(String s) {
        if (isBlank(s)) return false;
        try {
            double d = Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
