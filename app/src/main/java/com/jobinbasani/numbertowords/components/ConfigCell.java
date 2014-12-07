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
        setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
        setTextSize((float) (height * .06));
        setPadding(0,0,0,6);
        setBackgroundColor(getResources().getColor(R.color.colorConfigCell));
    }

    @Override
    public void onClick() {
        runAnimation();
        if(getText().toString().equals(NumberUtils.DELETE) || getText().toString().equals(NumberUtils.CLEAR)){
            getNumberTransformer().clearNumber(getText().toString().equals(NumberUtils.CLEAR));
        }else if(getText().toString().equals(NumberUtils.BACK)){
            getNumberTransformer().updatePanel(NumberUtils.numberControls,true);
        }
        else{
            getNumberTransformer().updatePanel(NumberUtils.optionControls, true);
        }
    }
}
