package com.artworld.game.world;

import com.artworld.game.entities.Characters.Bunny1;
import com.artworld.game.entities.Creature;
import com.artworld.game.entities.Entity;
import com.artworld.game.entities.EntityLoader;
import com.artworld.game.managers.AnimationManager;
import com.artworld.game.managers.SpawnManager;
import com.artworld.game.states.ContiniumState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import static com.artworld.game.utils.Constants.PPM;

/**
 * Created by Roma on 01.10.2017.
 */

public abstract class GameMap {
    protected ArrayList<Entity> entities;
    protected Entity player = null;
    protected World world;
    protected Box2DDebugRenderer b2dr;

    public GameMap() {
        createWorld();
        entities = new ArrayList<Entity>();
        entities.addAll(EntityLoader.loadEntities("basic", this, entities));

        if(entities.isEmpty()){
            player = new Bunny1();
            player.create(100, 100, Creature.BUNNY, this);
            player.setPlayer(true);
            entities.add(player);

        }else {

            for (Entity entity : entities) {
                if (entity.isPlayer()) {
                    player = entity;

                }
            }
        }

    }

    protected void createWorld() {
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
    }

    private void handleInput(float delta) {
        if (player != null && Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.jump();
        if (player != null && Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getBody().getLinearVelocity().x <= 2)
            player.toRight();
        if (player != null && Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getBody().getLinearVelocity().x >= -2)
            player.toLeft();
        }

    public void render (OrthographicCamera camera, SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.render(batch, camera);
        }
    }
    public void update (float delta) {
        handleInput(delta);
        for (Entity entity : entities) {
            entity.update(delta);
        }
        if(player != null && player.getBody().getPosition().y < -19)
            player.getBody().setTransform(100 / PPM, 100/ PPM, 0f);
    }
    public void dispose () {
        world.dispose();
        b2dr.dispose();
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
    public Entity getPlayer() {
        return player;
    }
    public abstract AnimationManager getAnimationManager();
    public abstract World getWorld();
    public abstract ContiniumState getContiniumState();
    public void Save(){
        EntityLoader.saveEntities("basic", entities);
    }
    public abstract TiledMap getTiledMap();

}
