package com.jobinbasani.numbertowords.components;

import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.jobinbasani.numbertowords.R;
import com.jobinbasani.numbertowords.components.interfaces.GridBlockI;
import com.jobinbasani.numbertowords.components.interfaces.NumberTransformerI;
import com.jobinbasani.numbertowords.config.NumberUtils;

/**
 * Created by jobinbasani on 11/10/14.
 */
public class GridNumberCell extends androidx.appcompat.widget.AppCompatTextView implements GridBlockI {

    private final Animation animation;
    private final NumberTransformerI numberTransformer;
    private final Context mContext;

    public GridNumberCell(Context context, int height, int width) {
        super(context);
        numberTransformer = (NumberTransformerI) context;
        mContext = context;
        setHeight(height);
        setGravity(Gravity.CENTER);
        setTextSize(height * NumberUtils.CELL_NUMBER_SIZE_FACTOR);
        setWidth(width);
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setTextColor(getResources().getColor(R.color.abc_primary_text_material_dark));
        setBackground(getResources().getDrawable(R.drawable.cell_border));
        animation = AnimationUtils.loadAnimation(context, R.anim.alpha_anim);
        setOnClickListener(view -> {
            GridBlockI gridBlock = (GridBlockI) view;
            gridBlock.onClick();
        });

    }

    protected void runAnimation() {
        startAnimation(animation);
    }

    protected Context getCellContext() {
        return mContext;
    }

    protected NumberTransformerI getNumberTransformer() {
        return numberTransformer;
    }

    @Override
    public void onClick() {
        if (!getText().toString().equals(NumberUtils.BLANK)) {
            runAnimation();
            if (NumberUtils.isNumber(getText().toString()))
                numberTransformer.updateNumber(getText().toString());
        }
    }

}
