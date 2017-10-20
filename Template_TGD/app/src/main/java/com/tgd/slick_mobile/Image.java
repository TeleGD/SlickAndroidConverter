package com.tgd.slick_mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Debug;
import android.util.Log;


/**
 * Created by Jerome on 23/03/2017.
 */

public class Image {
    private static final String TAG = "Image";

    private Context context;
    private String name;
    private Bitmap bitmap;
    private float cx=-1,cy=-1;
    private float rotation=0;

    public Image(String name)  throws SlickException {
        Log.d(TAG,"New Image Created : "+name);
        this.context=BasicGameState.currentContext();
        setImageName(name);
    }

    public Image(String name, Bitmap scaledBitmap) {
        this.name=name;
        bitmap=scaledBitmap;
        this.context=BasicGameState.currentContext();

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setImageName(String name)  throws SlickException {

        name=name.replace("./Images/","");
        name=name.replace("Images/","");
        name=name.replace("images/","");
        name=name.replace("image/","");
        name=name.replace("/","_");
        name=name.replace(" ","_");
        name=name.replace("-","_").replace(".png","").toLowerCase();
        Log.d(TAG,"name = "+name);

        this.name=name;

        int resID = context.getResources().getIdentifier(name , "drawable", context.getPackageName());
        if(resID!=-1){
            bitmap= BitmapFactory.decodeResource(context.getResources(),resID);
        }else{
            Log.d(TAG,"Erreur lors du chargement de l'image "+name);
            throw new SlickException("Erreur lors du chargement de l'image "+name);
        }

    }

    public String getName() {
        return name;
    }

    public Image getScaledCopy(float width, float height) {
        if(bitmap==null){
            Log.e(TAG,"Bitmap is null cannot created copy");
            return this;
        }
        return new Image(name, Bitmap.createScaledBitmap(bitmap,(int)width,(int) height,false));
    }

    public void setRotation(float rotation) {
        this.rotation=rotation;
    }

    public void setCenterOfRotation(int cx, int cy) {
        this.cx=cx;
        this.cy=cy;
    }

    public Matrix getMatrix(){
        if(cx==-1)cx=bitmap.getWidth()/2;
        if(cy==-1)cy=bitmap.getHeight()/2;

        Matrix matrix=new Matrix();
        matrix.setRotate(rotation,cx,cy);
        return matrix;
    }

    public void draw(float x, float y, float x2, float y2) {
        BasicGameState.currentGraphic().drawImage(this,x,y,x2,y2);
    }

    public float getWidth() {
        return bitmap.getWidth();
    }

    public float getHeight() {
        return bitmap.getHeight();
    }
}
