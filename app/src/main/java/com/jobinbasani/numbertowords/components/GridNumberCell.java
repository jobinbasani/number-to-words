package com.jobinbasani.numbertowords.components;

import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jobinbasani.numbertowords.R;

/**
 * Created by jobinbasani on 11/10/14.
 */
public class GridNumberCell extends TextView implements GridBlockI {

    private Animation animation;

    public GridNumberCell(Context context, int height) {
        super(context);
        setHeight(height);
        setGravity(Gravity.CENTER);
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setTextColor(getResources().getColor(android.support.v7.appcompat.R.color.abc_primary_text_material_dark));
        setTextSize((float) (height * .1));
        setBackground(getResources().getDrawable(R.drawable.cell_border));
        animation = AnimationUtils.loadAnimation(context,R.anim.alpha_anim);
    }

    @Override
    public void onClick() {
        System.out.println("in class method "+getText());

        if(!getText().equals(" ")){
            setAnimation(animation);
            animation.start();
        }
    }
}
