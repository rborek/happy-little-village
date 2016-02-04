package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class Assets {
	private static final AssetManager manager = new AssetManager();

	public static Texture getTexture(String name) {
		return manager.get("textures/" + name, Texture.class);
	}

	public static Texture[] getTextures(String... strings) {
		Texture[] textures = new Texture[strings.length];
		for (int i = 0; i < strings.length; i++) {
			textures[i] = manager.get("textures/" + strings[i], Texture.class);
		}
		return textures;
	}

	private static void loadTextures(TextureParameter param) {
		loadTextures(Gdx.files.internal("textures"), param);
	}

	private static void loadTextures(FileHandle dir, TextureParameter param) {
		for (FileHandle file : dir.list()) {
			if (file.isDirectory()) {
				loadTextures(file, param);
			} else {
				System.out.println("loading " + file);
				manager.load(file.toString(), Texture.class, param);
			}
		}
	}

	public static void load() {
		TextureParameter param = new TextureParameter();
		param.genMipMaps = true;
		param.minFilter = TextureFilter.MipMapLinearLinear;
		param.magFilter = TextureFilter.MipMapLinearLinear;
		loadTextures(param);
		manager.finishLoading();
	}

	public static void dispose() {

	}
}
