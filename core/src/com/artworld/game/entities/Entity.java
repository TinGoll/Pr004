package com.artworld.game.entities;

import com.artworld.game.entities.actions.Actions;
import com.artworld.game.managers.CharManager;
import com.artworld.game.world.GameMap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import static com.artworld.game.utils.Constants.*;


/**
 * Created by Roma on 30.09.2017.
 */

public abstract class Entity {

    protected World world;
    protected Body body;
    protected Creature creature;
    protected float stateTimer;
    protected boolean runningRight;
    protected boolean Dead, regenHp = false, regenMp = false;
    protected GameMap map;

    private boolean Player = false;
    protected Vector2 velocity, position;

    public Actions currentState, previousState;
    public Fixture SensorFixture;

    protected CharManager state;


    //region Характеристики по умолчанию.
    protected float hp = 100,
            mp = 50,
            dmg = 5,
            arm = 1,
            lvl = 1,
            exp = 0,
            speed = 40;
    //endregion
    public void create (EntitySnapshot snapshot, Creature creature, GameMap map){
        if(this.creature == null) {
            this.map = map;
            this.position = new Vector2(snapshot.getX(), snapshot.getY());
            this.creature = creature;
            defineState();
            state = new CharManager(this, hp, mp, dmg, arm);
            defineEntity();
            initAnimation();
        }
    }
    public void create (float x, float y, Creature creature, GameMap map){
        EntitySnapshot snapshot =  new EntitySnapshot(creature.getId(), x, y);
        create(snapshot, creature, map);
    }
    public void create (float x, float y, GameMap map){
        create (x, y, Creature.getRandomCreature(), map);
    }
    public void update (float delta){

       // state.regenHp(isRegenHp(), delta);
        //state.regenMp(isRegenMp(), delta);
    }
    // region Управление временно.
    public void jump() {

        if ( currentState != Actions.JUMPING  && isPlayer()) {
            body.applyLinearImpulse(new Vector2(0, 5f), body.getWorldCenter(), true);
            currentState = Actions.JUMPING;
        }
    }
    public void toRight() {
        if (isPlayer())
            body.applyLinearImpulse(new Vector2(0.3f, 0), body.getWorldCenter(), true);
    }
    public void toLeft() {
        if (isPlayer())
            body.applyLinearImpulse(new Vector2(-0.3f, 0), body.getWorldCenter(), true);
    }
    // endregion
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
    public void DirectionOfMovement (TextureRegion region){
        if((body.getLinearVelocity().x < 0 || !isRunningRight()) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if((body.getLinearVelocity().x > 0 || isRunningRight()) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
    }
    public Actions getState(){
        if(Dead)
            return Actions.DEAD;
        else if((body.getLinearVelocity().y > 0 && currentState == Actions.JUMPING) || (body.getLinearVelocity().y < 0 && previousState == Actions.JUMPING))
            return Actions.JUMPING;
        else if(body.getLinearVelocity().y < 0)
            return Actions.FALLING;
        else if(body.getLinearVelocity().x != 0)
            return Actions.WALKING;
        else
            return Actions.STANDING;
    }
    public void die() {
        if (!isDead()) {
            Dead = true;
            System.out.println("Персонаж " + " погиб.");
        }
    }
    public boolean isRunningRight() {
        return runningRight;
    }
    public EntitySnapshot getSaveSnapshot(){
        return new EntitySnapshot(creature.getId(), position.x, position.y);
    }

    public float getX () {
        return position.x;
    }
    public float getY () {
        return position.y;
    }
    public Creature getCreature() {
        return creature;
    }
    public float getWidth() {
        return creature.getWidth();
    }
    public float getHeight() {
        return creature.getHeight();
    }
    public float getWeight() {
        return creature.getWeight();
    }
    public float getStateTimer() {
        return stateTimer;
    }
    public boolean isPlayer() {
        return Player;
    }
    public boolean isDead() {
        return Dead;
    }
    public void setPlayer(boolean player) {
        Player = player;
    }

    public float getHp() {
        return hp;
    }
    public float getMp() {
        return mp;
    }
    public float getLvl() {
        return lvl;
    }
    public float getExp() {
        return exp;
    }

    public boolean isRegenHp() {
        return regenHp;
    }
    public boolean isRegenMp() {
        return regenMp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }
    public void setMp(float mp) {
        this.mp = mp;
    }
    public void setLvl(float lvl) {
        this.lvl = lvl;
    }
    public void setExp(float exp) {
        this.exp = exp;
    }

    public  void lvlUp(){
    }
    public  void lvlDown(){
    }
    public void setLvlByExp(float exp){

    }
    public void addExp(float exp){
    }
    public void takeExp(float exp){
    }
    protected abstract void defineEntity();
    protected abstract void defineState();
    protected abstract void initAnimation();
    public abstract void render(SpriteBatch batch);
}
