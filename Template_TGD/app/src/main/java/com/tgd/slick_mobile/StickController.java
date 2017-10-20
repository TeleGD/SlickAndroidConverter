package com.tgd.slick_mobile;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;


/**
 * Crée par Jerome le 19/10/2017 pour le projet TGD_GAME
 */

public class StickController {

    public boolean stickActive = false;

    private float CENTER_X = Conf.LONGUEUR*0.85f;
    private float CENTER_Y = Conf.HAUTEUR*0.80f;

    private static final float RADIUS = (float) (Conf.HAUTEUR*0.10f);
    private static final float RADIUS_STICK = RADIUS *0.6f;

    private double angle = 0;
    private double distance = 0;

    private float xStick= CENTER_X , yStick=CENTER_Y;
    private int indexAnalogue=0;
    private int pointerId = -1;

    public StickController(int position){
        indexAnalogue = position;

        if(indexAnalogue == 1){
            CENTER_X = Conf.LONGUEUR*0.15f;
        }

        xStick = CENTER_X;
    }


    public void render(Graphics g) {
        g.setColor(new Color(160,160,160,200));
        g.setLineWidth(10);
        g.drawOval(CENTER_X,CENTER_Y, RADIUS);
        g.setColor(new Color(170,170,170,100));
        g.fillOval(CENTER_X,CENTER_Y,RADIUS);

        g.fillOval(xStick,yStick, RADIUS_STICK*1.2f);

        //g.fillOval(CENTER_X,CdrawControllerENTER_Y, RADIUS_STICK*0.5f);

        g.setColor(new Color(200,200,200,255));

        g.fillOval(xStick,yStick, RADIUS_STICK);

        g.resetLineWidth();

    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i){

    }

    public boolean onTouch(MotionEvent motionEvent) {

        int action = motionEvent.getActionMasked();

        switch (action){

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {

                int pointerIndex = motionEvent.getActionIndex();
                int pointerId = motionEvent.getPointerId(pointerIndex);

                float x = motionEvent.getX(pointerIndex) / Utils.factorX;
                float y = motionEvent.getY(pointerIndex) / Utils.factorY;
                    if(this.pointerId!=-1)return false; // si on a deja affecte le pointer ca sert donc a rien

                    if (distance(x, y, xStick, yStick) < RADIUS_STICK) {
                        stickActive = true;
                        StateBasedGame.currentState.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                        this.pointerId = pointerId;
                        Log.d("StickController", "DOWN pointerIndex= " + pointerIndex + "   pointerId = " + pointerId);
                        return true;
                    } else {
                        stickActive = false;
                    }

            }

            case MotionEvent.ACTION_MOVE:{
                if(pointerId == -1)return false;

                final int pointerCurrent = motionEvent.findPointerIndex(pointerId);

                float x = motionEvent.getX(pointerCurrent)/Utils.factorX;
                float y = motionEvent.getY(pointerCurrent)/Utils.factorY;

                if(stickActive){

                    distance = distance(x,y,CENTER_X,CENTER_Y);
                    if(distance< RADIUS){

                        xStick = x;
                        yStick = y;
                    }else{
                        double opposee = y-CENTER_Y;
                        double adjacent = x-CENTER_X;
                        if(adjacent == 0){
                            yStick = CENTER_Y + y>CENTER_Y?RADIUS:-RADIUS;
                            xStick = CENTER_X;
                        }else{
                            angle = Math.atan(opposee/adjacent);
                            if(adjacent>0){
                                xStick = CENTER_X +  (float) (RADIUS*Math.cos(angle));
                                yStick = CENTER_Y +  (float) (RADIUS*Math.sin(angle));
                            }else{
                                xStick = CENTER_X -  (float) (RADIUS*Math.cos(angle));
                                yStick = CENTER_Y +  (float) (RADIUS*Math.sin(-angle));
                            }

                        }
                    }
                }else{

                }

                return false;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_UP: {
                if (pointerId == motionEvent.getPointerId(motionEvent.getActionIndex())) {
                    pointerId = -1;
                    xStick = CENTER_X;
                    yStick = CENTER_Y;
                    stickActive = false;
                }

                return false;
            }
        }

        return false;

    }

    private double distance(float x, float y, float x2, float y2) {
        return Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y2,2));
    }


    public void refreshInput(Input input) {

        if(xStick>CENTER_X+RADIUS/5) input.analogRight(indexAnalogue,(xStick-CENTER_X)/RADIUS);
        if(yStick>CENTER_Y+RADIUS/5) input.analogDown(indexAnalogue,(yStick-CENTER_Y)/RADIUS);
        if(xStick<CENTER_X-RADIUS/5) input.analogLeft(indexAnalogue,(CENTER_X-xStick)/RADIUS);
        if(yStick<CENTER_Y-RADIUS/5) input.analogUp(indexAnalogue,(CENTER_Y-yStick)/RADIUS);

    }
}
