package com.jobinbasani.numbertowords.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.jobinbasani.numbertowords.R;
import com.jobinbasani.numbertowords.components.ConfigCell;
import com.jobinbasani.numbertowords.components.GridNumberCell;
import com.jobinbasani.numbertowords.config.NumberUtils;

/**
 * Created by jobinbasani on 11/10/14.
 */
public class NumberPanelAdapter extends BaseAdapter {

    private Context mContext;
    private int cellHeight;
    Animation animation;
    private String[] mControls = {
            "7", "8", "9", NumberUtils.DELETE, "4", "5", "6", NumberUtils.CLEAR, "1", "2", "3", NumberUtils.PLAY, NumberUtils.BLANK, "0", NumberUtils.BLANK, NumberUtils.SETTINGS
    };

    public NumberPanelAdapter(Context context, int containerHeight) {
        mContext = context;
        cellHeight = containerHeight / (mControls.length / 4);
        System.out.println("cell height is " + cellHeight);
        animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha_anim);
    }

    @Override
    public int getCount() {
        return mControls.length;
    }

    @Override
    public Object getItem(int i) {
        return mControls[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(mControls[position].equals(NumberUtils.BLANK) || NumberUtils.isNumber(mControls[position])){
            GridNumberCell gridCell = new GridNumberCell(mContext, cellHeight);
            gridCell.setText(mControls[position]);
            return gridCell;
        }else{
            ConfigCell configCell = new ConfigCell(mContext, cellHeight);
            configCell.setText(mControls[position]);
            return configCell;
        }
    }
}
