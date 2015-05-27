
package ffcm.antsim.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;

import ffcm.antsim.AntSim;
import ffcm.antsim.resource.Resources;
import ffcm.ecs.ECSManager;
import ffcm.ecs.resources.EntityTemplateManager;

public class LoadingScreen implements Screen
{
    private static final float MinTime = 0.0f;

    private AssetManager assetManager;

    private Texture loadingTexture;
    private SpriteBatch spriteBatch;
    private float timeLoading = 0;

    private EntityTemplateManager entityTemplateManager;
    private Engine ecsEngine;

    private void PreLoad()
    {
        spriteBatch = Resources.instance.spriteBatch;
        spriteBatch.setProjectionMatrix(Resources.instance.viewport.getCamera().combined);

        assetManager.load("ui/uiSprites.atlas", TextureAtlas.class);
        assetManager.load("gfx/mainSprites.atlas", TextureAtlas.class);

        assetManager.load("terrain/terrain1.tmx", TiledMap.class);
    }

    private void PostLoad()
    {
        //init entity templates
        entityTemplateManager.ProcessTemplateFile(Gdx.files.internal("data/ant.json"));
        entityTemplateManager.ProcessTemplateFile(Gdx.files.internal("data/nest.json"));

        Resources.instance.InitSystems();

        AntSim.instance.setScreen(new SimulationScreen());
    }

    @Override
    public void show()
    {
        assetManager = Resources.instance.assetManager;
        entityTemplateManager = ECSManager.instance.entityTemplateManager;
        ecsEngine = ECSManager.instance.ecsEngine;

        assetManager.load("gfx/loading.png", Texture.class);
		assetManager.finishLoading();

		loadingTexture = assetManager.get("gfx/loading.png");

		PreLoad();
    }

    @Override
    public void render(float delta)
    {
        spriteBatch.setProjectionMatrix(Resources.instance.viewport.getCamera().combined);
        spriteBatch.begin();
        {
            spriteBatch.draw(loadingTexture, 0, 0, AntSim.V_WIDTH, AntSim.V_HEIGHT);
        }
        spriteBatch.end();

        timeLoading += delta;

        if(assetManager.update() && timeLoading >= MinTime)
            PostLoad();
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
        dispose();
    }

    @Override
    public void dispose()
    {
        assetManager.unload("gfx/loading.png");
    }
}
