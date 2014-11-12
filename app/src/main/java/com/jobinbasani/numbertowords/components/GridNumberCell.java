package com.jobinbasani.numbertowords.components;

import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jobinbasani.numbertowords.R;
import com.jobinbasani.numbertowords.components.interfaces.GridBlockI;
import com.jobinbasani.numbertowords.components.interfaces.NumberTransformerI;
import com.jobinbasani.numbertowords.config.NumberUtils;

/**
 * Created by jobinbasani on 11/10/14.
 */
public class GridNumberCell extends TextView implements GridBlockI {

    private Animation animation;
    private NumberTransformerI numberTransformer;
    public GridNumberCell(Context context, int height) {
        super(context);
        numberTransformer = (NumberTransformerI) context;
        setHeight(height);
        setGravity(Gravity.CENTER);
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setTextColor(getResources().getColor(android.support.v7.appcompat.R.color.abc_primary_text_material_dark));
        setTextSize((float) (height * .1));
        setBackground(getResources().getDrawable(R.drawable.cell_border));
        animation = AnimationUtils.loadAnimation(context,R.anim.alpha_anim);
    }

    protected void runAnimation(){
        setAnimation(animation);
        animation.start();
    }

    protected NumberTransformerI getNumberTransformer(){
        return numberTransformer;
    }

    @Override
    public void onClick() {
        if(!getText().equals(NumberUtils.BLANK)){
            runAnimation();
            numberTransformer.updateNumber(getText().toString());
        }
    }
}
