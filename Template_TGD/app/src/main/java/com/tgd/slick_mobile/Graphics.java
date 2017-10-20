package com.tgd.slick_mobile;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;


/**
 * Created by Jerome on 23/03/2017.
 */

public class Graphics{

    private static final String TAG = "Graphics";
    private Paint paint;
    private Canvas canvas;
    private TrueTypeFont font;
    private float defaultLineWidth;

    public Graphics(Canvas canvas){
        this.canvas=canvas;
        this.paint=new Paint();
        paint.setAntiAlias(true);
        defaultLineWidth = paint.getStrokeWidth();
    }

    public void setColor(Color color) {
        int rgba= android.graphics.Color.argb(color.getAlpha(),color.getRed(),color.getGreen(),color.getBlue());
        paint.setColor(rgba);
    }

    public void drawRect(float x, float y, float width, float height) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(x*Utils.factorX, y*Utils.factorY,(x+width)*Utils.factorX,(y+height)*Utils.factorY,paint);
    }

    public void drawOval(float x, float y, float r1) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x*Utils.factorX,y*Utils.factorY,r1*Utils.factorX,paint);
    }

    public void fillOval(float x, float y, float r1) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x*Utils.factorX,y*Utils.factorY,r1*Utils.factorX,paint);
    }

    public void fillRect(float x, float y, float width, float height) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x*Utils.factorX, y*Utils.factorY,(x+width)*Utils.factorX,(y+height)*Utils.factorY,paint);
    }

    public void drawImage(Image image, float x, float y) {
        if(image.getBitmap()!=null){

            Matrix matrix=image.getMatrix();
            matrix.postScale(Utils.factorX,Utils.factorY);
            matrix.postTranslate(x*Utils.factorX,y*Utils.factorY);
            canvas.drawBitmap(image.getBitmap(),matrix,paint);
        }else{
            Log.e(TAG,"Cannot draw a null image name : "+image.getName());
        }
    }
    public void drawImage(Image image,float x, float y, float width, float height) {
        if(image.getBitmap()!=null){

            drawImage(image.getScaledCopy((int)width,(int)height),x,y);
        }else{
            Log.e(TAG,"Cannot draw a null image name : "+image.getName());
        }
    }
    public void drawString(String str, float x, float y) {
        if(font==null){
            setFont(new TrueTypeFont(new Font("Arial", Font.PLAIN, 20)));
        }

        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(str, x*Utils.factorX
                , y*Utils.factorY +paint.getTextSize()  , paint);

    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setFont(TrueTypeFont font) {
        if(font.getFont()==null)return;
        this.font = font;
        paint.setTypeface(font.getTypeface());
        paint.setTextSize(Utils.factorY*font.getSize());
    }

    public void fillRoundRect(float x, float y, float width, float height, float rx, float ry) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(x*Utils.factorX,y*Utils.factorY,width*Utils.factorX,height*Utils.factorY,rx,ry,paint);
        }else{
            fillRect(x,y,width,height);
        }
    }

    public void drawRoundRect(float x, float y, float width, float height, float rx, float ry) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(x*Utils.factorX,y*Utils.factorY,width*Utils.factorX,height*Utils.factorY,rx,ry,paint);
        }else{
            fillRect(x,y,width,height);
        }
    }

    public void fillRoundRect(float x, float y, float width, float height, float rx) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(x*Utils.factorX,y*Utils.factorY,width*Utils.factorX,height*Utils.factorY,rx,rx,paint);
        }else{
            fillRect(x,y,width,height);
        }
    }


    public void setBackground(Color background) {
        setColor(background);
        fillRect(0,0,BasicGameState.currentContext().getResources().getDisplayMetrics().widthPixels,BasicGameState.currentContext().getResources().getDisplayMetrics().heightPixels);
    }

    public void translate(float v, int i) {
    }

    public void resetFont() {
        setFont(new TrueTypeFont(new Font("Arial", Font.PLAIN, 20)));
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setAntiAlias(boolean antiAlias) {
        paint.setAntiAlias(antiAlias);
    }

    public void resetLineWidth() {
        paint.setStrokeWidth(defaultLineWidth);
    }

    public void setLineWidth(float lineWidth) {
        paint.setStrokeWidth(lineWidth);
    }

    public void drawRoundRect(float x, float y, float width, float height, int cornerRadius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(x*Utils.factorX,y*Utils.factorY,width*Utils.factorX,height*Utils.factorY,cornerRadius,cornerRadius,paint);
        }else{
            fillRect(x,y,width,height);
        }
    }

}
