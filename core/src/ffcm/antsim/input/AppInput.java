
package ffcm.antsim.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.Ant;
import ffcm.antsim.World;
import ffcm.antsim.resource.Log;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.ECSManager;
import ffcm.ecs.EntityFactory;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;

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
		
		Ant ant = EntityFactory._instance.CreateEntity(Ant.class);
		
		ant.GetComponent(CTransform.class).position.set(worldPos);
		ant.GetComponent(CVelocity.class).vector.set(0.1f, 0.1f);
		
		ECSManager._instance.AddEntity(ant);
		world.AddAnt(ant);
		
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
	public boolean keyUp(int keycode)
	{
		if(keycode == Input.Keys.G)
		{
			world.drawGrid = !world.drawGrid;
			return true;
		}
		
		return false;
	}
}
