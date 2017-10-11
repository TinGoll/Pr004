package com.artworld.game.entities.Characters;

import com.artworld.game.entities.Creature;
import com.artworld.game.entities.Entity;
import com.artworld.game.entities.EntitySnapshot;
import com.artworld.game.world.GameMap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



/**
 * Created by Roma on 01.10.2017.
 */

public class Bunny1 extends Entity {

    protected Animation standing;
    protected Animation walking;
    protected Animation jumping;
    protected Animation falling;
    protected Animation attack;
    protected Animation dead;

    protected float timer = 0;

    @Override
    public void create(EntitySnapshot snapshot, Creature creature, GameMap map) {
        super.create(snapshot, creature, map);
        //spawnRadius = snapshot.getFloat("Жизнь", hp);

        System.out.println(state.getMaxHp());
    }

    @Override
    public void render(SpriteBatch batch) {

    }
    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    protected void defineEntity() {

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
    protected void initAnimation() {

    }

    @Override
    public EntitySnapshot getSaveSnapshot() {
        EntitySnapshot snapshot = super.getSaveSnapshot();
        snapshot.putFloat("Heals", getHp());
        snapshot.putFloat("Manna", getMp());
        snapshot.putFloat("Demage", state.getDmg());
        snapshot.putFloat("Armor", state.getArm());
        snapshot.putFloat("Lvl", lvl);
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
