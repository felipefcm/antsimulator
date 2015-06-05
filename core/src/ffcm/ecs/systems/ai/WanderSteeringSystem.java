
package ffcm.ecs.systems.ai;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import ffcm.ecs.comps.CRigidBody;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.CVelocity;
import ffcm.ecs.comps.CWander;
import ffcm.ecs.comps.Mapper;

public class WanderSteeringSystem extends EntitySystem implements EntityListener
{
    private static SteeringAcceleration<Vector2> steeringAcceleration;

    private ImmutableArray<Entity> entities;

    public WanderSteeringSystem(int priority)
    {
        super(priority);

        steeringAcceleration = new SteeringAcceleration<>(new Vector2());
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        Family family = Family.all(CWander.class, CTransform.class)
                              .one(CVelocity.class, CRigidBody.class)
                              .get();

        entities = engine.getEntitiesFor(family);
        engine.addEntityListener(family, this);
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity)
    {
        CWander wander = Mapper.wander.get(entity);
        CTransform transform = Mapper.transform.get(entity);

        CVelocity velocity = Mapper.velocity.get(entity);
        CRigidBody rigidBody = Mapper.rigidBody.get(entity);

        wander.transform = transform;
        wander.behaviour.setWanderOrientation(transform.rotation);

        if(velocity != null)
        {
            //has CVelocity
            wander.velocity = velocity;
            wander.rigidBody = null;
        }
        else
        {
            //has CRigidBody
            wander.rigidBody = rigidBody;
            wander.velocity = null;
        }
    }

    @Override
    public void entityRemoved(Entity entity)
    {
    }

    @Override
    public void update(float deltaTime)
    {
        for(Entity e : entities)
        {
            CWander wander = Mapper.wander.get(e);
            //CTransform transform = Mapper.transform.get(e);

            CVelocity velocity = Mapper.velocity.get(e);
            //CRigidBody rigidBody = Mapper.rigidBody.get(e);

            wander.behaviour.calculateSteering(steeringAcceleration);

            if(e instanceof IWanderSteeringCallback)
                if(!((IWanderSteeringCallback) e).WillApplyAcceleration(steeringAcceleration))
                    continue;

            if(velocity != null)
            {
                //has a CVelocity
                velocity.linear.add(steeringAcceleration.linear).clamp(0.0f, wander.maxLinearSpeed);

                velocity.angular += steeringAcceleration.angular;
                velocity.angular = MathUtils.clamp(velocity.angular, -wander.maxAngularSpeed, wander.maxAngularSpeed);
            }
            else
            {
                //has CRigidBody
            }

            //Log.d("Accel: " + steeringAcceleration.linear.x + "," + steeringAcceleration.linear.y + " angle: " + steeringAcceleration.linear.angle());
        }
    }

    public interface IWanderSteeringCallback
    {
        /**
         * Use this callback to be notified before the acceleration is applied.
         * This is the chance to perform any modification in the acceleration or
         * to skip this update frame.
         *
         * @param accel The acceleration calculated by the steering system
         * @return true if the acceleration should be applied, false otherwise
         */
        public boolean WillApplyAcceleration(SteeringAcceleration<Vector2> accel);
    }
}
