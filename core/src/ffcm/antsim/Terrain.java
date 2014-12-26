
package ffcm.antsim;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.resource.ResourceManager;

public class Terrain
{
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	
	private Vector2 size;
	
	private Viewport viewport;
	private SpriteBatch spriteBatch;
	
	public Terrain(Vector2 worldSize)
	{
		this.size = worldSize;
		
		viewport = ResourceManager._instance.viewport;
		spriteBatch = ResourceManager._instance.spriteBatch;
		
		map = new TmxMapLoader().load("terrain/terrain1.tmx");
		
		int mapWidth = map.getProperties().get("width", Integer.class);
		//int mapHeight = map.getProperties().get("height", Integer.class);
		int tilePixelWidth = map.getProperties().get("tilewidth", Integer.class);
		//int tilePixelHeight = map.getProperties().get("tileheight", Integer.class);
		
		float mapScale = World.WorldSize.x / (float)(mapWidth * tilePixelWidth);
		
		mapRenderer = new OrthogonalTiledMapRenderer(map, mapScale, spriteBatch);
		mapRenderer.setView(ResourceManager._instance.mainCamera);
	}
	
	public void Draw()
	{
		mapRenderer.setView(ResourceManager._instance.mainCamera);
		mapRenderer.render();
	}
	
	public void Dispose()
	{
	
	}
}












