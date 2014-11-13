package com.jobinbasani.numbertowords.components;

import android.content.Context;

import com.jobinbasani.numbertowords.R;
import com.jobinbasani.numbertowords.config.NumberUtils;

/**
 * Created by jobinbasani on 11/11/14.
 */
public class ConfigCell extends GridNumberCell {
    public ConfigCell(Context context, int height) {
        super(context, height);
        setBackgroundColor(getResources().getColor(R.color.colorConfigCell));
    }

    @Override
    public void onClick() {
        runAnimation();
        getNumberTransformer().setOptionsAdapter();
    }
}
