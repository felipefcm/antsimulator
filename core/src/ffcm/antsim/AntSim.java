
package ffcm.antsim;

import java.util.LinkedList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.systems.ECSManager;

public class AntSim extends ApplicationAdapter 
{
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 480;
	public static final float DESKTOP_SCALE = 2.0f;
	
	OrthographicCamera camera;
	Viewport viewport;
	SpriteBatch spriteBatch;
	
	LinkedList<Ant> antList;
	
	BitmapFont font;
	
	float timeAccum;
	int numFPSAccum;
	int numFPS;
	
	@Override
	public void create() 
	{
		antList = new LinkedList<Ant>();
		
		camera = new OrthographicCamera();
		viewport = new FitViewport(V_WIDTH, V_HEIGHT, camera);
		
		spriteBatch = new SpriteBatch(200);
		
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		
		timeAccum = 0;
		numFPS = 0;
		numFPSAccum = 0;
		
		ResourceManager._instance.InitTextures();
		//EntityFactory._instance.InitEntities();
		
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
		
		Gdx.input.setInputProcessor
		(
			new InputAdapter()
			{
				public boolean touchUp(int screenX, int screenY, int pointer, int button) 
				{
					Vector2 worldPos = viewport.unproject(new Vector2(screenX, screenY));
					
					
					
					return true;
				};
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
		
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		{
			font.draw(spriteBatch, "" + numFPS, 10.0f, 20.0f);
		}
		spriteBatch.end();
	}
	
	@Override
	public void dispose()
	{
		antList.clear();
		spriteBatch.dispose();
		
		super.dispose();
	}
}
