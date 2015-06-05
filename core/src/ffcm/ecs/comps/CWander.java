
package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;

import ffcm.ecs.resources.ILoadableFromJSON;

public class CWander extends Component implements Steerable<Vector2>, ILoadableFromJSON
{
    public Wander<Vector2> behaviour;

    //acceleration limits are in modulus
    public float maxLinearSpeed = 20.0f;
    public float maxAngularSpeed = 20.0f;
    public float maxLinearAcceleration = 5.0f;
    public float maxAngularAcceleration = 3.0f;

    //the entity components references must be kept to be used by the steering behaviour
    //they will be set when added to the wander system
    public CTransform transform;
    public CVelocity velocity;
    public CRigidBody rigidBody;

    public CWander()
    {
        this(1.0f, 1.0f);
    }

    public CWander(float radius, float offset)
    {
        this(radius, offset, 1.0f, 0.0f);
    }

    public CWander(float radius, float offset, float rate, float orientation)
    {
        behaviour = new Wander<>(this);

        behaviour.setWanderRadius(radius);
        behaviour.setWanderOffset(offset);
        behaviour.setWanderRate(rate);
        behaviour.setWanderOrientation(orientation);
    }

    public CWander(CWander wander)
    {
        behaviour = new Wander<>(this);

        maxLinearSpeed = wander.maxLinearSpeed;
        maxAngularSpeed = wander.maxAngularSpeed;
        maxLinearAcceleration = wander.maxLinearAcceleration;
        maxAngularAcceleration = wander.maxAngularAcceleration;

        behaviour.setWanderOrientation(wander.behaviour.getWanderOrientation());
        behaviour.setWanderRadius(wander.behaviour.getWanderRadius());
        behaviour.setWanderOffset(wander.behaviour.getWanderOffset());
        behaviour.setWanderRate(wander.behaviour.getWanderRate());
    }

    @Override
    public Vector2 getPosition()
    {
        return transform.position;
    }

    @Override
    public float getOrientation()
    {
        //face the linear velocity vector
        if(velocity != null)
            return velocity.linear.angleRad();
        else
            return rigidBody.body.getAngle();
    }

    @Override
    public Vector2 getLinearVelocity()
    {
        if(velocity != null)
            return velocity.linear;
        else
            return rigidBody.body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity()
    {
        if(velocity != null)
            return velocity.angular;
        else
            return rigidBody.body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius()
    {
        return 0;
    }

    @Override
    public boolean isTagged()
    {
        return false;
    }

    @Override
    public void setTagged(boolean tagged)
    {
    }

    @Override
    public Vector2 newVector()
    {
        return new Vector2();
    }

    @Override
    public float vectorToAngle(Vector2 vector)
    {
        return vector.angleRad();
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = MathUtils.cos(angle);
        outVector.y = MathUtils.sin(angle);

        return outVector;
    }

    @Override
    public float getMaxLinearSpeed()
    {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed)
    {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration()
    {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration)
    {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed()
    {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed)
    {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration()
    {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration)
    {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public void LoadFromJSON(JsonValue jsonValue)
    {
        maxLinearSpeed = jsonValue.get("maxLinearSpeed").asFloat();
        maxAngularSpeed = jsonValue.get("maxAngularSpeed").asFloat();
        maxLinearAcceleration = jsonValue.get("maxLinearAcceleration").asFloat();
        maxAngularAcceleration = jsonValue.get("maxAngularAcceleration").asFloat();

        behaviour.setWanderOrientation(jsonValue.get("orientation").asFloat());
        behaviour.setWanderRadius(jsonValue.get("radius").asFloat());
        behaviour.setWanderOffset(jsonValue.get("offset").asFloat());
        behaviour.setWanderRate(jsonValue.get("rate").asFloat());
    }
}
