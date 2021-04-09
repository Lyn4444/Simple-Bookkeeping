package com.example.simplebookkeeping.utils;

import java.text.DecimalFormat;

public class FloatDecimalFormat {

    public static String floatFormat(float price) {

        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return decimalFormat.format(price);

    }
}
