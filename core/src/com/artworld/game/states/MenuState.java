package com.artworld.game.states;

import com.artworld.game.Application;
import com.artworld.game.managers.GameStateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Roma on 22.08.2017.
 */

public class MenuState extends BaseState {

    private Stage stage;

    private TextButton buttonPlay, buttonExit, buttonLoad;
    private Viewport viewport;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        viewport = new FitViewport(w, h , camera);
        this.stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        initButtons();
    }

    private void initButtons() {

        buttonPlay = new TextButton("Новая игра", skin, "default");
        buttonPlay.setSize(viewport.getWorldWidth() / 2, 60);
        buttonPlay.setPosition((viewport.getWorldWidth() / 2) - (viewport.getWorldWidth() / 2 / 2), 260);
        buttonPlay.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.setState(GameStateManager.State.PLAY);
            }
        });

        buttonLoad = new TextButton("Продолжить", skin, "default");
        buttonLoad.setSize(viewport.getWorldWidth() / 2, 60);
        buttonLoad.setPosition((viewport.getWorldWidth() / 2) - (viewport.getWorldWidth() / 2 / 2), 190);
        buttonLoad.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonLoad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        buttonExit = new TextButton("Выход", skin, "default");
        buttonExit.setSize(viewport.getWorldWidth() / 2, 60);
        buttonExit.setPosition((viewport.getWorldWidth() / 2) - (viewport.getWorldWidth() / 2 / 2), 120);
        buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(buttonPlay);
        stage.addActor(buttonLoad);
        stage.addActor(buttonExit);


    }
    @Override
    public void update(float delta) {
        stage.act(delta);
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
        Gdx.input.setInputProcessor(null);
        System.out.println("DISPOSE MenuState");
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
