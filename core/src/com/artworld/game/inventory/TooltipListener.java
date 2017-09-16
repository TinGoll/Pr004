package com.artworld.game.inventory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Roma on 30.08.2017.
 */

public class TooltipListener extends InputListener {

    private boolean inside;

    private Actor tooltip;
    private boolean followCursor;

    private Vector2 position = new Vector2();
    private Vector2 tmp = new Vector2();
    private Vector2 offset = new Vector2(50, 50);

    public TooltipListener(Actor tooltip, boolean followCursor) {
        this.tooltip = tooltip;
        this.followCursor = followCursor;
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        if (inside && followCursor) {
            event.getListenerActor().localToStageCoordinates(tmp.set(x, y));
            tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y);
        }
        return false;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        inside = true;
        tooltip.setVisible(true);
        tmp.set(x, y);
        event.getListenerActor().localToStageCoordinates(tmp);
        tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y);
        tooltip.toFront();
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        inside = false;
        tooltip.setVisible(false);
    }

    public void setOffset(float offsetX, float offsetY) {
        offset.set(offsetX, offsetY);
    }
}
