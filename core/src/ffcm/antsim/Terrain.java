
package ffcm.antsim;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import ffcm.antsim.resource.Resources;

public class Terrain
{
	public TiledMap map;
	private TiledMapRenderer mapRenderer;

	public float mapScale;
	public Vector2 mapSizePixels;
	public Vector2 mapSizeWorld;
	public int tileSize;

	private SpriteBatch spriteBatch;

	public Vector2 nestPosition;
	
	public Terrain()
	{
		spriteBatch = Resources.instance.spriteBatch;

		map = Resources.instance.assetManager.get("terrain/terrain1.tmx", TiledMap.class);

		MapProperties nestObjProp = GetObjectByName("nest").getProperties();
        nestPosition = new Vector2((float) nestObjProp.get("x"), (float) nestObjProp.get("y"));
		
		tileSize = map.getProperties().get("tilewidth", Integer.class);

		mapSizePixels = new Vector2(map.getProperties().get("width", Integer.class) * tileSize, map.getProperties().get("height", Integer.class) * tileSize);

		//mapScale = 1024.0f / mapSizePixels.x;
		mapScale = 1.0f;

		mapSizeWorld = mapSizePixels.cpy().scl(mapScale);
		
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

	public TiledMapTileLayer.Cell GetCell(String layerName, final Vector2 worldPos)
	{
		Vector2 mapPos = worldPos.cpy().scl(1.0f / mapScale);

        int row = (int)(mapPos.y / tileSize);
        int col = (int)(mapPos.x / tileSize);

        //Log.Debug("Got Tile at " + row + "," + col);

        return ((TiledMapTileLayer) map.getLayers().get(layerName)).getCell(col, row);
    }
	
	public void Dispose()
	{
	}
}












