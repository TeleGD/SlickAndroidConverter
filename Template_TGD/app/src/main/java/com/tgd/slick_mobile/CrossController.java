package com.tgd.slick_mobile;

import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;


/**
 * Crée par Jerome le 19/10/2017 pour le projet TGD_GAME
 */

public class CrossController {

    private static final Color COLOR_PRESSED = new Color(150,255,150,255);
    private static final Color COLOR_DEFAULT = new Color(255,255,255,235);

    public boolean stickActive = false;

    private float CENTER_X = Conf.LONGUEUR*0.15f;
    private float CENTER_Y = Conf.HAUTEUR*0.45f;


    private static final float TAILLE = Conf.LONGUEUR*0.050f;
    private static final float ECART_CENTRE = (TAILLE*1.3f)/2;
    private static final float ROUND = 2;
    private static final float SCALE_FACTOR = 1.1f;


    private int indexCross=0;

    private boolean upPressed,downPressed,rightPressed,leftPressed;
    private GameContainer gameContainer;

    public CrossController(int position){
        indexCross = position;

        if(indexCross == 1){
            CENTER_X = Conf.LONGUEUR*0.85f;
        }

    }


    public void render(Graphics g) {
        g.setColor(new Color(200,200,200,200));

        g.setLineWidth(TAILLE/3);

        g.drawRect(CENTER_X+ECART_CENTRE,CENTER_Y-TAILLE/2,TAILLE*SCALE_FACTOR,TAILLE);
        g.drawRect(CENTER_X-ECART_CENTRE-TAILLE*SCALE_FACTOR,CENTER_Y-TAILLE/2,TAILLE*SCALE_FACTOR,TAILLE);
        g.drawRect(CENTER_X-TAILLE/2,CENTER_Y+ECART_CENTRE,TAILLE,TAILLE*SCALE_FACTOR);
        g.drawRect(CENTER_X-TAILLE/2,CENTER_Y-ECART_CENTRE-TAILLE*SCALE_FACTOR,TAILLE,TAILLE*SCALE_FACTOR);


        g.resetLineWidth();


        if(rightPressed)g.setColor(COLOR_PRESSED);
        else g.setColor(COLOR_DEFAULT);

        g.fillRect(CENTER_X+ECART_CENTRE,CENTER_Y-TAILLE/2,TAILLE*SCALE_FACTOR,TAILLE);


        if(leftPressed)g.setColor(COLOR_PRESSED);
        else g.setColor(COLOR_DEFAULT);

        g.fillRect(CENTER_X-ECART_CENTRE-TAILLE*SCALE_FACTOR,CENTER_Y-TAILLE/2,TAILLE*SCALE_FACTOR,TAILLE);

        if(downPressed)g.setColor(COLOR_PRESSED);
        else g.setColor(COLOR_DEFAULT);

        g.fillRect(CENTER_X-TAILLE/2,CENTER_Y+ECART_CENTRE,TAILLE,TAILLE*SCALE_FACTOR);


        if(upPressed)g.setColor(COLOR_PRESSED);
        else g.setColor(COLOR_DEFAULT);

        g.fillRect(CENTER_X-TAILLE/2,CENTER_Y-ECART_CENTRE-TAILLE*SCALE_FACTOR,TAILLE,TAILLE*SCALE_FACTOR);
        g.resetLineWidth();

    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i){
        this.gameContainer = gameContainer;
    }

    public boolean onTouch(MotionEvent motionEvent) {

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN ){
            int pointerIndex = motionEvent.getActionIndex();

            float x = motionEvent.getX(pointerIndex) / Utils.factorX;
            float y = motionEvent.getY(pointerIndex) / Utils.factorY;

            if(x>CENTER_X+ECART_CENTRE && x<CENTER_X+ECART_CENTRE+TAILLE*SCALE_FACTOR &&
                    y>CENTER_Y-TAILLE/2 && y<CENTER_Y-TAILLE/2+TAILLE){
                rightPressed = true;
                StateBasedGame.currentState.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }

            if(x>CENTER_X-ECART_CENTRE-TAILLE*SCALE_FACTOR && x<CENTER_X-ECART_CENTRE &&
                    y>CENTER_Y-TAILLE/2 && y<CENTER_Y+TAILLE/2){
                leftPressed = true;
                StateBasedGame.currentState.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }

            if (x > CENTER_X - TAILLE / 2 && x < CENTER_X + TAILLE / 2 &&
                    y > CENTER_Y + ECART_CENTRE && y < CENTER_Y + ECART_CENTRE + TAILLE * SCALE_FACTOR) {
                downPressed = true;
                StateBasedGame.currentState.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }

            if(x>CENTER_X-TAILLE/2 && x<CENTER_X+TAILLE/2 &&
                    y>CENTER_Y-ECART_CENTRE-TAILLE*SCALE_FACTOR && y<CENTER_Y-ECART_CENTRE){
                upPressed = true;
                StateBasedGame.currentState.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;

            }

            return false;
        }else if(motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL ){
            int pointerIndex = motionEvent.getActionIndex();

            float x = motionEvent.getX(pointerIndex) / Utils.factorX;
            float y = motionEvent.getY(pointerIndex) / Utils.factorY;



            rightPressed = false;
            upPressed = false;
            downPressed = false;
            leftPressed = false;

        }
        return false;

    }

    private double distance(float x, float y, float x2, float y2) {
        return Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y2,2));
    }


    public void refreshInput(Input input) {
        if(leftPressed)input.crossLeft();
        if(rightPressed)input.crossRight();
        if(upPressed)input.crossUp();
        if(downPressed)input.crossDown();
    }
}
