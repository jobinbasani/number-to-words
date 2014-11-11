package com.jobinbasani.numbertowords.components;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;

import com.jobinbasani.numbertowords.R;

/**
 * Created by jobinbasani on 11/10/14.
 */
public class GridNumberCell extends TextView {
    public GridNumberCell(Context context, int height) {
        super(context);
        setHeight(height);
        setGravity(Gravity.CENTER);
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setTextColor(getResources().getColor(android.support.v7.appcompat.R.color.abc_primary_text_material_dark));
        setTextSize((float) (height * .08));
        setBackground(getResources().getDrawable(R.drawable.cell_border));
    }
}
