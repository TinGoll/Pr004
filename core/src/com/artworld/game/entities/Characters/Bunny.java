package com.artworld.game.entities.Characters;

import com.artworld.game.states.PlayState;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import static com.artworld.game.utils.Constants.PPM;

/**
 * Created by Roma on 16.07.2017.
 */

public class Bunny extends Char {

    private Animation standing;
    private Animation walking;
    private Animation jumping;
    private Animation falling;
    private Animation attack;
    private Animation dead;

    private float timer;



    public Bunny(PlayState playState, float x, float y) {
        super(playState, x, y);
        velocity.x = -1;
        speed = 11f;
        stateTimer = 0f;
        runningRight = false;
        currentState = State.STANDING;
        previousState = State.STANDING;
        initAnimation();
        setBounds(0, 0, 64 / PPM, 64 / PPM);
        setNAME("Кролик");

    }
    private void initAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(playState.getAtlas().findRegion("bunny_standing_64x64"), i * 64, 0, 64, 64));
        standing = new Animation(0.3f, frames);
        frames.clear();

        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(playState.getAtlas().findRegion("bunny_running_64x64"), i * 64, 0, 64, 64));
        walking = new Animation(0.1f, frames);
        frames.clear();
    }
    @Override
    protected void defineEntity() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
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
        playerSensorFixture = body.createFixture(fdef);
        playerSensorFixture.setUserData(this);



    }
    @Override
    public void update(float delta) {
        updateAnimation(delta);
            if(!isPlayer()) {
                if (runningRight && body.getLinearVelocity().x <= 3 || !runningRight && body.getLinearVelocity().x >= -3)
                    body.applyLinearImpulse(velocity.x * speed * delta, velocity.y * speed * delta, body.getPosition().x + 10, body.getPosition().y + 10, true);
                timer += delta;
                if (timer > 2) {
                    reverseVelocity(true, true);
                    timer = 0;
                }
            }
          //  System.out.println(isGrounded());
    }

    private void updateAnimation(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 16 / PPM);
        setRegion(getFrame(delta));
    }
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        currentState = getState();
        switch (currentState) {
            case DEAD:
            case ATTACK:
            case JUMPING:
                region = (TextureRegion) walking.getKeyFrame(stateTimer, true);
                break;
            case WALKING:
                region = (TextureRegion) walking.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = (TextureRegion) walking.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
                region = (TextureRegion) standing.getKeyFrame(stateTimer, true);
                break;
            default:
                region = (TextureRegion) standing.getKeyFrame(stateTimer, true);
                break;
        }
        DirectionOfMovement(region);
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }
}
