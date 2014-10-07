
package ffcm.ecs.comps;

import com.badlogic.gdx.math.Vector2;

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
}
