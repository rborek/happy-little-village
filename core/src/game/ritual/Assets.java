package game.ritual;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class Assets {
	public static final AssetManager manager = new AssetManager();

	public static void load() {
		TextureParameter param = new TextureParameter();
		param.genMipMaps = true;
		param.minFilter = TextureFilter.MipMapLinearLinear;
		param.magFilter = TextureFilter.MipMapLinearLinear;
	}

	public static void dispose() {

	}
}
