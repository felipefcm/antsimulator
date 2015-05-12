
package ffcm.antsim;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.gui.MenuBar;
import ffcm.antsim.input.AppInput;
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

	private AssetManager assetManager;
	
	private Viewport viewport;
	private Viewport guiViewport;
	private SpriteBatch spriteBatch;
	private BitmapFont font;

	private AppInput appInput;

	public World world;
	
	private MenuBar menuBar;
	
	@Override
	public void create() 
	{	
		antSim = this;
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		Resources.instance.Init();

		viewport = Resources.instance.viewport;
		guiViewport = Resources.instance.guiViewport;
		spriteBatch = Resources.instance.spriteBatch;
		assetManager = Resources.instance.assetManager;
		font = Resources.instance.font;

        ECSConfig ecsConfig = new ECSConfig();
        ecsConfig.spriteBatch = Resources.instance.spriteBatch;
        ecsConfig.shapeRenderer = Resources.instance.shapeRenderer;
        ecsConfig.mainCamera = Resources.instance.mainCamera;
        ecsConfig.guiCamera = Resources.instance.guiCamera;
        ecsConfig.assetManager = Resources.instance.assetManager;

		ECSManager.instance.Init(ecsConfig);

		//init entity templates
		ECSManager.instance.entityTemplateManager.ProcessTemplateFile(Gdx.files.internal("data/ant.json"));

		world = new World();
		
		menuBar = new MenuBar();
		//menuBar.Init();
				
		appInput = new AppInput(world);
		
		//Gdx.input.setInputProcessor(new InputMultiplexer(menuBar.stage, appInput));
		
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

		setScreen(new SplashScreen());
	}
	
	@Override
	public void render() 
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		assetManager.update();

		super.render(); //will call current screen render

		//Update();
		
		//DrawGUI();
	}

	private void Update()
	{
		world.Update();
		world.Draw();

        ECSManager.instance.Update();
	}
	
	private void DrawGUI()
	{
		spriteBatch.setProjectionMatrix(Resources.instance.guiCamera.combined);
		spriteBatch.begin();
		{
			font.draw(spriteBatch, Gdx.graphics.getFramesPerSecond() + " | " + world.numAnts, 10.0f, 20.0f);
		}
		spriteBatch.end();
		
		menuBar.Draw();
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);

		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		guiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	}

	@Override
	public void dispose()
	{
		Resources.instance.Dispose();
		
		super.dispose();
	}
}
