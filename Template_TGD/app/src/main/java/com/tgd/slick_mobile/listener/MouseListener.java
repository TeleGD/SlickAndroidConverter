package com.tgd.slick_mobile.listener;

import com.tgd.slick_mobile.Input;

/**
 * Crée par Jerome le 03/10/2017 pour le projet TGD_GAME
 */

public interface MouseListener {
    void mouseDragged(int arg0, int arg1, int arg2, int arg3);
    void mouseMoved(int arg0, int arg1, int arg2, int arg3) ;
    void mousePressed(int type, int x, int y) ;
    void mouseReleased(int type, int x, int y) ;
    void mouseClicked(int type, int x, int y,int count) ;
    void mouseWheelMoved(int type) ;
    void inputEnded();
    void inputStarted();
    boolean isAcceptingInput();
     void setInput(Input arg0);

}
