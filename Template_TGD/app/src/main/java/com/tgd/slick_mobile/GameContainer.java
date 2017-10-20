package com.tgd.slick_mobile;



/**
 * Created by Jerome on 23/03/2017.
 */

public  class GameContainer {

    private static final String TAG = "BasicGameState";
    private boolean showFPS;
    private Input input;

    public GameContainer(){
        input = new Input();
    }

    public void setShowFPS(boolean showFPS) {
        this.showFPS = showFPS;
    }
    public int getHeight() {
        return (int) (Conf.HAUTEUR*Utils.factorY);
    }
    public int getWidth() {
        return (int) (Conf.LONGUEUR*Utils.factorX);
    }

    public Input getInput() {
        return input;
    }
}
