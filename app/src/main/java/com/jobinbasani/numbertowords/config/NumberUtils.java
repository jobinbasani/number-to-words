package com.jobinbasani.numbertowords.config;

/**
 * Created by jobinbasani on 11/11/14.
 */
public class NumberUtils {
    public static final String BLANK = " ";
    public static final String SETTINGS = "SETT";
    public static final String DELETE = "DEL";
    public static final String PLAY = "PLAY";
    public static final String CLEAR = "CLR";
    public static String[] numberControls = {
            "7", "8", "9", NumberUtils.DELETE, "4", "5", "6", NumberUtils.CLEAR, "1", "2", "3", NumberUtils.PLAY, NumberUtils.BLANK, "0", NumberUtils.BLANK, NumberUtils.SETTINGS
    };
    public static String[] optionControls = {
            "1", "1", "3", NumberUtils.DELETE, "4", "5", "6", NumberUtils.CLEAR, "1", "2", "3", NumberUtils.PLAY, NumberUtils.BLANK, "0", NumberUtils.BLANK, NumberUtils.SETTINGS
    };

    public static boolean isNumber(String str){
        return str.matches("\\d");
    }
}
