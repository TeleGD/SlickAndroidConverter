package com.tgd.slick_mobile;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.tgd.slick_mobile.interfaces.BasicGameStateInterface;
import com.tgd.slick_mobile.listener.KeyListener;
import com.tgd.slick_mobile.listener.MouseListener;


/**
 * Created by Jerome on 23/03/2017.
 */

public abstract class BasicGameState extends View implements  KeyListener, MouseListener {

    private static final String TAG = "BasicGameState";
    private static Context context;
    private static Graphics current_graphic;

    private final Graphics g;
    private GameContainer gameContainer;
    private StateBasedGame game;
    private boolean entered;

    private TGDController controller;

    public BasicGameState(){
        super(context);


        g=new Graphics(null);

        gameContainer = new GameContainer();
        controller = new TGDController();
    }


    @Override
    public void keyPressed(int key, char c) {

    }

    @Override
    public void keyReleased(int key, char c) {

    }


    public abstract void init(GameContainer container, StateBasedGame game) throws SlickException;
    public abstract void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException;;
    public abstract void update(GameContainer container, StateBasedGame game, int delta) throws SlickException;;
    public abstract int getID();

    public void enter(GameContainer container,StateBasedGame game) throws SlickException {}
    public void leave(GameContainer container,StateBasedGame game) throws SlickException {}

    protected void exitState(GameContainer container,StateBasedGame game) throws SlickException{
        leave(container, game);

        gameContainer.getInput().removeKeyListener(this);
        gameContainer.getInput().removeMouseListener(this);
    }

    protected void enterState(GameContainer container,StateBasedGame game) throws SlickException{
        enter(container, game);

        this.game=game;
        entered=true;

        gameContainer.getInput().addKeyListener(this);
        gameContainer.getInput().addMouseListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        g.setCanvas(canvas);
        setBackgroundColor(android.graphics.Color.BLACK);

        try {
            render(gameContainer,game,g);
        }catch (SlickException exception){
            exception.printStackTrace();
        }

        current_graphic = g;


        controller.render(g);
    }

    public  void needUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i){

        try {
            update(gameContainer,stateBasedGame,i);
        } catch (SlickException e) {
            e.printStackTrace();
        }


        controller.update(gameContainer,stateBasedGame,i);

    }

    public static Context currentContext(){
        return context;
    }

    public static Graphics currentGraphic() {
        return current_graphic;
    }

    public static void setCurrentContext(Context currentContext) {
        BasicGameState.context = currentContext;
    }

    public Graphics getGraphics() {
        return g;
    }

    private float xInit;
    private float yInit;
    private boolean clicMaybe;

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);

        if(controller.onTouch(motionEvent))return true;

        if(motionEvent.getAction()== MotionEvent.ACTION_DOWN){

            xInit=motionEvent.getX();
            yInit=motionEvent.getY();

            clicMaybe=true;
            willScroll();
            gameContainer.getInput().pressScreen();

            return true;

        }else if(motionEvent.getAction()== MotionEvent.ACTION_MOVE)
        {
            onScrolling(motionEvent.getX()-xInit,motionEvent.getY()-yInit);

            clicMaybe=false;
            return true;

        }else if(motionEvent.getAction()== MotionEvent.ACTION_UP)
        {
            if(clicMaybe || (Math.abs(motionEvent.getX()-xInit)<Utils.DPtoPX(7) && Math.abs(motionEvent.getY()-yInit)<Utils.DPtoPX(7))) {
                gameContainer.getInput().releaseScreen();
                return true;
            }
            else onScrolled(motionEvent.getX()-xInit,motionEvent.getY()-yInit);
        }

        return true;

    }


    public void willScroll(){

    }

    public void onScrolling(float width, float height){

    }

    public void onScrolled(float width, float height){

        if(height<-40){
            gameContainer.getInput().swipeUp();
        }else if(height>40){
            gameContainer.getInput().swipeDown();
        }else if(width<-40){
            gameContainer.getInput().swipeLeft();
        }else if(width>40){
            gameContainer.getInput().swipeRight();
        }

    }

    public boolean isEntered() {
        return entered;
    }


    @Override
    public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void mousePressed(int type, int x, int y) {

    }

    @Override
    public void mouseReleased(int type, int x, int y) {

    }

    @Override
    public void mouseClicked(int type, int x, int y, int count) {

    }

    @Override
    public void mouseWheelMoved(int type) {

    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }

    @Override
    public boolean isAcceptingInput() {
        return false;
    }

    @Override
    public void setInput(Input arg0) {

    }


}
