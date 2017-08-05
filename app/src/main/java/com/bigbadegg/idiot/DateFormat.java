package com.bigbadegg.idiot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hasee on 2017/8/5.
 */

public class DateFormat {


    public static String format(String format, String time) {
        SimpleDateFormat formatter;
        Date date = new Date(Long.valueOf(time));

        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

        return formatter.format(date);
    }

}
