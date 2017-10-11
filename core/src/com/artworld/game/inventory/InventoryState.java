package com.artworld.game.inventory;

import com.artworld.game.inventory.Inventory;
import com.artworld.game.inventory.InventoryActor;
import com.artworld.game.managers.GameStateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

/**
 * Created by Roma on 29.08.2017.
 */
public class InventoryState {

    private InventoryActor inventoryActor;
    private Inventory inventory;
    public static Stage stage;

    public InventoryState(Skin skin) {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        DragAndDrop dragAndDrop = new DragAndDrop();
        inventory = new Inventory();
        inventoryActor = new InventoryActor(inventory, dragAndDrop, skin);
        stage.addActor(inventoryActor);
        inventoryActor.setVisible(false);
    }
    public void update(float delta) {
        stage.act();
        if (Gdx.input.isKeyPressed(Input.Keys.I) && !inventoryActor.isVisible()) {
            inventoryActor.setVisible(true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            inventory.store(Item.values()[MathUtils.random(0, Item.values().length - 1)], 1);
        }


    }
    public void render() {
        stage.draw();
    }
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }
    public void resize(int w, int h) {
        stage.getViewport().update(w, h, true);
    }
}
