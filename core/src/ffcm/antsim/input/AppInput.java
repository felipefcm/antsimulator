
package ffcm.antsim.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.AntWorld;
import ffcm.antsim.resource.Log;
import ffcm.antsim.resource.Resources;
import ffcm.ecs.ECSManager;

public class AppInput extends InputAdapter
{
	private boolean rightButtonDown = false;
	private Vector2 mouseMoveStarted;
	
	private Viewport viewport;
	private OrthographicCamera mainCamera;
	private AntWorld world;
	
	public AppInput(AntWorld world)
	{
		viewport = Resources.instance.viewport;
		mainCamera = Resources.instance.mainCamera;
		
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
			
			//antWorld.SpawnAnts(1, worldPos);
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
		
		viewport.getCamera().translate(direction.x, direction.y, 0);
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
	    if(!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
	        return false;

		if(keycode == Input.Keys.G) //toggle grid
		{
			world.drawGrid = !world.drawGrid;
			return true;
		}
		else if(keycode == Input.Keys.A) //insert a huge amount of ants
		{
			Vector2 mouseScreenPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			Vector2 worldPos = viewport.unproject(mouseScreenPos);
			
			world.SpawnAnts(250, worldPos, true);
		}
		else if(keycode == Input.Keys.R) //refresh entity template descriptions
		{
			ECSManager.instance.entityTemplateManager.ReloadTemplates();

			Log.Info("Refreshed entity templates!");
		}
		else if(keycode == Input.Keys.T)
		{
			Resources.instance.spatialPartitioningSystem.drawTree = !Resources.instance.spatialPartitioningSystem.drawTree;
		}
		
		return false;
	}
}
