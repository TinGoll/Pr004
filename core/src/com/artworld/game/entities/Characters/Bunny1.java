package com.artworld.game.entities.Characters;

import com.artworld.game.entities.Creature;
import com.artworld.game.entities.Entity;
import com.artworld.game.entities.EntitySnapshot;
import com.artworld.game.entities.actions.Actions;
import com.artworld.game.entities.entytiInfo.EntytiTitle;
import com.artworld.game.world.GameMap;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static com.artworld.game.utils.Constants.PPM;


/**
 * Created by Roma on 01.10.2017.
 */

public class Bunny1 extends Entity {


    private float timer = 0;
    private EntytiTitle title;

    @Override
    public void create(EntitySnapshot snapshot, Creature creature, GameMap map) {
        super.create(snapshot, creature, map);
        //spawnRadius = snapshot.getFloat("Жизнь", hp);
        setType(EntityType.PLAYER);
        setPlayer(snapshot.getBoolean("Player", isPlayer()));
        title = new EntytiTitle(this);


    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        sprite.draw(batch);
        title.render(batch);

    }

    @Override
    public GameMap getMap() {
        return map;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void defineEntity() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(sprite.getX(), sprite.getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / PPM);

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    /*
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(6 / PPM, 18 / PPM);
        playerSensorFixture  = body.createFixture(poly,0);
        playerSensorFixture.setUserData(this);
        poly.dispose();
        */

       /* PolygonShape sensor = new PolygonShape();
        sensor.setAsBox(13 / PPM, 18 / PPM);
        fdef.shape = sensor;
        fdef.isSensor = true;
        playerSensorFixture = body.createFixture(fdef);
        playerSensorFixture.setUserData(this);
        */
        PolygonShape sensor = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-13, 25).scl(1 / PPM);
        vertice[1] = new Vector2(13, 25).scl(1 / PPM);
        vertice[2] = new Vector2(-13, -14).scl(1 / PPM);
        vertice[3] = new Vector2(13, -14).scl(1 / PPM);
        sensor.set(vertice);
        fdef.shape = sensor;
        fdef.isSensor = true;
        SensorFixture = body.createFixture(fdef);
        SensorFixture.setUserData(this);

    }

    @Override
    protected void defineState() {
        // Определяем базовые характеристики.
        hp = 300;
        mp = 50;
        dmg = 5;
        arm = 1;
        exp = 0;
        lvl = 1;
        regenHp = true;
        regenMp = true;
    }


    @Override
    public EntitySnapshot getSaveSnapshot() {
        EntitySnapshot snapshot = super.getSaveSnapshot();
        snapshot.putFloat("Heals", getHp());
        snapshot.putFloat("Manna", getMp());
        snapshot.putFloat("Demage", state.getDmg());
        snapshot.putFloat("Armor", state.getArm());
        snapshot.putFloat("Lvl", lvl);
        snapshot.putBoolean("Player", isPlayer());

        return snapshot;
    }

    @Override
    public void lvlUp() {
        lvl+=1;
        hp = state.getMaxHp();
        mp = state.getMaxMp();
        System.out.println("lvl: " + getLvl());
        System.out.println("hp: " + hp + "/" + state.getMaxHp());
        System.out.println("mp: " + state.getMaxMp());
        System.out.println("dmg: " + state.getDmg());
        System.out.println("Arm: " + state.getArm());
        System.out.println("сл. лвл: " + state.getExpByLvl(lvl+1));
        System.out.println("осталось опыта до сл уровня.: " + (state.getExpByLvl(lvl+1) - exp));
    }
    @Override
    public void lvlDown() {
        lvl-=1;
    }

    @Override
    public void addExp(float exp) {
        state.addExp(exp);
    }

    @Override
    public void takeExp(float exp) {
        state.takeExp(exp);
    }

    @Override
    public void setLvlByExp(float exp) {
        lvl = state.getLvlByExp(exp);
    }
}
