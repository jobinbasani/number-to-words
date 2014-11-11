package com.jobinbasani.numbertowords;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.jobinbasani.numbertowords.adapters.NumberPanelAdapter;


public class MainActivity extends ActionBarActivity {

    private Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView gridView = (GridView) findViewById(R.id.controlGrid);

        //addGridElements();
        System.out.println(gridView.getHeight()+"****");
        final ViewTreeObserver observer = gridView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println("from listener  "+gridView.getHeight());
                gridView.setAdapter(new NumberPanelAdapter(mContext,gridView.getHeight()));
                gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /*public void addGridElements(){
        GridLayout gridControl = (GridLayout) findViewById(R.id.controlGrid);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setGravity(Gravity.FILL);
        for(int i=0;i<16;i++){
            TextView textView = new TextView(this);
            textView.setText(i+" pos");
            gridControl.addView(textView,i,params);
        }
    }*/

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
}
