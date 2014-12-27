package com.jobinbasani.numbertowords.components;

import android.content.Context;

import com.jobinbasani.numbertowords.config.NumberUtils;

/**
 * Created by jobinbasani on 12/27/14.
 */
public class GridTextCell extends GridNumberCell {
    public GridTextCell(Context context, int height, int width) {
        super(context, height, width);
        setTextSize(height * NumberUtils.CELL_TEXT_SIZE_FACTOR);
    }

    @Override
    public void onClick() {
        if(!getText().toString().equals(NumberUtils.BLANK)){
            runAnimation();
            switch (getText().toString()){
                case NumberUtils.FDBACK:
                    getCellContext().startActivity(NumberUtils.getFeedbackIntent(getCellContext()));
                    break;
                case NumberUtils.COPY:
                    getNumberTransformer().copyToClipboard();
                    break;
                case NumberUtils.SHARE:
                    getCellContext().startActivity(NumberUtils.getShareDataIntent(getNumberTransformer().getShareText()));
                    break;
            }
        }
    }
}
