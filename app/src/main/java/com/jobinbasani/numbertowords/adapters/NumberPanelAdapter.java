package com.jobinbasani.numbertowords.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.jobinbasani.numbertowords.R;
import com.jobinbasani.numbertowords.components.GridNumberCell;

/**
 * Created by jobinbasani on 11/10/14.
 */
public class NumberPanelAdapter extends BaseAdapter {

    private Context mContext;
    private int cellHeight;
    Animation animation;
    private String[] mControls = {
            "7","8","9","D","4","5","6","C","1","2","3","P"," ","0"," ","S"
    };

    public NumberPanelAdapter(Context context, int containerHeight) {
        mContext = context;
        cellHeight = containerHeight/(mControls.length/4);
        System.out.println("cell height is "+cellHeight);
        animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha_anim);
    }

    @Override
    public int getCount() {
        return mControls.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        GridNumberCell textView;
        if(convertView == null){
            textView = new GridNumberCell(mContext,cellHeight);
        }else{
            System.out.println("in grid cell recycle view");
            textView = (GridNumberCell) convertView;
        }
        textView.setText(mControls[position]);
        textView.setAnimation(animation);
        animation.start();
        return textView;
    }
}
