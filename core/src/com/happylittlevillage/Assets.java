package com.happylittlevillage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.happylittlevillage.rituals.Ritual;
import com.happylittlevillage.screens.GameScreen;

import java.util.HashMap;

public final class Assets {
	private static final AssetManager manager = new AssetManager();
	private static final HashMap<Integer, BitmapFont> fonts = new HashMap<Integer, BitmapFont>();
	private static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/MoolBoran.ttf"));
	private static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

	private Assets() {
	}

	// returns the texture of a given file path
	public static Texture getTexture(String path) {
		return manager.get("data/textures/" + path, Texture.class);
	}

	//Updates the AssetManager, keeping it loading any assets in the preload queue.
	public static boolean update() {
		return manager.update();
	}

	// returns an array of all the data.textures listed
	public static Texture[] getTextures(String... paths) {
		Texture[] textures = new Texture[paths.length];
		for (int i = 0; i < paths.length; i++) {
			textures[i] = manager.get("data/textures/" + paths[i], Texture.class);
		}
		return textures;
	}

	public static void updateFonts() {
		Object[] keys = fonts.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			BitmapFont font = generateFont((Integer) keys[i]);
			fonts.get((Integer) keys[i]).dispose(); // without explicitly disposing, the font will stay in memory
			fonts.put((Integer) keys[i], font);
		}
	}

	public static void unloadDir(String dir) {
		FileHandle directory = Gdx.files.internal(dir);
		for (FileHandle file : directory.list()) {
			System.out.println("disposing file " + file);
			manager.unload(file.toString());
		}
	}


	private static BitmapFont generateFont(int size) {
		float scale = 1.0f * Gdx.graphics.getWidth() / GameScreen.WIDTH * Gdx.graphics.getHeight() / GameScreen.HEIGHT;
		if (scale < 1) {
			scale = 1;
		}
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 1.5f * scale;
		parameter.minFilter = Texture.TextureFilter.Linear;
		parameter.magFilter = Texture.TextureFilter.Linear;
		parameter.size = (int) Math.round(size * scale);
		BitmapFont font = generator.generateFont(parameter);
		font.getData().setScale(1 / scale);
		return font;
	}

	public static BitmapFont getFont(int size) {
		size *= 1.5;
		if (fonts.containsKey(size)) {
			return fonts.get(size);
		} else {
			BitmapFont font = generateFont(size);
			fonts.put(size, font);
			return font;
		}
	}


	// returns an array of data.textures given a folder/prefix
	public static Texture[] getTextures(String prefix) {
		FileHandle dir = Gdx.files.internal("data/" + prefix);
		if (dir.isDirectory()) {
			Texture[] textures = new Texture[dir.list().length];
			for (int i = 0; i < dir.list().length; i++) {
				textures[i] = manager.get(dir.list()[i].toString(), Texture.class);
			}
			return textures;
		}
		return null;
	}

	private static void loadTextures(TextureParameter param) {
		loadTextures(Gdx.files.internal("data/textures"), param);
	}

	// recursively goes through every directory, loading all files within them as a Texture
	private static void loadTextures(FileHandle dir, TextureParameter param) {
		for (FileHandle file : dir.list()) {
			if (file.isDirectory()) {
				loadTextures(file, param);
			} else if (!manager.isLoaded(file.toString())) {
				System.out.println("loading " + file);
				manager.load(file.toString(), Texture.class, param);
			}
		}
	}

	public static void loadMenuTextures() {
		TextureParameter param = new TextureParameter();
		param.genMipMaps = true;
		param.minFilter = TextureFilter.MipMapLinearLinear;
		param.magFilter = TextureFilter.Linear;
		loadTextures(Gdx.files.internal("data/textures/menu"), param);
		manager.finishLoading();
	}

	public static void loadLoadingScreenTextures() {
		TextureParameter param = new TextureParameter();
		param.genMipMaps = true;
		param.minFilter = TextureFilter.MipMapLinearLinear;
		param.magFilter = TextureFilter.Linear;
		loadTextures(Gdx.files.internal("data/textures/loading"), param);
		manager.finishLoading();
	}

	public static TextureAtlas getAtlas(String name) {
		return manager.get("data/atlas/" + name + ".atlas");
	}

	public static void loadAtlases() {
		FileHandle dir = Gdx.files.internal("data/atlas");
		for (FileHandle file : dir.list()) {
			if (file.toString().contains(".atlas")) {
				manager.load(file.toString(), TextureAtlas.class);
			}
		}
	}

	public static void load() {
		TextureParameter param = new TextureParameter();
		param.genMipMaps = true;
		param.minFilter = TextureFilter.MipMapLinearLinear;
		param.magFilter = TextureFilter.Linear;
		loadTextures(param);
		loadAtlases();
		manager.finishLoading();
		Ritual.load();
	}

	public static void dispose() {

	}

}
