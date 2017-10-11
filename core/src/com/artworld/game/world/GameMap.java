package com.artworld.game.world;

import com.artworld.game.entities.Characters.Bunny1;
import com.artworld.game.entities.Creature;
import com.artworld.game.entities.Entity;
import com.artworld.game.entities.EntityLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Roma on 01.10.2017.
 */

public abstract class GameMap {
    protected ArrayList<Entity> entities;

    public GameMap() {
        entities = new ArrayList<Entity>();
        entities.addAll(EntityLoader.loadEntities("basic", this, entities));
        System.out.println(entities.isEmpty());
        if(entities.isEmpty()){
            Bunny1 player = new Bunny1();
            player.create(100, 100, Creature.BUNNY, this);
            player.setPlayer(true);
            entities.add(player);
        }
    }

    public void render (OrthographicCamera camera, SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.render(batch);
        }
    }
    public void update (float delta) {
        for (Entity entity : entities) {
            entity.update(delta);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            EntityLoader.saveEntities("basic", entities);
        }
    }
    public void dispose () {

    }
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();
    public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), (int) (y / TileType.TILE_SIZE));
    }
    public int getPixelWidth() {
        return this.getWidth() * TileType.TILE_SIZE;
    }
    public int getPixelHeight() {
        return this.getHeight() * TileType.TILE_SIZE;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
