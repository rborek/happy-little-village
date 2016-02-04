package game.ritual;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class Assets {
	private static final AssetManager manager = new AssetManager();
	private static final String[] ui = new String[]{"food.png", "water.png"};

	public static Texture getTexture(String name) {
		return manager.get(name, Texture.class);
	}

	private static void loadUI(TextureParameter param) {
		for (String s : ui) {
			manager.load("ui/" + s, Texture.class, param);
		}
		System.out.println("done loading!");
	}

	public static void load() {
		TextureParameter param = new TextureParameter();
		param.genMipMaps = true;
		param.minFilter = TextureFilter.MipMapLinearLinear;
		param.magFilter = TextureFilter.MipMapLinearLinear;
		loadUI(param);
		manager.finishLoading();
	}

	public static void dispose() {

	}
}
