package com.jobinbasani.numbertowords;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.TextView;

import com.jobinbasani.numbertowords.components.ConfigCell;
import com.jobinbasani.numbertowords.components.ControlPadAnimation;
import com.jobinbasani.numbertowords.components.GridNumberCell;
import com.jobinbasani.numbertowords.components.interfaces.NumberTransformerI;
import com.jobinbasani.numbertowords.config.NumberUtils;


public class MainActivity extends ActionBarActivity implements NumberTransformerI, TextWatcher {

    private Context mContext = this;
    private TextView numberTextView;
    private TextView wordTextView;
    private int cellHeight;
    private int cellWidth;
    private GridLayout gridLayout;
    private LruCache<String, View> viewCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewCache = new LruCache<String, View>(32);
        setContentView(R.layout.activity_main);
        numberTextView = (TextView) findViewById(R.id.numberText);
        wordTextView = (TextView) findViewById(R.id.wordText);
        gridLayout = (GridLayout) findViewById(R.id.controlGrid);
        gridLayout.setLayoutAnimation(new ControlPadAnimation(this));

        final ViewTreeObserver observer = gridLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setCellHeight(gridLayout.getHeight()/NumberUtils.CELL_HEIGHT_DIVISOR);
                setCellWidth(gridLayout.getWidth()/NumberUtils.CELL_HEIGHT_DIVISOR);
                updatePanel(NumberUtils.numberControls, false);
                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        numberTextView.addTextChangedListener(this);
    }

    @Override
    public void updatePanel(String[] cells, boolean animate) {
        gridLayout.removeAllViews();
        for(int i=0;i<cells.length;i++){
            gridLayout.addView(getConfigCell(cells[i], i));
        }
        if(animate)
            gridLayout.startLayoutAnimation();
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
        else if(numberTextView.getText().toString().length()<12)
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

    private View getConfigCell(String config, int index){
        GridNumberCell configCell = (GridNumberCell) viewCache.get(index+"_"+config.hashCode());
        if(configCell!=null) return configCell;

        if(NumberUtils.isNumber(config) || NumberUtils.BLANK.equals(config))
            configCell = new GridNumberCell(mContext,getCellHeight(),getCellWidth());
        else
            configCell = new ConfigCell(mContext, getCellHeight(),getCellWidth());

        configCell.setText(config);
        viewCache.put(index+"_"+config.hashCode(),configCell);
        return configCell;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        wordTextView.setText(NumberUtils.convert(new Long(numberTextView.getText().toString())));
    }
}
