package com.artworld.game.world;

import com.artworld.game.entities.Creature;
import com.artworld.game.entities.Entity;
import com.artworld.game.entities.EntitySnapshot;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import static com.artworld.game.utils.Constants.GAMELVL;


/**
 * Created by Roma on 17.10.2017.
 */

public class SpawnArea {

    public float x, y, width, height, timeLastSpawn;
    private Vector2 point1, point2, point3, point4;
    private GameMap map;
    private int currentCreatureCounter = 0;
    private int creatureCounter = 0;
    private float minLvl = 1, maxLvl = 3;

    public SpawnArea(GameMap map, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
        initPoints();
    }

    private void initPoints(){
        point1 = new Vector2(x, y);
        point2 = new Vector2(x, y + height);
        point3 = new Vector2(x + width, y + height);
        point4 = new Vector2(x + width, y);
    }
    public Vector2 getPoint1(){
        return point1;
    }
    public Vector2 getPoint2(){
        return point2;
    }
    public Vector2 getPoint3(){
        return point3;
    }
    public Vector2 getPoint4(){
        return point4;
    }

    public Vector2 getRandomSpawnPoint(){
        int col = (int) (width / TileType.TILE_SIZE);
        int row = (int) (height / TileType.TILE_SIZE);
        int randX = MathUtils.random(0,col);
        int randY = MathUtils.random(0,row);
        return new Vector2(randX * TileType.TILE_SIZE + TileType.TILE_SIZE + x,
                randY * TileType.TILE_SIZE + TileType.TILE_SIZE + y);
    }
    public Vector2 getCentrSpawn(){
        int col = (int) (width /2 / TileType.TILE_SIZE );
        int row = (int) (height/ 2 / TileType.TILE_SIZE);
        return new Vector2(col * 32 + x, row * 32 + y);
    }

    public void spawnRandomCreature(Boolean randomPoint){
        Vector2 pos = getCentrSpawn();
        if(randomPoint)
            pos = getRandomSpawnPoint();
        EntitySnapshot snapshot = new EntitySnapshot(Creature.getRandomCreature().getId(), pos.x, pos.y);
        Entity entity =  Creature.createEntityUsingSnapshot(snapshot, map);
        addCEntity(entity);
    }

    public void spawnCreature(Creature creature, Boolean randomPoint){
        Vector2 pos = getCentrSpawn();
        if(randomPoint)
            pos = getRandomSpawnPoint();
        EntitySnapshot snapshot = new EntitySnapshot(creature.getId(), pos.x, pos.y);
        Entity entity =  Creature.createEntityUsingSnapshot(snapshot, map);
        addCEntity(entity);
    }

    private void addCEntity(Entity entity){
        entity.setArea(this);
        entity.setLvl(getLvl());
        map.getEntities().add(entity);
        setTimeLastSpawn(TimeUtils.millis());
        incrementCount();
    }
    private void incrementCount(){
        currentCreatureCounter +=1;
        creatureCounter +=1;
    }
    private void setTimeLastSpawn(float timeLastSpawn) {
        this.timeLastSpawn = timeLastSpawn;
    }

    public int getCurrentCreatureCounter() {
        return currentCreatureCounter;
    }
    public int getCreatureCounter() {
        return creatureCounter;
    }
    public void decrementCounter(){
        currentCreatureCounter -=1;
    }

    private float getLvl(){
        return  (MathUtils.random(minLvl,maxLvl) + GAMELVL);
    }

}
