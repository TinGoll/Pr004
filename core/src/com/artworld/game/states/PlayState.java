package com.artworld.game.states;

import com.artworld.game.Application;
import com.artworld.game.entities.Characters.Bunny;
import com.artworld.game.inventory.InventoryState;
import com.artworld.game.managers.GameStateManager;
import com.artworld.game.utils.CameraStyles;
import com.artworld.game.utils.TiledObjectUtil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.artworld.game.utils.Constants.PPM;

/**
 * Created by Roma on 09.07.2017.
 */

public class PlayState extends BaseState {
    private TiledMap map;
    private MapProperties props;
    private OrthogonalTiledMapRenderer renderer;

    private Viewport viewport;

    private int[] backgroundLayers;
    private int[] foregroundLayers;
    int levelWidth =0;
    int levelHeight = 0;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Bunny player;

    private TextureAtlas atlas;

    private InventoryState inventoryState;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        atlas = assetManager.get("Graphics/Atlas.pack", TextureAtlas.class);
        viewport = new FitViewport(w / PPM / 2, h / PPM / 2, camera);
        viewport.update((int)w,(int) h);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        createTiledMap();
        createWorld();
        inventoryState = new com.artworld.game.inventory.InventoryState(skin);

    }
    private void createTiledMap() {
        backgroundLayers = new int[]{0, 1}; // слои бакграунд
        foregroundLayers =  new int[]{ 2 };    // Фронт слой
        map = assetManager.get("Maps/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
        props = map.getProperties();
        levelWidth = props.get("width", Integer.class);
        levelHeight  = props.get("height", Integer.class);
    }
    private void createWorld() {
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        TiledObjectUtil.parseTiledRectObjectLayer(world, map.getLayers().get("Collisions").getObjects());
        TiledObjectUtil.parseTiledLineObjectLayer(world, map.getLayers().get("Collisions").getObjects());
        player = new Bunny(this, 100, 100);
        player.setPlayer(true);
    }
    @Override
    public void update(float delta) {
        cameraUpdate();
        worldUpdate(delta);
        handleInput(delta);
        player.update(delta);
        if(player.getBody().getPosition().y < -19)
            player.getBody().setTransform(100 / PPM, 100/ PPM, 0f);

        inventoryState.update(delta);
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.jump();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getBody().getLinearVelocity().x <= 2)
            player.toRight();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getBody().getLinearVelocity().x >= -2)
            player.toLeft();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            if(player.isPlayer()) {
                player.setPlayer(false);
            }else {
                player.setPlayer(true);
            }
        }
    }
    private void worldUpdate(float delta) {
        world.step(1 / 60f, 6 , 2);
    }
    private void cameraUpdate() {
       CameraStyles.lerpToTarget(camera, player.getBody().getPosition());
        float startX = camera.viewportWidth / 2;
        float startY = camera.viewportHeight / 2;
        CameraStyles.boundary(camera, startX, startY, levelWidth - startX * 2, levelHeight - startY * 2);

    }
    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRender();
        inventoryState.render();
    }
    private void worldRender() {
        renderer.setView(camera);
        renderer.render(backgroundLayers);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
        batch.end();
        renderer.render(foregroundLayers);
        b2dr.render(world, camera.combined);
    }
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        atlas.dispose();
        inventoryState.dispose();
        System.out.println("DISPOSE PlayState");
    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void resize(int w, int h) {
        viewport.update(w, h);
        inventoryState.resize(w,h);
    }
    public World getWorld() {
        return world;
    }
    public Application getApp() {
        return app;
    }
    public TextureAtlas getAtlas() {
        return atlas;
    }
}
