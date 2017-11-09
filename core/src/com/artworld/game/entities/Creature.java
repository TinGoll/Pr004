package com.artworld.game.entities;

import com.artworld.game.entities.Characters.Bunny1;
import com.artworld.game.world.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import java.util.HashMap;

/**
 * Created by Roma on 30.09.2017.
 */

public enum Creature {

    //region Существа
    BUNNY("bunny", "кролик", 64, 64, 1, 10, Bunny1.class), // Существо для игрока.
    OWL("owl", "сова", 64, 64, 10, 10, Bunny1.class),
    BULL("bull", "бык", 64, 64, 11, 10, Bunny1.class),
    FOX("fox", "лиса", 64, 64, 12, 10, Bunny1.class),
    PORCUPINE("porcupine","Дикобраз", 64, 64, 13, 10, Bunny1.class),
    SKUNK("skunk", "скунс",  64, 64, 14, 10, Bunny1.class),
    WOLF("wolf", "волк", 64, 64, 15, 10, Bunny1.class);
    //endregion
    //region Основные переменные существ
    private String textureRegion;
    private String title;
    private int id;
    private float width, height, weight;
    private Class loaderClass;
    private static HashMap<Integer, Creature> Creatures;
    //endregion
    private Creature(String textureRegion, String title, float width, float height, int id, float weight, Class loaderClass) {
        this.textureRegion = textureRegion;
        this.title = title;
        this.id = id;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.loaderClass = loaderClass;
    }
    static {
        Creatures = new HashMap<Integer, Creature>();
        for (Creature creature : Creature.values())
            Creatures.put(creature.id, creature);
    }
    public String getTextureRegion() {
        return textureRegion;
    }
    public String getTitle() {
        return title;
    }
    public int getId() {
        return id;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    public float getWeight() {
        return weight;
    }
    public Class getLoaderClass() {
        return loaderClass;
    }
    public static HashMap<Integer, Creature> getCreatures() {
        return Creatures;
    }
    public static Creature getCreature(int id) {
        return Creatures.get(id);
    }
    public static Creature getRandomCreature() {
        return Creatures.get(Creature.values()[MathUtils.random(1, Creature.values().length - 1)].getId());
    }
    public static Entity createEntityUsingSnapshot (EntitySnapshot entitySnapshot, GameMap map) {
        Creature creature = Creatures.get(entitySnapshot.id);
        try {
            @SuppressWarnings("unchecked")
            Entity entity = (Entity) ClassReflection.newInstance(creature.loaderClass);
            entity.create(entitySnapshot, creature, map);
            return entity;
        } catch (ReflectionException e) {
            Gdx.app.error("Загрузчик существ", "Не удалось загрузить существо: " + creature.title);
            return null;
        }
    }
}
