
package ffcm.antsim;

import java.util.LinkedList;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.resource.Log;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.ECSManager;
import ffcm.ecs.EntityFactory;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;

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
	
	private float cameraZoom = 1.0f;
	
	private LinkedList<Ant> antList;
	
	@Override
	public void create() 
	{	
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		ResourceManager._instance.InitTextures();
		EntityFactory._instance.Init();
		
		viewport = ResourceManager._instance.viewport;
		spriteBatch = ResourceManager._instance.spriteBatch;
		font = ResourceManager._instance.font;
		
		antList = new LinkedList<Ant>();
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
		
		Gdx.input.setInputProcessor
		(
			new InputAdapter()
			{
				public boolean touchUp(int screenX, int screenY, int pointer, int button) 
				{
					if(button != 0)
						return false;
					
					Vector2 worldPos = viewport.unproject(new Vector2(screenX, screenY));
					Log.Debug("Clicked on (" + worldPos.x + "," + worldPos.y + ")");
					
					Ant ant = EntityFactory._instance.CreateEntity(Ant.class);
					
					ant.GetComponent(CTransform.class).position.set(worldPos);
					ant.GetComponent(CVelocity.class).vector.set(0.1f, 0.1f);
					
					ECSManager._instance.AddEntity(ant);
					
					antList.add(ant);
					
					return true;
				};
				
				
				
				@Override
				public boolean scrolled(int amount)
				{
					
					((OrthographicCamera) viewport.getCamera()).zoom += (float) amount * 0.1f;
					Log.Info("current zoom value = " + ((OrthographicCamera) viewport.getCamera()).zoom);
					
					viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
					
					return true;
				}
			}
		);
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
		
		Update();
		
		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
		spriteBatch.begin();
		{
			font.draw(spriteBatch, "" + numFPS, 10.0f, 20.0f);
		}
		spriteBatch.end();
	}
	
	@Override
	public void dispose()
	{
		spriteBatch.dispose();
		
		super.dispose();
	}
}
