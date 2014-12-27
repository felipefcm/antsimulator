
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
import ffcm.antsim.resource.ResourceManager;
import ffcm.antsim.systems.DrawSystem;
import ffcm.antsim.systems.MoveSystem;
import ffcm.antsim.systems.SelectSystem;
import ffcm.antsim.systems.SpatialPartitioningSystem;
import ffcm.antsim.systems.WanderBehaviourSystem;
import ffcm.ecs.ECSManager;
import ffcm.ecs.EntityFactory;

public class AntSim extends ApplicationAdapter 
{
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 480;
	public static final float DESKTOP_SCALE = 2.0f;
	
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
	
	private MoveSystem moveSystem;
	private DrawSystem drawSystem;
	private WanderBehaviourSystem wanderSystem;
	private SpatialPartitioningSystem spatialPartSystem;
	public SelectSystem selectSystem;
	
	@Override
	public void create() 
	{	
		antSim = this;
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		ResourceManager._instance.InitTextures();
		EntityFactory._instance.Init("data/entities.json");
		
		viewport = ResourceManager._instance.viewport;
		guiViewport = ResourceManager._instance.guiViewport;
		spriteBatch = ResourceManager._instance.spriteBatch;
		font = ResourceManager._instance.font;
		
		moveSystem = new MoveSystem();
		drawSystem = new DrawSystem();
		wanderSystem = new WanderBehaviourSystem();
		spatialPartSystem = new SpatialPartitioningSystem();
		selectSystem = new SelectSystem(spatialPartSystem);
		
		ECSManager._instance.AddSystem(moveSystem);
		ECSManager._instance.AddSystem(drawSystem);
		ECSManager._instance.AddSystem(wanderSystem);
		ECSManager._instance.AddSystem(spatialPartSystem);
		ECSManager._instance.AddSystem(selectSystem);
		
		world = new World();
		
		menuBar = new MenuBar();
		menuBar.Init();
				
		appInput = new AppInput(world);
		
		Gdx.input.setInputProcessor(new InputMultiplexer(menuBar.stage, appInput));
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
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
		
		ECSManager._instance.Update();
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
		spriteBatch.setProjectionMatrix(ResourceManager._instance.guiCamera.combined);
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
		spriteBatch.dispose();
		
		super.dispose();
	}
}
