package com.aes.corebackend.util;

import java.util.Random;

public class UserCredentialUtil {
    public static String generatePassword(Integer length) {
        Long min = (long) Math.pow(10, length - 1);
        Long max = (long) Math.pow(10, length) - 1;

        Random random = new Random();
        String password = Long.toString(random.nextLong(max - min) + min);
        return password;
    }
}
