package com.artworld.game.entities.Characters;

import com.artworld.game.entities.Entity1;
import com.artworld.game.states.PlayState;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Roma on 10.07.2017.
 */

public abstract class Char extends Entity1 {
    public enum State {WALKING, FALLING, JUMPING, STANDING, ATTACK, GROWING, DEAD}

    public State currentState;
    public State previousState;
    public Fixture playerSensorFixture;

    protected float stateTimer;
    protected Vector2 velocity;
    protected boolean runningRight;
    protected boolean IsDead;
    private boolean IsPlayer = false;

    protected String NAME = "Char";

    public Char(PlayState playState, float x, float y) {
        super(playState, x, y);
        velocity = new Vector2(0, 0);
        runningRight = true;
    }

    public void jump() {
        System.out.println(isGrounded());
        if ( currentState != State.JUMPING  && isPlayer()) {
            body.applyLinearImpulse(new Vector2(0, 5f), body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }
    public void toRight() {
        if (isPlayer())
            getBody().applyLinearImpulse(new Vector2(0.3f, 0),  getBody().getWorldCenter(), true);
    }
    public void toLeft() {
        if (isPlayer())
            getBody().applyLinearImpulse(new Vector2(-0.3f, 0), getBody().getWorldCenter(), true);
    }

    public boolean isGrounded() {
        Array<Contact> contactList = world.getContactList();
        for (int i = 0; i < contactList.size; i++) {
            Contact contact = contactList.get(i);
            if(contact.isTouching() && (contact.getFixtureA() == this.playerSensorFixture || contact.getFixtureB() == this.playerSensorFixture)) {
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

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
    public State getState(){
        if(IsDead)
            return State.DEAD;
        else if((body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(body.getLinearVelocity().x != 0)
            return State.WALKING;
        else
            return State.STANDING;
    }
    public void die() {
        if (!isDead()) {
            IsDead = true;
            System.out.println("Персонаж " + NAME + " погиб.");
        }
    }

    public Vector2 getVelocity() {
        return velocity;
    }
    public boolean isDead() {
        return IsDead;
    }
    public boolean isPlayer() {
        return IsPlayer;
    }
    public boolean isRunningRight() {
        return runningRight;
    }
    public float getStateTimer() {
        return stateTimer;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
    public void setPlayer(boolean player) {
        IsPlayer = player;
    }
}
