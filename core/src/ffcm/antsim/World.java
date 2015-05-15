
package ffcm.antsim;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.entity.Ant;
import ffcm.antsim.entity.EntityFactory;
import ffcm.antsim.resource.Resources;

public class World
{
	//both sizes in world coordinates
	private static final int GridCellSize = 32; 
	public static final Vector2 WorldSize = new Vector2(1024, 1024);
	
	public boolean drawGrid = false;
	
	public int numAnts = 0;
	
	private Viewport viewport;
	private ShapeRenderer shapeRenderer;
	
	public Terrain terrain;
	
	public World()
	{		
		viewport = Resources.instance.viewport;
		shapeRenderer = Resources.instance.shapeRenderer;
		
		terrain = new Terrain(WorldSize);
	}
	
	public void Update()
	{
		
	}
	
	public void Draw()
	{
		terrain.Draw();
		
		if(drawGrid)
		{
			DrawGrid();
			DrawWorldOrigin();
		}
	}
	
	public void SpawnAnts(int num, Vector2 worldPos)
	{
		for(int i = 0; i < num; ++i)
		{
			Ant ant = EntityFactory.instance.CreateAnt();

			ant.transform.position.set(worldPos);
		}
		
		this.numAnts += num;
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
}
