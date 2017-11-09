package com.artworld.game.world;

import com.artworld.game.managers.AnimationManager;
import com.artworld.game.managers.SpawnManager;
import com.artworld.game.states.ContiniumState;
import com.artworld.game.utils.CameraStyles;
import com.artworld.game.utils.TiledObjectUtil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static com.artworld.game.utils.Constants.PPM;

/**
 * Created by Roma on 01.10.2017.
 */

public class TiledGameMap extends GameMap {

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    ContiniumState continiumState;
    AnimationManager animationManager;
    protected SpawnManager spawnManager;

    private int[] backgroundLayers;
    private int[] foregroundLayers;

    public TiledGameMap(ContiniumState continiumState) {
        this.continiumState = continiumState;
        animationManager = new AnimationManager(continiumState.getAtlas());
        tiledMap = new TmxMapLoader().load("Maps/map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/ PPM);
        backgroundLayers = new int[]{0, 1}; // слои бакграунд
        foregroundLayers =  new int[]{ 2 };    // Фронт слой

        TiledObjectUtil.parseTiledRectObjectLayer(world, tiledMap.getLayers().get("Collisions").getObjects());
        TiledObjectUtil.parseTiledLineObjectLayer(world, tiledMap.getLayers().get("Collisions").getObjects());
        spawnManager = new SpawnManager(this);
        spawnManager.update(Gdx.graphics.getDeltaTime());
        if(player != null)
            CameraStyles.lockOnTarget(continiumState.getCamera(), player.getBody().getPosition());
    }

    @Override
    public void render(OrthographicCamera camera, SpriteBatch batch) {

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render(backgroundLayers);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        super.render(camera, batch);
        batch.end();
        tiledMapRenderer.render(foregroundLayers);
        b2dr.render(world, camera.combined);
        cameraUpdate(camera);

    }

    private void cameraUpdate(OrthographicCamera camera) {
        if(player != null)
            CameraStyles.lerpToTarget(camera, player.getBody().getPosition());
        float startX = camera.viewportWidth / 2;
        float startY = camera.viewportHeight / 2;
        CameraStyles.boundary(camera, startX, startY, getWidth() - startX * 2, getHeight() - startY * 2);
    }

    @Override
    public void update(float delta) {
        world.step(1 / 60f, 6 , 2);
        super.update(delta);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
    }

    @Override
    public int getWidth() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
    }
    @Override
    public int getHeight() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
    }
    @Override
    public int getLayers() {
        return tiledMap.getLayers().getCount();
    }
    @Override
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
       Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col, row);
        if (cell != null) {
            TiledMapTile tile = cell.getTile();
            if (tile != null) {
                int id = tile.getId();
                return TileType.getTileTypeById(id);
            }
        }
        return null;
    }
    @Override
    public AnimationManager getAnimationManager() {
        return animationManager;
    }
    @Override
    public ContiniumState getContiniumState() {
        return continiumState;
    }
    @Override
    public World getWorld() {
        return world;
    }
    @Override
    public TiledMap getTiledMap() {
        return tiledMap;
    }

}
