
package ffcm.antsim;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.resource.Resources;
import ffcm.antsim.screen.SplashScreen;
import ffcm.ecs.ECSConfig;
import ffcm.ecs.ECSManager;

public class AntSim extends Game
{
	public static final int V_WIDTH = 1024;
	public static final int V_HEIGHT = 768;
	public static final float DESKTOP_SCALE = 1.2f;
	
	public static AntSim antSim;

	private Viewport viewport;
	private Viewport guiViewport;
	
	@Override
	public void create() 
	{	
		antSim = this;
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		Resources.instance.Init();

		viewport = Resources.instance.viewport;
		guiViewport = Resources.instance.guiViewport;

        ECSConfig ecsConfig = new ECSConfig();
        ecsConfig.spriteBatch = Resources.instance.spriteBatch;
        ecsConfig.shapeRenderer = Resources.instance.shapeRenderer;
        ecsConfig.mainCamera = Resources.instance.mainCamera;
        ecsConfig.guiCamera = Resources.instance.guiCamera;
        ecsConfig.assetManager = Resources.instance.assetManager;

		ECSManager.instance.Init(ecsConfig);

		//init entity templates
		ECSManager.instance.entityTemplateManager.ProcessTemplateFile(Gdx.files.internal("data/ant.json"));

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

		setScreen(new SplashScreen());
	}
	
	@Override
	public void render() 
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render(); //will call current screen render
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		guiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		super.resize(width, height);
	}

	@Override
	public void dispose()
	{
		Resources.instance.Dispose();
		
		super.dispose();
	}
}
