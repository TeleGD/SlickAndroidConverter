package com.tgd.slick_mobile;

/**
 * Crée par Jerome le 03/10/2017 pour le projet TGD_GAME
 */

public class Shape {
    public  float x,y,width,height;

    public Shape(float x, float y, float width, float height) {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    public float getX() {
        return x;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean intersects(Rectangle r) {
        if(r.getX()>x+width)return false;
        if(r.getX()+r.getWidth()<x)return false;
        if(r.getY()>y+height)return false;
        if(r.getY()+r.getHeight()<y)return false;

        return true;
    }


    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setX(float x) {
        this.x = x;
    }
}
