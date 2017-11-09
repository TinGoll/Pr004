package com.artworld.game.managers;

import com.artworld.game.entities.Creature;
import com.artworld.game.entities.actions.Actions;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

/**
 * Created by Roma on 12.10.2017.
 */

public class AnimationManager {

    private HashMap<String, Animation> animations;
    private Array<TextureRegion> frames;
    private TextureAtlas atlas;

    public AnimationManager(TextureAtlas atlas) {
        this.atlas = atlas;
        animations = new HashMap<String, Animation>();
        initAnimation();
    }

    public void initAnimation() {
        frames = new Array<TextureRegion>();
        for (Creature creature : Creature.values()) {
            for (Actions actions : Actions.values()) {
                    for (int i = 0; i < actions.getNumberFrames(); i++) {
                        TextureRegion textureRegion;
                        if (atlas.findRegion(creature.getTextureRegion() + "_" + actions.name()) == null) {
                            textureRegion = atlas.findRegion("nothing");
                            frames.add(textureRegion);
                            //System.out.println("Регион " + creature.getTextureRegion() + "_" + actions.name() + " не найден");

                        }else {
                            textureRegion = new TextureRegion(atlas.findRegion(creature.getTextureRegion() + "_" + actions.name()),
                                    i * (int) creature.getWidth(), 0, (int) creature.getWidth(), (int) creature.getHeight());
                                     frames.add(textureRegion);
                        }
                    }
                Animation animation = new Animation(actions.getSpeedAnimation(), frames);
                frames.clear();
                animations.put(creature.name() + "_" + actions.name(), animation);

            }
        }
    }
    public HashMap<String, Animation> getAnimations() {
        return animations;
    }
    public Animation getAnimation(Creature creature, Actions actions) {
        return animations.get(creature.name() + "_" + actions.name());
    }

    public void dispose(){

    }
}

