
package ffcm.ecs.comps;

import com.badlogic.gdx.math.Vector2;

import ffcm.ecs.IComponent;

public class CPathFind implements IComponent
{
	public Vector2 destination;
	
	public CPathFind()
	{
	}
	
	@Override
	public IComponent Clone()
	{
		CPathFind pathFind = new CPathFind();
		
		return pathFind;
	}

}