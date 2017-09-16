package com.artworld.game.inventory;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Roma on 29.08.2017.
 */

public class Inventory {

    private Array<Slot> slots;

    public Inventory(){
        slots = new Array<Slot>(25);



        for (int i = 0; i < 25; i ++) {
            slots.add( new Slot(null, 0));
        }
        for (Slot slot : slots) {
           // slot.add(Item.values()[MathUtils.random(0, Item.values().length - 1)], 1);
        }
        for (int i = 0; i < 3; i++) {
            Slot randomSlot = slots.get(MathUtils.random(0, slots.size - 1));
            randomSlot.take(randomSlot.getAmount());
        }
    }
    public int checkInventory(Item item) {
        int amount = 0;

        for (Slot slot : slots) {
            if (slot.getItem() == item) {
                amount += slot.getAmount();
            }
        }
        return amount;
    }

    public boolean store(Item item, int amount) {
        // Проверка слота  с тем же типом элемента
        Slot itemSlot = firstSlotWithItem(item);
        if (itemSlot != null) {
            itemSlot.add(item, amount);
            return true;
        } else {
            // Проверка наличия свободного слота
            Slot emptySlot = firstSlotWithItem(null);
            if (emptySlot != null) {
                emptySlot.add(item, amount);
                return true;
            }
        }
        // Нет пустого слота
        return false;
    }

    private Slot firstSlotWithItem(Item item) {
        for (Slot slot : slots) {
            if (slot.getItem() == item) {
                return slot;
            }
        }
        return null;
    }

    public Array<Slot> getSlots() {
        return slots;
    }
}
