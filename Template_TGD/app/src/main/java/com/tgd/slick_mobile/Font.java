package com.tgd.slick_mobile;

import android.graphics.Typeface;

import java.io.IOException;
import java.io.InputStream;

/**
 * Crée par Jerome le 31/03/2017 pour le projet TestAndroidSlick
 */

public class Font {
    public static final int BOLD = 0;
    public static final int PLAIN = 1 ;
    public static final String TRUETYPE_FONT = "";
    private final String name;
    private Typeface typeface;

    private  int size;
    private int type;

    public Font(String name, Typeface tf,int type, int size) {
        this(name,type,size);
        this.typeface = tf;
    }

    public Font(String name, int type, int size) {
        this.size=size;
        this.type=type;
        this.name=name;
        this.typeface = Typeface.DEFAULT;
    }

    public static Font createFont(String name) {
        return new Font(name,0,20);
    }

    public static Font createFont(String name, Typeface typeface) throws FontFormatException,IOException{
        return new Font(name,typeface,0,20);
    }

    public float getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Font deriveFont(int type, int size) {
        Font font = Font.createFont(this.name);
        font.setSize(size);
        font.setType(type);
        return font;
    }
}
