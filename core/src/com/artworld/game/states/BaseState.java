package com.artworld.game.states;

import com.artworld.game.Application;
import com.artworld.game.managers.GameStateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Roma on 09.07.2017.
 */

public abstract class BaseState {

    protected GameStateManager gsm;
    protected Application app;
    protected SpriteBatch batch;
    protected AssetManager assetManager;
    protected Skin skin;
    protected float w, h;

    protected OrthographicCamera camera;

    public BaseState(GameStateManager gsm) {
        this.gsm = gsm;
        this.app = gsm.application();
        this.batch = app.getBatch();
        this.assetManager = app.getAssetManager();
        this.camera = app.getCamera();
        this.skin = app.getSkin();
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
    }
    public abstract void update(float delta);
    public abstract void render();
    public abstract void dispose();
    public abstract void pause();
    public abstract void resume();
    public abstract void resize(int w, int h);

    public OrthographicCamera getCamera() {
        return camera;
    }
    public SpriteBatch getBatch() {
        return batch;
    }

    public Skin getSkin() {
        return skin;
    }
}
