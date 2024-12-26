package com.own.moviebooking2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestMain {
    public static void main(String[] args) {

        // Hoặc sử dụng định dạng tùy chỉnh
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime customStartTime1 = LocalDateTime.parse("2024-12-27 14:30:00", formatter);
        LocalDateTime customStartTime2 = LocalDateTime.parse("2024-12-26 15:30:00", formatter);

        System.out.println(customStartTime1.isBefore(customStartTime2));
    }
}
