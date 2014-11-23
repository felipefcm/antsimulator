
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
	private ArrayList<MenuItem> items;
	private ArrayList<Rectangle> itemRects;
	
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera guiCamera;
	
	private Rectangle barRect;
	private float itemSize;
	
	private int selectedItem;
	
	public MenuButton createAntButton;
	public MenuButton createFoodButton;
	
	public MenuBar()
	{
		items = new ArrayList<MenuItem>();
		itemRects = new ArrayList<Rectangle>();
		
		selectedItem = -1;
		
		createAntButton = new MenuButton();
		createFoodButton = new MenuButton();
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
		
		itemSize = barRect.width * 0.9f;
		
		AddItem(createAntButton);
		AddItem(createFoodButton);
	}
	
	public void AddItem(MenuItem item)
	{		
		itemRects.add(new Rectangle(barRect.width * 0.05f, barRect.y * 1.05f + items.size() * itemSize * 1.05f, itemSize, itemSize));
		items.add(item);
	}
	
	public boolean ProcessInputInMenu(final Vector2 screenPoint)
	{
		Vector3 pos = guiCamera.unproject(new Vector3(screenPoint, 0));
		
		if(!barRect.contains(pos.x, pos.y))
			return false;
		
		for(int i = 0; i < itemRects.size(); ++i)
		{
			if(itemRects.get(i).contains(pos.x, pos.y))
			{
				selectedItem = i;
				//items.get(i).
			}
		}
		
		return true;
	}
	
	public void Draw()
	{
		shapeRenderer.setProjectionMatrix(guiCamera.combined);
		shapeRenderer.setColor(Color.BLACK);
		
		shapeRenderer.begin(ShapeType.Filled);
		{
			//bg
			shapeRenderer.rect(barRect.x, barRect.y, barRect.width, barRect.height);
			
			for(int i = 0; i < items.size(); ++i)
			{
				shapeRenderer.setColor(selectedItem == i ? Color.RED : Color.WHITE);
				
				Rectangle itemRect = itemRects.get(i);
				shapeRenderer.rect(itemRect.x, itemRect.y, itemRect.width, itemRect.height);
			}
		}
		shapeRenderer.end();
	}
}











