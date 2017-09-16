package com.artworld.game;

import com.artworld.game.managers.GameStateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Application extends ApplicationAdapter {
	public static final String TITLE = "Honorary father";
	public static final float VERSION = 1.0f;
	public static final int V_WIDTH = 400 * 2;
	public static final int V_HEIGHT = 208 * 2;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private GameStateManager gsm;
	private AssetManager assetManager;
    private TextureAtlas textureAtlas;
    private Skin skin;

	@Override
	public void create () {

		batch = new SpriteBatch();
		assetManager = new AssetManager();
		queueAssets();
		camera = new OrthographicCamera();
        skin = assetManager.get("ui/skin.json", Skin.class);
		//textureAtlas = new TextureAtlas("Graphics/Atlas.pack");
		gsm = new GameStateManager(this);
	}
	private void queueAssets() {
		assetManager.load("ui/skin.json", Skin.class);
		assetManager.load("ui/skin.atlas", TextureAtlas.class);
		assetManager.finishLoading();
	}
	@Override
	public void render () {
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
	}
	@Override
	public void resize(int width, int height) {
		gsm.resize(width,height);
	}
	@Override
	public void pause() {
		gsm.pause();
	}
	@Override
	public void resume() {
		gsm.resume();
	}
	@Override
	public void dispose () {
		gsm.dispose();
		batch.dispose();
		assetManager.dispose();
        skin.dispose();
	}
	public SpriteBatch getBatch() {
		return batch;
	}
	public OrthographicCamera getCamera() {
		return camera;
	}
	public AssetManager getAssetManager() {
		return assetManager;
	}
    public Skin getSkin() {
        return skin;
    }
}
