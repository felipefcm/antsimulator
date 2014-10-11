
package ffcm.antsim;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.resource.ResourceManager;

public class World
{
	private static final int GridCellSize = 20; //world coordinates
	
	private Viewport viewport;
	private ShapeRenderer shapeRenderer;
	
	private LinkedList<Ant> antList;
	
	public World()
	{
		antList = new LinkedList<Ant>();
	}
	
	public void Init()
	{
		viewport = ResourceManager._instance.viewport;
		shapeRenderer = ResourceManager._instance.shapeRenderer;
	}
	
	public void AddAnt(Ant ant)
	{
		antList.add(ant);
	}
	
	public int GetNumAnts()
	{
		return antList.size();
	}
	
	private void DrawWorldOrigin()
	{
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.setColor(Color.BLUE);
		
		shapeRenderer.begin(ShapeType.Filled);
		{
			shapeRenderer.circle(0, 0, 4.0f);
		}
		shapeRenderer.end();
	}
	
	private void DrawGrid()
	{
		Vector2 aPoint = new Vector2();
		Vector2 bPoint = new Vector2();
		
		OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
		
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.setColor(Color.BLACK);
		
		shapeRenderer.begin(ShapeType.Line);
		{
			float left = camera.position.x - (viewport.getWorldWidth() * camera.zoom) / 2.0f;
			float bottom = camera.position.y - (viewport.getWorldHeight() * camera.zoom) / 2.0f;
			float width = viewport.getWorldWidth() * camera.zoom;
			float height = viewport.getWorldHeight() * camera.zoom;
			
			//vertical
			for(int i = (int)(left / GridCellSize); i <= (left + width) / GridCellSize; ++i)
			{				
				aPoint.set(i * GridCellSize, bottom);
				bPoint.set(i * GridCellSize, bottom + height);
				
				shapeRenderer.line(aPoint, bPoint);
			}
			
			//horizontal
			for(int i = (int)(bottom / GridCellSize); i <= (bottom + height) / GridCellSize; ++i)
			{
				aPoint.set(left, i * GridCellSize);
				bPoint.set(left + width, i * GridCellSize);
				
				shapeRenderer.line(aPoint, bPoint);
			}
		}
		shapeRenderer.end();
	}
	
	public void Draw()
	{
		DrawGrid();
		DrawWorldOrigin();
	}
}
