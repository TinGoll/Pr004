package com.artworld.game.states;

import com.artworld.game.managers.GameStateManager;
import com.artworld.game.world.GameMap;
import com.artworld.game.world.TileType;
import com.artworld.game.world.TiledGameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.artworld.game.utils.Constants.PPM;

/**
 * Created by Roma on 01.10.2017.
 */

public class ContiniumState extends BaseState {

    private Viewport viewport;
    private TextureAtlas atlas;
    private GameMap gameMap;


    public ContiniumState(GameStateManager gsm) {
        super(gsm);

        atlas = assetManager.get("Graphics/Atlas.pack", TextureAtlas.class);
        viewport = new FitViewport(w / PPM / 2, h / PPM / 2, camera);
        viewport.update((int)w,(int) h);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        gameMap = new TiledGameMap(this);
    }

    @Override
    public void update(float delta) {
        gameMap.update(delta);
        camera.update();
        if(Gdx.input.isTouched()){
            camera.translate(-Gdx.input.getDeltaX()/PPM, Gdx.input.getDeltaY()/PPM, 0);
        }

        if(Gdx.input.justTouched()){
            Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).scl(PPM);
            TileType type = gameMap.getTileTypeByLocation(1, pos.x, pos.y);
            if(type != null) {
                System.out.println("Плитка: " + type.getName() + " " + type.isCollidable());
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        gameMap.render(camera, batch);
    }

    @Override
    public void dispose() {
        gameMap.dispose();
        atlas.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int w, int h) {
        viewport.update(w, h);
    }
}
