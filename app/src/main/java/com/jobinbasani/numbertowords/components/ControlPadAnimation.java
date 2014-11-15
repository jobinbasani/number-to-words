package com.jobinbasani.numbertowords.components;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.jobinbasani.numbertowords.R;


/**
 * Created by jobinbasani on 11/11/14.
 */
public class ControlPadAnimation extends LayoutAnimationController {


    public ControlPadAnimation(Context context) {
        super(AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom));
        setDelay(getDelay()/3);
    }
}
