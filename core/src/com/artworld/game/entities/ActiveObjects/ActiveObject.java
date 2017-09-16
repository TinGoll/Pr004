package com.artworld.game.entities.ActiveObjects;

import com.artworld.game.entities.Entity;
import com.artworld.game.states.PlayState;

/**
 * Created by Roma on 11.07.2017.
 */

public abstract class ActiveObject extends Entity {
    public ActiveObject(PlayState playState, float x, float y) {
        super(playState, x, y);
    }
}
