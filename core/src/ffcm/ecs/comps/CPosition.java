
package ffcm.ecs.comps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;

import ffcm.ecs.Component;
import ffcm.ecs.compInterfaces.PositionComponentInterface;

public class CPosition extends PositionComponentInterface
{
	public Vector2 position;
	
	public CPosition()
	{
	}
	
	@Override
	public boolean InitComponent(JsonValue initData)
	{
		float[] pos = initData.asFloatArray();
		
		position = new Vector2(pos[0], pos[1]);
		
		return true;
	}
	
	@Override
	public int GetComponentKey()
	{
		return POSITION_KEY;
	}

	@Override
	public Component Clone()
	{
		CPosition cp = new CPosition();
		
		cp.position = position.cpy();
		
		return cp;
	}
}
