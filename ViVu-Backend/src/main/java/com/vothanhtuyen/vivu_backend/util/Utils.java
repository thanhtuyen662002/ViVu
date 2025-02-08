package com.vothanhtuyen.vivu_backend.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class Utils {
    public static Timestamp getCurrentTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char firstChar = Character.toUpperCase(str.charAt(0));
        return firstChar + str.substring(1);
    }
}
