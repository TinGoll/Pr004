package com.artworld.game.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

/**
 * Created by Roma on 30.08.2017.
 */

public class SlotTarget extends Target {
    private Slot targetSlot;

    public SlotTarget(SlotActor actor) {
        super(actor);
        targetSlot = actor.getSlot();
        getActor().setColor(Color.CORAL);
    }

    @Override
    public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
        Slot payloadSlot = (Slot) payload.getObject();
        // if (targetSlot.getItem() == payloadSlot.getItem() ||
        // targetSlot.getItem() == null) {
        getActor().setColor(Color.BROWN);
        return true;
        // } else {
        // getActor().setColor(Color.DARK_GRAY);
        // return false;
        // }
    }

    @Override
    public void drop(Source source, Payload payload, float x, float y, int pointer) {
    }

    @Override
    public void reset(Source source, Payload payload) {
        getActor().setColor(Color.CORAL);
    }
}
