package com.artworld.game.inventory;

/**
 * Created by Roma on 29.08.2017.
 */

public enum Item {

    CHERY("cherry", "Вишня"), CHOCOLATE("chocolate", "шоколадка"), CORN("corn", "кукуруза"), EGGPLANT("eggplant", "баклажан"),
    GREENAPPLE("greenapple", "зеленое яблоко"), KIWI("kiwi", "киви"), LEMON("lemon", "лемон"),
    LIME("lime", "лайм"), LOLLIPOP("lollipop", "лединец"), MUSHROOM("mushroom", "гриб"), ORANGE("orange", "аппельсин"),
    PEAR("pear", "груша"), PIE("pie", "пирог"), PIZZA("pizza", "пица"), RASPBERRIES("raspberries", "малина"), REDAPPLE("redapple", "красное яблоко"),
    STRAWBERRY("strawberry", "клубника"), WAFFLES("waffles", "вафельки"), WATERMELON("watermelon", "арбуз");

    private String textureRegion;
    private String itemName;


    private Item(String textureRegion, String itemName) {
        this.textureRegion = textureRegion;
        this.itemName = itemName;
    }

    public String getTextureRegion() {
        return textureRegion;
    }
    public String getItemName() {
        return itemName;
    }
}
