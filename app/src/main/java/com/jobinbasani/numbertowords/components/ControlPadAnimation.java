package com.jobinbasani.numbertowords.components;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;


/**
 * Created by jobinbasani on 11/11/14.
 */
public class ControlPadAnimation extends LayoutAnimationController {


    public ControlPadAnimation(Context context) {
        super(AnimationUtils.loadAnimation(context, android.support.v7.appcompat.R.anim.abc_fade_in));
        setDelay(getDelay()/3);
    }
}
