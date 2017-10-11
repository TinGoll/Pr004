package com.artworld.game.entities;

import com.artworld.game.world.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

/**
 * Created by Roma on 30.09.2017.
 */

public class EntityLoader {

    private static Json json = new Json();

    public static ArrayList<Entity> loadEntities (String id, GameMap map,  ArrayList<Entity> currentEntities) {
        Gdx.files.local("Entities/").file().mkdirs();
        FileHandle file = Gdx.files.local("Entities/" + id + ".entities");

        if (file.exists()) {
            EntitySnapshot[] snapshots = json.fromJson(EntitySnapshot[].class, file.readString());
            ArrayList<Entity> entities = new ArrayList<Entity>();
            for (EntitySnapshot snapshot : snapshots) {
                entities.add(Creature.createEntityUsingSnapshot(snapshot, map));
            }
            return entities;
        } else {
            saveEntities(id, currentEntities);
            return currentEntities;
        }

    }

    public static void saveEntities (String id, ArrayList<Entity> entities) {
        ArrayList<EntitySnapshot> snapshots = new ArrayList<EntitySnapshot>();
        for (Entity entity : entities)
            snapshots.add(entity.getSaveSnapshot());

        Gdx.files.local("Entities/").file().mkdirs();
        FileHandle file = Gdx.files.local("Entities/" + id + ".entities");
        file.writeString(json.prettyPrint(snapshots), false);
    }
}
