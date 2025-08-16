package com.coder.springjwt.helpers.generateDateandTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class GenerateDateAndTime {



    public static final String getTodayDate()
    {
        return LocalDate.now().toString();
    }

    public static final String getCurrentTime()
    {
        return LocalTime.now().toString();
    }

    public static final String getLocalDateTime()
    {
        return LocalDateTime.now().toString();
    }

}
