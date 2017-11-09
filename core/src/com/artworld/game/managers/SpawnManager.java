package com.artworld.game.managers;

import com.artworld.game.entities.Creature;
import com.artworld.game.world.GameMap;
import com.artworld.game.world.SpawnArea;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;

/**
 * Created by Roma on 17.10.2017.
 */

public class SpawnManager {

    ArrayList<SpawnArea>spawnAreas;
    GameMap map;

    public SpawnManager(GameMap map) {
        this.map = map;
        spawnAreas = new ArrayList<SpawnArea>();
        parseTiledRectObjectLayer(map.getTiledMap().getLayers().get("spawn").getObjects());
    }
    private  void parseTiledRectObjectLayer(MapObjects objects) {

        for(MapObject object : objects) {

            if(object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                SpawnArea spawnArea = new SpawnArea(map, rect.getX(),
                        rect.getY(),
                        rect.getWidth(),
                        rect.getHeight());

                spawnAreas.add(spawnArea);

            } else {
                continue;
            }

        }
    }

    public void update(float delta){

        for (SpawnArea area: spawnAreas)
            area.spawnCreature(Creature.BUNNY, false);

    }

    public ArrayList<SpawnArea> getSpawnAreas() {
        return spawnAreas;
    }
}
