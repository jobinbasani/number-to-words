package com.jobinbasani.numbertowords.config;

import java.text.DecimalFormat;

/**
 * Created by jobinbasani on 11/11/14.
 * Number to word conversion from http://www.rgagnon.com/javadetails/java-0426.html
 */
public class NumberUtils {
    public static final int CELL_HEIGHT_DIVISOR = 4;
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
    private static final String[] tensNames = {
            "",
            " ten",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
    };
    private static final String[] numNames = {
            "",
            " one",
            " two",
            " three",
            " four",
            " five",
            " six",
            " seven",
            " eight",
            " nine",
            " ten",
            " eleven",
            " twelve",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
    };
    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20){
            soFar = numNames[number % 100];
            number /= 100;
        }
        else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " hundred" + soFar;
    }


    public static String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) { return "Zero"; }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0,3));
        // nnnXXXnnnnnn
        int millions  = Integer.parseInt(snumber.substring(3,6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6,9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9,12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1 :
                tradBillions = convertLessThanOneThousand(billions)
                        + " billion ";
                break;
            default :
                tradBillions = convertLessThanOneThousand(billions)
                        + " billion ";
        }
        String result =  tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1 :
                tradMillions = convertLessThanOneThousand(millions)
                        + " million ";
                break;
            default :
                tradMillions = convertLessThanOneThousand(millions)
                        + " million ";
        }
        result =  result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1 :
                tradHundredThousands = "one thousand ";
                break;
            default :
                tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                        + " thousand ";
        }
        result =  result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result =  result + tradThousand;
        result = result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
        if(result.length()>0){
            result = new StringBuffer(result.length())
                    .append(Character.toTitleCase(result.charAt(0)))
                    .append(result.substring(1))
                    .toString();
        }
        return capitalize(result);
    }

    private static String capitalize(String word){
        if(word.length()>0){
            word = new StringBuffer(word.length())
                    .append(Character.toTitleCase(word.charAt(0)))
                    .append(word.substring(1))
                    .toString();
        }
        return word;
    }

    public static boolean isNumber(String str){
        return str.matches("\\d");
    }
}
