
package ffcm.antsim.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.Ant;
import ffcm.antsim.AntSim;
import ffcm.antsim.World;
import ffcm.antsim.comps.CTransform;
import ffcm.antsim.comps.CVelocity;
import ffcm.antsim.resource.Log;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.ECSManager;
import ffcm.ecs.EntityFactory;

public class AppInput extends InputAdapter
{
	private boolean rightButtonDown = false;
	private Vector2 mouseMoveStarted;
	
	private Viewport viewport;
	private OrthographicCamera mainCamera;
	private World world;
	
	public AppInput(World world)
	{
		viewport = ResourceManager._instance.viewport;
		mainCamera = ResourceManager._instance.mainCamera;
		
		this.world = world;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		if(button == Input.Buttons.RIGHT)
		{
			rightButtonDown = true;
			mouseMoveStarted = viewport.unproject(new Vector2(screenX, screenY));
		}
		else if(button == Input.Buttons.LEFT)
		{
			Vector2 worldPos = viewport.unproject(new Vector2(screenX, screenY));
			
			Log.Info("Clicked on world position: " + worldPos.x + ", " + worldPos.y);
			
			final int antsToAdd = 10;
			
			for(int i = 0; i < antsToAdd; ++i)
			{
				Ant ant = EntityFactory._instance.CreateEntity(Ant.class);
				
				ant.GetComponent(CTransform.class).position.set(worldPos);
				ant.GetComponent(CVelocity.class).vector.set(ResourceManager._instance.random.nextFloat() * (ResourceManager._instance.random.nextBoolean() ? 1.0f : -1.0f), ResourceManager._instance.random.nextFloat() * (ResourceManager._instance.random.nextBoolean() ? 1.0f : -1.0f));
				
				ECSManager._instance.AddEntity(ant);
				world.AddAnt(ant);
			}
			
			world.numAnts += antsToAdd;
		}
		else	
			return false;
		
		return true;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		if(!rightButtonDown)
			return false;
		
		Vector2 current = viewport.unproject(new Vector2(screenX, screenY));
		
		Vector2 direction = mouseMoveStarted.cpy().sub(current);
		
		((OrthographicCamera) viewport.getCamera()).translate(direction.x, direction.y, 0);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) 
	{
		if(button == Input.Buttons.RIGHT)
		{
			rightButtonDown = false;
			return true;
		}
		
		if(button != Input.Buttons.LEFT)
			return false;
		
		Vector2 worldPos = viewport.unproject(new Vector2(screenX, screenY));
		
		Log.Info("Clicked on world position: " + worldPos.x + ", " + worldPos.y);
		
		return true;
	};
	
	@Override
	public boolean scrolled(int amount)
	{
		float newZoom = mainCamera.zoom + (float) amount * 0.1f;
		
		if(newZoom > 0 && newZoom < 5.0f)
		{
			mainCamera.zoom = newZoom;
			viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		}
		
		return true;
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		if(keycode == Input.Keys.G)
		{
			world.drawGrid = !world.drawGrid;
			return true;
		}
		else
		if(keycode == Input.Keys.S)
		{			
			Vector2 mouseScreenPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			
			Vector2 worldPos = viewport.unproject(mouseScreenPos);
			
			Log.Debug("Searching world point: " + worldPos.x + "," + worldPos.y);
			
			AntSim.antSim.selectSystem.Clicked(worldPos);
		}
		
		return false;
	}
}
