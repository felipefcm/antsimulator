
package ffcm.antsim.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import ffcm.antsim.AntSim;
import ffcm.antsim.AntWorld;
import ffcm.antsim.ecs.comps.AntSimMapper;
import ffcm.antsim.ecs.comps.CFoodDecay;
import ffcm.antsim.entity.EntityFactory;
import ffcm.antsim.entity.Food;
import ffcm.antsim.resource.Resources;
import ffcm.antsim.screen.SimulationScreen;
import ffcm.ecs.ECSManager;
import ffcm.ecs.comps.CSpriteAnimation;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.Mapper;

public class FoodSpawnSystem extends EntitySystem implements EntityListener
{
    private ImmutableArray<Entity> entities;

    private static final int MAX_NUM_FOOD = 10;
    private static final float SPAWN_INTERVAL = 5.0f;

    private float spawnTimer = 0;

    private Array<TextureRegion> decayAnimationRegions;

    private AntWorld antWorld;

    public FoodSpawnSystem(int priority)
    {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        Family family = Family.all(CTransform.class, CSpriteAnimation.class, CFoodDecay.class).get();

        entities = engine.getEntitiesFor(family);

        engine.addEntityListener(family, this);

        TextureAtlas mainAtlas = Resources.instance.assetManager.get("gfx/mainSprites.atlas", TextureAtlas.class);

        decayAnimationRegions = new Array<>(false, 2);
        decayAnimationRegions.add(mainAtlas.findRegion("foodFresh"));
        decayAnimationRegions.add(mainAtlas.findRegion("foodOld"));
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity)
    {
        CFoodDecay foodDecay = AntSimMapper.foodDecay.get(entity);
        CSpriteAnimation decayAnimation = Mapper.spriteAnimation.get(entity);

        foodDecay.timeToDecay = MathUtils.random(8.0f, 15.0f);
        decayAnimation.SetRegions(decayAnimationRegions);
    }

    @Override
    public void entityRemoved(Entity entity)
    {
    }

    @Override
    public void update(float deltaTime)
    {
        spawnTimer += deltaTime;

        if(entities.size() < MAX_NUM_FOOD && spawnTimer >= SPAWN_INTERVAL)
        {
            Food food = EntityFactory.instance.CreateFood();

            //FIXME find some better way to obtain world reference
            if(antWorld == null)
                antWorld = ((SimulationScreen) AntSim.instance.getScreen()).antWorld;

            float margin = 30.0f;
            food.transform.position.set
            (
                MathUtils.random(margin, antWorld.terrain.mapSizeWorld.x - 2 * margin),
                MathUtils.random(margin, antWorld.terrain.mapSizeWorld.y - 2 * margin)
            );

            spawnTimer = 0;
        }

        for(Entity entity : entities)
        {
            CFoodDecay foodDecay = AntSimMapper.foodDecay.get(entity);
            CSpriteAnimation decayAnimation = Mapper.spriteAnimation.get(entity);

            foodDecay.decayTimer += deltaTime;

            if(foodDecay.decayTimer >= foodDecay.timeToDecay)
            {
                ECSManager.instance.ecsEngine.removeEntity(entity);
                continue;
            }

            if(foodDecay.decayTimer > foodDecay.timeToDecay * 0.8f)
            {
                decayAnimation.frame = 1;
            }
        }
    }
}
