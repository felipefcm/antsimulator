
package ffcm.antsim.comps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;

import ffcm.ecs.IComponent;

public class CVelocity implements IComponent
{
	public Vector2 vector;
	
	public CVelocity()
	{
		vector = new Vector2();
	}

	@Override
	public IComponent Clone()
	{
		CVelocity velocity = new CVelocity();
		velocity.vector = vector.cpy();
		
		return velocity;
	}

	@Override
	public IComponent CreateFromJson(final JsonValue jsonObj)
	{
		float[] velocity = jsonObj.asFloatArray();		
		
		vector.set(velocity[0], velocity[1]);
		
		return this;
	}
}
