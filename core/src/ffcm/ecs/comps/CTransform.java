
package ffcm.ecs.comps;

import com.badlogic.gdx.math.Vector2;

public class CTransform implements IComponent
{
	public Vector2 position;
	public float rotation;
	
	public CTransform()
	{
		position = new Vector2();
		rotation = 0;
	}

	@Override
	public IComponent Clone()
	{
		CTransform transform = new CTransform();
		
		transform.position = position.cpy();
		transform.rotation = rotation;
		
		return transform;
	}
}
