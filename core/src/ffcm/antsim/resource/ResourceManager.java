package ffcm.antsim.resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import ffcm.antsim.AntSim;

public class ResourceManager
{
	public static ResourceManager _instance = new ResourceManager();
	
	public OrthographicCamera mainCamera;
	public OrthographicCamera guiCamera;
	
	public FitViewport viewport;
	public FitViewport guiViewport;
	
	public SpriteBatch spriteBatch;
	public ShapeRenderer shapeRenderer;
	
	public BitmapFont font;
	
	private Texture spritesTexture;
	public HashMap<String, TextureRegion> spriteTextureRegionMap;
	
	public Random random;
	
	public ResourceManager()
	{		
		mainCamera = new OrthographicCamera();
		viewport = new FitViewport(AntSim.V_WIDTH, AntSim.V_HEIGHT, mainCamera);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		
		guiCamera = new OrthographicCamera();
		guiViewport = new FitViewport(AntSim.V_WIDTH, AntSim.V_HEIGHT, guiCamera);
		guiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		
		spriteBatch = new SpriteBatch(200);
		shapeRenderer = new ShapeRenderer(200);
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		spriteTextureRegionMap = new HashMap<String, TextureRegion>();
		
		random = new Random(new Date().getTime());
	}
	
	public void InitTextures()
	{
		spritesTexture = new Texture(Gdx.files.internal("gfx/sprites.png"));
		
		CreateSprites();
	}
	
	private void CreateSprites()
	{
		JsonReader jsonReader = new JsonReader();
		JsonValue root = jsonReader.parse(Gdx.files.internal("data/sprites.json"));
		
		for(JsonValue child = root.child; child != null; child = child.next)
		{
			String name = child.name;
			
			int[] origin = child.get("origin").asIntArray();
			int[] size = child.get("size").asIntArray();
			
			TextureRegion region = new TextureRegion(spritesTexture, origin[0], origin[1], size[0], size[1]);
			
			spriteTextureRegionMap.put(name, region);
		}
	}
}
