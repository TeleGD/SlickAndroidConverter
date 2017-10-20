package com.tgd.slick_mobile;

import com.tgd.multigame_design.general.ui.TGDComponent;
import com.tgd.slick_mobile.listener.KeyListener;
import com.tgd.slick_mobile.listener.MouseListener;

import java.security.Key;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Crée par Jerome le 03/10/2017 pour le projet TGD_GAME
 */

public class Input {
    public static final int NONE = -1 ;
    public static final int BUTTON_UP = 9;
    public static final int BUTTON_RIGHT = 10;
    public static final int BUTTON_LEFT = 11;
    public static final int BUTTON_DOWN = 12;
    public static final int BUTTON_BACK = 13;
    public static final int PRESS_SCREEN_DOWN = 14;
    public static final int ANAL_LEFT = 15;
    public static final int ANAL_RIGHT = 16;
    public static final int ANAL_UP = 17;
    public static final int ANAL_DOWN = 18;
    public static final int SWIPE_LEFT = 19;
    public static final int SWIPE_RIGHT = 20;
    public static final int SWIPE_UP = 21;
    public static final int SWIPE_DOWN = 22;

    public static final int BUTTON_1 = 23;
    public static final int BUTTON_2 = 24;
    public static final int BUTTON_3= 25;
    
    public static final ArrayList<MouseListener> mouseListeners = new ArrayList<>();
    public static final ArrayList<KeyListener> keyListeners = new ArrayList<>();
    
    public void addMouseListener(MouseListener mouseListener) {
        mouseListeners.add(mouseListener);
    }
    
    public void removeMouseListener(MouseListener mouseListener){
        mouseListeners.remove(mouseListener);
    }
    
    public void addKeyListener(KeyListener keyListener) {
        keyListeners.add(keyListener);
    }
    
    public void removeKeyListener(KeyListener keyListener){
        keyListeners.remove(keyListener);
    }
    
    
    public void moveLeft(){
        
    }

    public void moveRight(){
        
    }
    
    public void pressScreen() {
        for(int i=0;i<keyListeners.size();i++){
            keyListeners.get(i).keyPressed(PRESS_SCREEN_DOWN, (char) 0);
        }
    }
    
    public void releaseScreen() {
        for(int i=0;i<keyListeners.size();i++){
            keyListeners.get(i).keyReleased(PRESS_SCREEN_DOWN, (char) 0);
        }
    }
    
    public void swipeUp() {
        notifyListeners(SWIPE_UP);
    }
    public void swipeDown() {
        notifyListeners(SWIPE_DOWN);
    }
    public void swipeLeft() {
        notifyListeners(SWIPE_LEFT);
    }
    public void swipeRight() {
        notifyListeners(SWIPE_RIGHT);
    }

    public void analogRight(int indexAnalogue, double v) {
        notifyListeners(ANAL_RIGHT);
    }
    public void analogLeft(int indexAnalogue,double v) {
        notifyListeners(ANAL_LEFT);
    }
    public void analogDown(int indexAnalogue,double v) {
        notifyListeners(ANAL_DOWN);
    }
    public void analogUp(int indexAnalogue,double v) {
        notifyListeners(ANAL_UP);
    }


    public void crossUp() {
        notifyListeners(BUTTON_UP);
    }
    public void crossDown() {
        notifyListeners(BUTTON_DOWN);
    }
    public void crossLeft() {
        notifyListeners(BUTTON_LEFT);
    }
    public void crossRight() {
        notifyListeners(BUTTON_RIGHT);
    }


    private void notifyListeners(final int input_id) {
        for(int i=0;i<keyListeners.size();i++){

            final KeyListener keyListener = keyListeners.get(i);
            keyListener.keyPressed(input_id, (char) 0);

            new Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    keyListener.keyReleased(input_id, (char) 0);
                }
            },60);

        }
    }
    private void notifyPressedListeners(final int input_id) {
        for(int i=0;i<keyListeners.size();i++){
            keyListeners.get(i).keyPressed(input_id, (char) 0);
        }
    }

    private void notifyReleasedListeners(final int input_id) {
        for(int i=0;i<keyListeners.size();i++){
            keyListeners.get(i).keyReleased(input_id, (char) 0);
        }
    }

    public void releaseButton(int indexButton) {
        if(indexButton == 0){
            notifyReleasedListeners(BUTTON_1);
        }else if(indexButton == 1){
            notifyReleasedListeners(BUTTON_2);
        }else if(indexButton == 2){
            notifyReleasedListeners(BUTTON_3);
        }
    }

    public void pressButton(int indexButton) {
        if(indexButton == 0){
            notifyPressedListeners(BUTTON_1);
        }else if(indexButton == 1){
            notifyPressedListeners(BUTTON_2);
        }else if(indexButton == 2){
            notifyPressedListeners(BUTTON_3);
        }
    }
}
