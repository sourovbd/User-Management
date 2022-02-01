package com.aes.corebackend.util;

public class ValidationPattern {

    public final static String MOBILE_NUMBER_PATTERN = "^(?:(\\+88)?01[13456798][0-9]{8}|)$";
    public final static String PHONE_NUMBER_PATTERN = "^(?:(\\+88)?0[0-9]{7,10}|)$";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
}
