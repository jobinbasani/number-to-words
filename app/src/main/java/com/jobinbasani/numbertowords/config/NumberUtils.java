package com.jobinbasani.numbertowords.config;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.jobinbasani.numbertowords.R;

import java.text.DecimalFormat;

/**
 * Created by jobinbasani on 11/11/14.
 * Number to word conversion from http://www.rgagnon.com/javadetails/java-0426.html
 */
public class NumberUtils {
    public static final int CELL_HEIGHT_DIVISOR = 4;
    public static final String NUMBER_BUNDLE_KEY = "USER_NUMBER";
    public static final float CELL_TEXT_SIZE_FACTOR = .06f;
    public static final float CELL_NUMBER_SIZE_FACTOR = .10f;
    public static final String BLANK = " ";
    public static final String MORE_OPTIONS = "...";
    public static final String DELETE = "DELETE";
    public static final String SPEAK = "SPEAK";
    public static final String RATE = "RATE";
    public static final String FDBACK = "FEEDBACK";
    public static final String BACK = "BACK";
    public static final String CLEAR = "CLEAR";
    public static final String COPY = "COPY";
    public static final String SHARE = "SHARE";
    public static String[] numberControls = {
            "7", "8", "9", DELETE, "4", "5", "6", CLEAR, "1", "2", "3", SPEAK, COPY, "0", SHARE, MORE_OPTIONS
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

    public static Intent getFeedbackIntent(Context context){
        Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + context.getResources().getString(R.string.feedbackEmail) + "?subject=" + Uri.encode(context.getResources().getString(R.string.feedbackSubject))));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.feedbackSubject));
        return Intent.createChooser(emailIntent, context.getResources().getString(R.string.feedbackIntentTitle));
    }

    public static Intent getShareDataIntent(String data){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, data);
        shareIntent.setType("text/plain");
        return Intent.createChooser(shareIntent, "Share");
    }

    public static boolean showRateApp(Context context){
        String installedBy = context.getPackageManager().getInstallerPackageName(context.getPackageName());
        if(installedBy == null || !installedBy.equals(context.getResources().getString(R.string.playStore))){
            return false;
        }
        return true;
    }

    public static String[] getOptionControls(Context context){
        String[] optionControls = {
                FDBACK, RATE, BLANK, BACK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK
        };
        int ratePosition = -1;
        for(int i=0;i<optionControls.length;i++){
            if(optionControls[i].equals(RATE))ratePosition = i;
        }
        if(ratePosition!=-1 && !showRateApp(context)){
            optionControls[ratePosition] = BLANK;
        }
        return optionControls;
    }

    public static Intent getPlaystoreListing(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id="+context.getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        return intent;
    }
}
