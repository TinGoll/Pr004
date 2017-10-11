package com.artworld.game.entities;

import com.artworld.game.inventory.Item;

import java.util.HashMap;

/**
 * Created by Roma on 30.09.2017.
 */

public class EntitySnapshot {

    public int id;
    public float x, y;


    public HashMap<String, String> data;
    public HashMap<Item, Integer> inventory;

    public EntitySnapshot() {}

    public EntitySnapshot( int id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
        data = new HashMap<String, String>();
        inventory = new HashMap<Item, Integer>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void putFloat (String key, float value) {
        data.put(key, "" + value);
    }

    public void putInt (String key, int value) {
        data.put(key, "" + value);
    }

    public void putBoolean (String key, boolean value) {
        data.put(key, "" + value);
    }

    public void putString (String key, String value) {
        data.put(key, value);
    }

    public float getFloat (String key, float defaultValue) {
        if (data.containsKey(key)) {
            try {
                return Float.parseFloat(data.get(key));
            } catch (Exception e) {
                return defaultValue;
            }
        } else
            return defaultValue;
    }

    public int getInt (String key, int defaultValue) {
        if (data.containsKey(key)) {
            try {
                return Integer.parseInt(data.get(key));
            } catch (Exception e) {
                return defaultValue;
            }
        } else
            return defaultValue;
    }

    public boolean getBoolean (String key, boolean defaultValue) {
        if (data.containsKey(key)) {
            try {
                return Boolean.parseBoolean(data.get(key));
            } catch (Exception e) {
                return defaultValue;
            }
        } else
            return defaultValue;
    }

    public String getString (String key, String defaultValue) {
        if (data.containsKey(key)) {
            return data.get(key);
        } else
            return defaultValue;
    }

}
