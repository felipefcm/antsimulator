
package ffcm.antsim.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ffcm.antsim.AntSim;
import ffcm.antsim.resource.Resources;

public class SplashScreen implements Screen
{
    private AssetManager assetManager;

    private Texture loadingTexture;
    private SpriteBatch spriteBatch;
    private float timeLoading = 0;

    @Override
    public void show()
    {
        assetManager = Resources.instance.assetManager;

        spriteBatch = Resources.instance.spriteBatch;
        spriteBatch.setProjectionMatrix(Resources.instance.viewport.getCamera().combined);

        assetManager.load("gfx/loading.png", Texture.class);
		assetManager.finishLoading();

		loadingTexture = assetManager.get("gfx/loading.png");

        assetManager.load("ui/uiSprites.atlas", TextureAtlas.class);
        assetManager.load("gfx/mainSprites.atlas", TextureAtlas.class);
    }

    @Override
    public void render(float delta)
    {
        spriteBatch.begin();
        {
            spriteBatch.draw(loadingTexture, 0, 0, AntSim.V_WIDTH, AntSim.V_HEIGHT);
        }
        spriteBatch.end();

        timeLoading += delta;

        if(assetManager.update())
        {
            if(timeLoading >= 1.0f)
                AntSim.antSim.setScreen(null);
        }
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void dispose()
    {
    }
}
