package ffcm.antsim.resource;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ffcm.antsim.AntSim;

public class ResourceManager
{
	public static ResourceManager _instance = new ResourceManager();
	
	public OrthographicCamera camera;
	public Viewport viewport;
	public SpriteBatch spriteBatch;
	
	public BitmapFont font;
	
	private Texture antTexture;
	public HashMap<String, TextureRegion> antTextureRegionMap;
	
	private Texture objectsTexture;
	public HashMap<String, TextureRegion> objectsTextureRegionMap;
	
	public ResourceManager()
	{
		camera = new OrthographicCamera();
		viewport = new FitViewport(AntSim.V_WIDTH, AntSim.V_HEIGHT, camera);
		
		spriteBatch = new SpriteBatch(200);
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		antTextureRegionMap = new HashMap<String, TextureRegion>();
		objectsTextureRegionMap = new HashMap<String, TextureRegion>();
	}
	
	public void InitTextures()
	{
		antTexture = new Texture(Gdx.files.internal("gfx/ants.png"));
		objectsTexture = new Texture(Gdx.files.internal("gfx/objects.png"));
		
		ProcessAntSprites();
		ProcessObjectSprites();
	}
	
	private void ProcessAntSprites()
	{
		JsonReader jsonReader = new JsonReader();
		JsonValue rootObject = jsonReader.parse(Gdx.files.internal("data/ants.json"));
		
		JsonValue child = rootObject.child;
		
		while(child != null)
		{
			String name = child.name;
			Vector2 origin = new Vector2();
			Vector2 size = new Vector2();
			
			float[] originValues = child.get("origin").asFloatArray();
			float[] sizeValues = child.get("size").asFloatArray();
			
			origin.set(originValues[0], originValues[1]);
			size.set(sizeValues[0], sizeValues[1]);
			
			TextureRegion region = new TextureRegion(antTexture, (int) origin.x, (int) origin.y, (int) size.x, (int) size.y);
			
			antTextureRegionMap.put(name, region);
			
			child = child.next();
		}
	}
	
	private void ProcessObjectSprites()
	{
		JsonReader jsonReader = new JsonReader();
		JsonValue rootObject = jsonReader.parse(Gdx.files.internal("data/objects.json"));
		
		JsonValue child = rootObject.child;
		
		while(child != null)
		{
			String name = child.name;
			Vector2 origin = new Vector2();
			Vector2 size = new Vector2();
			
			float[] originValues = child.get("origin").asFloatArray();
			float[] sizeValues = child.get("size").asFloatArray();
			
			origin.set(originValues[0], originValues[1]);
			size.set(sizeValues[0], sizeValues[1]);
			
			TextureRegion region = new TextureRegion(objectsTexture, (int) origin.x, (int) origin.y, (int) size.x, (int) size.y);
			
			objectsTextureRegionMap.put(name, region);
			
			child = child.next();
		}
	}
}
