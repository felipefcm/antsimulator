package ffcm.ecs;

import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CMobile;
import ffcm.ecs.comps.CPosition;

public class ComponentFactory
{
	public static ComponentFactory _instance = new ComponentFactory();
	
	public ComponentFactory()
	{
	}
	
	public Component CreateComponent(String name)
	{
		if(name.equalsIgnoreCase("position"))
		{
			return new CPosition();
		}
		
		if(name.equalsIgnoreCase("mobile"))
		{
			return new CMobile();
		}
		
		if(name.equalsIgnoreCase("drawable"))
		{
			return new CDrawable();
		}
		
		return null;
	}
}
