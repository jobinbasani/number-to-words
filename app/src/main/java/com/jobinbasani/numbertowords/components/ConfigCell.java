package com.jobinbasani.numbertowords.components;

import android.content.Context;
import android.view.Gravity;

import com.jobinbasani.numbertowords.R;
import com.jobinbasani.numbertowords.config.NumberUtils;

/**
 * Created by jobinbasani on 11/11/14.
 */
public class ConfigCell extends GridNumberCell {
    public ConfigCell(Context context, int height, int width) {
        super(context, height, width);
        setTextSize(height * NumberUtils.CELL_TEXT_SIZE_FACTOR);
        setBackgroundColor(getResources().getColor(R.color.colorConfigCell));
    }

    @Override
    public void onClick() {
        if(!getText().toString().equals(NumberUtils.BLANK)) {
            runAnimation();
            switch (getText().toString()){
                case NumberUtils.DELETE:
                    getNumberTransformer().clearNumber(false);
                    break;
                case NumberUtils.CLEAR:
                    getNumberTransformer().clearNumber(true);
                    break;
                case NumberUtils.BACK:
                    getNumberTransformer().updatePanel(NumberUtils.numberControls, true);
                    break;
                case NumberUtils.MORE_OPTIONS:
                    getNumberTransformer().updatePanel(NumberUtils.getOptionControls(getCellContext()), true);
                    break;
                case NumberUtils.SPEAK:
                    getNumberTransformer().speakNumberText();
                    break;
            }
        }
    }
}
