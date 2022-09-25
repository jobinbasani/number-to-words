package com.jobinbasani.numbertowords;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.LruCache;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jobinbasani.numbertowords.components.ConfigCell;
import com.jobinbasani.numbertowords.components.ControlPadAnimation;
import com.jobinbasani.numbertowords.components.GridNumberCell;
import com.jobinbasani.numbertowords.components.GridTextCell;
import com.jobinbasani.numbertowords.components.interfaces.NumberTransformerI;
import com.jobinbasani.numbertowords.config.NumberUtils;

import java.text.DecimalFormat;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NumberTransformerI, TextWatcher, TextToSpeech.OnInitListener {

    private final Context mContext = this;
    private TextView numberTextView;
    private TextView wordTextView;
    private int cellHeight;
    private int totalHeight;
    private int totalWidth;
    private int cellWidth;
    private GridLayout gridLayout;
    private LruCache<String, View> viewCache;
    private TextToSpeech tts;
    private boolean ttsEnabled = false;
    private final DecimalFormat formatter = new DecimalFormat("###,###,###,###,###");
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        tts = new TextToSpeech(this,this);
        viewCache = new LruCache<>(32);
        setContentView(R.layout.activity_main);
        numberTextView = findViewById(R.id.numberText);
        wordTextView = findViewById(R.id.wordText);
        gridLayout = findViewById(R.id.controlGrid);
        gridLayout.setLayoutAnimation(new ControlPadAnimation(this));

        final ViewTreeObserver observer = gridLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                totalHeight = gridLayout.getHeight();
                totalWidth = gridLayout.getWidth();
                cellHeight = totalHeight/NumberUtils.CELL_HEIGHT_DIVISOR;
                cellWidth = totalWidth/NumberUtils.CELL_HEIGHT_DIVISOR;
                updatePanel(NumberUtils.numberControls, false);
                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        numberTextView.addTextChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
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
    public void speakNumberText() {
        if(ttsEnabled){
            if(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)<3){
                Toast.makeText(this,"Please turn up the volume...",Toast.LENGTH_SHORT).show();
            }
            tts.speak(wordTextView.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
        }else{
            Toast.makeText(this,"Unable to use Text To Speech!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void copyToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText("word_text",getShareText()));
        Toast.makeText(this,"Copied to clipboard...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getShareText() {
        return numberTextView.getText()+" - "+wordTextView.getText();
    }

    @Override
    public void updateNumber(String number) {
        String plainNumber = numberTextView.getText().toString().replaceAll(",","");
        if(numberTextView.getText().toString().equals("0"))
            numberTextView.setText(number);
        else if(plainNumber.length()<12){
            numberTextView.setText(getFormattedNumber(Long.parseLong(plainNumber + number)));
        }
    }

    @Override
    public void clearNumber(boolean clearAll) {
        if(clearAll){
            numberTextView.setText("0");
        }else{
            String plainNumber = numberTextView.getText().toString().replaceAll(",","");
            if(plainNumber.length()>1){
                numberTextView.setText(getFormattedNumber(Long.parseLong(plainNumber.substring(0, plainNumber.length() - 1))));
            }else{
                numberTextView.setText("0");
            }
        }
    }

    private String getFormattedNumber(long number){
        return formatter.format(number);
    }

    private View getConfigCell(String config, int index){
        GridNumberCell gridCell = (GridNumberCell) viewCache.get(index+"_"+config.hashCode());
        if(gridCell!=null) return gridCell;

        if((index+1)%4 != 0){
            if(NumberUtils.isNumber(config)){
                gridCell = new GridNumberCell(mContext,getCellHeight(index>11),getCellWidth(true));
            }else{
                gridCell = new GridTextCell(mContext,getCellHeight(index>11),getCellWidth(true));
            }
        }
        else
            gridCell = new ConfigCell(mContext, getCellHeight(index>11),getCellWidth(false));

        gridCell.setText(config);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setGravity(Gravity.CENTER);

        gridCell.setLayoutParams(params);

        viewCache.put(index+"_"+config.hashCode(),gridCell);
        return gridCell;
    }

    public int getCellWidth(boolean isLastColumn) {
        if(isLastColumn) return totalWidth - (cellWidth * (NumberUtils.CELL_HEIGHT_DIVISOR-1));
        return cellWidth;
    }

    public int getCellHeight(boolean isLastRow) {
        if(isLastRow) return totalHeight - (cellHeight * (NumberUtils.CELL_HEIGHT_DIVISOR-1));
        return cellHeight;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        wordTextView.setText(NumberUtils.convert(Long.parseLong(numberTextView.getText().toString().replaceAll(",",""))));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        numberTextView.setText(savedInstanceState.getString(NumberUtils.NUMBER_BUNDLE_KEY,"0"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(NumberUtils.NUMBER_BUNDLE_KEY,numberTextView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "This Language is not supported", Toast.LENGTH_LONG).show();
            } else {
                ttsEnabled = true;
            }

        } else {
            Toast.makeText(this,"Text To Speech initialization Failed!",Toast.LENGTH_LONG).show();
        }
    }

}
