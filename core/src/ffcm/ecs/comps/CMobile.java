
package ffcm.ecs.comps;

import com.badlogic.gdx.utils.JsonValue;

import ffcm.ecs.Component;
import ffcm.ecs.compInterfaces.MobileComponentInterface;

public class CMobile extends MobileComponentInterface
{
	public CMobile()
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
		return MOBILE_KEY;
	}
		
	@Override
	public void Update()
	{
		
	}

	@Override
	public Component Clone()
	{
		CMobile cp = new CMobile();	
		
		return cp;
	}
}
