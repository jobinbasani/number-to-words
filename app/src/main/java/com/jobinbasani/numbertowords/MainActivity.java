package com.jobinbasani.numbertowords;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.jobinbasani.numbertowords.adapters.NumberPanelAdapter;
import com.jobinbasani.numbertowords.components.ControlPadAnimation;
import com.jobinbasani.numbertowords.components.interfaces.GridBlockI;
import com.jobinbasani.numbertowords.components.interfaces.NumberTransformerI;
import com.jobinbasani.numbertowords.config.NumberUtils;


public class MainActivity extends ActionBarActivity implements NumberTransformerI {

    private Context mContext = this;
    private TextView numberTextView;
    private NumberPanelAdapter numberAdapter;
    private NumberPanelAdapter optionsAdapter;
    private int containerHeight;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberTextView = (TextView) findViewById(R.id.numberText);
        gridView = (GridView) findViewById(R.id.controlGrid);
        gridView.setLayoutAnimation(new ControlPadAnimation(this));

        final ViewTreeObserver observer = gridView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setContainerHeight(gridView.getHeight());
                if(numberAdapter==null){
                    numberAdapter = new NumberPanelAdapter(mContext,getContainerHeight(), NumberUtils.numberControls);
                }
                gridView.setAdapter(numberAdapter);
                gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GridBlockI block = (GridBlockI) view;
                block.onClick();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateNumber(String number) {
        if(numberTextView.getText().toString().equals("0"))
            numberTextView.setText(number);
        else
            numberTextView.append(number);
    }

    @Override
    public void clearNumber(boolean clearAll) {
        if(clearAll){
            numberTextView.setText("0");
        }else{
            if(numberTextView.getText().length()>1){
                numberTextView.setText(numberTextView.getText().toString().substring(0,numberTextView.getText().toString().length()-1));
            }else{
                numberTextView.setText("0");
            }
        }
    }

    @Override
    public void setOptionsAdapter() {
        if(optionsAdapter == null){
            optionsAdapter = new NumberPanelAdapter(this,getContainerHeight(),NumberUtils.optionControls);
        }
        gridView.setAdapter(optionsAdapter);
        
    }

    public int getContainerHeight() {
        return containerHeight;
    }

    public void setContainerHeight(int containerHeight) {
        this.containerHeight = containerHeight;
    }
}
