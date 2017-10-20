package com.tgd.slick_mobile;

/**
 * Crée par Jerome le 31/03/2017 pour le projet TestAndroidSlick
 */

public class Rectangle extends Shape{

    public Rectangle(float x, float y, float width, float height) {
        super(x,y,width,height);
    }



    public void setCenterX(float centerX) {
        this.x=  centerX-width/2;
    }
    public void setCenterY(float centerY) {
        this.y=  centerY-height/2;
    }

    public boolean contains(float x,float y){
        if(getX()>x)return false;
        if(getX()+getWidth()<x)return false;
        if(getY()>y)return false;
        if(getY()+getHeight()<y)return false;

        return true;
    }

}
