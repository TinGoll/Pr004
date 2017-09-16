package com.artworld.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

/**
 * Created by Roma on 30.08.2017.
 */

public class InventoryActor extends Window {
    public InventoryActor(Inventory inventory, DragAndDrop dragAndDrop, Skin skin) {
        super("Инвентарь", skin);

        TextButton closeButton = new TextButton("х", skin);
        closeButton.addListener(new HidingClickListener(this));
        getTitleTable().add(closeButton).height(getPadTop());

        setPosition(400, 100);
        defaults().space(1);
        row().fill().expandX();

        int i = 0;
        for (Slot slot : inventory.getSlots()) {
            SlotActor slotActor = new SlotActor(skin, slot);
            dragAndDrop.addSource(new SlotSource(slotActor));
            dragAndDrop.addTarget(new SlotTarget(slotActor));
            add(slotActor);

            i++;
            if (i % 5 == 0) {
                row();
            }
        }

        pack();

        // it is hidden by default
        setVisible(false);
    }
}
