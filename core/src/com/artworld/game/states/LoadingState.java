package com.artworld.game.states;

import com.artworld.game.Application;
import com.artworld.game.managers.GameStateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Roma on 31.08.2017.
 */

public class LoadingState extends BaseState {

    private Stage stage;
    private float progress;
    private ProgressBar bar;
    private Viewport viewport;

    public LoadingState(GameStateManager gsm) {
        super(gsm);
        viewport = new FitViewport(w, h , camera);
        stage = new Stage(viewport, batch);
        stage.clear();
        progress = 0f;
        bar = new ProgressBar(0f, 1f, 0.01f, false, skin);
        bar.setPosition((viewport.getWorldWidth() / 2) - (viewport.getWorldWidth() / 2 / 2), 260);
        bar.setSize(viewport.getWorldWidth() / 2, 50);
        stage.addActor(bar);
        queueAssets();
    }

    private void queueAssets(){
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("Maps/map.tmx", TiledMap.class);
        assetManager.load("Graphics/Atlas.pack", TextureAtlas.class);
        assetManager.finishLoading();
    }
    @Override
    public void update(float delta) {
        stage.act(delta);
        progress = MathUtils.lerp(progress, assetManager.getProgress(), .1f);
        bar.setValue(progress);

        if(assetManager.update() && progress >= assetManager.getProgress()-0.0001f){
            gsm.setState(GameStateManager.State.MENU);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
    @Override
    public void dispose() {
        stage.dispose();
        System.out.println("DISPOSE LoadingState");
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int w, int h) {
        stage.getViewport().update(w, h, true);
    }
}
