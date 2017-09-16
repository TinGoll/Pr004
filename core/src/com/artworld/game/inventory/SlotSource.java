package com.artworld.game.inventory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

/**
 * Created by Roma on 30.08.2017.
 */

public class SlotSource extends Source {

    private Slot sourceSlot;

    public SlotSource(SlotActor actor) {
        super(actor);
        this.sourceSlot = actor.getSlot();

    }

    @Override
    public Payload dragStart(InputEvent event, float x, float y, int pointer) {
        if (sourceSlot.getAmount() == 0) {
            return null;
        }

        Payload payload = new Payload();
        Slot payloadSlot = new Slot(sourceSlot.getItem(), sourceSlot.getAmount());
        sourceSlot.take(sourceSlot.getAmount());
        payload.setObject(payloadSlot);

        TextureAtlas icons = new TextureAtlas("Graphics/Atlas.pack");
        TextureRegion icon = icons.findRegion(payloadSlot.getItem().getTextureRegion());

        Actor dragActor = new Image(icon);
        payload.setDragActor(dragActor);

        Actor validDragActor = new Image(icon);
       // validDragActor.setColor(0, 1, 0, 1);
        payload.setValidDragActor(validDragActor);

        Actor invalidDragActor = new Image(icon);
        invalidDragActor.setColor(1, 0, 0, 1);
        payload.setInvalidDragActor(invalidDragActor);

        return payload;
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
        Slot payloadSlot = (Slot) payload.getObject();
        if (target != null) {
            Slot targetSlot = ((SlotActor) target.getActor()).getSlot();
            if (targetSlot.getItem() == payloadSlot.getItem() || targetSlot.getItem() == null) {
                targetSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
            } else {
                Item targetType = targetSlot.getItem();
                int targetAmount = targetSlot.getAmount();
                targetSlot.take(targetAmount);
                targetSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
                sourceSlot.add(targetType, targetAmount);
            }
        } else {
            sourceSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
        }
    }


}
