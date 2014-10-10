
package ffcm.antsim;

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
	
	public World()
	{
	}
	
	public void Init()
	{
		viewport = ResourceManager._instance.viewport;
		shapeRenderer = ResourceManager._instance.shapeRenderer;
	}
	
	private void DrawWorldOrigin()
	{
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.setColor(Color.RED);
		
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
			
			//vertical
			for(int i = 0; i <= viewport.getWorldWidth() * camera.zoom / GridCellSize; ++i)
			{
				aPoint.set(left + i * GridCellSize, bottom);
				bPoint.set(left + i * GridCellSize, bottom + viewport.getWorldHeight() * camera.zoom);
				
				shapeRenderer.line(aPoint, bPoint);
			}
			
			//horizontal
			for(int i = 0; i <= viewport.getWorldHeight() * camera.zoom / GridCellSize; ++i)
			{
				aPoint.set(left, bottom + i * GridCellSize);
				bPoint.set(left + viewport.getWorldWidth() * camera.zoom, bottom + i * GridCellSize);
				
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
