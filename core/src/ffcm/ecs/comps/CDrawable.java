
package ffcm.ecs.comps;

import com.badlogic.gdx.utils.JsonValue;

import ffcm.ecs.Component;
import ffcm.ecs.compInterfaces.DrawableComponentInterface;

public class CDrawable extends DrawableComponentInterface
{
	public CDrawable()
	{
	}
	
	@Override
	public boolean InitComponent(JsonValue initData)
	{
		return true;
	}
	
	@Override
	public int GetComponentKey()
	{
		return DRAWABLE_KEY;
	}

	@Override
	public Component Clone()
	{
		CDrawable cp = new CDrawable();
		
		return cp;
	}
}
