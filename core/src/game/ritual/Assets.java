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
		return manager.get(name, Texture.class);
	}

	private static void loadUI(TextureParameter param) {
		FileHandle handle = Gdx.files.internal("ui/");
		for (FileHandle file: handle.list()) {
			manager.load(file.toString(), Texture.class, param);
			System.out.println("loaded " + file);
		}
		System.out.println("done loading ui!");
	}

	private static void loadGems(TextureParameter param) {
		FileHandle handle = Gdx.files.internal("gems/");
		for (FileHandle file: handle.list()) {
			manager.load(file.toString(), Texture.class, param);
			System.out.println("loaded " + file);
		}
		System.out.println("done loading gems!");
	}

	private static void loadVillagers(TextureParameter param) {
		FileHandle handle = Gdx.files.internal("villagers/");
		for (FileHandle directory: handle.list()) {
			for (FileHandle file : directory.list()) {
				manager.load(file.toString(), Texture.class, param);
				System.out.println("loaded " + file);
			}
		}
		System.out.println("done loading villagers!");
	}

	public static void load() {
		TextureParameter param = new TextureParameter();
		param.genMipMaps = true;
		param.minFilter = TextureFilter.MipMapLinearLinear;
		param.magFilter = TextureFilter.MipMapLinearLinear;
		loadUI(param);
		loadVillagers(param);
		loadGems(param);
		manager.finishLoading();
	}

	public static void dispose() {

	}
}
