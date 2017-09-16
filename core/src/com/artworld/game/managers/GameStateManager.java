package com.artworld.game.managers;

import com.artworld.game.Application;
import com.artworld.game.states.BaseState;
import com.artworld.game.states.LoadingState;
import com.artworld.game.states.MenuState;
import com.artworld.game.states.PlayState;

import java.util.Stack;

/**
 * Created by Roma on 09.07.2017.
 */

public class GameStateManager {
    private final Application app;
    private Stack<BaseState> states;
    public enum State {
        SPLASH,
        PLAY,
        INVENTORY,
        LOADING,
        MENU
    }
    public GameStateManager(Application app) {
        this.app = app;
        this.states = new Stack<BaseState>();
        this.setState(State.LOADING);
    }
    public void render() {
        states.peek().render();
    }
    public void update(float delta) {
        states.peek().update(delta);
    }
    public void resize(int w, int h) {
        states.peek().resize(w, h);
    }
    public void pause() {
        states.peek().pause();
    }
    public void resume() {
        states.peek().resume();
    }
    public void dispose() {
        for(BaseState gs : states) {
            gs.dispose();
        }
        states.clear();
    }
    public void setState(State state) {
        if(!states.isEmpty()) {
            states.pop().dispose();
        }
        states.push(getState(state));
    }
    private BaseState getState(State state) {
        switch(state) {
            case SPLASH: return null;
            case PLAY: return new PlayState(this);
            case LOADING: return new LoadingState(this);
            case INVENTORY: return null;
            case MENU: return new MenuState(this);
        }
        return null;
    }
    public Application application() {
        return app;
    }
}
