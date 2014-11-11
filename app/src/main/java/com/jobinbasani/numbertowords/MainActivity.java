package com.jobinbasani.numbertowords;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.jobinbasani.numbertowords.adapters.NumberPanelAdapter;
import com.jobinbasani.numbertowords.components.GridNumberCell;


public class MainActivity extends ActionBarActivity {

    private Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridLayout gridLayout = (GridLayout) findViewById(R.id.controlGrid);
        final ViewTreeObserver observer = gridLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println("from listener  " + gridLayout.getHeight());
                System.out.println("from listener width " + gridLayout.getWidth());
                addGridElements(gridLayout.getHeight(),gridLayout.getWidth());
                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

       // gridLayout.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(mContext,R.anim.alpha_anim)));

        /*System.out.println(gridView.getHeight()+"****");
        final GridView gridView = (GridView) findViewById(R.id.controlGrid);
        final ViewTreeObserver observer = gridView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println("from listener  "+gridView.getHeight());
                gridView.setAdapter(new NumberPanelAdapter(mContext,gridView.getHeight()));
                gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });*/
    }

    public void addGridElements(int height, int width){
        GridLayout gridControl = (GridLayout) findViewById(R.id.controlGrid);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setGravity(Gravity.FILL);
        for(int i=0;i<16;i++){
            GridNumberCell cell = new GridNumberCell(mContext,height,width);
            cell.setText(i+"h");
            gridControl.addView(cell);
        }
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
}
