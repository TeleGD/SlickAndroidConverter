package com.tgd.slick_mobile;

import android.view.MotionEvent;

/**
 * Crée par Jerome le 19/10/2017 pour le projet TGD_GAME
 */

public class TGDController {

    private StickController stickDroit = new StickController(0);
    private StickController stickGauche = new StickController(1);
    private CrossController cross = new CrossController(0);
    private ButtonController[] buttons = new ButtonController[2];

    private int comptFrame = 0;

    public TGDController()
    {
        for(int i=0;i<buttons.length;i++)buttons[i] = new ButtonController(i);
    }
    public void render(Graphics g) {
        cross.render(g);
        stickGauche.render(g);
        stickDroit.render(g);

        for(int i=0;i<buttons.length;i++)buttons[i].render(g);

    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int k) {
        cross.update(gameContainer, stateBasedGame, k);
        stickGauche.update(gameContainer, stateBasedGame, k);
        stickDroit.update(gameContainer, stateBasedGame, k);

        for(int i=0;i<buttons.length;i++)buttons[i].update(gameContainer, stateBasedGame, k);

        if(comptFrame==5){
            stickGauche.refreshInput(gameContainer.getInput());
            stickDroit.refreshInput(gameContainer.getInput());
            cross.refreshInput(gameContainer.getInput());

            for(int i=0;i<buttons.length;i++)buttons[i].refreshInput(gameContainer.getInput());

            comptFrame=0;
        }
        comptFrame++;


    }

    public boolean onTouch(MotionEvent motionEvent) {
        boolean test =  stickGauche.onTouch(motionEvent) || stickDroit.onTouch( motionEvent) || cross.onTouch(motionEvent);

        if(test)return true;

        for(int i=0;i<buttons.length;i++){
            if(buttons[i].onTouch(motionEvent))return true;
        }

        return false;
    }
}
