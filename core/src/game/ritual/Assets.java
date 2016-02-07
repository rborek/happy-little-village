package game.ritual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class Assets {
	private static final AssetManager manager = new AssetManager();

	// returns the texture of a given file path
	public static Texture getTexture(String path) {
		return manager.get("textures/" + path, Texture.class);
	}

	// returns an array of all the textures listed
	public static Texture[] getTextures(String... paths) {
		Texture[] textures = new Texture[paths.length];
		for (int i = 0; i < paths.length; i++) {
			textures[i] = manager.get("textures/" + paths[i], Texture.class);
		}
		return textures;
	}

	// returns an array of textures given a folder/prefix
	public static Texture[] getTextures(String prefix) {
		FileHandle dir = Gdx.files.internal("prefix");
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
		loadTextures(Gdx.files.internal("textures"), param);
	}

	// recursively goes through every directory, loading all files within them as a Texture
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
		param.magFilter = TextureFilter.Linear;
		loadTextures(param);
		manager.finishLoading();
	}

	public static void dispose() {

	}
}
