package com.artworld.game.world;

import com.artworld.game.states.ContiniumState;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import static com.artworld.game.utils.Constants.PPM;

/**
 * Created by Roma on 01.10.2017.
 */

public class TiledGameMap extends GameMap {

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    private int[] backgroundLayers;
    private int[] foregroundLayers;

    public TiledGameMap(ContiniumState continiumState) {
        tiledMap = new TmxMapLoader().load("Maps/map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/ PPM);
        backgroundLayers = new int[]{0, 1}; // слои бакграунд
        foregroundLayers =  new int[]{ 2 };    // Фронт слой
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
       // b2dr.render(world, camera.combined);

    }

    @Override
    public void update(float delta) {
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
}
