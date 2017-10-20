package com.tgd.slick_mobile;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

/**
 * Crée par Jerome le 31/03/2017 pour le projet TestAndroidSlick
 */

public class TrueTypeFont {

    private static final String TAG = "TrueTypeFont";
    private  Font font;
    private Typeface typeface;

    public TrueTypeFont(Font fontTemp, boolean b) {
        this.font=fontTemp;
    }

    public TrueTypeFont(Font fontTemp) {
        this.font=fontTemp;
    }


    public TrueTypeFont deriveFont(int type, int size) {
        TrueTypeFont ttf=new TrueTypeFont(this.font);
        ttf.setSize(size);
        ttf.setType(type);
        return ttf;
    }

    public float getSize() {
        return font.getSize();
    }

    public Font getFont() {
        return font;
    }

    public void setSize(int size) {
        this.font.setSize(size);
    }

    public void setType(int type) {
        this.font.setType(type);
    }

    public int getHeight(String str) {
        Paint p=new Paint();
        p.setTextSize(font.getSize());
        Rect bounds = new Rect();
        p.getTextBounds(str, 0, str.length(), bounds);
        int height = bounds.height();
        int width = bounds.width();
        return (int) (height);
    }

    public int getWidth(String str) {
        Paint p=new Paint();
        p.setTextSize(font.getSize());
        Rect bounds = new Rect();
        p.getTextBounds(str, 0, str.length(), bounds);
        int width = bounds.width();
        //Log.d(TAG,"str = "+str +" witdh = "+width);
        return (int) (width);

    }

    public Typeface getTypeface() {
        return typeface;
    }
}
