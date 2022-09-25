package com.jobinbasani.numbertowords.components.interfaces;

/**
 * Created by jobinbasani on 11/11/14.
 */
public interface NumberTransformerI {
    void updateNumber(String number);
    void clearNumber(boolean clearAll);
    void updatePanel(String[] cells, boolean animate);
    void speakNumberText();
    void copyToClipboard();
    String getShareText();
}
