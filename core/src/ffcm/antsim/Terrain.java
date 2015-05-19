
package ffcm.antsim;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import ffcm.antsim.resource.Resources;

public class Terrain
{
	public TiledMap map;
	private TiledMapRenderer mapRenderer;

	public float mapScale;

	private SpriteBatch spriteBatch;

	public Vector2 nestPosition;
	
	public Terrain()
	{
		spriteBatch = Resources.instance.spriteBatch;

		map = Resources.instance.assetManager.get("terrain/terrain1.tmx", TiledMap.class);

		MapProperties nestObjProp = GetObjectByName("nest").getProperties();
        nestPosition = new Vector2((float) nestObjProp.get("x"), (float) nestObjProp.get("y"));
		
		int mapWidth = map.getProperties().get("width", Integer.class);
		//int mapHeight = map.getProperties().get("height", Integer.class);

		int tilePixelWidth = map.getProperties().get("tilewidth", Integer.class);
		//int tilePixelHeight = map.getProperties().get("tileheight", Integer.class);
		
		mapScale = World.WorldSize.x / (float)(mapWidth * tilePixelWidth);
		
		mapRenderer = new OrthogonalTiledMapRenderer(map, mapScale, spriteBatch);
		mapRenderer.setView(Resources.instance.mainCamera);
	}
	
	public void Draw()
	{
		mapRenderer.setView(Resources.instance.mainCamera);
		mapRenderer.render();
	}

	public MapObject GetObjectByName(String name)
	{
		return map.getLayers().get("objects").getObjects().get(name);
	}
	
	public void Dispose()
	{
	}
}












