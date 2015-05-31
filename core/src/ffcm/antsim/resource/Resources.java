package ffcm.antsim.resource;

import com.badlogic.ashley.core.Engine;
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
import ffcm.antsim.ecs.systems.FoodSpawnSystem;
import ffcm.ecs.ECSManager;
import ffcm.ecs.resources.EntityTemplateManager;
import ffcm.ecs.systems.MoveSystem;
import ffcm.ecs.systems.SpatialPartitioningSystem;
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
	public SpatialPartitioningSystem spatialPartitioningSystem;
	public FoodSpawnSystem foodSpawnSystem;

	public Engine ecsEngine;
	public EntityTemplateManager entityTemplateManager;

	public Resources()
	{
	}

	public void Init()
    {
        mainCamera = new OrthographicCamera();
		viewport = new FitViewport(AntSim.V_WIDTH, AntSim.V_HEIGHT, mainCamera);
		viewport.apply(true);

		guiCamera = new OrthographicCamera();
		guiViewport = new FitViewport(AntSim.V_WIDTH, AntSim.V_HEIGHT, guiCamera);

		spriteBatch = new SpriteBatch(1000);
		shapeRenderer = new ShapeRenderer(500);

		font = new BitmapFont();

		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader());

        long seed = new Date().getTime();
		MathUtils.random.setSeed(seed);
		Log.Debug("Using random seed: " + seed);
    }

    public void InitSystems()
	{
        ecsEngine = ECSManager.instance.ecsEngine;

        wanderSteeringSystem = new WanderSteeringSystem(0);

        foodSpawnSystem = new FoodSpawnSystem(1);
        moveSystem = new MoveSystem(5);

        spatialPartitioningSystem = new SpatialPartitioningSystem(10, 2);

        spriteDrawSystem = new SpriteDrawSystem(15);

        ecsEngine.addSystem(wanderSteeringSystem);
        ecsEngine.addSystem(moveSystem);
        ecsEngine.addSystem(spatialPartitioningSystem);
        ecsEngine.addSystem(spriteDrawSystem);
        ecsEngine.addSystem(foodSpawnSystem);
	}

	public boolean InitEntityTemplates()
	{
	    entityTemplateManager = ECSManager.instance.entityTemplateManager;

        if(!entityTemplateManager.ProcessTemplateFile("data/ant.json"))
            return false;

        if(!entityTemplateManager.ProcessTemplateFile("data/nest.json"))
            return false;

        if(!entityTemplateManager.ProcessTemplateFile("data/food.json"))
            return false;

        return true;
	}

	public void Dispose()
    {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        assetManager.dispose();

        ecsEngine.removeSystem(wanderSteeringSystem);
        ecsEngine.removeSystem(moveSystem);
        ecsEngine.removeSystem(spatialPartitioningSystem);
        ecsEngine.removeSystem(spriteDrawSystem);
        ecsEngine.removeSystem(foodSpawnSystem);
    }
}
