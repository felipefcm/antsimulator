
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
import ffcm.antsim.input.GuiInput;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.ECSManager;
import ffcm.ecs.EntityFactory;

public class AntSim extends ApplicationAdapter 
{
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 480;
	public static final float DESKTOP_SCALE = 2.0f;
	
	float timeAccum = 0;
	int numFPSAccum = 0;
	int numFPS = 0;
	
	private Viewport viewport;
	private SpriteBatch spriteBatch;
	private BitmapFont font;
	
	private AppInput appInput;
	private GuiInput guiInput;
	
	private World world;
	
	private MenuBar menuBar;
	
	@Override
	public void create() 
	{	
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		ResourceManager._instance.InitTextures();
		EntityFactory._instance.Init();
		
		viewport = ResourceManager._instance.viewport;
		spriteBatch = ResourceManager._instance.spriteBatch;
		font = ResourceManager._instance.font;
		
		world = new World();
		
		menuBar = new MenuBar();
		menuBar.Init();
				
		appInput = new AppInput(world);
		guiInput = new GuiInput(menuBar);
		
		Gdx.input.setInputProcessor(new InputMultiplexer(guiInput, appInput));
		
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.55f, 1.0f);
	}
	
	private void Update()
	{
		timeAccum += Gdx.graphics.getDeltaTime();
		
		if(timeAccum < 1.0f)
		{
			++numFPSAccum;
		}
		else
		{
			numFPS = numFPSAccum;
			
			timeAccum = 0;
			numFPSAccum = 0;
		}
		
		ECSManager._instance.Update();
		world.Update();
	}
	
	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
		
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	}
	
	@Override
	public void render() 
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.Draw();
		
		Update();
		
		DrawGUI();
	}
	
	private void DrawGUI()
	{
		spriteBatch.setProjectionMatrix(ResourceManager._instance.guiCamera.combined);
		spriteBatch.begin();
		{
			font.draw(spriteBatch, numFPS + " | " + world.GetNumAnts(), 10.0f, 20.0f);
		}
		spriteBatch.end();
		
		menuBar.Draw();
	}
	
	@Override
	public void dispose()
	{
		spriteBatch.dispose();
		
		super.dispose();
	}
}
