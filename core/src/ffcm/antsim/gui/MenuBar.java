
package ffcm.antsim.gui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import ffcm.antsim.resource.ResourceManager;

public class MenuBar
{
	private ArrayList<IMenuItem> items;
	
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera guiCamera;
	
	private Rectangle barRect;
	
	public MenuBar()
	{
		items = new ArrayList<IMenuItem>();
	}
	
	public void Init()
	{
		shapeRenderer = ResourceManager._instance.shapeRenderer;
		guiCamera = ResourceManager._instance.guiCamera;
		
		barRect = new Rectangle
				(
					0, guiCamera.viewportHeight * 0.1f, 
					guiCamera.viewportWidth * 0.05f, guiCamera.viewportHeight * 0.8f
				);
		
		AddItem
		(
			new IMenuItem()
			{
				@Override
				public void Pressed()
				{
					
				}
				
				@Override
				public String GetLabel()
				{
					return "Ant";
				}
			}
		);
	}
	
	public void AddItem(IMenuItem item)
	{
		items.add(item);
	}
	
	public boolean IsPointInMenuBar(final Vector2 screenPoint)
	{
		Vector3 pos = guiCamera.unproject(new Vector3(screenPoint, 0));
		
		return barRect.contains(pos.x, pos.y);
	}
	
	public void Draw()
	{
		shapeRenderer.setProjectionMatrix(guiCamera.combined);
		shapeRenderer.setColor(Color.BLACK);
		
		shapeRenderer.begin(ShapeType.Filled);
		{
			shapeRenderer.rect(barRect.x, barRect.y, barRect.width, barRect.height);
		}
		shapeRenderer.end();
	}
}
