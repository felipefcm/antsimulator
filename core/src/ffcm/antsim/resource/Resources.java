package ffcm.antsim.resource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Date;

import ffcm.antsim.AntSim;
import ffcm.ecs.systems.MoveSystem;
import ffcm.ecs.systems.SpriteDrawSystem;
import ffcm.ecs.systems.ai.WanderSteeringSystem;

public class Resources
{
	public static Resources instance = new Resources();

	public OrthographicCamera mainCamera;
	public OrthographicCamera guiCamera;

	public FitViewport viewport;
	public FitViewport guiViewport;

	public SpriteBatch spriteBatch;
	public ShapeRenderer shapeRenderer;

	public AssetManager assetManager;

	public BitmapFont font;

	public MoveSystem moveSystem;
	public SpriteDrawSystem spriteDrawSystem;
	public WanderSteeringSystem wanderSteeringSystem;
	
	public Resources()
	{
	}

	public void Init()
    {
        mainCamera = new OrthographicCamera();
		viewport = new FitViewport(AntSim.V_WIDTH, AntSim.V_HEIGHT, mainCamera);

		mainCamera.translate(AntSim.V_WIDTH * 0.5f, AntSim.V_HEIGHT * 0.5f, 0);

		guiCamera = new OrthographicCamera();
		guiViewport = new FitViewport(AntSim.V_WIDTH, AntSim.V_HEIGHT, guiCamera);

		spriteBatch = new SpriteBatch(200);
		shapeRenderer = new ShapeRenderer(200);

		font = new BitmapFont();

		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader());

		MathUtils.random.setSeed(new Date().getTime());
    }

	public void Dispose()
    {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        assetManager.dispose();
    }
}
