package com.artworld.game.entities.Characters;

import com.artworld.game.states.PlayState;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;

import static com.artworld.game.utils.Constants.PPM;

/**
 * Created by Roma on 11.07.2017.
 */

public class Spider extends Char {

    private Animation walkAnimation;
    private TextureRegion standing;
    private Array<TextureRegion> frames;

    private boolean setToDestroy;
    private boolean destroyed;
    private float timer;

    public Spider(PlayState playState, float x, float y) {
        super(playState, x, y);

        currentState = previousState = State.WALKING;
        setBounds(getX(), getY(), 64 / PPM, 64 / PPM);
        velocity.x  = 1;
        speed = 4;
        standing = new TextureRegion(playState.getAtlas().findRegion("bunny_running_64x64"), 64*0, 0, 64, 64);
        setRegion(standing);
       // setNAME("Кролик");
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

    }

    @Override
    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 16 / PPM);
        //body.setLinearVelocity(velocity.x * speed * delta, body.getLinearVelocity().y);
        body.applyLinearImpulse(velocity.x * speed * delta, velocity.y * speed * delta, body.getPosition().x + 10, body.getPosition().y + 10, true);

        timer += delta;
        if(timer > 2){
            reverseVelocity(true, true);
            timer = 0;
        }

    }

}
