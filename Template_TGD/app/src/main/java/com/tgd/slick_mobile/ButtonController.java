package com.tgd.slick_mobile;

import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;

/**
 * Crée par Jerome le 19/10/2017 pour le projet TGD_GAME
 */

public class ButtonController {

    private float CENTER_X = Conf.LONGUEUR*0.90f;
    private float CENTER_Y = Conf.HAUTEUR*0.55f;
    private static final float RADIUS = (float) (Conf.HAUTEUR*0.05f);

    private int indexButton;
    private GameContainer gameContainer;
    private boolean pressed = false;

    public ButtonController(int indexButton){
        this.indexButton=indexButton;

        if(indexButton == 1)CENTER_X = Conf.LONGUEUR*0.82f;
        else if(indexButton == 2)CENTER_X = Conf.LONGUEUR*0.74f;

    }



    public void render(Graphics g) {
        g.setColor(new Color(160,160,160,200));
        g.setLineWidth(10);
        g.drawOval(CENTER_X,CENTER_Y, RADIUS);
        g.setColor(new Color(170,170,170,100));
        g.fillOval(CENTER_X,CENTER_Y,RADIUS);
        g.setColor(new Color(255,255,255,255));
        g.setFont(new TrueTypeFont(new Font("Arial",Font.BOLD,35)));
        g.drawString(getNameButton(),CENTER_X-g.getFont().getWidth(getNameButton())/2,CENTER_Y-g.getFont().getHeight(getNameButton()));
        g.resetLineWidth();
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int k) {
        this.gameContainer = gameContainer;
    }

    public void refreshInput(Input input) {
        if(pressed)input.pressButton(indexButton);
    }

    public String getNameButton() {
        if(indexButton ==0)return "A";
        else if(indexButton == 1)return "B";
        else if(indexButton == 2)return "C";
        return "D";
    }

    public boolean onTouch(MotionEvent motionEvent) {

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN ){
            int pointerIndex = motionEvent.getActionIndex();

            float x = motionEvent.getX(pointerIndex) / Utils.factorX;
            float y = motionEvent.getY(pointerIndex) / Utils.factorY;

            if(distance(x,y,CENTER_X,CENTER_Y)<RADIUS)
            {
                gameContainer.getInput().pressButton(indexButton);
                StateBasedGame.currentState.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                pressed = true;
                return true;
            }

        }else if(motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL ){

            if(pressed) gameContainer.getInput().releaseButton(indexButton);
            pressed = false;

            int pointerIndex = motionEvent.getActionIndex();

            float x = motionEvent.getX(pointerIndex) / Utils.factorX;
            float y = motionEvent.getY(pointerIndex) / Utils.factorY;


        }
        return false;
    }

    private double distance(float x, float y, float x2, float y2) {
        return Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y2,2));
    }

}

