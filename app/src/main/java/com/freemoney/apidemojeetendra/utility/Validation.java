package com.freemoney.apidemojeetendra.utility;

import java.util.regex.Pattern;

public class Validation {
    public static int BLANK_CHECK = 2;
    public static int EMAIL = 1;
    public static int MOBILE = 3;

    public static boolean isValid(int type, String value) {
        switch (type) {
            case 1:
                return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(value).matches();
            case 2:
                if (value.trim().equals("")) {
                    return false;
                }
                return true;
            case 3:
                if (value.trim().length() >= 10) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }
}
