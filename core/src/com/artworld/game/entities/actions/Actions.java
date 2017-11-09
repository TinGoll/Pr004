package com.artworld.game.entities.actions;

/**
 * Created by Roma on 03.10.2017.
 */

public enum Actions {
    WALKING(0.1f, 5, true),
    FALLING(0.3f, 3, true),
    JUMPING(0.3f, 3, true),
    STANDING(0.3f, 5, true),
    ATTACK(0.3f, 3, true),
    GROWING(0.3f, 3, false),
    DEAD(0.3f, 3, false);

    private float speedAnimation;
    private int numberFrames;
    private boolean looping;

    Actions(float speedAnimation, int numberFrames, boolean looping) {
        this.speedAnimation = speedAnimation;
        this.numberFrames = numberFrames;
        this.looping = looping;
    }
    public float getSpeedAnimation() {
        return speedAnimation;
    }
    public int getNumberFrames() {
        return numberFrames;
    }
    public boolean isLooping() {
        return looping;
    }
}
