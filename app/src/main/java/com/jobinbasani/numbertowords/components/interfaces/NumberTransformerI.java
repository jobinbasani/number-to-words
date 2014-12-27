package com.jobinbasani.numbertowords.components.interfaces;

/**
 * Created by jobinbasani on 11/11/14.
 */
public interface NumberTransformerI {
    public void updateNumber(String number);
    public void clearNumber(boolean clearAll);
    public void updatePanel(String[] cells, boolean animate);
    public void speakNumberText();
    public void copyToClipboard();
    public String getShareText();
}
