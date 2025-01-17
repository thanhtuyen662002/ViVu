package com.vothanhtuyen.vivu_backend.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class Utils {
    public static Timestamp getCurrentTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
}
