package com.corba.corbam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static String formatDate(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date convertedDate = dateFormat.parse(strDate);
            SimpleDateFormat sdfnewformat = new SimpleDateFormat("dd MM yyyy");
            return sdfnewformat.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
