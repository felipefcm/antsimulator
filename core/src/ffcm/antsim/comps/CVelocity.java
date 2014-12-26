
package ffcm.antsim.comps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;

import ffcm.ecs.IComponent;

public class CVelocity implements IComponent
{
	public Vector2 vector;
	public float maxVelocity;
	
	public CVelocity()
	{
		vector = new Vector2(0, 0);
		maxVelocity = 1.0f;
	}

	@Override
	public IComponent Clone()
	{
		CVelocity velocity = new CVelocity();
		velocity.vector = vector.cpy();
		velocity.maxVelocity = maxVelocity;
		
		return velocity;
	}

	@Override
	public IComponent CreateFromJson(final JsonValue jsonObj)
	{
		JsonValue maxVel = jsonObj.get("maxVelocity");
		
		if(maxVel != null)
			maxVelocity = maxVel.asFloat();
		
		return this;
	}
}
