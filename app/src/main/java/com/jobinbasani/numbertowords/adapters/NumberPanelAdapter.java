package com.jobinbasani.numbertowords.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.jobinbasani.numbertowords.R;
import com.jobinbasani.numbertowords.components.ConfigCell;
import com.jobinbasani.numbertowords.components.DeleteCell;
import com.jobinbasani.numbertowords.components.GridNumberCell;
import com.jobinbasani.numbertowords.config.NumberUtils;

/**
 * Created by jobinbasani on 11/10/14.
 */
public class NumberPanelAdapter extends BaseAdapter {

    private Context mContext;
    private int cellHeight;
    private String[] dataArray;
    Animation animation;

    public NumberPanelAdapter(Context context, int containerHeight, String[] dataArray) {
        mContext = context;
        cellHeight = containerHeight / (dataArray.length / 4);
        this.dataArray = dataArray;
        System.out.println("cell height is " + cellHeight);
        animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha_anim);
    }

    @Override
    public int getCount() {
        return dataArray.length;
    }

    @Override
    public Object getItem(int i) {
        return dataArray[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(dataArray[position].equals(NumberUtils.BLANK) || NumberUtils.isNumber(dataArray[position])){
            GridNumberCell gridCell = new GridNumberCell(mContext, cellHeight);
            gridCell.setText(dataArray[position]);
            return gridCell;
        }else{
            return getConfigCell(dataArray[position]);
        }
    }

    private View getConfigCell(String config){
        ConfigCell configCell = null;

        if(config.equals(NumberUtils.DELETE) || config.equals(NumberUtils.CLEAR))
            configCell = new DeleteCell(mContext,cellHeight);
        else
            configCell = new ConfigCell(mContext, cellHeight);

        configCell.setText(config);

        return configCell;
    }
}
