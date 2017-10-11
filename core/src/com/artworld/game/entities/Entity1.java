package com.artworld.game.entities;

import com.artworld.game.Application;
import com.artworld.game.states.PlayState;
import com.artworld.game.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Roma on 10.07.2017.
 */

public abstract class Entity1 extends Sprite {
    public enum Type {PLAYER, ENEMY, ITEM, ACTIVEOBJECT}
    protected Application app;
    protected PlayState playState;
    protected World world;
    protected Body body;
    protected Type type;

    protected float speed = 0f;
    protected float structure = 100f;
    protected  float hp = structure;


    public Entity1(PlayState playState, float x, float y) {
        this.playState = playState;
        this.app = playState.getApp();
        this.world = playState.getWorld();
        setPosition(x / Constants.PPM, y / Constants.PPM);
        defineEntity();
    }

    protected abstract void defineEntity();
    public abstract void update(float delta);
    public Body getBody() {
        return body;
    }
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
