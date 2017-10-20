package com.tgd.slick_mobile;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;

import java.util.ArrayList;

/**
 * Created by Jerome on 23/03/2017.
 */

public abstract class StateBasedGame extends Activity implements Runnable {

    private static final String TAG = "StateBasedGame";
    private ArrayList<BasicGameState> gameStates=new ArrayList<>();

    public static volatile BasicGameState currentState;
    private volatile boolean stop;
    private volatile boolean entered;

    private Thread thread;
    private GameContainer gameContainer;
    private ArrayList<Integer> ids=new ArrayList<>();

    public StateBasedGame() {
        BasicGameState.setCurrentContext(this);
    }
    public StateBasedGame(String title) {
        BasicGameState.setCurrentContext(this);
    }

    public void init(){};
    public abstract void initStatesList(GameContainer container) throws SlickException;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);

        gameContainer=new GameContainer();

        try{
            BasicGameState.setCurrentContext(this);

            float widthPixels=getResources().getDisplayMetrics().widthPixels;
            float heightPixels=getResources().getDisplayMetrics().heightPixels;

            Utils.density=getResources().getDisplayMetrics().density;
            Utils.factorX=widthPixels/Conf.LONGUEUR;
            Utils.factorY=heightPixels/Conf.HAUTEUR;
            Utils.diag= (float) Math.sqrt(Math.pow(Utils.factorX,2)+ Math.pow(Utils.factorY,2));
            Log.d(TAG,"FACTOR X = "+Utils.factorX);
            Log.d(TAG,"FACTOR Y = "+Utils.factorY);
            Log.d(TAG,"DIAG = "+Utils.diag);

            init();
            initStatesList(gameContainer);
        }catch (SlickException exception){
            exception.printStackTrace();
        }
        View decorView = getWindow().getDecorView();
        decorView.setBackgroundColor(android.graphics.Color.BLACK);

        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

    }

    public void addState(BasicGameState basicGameState) throws SlickException {
        gameStates.add(basicGameState);
        basicGameState.init(gameContainer,this);
        Log.d(TAG,"ADD STATE");

    }

    public void enterState(int id) {
        Log.d(TAG,"WANT TO ENTER IN STATE "+id);
        entered=false;

        for(int i=0;i<gameStates.size();i++){
            if(gameStates.get(i).getID()==id){
                this.setContentView(gameStates.get(i));
                try{
                    if(currentState!=null){
                        currentState.exitState(gameContainer,this);
                    }
                    currentState=gameStates.get(i);
                    currentState.enterState(gameContainer,this);

                }catch (SlickException e){
                    e.printStackTrace();
                }
                Log.d(TAG,"ENTERED IN STATE "+id);
                entered=true;
            }
        }
    }

    public void enterState(final int id, final Animation animExit, final Animation animEnter) {
        Log.d(TAG,"WANT TO ENTER IN STATE BY ANIMATION"+id);
        ids.add(id);
        entered=false;
        currentState.startAnimation(animExit);
        currentState.postDelayed(new Runnable() {
            @Override
            public void run() {
                enterState(id);
                currentState.startAnimation(animEnter);
            }
        },animExit.getDuration());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stop=true;
        thread=null;
    }

    @Override
    protected void onStop() {
        super.onStop();

        stop=true;
        thread=null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(thread==null){
            thread=new Thread(this);
            thread.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(thread==null){
            thread=new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        long time;
        stop=false;

        gameContainer=new GameContainer();
        while(!stop){
            if(currentState!=null && entered){
                time= System.currentTimeMillis();
                currentState.needUpdate(gameContainer,this,30);
                currentState.postInvalidate();

                if(System.currentTimeMillis()-time<33){
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }else{
                //Log.w(TAG,"CURRENT STATE IS NULL");
            }

        }
    }

    @Override
    public void onBackPressed() {
        if(ids.size()>1){

                enterState(ids.get(ids.size()-2),new FadeOutTransition(),new FadeInTransition());
                ids.remove(ids.size()-1);
                ids.remove(ids.size()-1);

        }else{
            super.onBackPressed();
        }
    }

}
