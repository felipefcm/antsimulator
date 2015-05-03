
package ffcm.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import ffcm.ecs.comps.CCollider;
import ffcm.ecs.comps.CRigidBody;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.Mapper;
import ffcm.ecs.render.RenderManager;

public class PhysicsSystem extends EntitySystem implements EntityListener
{
    public static int MeterToPixel;
    public static float PixelToMeter;

    public World world;
    public boolean debugDraw = false;

    private Box2DDebugRenderer debugRenderer;

    private ImmutableArray<Entity> entities;

    private final float stepDuration = 1 / 60.0f;
    private float timeAccum = 0;

    //meterToPixel
    public PhysicsSystem(int meterToPixelFactor, int priority)
    {
        super(priority);

        MeterToPixel = meterToPixelFactor;
        PixelToMeter = 1 / (float) MeterToPixel;

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        Family family = Family.all(CRigidBody.class, CCollider.class).get();

        entities = engine.getEntitiesFor(family);
        engine.addEntityListener(family, this);
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
        world.dispose();
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity)
    {
        CRigidBody rigidBody = Mapper.rigidBody.get(entity);
        CCollider collider = Mapper.collider.get(entity);
        CTransform transform = Mapper.transform.get(entity);

        BodyDef bodyDef = rigidBody.bodyDef;

        if(transform != null)
            bodyDef.position.set(transform.position.x * PixelToMeter, transform.position.y * PixelToMeter);

        FixtureDef fixtureDef = rigidBody.fixtureDef;
        fixtureDef.shape = collider.shape;

        rigidBody.body = world.createBody(bodyDef);

        rigidBody.body.setMassData(rigidBody.massData);
        rigidBody.body.resetMassData();

        rigidBody.body.createFixture(fixtureDef);

        collider.shape.dispose();
    }

    @Override
    public void entityRemoved(Entity entity)
    {
        CRigidBody rigidBody = Mapper.rigidBody.get(entity);

        world.destroyBody(rigidBody.body);
    }

    @Override
    public void update(float deltaTime)
    {
        timeAccum += deltaTime;

        while(timeAccum >= stepDuration)
        {
            timeAccum -= stepDuration;
            world.step(stepDuration, 6, 2);

            for(Entity e : entities)
            {
                CTransform transform = Mapper.transform.get(e);

                if(transform == null)
                    continue;

                CRigidBody rigidBody = Mapper.rigidBody.get(e);

                transform.position.set(rigidBody.body.getPosition()).scl(MeterToPixel);
                transform.rotation = rigidBody.body.getAngle() * MathUtils.radDeg;
            }
        }

        //no special render pass for debug rendering
        if(debugDraw)
        {
            debugRenderer.render(world, RenderManager.instance.state.mainCamera.combined.cpy().scl(MeterToPixel));
        }
    }
}
