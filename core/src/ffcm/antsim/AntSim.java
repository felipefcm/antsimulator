
package ffcm.antsim;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.gui.MenuBar;
import ffcm.antsim.input.AppInput;
import ffcm.antsim.resource.Resources;
import ffcm.ecs.ECSConfig;
import ffcm.ecs.ECSManager;

public class AntSim extends ApplicationAdapter 
{
	public static final int V_WIDTH = 1024;
	public static final int V_HEIGHT = 768;
	public static final float DESKTOP_SCALE = 1.2f;
	
	public static AntSim antSim;
	
	float timeAccum = 0;
	int numFPSAccum = 0;
	int numFPS = 0;
	
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
		font = Resources.instance.font;

        ECSConfig ecsConfig = new ECSConfig();
        ecsConfig.spriteBatch = Resources.instance.spriteBatch;
        ecsConfig.shapeRenderer = Resources.instance.shapeRenderer;
        ecsConfig.mainCamera = Resources.instance.mainCamera;
        ecsConfig.guiCamera = Resources.instance.guiCamera;

		ECSManager.instance.Init(ecsConfig);

		world = new World();
		
		menuBar = new MenuBar();
		menuBar.Init();
				
		appInput = new AppInput(world);
		
		Gdx.input.setInputProcessor(new InputMultiplexer(menuBar.stage, appInput));
		
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
	}
	
	private void Update()
	{
		timeAccum += Gdx.graphics.getDeltaTime();
		
		if(timeAccum <= 1.0f)
		{
			++numFPSAccum;
		}
		else
		{
			numFPS = numFPSAccum;
			
			timeAccum = 0;
			numFPSAccum = 0;
		}
		
		world.Update();
		world.Draw();
		
        ECSManager.instance.Update();
	}
	
	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
		
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		guiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	}
	
	@Override
	public void render() 
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Update();
		
		DrawGUI();
	}
	
	private void DrawGUI()
	{
		spriteBatch.setProjectionMatrix(Resources.instance.guiCamera.combined);
		spriteBatch.begin();
		{
			font.draw(spriteBatch, numFPS + " | " + world.numAnts, 10.0f, 20.0f);
		}
		spriteBatch.end();
		
		menuBar.Draw();
	}
	
	@Override
	public void dispose()
	{
		Resources.instance.Dispose();
		
		super.dispose();
	}
}
