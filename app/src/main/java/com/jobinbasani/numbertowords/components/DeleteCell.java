package com.jobinbasani.numbertowords.components;

import android.content.Context;

import com.jobinbasani.numbertowords.config.NumberUtils;

/**
 * Created by jobinbasani on 11/12/14.
 */
public class DeleteCell extends ConfigCell {
    public DeleteCell(Context context, int height) {
        super(context, height);
    }

    @Override
    public void onClick() {
        super.onClick();
        getNumberTransformer().clearNumber(getText().toString().equals(NumberUtils.CLEAR));
    }
}
