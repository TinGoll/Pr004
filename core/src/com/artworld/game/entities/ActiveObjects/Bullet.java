package com.artworld.game.entities.ActiveObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Roma on 27.09.2017.
 */

public class Bullet implements Pool.Poolable {
    public Vector2 position;
    public boolean alive;

    public Bullet(float posX, float posY) {
        this.position.set(posX, posY);
        this.alive = true;
    }

    public void init(float posX, float posY) {
        position.set(posX,  posY);
        alive = true;
    }

    @Override
    public void reset() {
        position.set(0,0);
        alive = false;
    }

    public void update (float delta) {

        // обновление позиции пули
        position.add(1*delta*60, 1*delta*60);

        // если пуля вне экрана, установка состояние в "мертвая"
      //  if (isOutOfScreen()) alive = false;
    }
}
