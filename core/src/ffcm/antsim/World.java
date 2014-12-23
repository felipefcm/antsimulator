
package ffcm.antsim;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.comps.CTransform;
import ffcm.antsim.resource.QuadTree;
import ffcm.antsim.resource.ResourceManager;

public class World
{
	//both sizes in world coordinates
	private static final int GridCellSize = 20; 
	public static final Vector2 WorldSize = new Vector2(1024, 1024);
	
	public boolean drawGrid = false;	
	
	private Viewport viewport;
	private ShapeRenderer shapeRenderer;
	
	private LinkedList<Ant> antList;
	
	private Terrain terrain;
	
	private QuadTree quadTree;
	
	public World()
	{		
		viewport = ResourceManager._instance.viewport;
		shapeRenderer = ResourceManager._instance.shapeRenderer;
		
		antList = new LinkedList<Ant>();
		terrain = new Terrain(WorldSize);
	}
	
	public void Update()
	{
		if(quadTree != null)
			quadTree.Clear();
		
		quadTree = new QuadTree(new Rectangle(-WorldSize.x * 0.5f, -WorldSize.y * 0.5f, WorldSize.x, WorldSize.y));
		
		for(int i = 0; i < antList.size(); ++i)
			quadTree.Add(antList.get(i).GetComponent(CTransform.class).position);
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
	
	public void AddAnt(Ant ant)
	{
		antList.add(ant);
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
	
	public int GetNumAnts()
	{
		return antList.size();
	}
}
