
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

    public float maxLinearSpeed = 20.0f;
    public float maxLinearAcceleration = 5.0f;
    public float maxAngularSpeed = 20.0f;
    public float maxAngularAcceleration = 3.0f;

    //these values will be updated by the WanderSystem
    private Vector2 position;
    private Vector2 linearVel;
    private float angularVel;

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
        behaviour = wander.behaviour;
        maxLinearSpeed = wander.maxLinearSpeed;
        maxLinearAcceleration = wander.maxLinearAcceleration;
        maxAngularSpeed = wander.maxAngularSpeed;
        maxAngularAcceleration = wander.maxAngularAcceleration;

        position = wander.position.cpy();
        linearVel = wander.linearVel.cpy();
        angularVel = wander.angularVel;
    }

    public void SetSteerablePosition(Vector2 position)
    {
        this.position = position;
    }

    public void SetSteerableLinearVelocity(Vector2 linearVel)
    {
        this.linearVel = linearVel;
    }

    public void SetSteerableAngularVel(float angularVel)
    {
        this.angularVel = angularVel;
    }

    @Override
    public Vector2 getPosition()
    {
        return position;
    }

    @Override
    public float getOrientation()
    {
        return linearVel.angleRad();
    }

    @Override
    public Vector2 getLinearVelocity()
    {
        return linearVel;
    }

    @Override
    public float getAngularVelocity()
    {
        return angularVel;
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

    }
}
